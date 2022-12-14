package br.com.ifg.controlefinanca.models.usuario.dto;

import br.com.ifg.controlefinanca.models.usuario.entity.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    @Size(max = 80)
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    @NotNull
    private LocalDate nascimento;
    @NotBlank
    private String estado;
    private Boolean ativo;
    private List<Role> roles;
}
