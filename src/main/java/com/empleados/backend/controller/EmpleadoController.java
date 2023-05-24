package com.empleados.backend.controller;

import com.empleados.backend.exceptions.ResourceNotFoundException;
import com.empleados.backend.model.Empleado;
import com.empleados.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
//Esto nos permite intercambiar recursos entre back y front
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository repository;

    @GetMapping("/empleados")
    public List<Empleado> listarTodosLosEmpleados() {
        return repository.findAll();
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con el id " + id));
        return ResponseEntity.ok(empleado);
    }

    @PostMapping("/empleados")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return repository.save(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado DetallesEmpleado) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el id " + id));
        empleado.setNombre(DetallesEmpleado.getNombre());
        empleado.setApellido(DetallesEmpleado.getApellido());
        empleado.setEmail(DetallesEmpleado.getEmail());
 
        Empleado empleadoActualizado = repository.save(empleado);

        return ResponseEntity.ok(empleadoActualizado);
    }
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Long id){
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

        repository.delete(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Eliminar",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
