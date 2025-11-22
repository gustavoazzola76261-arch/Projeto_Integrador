package br.com.fecaf.model;

/**
 * Representa um ônibus autônomo da frota do campus.
 * Herda de Veiculo e define um cálculo próprio de consumo.
 */
public class OnibusAutonomo extends Veiculo {

    public OnibusAutonomo(String id, double massa, Motor motor, double autonomia) {
        super(id, massa, motor, autonomia);
    }

    /**
     * Calcula o consumo energético estimado com base na massa e distância.
     * O fator k é um pouco maior devido ao peso do veículo.
     */
    @Override
    public double estimarConsumoEnergetico(double distancia, double velocidadeMedia) {
        double k = 0.00025;
        return k * massa * distancia;
    }

    @Override
    public String toString() {
        return String.format("🚌 Ônibus Autônomo %s | massa: %.1f kg | motor: %s | autonomia: %.0f m",
                id, massa, motor.toString(), autonomia);
    }
}