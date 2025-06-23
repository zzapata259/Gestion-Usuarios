package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.service.ProfesorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProfesorControllerTest.class) // Indica que se está probando el controlador de Profesor
public class ProfesorControllerTest {

    @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockBean
    private ProfesorService profesorService; // Crea un mock del servicio de Profesor

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Profesor profesor;

    @BeforeEach
    void setUp() {
        // Configura un objeto Profesor de ejemplo antes de cada prueba
        profesor = new Profesor();
        profesor.setId(1);
        profesor.setRun("12345678-9");
        profesor.setNombre("Juan Pérez");
        profesor.setApellido("Juan Pérez");
        profesor.setFechaNacimiento("13/01/1999");
    }

    @Test
    public void testGetAllProfesors() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Profesor
        when(profesorService.findAll()).thenReturn(List.of(profesor));

        // Realiza una petición GET a /api/profesors y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/profesors"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$[0].run").value("12345678-9")) // Verifica que el primer elemento tenga el run "12345678-9"
                .andExpect(jsonPath("$[0].nombres").value("Juan Pérez")); // Verifica que el primer elemento tenga el nombre "Juan Pérez"
    }

    @Test
    public void testGetProfesorById() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve el objeto Profesor
        when(profesorService.findById(1)).thenReturn(profesor);

        // Realiza una petición GET a /api/profesors/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/profesors/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testCreateProfesor() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Profesor
        when(profesorService.save(any(Profesor.class))).thenReturn(profesor);

        // Realiza una petición POST a /api/profesors con el objeto Profesor en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/profesors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profesor))) // Convierte el objeto Profesor a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testUpdateProfesor() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Profesor
        when(profesorService.save(any(Profesor.class))).thenReturn(profesor);

        // Realiza una petición PUT a /api/profesors/1 con el objeto Profesor en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/api/profesors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profesor))) // Convierte el objeto Profesor a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testDeleteProfesor() throws Exception {
        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada
        doNothing().when(profesorService).delete(1);

        // Realiza una petición DELETE a /api/profesors/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/api/profesors/1"))
                .andExpect(status().isOk()); // Verifica que el estado de la respuesta sea 200 OK

        // Verifica que el método deleteById() del servicio se haya llamado exactamente una vez con el id 1
        verify(profesorService, times(1)).delete(1);
    }
}
