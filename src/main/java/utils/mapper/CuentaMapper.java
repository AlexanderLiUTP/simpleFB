package utils.mapper;

import productos.Cuenta;
import utils.TipoCuenta;
import utils.mapper.BaseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
