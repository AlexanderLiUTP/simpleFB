package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.exceptions.SQLQueryException;
import com.banquito.banquitoApp.models.productos.Movimientos;
import com.banquito.banquitoApp.utils.dao.MovimientoDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class MovimientosController {
    MovimientoDao dao = new MovimientoDao();

    @Scope("Singleton")
    public MovimientosController MovimientosController(){
        return new MovimientosController();
    }

    public List<Movimientos> getAll(){
        List<Movimientos> movimientos = dao.findAll();
        return  movimientos;
    }

    public Movimientos getMovimientos(long movimientoId){
        return dao.findById(movimientoId).get();
    }

    public void createMovimientos(Movimientos movimiento)throws SQLQueryException{
        if(!dao.save(movimiento)){
            throw new SQLQueryException();
        }    
    }

    public void editMovimientos(Movimientos movimiento) throws SQLQueryException {
        if(!dao.update(movimiento)){
            throw new SQLQueryException();
        }
    }

    public void deleteMovimientos(long movimientoId)throws SQLQueryException{
        if(!dao.delete(movimientoId)){
            throw new SQLQueryException();
        }
    }
}
