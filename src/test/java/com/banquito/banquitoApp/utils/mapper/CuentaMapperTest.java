package com.banquito.banquitoApp.utils.mapper;

import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CuentaMapperTest {

    @Test
    void fromJSON() {
        CuentaMapper cuentaMapper = new CuentaMapper();
        Cuenta cuenta = new Cuenta(2,  5120.25, 27452200, TipoCuenta.Ahorro);
        Map<String, Object> json = new HashMap<>();
        json.put("cuantaId", 2);
        json.put("tipoCuenta", "Ahorro");
        json.put("balance", 5120.25);
        json.put("cedulaTitular", 27452200);
        assertEquals(cuenta.toString(), cuentaMapper.fromJSON(json).toString());
    }
}