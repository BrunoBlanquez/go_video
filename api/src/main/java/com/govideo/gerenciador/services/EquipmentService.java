package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipmentDTO;
import com.govideo.gerenciador.dtos.MessageResponseDTO;
import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.exceptions.ResourceNotFoundException;
import com.govideo.gerenciador.forms.EquipmentForm;
import com.govideo.gerenciador.repositories.EquipmentRepository;
import com.govideo.gerenciador.repositories.EquipmentRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentRentRepository equipmentRentRepository;

    public Page<EquipmentDTO> consultar(Pageable pageable) {
        Page<Equipment> equipaments = equipmentRepository.findAll(pageable);
        return EquipmentDTO.convertToDTO(equipaments);
    }

    public Page<EquipmentDTO> consultarAtivos(Pageable pageable) {
        Page<Equipment> equipaments = equipmentRepository.findAtivos(pageable);
        return EquipmentDTO.convertToDTO(equipaments);
    }

    public EquipmentDTO consultarPorIdRetornarDTO(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipment not found!"));
        return new EquipmentDTO(equipment);
    }

    public Equipment consultById(Long id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipment not found!"));
    }

    public Page<EquipmentDTO> findAllByStatus(StatusEquipment status, Pageable pageable) {
        return equipmentRepository.findByStatus(status, pageable).map(EquipmentDTO::new);
    }

    @Transactional
    public EquipmentDTO cadastrar(EquipmentForm equipmentForm) {
        Equipment equipment = equipmentForm.toDomain();
        equipment = equipmentRepository.save(equipment);
        return new EquipmentDTO(equipment);
    }

    @Transactional
    public EquipmentDTO alterar(Long id, EquipmentForm equipmentForm) {
        Equipment equipment = consultById(id);

        equipment.setModel(equipmentForm.getModel());
        equipment.setDescription(equipmentForm.getDescription());
        equipment.setBrand(equipmentForm.getBrand());
        equipment.setCategory(equipmentForm.getCategory());
        equipment.setImageURL(equipmentForm.getImageURL());
        equipment = equipmentRepository.save(equipment);

        return new EquipmentDTO(equipment);
    }

    @Transactional
    public EquipmentDTO changeStatus(Long id, StatusEquipment status) {
        Equipment equipment = consultById(id);
        equipment.setStatus(status);
        equipment = equipmentRepository.save(equipment);

        return new EquipmentDTO(equipment);
    }

    @Transactional
    public MessageResponseDTO deleteOrInactivateEquipment(Long id) {
        Equipment equipment = consultById(id);
        Pageable pageable = PageRequest.of(0, 10);

        if (!equipment.isAvailable()) {
            throw new OperationNotAllowedException("Current equipment status is " + equipment.getStatus() + ", therefore it can't be inactivated or deleted!");
        }

        if (!equipmentRentRepository.findByEquipment(equipment, pageable).hasContent()) {
            equipmentRepository.delete(equipment);
            return new MessageResponseDTO("Equipament successfully deleted!");
        }

        changeStatus(id, StatusEquipment.INACTIVE);
        return new MessageResponseDTO("Equipament successfully inactivated!");
    }

}
