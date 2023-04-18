package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.models.productos.Movimientos;
import com.banquito.banquitoApp.utils.dao.MovimientoDao;

import java.util.List;

public class MovimientosController {
    MovimientoDao dao = new MovimientoDao();
    public List<Movimientos> getAll(){
        List<Movimientos> clientes = dao.findAll();
        return  clientes;
    }

    public Movimientos getMovimientos(long movimientoId){
        return dao.findById(movimientoId).get();
    }

    public void createMovimientos(Movimientos cliente){
        dao.save(cliente);
    }

    public Movimientos editMovimientos(Movimientos cliente){
        return dao.update(cliente);
    }

    public void deleteMovimientos(long movimientoId){
        dao.delete(movimientoId);
    }
}
