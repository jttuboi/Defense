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
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;


public class Skybox {
    //texturas
    private Texture top;
    private Texture bottom;
    private Texture left;
    private Texture right;
    private Texture front;
    private Texture back;
    //tamanho da skybox
    private double size;
    //angulo de rotacao de acordo com o jogador
    private double angle_yaw;
    private double angle_look;
    //nro da skybox que deseja usar
    private String number;
    //posicao para arrumar caso camera 2 seja ativada
    private double c2_x;
    private double c2_y;
    private double c2_z;
    private double c2_yaw;
    private double c2_pitch;


    public Skybox() {
        this.size = 500;
        this.angle_yaw = 0;
        this.angle_look = 0;

        this.number = "01";

        this.c2_x = 0;
        this.c2_y = 0;
        this.c2_z = 0;
        this.c2_yaw = 0;
        this.c2_pitch = 0;
    }


    public Skybox(double size, double angleYaw, String number) {
        this.size = size;
        this.angle_yaw = angleYaw;

        switch (Defense.position_camera) {
            case Defense.BACK:
                this.angle_look = 0;
                break;
            case Defense.LEFT:
                this.angle_look = 1.570796326794897;
                break;
            case Defense.FRONT:
                this.angle_look = 3.141592653589793;
                break;
            case Defense.RIGHT:
                this.angle_look = 4.71238898038469;
                break;
        }

        this.number = number;

        this.c2_x = 0;
        this.c2_y = 0;
        this.c2_z = 0;
        this.c2_yaw = 0;
        this.c2_pitch = 0;
    }


    public void init() {
        try {
            top = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "top.png"), true, TextureIO.PNG);
            top.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            top.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }

        try {
            bottom = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "bottom.png"), true, TextureIO.PNG);
            bottom.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            bottom.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }

        try {
            left = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "left.png"), true, TextureIO.PNG);
            left.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            left.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }

        try {
            right = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "right.png"), true, TextureIO.PNG);
            right.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            right.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }

        try {
            front = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "front.png"), true, TextureIO.PNG);
            front.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            front.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }

        try {
            back = TextureIO.newTexture(getClass().getClassLoader().getResourceAsStream("./image/skybox/" + number + "back.png"), true, TextureIO.PNG);
            back.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            back.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (IOException ex) {
            System.out.println("Erro ao ler imagem");
        } catch (GLException ex) {
        }
    }


    void renderer(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);

        //arruma a visao da camera2 de acordo com jogador
        gl.glTranslated(c2_x, c2_y, c2_z);
        gl.glRotated(Math.toDegrees(c2_pitch), 1, 0, 0);
        gl.glRotated(Math.toDegrees(c2_yaw), 0, 1, 0);
        gl.glRotated(Math.toDegrees(c2_pitch), 0, 0, 1);

        gl.glPushMatrix();

        //rotaciona todo skybox convertendo pra graus de acordo com o jogador
        gl.glRotated(Math.toDegrees(angle_yaw + angle_look), 0, 1, 0);

        gl.glActiveTexture(GL.GL_TEXTURE0);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);

        //desenha a parte da frente
        gl.glPushMatrix();
        front.bind();
        front.enable();

        gl.glTranslated(0, 0, -size);
        gl.glRotated(0, 0, 1, 0);
        gl.glScaled(size, size, size);
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

        //desenha a parte de tras
        gl.glPushMatrix();
        back.bind();
        back.enable();

        gl.glTranslated(0, 0, size);
        gl.glRotated(180, 0, 1, 0);
        gl.glScaled(size, size, size);
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

        //desenha o lado esquerdo
        gl.glPushMatrix();
        left.bind();
        left.enable();

        gl.glTranslated(-size, 0, 0);
        gl.glRotated(90, 0, 1, 0);
        gl.glScaled(size, size, size);
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

        //desenha o lado direito
        gl.glPushMatrix();
        right.bind();
        right.enable();

        gl.glTranslated(size, 0, 0);
        gl.glRotated(-90, 0, 1, 0);
        gl.glScaled(size, size, size);
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

        //desenha a parte de cima
        gl.glPushMatrix();
        top.bind();
        top.enable();

        gl.glTranslated(0, size, 0);
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(-90, 0, 0, 1);
        gl.glScaled(size, size, size);
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

        //desenha a parte de baixo
//            gl.glPushMatrix();
//                bottom.bind();
//                bottom.enable();
//
//                gl.glTranslated(0, -size, 0);
//                gl.glRotated(-90, 1, 0, 0);
//                gl.glRotated(90, 0, 0, 1);
//                gl.glScaled(size, size, size);
//                gl.glBegin(GL.GL_QUADS);
//                    gl.glNormal3d(0, 0, 1);
//                    gl.glTexCoord2d(0, 0); gl.glVertex2d(-1,  1);
//                    gl.glTexCoord2d(1, 0); gl.glVertex2d( 1,  1);
//                    gl.glTexCoord2d(1, 1); gl.glVertex2d( 1, -1);
//                    gl.glTexCoord2d(0, 1); gl.glVertex2d(-1, -1);
//                gl.glEnd();
//            gl.glPopMatrix();

        gl.glPopMatrix();
        gl.glPopMatrix();
    }


    public void update(double x, double y, double z, double yaw, double pitch) {
        switch (Defense.current_camera) {
            case 1:
                c2_x = 0;
                c2_y = 0;
                c2_z = 0;
                c2_yaw = 0;
                c2_pitch = 0;
                switch (Defense.position_camera) {
                    case Defense.BACK:
                        angle_look = 0;
                        break;
                    case Defense.LEFT:
                        angle_look = 1.570796326794897;
                        break;
                    case Defense.FRONT:
                        angle_look = 3.141592653589793;
                        break;
                    case Defense.RIGHT:
                        angle_look = 4.71238898038469;
                        break;
                }
                break;
            case 2:
                angle_look = 0;
                angle_yaw = 0;
                c2_x = -x;
                c2_y = -y;
                c2_z = -z;
                c2_yaw = yaw + 1.570796326794897; //90
                c2_pitch = pitch;
                break;
        }
    }


    public void setAngleYaw(double angleYaw) {
        this.angle_yaw = angleYaw;
    }


    public double getAngleYaw() {
        return this.angle_yaw;
    }


    public void rotateYaw(double angleYaw, String direction) {
        //Rotaciona a skybox de acordo com o angulo em radianos
        if (direction.equals("RIGHT")) {
            this.angle_yaw += angleYaw;
        } else if (direction.equals("LEFT")) {
            this.angle_yaw -= angleYaw;
        }
    }
}
