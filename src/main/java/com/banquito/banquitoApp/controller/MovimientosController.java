package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Movimientos;
import com.banquito.banquitoApp.utils.dao.MovimientoDao;

import java.util.List;

public class MovimientosController {
    MovimientoDao dao = new MovimientoDao();
    public List<Movimientos> getAll(){
        List<Movimientos> movimientos = dao.findAll();
        return  movimientos;
    }

    public Movimientos getMovimientos(long movimientoId){
        return dao.findById(movimientoId).get();
    }

    public void createMovimientos(Movimientos movimiento){
        dao.save(movimiento);
    }

    public void editMovimientos(Movimientos movimiento) throws SQLQueryException {
        if(!dao.update(movimiento)){
            throw new SQLQueryException();
        }
    }

    public void deleteMovimientos(long movimientoId){
        dao.delete(movimientoId);
    }
}
