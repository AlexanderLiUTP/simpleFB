package com.banquito.banquitoApp.models.personas;

import com.banquito.banquitoApp.models.productos.Cuenta;
import com.banquito.banquitoApp.utils.operaciones.CalificacionRiesgo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Cliente implements Serializable {

    private long cedula;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private boolean estatusTrabajo;
    private CalificacionRiesgo nivelRiesgo = CalificacionRiesgo.B;
    private List cuentas = new ArrayList<>();

    public Cliente(){

    }
    public Cliente(long cedula, String nombre, String apellido, String fechaNacimiento, boolean estatusTrabajo){
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
        this.estatusTrabajo = estatusTrabajo;
    }



    public Cliente(long cedula, String nombre, String apellido,
                   String fechaNacimiento, boolean estatusTrabajo, CalificacionRiesgo riesgo){
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
        this.estatusTrabajo = estatusTrabajo;
        this.nivelRiesgo = riesgo;
    }

    public long getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getEstatusTrabajo() {
        return estatusTrabajo;
    }

    public void setEstatusTrabajo(boolean estatusTrabajo) {
        this.estatusTrabajo = estatusTrabajo;
    }

    public int getEdad(){
        return fechaNacimiento.until(LocalDate.now()).getYears();
    }

    public List<? extends Cuenta> getCuentas() {
        return cuentas;
    }

    public <T extends Cuenta> T getCuenta(int i){
        return (T) cuentas.get(i-1);
    }

    public CalificacionRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(CalificacionRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public void setCuentas(List<? extends Cuenta> cuentas) {
        cuentas.forEach(cuenta -> this.cuentas.add(cuenta));
    }

    public <T extends Cuenta>void setCuentas(T cuenta) {
        this.cuentas.add(cuenta);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", estatusTrabajo=" + estatusTrabajo +
                ", nivelRiesgo=" + nivelRiesgo +
                ", cuentas=" + cuentas +
                '}';
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }
}
