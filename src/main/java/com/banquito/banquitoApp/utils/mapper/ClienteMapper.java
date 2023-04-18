package com.banquito.banquitoApp.utils.mapper;

import com.banquito.banquitoApp.utils.operaciones.CalificacionRiesgo;
import com.banquito.banquitoApp.models.personas.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class ClienteMapper implements BaseMapper<Cliente>{
    @Override
    public Cliente toModel(ResultSet resultSet) {
        Cliente cliente = new Cliente();
        try {
            cliente.setCedula(resultSet.getLong("CEDULA"));
            cliente.setNombre(resultSet.getString("NOMBRE"));
            cliente.setApellido(resultSet.getString("APELLIDO"));
            cliente.setFechaNacimiento(resultSet.getDate("FECHA_NACIMIENTO").toLocalDate());
            cliente.setEstatusTrabajo(resultSet.getBoolean("estatusTrabajo"));
            cliente.setNivelRiesgo(CalificacionRiesgo.valueOf(resultSet.getString("nivelRiesgo")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public Cliente fromJSON(Map<String, Object> json){
        Cliente cliente = new Cliente();
        cliente.setCedula((int) json.get("cedula"));
        cliente.setNombre((String) json.get("nombre"));
        cliente.setApellido((String) json.get("apellido"));
        cliente.setFechaNacimiento(LocalDate.parse((CharSequence) json.get("fechaNacimiento")));
        cliente.setEstatusTrabajo((Boolean) json.get("estatusTrabajo"));
        if (json.get("nivelRiesgo") != null){
            cliente.setNivelRiesgo(CalificacionRiesgo.valueOf((String) json.get("nivelRiesgo")));
        }
        return  cliente;
    }


}
