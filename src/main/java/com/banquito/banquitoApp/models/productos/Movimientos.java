package com.banquito.banquitoApp.models.productos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter @Setter @ToString
public class Movimientos implements Serializable {
    long id;
    String tipoMovimiento;
    String estado;
    LocalDate fecha;
    LocalTime hora;

    public Movimientos(String tipoMovimiento, String estado, LocalDate fecha, LocalTime hora, long cuentaId) {
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.cuentaId = cuentaId;
    }

    public Movimientos(long id, String tipoMovimiento, String estado, LocalDate fecha, LocalTime hora, long cuentaId) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.cuentaId = cuentaId;
    }

    long cuentaId;
    public Movimientos(){

    }
    public Movimientos(String tipoMovimiento, String estado){
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
    }

 public Movimientos(long id, String tipoMovimiento, String estado, long cuentaId) {
  this.id = id;
  this.tipoMovimiento = tipoMovimiento;
  this.estado = estado;
  this.cuentaId = cuentaId;
 }
}
