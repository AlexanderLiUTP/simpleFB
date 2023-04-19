package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.dao.CuentaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class CuentaController {
    CuentaDao dao = new CuentaDao();

    @Bean
    @Scope("Singleton")
    public CuentaController CuentaController(){
        return new CuentaController();
    }
    public List<Cuenta> getAll(){
        List<Cuenta> cuentas = dao.findAll();
        return  cuentas;
    }

    public Cuenta getCuenta(long cuentaId){
        return dao.findById(cuentaId).get();
    }

    public void createCuenta(Cuenta cuenta) throws SQLQueryException{
        if(!dao.save(cuenta)){
            throw new SQLQueryException();
        }
    }

    public void editCuenta(Cuenta cuenta) throws SQLQueryException {
        if(!dao.update(cuenta)){
            throw new SQLQueryException();
        }
    }

    public void deleteCuenta(long cuentaId) throws SQLQueryException{
if (!dao.delete(cuentaId)){
           throw new SQLQueryException();

       }    }
}
