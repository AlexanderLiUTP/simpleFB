package com.banquito.banquitoApp.models.productos;

import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.io.Serializable;

public class CuentaAhorro extends Cuenta implements Serializable {
    private final int MAX_MOV_MAN = 6;

    public CuentaAhorro(int id, double balance, long cedulaId) {
        super(id, balance, cedulaId, TipoCuenta.Ahorro);

    }

    public void generarInteres(){

    }


}
