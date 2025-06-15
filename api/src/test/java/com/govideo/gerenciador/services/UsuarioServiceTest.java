package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.MessageResponseDTO;
import com.govideo.gerenciador.dtos.UsuarioDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusUsuario;
import com.govideo.gerenciador.exceptions.ConflitoDeEmailException;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.exceptions.ResourceNotFoundException;
import com.govideo.gerenciador.forms.AlteraNomeUsuarioForm;
import com.govideo.gerenciador.forms.UsuarioForm;
import com.govideo.gerenciador.repositories.EquipmentRentRepository;
import com.govideo.gerenciador.repositories.PerfilRepository;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private EquipmentRentRepository equipmentRentRepository;

    public Usuario mockUsuarioEntity() {
        Usuario usuario = new Usuario("Nome", "usuario@email.com", "123");
        usuario.setId(1L);
        Perfil perfil = new Perfil("ROLE_COLABORADOR");
        perfil.setId(1L);
        usuario.getPerfis().add(perfil);
        return usuario;
    }

    public UsuarioForm mockUsuarioForm() {
        return new UsuarioForm("Usuario", "usuario@email.com", "123");
    }

    public AlteraNomeUsuarioForm mockAlteraNomeUsuarioForm() {
        return new AlteraNomeUsuarioForm("Usuario");
    }

    public Page<Usuario> mockUsuarioPage() {
        return new PageImpl<>(Collections.singletonList(mockUsuarioEntity()));
    }

    public Page<Emprestimo> mockEmprestimoPage() {
        Equipment equipment = new Equipment("Pocket Cinema 6K", "Filmadora profissional Pocket Cinema 6K", "Black Magic", "Filmadoras", "https://emania.vteximg.com.br/arquivos/ids/209607");
        equipment.setId(1L);
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setEquipamento(equipment);
        return new PageImpl<>(Collections.singletonList(emprestimo));
    }

    @Test
    public void deveriaCadastrarUsuario() {
        when(usuarioRepository.save(any())).thenReturn(mockUsuarioEntity());
        UsuarioDTO retornoUsuario = usuarioService.cadastrar(mockUsuarioForm());
        assertEquals("usuario@email.com", retornoUsuario.getEmail());
    }

    @Test
    public void naoDeveriaCadastrarUsuarioComEmailExistente() {
        ConflitoDeEmailException exception = assertThrows(ConflitoDeEmailException.class, () -> {
            when(usuarioRepository.existsByEmail(any())).thenReturn(true);
            UsuarioDTO retornoUsuario = usuarioService.cadastrar(new UsuarioForm("Colaborador", "colaborador@email.com", "123"));
        });
        assertEquals("Já existe cadastro com o e-mail informado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarUsuarioAoBuscarPorId() {
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(mockUsuarioEntity()));
        Usuario retornoUsuario = usuarioService.consultarPorId(1L);
        assertNotNull(retornoUsuario);
    }

    @Test
    public void naoDeveriaRetornarUsuarioAoBuscarPorId() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            when(usuarioRepository.findById(any())).thenReturn(Optional.empty());
            usuarioService.consultarPorId(1L);
        });
        assertEquals("Usuário não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarUsuarioDTOAoBuscarPorId() {
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(mockUsuarioEntity()));
        UsuarioDTO retornoUsuario = usuarioService.consultarPorIdRetornarDTO(1L, mockUsuarioEntity());
        assertNotNull(retornoUsuario);
    }

    @Test
    public void naoDeveriaRetornarUsuarioDTOAoBuscarPorId() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            when(usuarioRepository.findById(any())).thenReturn(Optional.empty());
            usuarioService.consultarPorIdRetornarDTO(1L, mockUsuarioEntity());
        });
        assertEquals("Usuário não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarTodosOsUsuarios() {
        Pageable pageable = PageRequest.of(0, 10);
        when(usuarioRepository.findAll(pageable)).thenReturn(mockUsuarioPage());
        Page<UsuarioDTO> usuarioDTOPage = usuarioService.consultar(pageable);
        assertEquals(1L, usuarioDTOPage.getTotalElements());
    }

    @Test
    public void deveriaRetornarUsuariosComStatusAtivo() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(usuarioRepository.findAll(paginacao)).thenReturn(mockUsuarioPage());
        when(usuarioRepository.findByStatus(StatusUsuario.ATIVO, paginacao)).thenReturn(mockUsuarioPage());
        Page<UsuarioDTO> usuarioDTO = usuarioService.consultarPorStatus("ATIVO", paginacao);
        assertEquals(1L, usuarioDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarUsuariosComStatusInativo() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(usuarioRepository.findAll(paginacao)).thenReturn(mockUsuarioPage());
        when(usuarioRepository.findByStatus(StatusUsuario.INATIVO, paginacao)).thenReturn(mockUsuarioPage());
        Page<UsuarioDTO> usaurioDTO = usuarioService.consultarPorStatus("INATIVO", paginacao);
        assertEquals(1L, usaurioDTO.getTotalElements());
    }

    @Test
    public void deveriaAlterarNomeDeUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(mockUsuarioEntity()));
        when(usuarioRepository.save(any())).thenReturn(mockUsuarioEntity());
        UsuarioDTO retornoUsuario = usuarioService.alterarNome(1L, mockAlteraNomeUsuarioForm());
        assertEquals(1L, (long) retornoUsuario.getId());
    }

    @Test
    public void deveriaInativarUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        when(equipmentRentRepository.findVigentesByUsuario(usuario.getId(), paginacao)).thenReturn(Page.empty());
        usuario.setStatus(StatusUsuario.INATIVO);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        MessageResponseDTO retorno = usuarioService.inativar(usuario.getId());
        assertEquals("Usuário inativado com sucesso!", retorno.getMensagem());
    }

    @Test
    public void naoDeveriaInativarUsuarioComEmprestimoVigente() {
        OperationNotAllowedException exception = assertThrows(OperationNotAllowedException.class, () -> {
            Pageable paginacao = PageRequest.of(0, 10);
            Usuario usuario = mockUsuarioEntity();
            when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
            when(equipmentRentRepository.findVigentesByUsuario(usuario.getId(), paginacao)).thenReturn(mockEmprestimoPage());
            usuarioService.inativar(usuario.getId());
        });
        assertEquals("Usuários com empréstimos vigentes não podem ser inativados!", exception.getMessage());
    }

}
