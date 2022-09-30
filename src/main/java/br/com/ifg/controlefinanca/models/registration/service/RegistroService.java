package br.com.ifg.controlefinanca.models.registration.service;

import br.com.ifg.controlefinanca.models.registration.dto.RegistroRequestDTO;
import br.com.ifg.controlefinanca.models.util.EmailValidator;
import br.com.ifg.controlefinanca.models.registration.token.entity.ConfirmaToken;
import br.com.ifg.controlefinanca.models.registration.token.service.ConfirmaTokenService;
import br.com.ifg.controlefinanca.models.usuario.Usuario;
import br.com.ifg.controlefinanca.models.usuario.enuns.UsuarioRole;
import br.com.ifg.controlefinanca.models.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RegistroService {
    //TODO: Criar Singleton para E-mail Validator
    private EmailValidator emailValidator;
    private UsuarioService usuarioService;
    private final ConfirmaTokenService confirmaTokenService;


    public String register(RegistroRequestDTO request) {
        Boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("O E-mail não é válido!");
        }
        return usuarioService.signUpUser(
                new Usuario(
                        request.getNome(),
                        request.getEmail(),
                        request.getSenha(),
                        request.getNascimento(),
                        request.getEstado(),
                        UsuarioRole.USER)
        );
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmaToken confirmaToken = confirmaTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token não encontrado"));

        if (Objects.nonNull(confirmaToken.getConfirmadoEm())) {
            throw new IllegalStateException("O E-mail já foi confimado");
        }

        LocalDateTime expiredAt = confirmaToken.getExpiraEm();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado");
        }

        confirmaTokenService.setConfirmedAt(token);
        usuarioService.enableAppUser(
                confirmaToken.getUsuario().getEmail());
        return "E-mail Confirmado";
    }
}
