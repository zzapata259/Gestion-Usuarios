package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Reserva;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId(1);
        reserva.setFechaSolicitada(new Date());
        reserva.setHoraSolicitada(new Date());
        reserva.setHoraCierre(new Date(System.currentTimeMillis() + 3600000)); // +1 hora
        reserva.setEstado(1);
    }

    @Test
    public void testGetAllReservas() throws Exception {
        when(reservaService.findAll()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value(1));
    }

    @Test
    public void testGetReservaById() throws Exception {
        when(reservaService.findById(1)).thenReturn(reserva);

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value(1));
    }

    @Test
    public void testCreateReserva() throws Exception {
        when(reservaService.save(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value(1));
    }

    @Test
    public void testUpdateReserva() throws Exception {
        when(reservaService.save(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(put("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value(1));
    }

    @Test
    public void testDeleteReserva() throws Exception {
        doNothing().when(reservaService).deleteById(1);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk());

        verify(reservaService, times(1)).deleteById(1);
    }
}
