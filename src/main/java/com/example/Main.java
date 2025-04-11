package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialiser les métriques Prometheus
        MetricsManager.initMetrics();
        
        Calculatrice calculatrice = new Calculatrice();
        Scanner scanner = new Scanner(System.in);
        
        boolean continuer = true;
        int totalOperations = 0;
        
        while (continuer) {
            System.out.println("\n===== MENU CALCULATRICE =====");
            System.out.println("1. Addition");
            System.out.println("2. Soustraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une opération (0-4): ");
            
            try {
                int choix = scanner.nextInt();
                
                // Pousser les métriques après chaque choix utilisateur
                MetricsManager.pushMetrics();
                
                if (choix == 0) {
                    System.out.println("Au revoir!");
                    continuer = false;
                    continue;
                }
                
                if (choix < 1 || choix > 4) {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    MetricsManager.recordError("menu", "invalid_choice");
                    continue;
                }
                
                System.out.print("Entrez le premier nombre: ");
                int a = scanner.nextInt();
                System.out.print("Entrez le deuxième nombre: ");
                int b = scanner.nextInt();
                
                // Enregistrer une métrique de session
                totalOperations++;
                
                try {
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
                            try {
                                if (b == 0) {
                                    System.out.println("Erreur: Division par zéro impossible!");
                                    MetricsManager.recordError("division", "division_by_zero");
                                } else {
                                    System.out.println("Résultat: " + calculatrice.division(a, b));
                                }
                            } catch (ArithmeticException e) {
                                System.out.println("Erreur: " + e.getMessage());
                            }
                            break;
                    }
                    
                    // Pousser les métriques après chaque opération
                    MetricsManager.pushMetrics();
                    
                } catch (Exception e) {
                    System.out.println("Erreur: " + e.getMessage());
                    MetricsManager.recordError("execution", e.getClass().getSimpleName());
                    MetricsManager.pushMetrics();
                }
            } catch (Exception e) {
                System.out.println("Erreur d'entrée: " + e.getMessage());
                scanner.nextLine(); // Nettoyer le buffer d'entrée
                MetricsManager.recordError("input", "invalid_input");
                MetricsManager.pushMetrics();
            }
        }
        
        scanner.close();
        
        // Pousser les métriques finales avant de quitter
        MetricsManager.pushMetrics();
        
        // Arrêter le serveur de métriques avant de quitter
        MetricsManager.stopMetrics();
    }
}