package com.banquito.banquitoApp.controller;

import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.models.personas.Cliente;

import com.banquito.banquitoApp.utils.dao.CuentaDao;
import com.banquito.banquitoApp.utils.operaciones.Banca;
import com.banquito.banquitoApp.exceptions.NotEnoughFundsException;
import com.banquito.banquitoApp.exceptions.NegativeInputFunds;
import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.util.ArrayList;
import java.util.List;

public class BancaController {
    CuentaDao cuentaDao = new CuentaDao();
    Banca sucursal = new Banca();
    public BancaController(){

    }
    // TODO: metodos para obtener los datos conjunto de clientes y cuentas
    public List<Cuenta> getCuentas(long cedula){
        // List<Cuenta> listaCuentas = new ArrayList<>();
        List<Cuenta> listaCuentas = cuentaDao.findAllFromClient(cedula);
        return listaCuentas;
    }

    public boolean retirar(Cuenta cuenta, double cantidad){
        try{
            return sucursal.retirar(cuenta, cantidad);
        }catch (NotEnoughFundsException e){
            // System.out.println(e.getMessage);
        }catch (NegativeInputFunds e){
            // System.out.println(e.getMessage);
        }
                return false;

    }

    public boolean depositar(Cuenta cuenta, double cantidad){
        try{
            return sucursal.depositar(cuenta, cantidad);
        }catch (NegativeInputFunds e){
            // System.out.println(e.getMessage);
        }
        return false;
    }

     public boolean transferenciaEntrecuentas(Cuenta cuentaEmisor, Cuenta cuentaReceptor, double cantidad){
            return sucursal.transferenciaEntrecuentas(cuentaEmisor, cuentaReceptor, cantidad);

    }

    public double getBalance(Cuenta cuenta){
        return sucursal.consultarBalance(cuenta);
    }

    public boolean abrirCuenta(Cliente titular, TipoCuenta tipoCuenta, double montoInicial){
        return sucursal.abirCuenta(titular, tipoCuenta, montoInicial);
    }

    
}