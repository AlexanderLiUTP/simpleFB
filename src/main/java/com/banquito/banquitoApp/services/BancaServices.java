package com.banquito.banquitoApp.services;

import com.banquito.banquitoApp.controller.BancaController;
import com.banquito.banquitoApp.exceptions.SQLQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banquito.banquitoApp.models.productos.Cuenta;


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

@GetMapping("cuentas/{cedula}")
    public ResponseEntity<?> getClienteCuentas(@PathVariable("cedula") long cedula){
    Map<String,Object> map = new LinkedHashMap<String,Object>();
    List<Cuenta> listaCuentas = sucursal.getCuentas(cedula);
        map.put("timestamp", new Timestamp(System.currentTimeMillis()));
        map.put("status", 200);
        map.put("data", listaCuentas);
        return new ResponseEntity<>(map, HttpStatus.OK);
}

}
