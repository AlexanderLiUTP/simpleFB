package productos;

import personas.Cliente;
import utils.TipoCuenta;

import java.io.Serializable;

public class CuentaCorriente extends Cuenta implements Serializable {


    public CuentaCorriente(int id, double balance, long cedulaId){
        super(id, balance, cedulaId, TipoCuenta.Corriente);

    }
}
