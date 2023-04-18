package com.banquito.banquitoApp.controller;

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

    public Cuenta editCuenta(Cuenta cuenta){
        return dao.update(cuenta);
    }

    public void deleteCuenta(long cuentaId){
        dao.delete(cuentaId);
    }
}
