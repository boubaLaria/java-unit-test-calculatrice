package com.example;

public class Calculatrice {

    public int addition(int a, int b) {
        long startTime = System.nanoTime();
        int result = a + b;
        // Enregistrer l'opération avec plus de détails
        MetricsManager.recordOperation("addition", result);
        return result;
    }

    public int soustraction(int a, int b) {
        long startTime = System.nanoTime();
        int result = a - b;
        // Enregistrer l'opération avec plus de détails
        MetricsManager.recordOperation("soustraction", result);
        return result;
    }

    public int multiplication(int a, int b) {
        long startTime = System.nanoTime();
        int result = a * b;
        // Enregistrer l'opération avec plus de détails
        MetricsManager.recordOperation("multiplication", result);
        return result;
    }

    public float division(int a, int b) {
        long startTime = System.nanoTime();
        try {
            if (b == 0) {
                // Enregistrer l'erreur de division par zéro
                MetricsManager.recordError("division", "division_by_zero");
                throw new ArithmeticException("Division par zéro impossible");
            }
            float result = (float) a / b;
            // Enregistrer l'opération réussie
            MetricsManager.recordOperation("division", result);
            return result;
        } catch (ArithmeticException e) {
            throw e;
        } catch (Exception e) {
            // Enregistrer d'autres erreurs potentielles
            MetricsManager.recordError("division", "unexpected_error");
            throw e;
        }
    }
}
