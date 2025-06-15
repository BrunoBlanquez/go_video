package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipmentDTO;
import com.govideo.gerenciador.dtos.MessageResponseDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.exceptions.ResourceNotFoundException;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.repositories.EquipmentRentRepository;
import com.govideo.gerenciador.repositories.EquipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class EquipmentServiceTest {

    @InjectMocks
    private EquipmentService equipmentService;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private EquipmentRentRepository equipmentRentRepository;

    public Equipment mockEquipamentoEntity() {
        Equipment equipment = new Equipment("Pocket Cinema 6K", "Filmadora profissional Pocket Cinema 6K", "Black Magic", "Filmadoras", "https://emania.vteximg.com.br/arquivos/ids/209607");
        equipment.setId(1L);
        return equipment;
    }

    public EquipamentoForm mockEquipamentoForm() {
        return new EquipamentoForm("Pocket Cinema 6K", "Filmadora profissional Pocket Cinema 6K", "Black Magic", "Filmadoras", "https://emania.vteximg.com.br/arquivos/ids/209607");
    }

    public Page<Equipment> mockEquipamentoPage() {
        return new PageImpl<>(Collections.singletonList(mockEquipamentoEntity()));
    }

    public Page<Emprestimo> mockEmprestimoPage(Equipment equipment) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setEquipamento(equipment);
        return new PageImpl<>(Collections.singletonList(emprestimo));
    }

    @Test
    public void deveriaCadastrarEquipamento() {
        when(equipmentRepository.save(any())).thenReturn(mockEquipamentoEntity());
        EquipmentDTO retornoEquipamento = equipmentService.cadastrar(mockEquipamentoForm());
        assertEquals(1L, (long) retornoEquipamento.getId());
    }

    @Test
    public void deveriaRetornarEquipamentoAoBuscarPorId() {
        when(equipmentRepository.findById(any())).thenReturn(Optional.of(mockEquipamentoEntity()));
        Equipment retornoEquipment = equipmentService.consultById(1L);
        assertNotNull(retornoEquipment);
    }

    @Test
    public void naoDeveriaRetornarEquipamentoAoBuscarPorId() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            when(equipmentRepository.findById(any())).thenReturn(Optional.empty());
            equipmentService.consultById(1L);
        });
        assertEquals("Equipamento não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarEquipamentoDTOAoBuscarPorId() {
        when(equipmentRepository.findById(any())).thenReturn(Optional.of(mockEquipamentoEntity()));
        EquipmentDTO retornoEquipamento = equipmentService.consultarPorIdRetornarDTO(1L);
        assertNotNull(retornoEquipamento);
    }

    @Test
    public void naoDeveriaRetornarEquipamentoDTOAoBuscarPorId() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            when(equipmentRepository.findById(any())).thenReturn(Optional.empty());
            equipmentService.consultarPorIdRetornarDTO(1L);
        });
        assertEquals("Equipamento não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarTodosOsEquipamentos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(equipmentRepository.findAll(pageable)).thenReturn(mockEquipamentoPage());
        Page<EquipmentDTO> equipamentoDTOPage = equipmentService.consultar(pageable);
        assertEquals(1L, equipamentoDTOPage.getTotalElements());
    }

    @Test
    public void deveriaRetornarEquipamentosComStatusDisponivel() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(equipmentRepository.findAll(paginacao)).thenReturn(mockEquipamentoPage());
        when(equipmentRepository.findByStatus(StatusEquipment.AVAILABLE, paginacao)).thenReturn(mockEquipamentoPage());
        Page<EquipmentDTO> equipamentoDTO = equipmentService.findAllByStatus(StatusEquipment.AVAILABLE, paginacao);
        assertEquals(1L, equipamentoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEquipamentosComStatusIndisponivel() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(equipmentRepository.findAll(paginacao)).thenReturn(mockEquipamentoPage());
        when(equipmentRepository.findByStatus(StatusEquipment.UNAVAILABLE, paginacao)).thenReturn(mockEquipamentoPage());
        Page<EquipmentDTO> equipamentoDTO = equipmentService.findAllByStatus(StatusEquipment.UNAVAILABLE, paginacao);
        assertEquals(1L, equipamentoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEquipamentosComStatusInativo() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(equipmentRepository.findAll(paginacao)).thenReturn(mockEquipamentoPage());
        when(equipmentRepository.findByStatus(StatusEquipment.INACTIVE, paginacao)).thenReturn(mockEquipamentoPage());
        Page<EquipmentDTO> equipamentoDTO = equipmentService.findAllByStatus(StatusEquipment.INACTIVE, paginacao);
        assertEquals(1L, equipamentoDTO.getTotalElements());
    }

    @Test
    public void deveriaAlterarEquipamentoComSucesso() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(mockEquipamentoEntity()));
        when(equipmentRepository.save(any())).thenReturn(mockEquipamentoEntity());
        EquipmentDTO retornoEquipamento = equipmentService.alterar(1L, mockEquipamentoForm());
        assertEquals(1L, (long) retornoEquipamento.getId());
    }

    @Test
    public void deveriaAlterarStatusDeEquipamentoComSucesso() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(mockEquipamentoEntity()));
        Equipment equipment = mockEquipamentoEntity();
        equipment.setStatus(StatusEquipment.INACTIVE);
        when(equipmentRepository.save(any())).thenReturn(equipment);
        EquipmentDTO retornoEquipamento = equipmentService.changeStatus(1L, StatusEquipment.INACTIVE);
        assertEquals(StatusEquipment.INACTIVE, retornoEquipamento.getStatus());
    }

    @Test
    public void deveriaExcluirEquipamentoDisponivelSemEmprestimos() {
        Pageable paginacao = PageRequest.of(0, 10);
        Equipment equipment = mockEquipamentoEntity();
        when(equipmentRepository.findById(any())).thenReturn(Optional.of(equipment));
        when(equipmentRentRepository.findByEquipment(equipment, paginacao)).thenReturn(Page.empty());
        doNothing().when(equipmentRepository).delete(any());
        MessageResponseDTO retorno = equipmentService.deleteEquipment(1L);
        assertEquals("Equipamento excluído com sucesso!", retorno.getMensagem());
    }

    @Test
    public void deveriaExibirMensagemAoTentarExcluirEquipamentoIndisponivel() {
        Pageable paginacao = PageRequest.of(0, 10);
        Equipment equipment = mockEquipamentoEntity();
        equipment.setStatus(StatusEquipment.INDISPONÍVEL);

        OperationNotAllowedException exception = assertThrows(OperationNotAllowedException.class, () -> {
            when(equipmentRepository.findById(any())).thenReturn(Optional.of(equipment));
            when(equipmentRentRepository.findByEquipment(equipment, paginacao)).thenReturn(mockEmprestimoPage(equipment));
            MessageResponseDTO retorno = equipmentService.deleteEquipment(1L);
        });
        assertEquals("O status atual do equipamento é INDISPONÍVEL, então ele não pode ser inativado ou excluído!", exception.getMessage());

        verify(equipmentRepository, Mockito.times(0)).delete(equipment);
    }

    @Test
    public void deveriaInativarEquipamentoDisponivelComEmprestimos() {
        Pageable paginacao = PageRequest.of(0, 10);
        Equipment equipment = mockEquipamentoEntity();
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(any())).thenReturn(equipment);
        when(equipmentRentRepository.findByEquipment(equipment, paginacao)).thenReturn(mockEmprestimoPage(equipment));
        MessageResponseDTO retorno = equipmentService.deleteEquipment(1L);
        assertEquals("Equipamento inativado com sucesso!", retorno.getMensagem());
        verify(equipmentRepository, Mockito.times(1)).save(equipment);
        verify(equipmentRepository, Mockito.times(0)).delete(equipment);
    }

}
