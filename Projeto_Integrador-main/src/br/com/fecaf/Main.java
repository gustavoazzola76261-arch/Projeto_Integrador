package br.com.fecaf;

import br.com.fecaf.model.Motor;
import br.com.fecaf.model.OnibusAutonomo;
import br.com.fecaf.service.CalculoFisicoService;
import br.com.fecaf.service.MelhorRota5PontosService;
import br.com.fecaf.ui.SimuladorFrame;

import javax.swing.*;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n===== SISTEMA DE GERENCIAMENTO DE FROTA AUTÔNOMA =====\n");

        // ================================================================
        //                      FROTA DE ÔNIBUS
        // ================================================================

        Motor motorPadrão = new Motor("Elétrico", 5000, 0.85);

        List<OnibusAutonomo> frota = List.of(
                new OnibusAutonomo("Bus-01", 3500, motorPadrão, 20000),
                new OnibusAutonomo("Bus-02", 3800, motorPadrão, 26000),
                new OnibusAutonomo("Bus-03", 3000, motorPadrão, 15000)
        );

        // Escolher ônibus
        OnibusAutonomo onibusSelecionado = escolherOnibus(frota);

        System.out.println("\nVeículo selecionado:");
        System.out.println(onibusSelecionado + "\n");

        // ================================================================
        //                           MENU
        // ================================================================
        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1) Calcular melhor rota");
            System.out.println("2) Executar rota com cálculos físicos");
            System.out.println("3) Abrir simulador visual");
            System.out.println("4) Trocar de ônibus");
            System.out.println("5) Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> executarMelhorRota(onibusSelecionado, false);
                case 2 -> executarMelhorRota(onibusSelecionado, true);
                case 3 -> abrirSimuladorVisual();
                case 4 -> onibusSelecionado = escolherOnibus(frota);
                case 5 -> {
                    System.out.println("\nEncerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }

    }

    // ================================================================
    //                     ESCOLHER ÔNIBUS DA FROTA
    // ================================================================
    private static OnibusAutonomo escolherOnibus(List<OnibusAutonomo> frota) {
        System.out.println("===== Selecione o ônibus =====");

        for (int i = 0; i < frota.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, frota.get(i));
        }

        System.out.print("Escolha: ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha < 1 || escolha > frota.size()) {
            System.out.println("Opção inválida, selecionando Bus-01.");
            return frota.get(0);
        }

        return frota.get(escolha - 1);
    }


    // ================================================================
    //           EXECUTAR MELHOR ROTA (COM OU SEM FÍSICA)
    // ================================================================
    private static MelhorRota5PontosService.ResultadoRota executarMelhorRota(
            OnibusAutonomo onibus,
            boolean executarFisica
    ) {

        String pontoInicial = "Entrada";

        MelhorRota5PontosService rotaService = new MelhorRota5PontosService(pontoInicial);
        MelhorRota5PontosService.ResultadoRota resultado = rotaService.calcularMelhorRota();

        System.out.println("\n===== MELHOR ROTA ENCONTRADA =====");
        resultado.rota.forEach(p -> System.out.println(" → " + p));
        System.out.printf("\nDistância total: %.2f metros\n", resultado.distancia);

        // ------------------------ Autonomia ------------------------
        if (executarFisica) {
            if (resultado.distancia > onibus.getAutonomia()) {
                System.out.println("\n❌ ERRO: A rota é maior que a autonomia do veículo!");
                System.out.printf("Autonomia: %.2f m | Rota necessária: %.2f m%n",
                        onibus.getAutonomia(), resultado.distancia);
                return resultado;
            }

            executarFisicaDaRota(onibus, resultado.rota, resultado.distancia);
        }

        return resultado;
    }


    // ================================================================
    //                    CÁLCULOS FÍSICOS DA ROTA
    // ================================================================
    private static void executarFisicaDaRota(OnibusAutonomo onibus,
                                             List<String> rota,
                                             double distanciaTotalRota) {

        CalculoFisicoService calc = new CalculoFisicoService(onibus);
        double velocidadeMedia = 5.0; // m/s (~18 km/h)

        double tempoTotal = 0;
        double energiaTotal = 0;
        double distanciaTotal = 0;

        System.out.println("\n===== DETALHES DO TRAJETO =====");

        for (int i = 0; i < rota.size() - 1; i++) {
            String origem = rota.get(i);
            String destino = rota.get(i + 1);

            CalculoFisicoService.ResultadoTrecho t =
                    calc.calcularTrecho(origem, destino, velocidadeMedia);

            System.out.println("\nTrecho: " + origem + " → " + destino);
            System.out.printf("Distância: %.2f m\n", t.distancia);
            System.out.printf("Tempo: %.2f s (%.2f min)\n", t.tempo, t.tempo / 60);
            System.out.printf("Energia: %.2f J\n", t.energia);

            distanciaTotal += t.distancia;
            tempoTotal += t.tempo;
            energiaTotal += t.energia;
        }

        // ------------------------ RELATÓRIO FINAL ------------------------
        System.out.println("\n===== RELATÓRIO FINAL =====");
        System.out.printf("Distância total: %.2f m\n", distanciaTotal);
        System.out.printf("Tempo total: %.2f s (%.2f min)\n", tempoTotal, tempoTotal / 60);
        System.out.printf("Energia total consumida: %.2f J\n", energiaTotal);

        double autonomiaRestante = onibus.getAutonomia() - distanciaTotal;

        System.out.printf("Autonomia inicial: %.2f m\n", onibus.getAutonomia());
        System.out.printf("Autonomia restante: %.2f m\n", autonomiaRestante);

        if (autonomiaRestante < 0) {
            System.out.println("⚠️ O ônibus ficaria sem carga no meio da rota!");
        } else {
            System.out.println("✔️ A rota pode ser concluída com segurança.");
        }
    }


    // ================================================================
    //                     ABRIR SIMULADOR VISUAL
    // ================================================================
    private static void abrirSimuladorVisual() {

        // Coordenadas fictícias do mapa
        Map<String, int[]> coordenadas = Map.of(
                "Entrada", new int[]{100, 200},
                "LabInfo", new int[]{500, 120},
                "Biblioteca", new int[]{500, 220},
                "Campus Central", new int[]{280, 300},
                "Campus Norte", new int[]{300, 50}
        );

        MelhorRota5PontosService rotaService = new MelhorRota5PontosService("Entrada");
        MelhorRota5PontosService.ResultadoRota resultado = rotaService.calcularMelhorRota();

        JFrame janela = new JFrame("Simulador Visual de Rotas");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimuladorFrame painel = new SimuladorFrame(coordenadas, resultado.rota, resultado.distancia);
        janela.add(painel);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        System.out.println("\nSimulador visual aberto!");
    }

}
