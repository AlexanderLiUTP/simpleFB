package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.personas.Cliente;
import com.banquito.banquitoApp.utils.dao.ClienteDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
@Configuration
public class ClienteController {
    ClienteDao dao = new ClienteDao();

    @Bean
    @Scope("Singleton")
    public ClienteController ClienteController(){
        return new ClienteController();
    }
    public List<Cliente> getAll(){
        List<Cliente> clientes = dao.findAll();
        return  clientes;
    }

    public Cliente getCliente(long cedula){
       return dao.findById(cedula).get();
    }

    public void createCliente(Cliente cliente) throws SQLQueryException {
        if(!dao.save(cliente)){
            throw new SQLQueryException();
        }
    }

    public Cliente editCliente(Cliente cliente){
        return dao.update(cliente);
    }

    public void deleteCliente(long cedula){
        dao.delete(cedula);
    }
}
