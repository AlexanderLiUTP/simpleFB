package utils.mapper;

import productos.Movimientos;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MovimientoMapper implements BaseMapper<Movimientos>{
    @Override
    public Movimientos toModel(ResultSet resultSet) {
         Movimientos movimientos = null;
        try {
            long id = resultSet.getInt("ID_MOVIMIENTO");
            String tipoMovimiento = resultSet.getString("TIPO_MOVIMIENTO");
            String estado = resultSet.getString("ESTADO");
            LocalDate fecha = resultSet.getDate("FECHA").toLocalDate();
            LocalTime hora = resultSet.getTime("HORA").toLocalTime();
            long cuentaId = resultSet.getLong("ID_CUENTA");
            movimientos = new Movimientos(id, tipoMovimiento, estado, fecha, hora, cuentaId);
            return movimientos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }
}
