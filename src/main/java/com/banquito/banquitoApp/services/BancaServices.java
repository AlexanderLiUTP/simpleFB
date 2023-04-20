package com.banquito.banquitoApp.services;

import com.banquito.banquitoApp.controller.BancaController;
import com.banquito.banquitoApp.controller.CuentaController;
import com.banquito.banquitoApp.controller.ClienteController;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.models.personas.Cliente;

import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.util.ArrayList;
import java.util.List;


import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/banca")
public class BancaServices {
BancaController sucursal = new BancaController();
CuentaController cuentaController  = new CuentaController();
ClienteController clienteController  = new ClienteController();

@GetMapping("cuentas/{cedula}")
    public ResponseEntity<?> getClienteCuentas(@PathVariable("cedula") long cedula){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    List<Cuenta> listaCuentas = sucursal.getCuentas(cedula);
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 200);
        map.put("data", listaCuentas);
        return new ResponseEntity<>(map, HttpStatus.OK);
}

@GetMapping("/balance/{cuentaId}")
    public ResponseEntity<?> getBalance(@PathVariable("cuentaId") long cedula){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    Cuenta cuenta = cuentaController.getCuenta(cedula);
    double balance = sucursal.getBalance(cuenta);
    map.put("timestamp", new Timestamp(System.currentTimeMillis()));
    map.put("status", 200);
    map.put("data", balance);
    return new ResponseEntity<>(map, HttpStatus.OK);
}

@PostMapping("/retirar")
@ResponseBody
    public ResponseEntity<?> retirar(@RequestBody Map<String, Object> requestBody){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        long cuentaId = (int) requestBody.get("cuentaId");
        double cantidad = (double) requestBody.get("cantidad");
        Cuenta cuenta = cuentaController.getCuenta(cuentaId);
        if (sucursal.retirar(cuenta, cantidad)){
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 200);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 400);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

@PostMapping("/depositar")
@ResponseBody
    public ResponseEntity<?> depositar(@RequestBody Map<String, Object> requestBody){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        long cuentaId = (int) requestBody.get("cuentaId");
        double cantidad = (double) requestBody.get("cantidad");
        Cuenta cuenta = cuentaController.getCuenta(cuentaId);
        if (sucursal.depositar(cuenta, cantidad)){
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 200);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 400);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

@PostMapping("/transac")
@ResponseBody
    public ResponseEntity<?> transferenciaEntrecuentas(@RequestBody Map<String, Object> requestBody){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        long idEmisor = (int) requestBody.get("idEmisor");
        long idReceptor = (int) requestBody.get("idReceptor");
        double cantidad = (double) requestBody.get("cantidad");
        Cuenta cuentaEmiror = cuentaController.getCuenta(idEmisor);
        Cuenta cuentaReceptor = cuentaController.getCuenta(idReceptor);
        if (sucursal.transferenciaEntrecuentas(cuentaEmiror, cuentaReceptor, cantidad)){
            map.put("timestamp", new Timestamp(System.currentTimeMillis()));
            map.put("status", 200);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 400);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

@PostMapping("/abrirCuenta")
@ResponseBody
    public ResponseEntity<?> abrirCuenta(@RequestBody Map<String, Object> requestBody){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        long cedula = (int) requestBody.get("cedula");
        TipoCuenta tipoCuenta = TipoCuenta.valueOf( (String) requestBody.get("tipoCuenta"));
        double montoInicial =0;
        if(requestBody.get("montoInicial") != null){
            montoInicial = (double) requestBody.get("montoInicial");
        }
        Cliente titular = clienteController.getCliente(cedula);
        if(montoInicial > 0){ 
            if ((sucursal.abrirCuenta(titular, tipoCuenta, montoInicial) )){
                map.put("timestamp", new Timestamp(System.currentTimeMillis()));
                map.put("status", 200);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 400);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
