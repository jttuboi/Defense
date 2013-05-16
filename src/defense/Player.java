/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class Player {
    //posicao do jogador
    private double player_x;
    private double player_y;
    private double player_z;
    //posicao do escudo
    private double shield_x;
    private double shield_y;
    private double shield_z;
    //posicao da visao do jogador
    private double look_x;
    private double look_y;
    private double look_z;
    //posicao da visao lateral do jogador (lado direito)
    private double look_side_x;
    private double look_side_z;
    private double look_side_y;
    //distancia de visao do jogador
    private double distance_look;
    //distancia do jogador com o escudo
    private double distance_shield;
    //angulo de visao do jogador
    private double pitch;
    private double yaw;
    //modelos do jogador e do escudo
    private JWavefrontModel player;
    private JWavefrontModel shield;


    public Player() {
        //posicao inicial
        player_x = 0;
        player_y = 1;
        player_z = 0;

        //seta posicao escudo
        shield_x = 0;
        shield_y = 0.8;
        shield_z = -0.6;

        //distancia do look
        distance_look = 1;

        //distancia do escudo com player
        distance_shield = 0.6;

        //arrumando look inicial
        look_x = 0;
        look_y = 1;
        look_z = -distance_look;

        //arrumando o look lateral inicial
        look_side_x = distance_look;
        look_side_y = 1;
        look_side_z = 0;

        //arrumando angulacao inicial
        pitch = 0;
        yaw = 0;
    }


    public void init(GLAutoDrawable drawable) {
        try {
            player = new JWavefrontModel(new File("./data/player2.obj"));
            player.unitize();
            player.facetNormals();
            player.vertexNormals(90);
            //player.dump(false);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        player.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);


        try {
            shield = new JWavefrontModel(new File("./data/shield2.obj"));
            shield.unitize();
            shield.facetNormals();
            shield.vertexNormals(90);
            //shield.dump(false);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        shield.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
    }


    public void renderer(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //desenha jogador
        gl.glPushMatrix();
        gl.glTranslated(this.player_x, this.player_y, this.player_z);
        //gl.glRotated(this.angle_z, 0, 0, 1); //eixo player_z
        gl.glRotated(Math.toDegrees(yaw), 0, 1, 0); //eixo player_y
        //gl.glRotated(this.angle_x, 1, 0, 0); //eixo player_x
        player.draw(drawable);
        gl.glPopMatrix();

        //desenha escudo (escudo está 0, 0.2, 0.6)
        gl.glPushMatrix();
        gl.glTranslated(this.shield_x, this.shield_y, this.shield_z);
        //gl.glRotated(this.angle_z, 0, 0, 1); //eixo player_z
        gl.glRotated(Math.toDegrees(yaw), 0, 1, 0); //eixo player_y
        //gl.glRotated(this.angle_x, 1, 0, 0); //eixo player_x
        gl.glScaled(0.5, 0.5, 0.5);
        shield.draw(drawable);
        gl.glPopMatrix();

//        GLUT glut = new GLUT();
//
//        //desenha visao do jogador
//        gl.glPushMatrix();
//            gl.glTranslated(look_x, look_y, look_z);
//            glut.glutWireSphere(0.05, 20, 20);
//        gl.glPopMatrix();
//
//        //desenha visao lateral do jogador
//        gl.glPushMatrix();
//            gl.glTranslated(look_side_x, look_side_y, look_side_z);
//            glut.glutWireSphere(0.05, 20, 20);
//        gl.glPopMatrix();
    }


    public void setPosition(double x, double y, double z) {
        this.player_x = x;
        this.player_y = y;
        this.player_z = z;
    }


    public void moveFrontBack(double distance) {
        //atualiza posicao do jogador
        player_x = player_x - (distance * Math.sin(yaw));
        player_z = player_z - (distance * Math.cos(yaw));

        //atualiza visao do jogador
        look_x = player_x - ((distance_look) * Math.sin(yaw));
        look_z = player_z - ((distance_look) * Math.cos(yaw));

        //visao lateral do jogador
        look_side_x = player_x - (distance_look * Math.sin(yaw - 1.570796326794897)); //yaw - 90 graus
        look_side_z = player_z - (distance_look * Math.cos(yaw - 1.570796326794897)); //yaw - 90 graus

        //atualiza posicao do escudo
        atualizeShield();
    }


    public void moveLeftRight(double distance) {
        //atualiza posicao do jogador
        player_x = player_x - (distance * Math.sin(yaw - 1.570796326794897));
        player_z = player_z - (distance * Math.cos(yaw - 1.570796326794897));

        //atualiza visao do jogador
        look_x = player_x - ((distance_look) * Math.sin(yaw));
        look_z = player_z - ((distance_look) * Math.cos(yaw));

        //visao lateral do jogador
        look_side_x = player_x - (distance_look * Math.sin(yaw - 1.570796326794897)); //yaw - 90 graus
        look_side_z = player_z - (distance_look * Math.cos(yaw - 1.570796326794897)); //yaw - 90 graus

        //atualiza posicao do escudo
        atualizeShield();
    }


    public void rotateLeft(double amount) {
        //muda o angulo do jogador para a esquerda
        yaw += amount;
        //System.out.println(yaw);

        //arruma a visao do jogador dado que a posicao permanece o mesmo
        look_x = player_x - (distance_look * Math.sin(yaw));
        look_z = player_z - (distance_look * Math.cos(yaw));

        //visao lateral do jogador
        look_side_x = player_x - (distance_look * Math.sin(yaw - 1.570796326794897)); //yaw - 90 graus
        look_side_z = player_z - (distance_look * Math.cos(yaw - 1.570796326794897)); //yaw - 90 graus

        //atualiza posicao do escudo
        atualizeShield();
    }


    public void rotateRight(double amount) {
        //muda o angulo do jogador para a esquerda
        yaw -= amount;

        //arruma a visao do jogador dado que a posicao permanece o mesmo
        look_x = player_x - (distance_look * Math.sin(yaw));
        look_z = player_z - (distance_look * Math.cos(yaw));

        //visao lateral do jogador
        look_side_x = player_x - (distance_look * Math.sin(yaw - 1.570796326794897)); //yaw - 90 graus
        look_side_z = player_z - (distance_look * Math.cos(yaw - 1.570796326794897)); //yaw - 90 graus

        //atualiza posicao do escudo
        atualizeShield();
    }


    public double getX() {
        return this.player_x;
    }


    public double getY() {
        return this.player_y;
    }


    public double getZ() {
        return this.player_z;
    }


    public double getShieldX() {
        return this.shield_x;
    }


    public double getShieldY() {
        return this.shield_y;
    }


    public double getShieldZ() {
        return this.shield_z;
    }


    public double getLookX() {
        return this.look_x;
    }


    public double getLookY() {
        return this.look_y;
    }


    public double getLookZ() {
        return this.look_z;
    }


    public double getYaw() {
        return this.yaw;
    }


    public double getVectorX() {
        //(xL - player_x)/n
        return ((look_x - player_x) / n());
    }


    public double getVectorY() {
        //(yL - player_y)/n
        return ((look_y - player_y) / n());
    }


    public double getVectorZ() {
        //(zL - player_z)/n
        return ((look_z - player_z) / n());
    }


    private double n() {
        //(xL - player_x)^2, (yL - player_y)^2, (zL - player_z)^2
        double x2 = Math.pow(look_x - player_x, 2);
        double y2 = Math.pow(look_y - player_y, 2);
        double z2 = Math.pow(look_z - player_z, 2);

        //raiz((xL - player_x)^2 + (yL - player_y)^2 + (zL - player_z)^2), jah retorna positivo pq tira a raiz
        return Math.sqrt(x2 + y2 + z2);
    }


    public double getVectorSideX() {
        //(xsL - player_x)/n
        return ((look_side_x - player_x) / n_side());
    }


    public double getVectorSideY() {
        //(ysL - player_y)/n
        return ((look_side_y - player_y) / n_side());
    }


    public double getVectorSideZ() {
        //(zsL - player_z)/n
        return ((look_side_z - player_z) / n_side());
    }


    private double n_side() {
        //(xsL - player_x)^2, (ysL - player_y)^2, (zsL - player_z)^2
        double x2 = Math.pow(look_side_x - player_x, 2);
        double y2 = Math.pow(look_side_y - player_y, 2);
        double z2 = Math.pow(look_side_z - player_z, 2);

        //raiz((xsL - player_x)^2 + (ysL - player_y)^2 + (zsL - player_z)^2), jah retorna positivo pq tira a raiz
        return Math.sqrt(x2 + y2 + z2);
    }


    private void atualizeShield() {
        //corrige posicao do escudo
        shield_x = player_x + distance_shield * getVectorX();
        shield_z = player_z + distance_shield * getVectorZ();
    }
}
