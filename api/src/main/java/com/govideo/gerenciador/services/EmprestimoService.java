package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.repositories.EmprestimoRepository;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EquipamentoService equipamentoService;

    //TODO: APAGAR APÓS IMPLEMENTAÇÃO DE SECURITY
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<EmprestimoDTO> consultar(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findAll(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public EmprestimoDTO consultarPorIdRetornarDTO(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado!"));
        return new EmprestimoDTO(emprestimo);
    }

    public Emprestimo consultarPorId(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado!"));
    }

    public Page<EmprestimoDTO> consultarEncerrados(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findByDataFimIsNotNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarVigentes(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findByDataFimIsNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosPorEquipamento(Long idEquipamento, Pageable paginacao){
        Equipamento equipamento = equipamentoService.consultarPorId(idEquipamento);
        Page<Emprestimo> emprestimos = emprestimoRepository.findByEquipamento(equipamento, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    //TODO: VALIDAR QUAL USUARIO ESTÁ LOGADO PARA TER ACESSO OU NÃO A ESSA BUSCA
    //TODO: CRIAR NA SERVICE DE USUARIO UMA CONSULTA POR ID, COMO A DE EQUIPAMENTOS E SUBSTITUIR O OPTIONAL DENTRO DESSE MÉTODO
    public Page<EmprestimoDTO> consultarEmprestimosPorUsuario(Long idUsuario, Pageable paginacao){
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        Page<Emprestimo> emprestimos = emprestimoRepository.findByUsuario(usuario.get(), paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    //TODO: CRIAR NA SERVICE DE USUARIO UMA CONSULTA POR ID, COMO A DE EQUIPAMENTOS E SUBSTITUIR O OPTIONAL DENTRO DESSE MÉTODO
    public Page<EmprestimoDTO> consultarEmprestimosVigentesPorUsuario(Long idUsuario, Pageable paginacao){
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        Page<Emprestimo> emprestimos = emprestimoRepository.findPorUsuarioEStatus(usuario.get().getId(), paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    //TODO: AVALIAR SE O USUÁRIO ESTÁ NA CONTROLLER E É PASSADO PARA O SERVICE OU SE É CAPTURADO E TRATADO AQUI
    @Transactional
    public EmprestimoDTO cadastrar(Long idEquipamento) {
        Equipamento equipamento = equipamentoService.consultarPorId(idEquipamento);
        Emprestimo emprestimo = null;


        //TODO: BUSCAR O USUÁRIO PELO ID (NA SERVICE DE USUARIO) QUE VEM PELA CONTROLLER
        Usuario usuario = new Usuario();
        usuarioRepository.save(usuario);

        try {
            if (equipamento.getStatus() == StatusEquipamento.DISPONIVEL) {
                emprestimo = new Emprestimo(equipamento, usuario);
                emprestimo = emprestimoRepository.save(emprestimo);
                equipamentoService.alterarStatus(idEquipamento, StatusEquipamento.INDISPONIVEL);
            } else {
                throw new EquipamentoNaoDisponivelException("O equipamento informado não está disponível para empréstimo.");
            }
        } catch (EquipamentoNaoDisponivelException e) {
        }

        return new EmprestimoDTO(emprestimo);
    }

    @Transactional
    public EmprestimoDTO encerrar(Long id) {
        Emprestimo emprestimo = consultarPorId(id);
        emprestimo.setDataFim(LocalDateTime.now());
        emprestimoRepository.save(emprestimo);
        equipamentoService.alterarStatus(emprestimo.getEquipamento().getId(), StatusEquipamento.DISPONIVEL);
        return new EmprestimoDTO(emprestimo);
    }

}
