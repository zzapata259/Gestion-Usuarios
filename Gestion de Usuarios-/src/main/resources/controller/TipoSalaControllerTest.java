package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.TipoSala;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.TipoSalaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(TipoSalaController.class)
public class TipoSalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoSalaService tipoSalaService;

    @Autowired
    private ObjectMapper objectMapper;

    private TipoSala tipoSala;

    @BeforeEach
    void setUp() {
        tipoSala = new TipoSala();
        tipoSala.setIdTipo(1);
        tipoSala.setNombre("Laboratorio");
    }

    @Test
    public void testGetAllTipoSalas() throws Exception {
        when(tipoSalaService.findAll()).thenReturn(List.of(tipoSala));

        mockMvc.perform(get("/api/tipoSalas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTipo").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laboratorio"));
    }

    @Test
    public void testGetTipoSalaById() throws Exception {
        when(tipoSalaService.findById(1)).thenReturn(tipoSala);

        mockMvc.perform(get("/api/tipoSalas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTipo").value(1))
                .andExpect(jsonPath("$.nombre").value("Laboratorio"));
    }

    @Test
    public void testCreateTipoSala() throws Exception {
        when(tipoSalaService.save(any(TipoSala.class))).thenReturn(tipoSala);

        mockMvc.perform(post("/api/tipoSalas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoSala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTipo").value(1))
                .andExpect(jsonPath("$.nombre").value("Laboratorio"));
    }

    @Test
    public void testUpdateTipoSala() throws Exception {
        when(tipoSalaService.save(any(TipoSala.class))).thenReturn(tipoSala);

        mockMvc.perform(put("/api/tipoSalas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoSala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTipo").value(1))
                .andExpect(jsonPath("$.nombre").value("Laboratorio"));
    }

    @Test
    public void testDeleteTipoSala() throws Exception {
        doNothing().when(tipoSalaService).deleteById(1);

        mockMvc.perform(delete("/api/tipoSalas/1"))
                .andExpect(status().isOk());

        verify(tipoSalaService, times(1)).deleteById(1);
    }
}
