package com.banquito.banquitoApp.utils.operaciones;

import com.banquito.banquitoApp.exceptions.NegativeInputFunds;
import com.banquito.banquitoApp.exceptions.NotEnoughFundsException;
import com.banquito.banquitoApp.models.productos.Cuenta;

import java.util.Map;

public interface IOperaciones {

    <T extends Cuenta> boolean retirar(T cuenta, double cantidad) throws NotEnoughFundsException, NegativeInputFunds;
    <T extends Cuenta> boolean depositar(T cuenta, double cantidad) throws NegativeInputFunds;
    <T extends Cuenta> boolean transferenciaEntrecuentas(T cuentaEmisor, T cuentaReceptor, double cantidadTransferir);
    <T extends Cuenta> Double consultarBalance(T cuenta);

    <T extends Cuenta> Map obtenerMovimientos(T cuenta);


}
