package com.banquito.banquitoApp.models.productos;

import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.io.Serializable;

public class CuentaCorriente extends Cuenta implements Serializable {


    public CuentaCorriente(int id, double balance, long cedulaId){
        super(id, balance, cedulaId, TipoCuenta.Corriente);

    }
}
