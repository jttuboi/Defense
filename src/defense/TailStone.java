/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.util.ArrayList;


public class TailStone {
    private Stone stone;
    private int intensity;
    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private ArrayList<Double> z;


    public TailStone(Stone stone, int intensity) {
        this.stone = stone;
        this.intensity = intensity;

        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();
    }


    public void init() {
        for (int i = 0; i < intensity; i++) {
            //calcula a posicao atras da pedra com distancia 0.15
            double px = stone.getX() - stone.getVectorX() * 0.15;
            double py = stone.getY() - stone.getVectorY() * 0.15;
            double pz = stone.getZ() - stone.getVectorZ() * 0.15;

            // -0.05 < a < 0.05
            // Math.random()*intervalo + inicio
            double rx = Math.random() * 0.14 - 0.07;
            double ry = Math.random() * 0.14 - 0.07;
            double rz = Math.random() * 0.14 - 0.07;

            //atraves do random, ele muda a posicao de acordo com sua variacao de posicao em cada coordenada
            double npx = px + rx;
            double npy = py + ry;
            double npz = pz + rz;

            //coloca no vetor a posicao que sera desenhada
            x.add(Double.valueOf(npx));
            y.add(Double.valueOf(npy));
            z.add(Double.valueOf(npz));
        }
    }


    public void update() {
        //randomiza posicao em relacao a posicao e vetor da pedra
        for (int i = 0; i < intensity; i++) {
            //calcula a posicao atras da pedra com distancia 0.15
            double px = stone.getX() - stone.getVectorX() * 0.15;
            double py = stone.getY() - stone.getVectorY() * 0.15;
            double pz = stone.getZ() - stone.getVectorZ() * 0.15;

            // -0.07 < a < 0.07
            // Math.random()*intervalo + inicio
            double rx = Math.random() * 0.14 - 0.07;
            double ry = Math.random() * 0.14 - 0.07;
            double rz = Math.random() * 0.14 - 0.07;

            //atraves do random, ele muda a posicao de acordo com sua variacao de posicao em cada coordenada
            double npx = px + rx;
            double npy = py + ry;
            double npz = pz + rz;

            //coloca no vetor a posicao que sera desenhada
            x.set(i, Double.valueOf(npx));
            y.set(i, Double.valueOf(npy));
            z.set(i, Double.valueOf(npz));
        }
    }


    public int getIntensity() {
        return intensity;
    }


    public ArrayList<Double> getX() {
        return x;
    }


    public ArrayList<Double> getY() {
        return y;
    }


    public ArrayList<Double> getZ() {
        return z;
    }
}
