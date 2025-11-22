package br.com.fecaf.model;

/**
 * Representa um carro autônomo do sistema de frotas.
 * Herda de Veiculo e implementa seu próprio cálculo de consumo energético.
 */
public class CarroAutonomo extends Veiculo {

    public CarroAutonomo(String id, double massa, Motor motor, double autonomia) {
        super(id, massa, motor, autonomia);
    }

    /**
     * Calcula o consumo energético estimado com base em:
     * - massa do veículo
     * - distância percorrida
     * - velocidade média
     */
    @Override
    public double estimarConsumoEnergetico(double distancia, double velocidadeMedia) {
        // modelo simplificado: consumo = k * massa * distancia
        double k = 0.00015;
        return k * massa * distancia;
    }

    @Override
    public String toString() {
        return String.format("🚘 Carro Autônomo %s | massa: %.1f kg | motor: %s | autonomia: %.0f m",
                id, massa, motor.toString(), autonomia);
    }
}
