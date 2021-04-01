package br.com.caelum.carangobom.componentes.marca;

import br.com.caelum.carangobom.marca.Marca;
import br.com.caelum.carangobom.testcontainer.MySQLCustomContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.MySQLContainer;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MarcaControllerIT {

    public static MySQLContainer<MySQLCustomContainer> mysql = MySQLCustomContainer.getInstance();

    static {
        mysql.withDatabaseName("carangobom");
        mysql.start();
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void deveListarMarcasOrdenadasAlfabeticamente() throws Exception {
        mockMvc.perform(get("/marcas"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[6].id", is(2)))
            .andExpect(jsonPath("$[6].nome", is("Volkswagen")));
    }

    @Test
    void deveRecuperarMarcaPorIdExistente() throws Exception {
        mockMvc.perform(get("/marcas/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Fiat")));
    }

    @Test
    void deveCadastrarMarcaNaoCadastrada() throws Exception {
        String json = mapper.writeValueAsString(new Marca("Ferrari"));

        MockHttpServletRequestBuilder requisicao = post("/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requisicao)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/marcas/8"));

    }

    @Test
    void deveAlterarNomeDeMarcaExistente() throws Exception {
        String novoNome = "Fiat - ALTERADA";
        String json = mapper.writeValueAsString(new Marca(1L, novoNome));

        MockHttpServletRequestBuilder requisicao = put("/marcas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requisicao)
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/marcas/1"))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is(novoNome)));
    }

    @Test
    void deveExcluirMarcaExistente() throws Exception {
        mockMvc.perform(delete("/marcas/1"))
            .andDo(print())
            .andExpect(status().isOk());

        mockMvc.perform(get("/marcas/1"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}