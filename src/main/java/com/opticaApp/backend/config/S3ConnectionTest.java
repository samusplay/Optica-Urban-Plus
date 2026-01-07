package com.opticaApp.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

@Component
public class S3ConnectionTest implements CommandLineRunner {

    private final S3Client s3Client;

    public S3ConnectionTest(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void run(String... args) {
        System.out.println("---------------------------------------------");
        System.out.println("📡 INTENTANDO CONECTAR A AWS S3...");

        try {
            // Intenta listar los buckets de tu cuenta
            s3Client.listBuckets().buckets().forEach(bucket ->
                    System.out.println("   ✅ BUCKET ENCONTRADO: " + bucket.name())
            );
            System.out.println("🚀 ¡CONEXIÓN EXITOSA!");
        } catch (Exception e) {
            System.err.println("❌ ERROR DE CONEXIÓN: " + e.getMessage());
        }
        System.out.println("---------------------------------------------");
    }
}
