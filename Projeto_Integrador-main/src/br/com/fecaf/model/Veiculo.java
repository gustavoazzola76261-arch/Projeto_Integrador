package br.com.fecaf.model;

/**
 * Classe base de todos os veículos autônomos.
 * Fornece estrutura comum e encapsula dados físicos e de motor.
 */
public abstract class Veiculo {

    protected String id;
    protected double massa; // kg
    protected Motor motor;
    protected double posicao;
    protected double velocidadeAtual; // m/s
    protected double autonomia;

    public Veiculo(String id, double massa, Motor motor, double autonomia) {
        this.id = id;
        this.massa = massa;
        this.motor = motor;
        this.autonomia = autonomia;
        this.posicao = 0;
        this.velocidadeAtual = 0;
    }

    // 👉 Cada veículo calcula seu consumo de forma específica
    public abstract double estimarConsumoEnergetico(double distancia, double velocidadeMedia);

    // ====== GETTERS E SETTERS ======
    public String getId() {
        return id;
    }

    public double getMassa() {
        return massa;
    }

    public Motor getMotor() {
        return motor;
    }

    public double getPosicao() {
        return posicao;
    }

    public double getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public void setVelocidadeAtual(double velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }

    public void setPosicao(double posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return String.format("%s | massa: %.1f kg | motor: %s | autonomia: %.0f m",
                id, massa, motor.getTipo(), autonomia);
    }
}
