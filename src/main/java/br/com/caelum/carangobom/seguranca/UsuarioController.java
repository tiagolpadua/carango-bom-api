package br.com.caelum.carangobom.seguranca;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/usuarios")
@Transactional
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> lista() {
        return usuarioRepository.findAllByOrderByUsername()
                .stream()
                .map(Usuario::comSenhaDescaracterizada)
                .collect(toList());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> peloUsername (@PathVariable String username) {
        return usuarioRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody Usuario usuario, UriComponentsBuilder uriBuilder) {
        Optional<Usuario> usuarioJahCadastrado = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioJahCadastrado.isPresent()) {
            return ResponseEntity.badRequest().body("\"Usuário " + usuario.getUsername() + " já existe.\"");
        } else {
            Usuario novoUsuario = usuarioRepository.save(usuario);

            URI uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(novoUsuario.getUsername()).toUri();
            return ResponseEntity.created(uri).body(novoUsuario.comSenhaDescaracterizada());
        }
    }

    @PutMapping("/{username}/senha")
    public ResponseEntity<?> alteraSenha(@Valid @RequestBody Usuario usuario) {
        Optional<Usuario> possivelUsuario = usuarioRepository.findByUsername(usuario.getUsername());

        return possivelUsuario.map(usuarioCadastrado -> {
                usuarioCadastrado.setPassword(usuario.getPassword());
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> removeUsuario(@PathVariable String username) {
        Optional<Usuario> possivelUsuario = usuarioRepository.findByUsername(username);
        possivelUsuario.ifPresent(usuarioRepository::delete);

        return possivelUsuario.map(u -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
