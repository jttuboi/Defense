/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import com.sun.opengl.util.GLUT;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class ModelStone {
    //modelo da pedra
    private JWavefrontModel stone;


    public ModelStone() {
    }


    public void init(GLAutoDrawable drawable) {
        //load .obj
        try {
            stone = new JWavefrontModel(new File("./data/stone.obj"));
            stone.unitize();
            stone.facetNormals();
            stone.vertexNormals(90);
            //stone.dump(false);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        stone.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
    }


    public void renderer(GLAutoDrawable drawable, double x, double y, double z, TailStone tail, double scale) {
        GL gl = drawable.getGL();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);

        //desenha pedra
        gl.glPushMatrix();
        gl.glTranslated(x, y, z);
//            gl.glRotated(this.angle_z, 0, 0, 1); //eixo z
//            gl.glRotated(this.angle_y, 0, 1, 0); //eixo y
//            gl.glRotated(this.angle_x, 1, 0, 0); //eixo x
        gl.glScaled(scale, scale, scale);
        stone.draw(drawable);
        gl.glPopMatrix();

        //desenha tail
        gl.glPushMatrix();
        for (int i = 0; i < tail.getIntensity(); i++) {
            gl.glPushMatrix();

            gl.glTranslated(tail.getX().get(i).doubleValue(),
                    tail.getY().get(i).doubleValue(), tail.getZ().get(i).doubleValue());
            glut.glutSolidSphere(0.005, 5, 5);
            gl.glPopMatrix();
        }
        gl.glPopMatrix();

        gl.glPopMatrix();


    }
}
