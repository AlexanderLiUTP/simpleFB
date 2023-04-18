package utils.dao;

import productos.Movimientos;
import utils.db.DBConnection;
import utils.mapper.MovimientoMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovimientoDao implements CrudDao<Movimientos, Long>{

    private Connection connection;
    private MovimientoMapper mapper;

    public MovimientoDao(){
        this.connection = DBConnection.getConnection();
        this.mapper = new MovimientoMapper();
    }

    @Override

    public void save(Movimientos model) {
        String insertUserQuery = "INSERT INTO MOVIMIENTOS (TIPO_MOVIMIENTO, ESTADO, FECHA, HORA, ID_CUENTA) VALUES (?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)){
            preparedStatement.setString(1,model.tipoMovimiento());
            preparedStatement.setString(2,model.estado());
            preparedStatement.setDate(3, Date.valueOf(model.fecha()));
            preparedStatement.setTime(4,Time.valueOf(model.hora()));
            preparedStatement.setLong(5,model.cuentaId());
            preparedStatement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movimientos> findById(Long id) {
        String selectByIdQuery = "SELECT * FROM MOVIMIENTOS WHERE ID_MOVIMIENTO = ?";
        Movimientos movimiento = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery)){
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                movimiento = mapper.toModel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.nonNull(movimiento) ? Optional.of(movimiento):Optional.empty();
    }

    @Override
    public List<Movimientos> findAll() {
        String selectAllQuery = "SELECT * FROM MOVIMIENTOS";
        List<Movimientos> movimientos = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Movimientos movimiento = mapper.toModel(resultSet);
                movimientos.add(movimiento);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return movimientos;
    }

    @Override
    public Movimientos
    update(Movimientos model) {
        String updateMovimientosQuery = "UPDATE MOVIMIENTOS SET TIPO_MOVIMIENTO = ? , ESTADO = ? , FECHA = ?, HORA = ? WHERE ID_MOVIMIENTO = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateMovimientosQuery)){
            preparedStatement.setString(1,model.tipoMovimiento());
            preparedStatement.setString(2,model.estado());
            preparedStatement.setDate(3,Date.valueOf(model.fecha()));
            preparedStatement.setTime(4,Time.valueOf(model.hora()));
            preparedStatement.setLong(5,model.id());
            preparedStatement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
        }
        //NOTE: This is actually not a good practice since the data could be manipulated by a trigger.
        return model;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM MOVIMIENTOS WHERE ID_MOVIMIENTO = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String deleteQuery = "DELETE FROM MOVIMIENTOS";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
