package br.com.fecaf.service;

import br.com.fecaf.model.MapaCampus;
import java.util.*;

/**
 * Serviço para calcular a melhor rota entre 5 pontos fixos do campus,
 * iniciando de um ponto inicial definido.
 */
public class MelhorRota5PontosService {

    private final String pontoInicial;

    public MelhorRota5PontosService(String pontoInicial) {
        this.pontoInicial = pontoInicial;
    }

    /**
     * Calcula a melhor rota possível, fixando o ponto inicial.
     *
     * @return ResultadoRota contendo a rota e a distância total.
     */
    public ResultadoRota calcularMelhorRota() {
        // Pontos do campus fixos, pois o mapa é estático
        List<String> pontos = new ArrayList<>(Arrays.asList(
                "Entrada", "LabInfo", "Biblioteca", "Campus Central", "Campus Norte"
        ));

        // Remove o ponto inicial
        pontos.remove(pontoInicial);

        List<String> melhorRota = null;
        double menorDistancia = Double.MAX_VALUE;

        // Permutações dos outros 4 pontos
        List<List<String>> permutacoes = gerarPermutacoes(pontos);

        for (List<String> rota : permutacoes) {
            // Rota completa iniciando no ponto inicial
            List<String> rotaCompleta = new ArrayList<>();
            rotaCompleta.add(pontoInicial);
            rotaCompleta.addAll(rota);

            double distancia = calcularDistanciaTotal(rotaCompleta);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                melhorRota = rotaCompleta;
            }
        }

        return new ResultadoRota(melhorRota, menorDistancia);
    }

    /** Calcula a distância total de uma rota completa */
    private double calcularDistanciaTotal(List<String> rota) {
        double total = 0;
        for (int i = 0; i < rota.size() - 1; i++) {
            String origem = rota.get(i);
            String destino = rota.get(i + 1);
            total += MapaCampus.getDistancia(origem, destino);
        }
        return total;
    }

    /** Gera todas as permutações de uma lista */
    private List<List<String>> gerarPermutacoes(List<String> lista) {
        List<List<String>> resultado = new ArrayList<>();
        permutar(lista, 0, resultado);
        return resultado;
    }

    private void permutar(List<String> arr, int index, List<List<String>> resultado) {
        if (index == arr.size()) {
            resultado.add(new ArrayList<>(arr));
        } else {
            for (int i = index; i < arr.size(); i++) {
                Collections.swap(arr, i, index);
                permutar(arr, index + 1, resultado);
                Collections.swap(arr, i, index);
            }
        }
    }

    /** Classe para armazenar o resultado da melhor rota */
    public static class ResultadoRota {
        public final List<String> rota;
        public final double distancia;

        public ResultadoRota(List<String> rota, double distancia) {
            this.rota = rota;
            this.distancia = distancia;
        }
    }
}
