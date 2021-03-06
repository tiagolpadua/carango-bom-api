package br.com.caelum.carangobom.veiculo;

import br.com.caelum.carangobom.marca.Marca;
import br.com.caelum.carangobom.marca.MarcaRepository;
import br.com.caelum.carangobom.seguranca.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
@Transactional
@AllArgsConstructor
public class VeiculoController {

    private MarcaRepository marcaRepository;
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public List<Veiculo> listaVeiculos() {
        return veiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> veiculoPorId(@PathVariable Long id) {
        return veiculoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Veiculo> cadastraVeiculo(@Valid @RequestBody Veiculo novoVeiculo, UriComponentsBuilder uriBuilder) {
        Veiculo veiculoSalvo = veiculoRepository.save(novoVeiculo);

        URI location = uriBuilder.path("/veiculos/{id}")
                .buildAndExpand(veiculoSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(veiculoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> alteraVeiculo(@PathVariable Long id, @Valid @RequestBody Veiculo veiculoAlterado) {
        Optional<Veiculo> possivelVeiculo = veiculoRepository.findById(id);
        possivelVeiculo.ifPresent(v -> veiculoRepository.save(veiculoAlterado));

        return possivelVeiculo.map(v -> ResponseEntity.ok(veiculoAlterado))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaVeiculo(@PathVariable Long id) {
        Optional<Veiculo> possivelVeiculo = veiculoRepository.findById(id);
        possivelVeiculo.ifPresent(veiculoRepository::delete);

        return possivelVeiculo.map(v -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @InitBinder("veiculo")
    public void configuraValidadores(WebDataBinder binder) {
        binder.addValidators(new ValidacaoDeMarcaExistente());
    }

    private class ValidacaoDeMarcaExistente implements Validator {

        @Override
        public boolean supports(Class<?> aClass) {
            return Veiculo.class.isAssignableFrom(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            Veiculo v = (Veiculo) o;

            Optional<Marca> possivelMarca = marcaRepository.findById(v.getMarcaId());
            if (possivelMarca.isEmpty()) {
                errors.rejectValue("marcaId", "veiculo.marca.id.inexistente", "Marca inexistente: " + v.getMarcaId());
            }
        }
    }

}
