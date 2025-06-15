package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.exceptions.ResourceNotFoundException;
import com.govideo.gerenciador.repositories.EquipmentRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EquipmentRentRepository equipmentRentRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private EquipmentService equipmentService;

    public Page<EmprestimoDTO> consultar(Pageable paginacao) {
        Page<Emprestimo> emprestimos = equipmentRentRepository.findAll(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public EmprestimoDTO consultarPorIdRetornarDTO(Long id, Usuario usuarioLogado) {
        Emprestimo emprestimo = equipmentRentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado!"));

        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();
        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !usuarioLogado.equals(emprestimo.getUsuario())) {
            throw new OperationNotAllowedException("Não é possível consultar empréstimos de outro colaborador!");
        }
        return new EmprestimoDTO(emprestimo);
    }

    public Emprestimo consultarPorId(Long id) {
        return equipmentRentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado!"));
    }

    public Page<EmprestimoDTO> consultarEncerrados(Pageable paginacao) {
        Page<Emprestimo> emprestimos = equipmentRentRepository.findByDataFimIsNotNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarVigentes(Pageable paginacao) {
        Page<Emprestimo> emprestimos = equipmentRentRepository.findByDataFimIsNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosPorEquipamento(Long idEquipamento, Pageable paginacao) {
        Equipment equipment = equipmentService.consultById(idEquipamento);
        Page<Emprestimo> emprestimos = equipmentRentRepository.findByEquipment(equipment, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperationNotAllowedException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = equipmentRentRepository.findByUsuario(usuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosEncerradosPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperationNotAllowedException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = equipmentRentRepository.findEncerradosByUsuario(idUsuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosVigentesPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperationNotAllowedException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = equipmentRentRepository.findVigentesByUsuario(idUsuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    @Transactional
    public EmprestimoDTO cadastrar(Long idEquipamento, Usuario usuarioLogado) throws EquipamentoNaoDisponivelException {
        Equipment equipment = equipmentService.consultById(idEquipamento);

        if (equipment.getStatus() == StatusEquipment.AVAILABLE) {
            Emprestimo emprestimo = new Emprestimo(equipment, usuarioLogado);
            emprestimo = equipmentRentRepository.save(emprestimo);
            equipmentService.changeStatus(idEquipamento, StatusEquipment.UNAVAILABLE);
            return new EmprestimoDTO(emprestimo);
        } else {
            throw new EquipamentoNaoDisponivelException("O equipamento informado não está disponível para empréstimo.");
        }
    }

    @Transactional
    public EmprestimoDTO encerrar(Long id, Usuario usuarioLogado) {
        Emprestimo emprestimo = consultarPorId(id);

        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !usuarioLogado.equals(emprestimo.getUsuario())) {
            throw new OperationNotAllowedException("Não é possível encerrar empréstimos de outro colaborador!");
        }

        emprestimo.setDataFim(LocalDateTime.now());
        equipmentRentRepository.save(emprestimo);
        equipmentService.changeStatus(emprestimo.getEquipamento().getId(), StatusEquipment.AVAILABLE);
        return new EmprestimoDTO(emprestimo);
    }

}
