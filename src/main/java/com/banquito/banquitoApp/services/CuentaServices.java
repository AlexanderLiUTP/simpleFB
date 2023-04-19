package com.banquito.banquitoApp.services;

import com.banquito.banquitoApp.controller.CuentaController;
import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.mapper.CuentaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(path = "/api/v1/cuentas")
public class CuentaServices {
private CuentaController cuentaController;
private CuentaMapper mapper;

@Autowired
public CuentaServices(CuentaController cuentaController){
    this.cuentaController = cuentaController;
    this.mapper = new CuentaMapper();
    }

@GetMapping("")
    public ResponseEntity<?> getCuentasService(){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    List<Cuenta> listaCuentas = cuentaController.getAll();
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 200);
        map.put("data", listaCuentas);
        return new ResponseEntity<>(map, HttpStatus.OK);
}

@GetMapping("/{cuentaId}")
public ResponseEntity<?> getCuentaService(@PathVariable("cuentaId") long cuentaId){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    Cuenta cuenta = cuentaController.getCuenta(cuentaId);
    map.put("timestamp", new Timestamp(System.currentTimeMillis()));
    map.put("status", 200);
    map.put("data", cuenta);
    return new ResponseEntity<>(map, HttpStatus.OK);
}

@PostMapping("")
@ResponseBody
public ResponseEntity<?> createCuentaService(@RequestBody Map<String, Object> cuentaJSON){
    Map<String,Object> map = new LinkedHashMap<String, Object>();
    Cuenta cuenta = mapper.fromJSON(cuentaJSON);
    try{
       cuentaController.createCuenta(cuenta);
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

    @PutMapping(value="/{cuentaId}")
    public ResponseEntity<?> editCuentaService(@PathVariable("cuentaId") Integer cuentaId, @RequestBody(required = true) Map<String, Object> cuentaJSON){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        // TODO: Revisar cuales son los atributos editables y añadir validación de que se 
        // guarde en el edit.
        Cuenta cuenta = cuentaController.getCuenta(cuentaId);
        Cuenta cuentaEdit = mapper.fromJSON(cuentaJSON);
        cuentaEdit.setId(cuentaId);
        try{
            cuentaController.editCuenta(cuentaEdit);
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

    @DeleteMapping(value="/{cuentaId}")
    public ResponseEntity<?> deleteCuentaService(@PathVariable("cuentaId") Integer cuentaId){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        try{
            cuentaController.deleteCuenta(cuentaId);
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

