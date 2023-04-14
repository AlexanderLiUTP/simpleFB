package productos;

import personas.Cliente;
import utils.TipoCuenta;

public class CuentaCorriente extends Cuenta{


    public CuentaCorriente(int id, double balance, Cliente titular){
        super(id, balance, titular, TipoCuenta.Corriente);

    }
}
