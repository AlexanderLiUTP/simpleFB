package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.dao.CuentaDao;

import java.util.List;

public class CuentaController {
    CuentaDao dao = new CuentaDao();
    public List<Cuenta> getAll(){
        List<Cuenta> cuentas = dao.findAll();
        return  cuentas;
    }

    public Cuenta getCuenta(long cuentaId){
        return dao.findById(cuentaId).get();
    }

    void createCuenta(Cuenta cuenta){
        dao.save(cuenta);
    }

    public void editCuenta(Cuenta cuenta) throws SQLQueryException {
        if(!dao.update(cuenta)){
            throw new SQLQueryException();
        }
    }

    public void deleteCuenta(long cuentaId){
        dao.delete(cuentaId);
    }
}
