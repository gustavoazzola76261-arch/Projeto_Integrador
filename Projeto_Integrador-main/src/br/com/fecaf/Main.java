package br.com.fecaf;

import br.com.fecaf.model.MapaCampus;
import br.com.fecaf.model.Motor;
import br.com.fecaf.model.OnibusAutonomo;
import br.com.fecaf.service.CalculoFisicoService;
import br.com.fecaf.service.MelhorRota5PontosService;
import br.com.fecaf.ui.SimuladorFrame;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n===== SISTEMA DE GERENCIAMENTO DE FROTA AUTÔNOMA =====\n");

        // MOTOR + ÔNIBUS
        Motor motor = new Motor("Elétrico", 5000, 0.85);
        OnibusAutonomo onibus = new OnibusAutonomo(
                "Bus-01",
                3500,
                motor,
                20000
        );

        System.out.println("Veículo carregado:");
        System.out.println(onibus + "\n");

        // PONTO INICIAL DA ROTA
        String pontoInicial = "Entrada";

        // SERVIÇO DE ROTA IDEAL
        MelhorRota5PontosService rotaService = new MelhorRota5PontosService(pontoInicial);
        MelhorRota5PontosService.ResultadoRota resultado =
                rotaService.calcularMelhorRota();

        System.out.println("===== MELHOR ROTA ENCONTRADA =====");
        resultado.rota.forEach(p -> System.out.println(" • " + p));
        System.out.printf("\nDistância total: %.2f metros\n\n", resultado.distancia);

        // CÁLCULOS FÍSICOS
        CalculoFisicoService calc = new CalculoFisicoService(onibus);

        double velocidadeMedia = 5.0; // m/s (~18 km/h)

        System.out.println("===== DETALHES DO TRAJETO =====");

        double distanciaTotal = 0;
        double tempoTotal = 0;
        double energiaTotal = 0;

        for (int i = 0; i < resultado.rota.size() - 1; i++) {

            String origem = resultado.rota.get(i);
            String destino = resultado.rota.get(i + 1);

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

        System.out.println("\n===== RESUMO FINAL DA VIAGEM =====");
        System.out.printf("Distância total: %.2f m\n", distanciaTotal);
        System.out.printf("Tempo total: %.2f s (%.2f min)\n", tempoTotal, tempoTotal / 60);
        System.out.printf("Energia total consumida: %.2f J\n", energiaTotal);

        System.out.println("\n===== ABRINDO SIMULADOR VISUAL =====");

        // ==============================
        // NOVO BLOCO: COORDENADAS DO MAPA
        // ==============================
        Map<String, int[]> coordenadas = Map.of(
                "Entrada", new int[]{100, 200},
                "LabInfo", new int[]{500, 120},
                "Biblioteca", new int[]{500, 220},
                "Campus Central", new int[]{280, 300},
                "Campus Norte", new int[]{300, 50}
        );

        // ABRINDO INTERFACE
        JFrame janela = new JFrame("Simulador Visual de Rotas");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimuladorFrame painel = new SimuladorFrame(
                coordenadas,           // coordenadas visuais no mapa
                resultado.rota,        // rota calculada
                resultado.distancia    // distância total oficial
        );

        janela.add(painel);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        System.out.println("\n===== FIM DA EXECUÇÃO =====");
    }
}