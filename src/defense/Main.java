/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.io.IOException;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;


public class Main {
    //largura e altura da tela
    public final static int WIDTHSCREEN = 1024;
    public final static int HEIGHTSCREEN = 768;
    //teclado e mouse funcionando atraves de eventos
    public static InputController input_controller = new InputController();


    public static void main(String[] args) throws IOException {
        //cria uma janela e adiciona o painel
        JFrame frame = new JFrame("Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Main.WIDTHSCREEN, Main.HEIGHTSCREEN);
        frame.addKeyListener(Main.input_controller);
        frame.addMouseListener(Main.input_controller);
        frame.addMouseMotionListener(Main.input_controller);

        //creating the projection panel
        GLCapabilities glcaps = new GLCapabilities();
        glcaps.setAccumBlueBits(16);
        glcaps.setAccumGreenBits(16);
        glcaps.setAccumRedBits(16);
        glcaps.setDoubleBuffered(true);
        glcaps.setHardwareAccelerated(true);

        //cria o jogo e adiciona no frame
        Defense def = new Defense(glcaps);
        Defense.panel.addGLEventListener(def);
        Defense.panel.setOpaque(true);
        frame.add(Defense.panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
