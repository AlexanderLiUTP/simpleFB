package productos;

import personas.Cliente;
import utils.TipoCuenta;

import java.io.Serializable;

public class CuentaCorriente extends Cuenta implements Serializable {


    public CuentaCorriente(int id, double balance, Cliente titular){
        super(id, balance, titular, TipoCuenta.Corriente);

    }
}
