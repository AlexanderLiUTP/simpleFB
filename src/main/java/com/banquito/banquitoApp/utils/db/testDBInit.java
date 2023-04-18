package com.banquito.banquitoApp.utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class testDBInit {


    private testDBInit(){}

    public static void init() throws ClassNotFoundException {

        //Creamos la carga del Driver
        //Class.forName("org.h2.Driver");

        //Habilitamos y creamos una conexion hacia la base de datos
        Connection connection = DBConnection.getConnection();

        try(Statement statement = connection.createStatement()) {
            statement.execute(getMovimientosTruncate());
        }catch (SQLException e){
            e.printStackTrace();
        }

        try(Statement statement = connection.createStatement()) {
            statement.execute(getCuentasTruncate());
        }catch (SQLException e){
            e.printStackTrace();
        }

        try(Statement statement = connection.createStatement()) {
            statement.execute(getCustomersTruncate());
        }catch (SQLException e){
            e.printStackTrace();
        }

        try(Statement statement = connection.createStatement()) {
            statement.execute(getCustomersCreateQuery());
        }catch (SQLException e){
            e.printStackTrace();
        }



        try(Statement statement = connection.createStatement()) {
            statement.execute(getCuentasCreateQuery());
        }catch (SQLException e){
            e.printStackTrace();
        }


        try(Statement statement = connection.createStatement()) {
            System.out.println("Creating table movimientos");
            statement.execute(getMovimientosCreateQuery());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static String getCustomersTruncate(){
        return "DROP TABLE IF EXISTS clientes;";
    }

    private static String getCustomersCreateQuery(){
        return "CREATE TABLE IF NOT EXISTS clientes (" +
                "\tCEDULA BIGINT NOT NULL PRIMARY KEY,\n" +
                "\tNOMBRE varchar(255) NOT NULL,\n" +
                "\tAPELLIDO varchar(255) NOT NULL,\n" +
                "\tFECHA_NACIMIENTO DATE NOT NULL,\n" +
                "\testatusTrabajo boolean NOT NULL,\n" +
                "\tnivelRiesgo ENUM('AAA', 'AA', 'A', 'BBB', 'BB', 'B', 'C', 'D', 'F')\n" +
                ");";
    }

    private static String getCuentasTruncate(){
        return "DROP TABLE IF EXISTS cuentas;";
    }
    private static String getCuentasCreateQuery(){
        return "CREATE TABLE IF NOT EXISTS cuentas (\n" +
                "\tID_CUENTA BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
                "\tTIPO_CUENTA ENUM('Ahorro','Corriente','PlazoFijo') NOT NULL,\n" +
                "\tBALANCE DOUBLE NOT NULL,\n" +
                "\tMOV_INTEREST INT NOT NULL,\n" +
                "\tMOV_MANTENIMIENTO INT NOT NULL,\n" +
                "\tCEDULA_TITULAR BIGINT NOT NULL, \n" +
                "\tCONSTRAINT FK_CLIENTE_CEDULA foreign key(CEDULA_TITULAR) references CLIENTES(CEDULA)\n" +
                ");";
    }

    private static String getMovimientosTruncate(){
        return "DROP TABLE IF EXISTS movimientos;";
    }
    private static String getMovimientosCreateQuery(){
        return "CREATE TABLE IF NOT EXISTS movimientos (" +
                "\tID_MOVIMIENTO BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "\tTIPO_MOVIMIENTO varchar(255) NOT NULL,\n" +
                "\tESTADO varchar(255) NOT NULL,\n" +
                "\tFECHA DATE NOT NULL,\n" +
                "\tHORA TIME NOT NULL,\n" +
                "\tID_CUENTA BIGINT NOT NULL, \n" +
                "\tCONSTRAINT FK_CUENTA_ID foreign key(ID_CUENTA) references CUENTAS(ID_CUENTA)\n" +
                ");";
    }
}
