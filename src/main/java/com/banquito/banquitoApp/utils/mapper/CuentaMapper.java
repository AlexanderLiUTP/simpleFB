package com.banquito.banquitoApp.utils.mapper;

import com.banquito.banquitoApp.models.personas.Cliente;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.operaciones.CalificacionRiesgo;
import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class CuentaMapper implements BaseMapper<Cuenta> {
    @Override
    public Cuenta toModel(ResultSet resultSet) {
        Cuenta cuenta = new Cuenta();
        try {
            cuenta.setId(resultSet.getLong("ID_CUENTA"));
            cuenta.setTipoCuenta(TipoCuenta.valueOf(resultSet.getString("TIPO_CUENTA")));
            cuenta.setBalance(resultSet.getDouble("BALANCE"));
            cuenta.setMovInterest(resultSet.getInt("MOV_INTEREST"));
            cuenta.setMovMan(resultSet.getInt("MOV_MANTENIMIENTO"));
            cuenta.setCedulaTitular(resultSet.getLong("CEDULA_TITULAR"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuenta;
    }
    public Cuenta fromJSON(Map<String, Object> json){
        Cuenta cuenta = new Cuenta();
        if (json.get("cuentaId")!=null){
            cuenta.setId((int) json.get("cuentaId"));
        }
        cuenta.setTipoCuenta(TipoCuenta.valueOf((String) json.get("tipoCuenta")));
        cuenta.setBalance((double) json.get("balance"));
        cuenta.setCedulaTitular((int) json.get("cedulaTitular"));
        return  cuenta;
    }
}
