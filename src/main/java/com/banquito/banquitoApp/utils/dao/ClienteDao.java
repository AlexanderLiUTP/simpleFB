package com.banquito.banquitoApp.utils.dao;

import com.banquito.banquitoApp.utils.db.DBConnection;
import com.banquito.banquitoApp.utils.mapper.ClienteMapper;
import com.banquito.banquitoApp.models.personas.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClienteDao implements CrudDao<Cliente, Long>{

    private Connection connection;
    private ClienteMapper mapper;

    public ClienteDao(){
        this.connection = DBConnection.getConnection();
        this.mapper = new ClienteMapper();
    }

    @Override
    public boolean save(Cliente model) {
        String insertUserQuery = "INSERT INTO CLIENTES (CEDULA, NOMBRE, APELLIDO, FECHA_NACIMIENTO, estatusTrabajo, nivelRiesgo) VALUES (?,?,?,?,?,?)";
        boolean completed = true;
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)){
            preparedStatement.setLong(1,model.getCedula());
            preparedStatement.setString(2,model.getNombre());
            preparedStatement.setString(3,model.getApellido());
            preparedStatement.setDate(4, Date.valueOf(model.getFechaNacimiento()));
            preparedStatement.setBoolean(5,model.getEstatusTrabajo());
            preparedStatement.setString(6,model.getNivelRiesgo().name());
            preparedStatement.executeUpdate();
            return completed;
        }catch (SQLException e ){
            e.printStackTrace();
            return  !completed;
        }
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        String selectByIdQuery = "SELECT * FROM CLIENTES WHERE CEDULA = ?";
        Cliente cliente = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery)){
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cliente = mapper.toModel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.nonNull(cliente) ? Optional.of(cliente):Optional.empty();
    }

    @Override
    public List<Cliente> findAll() {
        String selectAllQuery = "SELECT * FROM CLIENTES";
        List<Cliente> clientes = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cliente cliente = mapper.toModel(resultSet);
                clientes.add(cliente);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public boolean update(Cliente model) {
        boolean completed = true;
        String updateClienteQuery = "UPDATE CLIENTES SET NOMBRE = ? , APELLIDO = ? , FECHA_NACIMIENTO = ?, estatusTrabajo = ?, nivelRiesgo = ? WHERE CEDULA = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateClienteQuery)){
            preparedStatement.setString(1,model.getNombre());
            preparedStatement.setString(2,model.getApellido());
            preparedStatement.setDate(3,Date.valueOf(model.getFechaNacimiento()));
            preparedStatement.setBoolean(4,model.getEstatusTrabajo());
            preparedStatement.setString(5,model.getNivelRiesgo().name());
            preparedStatement.setLong(6,model.getCedula());


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
        String deleteQuery = "DELETE FROM CLIENTES WHERE CEDULA = ?";
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
        String deleteQuery = "DELETE FROM CLIENTES";
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
