package productos;

import personas.Cliente;
import utils.TipoCuenta;

import java.io.Serializable;

public class CuentaPlazoFijo extends Cuenta implements Serializable {
    public CuentaPlazoFijo(int id,double balance, Cliente titular) {
        super(id, balance, titular, TipoCuenta.PlazoFijo);
    }
}
