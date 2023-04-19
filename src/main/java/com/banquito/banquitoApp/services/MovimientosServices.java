package com.banquito.banquitoApp.services;
import com.banquito.banquitoApp.controller.MovimientosController;
import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Movimientos;
import com.banquito.banquitoApp.utils.mapper.MovimientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(path = "/api/v1/movimientos")
public class MovimientosServices {
private MovimientosController movimientoController;
private MovimientoMapper mapper;

@Autowired
public MovimientosServices(MovimientosController movimientoController){
    this.movimientoController = movimientoController;
    this.mapper = new MovimientoMapper();}

@GetMapping("")
    public ResponseEntity<?> getMovimientossService(){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    List<Movimientos> listaMovimientos = movimientoController.getAll();
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 200);
        map.put("data", listaMovimientos);
        return new ResponseEntity<>(map, HttpStatus.OK);
}

@GetMapping("/{movId}")
public ResponseEntity<?> getMovimientosService(@PathVariable("movId") long movId){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    Movimientos movimiento = movimientoController.getMovimientos(movId);
    map.put("timestamp", new Timestamp(System.currentTimeMillis()));
    map.put("status", 200);
    map.put("data", movimiento);
    return new ResponseEntity<>(map, HttpStatus.OK);
}

@PostMapping("")
@ResponseBody
public ResponseEntity<?> createMovimientosService(@RequestBody Map<String, Object> movimientoJSON){
    Map<String,Object> map = new LinkedHashMap<String, Object>();
    Movimientos movimiento = mapper.fromJSON(movimientoJSON);
    try{
       movimientoController.createMovimientos(movimiento);
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 201);
        map.put("message", "Datos guardados exitosamente");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }catch(SQLQueryException ex){
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 400);
        map.put("error", "BAD REQUEST");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}

    @PutMapping(value="/{movId}")
    public ResponseEntity<?> editMovimientosService(@PathVariable("movId") Integer movId, @RequestBody(required = true) Map<String, Object> movimientoJSON){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Movimientos movimiento = movimientoController.getMovimientos(movId);
        Movimientos movimientoEdit = mapper.fromJSON(movimientoJSON);
        movimientoEdit.setId(movId);
        try{
            movimientoController.editMovimientos(movimientoEdit);
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 200);
            map.put("message", "Solicitud completada");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(SQLQueryException ex){
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 400);
            map.put("error", "BAD REQUEST");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value="/{movId}")
    public ResponseEntity<?> deleteMovimientosService(@PathVariable("movId") Integer movId){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        try{
            movimientoController.deleteMovimientos(movId);
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 200);
            map.put("message", "Solicitud completada");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(SQLQueryException ex){
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 400);
            map.put("error", "BAD REQUEST");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    }

}
