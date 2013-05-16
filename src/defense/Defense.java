/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class Defense extends EventHandler implements GLEventListener, ActionListener {
    //posicoes da camera em relacao ao jogador
    public final static int DEFAULT = 0;
    public final static int FRONT = 1;
    public final static int BACK = 2;
    public final static int RIGHT = 3;
    public final static int LEFT = 4;
    //opengl
    public static GLJPanel panel;
    private GLU glu = new GLU();
    //camera
    public static Camera camera1;
    public static Camera camera2;
    public static int current_camera;
    public static int position_camera;
    //objetos
    private World world;
    private Skybox skybox;
    //timer para controle de FPS
    public Timer timer;
    private WindowController window_controller;
    //booleans de controle
    private boolean rotate_skybox_left = false;
    private boolean rotate_skybox_right = false;
    private final static int MOUSE_LEFT = -1;
    private final static int MOUSE_STATIC = 0;
    private final static int MOUSE_RIGHT = 1;
    private static int skybox_move_direction_mouse = MOUSE_STATIC;


    public Defense(GLCapabilities glcaps) {
        panel = new GLJPanel(glcaps);

        //camera
        camera1 = new Camera(0, 2, 3, 0, 0, 10, 3);  //posicao inicial da camera é ajustada
        camera1.setYaw(4.71238898038469f);  //camera vira 270 graus para virar para frente
        camera1.setPitch(-0.1745329251994329f);  // camera inclina 10 graus para baixo
        camera1.look(10);  //ajusta a camera pra posicao certa

        camera2 = new Camera();
        camera2.setYaw(0);
        camera2.setPitch(0);
        camera2.look(10);

        //camera 1 ativada
        current_camera = 1;
        position_camera = BACK;

        //ajusta tempo e habilita funcao actionperformed
        timer = new Timer(17, this); //33 = 30 fps  17 = 60 fps

        window_controller = new WindowController();

        //Adiciona a classe na lista de classes que deve receber os eventos
        Main.input_controller.addEventHandler(this);
    }


    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();

        //luz
        lighting(drawable);

        //desativa vsync
        gl.setSwapInterval(0);

        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClearDepth(1.0f);  // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);  // Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);  // The Type Of Depth Test To Do
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  // Really Nice Perspective Calculations

        //cria o skybox
        skybox = new Skybox(300, 0, "01");
        skybox.init();

        //cria o mundo
        world = new World();
        world.init(drawable);

        //desenha radar, contadores
        window_controller.init(world.getPlayer(), world.getListStone(), world.getCollision());

        //comeca o loop
        timer.start();
    }


    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); //backgroung color
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); //limpa o buffer

        gl.glViewport(0, 0, Main.WIDTHSCREEN, Main.HEIGHTSCREEN);


        //pesperctive view
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(70.0, (double) Main.WIDTHSCREEN / (double) Main.HEIGHTSCREEN, 1, 1000);


        //model view
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        //desabilita luz
        gl.glDisable(GL.GL_LIGHTING);

        //skybox
        skybox.renderer(drawable);

        //habilita luz
        gl.glEnable(GL.GL_LIGHTING);

        //camera
        if (current_camera == 1) {
            glu.gluLookAt(camera1.getXPos(), camera1.getYPos(), camera1.getZPos(),
                    camera1.getXLPos(), camera1.getYLPos(), camera1.getZLPos(), 0.0, 1.0, 0.0);
        }
        if (current_camera == 2) {
            glu.gluLookAt(camera2.getXPos(), camera2.getYPos(), camera2.getZPos(),
                    camera2.getXLPos(), camera2.getYLPos(), camera2.getZLPos(), 0.0, 1.0, 0.0);
        }

        //world
        world.renderer(drawable);

        gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 1, 100.0);
        gl.glFlush(); //forca o desenho das primitivas
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        width = drawable.getWidth();
        height = drawable.getHeight();

        gl.glViewport(10, 10, width - 20, height - 20);


        //perspective view
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(70.0, (double) width / (double) height, 1, 1000);


        //model view
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        if (current_camera == 1) {
            glu.gluLookAt(camera1.getXPos(), camera1.getYPos(), camera1.getZPos(),
                    camera1.getXLPos(), camera1.getYLPos(), camera1.getZLPos(), 0.0, 1.0, 0.0);
        }
        if (current_camera == 2) {
            glu.gluLookAt(camera2.getXPos(), camera2.getYPos(), camera2.getZPos(),
                    camera2.getXLPos(), camera2.getYLPos(), camera2.getZLPos(), 0.0, 1.0, 0.0);
        }
    }


    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }


    private void lighting(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //define a posicao e parametros da luz 0
        float position[] = {0.0f, 2.0f, 0.0f, 1.0f};
        float[] luzAmbiente = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] luzDifusa = {0.75f, 0.75f, 0.75f, 1.0f};
        float[] luzEspecular = {0.7f, 0.7f, 0.7f, 1.0f};

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);

        //ativando luz ambiente global
        float global_ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, global_ambient, 0);

        //define as propriedades de reflexao da superficie
        float diffuse[] = {0.5f, 0.5f, 0.5f, 1.0f};
        float specular[] = {0.9f, 0.9f, 0.9f, 1.0f};
        float shininess = 65.0f;
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininess);

        //ativa cor no material
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);

        //ativa a iluminacao
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
    }


    @Override
    public void doEvent(String event) {
        //sai do jogo
        if (event.equals("ESCAPE_PRESS")) {
            System.exit(1);
        }

        //ativa camera 1
        if (event.equals("F1_PRESS")) {
            current_camera = 1;
            position_camera = BACK;
        }
        if (event.equals("F2_PRESS")) {
            current_camera = 1;
            position_camera = FRONT;
        }
        if (event.equals("F3_PRESS")) {
            current_camera = 1;
            position_camera = RIGHT;
        }
        if (event.equals("F4_PRESS")) {
            current_camera = 1;
            position_camera = LEFT;
        }

        //ativa camera 2 (bugado em relacao ao skybox e andar pela tela)
//        if (event.equals("F5_PRESS")) {
//            current_camera = 2;
//            position_camera = DEFAULT;
//
//            //reinicia posicao da camera
//            camera2.updatePosition(0, 10, 0); //posicao da camera é ajustada
//            camera2.setYaw(4.71238898038469);//5.497787143782138f);  //camera vira 315 graus para virar para frente
//            camera2.setPitch(0);//-0.7853981633974483f);  // camera inclina 45 graus para baixo
//            camera2.look(10);  //ajusta a camera pra posicao certa
//        }

        //rotaciona o skybox junto com a camera1
        if (event.equals("Q_PRESS")) {
            rotate_skybox_left = true;
        }
        if (event.equals("Q_RELEASE")) {
            rotate_skybox_left = false;
        }
        if (event.equals("E_PRESS")) {
            rotate_skybox_right = true;
        }
        if (event.equals("E_RELEASE")) {
            rotate_skybox_right = false;
        }
        if (event.equals("MOVE_X_LEFT")) {
            skybox_move_direction_mouse = MOUSE_LEFT;
        }
        if (event.equals("MOVE_X_RIGHT")) {
            skybox_move_direction_mouse = MOUSE_RIGHT;
        }
        if (event.equals("MOUSE_DEAD_ZONE")) {
            skybox_move_direction_mouse = MOUSE_STATIC;
        }
    }


    public void actionPerformed(ActionEvent e) {
        switch (current_camera) {
            case 1: {
                if (rotate_skybox_left) {
                    //quando rotaciona o jogador, tbm rotaciona a skybox
                    skybox.rotateYaw(0.1, "LEFT");
                }
                if (rotate_skybox_right) {
                    //quando rotaciona o jogador, tbm rotaciona a skybox
                    skybox.rotateYaw(0.1, "RIGHT");
                }
                switch (skybox_move_direction_mouse) {
                    case MOUSE_LEFT:
                        skybox.rotateYaw(0.1, "LEFT");
                        break;
                    case MOUSE_RIGHT:
                        skybox.rotateYaw(0.1, "RIGHT");
                        break;
                }
            }
            break;
            case 2: {
            }
            break;
        }
        //atualiza skybox de acordo com a camera
        skybox.update(camera2.getXPos(), camera2.getYPos(), camera2.getZPos(),
                camera2.getYaw(), 0);

        //update mundo
        world.update();

        //desenha e dah update no window controller
        window_controller.paint(panel.getGraphics());

        //atualiza painel
        panel.repaint();
    }
}
