
package br.com.fecaf.model;

/**
 * Representa um motor genérico utilizado por veículos autônomos.
 *
 * Esta classe modela características essenciais de um motor, como:
 * - Tipo (elétrico, combustão, híbrido)
 * - Potência nominal (em Watts)
 * - Eficiência (fração entre 0 e 1 que indica perdas do sistema)
 *
 * O motor participa diretamente dos cálculos físicos do veículo,
 * como força gerada e aceleração.
 */
public class Motor {

    /**
     * Tipo do motor, útil para identificação e análises futuras.
     * Ex.: "Elétrico", "Combustão", "Híbrido".
     */
    private String tipo;

    /**
     * Potência nominal do motor em Watts (W).
     * A potência representa a quantidade máxima de energia por segundo
     * que o motor é capaz de fornecer ao sistema.
     */
    private double potencia;

    /**
     * Eficiência do motor, variando de 0.0 a 1.0.
     * Indica a proporção da potência que é realmente convertida em movimento.
     * Por exemplo:
     *  - 1.0  = motor ideal (100% eficiente)
     *  - 0.80 = 80% da potência vira força útil
     *  - 0.50 = metade da energia é perdida em aquecimento ou atrito
     */
    private double eficiencia;

    /**
     * Construtor do motor.
     *
     * @param tipo Tipo do motor (elétrico, combustão etc.)
     * @param potencia Potência nominal em Watts
     * @param eficiencia Eficiência do motor (0.0 a 1.0)
     */
    public Motor(String tipo, double potencia, double eficiencia) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.eficiencia = eficiencia;
    }

    /** Retorna o tipo do motor. */
    public String getTipo() {
        return tipo;
    }

    /** Retorna a potência nominal do motor em Watts. */
    public double getPotencia() {
        return potencia;
    }

    /** Retorna a eficiência do motor (0.0 a 1.0). */
    public double getEficiencia() {
        return eficiencia;
    }

    /**
     * Calcula e retorna a força útil gerada pelo motor.
     *
     * Fórmula simplificada para simulação:
     *
     *     força = potência * eficiência
     *
     * A força será usada nos cálculos de aceleração do veículo.
     *
     * @return Força gerada pelo motor.
     */
    public double getForca() {
        return potencia * eficiencia;
    }

    /**
     * Retorna uma representação textual do motor,
     * útil para depuração e logs.
     */
    @Override
    public String toString() {
        return String.format(
                "%s (%.0f W, eficiência %.2f)",
                tipo, potencia, eficiencia
        );
    }
}
