package com.example;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.exporter.PushGateway;
import io.prometheus.client.CollectorRegistry;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

public class MetricsManager {
    // Compteur simple pour nombre d'opérations
    private static final Counter operationsCounter = Counter.build()
            .name("calculatrice_operations_total")
            .help("Nombre total d'opérations effectuées")
            .labelNames("operation")
            .register();
    
    // Gauge pour stocker le dernier résultat
    private static final Gauge lastResultGauge = Gauge.build()
            .name("calculatrice_last_result")
            .help("Dernier résultat calculé")
            .labelNames("operation")
            .register();
    
    // Compteur d'erreurs
    private static final Counter errorsCounter = Counter.build()
            .name("calculatrice_errors_total")
            .help("Nombre total d'erreurs")
            .labelNames("operation", "error_type")
            .register();
    
    // Histogram pour mesurer la distribution des résultats
    private static final Histogram resultHistogram = Histogram.build()
            .name("calculatrice_result_distribution")
            .help("Distribution des résultats calculés")
            .labelNames("operation")
            .buckets(0.0, 1.0, 10.0, 100.0, 1000.0, 10000.0)
            .register();
    
    // Summary pour mesurer le temps d'exécution des opérations
    private static final Summary executionTime = Summary.build()
            .name("calculatrice_execution_time")
            .help("Temps d'exécution des opérations")
            .labelNames("operation")
            .quantile(0.5, 0.05)   // Médiane avec une erreur de 5%
            .quantile(0.9, 0.01)   // 90e percentile avec une erreur de 1%
            .register();
            
    // Gauge pour le nombre d'utilisations de l'application
    private static final Gauge usageGauge = Gauge.build()
            .name("calculatrice_usage")
            .help("Indicateur d'utilisation de la calculatrice")
            .register();
    
    private static HTTPServer server;
    private static final CollectorRegistry registry = new CollectorRegistry();
    private static String pushGatewayUrl = "localhost:9091";
    private static final PushGateway pushGateway = new PushGateway(pushGatewayUrl);

    static {
        operationsCounter.register(registry);
        lastResultGauge.register(registry);
        errorsCounter.register(registry);
        resultHistogram.register(registry);
        executionTime.register(registry);
        usageGauge.register(registry);
    }
    
    public static void initMetrics() {
        try {
            // Démarrer un serveur HTTP sur le port 8080 pour exposer les métriques
            server = new HTTPServer(new InetSocketAddress(8080), null);
            System.out.println("Métriques disponibles à http://localhost:8080/metrics");

            // Incrémenter le gauge d'utilisation
            usageGauge.inc();
            
            // Pousser les métriques initiales vers PushGateway
            pushMetrics();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'initialisation des métriques: " + e.getMessage());
        }
    }
    
    public static void stopMetrics() {
        try {
            // Décrémenter le gauge d'utilisation
            usageGauge.dec();
            
            // Pousser les métriques finales avant de fermer
            pushMetrics();
            
            if (server != null) {
                server.stop();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'arrêt des métriques: " + e.getMessage());
        }
    }
    
    public static void recordOperation(String operation, double result) {
        // Enregistrer l'opération
        operationsCounter.labels(operation).inc();
        
        // Enregistrer le résultat
        lastResultGauge.labels(operation).set(result);
        
        // Ajouter le résultat à l'histogram
        resultHistogram.labels(operation).observe(Math.abs(result));
        
        // Simuler un temps d'exécution pour le summary
        // (dans une application réelle, on mesurerait le temps réel)
        executionTime.labels(operation).observe(new Random().nextDouble() * 0.1);
        
        // Pousser les métriques après chaque opération
        pushMetrics();
    }
    
    public static void recordError(String operation, String errorType) {
        errorsCounter.labels(operation, errorType).inc();
        pushMetrics();
    }

    public static void pushMetrics() {
        try {
            pushGateway.pushAdd(registry, "calculatrice_metrics");
            System.out.println("Métriques poussées avec succès vers PushGateway.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi des métriques: " + e.getMessage());
        }
    }

    public static void setPushGatewayUrl(String url) {
        pushGatewayUrl = url;
        pushGateway.setBaseUrl(pushGatewayUrl);
    }
}