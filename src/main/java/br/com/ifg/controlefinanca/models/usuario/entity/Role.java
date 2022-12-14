package br.com.ifg.controlefinanca.models.usuario.entity;

import br.com.ifg.controlefinanca.models.usuario.enuns.UsuarioRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UsuarioRole role;;
}