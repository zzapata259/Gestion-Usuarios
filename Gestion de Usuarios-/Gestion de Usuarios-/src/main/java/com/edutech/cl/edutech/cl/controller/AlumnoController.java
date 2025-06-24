
	package com.edutech.cl.edutech.cl.controller;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edutech.cl.edutech.cl.model.Alumno;
import com.edutech.cl.edutech.cl.service.AlumnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/Alumnos")
public class AlumnoController {
    
    @Autowired
    private AlumnoService alumnoService;

    @Operation(summary = "Obtiene el listado de matriculas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class)))
    @GetMapping
    public ResponseEntity<List<Matricula>> listar(){

        List<Matricula> matriculas = matriculaService.findAll();

        //HATE OAS
        matriculas.forEach(m -> //For Each agrega en la lista matriculas con un bucle for los links
        m.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(m.getId())).withSelfRel()));

        CollectionModel<Matricula> modelo = CollectionModel.of(matriculas);
        modelo.add(linkTo(methodOn(MatriculaController.class).listar()).withSelfRel());
        
        
        return ResponseEntity.ok(matriculas);
        
    }
    

    @Operation(summary = "Crea una nueva Matricula")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Alumnocreada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping
    public AlumnoagregarMatricula(@RequestBody Alumnomatricula){
        
        //HATE OAS
        Alumnonueva = matriculaService.save(matricula);
        nueva.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(nueva.getId())).withSelfRel()); // línea HATEOAS
        return nueva;

        //Retornamos el objeto Alumnocomo variable "nueva", enriquecido con links
    }

    @Operation(summary = "Obtiene una Alumnopor su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Alumnoencontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "404",description = "Alumnono encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public AlumnobuscarMatricula(@PathVariable Integer id)
    {   
        Alumnoalumno= matriculaService.findById(id);
        matricula.add(linkTo(methodOn(MatriculaController.class).buscarMatricula(id)).withSelfRel()); //HATE OAS

        //Agregamos link en la respuesta

        return matricula;
    }

    @Operation(summary = "Crea una nueva Alumnosimulando un pago y agregando al Alumno en su Base de datos")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201",description = "Alumnocreada exitosamente, el pago fue verificado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Matricula.class))),
        @ApiResponse(responseCode = "400",description = "Solicitud inválida, el pago no fue verificado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    @PostMapping("/Simulacion")
    public Map<String, String> procesarPago(@RequestBody Alumnomatricula) {
        Map<String, String> respuesta = new HashMap<>();

        if (matricula.getMonto() > 0 && matricula.getAlumno() != null) {
            alumnoRepository.save(matricula.getAlumno());
            matriculaRepository.save(matricula);

            respuesta.put("estado", "exitoso");
            respuesta.put("mensaje", "Pago procesado, entregando detalles:" + matricula.getAlumno());

            String enlace = linkTo(methodOn(MatriculaController.class).buscarMatricula(matricula.getId())).withSelfRel().getHref();
            respuesta.put("link: ", enlace);

            //Agregamos link en la respuesta
            //HATE OAS

        } else {
            respuesta.put("estado", "rechazado");
            respuesta.put("mensaje", "Monto inválido o alumno no proporcionado");
        }

        return respuesta;
    }
    
    @GetMapping
    public ResponseEntity<List<Alumno>> listar(){

        List<Alumno> alumnos = alumnoService.findAll();
        
        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
        
    }

    @PostMapping
    public Alumno agregarAlumno(@RequestBody Alumno alumno){
        return alumnoService.save(alumno);
    }

    @GetMapping("{id}")
    public Alumno buscaAlumno(@PathVariable Integer id)
    {
        return alumnoService.findById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Integer id) {
        alumnoService.delete(id); 
        return ResponseEntity.noContent().build(); 

    
}    
}
