package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Estudiante;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.EstudianteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(EstudianteController.class) // Indica que se está probando el controlador de Estudiante
public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockBean
    private EstudianteService estudianteService; // Crea un mock del servicio de Estudiante

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        // Configura un objeto Estudiante de ejemplo antes de cada prueba
        estudiante = new Estudiante();
        estudiante.setId(1);
        estudiante.setRun("12345678-9");
        estudiante.setNombres("Juan Pérez");
    }

    @Test
    public void testGetAllEstudiantes() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Estudiante
        when(estudianteService.findAll()).thenReturn(List.of(estudiante));

        // Realiza una petición GET a /api/estudiantes y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/estudiantes"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$[0].run").value("12345678-9")) // Verifica que el primer elemento tenga el run "12345678-9"
                .andExpect(jsonPath("$[0].nombres").value("Juan Pérez")); // Verifica que el primer elemento tenga el nombre "Juan Pérez"
    }

    @Test
    public void testGetEstudianteById() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve el objeto Estudiante
        when(estudianteService.findById(1)).thenReturn(estudiante);

        // Realiza una petición GET a /api/estudiantes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/estudiantes/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testCreateEstudiante() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(estudianteService.save(any(Estudiante.class))).thenReturn(estudiante);

        // Realiza una petición POST a /api/estudiantes con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudiante))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testUpdateEstudiante() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(estudianteService.save(any(Estudiante.class))).thenReturn(estudiante);

        // Realiza una petición PUT a /api/estudiantes/1 con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/api/estudiantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudiante))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testDeleteEstudiante() throws Exception {
        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada
        doNothing().when(estudianteService).deleteById(1);

        // Realiza una petición DELETE a /api/estudiantes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/api/estudiantes/1"))
                .andExpect(status().isOk()); // Verifica que el estado de la respuesta sea 200 OK

        // Verifica que el método deleteById() del servicio se haya llamado exactamente una vez con el id 1
        verify(estudianteService, times(1)).deleteById(1);
    }
}
