import exceptions.NegativeInputFunds;
import exceptions.NotEnoughFundsException;
import personas.Cliente;
import productos.Cuenta;
import utils.Banca;
import utils.CalificacionRiesgo;
import utils.TipoCuenta;
import utils.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws IOException {

        Cliente titular = crearCliente();
        Banca sucursal = new Banca();
        menuBanquito(titular, sucursal);


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

    private static void printMsg(String msg){
        System.out.println(msg);
    }

    private static void testOperaciones(){
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

    private static Cliente crearCliente() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Bienvenido al BANQUITO!");
        System.out.println();
        System.out.println("Para utilizar los servicios de nuestro BANQUITO debe de registrarse como cliente nuestro.");
        System.out.println("¿Cuál es su nombre?");
        String clientName = bf.readLine();
        System.out.println("¿Cuál es su apellido?");
        String clientLastName = bf.readLine();
        System.out.println("Cedula");
        String cedula = bf.readLine();
        System.out.println("Fecha de naciminto (aaaa-mm-dd)");
        String dob = bf.readLine();
        System.out.println("¿Trabaja? Si/No");
        Boolean estatusTrabajo = bf.readLine().toLowerCase() == "si"? true: false;

        Cliente titular = new Cliente(cedula, clientName, clientLastName, dob, estatusTrabajo);

        System.out.println("Cliente: " +titular.getNombre()+ ". Hemos creado su perfil exitosamente" );

        System.out.println(titular);

        return titular;
    }

    private static void menuBanquito(Cliente titular, Banca sucursal) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("¡Bienvenido a mi BANQUITO!");
        int opcion= 0;
        do {
            System.out.println("¿Qué desea realizar?");
            System.out.println("1. Abrir Cuenta");
            System.out.println("2. Operaciones Cuentas");
            System.out.println("3. Balance Cuentas");
            System.out.println("0. Salir");
            opcion = Integer.parseInt(bf.readLine());

            switch (opcion){
                case 1 -> menuAbrirCuenta(titular, sucursal);
                case 2 -> menuOperacionesCuentas(titular, sucursal);
                case 3 -> menuBalanceCuentas(titular, sucursal);
                case 0 -> System.out.println();
                default -> System.out.println("Escoja una opción válida");
            }
        }while (opcion != 0);

        System.out.println("¡Adios, vuelva pronto!");
    }

    private static void menuAbrirCuenta(Cliente titular, Banca sucursal) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int opcion= 0;
        do {
            System.out.println("¿Qué tipo de cuenta desea abrir?");
            System.out.println("1. Cuenta Ahorro");
            System.out.println("2. Cuenta Corriente");
            System.out.println("3. Cuenta Plazo Fijo");
            System.out.println("0. Regresar");
            opcion = Integer.parseInt(bf.readLine());

            switch (opcion){
                case 1 -> crearAhorro(sucursal, titular);
                case 2 -> crearCorriente(sucursal, titular);
                case 3 -> crearPlazoFijo(sucursal, titular);
                case 0 -> System.out.println();
                default -> System.out.println("Escoja una opción válida");
            }
        }while (opcion != 0);

        System.out.println("Regresando...");
    }

    private static void menuBalanceCuentas(Cliente titular, Banca sucursal) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int opcion= 0;
            System.out.println("¿Cuál cuenta desea revisar el balance?");
            for (int i = 1; i <= titular.getCuentas().size(); i++) {
                Cuenta cuenta = titular.getCuenta(i);
                System.out.println( i + ". Cuenta " +cuenta.getTipoCuenta() + ". Número: "+ cuenta.getId() );
            }
            System.out.println("0. Regresar");
            opcion = Integer.parseInt(bf.readLine());
            Cuenta cuenta = titular.getCuenta(opcion);
            System.out.print("El balance de su cuenta " +cuenta.getId() +" es de: ");
            System.out.println(cuenta.getBalance());
        System.out.println("Regresando...");
        System.out.println();
    }

    private static void menuOperacionesCuentas(Cliente titular, Banca sucursal) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int opcion= 0;
        do {
            System.out.println("¿Qué tipo de operación desea realizar?");
            System.out.println("1. Retirar");
            System.out.println("2. Depositar");
            System.out.println("3. Transferencia entre cuentas");
            System.out.println("0. Regresar");
            opcion = Integer.parseInt(bf.readLine());

            switch (opcion){
                case 1 -> menuRetirar(sucursal, titular);
                case 2 -> menuDepositar(sucursal, titular);
                case 3 -> menuTransferencias(sucursal, titular);
                case 0 -> System.out.println();
                default -> System.out.println("Escoja una opción válida");
            }
        }while (opcion != 0);

        System.out.println("Regresando...");
        System.out.println();

    }

    private static void menuRetirar(Banca sucursal, Cliente titular) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int montoRetiro= 0;
        int cuentaId =0;
            System.out.println("Elija la cuenta del cual desea retirar");
            for (int i = 1; i <= titular.getCuentas().size(); i++) {
                Cuenta cuenta = titular.getCuenta(i);
                System.out.println( i + ". Cuenta " +cuenta.getTipoCuenta() + ". Número: "+ cuenta.getId() );
          }
        cuentaId = Integer.parseInt(bf.readLine());
        while (cuentaId< 0 || cuentaId > titular.getCuentas().size()) {
            System.out.println("Elija una valor valido");
            cuentaId = Integer.parseInt(bf.readLine());
        }


        Cuenta cuenta = titular.getCuenta(cuentaId);
        printBalance(cuenta);
        System.out.println("¿Cuánto desea retirar?");
        montoRetiro = Integer.parseInt(bf.readLine());

        retirarTest(sucursal, titular, cuentaId, montoRetiro);
        System.out.println("Nuevo balance");
        printBalance(cuenta);
        System.out.println("Regresando...");
        System.out.println();
    }

    private static void menuDepositar(Banca sucursal, Cliente titular) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int montoRetiro= 0;
        int cuentaId =0;
        System.out.println("Elija la cuenta al cual desea depositar");
        for (int i = 1; i <= titular.getCuentas().size(); i++) {
            Cuenta cuenta = titular.getCuenta(i);
            System.out.println( i + ". Cuenta " +cuenta.getTipoCuenta() + ". Número: "+ cuenta.getId() );
        }


        cuentaId = Integer.parseInt(bf.readLine());
        while (cuentaId< 0 || cuentaId > titular.getCuentas().size()) {
            System.out.println("Elija una valor valido");
            cuentaId = Integer.parseInt(bf.readLine());
        }
        Cuenta cuenta = titular.getCuenta(cuentaId);
        printBalance(cuenta);
        System.out.println("¿Cuánto desea depositar?");
        montoRetiro = Integer.parseInt(bf.readLine());

        depositarTest(sucursal, titular, cuentaId, montoRetiro);

        System.out.println("Regresando...");
        System.out.println();
    }
    private static void menuTransferencias(Banca sucursal, Cliente titular) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int montoRetiro = 0;
        int emisorId = 0;
        int receptorId = 0;
        System.out.println("Elija la cuenta emisor");
        for (int i = 1; i <= titular.getCuentas().size(); i++) {
            Cuenta cuenta = titular.getCuenta(i);
            System.out.println( i + ". Cuenta " +cuenta.getTipoCuenta() + ". Número: "+ cuenta.getId() );
        }
        emisorId = Integer.parseInt(bf.readLine());

        while (emisorId< 0 || emisorId > titular.getCuentas().size()) {
            System.out.println("Elija una valor valido");
            emisorId = Integer.parseInt(bf.readLine());
        }

        Cuenta cuentaEmisor = titular.getCuenta(emisorId);
        printBalance(cuentaEmisor);

        System.out.println("Elija la cuenta receptor");
        for (int i = 1; i <= titular.getCuentas().size(); i++) {
            Cuenta cuenta = titular.getCuenta(i);
            if( emisorId != i){
                System.out.println( i + ". Cuenta " +cuenta.getTipoCuenta() + ". Número: "+ cuenta.getId() );
            }
        }
        receptorId = Integer.parseInt(bf.readLine());
        while (receptorId< 0 || receptorId > titular.getCuentas().size() || receptorId == emisorId) {
            System.out.println("Elija una valor valido");
            receptorId = Integer.parseInt(bf.readLine());
        }
        Cuenta cuentaReceptor = titular.getCuenta(receptorId);
        printBalance(cuentaReceptor);

        System.out.println("¿Cuánto desea Transferir?");
        montoRetiro = Integer.parseInt(bf.readLine());

        transacEntreCuentas(sucursal, titular, emisorId, receptorId, montoRetiro);

        System.out.println("Regresando...");
        System.out.println();
    }

    private static void printBalance(Cuenta cuenta){
        System.out.print("El balance de su cuenta " +cuenta.getId() +" es de: " +cuenta.getBalance());
        System.out.println();
    }

}
