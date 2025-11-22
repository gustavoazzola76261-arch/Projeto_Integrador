package br.com.fecaf.ui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimuladorFrame extends JPanel {

    private final Map<String, int[]> coordenadas;
    private final List<String> rota;
    private final double distanciaTotal;

    // Mapa interno para armazenar distâncias dos trechos da rota
    private final Map<String, Double> distanciasTrechos = new HashMap<>();

    // Posição configurável do texto da distância total
    private int distanciaTotalX = 220;
    private int distanciaTotalY = 380;

    public SimuladorFrame(Map<String, int[]> coordenadas,
                          List<String> rota,
                          double distanciaTotal) {

        this.coordenadas = coordenadas;
        this.rota = rota;
        this.distanciaTotal = distanciaTotal;

        calcularDistanciasTrechos();

        setPreferredSize(new Dimension(700, 450));
        setBackground(Color.WHITE);
    }

    /**
     * Calcula as distâncias entre cada par consecutivo usando o MapaCampus
     * OBS: NÃO altera nada na classe MapaCampus
     */
    private void calcularDistanciasTrechos() {
        for (int i = 0; i < rota.size() - 1; i++) {
            String p1 = rota.get(i);
            String p2 = rota.get(i + 1);

            String chave = p1 + "-" + p2;

            double dist = br.com.fecaf.model.MapaCampus.getDistancia(p1, p2);

            distanciasTrechos.put(chave, dist);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        desenharRota(g2);
        desenharPontos(g2);
        desenharDistanciaTotal(g2);
    }

    /* ====================== DESENHO DOS PONTOS ====================== */

    private void desenharPontos(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Arial", Font.BOLD, 13));

        for (Map.Entry<String, int[]> entry : coordenadas.entrySet()) {
            String nome = entry.getKey();
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];

            g2.fillOval(x - 6, y - 6, 12, 12);
            g2.drawString(nome, x + 12, y);
        }
    }

    /* ====================== DESENHAR ROTAS E TRECHOS ====================== */

    private void desenharRota(Graphics2D g2) {

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        for (int i = 0; i < rota.size() - 1; i++) {

            String p1 = rota.get(i);
            String p2 = rota.get(i + 1);

            int[] c1 = coordenadas.get(p1);
            int[] c2 = coordenadas.get(p2);

            int x1 = c1[0];
            int y1 = c1[1];
            int x2 = c2[0];
            int y2 = c2[1];

            g2.drawLine(x1, y1, x2, y2);

            // chave do trecho
            String chave = p1 + "-" + p2;
            double dist = distanciasTrechos.get(chave);

            // posição do texto no meio da linha
            int meioX = (x1 + x2) / 2;
            int meioY = (y1 + y2) / 2;

            g2.drawString(dist + "m", meioX + 5, meioY - 5);
        }
    }

    /* ====================== TEXTO DA DISTÂNCIA TOTAL ====================== */

    private void desenharDistanciaTotal(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString("Distância total: " + distanciaTotal + " m",
                distanciaTotalX, distanciaTotalY);
    }
}
