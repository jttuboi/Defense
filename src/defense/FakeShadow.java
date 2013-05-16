/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;


public class FakeShadow {
    //objetos
    private ArrayList<Stone> stone;
    private Player player;
    //textura
    private Texture texture;
    private Texture texture2;


    public FakeShadow(Player player, ArrayList<Stone> stone) {
        this.player = player;
        this.stone = stone;
    }


    public void init() {
        try {
            texture = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/shadow.png"), true, TextureIO.PNG);
            texture.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            texture.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }
    }


    public void renderer(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);

        gl.glActiveTexture(GL.GL_TEXTURE0);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);

        //desenha sombra do jogador
        drawCircleShadow(gl, texture, player.getX(), player.getZ(), 0.5);

        //desenha sombras das pedras
        for (int i = 0; i < stone.size(); i++) {
            drawCircleShadow(gl, texture, stone.get(i).getX(), stone.get(i).getZ(), 0.1);
        }
        gl.glPopMatrix();
    }


    public void drawCircleShadow(GL gl, Texture tex, double x, double z, double scale) {
        gl.glPushMatrix();
        tex.bind();
        tex.enable();

        gl.glTranslated(x, 0.01, z);
        gl.glRotated(90, 1, 0, 0);
        gl.glScaled(scale, scale, scale);
        gl.glBegin(GL.GL_POLYGON);
        gl.glNormal3d(0, 0, 1);
        gl.glTexCoord2d(0.15, 0.15);
        gl.glVertex2d(-0.7, -0.7);
        gl.glTexCoord2d(0.31, 0.19);
        gl.glVertex2d(-0.38, -0.92);
        gl.glTexCoord2d(0.50, 0.00);
        gl.glVertex2d(0, -1);
        gl.glTexCoord2d(0.69, 0.19);
        gl.glVertex2d(0.38, -0.92);
        gl.glTexCoord2d(0.85, 0.15);
        gl.glVertex2d(0.7, -0.7);
        gl.glTexCoord2d(0.96, 0.31);
        gl.glVertex2d(0.92, -0.38);
        gl.glTexCoord2d(1.00, 0.50);
        gl.glVertex2d(1, 0);
        gl.glTexCoord2d(0.96, 0.69);
        gl.glVertex2d(0.92, 0.38);
        gl.glTexCoord2d(0.85, 0.85);
        gl.glVertex2d(0.7, 0.7);
        gl.glTexCoord2d(0.69, 0.96);
        gl.glVertex2d(0.38, 0.92);
        gl.glTexCoord2d(0.50, 1.00);
        gl.glVertex2d(0, 1);
        gl.glTexCoord2d(0.31, 0.96);
        gl.glVertex2d(-0.38, 0.92);
        gl.glTexCoord2d(0.15, 0.85);
        gl.glVertex2d(-0.7, 0.7);
        gl.glTexCoord2d(0.19, 0.69);
        gl.glVertex2d(-0.92, 0.38);
        gl.glTexCoord2d(0.00, 0.50);
        gl.glVertex2d(-1, 0);
        gl.glTexCoord2d(0.19, 0.31);
        gl.glVertex2d(-0.92, -0.38);
        gl.glEnd();
        gl.glPopMatrix();
    }
}
