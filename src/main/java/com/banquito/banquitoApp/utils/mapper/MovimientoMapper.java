package com.banquito.banquitoApp.utils.mapper;

import com.banquito.banquitoApp.models.productos.Movimientos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class MovimientoMapper implements BaseMapper<Movimientos>{
    @Override
    public Movimientos toModel(ResultSet resultSet) {
         Movimientos movimientos = new Movimientos();
        try {
            movimientos.setId(resultSet.getInt("ID_MOVIMIENTO"));
            movimientos.setTipoMovimiento(resultSet.getString("TIPO_MOVIMIENTO")) ;
            movimientos.setEstado(resultSet.getString("ESTADO"));
            movimientos.setFecha(resultSet.getDate("FECHA").toLocalDate());
            movimientos.setHora(resultSet.getTime("HORA").toLocalTime());
            movimientos.setCuentaId(resultSet.getLong("ID_CUENTA"));
            return movimientos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    public Movimientos fromJSON(Map<String, Object> json){
        Movimientos movimiento = new Movimientos();
        if (json.get("movimientoId")!=null){
           movimiento.setId ((int) json.get("movimientoId"));
        }
        movimiento.setTipoMovimiento((String) json.get("tipoMovimiento"));
        movimiento.setEstado((String) json.get("estado"));
        movimiento.setCuentaId((int) json.get("cuentaId"));
        return  movimiento;
    }
}
