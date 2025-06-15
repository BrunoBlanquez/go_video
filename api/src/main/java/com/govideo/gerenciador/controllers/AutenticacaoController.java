package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.TokenDTO;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusUsuario;
import com.govideo.gerenciador.exceptions.CredenciaisIncorretasException;
import com.govideo.gerenciador.exceptions.OperationNotAllowedException;
import com.govideo.gerenciador.forms.LoginForm;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import com.govideo.gerenciador.services.AutenticacaoService;
import com.govideo.gerenciador.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@Tag(name = "Autenticação Endpoint")
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Operation(summary = "Logar com email e senha")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Optional<Usuario> usuario = usuarioRepository.findByEmail(form.getEmail());
        if (usuario.isPresent()) {
            if (usuario.get().getStatus().equals(StatusUsuario.INATIVO)) {
                throw new OperationNotAllowedException("Seu perfil está inativo, entre em contato com o administrador do sistema");
            }
        } else {
            throw new CredenciaisIncorretasException("Email informado incorreto");
        }

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            throw new CredenciaisIncorretasException("Senha informada incorreta");
        }
    }

}