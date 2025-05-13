package com.alura.conversor.principal;
import com.alura.conversor.modelos.MonedaExch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder().create();

        String direccion = "https://v6.exchangerate-api.com/v6/60c8589f4a6e012c24fbf277/latest/USD";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        MonedaExch miMonedaExch = gson.fromJson(json, MonedaExch.class);

        int opcion;
        do {
            System.out.println("""
                ***************************************************
                Sea bienvenido/a al conversor de Moneda =]
                
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real brasileño =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano =>> Dólar
                7) Salir
                Elija una opción valida:
                ***************************************************
                """);

            opcion = Integer.parseInt(lectura.nextLine());

            switch (opcion) {
                case 1 -> convertir(miMonedaExch, "USD", "ARS", lectura);
                case 2 -> convertir(miMonedaExch, "ARS", "USD", lectura);
                case 3 -> convertir(miMonedaExch, "USD", "BRL", lectura);
                case 4 -> convertir(miMonedaExch, "BRL", "USD", lectura);
                case 5 -> convertir(miMonedaExch, "USD", "COP", lectura);
                case 6 -> convertir(miMonedaExch, "COP", "USD", lectura);
                case 7 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 7);

        System.out.println("Programa finalizado.");
    }

    private static void convertir(MonedaExch datos, String de, String a, Scanner lectura) {
        if (!datos.getConversion_rates().containsKey(a) || !datos.getConversion_rates().containsKey(de)) {
            System.out.println("No se encontró la tasa de conversión para esas monedas.");
            return;
        }

        System.out.println("Ingrese la cantidad a convertir: ");
        double cantidad = Double.parseDouble(lectura.nextLine());

        double tasaDe = datos.getConversion_rates().get(de);
        double tasaA = datos.getConversion_rates().get(a);
        double resultado;

        if (de.equals("USD")) {
            resultado = cantidad * tasaA;
        } else if (a.equals("USD")) {
            resultado = cantidad / tasaDe;
        } else {
            // Conversión indirecta: de otra moneda a otra pasando por USD
            resultado = (cantidad / tasaDe) * tasaA;
        }

        System.out.printf("El valor %.2f %s corresponde al valor final de %.2f %s%n", cantidad, de, resultado, a);
    }
}