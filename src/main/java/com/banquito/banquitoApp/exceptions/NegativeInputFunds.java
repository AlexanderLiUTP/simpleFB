package com.banquito.banquitoApp.exceptions;

public class NegativeInputFunds extends Exception{
    public NegativeInputFunds(){
        super ("Entrada negativa de fondos");
    }
}
