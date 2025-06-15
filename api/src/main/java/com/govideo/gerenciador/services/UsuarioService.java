package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.MessageResponseDTO;
import com.govideo.gerenciador.dtos.UsuarioDTO;
import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusUsuario;
import com.govideo.gerenciador.exceptions.ConflitoDeEmailException;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.exceptions.ResourceNotFoundException;
import com.govideo.gerenciador.forms.AlteraNomeUsuarioForm;
import com.govideo.gerenciador.forms.AlteraSenhaUsuarioForm;
import com.govideo.gerenciador.forms.UsuarioForm;
import com.govideo.gerenciador.repositories.EquipmentRentRepository;
import com.govideo.gerenciador.repositories.PerfilRepository;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private EquipmentRentRepository equipmentRentRepository;

    public Page<UsuarioDTO> consultar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return UsuarioDTO.converterParaDTO(usuarios);
    }

    public UsuarioDTO consultarPorIdRetornarDTO(Long id, Usuario usuarioLogado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(id)) {
            throw new OperationNotAllowedException("Não é possível consultar perfil de outro colaborador!");
        }
        return new UsuarioDTO(usuario);
    }

    public Usuario consultarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public Page<UsuarioDTO> consultarPorStatus(String statusString, Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);

        if (statusString != null) {
            if (statusString.equalsIgnoreCase("ATIVO")) {
                usuarios = usuarioRepository.findByStatus(StatusUsuario.ATIVO, paginacao);
            } else if (statusString.equalsIgnoreCase("INATIVO")) {
                usuarios = usuarioRepository.findByStatus(StatusUsuario.INATIVO, paginacao);
            }
        }
        return UsuarioDTO.converterParaDTO(usuarios);
    }

    @Transactional
    public UsuarioDTO cadastrar(UsuarioForm usuarioForm) {
        Usuario usuario = usuarioForm.converterParaEntidade();
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ConflitoDeEmailException("Já existe cadastro com o e-mail informado!");
        }
        if (perfilRepository.existsByPerfil("ROLE_COLABORADOR")) {
            Optional<Perfil> perfilColaborador = perfilRepository.findByPerfil("ROLE_COLABORADOR");
            usuario.getPerfis().add(perfilColaborador.get());
        } else {
            Perfil perfil = perfilRepository.save(new Perfil("ROLE_COLABORADOR"));
            usuario.getPerfis().add(perfil);
        }
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario = usuarioRepository.save(usuario);
        perfilRepository.save(usuario.getPerfis().get(0));
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO resetarSenha(Long id) {
        Usuario usuario = consultarPorId(id);

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getEmail() + "123"));
        usuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO alterarSenha(Long id, Usuario usuarioLogado, AlteraSenhaUsuarioForm usuarioForm) {
        Usuario usuario = consultarPorId(id);
        Long idUsuarioLogado = usuarioLogado.getId();

        if (!idUsuarioLogado.equals(id)) {
            throw new OperationNotAllowedException("Não é possível alterar a senha de outro colaborador!");
        }

        var passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(usuarioForm.getSenhaAntiga(), usuario.getSenha())) {
            throw new IllegalArgumentException("Sua senha antiga foi inserida incorretamente. Digite-a novamente!");
        }
        if (!usuarioForm.getNovaSenha().equals(usuarioForm.getConfirmaNovaSenha())) {
            throw new IllegalArgumentException("Certifique-se de que as senhas correspondam!");
        }
        if (passwordEncoder.matches(usuarioForm.getNovaSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Crie uma nova senha diferente da atual!");
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioForm.getNovaSenha()));
        usuario = usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO alterarNome(Long id, AlteraNomeUsuarioForm usuarioForm) {
        Usuario usuario = consultarPorId(id);

        usuario.setNome(usuarioForm.getNome());
        usuario = usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario);
    }

    @Transactional
    public MessageResponseDTO inativar(Long id) {
        Usuario usuario = consultarPorId(id);
        String mensagem;
        Pageable paginacao = PageRequest.of(0, 10);

        if (usuario.getEmail().equals("admin@email.com")) {
            throw new OperationNotAllowedException("O administrador não pode ser inativado!");
        }

        if (equipmentRentRepository.findVigentesByUsuario(id, paginacao).hasContent()) {
            throw new OperationNotAllowedException("Usuários com empréstimos vigentes não podem ser inativados!");
        } else {
            usuario.setStatus(StatusUsuario.INATIVO);
            mensagem = "Usuário inativado com sucesso!";
        }

        usuarioRepository.save(usuario);
        return new MessageResponseDTO(mensagem);
    }

}
