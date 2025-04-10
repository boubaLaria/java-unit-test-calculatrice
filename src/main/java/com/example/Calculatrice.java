package com.example;

public class Calculatrice {

    public int addition(int a, int b) {
       return a + b;
    }

    public int soustraction(int a, int b) {
       return a - b;
    }

    public int multiplication(int a, int b) {
         return a * b;
    }

    public float division(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division par zéro impossible");
        }
        return a / b;
    }

}
