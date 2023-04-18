package com.banquito.banquitoApp.models.productos;

import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.io.Serializable;

public class CuentaPlazoFijo extends Cuenta implements Serializable {
    public CuentaPlazoFijo(int id,double balance, long cedulaId) {
        super(id, balance, cedulaId, TipoCuenta.PlazoFijo);
    }
}
