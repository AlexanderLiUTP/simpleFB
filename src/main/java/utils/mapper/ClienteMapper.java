package utils.mapper;

import utils.CalificacionRiesgo;
import personas.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
