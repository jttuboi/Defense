/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 *
 * Texturas: http://www.tutorialsforblender3d.com/Textures/Textures_index.html
 */
package defense;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;


public class Floor {
    //mapa do jogo
    private Map map;
    //nome do mapa
    private String name_map;
    //texturas para serem alocadas de acordo que o mapa pedir
    private ArrayList<Texture> textures;
    private ArrayList<Integer> number_textures;


    public Floor() {
        textures = new ArrayList<Texture>();
        number_textures = new ArrayList<Integer>();

        map = new Map();
        name_map = "01";
    }


    public Floor(String name_map) {
        textures = new ArrayList<Texture>();
        number_textures = new ArrayList<Integer>();

        map = new Map();
        this.name_map = name_map;
    }


    public void init() {
        map.openMap(name_map);

        try {
            for (int i = 0; i < map.getTypeName().size(); i++) {
                Texture texture;
                texture = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/floor/" + map.getTypeName().get(i) + ".png"), true, TextureIO.PNG);
                texture.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
                texture.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
                textures.add(texture);
                number_textures.add(map.getTypeNumber().get(i));
            }
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem floor");
        } catch (GLException ex) {
        }
    }


    void renderer(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW); //define que a matrix eh a de modelo
        gl.glActiveTexture(GL.GL_TEXTURE0);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);

        int x = -map.getX();
        int z = -map.getY();

        //gl.glRotated(angle, 0, 1, 0);
        for (int i = 0; i < map.getLine(); i++) {
            for (int j = 0; j < map.getLine(); j++) {
                gl.glPushMatrix();

                int n = number_textures.indexOf(map.getMapType().get(i * map.getLine() + j));

                textures.get(n).bind();
                textures.get(n).enable();

                gl.glTranslated(x * map.getSizeTile(), 0, z * map.getSizeTile());
                gl.glRotated(-90, 1, 0, 0);
                gl.glRotated(90, 0, 0, 1);
                gl.glScaled(map.getSizeTile() / 2, map.getSizeTile() / 2, map.getSizeTile() / 2);
                gl.glBegin(GL.GL_QUADS);
                gl.glNormal3d(0, 0, 1);
                gl.glTexCoord2d(0, 0);
                gl.glVertex2d(-1, 1);
                gl.glTexCoord2d(1, 0);
                gl.glVertex2d(1, 1);
                gl.glTexCoord2d(1, 1);
                gl.glVertex2d(1, -1);
                gl.glTexCoord2d(0, 1);
                gl.glVertex2d(-1, -1);
                gl.glEnd();

                gl.glPopMatrix();

                x++;
            }
            x = -map.getX();
            z++;
        }
        gl.glPopMatrix();
    }
}
