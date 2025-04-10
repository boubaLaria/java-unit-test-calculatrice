package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculatrice calculatrice = new Calculatrice();
        Scanner scanner = new Scanner(System.in);
        
        boolean continuer = true;
        
        while (continuer) {
            System.out.println("\n===== MENU CALCULATRICE =====");
            System.out.println("1. Addition");
            System.out.println("2. Soustraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une opération (0-4): ");
            
            int choix = scanner.nextInt();
            
            if (choix == 0) {
                System.out.println("Au revoir!");
                continuer = false;
                continue;
            }
            
            if (choix < 1 || choix > 4) {
                System.out.println("Choix invalide. Veuillez réessayer.");
                continue;
            }
            
            System.out.print("Entrez le premier nombre: ");
            int a = scanner.nextInt();
            System.out.print("Entrez le deuxième nombre: ");
            int b = scanner.nextInt();
            
            switch (choix) {
                case 1:
                    System.out.println("Résultat: " + calculatrice.addition(a, b));
                    break;
                case 2:
                    System.out.println("Résultat: " + calculatrice.soustraction(a, b));
                    break;
                case 3:
                    System.out.println("Résultat: " + calculatrice.multiplication(a, b));
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Erreur: Division par zéro impossible!");
                    } else {
                        System.out.println("Résultat: " + calculatrice.division(a, b));
                    }
                    break;
            }
        }
        
        scanner.close();
    }


}