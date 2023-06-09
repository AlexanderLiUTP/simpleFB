package com.banquito.banquitoApp.utils.dao;

import com.banquito.banquitoApp.utils.db.DBConnection;
import com.banquito.banquitoApp.utils.mapper.CuentaMapper;
import com.banquito.banquitoApp.models.productos.Cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CuentaDao implements CrudDao<Cuenta, Long> {

    private Connection connection;
    private CuentaMapper mapper;

    public CuentaDao(){
        this.connection = DBConnection.getConnection();
        this.mapper = new CuentaMapper();
    }

    @Override
    public boolean save(Cuenta model) {
        boolean completed = true;
        String insertUserQuery = "INSERT INTO CUENTAS (TIPO_CUENTA, BALANCE, MOV_INTEREST, MOV_MANTENIMIENTO, CEDULA_TITULAR) VALUES (?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)){
            preparedStatement.setString(1,model.getTipoCuenta().name());
            preparedStatement.setDouble(2,model.getBalance());
            preparedStatement.setInt(3,model.getMovInterest());
            preparedStatement.setInt(4,model.getMovMan());
            preparedStatement.setLong(5,model.getCedulaTitular());
            preparedStatement.executeUpdate();
            return completed;
        }catch (SQLException e ){
            e.printStackTrace();
            return !completed;
        }
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        String selectByIdQuery = "SELECT * FROM CUENTAS WHERE ID_CUENTA = ?";
        Cuenta cuenta = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery)){
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cuenta = mapper.toModel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.nonNull(cuenta) ? Optional.of(cuenta):Optional.empty();
    }

    @Override
    public List<Cuenta> findAll() {
        String selectAllQuery = "SELECT * FROM CUENTAS";
        List<Cuenta> cuentas = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cuenta cuenta = mapper.toModel(resultSet);
                cuentas.add(cuenta);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return cuentas;
    }

    @Override
    public boolean update(Cuenta model) {
        boolean completed = true;
        String updateCuentaQuery = "UPDATE CUENTAS SET TIPO_CUENTA = ? , BALANCE = ? , MOV_INTEREST = ? , MOV_MANTENIMIENTO = ? WHERE ID_CUENTA = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateCuentaQuery)){
            preparedStatement.setString(1,model.getTipoCuenta().name());
            preparedStatement.setDouble(2,model.getBalance());
            preparedStatement.setInt(3,model.getMovInterest());
            preparedStatement.setInt(4,model.getMovMan());
            preparedStatement.setLong(5,model.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
            return !completed;
        }
        //NOTE: This is actually not a good practice since the data could be manipulated by a trigger.
        return completed;
    }

    @Override
    public boolean delete(Long id) {
        boolean completed = true;
        String deleteQuery = "DELETE FROM CUENTAS WHERE ID_CUENTA = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            return completed;
        } catch (SQLException e) {
            e.printStackTrace();
            return !completed;
        }
    }

    @Override
    public void deleteAll() {
        String deleteQuery = "DELETE FROM CUENTAS";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cuenta> findAllFromClient(long cedula){
        List<Cuenta> cuentas = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM CUENTAS where cuentas.CEDULA_TITULAR = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)){
            preparedStatement.setLong(1,cedula);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cuenta cuenta = mapper.toModel(resultSet);
                cuentas.add(cuenta);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return cuentas;
    }

}
