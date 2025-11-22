package br.com.fecaf.service;

import br.com.fecaf.model.MapaCampus;
import br.com.fecaf.model.OnibusAutonomo;

/**
 * Serviço responsável por fazer os cálculos físicos da viagem:
 * - tempo
 * - velocidade
 * - energia consumida
 *
 * A ideia é calcular trecho por trecho entre os pontos da rota.
 */
public class CalculoFisicoService {

    private final OnibusAutonomo onibus;

    public CalculoFisicoService(OnibusAutonomo onibus) {
        this.onibus = onibus;
    }

    /**
     * Calcula o tempo gasto em um trecho (em segundos).
     *
     * tempo = distancia / velocidade
     */
    public double calcularTempo(double distancia, double velocidadeMedia) {
        return distancia / velocidadeMedia;
    }

    /**
     * Calcula o consumo energético usando o método do próprio ônibus.
     */
    public double calcularEnergia(double distancia, double velocidadeMedia) {
        return onibus.estimarConsumoEnergetico(distancia, velocidadeMedia);
    }

    /**
     * Gera um relatório completo do trecho com:
     * - distância
     * - velocidade
     * - tempo
     * - energia consumida
     */
    public void relatorioTrecho(String origem, String destino, double velocidadeMedia) {

        double distancia = MapaCampus.getDistancia(origem, destino);
        double tempo = calcularTempo(distancia, velocidadeMedia);
        double energia = calcularEnergia(distancia, velocidadeMedia);

        System.out.println("\n📍 Trecho: " + origem + " → " + destino);
        System.out.printf("Distância: %.2f m\n", distancia);
        System.out.printf("Velocidade média: %.2f m/s\n", velocidadeMedia);
        System.out.printf("Tempo estimado: %.2f s (%.2f min)\n",
                tempo, tempo / 60.0);
        System.out.printf("Energia estimada: %.2f J\n", energia);
    }
    public ResultadoTrecho calcularTrecho(String origem, String destino, double velocidadeMedia) {

        double distancia = MapaCampus.getDistancia(origem, destino);
        double tempo = calcularTempo(distancia, velocidadeMedia);
        double energia = calcularEnergia(distancia, velocidadeMedia);

        return new ResultadoTrecho(distancia, tempo, energia);
    }

    public static class ResultadoTrecho {
        public final double distancia;
        public final double tempo;
        public final double energia;

        public ResultadoTrecho(double distancia, double tempo, double energia) {
            this.distancia = distancia;
            this.tempo = tempo;
            this.energia = energia;
        }
    }

}