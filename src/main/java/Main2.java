import com.banquito.banquitoApp.models.personas.Cliente;
import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.models.productos.Movimientos;
import com.banquito.banquitoApp.utils.dao.ClienteDao;
import com.banquito.banquitoApp.utils.dao.CuentaDao;
import com.banquito.banquitoApp.utils.dao.MovimientoDao;
import com.banquito.banquitoApp.utils.db.DBConnection;
import com.banquito.banquitoApp.utils.db.testDBInit;
import com.banquito.banquitoApp.utils.operaciones.CalificacionRiesgo;
import com.banquito.banquitoApp.utils.operaciones.TipoCuenta;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Main2 {
    private static Connection connection;

    public static void main(String[] args) throws ClassNotFoundException {
        Logger logger = Logger.getLogger("JDBC-CRUD");
        // Creates the table "CUSTOMERS"
        testDBInit.init();
        Connection connection = DBConnection.getConnection();
        cargaDatosToTable();
        CuentaDao cuentaDao = new CuentaDao();
        ClienteDao clienteDao = new ClienteDao();
        MovimientoDao movimientoDao = new MovimientoDao();
        Cuenta newCUenta;

        List<String> nombres = Arrays.asList("Juan", "Pedro", "Miguel", "Alberto", "Carlos");
        List<String> apellidos = Arrays.asList("Juan", "Pedro", "Miguel", "Alberto", "Carlos");
        Collections.shuffle(nombres);
        Collections.shuffle(apellidos);
        Random rand = new Random();

        String[] tipo = {"Ahorro", "Corriente"};

//        for (int i = 0; i<5; i++){
//            newCUenta = new Cuenta();
//            newCUenta.setTipoCuenta(tipo[rand.nextInt(2)]);
//            newCUenta.setLastName(apellidos.get(i));
//            newCUenta.setFirstName(nombres.get(i));
//            newCUenta.setBalance(rand.nextInt(1, 500));
//
//            cuentaDao.save(newCUenta);
//        }




        List<Cuenta> cuentas = cuentaDao.findAll();
        List<Cliente> cliente = clienteDao.findAll();
        List<Movimientos> movimientos = movimientoDao.findAll();

        cuentas.forEach(System.out::println);
        cliente.forEach(System.out::println);
        movimientos.forEach(System.out::println);
        Movimientos mov = movimientos.get(0);
        mov.setTipoMovimiento("Deposito");
        System.out.println(mov);
        movimientoDao.update(mov);
        movimientos = movimientoDao.findAll();

        Cuenta cuentaUpdate = cuentas.get(0);
        cuentaUpdate.setBalance(985.25);
        cuentaDao.update(cuentaUpdate);
        cuentas = cuentaDao.findAll();

        Cliente clienteUpdate = cliente.get(0);

        clienteUpdate.setNivelRiesgo(CalificacionRiesgo.AAA);
        clienteUpdate.setApellido("Li");

        clienteDao.update(clienteUpdate);
        cliente = clienteDao.findAll();
        System.out.println("Edited");
        cliente.forEach(System.out::println);
        cuentas.forEach(System.out::println);
        movimientos.forEach(System.out::println);
//        int randNumber = rand.nextInt(cuentas.size());
//        Cuenta cuentaUpdate = cuentas.get(randNumber);
//        cuentaUpdate.setBalance(rand.nextInt(600, 900));
//
//        randNumber = rand.nextInt(cuentas.size());
//
//        cuentaUpdate = cuentas.get(randNumber);
//        cuentaUpdate.setBalance(rand.nextInt(600, 900));



    }

    private static void cargaDatosToTable() {
        ClienteDao clientDao = new ClienteDao();
        CuentaDao accDao = new CuentaDao();
        MovimientoDao movDao = new MovimientoDao();

        Cliente clientDummy = new Cliente();
        clientDummy.setCedula(27452200);
        clientDummy.setNombre("Alexander");
        clientDummy.setApellido("Gonzalo");
        clientDummy.setFechaNacimiento(LocalDate.parse("1998-02-25"));
        clientDummy.setEstatusTrabajo(true);
        clientDummy.setNivelRiesgo(CalificacionRiesgo.B);
        clientDao.save(clientDummy);

        Cuenta accDummy = new Cuenta();

        accDummy.setTipoCuenta(TipoCuenta.Ahorro);
        accDummy.setBalance(500.24);
        accDummy.setCedulaTitular(27452200);
        accDao.save(accDummy);

        accDummy = new Cuenta();
        accDummy.setTipoCuenta(TipoCuenta.Ahorro);
        accDummy.setBalance(5458.25);
        accDummy.setCedulaTitular(27452200);
        accDao.save(accDummy);

        List<Cuenta> cuentas = accDao.findAll();
        long cuentaId = cuentas.get(0).getId();
        LocalDate dateNow = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        Movimientos movDummy = new Movimientos( );
        movDummy.setId(1);
        movDummy.setTipoMovimiento("Retiro");
        movDummy.setEstado("Rechazado");
        movDummy.setFecha(dateNow);
        movDummy.setHora(localTime);
        movDummy.setCuentaId(cuentaId);
        movDao.save(movDummy);
        movDao.save(movDummy);



    }
}
