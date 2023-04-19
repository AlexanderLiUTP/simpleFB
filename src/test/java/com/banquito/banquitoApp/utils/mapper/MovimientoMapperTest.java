package com.banquito.banquitoApp.utils.mapper;

import com.banquito.banquitoApp.models.productos.Movimientos;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoMapperTest {

    @Test
    void fromJSON() {
        MovimientoMapper cuentaMapper = new MovimientoMapper();
        Movimientos movimiento = new Movimientos(4,  "Deposito", "Aprovado", 25);
        Map<String, Object> json = new HashMap<>();
        json.put("movimientoId", 4);
        json.put("tipoMovimiento", "Deposito");
        json.put("estado", "Aprovado");
        json.put("cuentaId", 25);
        assertEquals(movimiento.toString(), cuentaMapper.fromJSON(json).toString());
    }
}