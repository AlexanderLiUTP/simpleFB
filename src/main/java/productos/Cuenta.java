package productos;

import personas.Cliente;
import utils.TipoCuenta;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class Cuenta implements Serializable {

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private TipoCuenta tipoCuenta;
    private double balance;
    private Map<LocalDateTime, Movimientos> historial = new TreeMap<>();
    private int movInterest= 0;
    private int movMan = 0;
    private int MAX_MOV_MAN = 4;
    private int MAX_MOV_INTEREST = 6;
    private Cliente titular;

    public long getCedulaTitular() {
        return cedulaTitular;
    }

    public void setCedulaTitular(long cedulaTitular) {
        this.cedulaTitular = cedulaTitular;
    }

    private long cedulaTitular;
    public Cuenta(){
        super();
    }
    public Cuenta(int id, double balance, long cedulaTitular){
        this.id = id;
        this.balance = balance;
        this.cedulaTitular = cedulaTitular;
    }
    public Cuenta(int id, double balance, long cedulaTitular, TipoCuenta tipoCuenta){
        this.id = id;
        this.balance = balance;
        this.cedulaTitular = cedulaTitular;
        this.tipoCuenta = tipoCuenta;
    }

    public void aumentarMovInterest(){
        movInterest+=1;
    }
    public void aumentarMovMan(){
        movMan+=1;
    }

    public boolean revisarMantenimiento() {
        if(movMan == MAX_MOV_MAN){
            movMan = 0;
            return true;
        }
        return false;
    }

    public boolean revisarGenerarIntereses() {
        if(movInterest == MAX_MOV_INTEREST){
            movInterest = 0;
            return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<LocalDateTime, Movimientos> getMovimientos() {
        return historial;
    }

    public void setMovimientos(Map<LocalDateTime, Movimientos> historial) {
        this.historial.putAll(historial);
    }

    public void setMovMan(int mov) {
        this.movMan = mov;
    }

    public int getMovMan() {
        return movMan;
    }

    public int getMovInterest() {
        return movInterest;
    }

    public void setMovInterest(int movInterest) {
        this.movInterest = movInterest;
    }

    public int getMAX_MOV_MAN() {
        return MAX_MOV_MAN;
    }
    public int getMAX_MOV_INTEREST() {
        return MAX_MOV_INTEREST;
    }
    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + this.id +
                ", tipoCuenta=" + this.tipoCuenta +
                ", balance=" + this.balance +
                ", cedula=" + this.cedulaTitular+
                '}';
    }
}
