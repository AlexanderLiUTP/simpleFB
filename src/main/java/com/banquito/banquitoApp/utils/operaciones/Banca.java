package com.banquito.banquitoApp.utils.operaciones;

import com.banquito.banquitoApp.exceptions.NegativeInputFunds;
import com.banquito.banquitoApp.exceptions.NotEnoughFundsException;
import com.banquito.banquitoApp.models.personas.Cliente;
import com.banquito.banquitoApp.models.productos.*;
import com.banquito.banquitoApp.utils.dao.CuentaDao;
import com.banquito.banquitoApp.utils.dao.MovimientoDao;
import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
//import java.util.function.BiPredicate;

public class Banca implements IOperaciones {
    private int uniqueId =1;
    private CuentaDao cuentaDao = new CuentaDao();
    private MovimientoDao movimientosDao = new MovimientoDao();

    //private BiPredicate<Double, Double> fondos = (fondos, balance) -> fondos > balance;
    @Override
    public <T extends Cuenta> boolean retirar(T cuenta, double cantidad) throws NotEnoughFundsException, NegativeInputFunds {
        if(cuenta.getTipoCuenta() == TipoCuenta.PlazoFijo){
            System.out.println("No puede hacer retiros a plazo fijo");
            return false;
        }
        if (cantidad < 0){
            agregarMovimientos("Retiro", "Rechazado", cuenta );
            throw new NegativeInputFunds();

        }
        if(!fondosSuficiente(cuenta, cantidad)){
            agregarMovimientos("Retiro", "Rechazado", cuenta );
            throw new NotEnoughFundsException();

        }
        cuenta.setBalance(cuenta.getBalance() - cantidad);
        cuentaDao.update(cuenta);
        operacionesMovimiento(cuenta);
        agregarMovimientos("Retiro", "Aceptado", cuenta );
        return true;
    }

    @Override
    public <T extends Cuenta> boolean depositar(T cuenta, double cantidad) throws NegativeInputFunds{
        if (cantidad > 0){
            cuenta.setBalance(cuenta.getBalance() + cantidad);
            cuentaDao.update(cuenta);
            operacionesMovimiento(cuenta);
            agregarMovimientos("Deposito", "Aceptado", cuenta );

            return true;
        }
        agregarMovimientos("Deposito", "Rechazado", cuenta );
        throw new NegativeInputFunds();
    }

    @Override
    public <T extends Cuenta> boolean transferenciaEntrecuentas(T cuentaEmisor, T cuentaReceptor,
                                                                double cantidadTransferir) {
        try{
            if(retirar(cuentaEmisor, cantidadTransferir))
            {
                    depositar(cuentaReceptor, cantidadTransferir);
                    return true;
            }
        }catch (NotEnoughFundsException e){
            return false;
        }catch (NegativeInputFunds e){
            return false;
        }
        return false;
    }

    @Override
    public <T extends Cuenta> Double consultarBalance(T cuenta) {
        return cuenta.getBalance();
    }

    @Override
    public <T extends Cuenta> Map obtenerMovimientos(T cuenta) {
        return cuenta.getMovimientos();
    }

    private <T extends Cuenta> boolean fondosSuficiente(T cuenta, double valor){
        return cuenta.getBalance()>valor;
    }

    private <T extends Cuenta> void agregarMovimientos(String tipoMov, String estado, T cuenta){
        LocalDateTime dateTimeNow = LocalDateTime.now();
        Movimientos historial = new Movimientos();
                //(tipoMov, estado, dateTimeNow.toLocalDate(), dateTimeNow.toLocalTime(),cuenta.getId());
        historial.setTipoMovimiento(tipoMov);
        historial.setEstado(estado);
        historial.setFecha(dateTimeNow.toLocalDate());
        historial.setHora(dateTimeNow.toLocalTime());
        historial.setCuentaId(cuenta.getId());
        movimientosDao.update(historial);
        Map<LocalDateTime, Movimientos> entryMovimientos = new TreeMap<>();
        entryMovimientos.put(dateTimeNow, historial);
        cuenta.setMovimientos(entryMovimientos);
    }



    public boolean abirCuenta(Cliente titular, TipoCuenta tipoCuenta, double montoInicial){
        if (!isLegalAge(titular)){
            // Throws NotLegalAgeException
            System.out.println("Es menor de edad.");
            return false;
        }
        if(isFirstAcc(titular) && !tipoCuenta.equals(TipoCuenta.Ahorro)){
            // Throws FirstAccountNotSaving
            System.out.println("Su primera cuenta no puede ser de Plazo Fijo o Corriente.");
            return false;
        }

        switch (tipoCuenta){
            case Ahorro -> {
                CuentaAhorro nuevaCuenta = new CuentaAhorro(generarId(),montoInicial, titular.getCedula());
                titular.setCuentas(nuevaCuenta);
                cuentaDao.save(nuevaCuenta);
                return true;
            }
            case Corriente -> {
                if(titular.getNivelRiesgo().isRisky()){
                    System.out.println("Mal historial de credito. Cuenta Corriente no abierta.");
                    return false;
                }
                CuentaCorriente nuevaCuenta = new CuentaCorriente(generarId(),montoInicial, titular.getCedula());
                titular.setCuentas(nuevaCuenta);
                cuentaDao.save(nuevaCuenta);
                return true;
            }
            case PlazoFijo -> {
                if(titular.getNivelRiesgo().isRisky()){
                    System.out.println("Mal historial de credito. Cuenta de Plazo Fijo no abierta.");
                    return false;
                }
                CuentaPlazoFijo nuevaCuenta = new CuentaPlazoFijo(generarId(), montoInicial,titular.getCedula());
                titular.setCuentas(nuevaCuenta);
                cuentaDao.save(nuevaCuenta);
                return true;
            }
        }
        return false;
    }


    public <T extends Cuenta>void generarInteres(T cuenta){
        if(cuenta.revisarGenerarIntereses()){
            double tasa =cuenta.getTipoCuenta().getTasa()/100d;
            double interesesAgregados = cuenta.getBalance() *  tasa;
            System.out.println(interesesAgregados);
            try{
                depositar(cuenta, interesesAgregados);
                // DEGUG PRINT
                System.out.println("Intereses generados");
            } catch (NegativeInputFunds e){
                System.out.println(e.getMessage()); // Sería Log
            }
        }

    }

    public <T extends Cuenta>void cobrarMantenimiento(T cuenta){
        if (cuenta.revisarMantenimiento()) {
            double mantenimiento = cuenta.getBalance() * 0.012;

            try {
                retirar(cuenta, mantenimiento);
                // DEGUG PRINT
                System.out.println("Mantenimiento cobrado");
            } catch (NegativeInputFunds e) {
                System.out.println(e.getMessage()); // Sería Log
            } catch (NotEnoughFundsException e) {
                System.out.println(e.getMessage()); // Sería Log
            }
        }
    }

    public <T extends Cuenta> void operacionesMovimiento(T cuenta){
        generarInteres(cuenta);
        cobrarMantenimiento(cuenta);
        cuenta.aumentarMovMan();
        cuenta.aumentarMovInterest();
        cuentaDao.update(cuenta);
    }

    private int generarId(){
        return uniqueId++;
    }

    private boolean isLegalAge(Cliente titular){
        return titular.getEdad()>18;
    }

    private boolean isFirstAcc(Cliente titular){
        return titular.getCuentas().isEmpty();
    }
}
