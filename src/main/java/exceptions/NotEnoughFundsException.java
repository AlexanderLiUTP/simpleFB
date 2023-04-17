package exceptions;

public class NotEnoughFundsException extends Exception{
    public NotEnoughFundsException (){
        super("No hay fondos");
    }
}
