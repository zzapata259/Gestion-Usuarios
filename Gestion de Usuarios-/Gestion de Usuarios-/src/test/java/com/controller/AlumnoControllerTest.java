package com.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.model.Curso;
import com.edutech.cl.edutech.cl.model.Profesor;
import com.edutech.cl.edutech.cl.service.AlumnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AlumnoControllerTest.class) // Indica que se está probando el controlador de Estudiante
public class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockBean
    private AlumnoService alumnoService; // Crea un mock del servicio de Estudiante

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Alumno alumno;

    @BeforeEach
        void setUp() {
            //Creamos un Arraylist Alumnos para utilizarlo en el objeto alumno
            ArrayList<Alumno> alumnos = new ArrayList<>();

            //Creamos un objeto profesor para utilizarlo en el objeto Curso
            Profesor profesor = new Profesor(1, "098765432-1", "Jaime", "Guzman", "12/12/2012");

            //Creamos un objeto curso para utilizarlo en el objeto alumno
            Curso curso = new Curso(1,"8° Básico",profesor,alumnos);

            //Creamos objeto alumno para utilizarlo en los test
            
            alumno = new Alumno();
            alumno.setId(1);
            alumno.setRun("123456789-0");
            alumno.setNombre("Hector");
            alumno.setApellido("Contreras");
            alumno.setFechaNacimiento("12/12/2012");
            alumno.setCurso(curso);
        }

    @Test
    public void testGetAllAlumnos() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Estudiante
        when(alumnoService.findAll()).thenReturn(List.of(alumno));

        // Realiza una petición GET a /api/estudiantes y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/alumno"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$[0].run").value("12345678-9")) // Verifica que el primer elemento tenga el run "12345678-9"
                .andExpect(jsonPath("$[0].nombres").value("Juan Pérez")); // Verifica que el primer elemento tenga el nombre "Juan Pérez"
    }

    @Test
    public void testGetAlumnoById() throws Exception {
        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve el objeto Estudiante
        when(alumnoService.findById(1)).thenReturn(alumno);

        // Realiza una petición GET a /api/estudiantes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/alumnos/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testCreateAlumno() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(alumnoService.save(any(Alumno.class))).thenReturn(alumno);

        // Realiza una petición POST a /api/estudiantes con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testUpdateAlumno() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(alumnoService.save(any(Alumno.class))).thenReturn(alumno);

        // Realiza una petición PUT a /api/estudiantes/1 con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/api/alumnos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.run").value("12345678-9")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.nombres").value("Juan Pérez")); // Verifica que el nombre del objeto devuelto sea "Juan Pérez"
    }

    @Test
    public void testDeleteAlumno() throws Exception {
        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada
        doNothing().when(alumnoService).delete(1);

        // Realiza una petición DELETE a /api/estudiantes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/api/alumnos/1"))
                .andExpect(status().isOk()); // Verifica que el estado de la respuesta sea 200 OK

        // Verifica que el método deleteById() del servicio se haya llamado exactamente una vez con el id 1
        verify(alumnoService, times(1)).delete(1);
    }
}
