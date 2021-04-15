package br.com.caelum.carangobom.seguranca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @NotBlank @Size(min = 5, max = 50)
    private String username;

    @NotBlank @Size(min = 6, max = 30)
    private String password;

    public Usuario(String username) {
        this(username, null);
    }

    public Usuario comSenhaDescaracterizada() {
        return new Usuario(username, "***************************");
    }
}
