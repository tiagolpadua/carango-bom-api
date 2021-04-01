package br.com.caelum.carangobom.marca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Marca {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nome;

    public Marca(String nome) {
        this(null, nome);
    }

}
