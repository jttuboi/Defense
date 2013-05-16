/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;


public class WindowController {
    //radar
    private Radar radar;
    //para conseguir a contagem das pedras
    private Collision collision;
    //vida do jogador
    private Life life;
    //contador de tempo
    private Clock clock;
    //controle de contagem das pedras
    private int count_collision;
    private int quantity_stone;
    //controle do tempo de vida
    private int count_seconds;
    //morto
    private boolean is_dead = false;


    public WindowController() {

        radar = new Radar(25, 500);
        life = new Life();
        clock = new Clock();

        count_collision = 0;
        quantity_stone = World.quantity_stone;

        count_seconds = 0;
    }


    public void init(Player player, ArrayList<Stone> list_stone, Collision collision) {
        this.collision = collision;

        radar.init(player, list_stone);
        clock.start();
    }


    public void update() {
        //atualiza radar
        radar.update();

        //atualiza as contagens
        count_collision = collision.getCountCollisionShield();
        quantity_stone = World.quantity_stone;

        //atualiza as condicoes de vida do jogador
        if (collision.getCollisionPlayer()) {
            life.decreaseLife(10);
            collision.setCollisionPlayer(false);
        }

        //atualiza a contagem de segundos
        count_seconds = clock.timePassedSec();

        if (life.isDead()) {
            is_dead = true;
        }
    }


    public void paint(Graphics g) {
        //se morreu aparece mensagem de fim de jogo
        if (life.isDead()) {
            g.setColor(Color.red);
            g.setFont(new Font("Segoe Script", Font.PLAIN, 60));
            g.drawString("Fim de Jogo", 300, 300);
            g.setColor(Color.yellow);
            g.setFont(new Font("Segoe Script", Font.PLAIN, 40));
            g.drawString("Aperte ESC para sair", 260, 350);

            g.setColor(Color.red);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
            g.drawString("Estatística", 410, 400);
            g.setColor(Color.lightGray);
            g.drawString("Pedras defendidas:", 350, 430);
            g.drawString("Pedras criadas:", 350, 460);
            g.drawString("Tempo de vida:", 350, 490);

            g.setColor(Color.red);
            g.drawString(Integer.toString(count_collision), 625, 430);
            g.drawString(Integer.toString(quantity_stone), 570, 460);
            g.drawString(Integer.toString(count_seconds)+" segundos", 565, 490);
        } else {
            //faz update
            update();

            //desenha efeito de colisao com jogador
            if (collision.getCollisionPlayer()) {
                g.drawRect(Main.WIDTHSCREEN, Main.HEIGHTSCREEN, Main.WIDTHSCREEN, Main.HEIGHTSCREEN);
            }
            //desenha o radar na tela
            radar.paint(g);

            //imprime a contagens na tela e a barra de vida
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

            g.setColor(Color.black);
            g.drawString("Pedras defendidas:", 20, 20);
            g.drawString("Pedras criadas:", 20, 40);
            g.drawString("Vida:", 20, 60);
            g.drawRect(70, 45, 101, 15);
            g.drawString("Tempo:", 20, 80);

            g.setColor(Color.blue);
            g.drawString(Integer.toString(count_collision), 198, 20);
            g.drawString(Integer.toString(quantity_stone), 165, 40);
            if ((int) life.getPercetageLife() > 20) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }
            g.fillRect(71, 46, (int) life.getPercetageLife(), 14); //tamanho maximo: 100 pixels
            g.setColor(Color.blue);
            g.drawString(Integer.toString(count_seconds) + " seg", 92, 80);
        }
    }


    public boolean isDead() {
        return is_dead;
    }


    public int getCountCollision() {
        return count_collision;
    }


    public static void main(String[] args) {
        String listaDeFontes[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < listaDeFontes.length; i++) {
            System.out.println(listaDeFontes[i]);
        }
    }
}
