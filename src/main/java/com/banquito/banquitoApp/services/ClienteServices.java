package com.banquito.banquitoApp.services;

import com.banquito.banquitoApp.controller.ClienteController;
import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.personas.Cliente;
import com.banquito.banquitoApp.utils.mapper.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/clientes")
public class ClienteServices {
private ClienteController clienteController;
private ClienteMapper mapper;

@Autowired
public ClienteServices(ClienteController clienteController){
    this.clienteController = clienteController;
    this.mapper = new ClienteMapper();}

@GetMapping("")
    public ResponseEntity<?> getClientesService(){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    List<Cliente> listaClientes = clienteController.getAll();
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 200);
        map.put("data", listaClientes);
        return new ResponseEntity<>(map, HttpStatus.OK);
}

@GetMapping("/{cedula}")
public ResponseEntity<?> getClienteService(@PathVariable("cedula") long cedula){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    Cliente cliente = clienteController.getCliente(cedula);
    map.put("timestamp", new Timestamp(System.currentTimeMillis()));
    map.put("status", 200);
    map.put("data", cliente);
    return new ResponseEntity<>(map, HttpStatus.OK);
}

@PostMapping("")
@ResponseBody
public ResponseEntity<?> createClienteService(@RequestBody Map<String, Object> clienteJSON){
    Map<String,Object> map = new LinkedHashMap<String, Object>();
    Cliente cliente = mapper.fromJSON(clienteJSON);
    try{
       clienteController.createCliente(cliente);
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

    @PutMapping(value="/{cedula}")
    public ResponseEntity<?> editClienteService(@PathVariable("cedula") Integer cedula, @RequestBody(required = true) Map<String, Object> clienteJSON){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Cliente cliente = clienteController.getCliente(cedula);
        Cliente clienteEdit = mapper.fromJSON(clienteJSON);
        clienteEdit.setCedula(cedula);
        try{
            clienteController.editCliente(clienteEdit);
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

    @DeleteMapping(value="/{cedula}")
    public ResponseEntity<?> deleteClienteService(@PathVariable("cedula") Integer cedula){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        try{
            clienteController.deleteCliente(cedula);
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
