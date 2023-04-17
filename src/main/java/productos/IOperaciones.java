package productos;

import exceptions.NegativeInputFunds;
import exceptions.NotEnoughFundsException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Map;

public interface IOperaciones {

    <T extends Cuenta> boolean retirar(T cuenta, double cantidad) throws NotEnoughFundsException, NegativeInputFunds;
    <T extends Cuenta> boolean depositar(T cuenta, double cantidad) throws NegativeInputFunds;
    <T extends Cuenta> boolean transferenciaEntrecuentas(T cuentaEmisor, T cuentaReceptor, double cantidadTransferir);
    <T extends Cuenta> Double consultarBalance(T cuenta);

    <T extends Cuenta> Map obtenerHistorial(T cuenta);


}
