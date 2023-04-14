package productos;

import personas.Cliente;
import utils.TipoCuenta;

public class CuentaPlazoFijo extends Cuenta{
    public CuentaPlazoFijo(int id,double balance, Cliente titular) {
        super(id, balance, titular, TipoCuenta.PlazoFijo);
    }
}
