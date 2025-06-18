package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Sala;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.SalaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(SalaController.class)
public class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala();
        sala.setCodigo(1);
        sala.setNombre("Sala 101");
        sala.setCapacidad(50);
        sala.setIdInstituo(1);
    }

    @Test
    public void testGetAllSalas() throws Exception {
        when(salaService.findAll()).thenReturn(List.of(sala));

        mockMvc.perform(get("/api/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sala 101"));
    }

    @Test
    public void testGetSalaById() throws Exception {
        when(salaService.findById(1)).thenReturn(sala);

        mockMvc.perform(get("/api/salas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala 101"));
    }

    @Test
    public void testCreateSala() throws Exception {
        when(salaService.save(any(Sala.class))).thenReturn(sala);

        mockMvc.perform(post("/api/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala 101"));
    }

    @Test
    public void testUpdateSala() throws Exception {
        when(salaService.save(any(Sala.class))).thenReturn(sala);

        mockMvc.perform(put("/api/salas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Sala 101"));
    }

    @Test
    public void testDeleteSala() throws Exception {
        doNothing().when(salaService).deleteById(1);

        mockMvc.perform(delete("/api/salas/1"))
                .andExpect(status().isOk());

        verify(salaService, times(1)).deleteById(1);
    }
}
