import exceptions.NegativeInputFunds;
import exceptions.NotEnoughFundsException;
import personas.Cliente;
import utils.Banca;
import utils.CalificacionRiesgo;
import utils.TipoCuenta;

public class Main {
    public static void main(String[] args) {
        Banca sucursal = new Banca();
        Cliente juan = new Cliente("3-456-898", "Juan", "Gonzalez", "1998-05-25", true);
        Cliente albert = new Cliente("6-156-2598", "Albert", "Fernandez", "1982-07-15", true);
        Cliente mike = new Cliente("8-956-2002", "Mike", "Sanchez", "2008-01-15", true);
        Cliente rodolf = new Cliente("1-756-9058", "Rodolf", "Smith", "1998-05-25", true);
        Cliente bernando = new Cliente("5-302-6898", "Bernando", "Martinez", "1998-05-25", true, CalificacionRiesgo.C);


        System.out.println("TC-C01");
        System.out.println("Expected: Cuenta creada");
        crearAhorro(sucursal, juan );

        System.out.println("TC-C02");
        System.out.println("Expected: Cuenta no creada");
        crearCorriente(sucursal, albert);

        System.out.println("TC-C03");
        System.out.println("Expected: Cuenta no creada");
        crearPlazoFijo(sucursal, albert);

        System.out.println("TC-C04");
        System.out.println("Expected: Cuenta creada");
        crearCorriente(sucursal, juan);

        System.out.println("TC-C05");
        System.out.println("Expected: Cuenta creada");
        crearPlazoFijo(sucursal, juan);

        System.out.println("TC-C06");
        System.out.println("Expected: Cuenta no creada por ser menor");
        crearAhorro(sucursal, mike);

        System.out.println("TC-C07");
        System.out.println("Expected: Cuenta creada");
        crearAhorro(sucursal, rodolf);
        crearCorriente(sucursal, rodolf);
        crearPlazoFijo(sucursal, rodolf);

        System.out.println("TC-C08");
        System.out.println("Expected: Cuenta no creada por mal historial");
        crearAhorro(sucursal, bernando);
        crearCorriente(sucursal, bernando);
        crearPlazoFijo(sucursal, bernando);

        System.out.println("TC-OP01");
        System.out.println("Expected: Retiro Declinado");
        retirarTest(sucursal, rodolf, 3, 200);

        System.out.println("TC-OP02");
        System.out.println("Expected: Deposito Aprobado");
        depositarTest(sucursal, rodolf, 3, 500);


        System.out.println("TC-OP03");
        System.out.println("Expected: Deposito Declinado");
        depositarTest(sucursal, rodolf, 3, -200);

        System.out.println("TC-OP04");
        System.out.println("Expected: Retiro Declinado");
        retirarTest(sucursal, juan, 1, 50000);


        System.out.println("TC-OP05");
        System.out.println("Transaccion entre cuentas");
        transacEntreCuentas(sucursal, juan, 1, 2, 200);

        System.out.println("TC-OP06");
        System.out.println("Transaccion entre cuentas falta de fondo");
        transacEntreCuentas(sucursal, rodolf, 1, 2, 20000);

/*        try {

            sucursal.depositar(juan.getCuentas().get(0), 100);
        }catch (NegativeInputFunds e){
            System.out.println(e.getMessage());
        }


        try {

            sucursal.depositar(juan.getCuentas().get(0), -200);
        }catch (NegativeInputFunds e){
            System.out.println(e.getMessage());
        }

        sucursal.abirCuenta(juan,TipoCuenta.PlazoFijo, 500);

        for (int i= 0; i<10; i++){
            try {

                sucursal.depositar(juan.getCuenta(1), 10);
            } catch (NegativeInputFunds e){
                System.out.println(e.getMessage());
            }
            System.out.println("Balance actual: " +juan.getCuenta(1).getBalance());
        }

        sucursal.transferenciaEntrecuentas(juan.getCuenta(1), juan.getCuenta(2), 200 );

        juan.getCuentas().forEach(System.out::println);

        juan.getCuenta(1).getHistorial().forEach((fecha, movimiento) -> System.out.println(movimiento));
*/


    }
    private static void crearAhorro(Banca sucursal, Cliente clienteTest) {
        if (sucursal.abirCuenta(clienteTest, TipoCuenta.Ahorro, 500)) {
            System.out.println("Cuenta Ahorro Creada");

        } else {
            System.out.println("Cuenta Ahorro NO creada");
            clienteTest.getCuentas().forEach(System.out::println);
        }
        clienteTest.getCuentas().forEach(System.out::println);
        System.out.println();

    }

