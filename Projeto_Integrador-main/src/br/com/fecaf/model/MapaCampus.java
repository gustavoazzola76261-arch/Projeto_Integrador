package br.com.fecaf.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa um mapa fixo do campus universitário.
 *
 * Esta classe funciona como uma tabela estática de distâncias entre pontos.
 *
 * - Todas as distâncias são armazenadas em um Map estático.
 * - O método getDistancia() é estático, permitindo acesso direto:
 *   MapaCampus.getDistancia("A", "B")
 *
 * Ideal para cenários onde:
 * - O mapa não muda durante a execução
 * - Queremos ter um ponto único de referência para distâncias
 */
public class MapaCampus {

    /**
     * Armazena as distâncias entre os pontos do campus.
     *
     * A chave é formada por: "PontoA-PontoB"
     * Exemplo: "A-B"
     *
     * OBS: O método getDistancia() considera ida e volta (A→B ou B→A).
     */
    private static final Map<String, Double> distancias = new HashMap<>();

    // --------------------------------------------------------------
    // BLOCO ESTÁTICO — executado uma única vez quando a classe carrega
    // --------------------------------------------------------------
    static {
        // 5 pontos do campus interligados com distâncias únicas (> 500 m)
        distancias.put("Entrada-LabInfo", 1000.0);
        distancias.put("Entrada-Biblioteca", 980.0);
        distancias.put("Entrada-Campus Central", 580.0);
        distancias.put("Entrada-Campus Norte", 650.0);
        distancias.put("LabInfo-Biblioteca", 500.0);
        distancias.put("LabInfo-Campus Central", 750.0);
        distancias.put("LabInfo-Campus Norte", 600.0);
        distancias.put("Biblioteca-Campus Central", 600.0);
        distancias.put("Biblioteca-Campus Norte", 720.0);
        distancias.put("Campus Central-Campus Norte", 730.0);
    }

    /**
     * Retorna a distância entre dois pontos do campus.
     *
     * O método procura automaticamente em ambas direções:
     * - A→B
     * - B→A
     *
     * @param pontoA primeiro ponto
     * @param pontoB segundo ponto
     * @return distância em metros (double). Caso não exista, retorna 0.
     */
    public static double getDistancia(String pontoA, String pontoB) {
        String chaveDireta = pontoA + "-" + pontoB;
        String chaveReversa = pontoB + "-" + pontoA;
        return distancias.getOrDefault(chaveDireta, distancias.getOrDefault(chaveReversa, 0.0));
    }

    /**
     * Apenas para debugging e testes simples.
     */
    public static void imprimirMapa() {
        distancias.forEach((k, v) -> System.out.println(k + " = " + v + " m"));
    }
}
