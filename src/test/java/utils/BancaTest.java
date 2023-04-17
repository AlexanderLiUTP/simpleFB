package utils;

import exceptions.NegativeInputFunds;
import exceptions.NotEnoughFundsException;
import org.junit.Assert;
import org.junit.Test;
import personas.Cliente;

public class BancaTest {

    @Test
    public void retirar() throws NotEnoughFundsException, NegativeInputFunds {
        Cliente juan = new Cliente("3-456-898", "Juan", "Gonzalez", "1998-05-25", true);
        Banca sucursal = new Banca();
        sucursal.abirCuenta(juan, TipoCuenta.Ahorro, 500);
        Assert.assertTrue(sucursal.retirar(juan.getCuenta(1), 0));
    }

    @Test
    public void depositar() throws NegativeInputFunds {
        Cliente juan = new Cliente("3-456-898", "Juan", "Gonzalez", "1998-05-25", true);
        Banca sucursal = new Banca();
        sucursal.abirCuenta(juan, TipoCuenta.Ahorro, 500);
        Assert.assertTrue(sucursal.depositar(juan.getCuenta(1), 20));
    }

    @Test(expected= NegativeInputFunds.class)
    public void depositarException() throws NegativeInputFunds {
        Cliente juan = new Cliente("3-456-898", "Juan", "Gonzalez", "1998-05-25", true);
        Banca sucursal = new Banca();
        sucursal.abirCuenta(juan, TipoCuenta.Ahorro, 500);
        sucursal.depositar(juan.getCuenta(1), -100);
    }


}