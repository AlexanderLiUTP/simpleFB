package productos;

import personas.Cliente;
import utils.TipoCuenta;

import java.io.Serializable;

public class CuentaAhorro extends Cuenta implements Serializable {
    private final int MAX_MOV_MAN = 6;

    public CuentaAhorro(int id, double balance, Cliente titular) {
        super(id, balance, titular, TipoCuenta.Ahorro);

    }

    public void generarInteres(){

    }


}