    private static void crearCorriente(Banca sucursal, Cliente clienteTest) {
        if (sucursal.abirCuenta(clienteTest, TipoCuenta.Corriente, 500)) {

            System.out.println("Cuenta Corriente Creada");
        } else {
            System.out.println("Cuenta Corriente NO creada");
            clienteTest.getCuentas().forEach(System.out::println);
        }
        clienteTest.getCuentas().forEach(System.out::println);

        System.out.println();

    }

    private static void crearPlazoFijo(Banca sucursal, Cliente clienteTest){
        if (sucursal.abirCuenta(clienteTest, TipoCuenta.PlazoFijo, 500)){
            //DEBUG
            System.out.println("Cuenta Plazo Fijo Creada");
        }
        else
        {
            System.out.println("Cuenta Plazo Fijo NO creada");
        }
        clienteTest.getCuentas().forEach(System.out::println);
        System.out.println();
    }

    private static void retirarTest(Banca sucursal, Cliente cliente, int cuentaId, double cantidad){
        try {
            System.out.println("Balance antes de retirar");
            System.out.println(cliente.getCuenta(cuentaId).getBalance());

            sucursal.retirar(cliente.getCuenta(cuentaId), cantidad);

            System.out.println("Retiro Aceptado");
            System.out.println(cliente.getCuenta(cuentaId).getBalance());
        }catch (NegativeInputFunds e){
            System.out.println(e.getMessage());
        }catch (NotEnoughFundsException e){
            System.out.println("Retiro Declinado");
            System.out.println(e.getMessage());
        }
        System.out.println();


    }

    private static void depositarTest(Banca sucursal, Cliente cliente, int cuentaId, double cantidad) {

        try {
            System.out.println("Balance antes de depositar");
            System.out.println(cliente.getCuenta(cuentaId).getBalance());

            sucursal.depositar(cliente.getCuenta(cuentaId),cantidad );

            System.out.println("Deposito Aceptado");
            System.out.println(cliente.getCuenta(cuentaId).getBalance());

        } catch (NegativeInputFunds e){
            System.out.println("Deposito Declinado");
            System.out.println(e.getMessage());
        }
        System.out.println();

    }
    private static void transacEntreCuentas(Banca sucursal, Cliente cliente, int idCuentaEmisor, int idCuentaReceptor, double cantidad){
        System.out.println("Balance cuenta emisor: " + cliente.getCuenta(idCuentaEmisor).getBalance());
        System.out.println("Balance cuenta Receptor: " + cliente.getCuenta(idCuentaReceptor).getBalance());
        System.out.println("Cantidad a transferir: " +cantidad);
        System.out.println();

        if(!sucursal.transferenciaEntrecuentas(cliente.getCuenta(idCuentaEmisor), cliente.getCuenta(idCuentaReceptor), cantidad )){
            System.out.println("Transacción Declinada");
        }else {

            System.out.println("Transacción Aceptada");
        }
        System.out.println();


        System.out.println("Nuevo balance cuenta emisor: " + cliente.getCuenta(idCuentaEmisor).getBalance());
        System.out.println("Nuevo balance cuenta Receptor: " + cliente.getCuenta(idCuentaReceptor).getBalance());
        System.out.println();
    }

    }
