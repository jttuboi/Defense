/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.util.ArrayList;
import javax.media.opengl.GLAutoDrawable;


public class World extends EventHandler {
    //objetos
    private Floor floor = new Floor("05");
    private Player player = new Player();
    private ModelStone model_stone = new ModelStone();
    private ArrayList<Stone> list_stone = new ArrayList<Stone>();
    private ArrayList<Boolean> list_stone_exist = new ArrayList<Boolean>();
    //quantidade de pedras criadas durante o jogo
    public final static int QUANT_MAX_STONE = 20;
    public static int quantity_stone = QUANT_MAX_STONE;
    //colisoes
    private Collision collision;
    private boolean draw_collision = false;
    //sombra
    private FakeShadow shadow;
    //controle de movimento do jogador de acordo com o mouse
    private final static int MOUSE_LEFT = -1;
    private final static int MOUSE_STATIC = 0;
    private final static int MOUSE_RIGHT = 1;
    private static int player_move_direction_mouse = MOUSE_STATIC;
    //booleans de ativacao de keyboard para a funcao de loop logico do jogo
    private boolean player_move_front = false;
    private boolean player_move_back = false;
    private boolean player_move_left = false;
    private boolean player_move_right = false;
    private boolean player_rotate_left = false;
    private boolean player_rotate_right = false;
    private boolean camera2_move_front = false;
    private boolean camera2_move_back = false;
    private boolean camera2_rotate_left = false;
    private boolean camera2_rotate_right = false;
    private boolean camera2_rotate_up = false;
    private boolean camera2_rotate_down = false;
    private boolean player_acelerate_movement = false;


    public World() {
        //Adiciona a classe na lista de classes que deve receber os eventos
        Main.input_controller.addEventHandler(this);
    }


    public void init(GLAutoDrawable drawable) {
        //cria um chao para o jogo
        floor.init();

        //inicia a criacao do player
        player.init(drawable);

        //inicia a criacao do modelo da pedra
        model_stone.init(drawable);

        //inicia a criacao dos locais de colisao
        collision = new Collision(player, list_stone, list_stone_exist);
        collision.init();

        //cria uma lista inicial de pedras
//        collision.createStone((int) QUANT_MAX_STONE, 0.01, 2, 0.1);
        collision.createStone((int) QUANT_MAX_STONE / 4, 0.5, 15, 0.1);
        collision.createStone((int) QUANT_MAX_STONE / 4, 0.5, 25, 0.1);
        collision.createStone((int) QUANT_MAX_STONE / 4, 0.5, 35, 0.1);
        collision.createStone((int) QUANT_MAX_STONE / 4, 0.5, 45, 0.1);

        //inicia a criacao das sombras
        shadow = new FakeShadow(player, list_stone);
        shadow.init();
    }


    public void renderer(GLAutoDrawable drawable) {
        //desenha chao
        floor.renderer(drawable);

        //desenha pedras e tailstone
        for (int i = 0; i < list_stone.size(); i++) {
            model_stone.renderer(drawable, list_stone.get(i).getX(),
                    list_stone.get(i).getY(), list_stone.get(i).getZ(), list_stone.get(i).getTail(), 0.1);
        }

        //desenha jogador
        player.renderer(drawable);

        //desenha sombras
        shadow.renderer(drawable);

        //desenha ou nao a colisao
        if (draw_collision) {
            collision.renderer(drawable);
        }
    }


    public void update() {
        //atualiza a posicao da camera
        switch (Defense.current_camera) {
            //movimento do jogador
            case 1: {
                if (player_acelerate_movement) {
                    //movimento com shift pressionado
                    if (player_move_front) {
                        player.moveFrontBack(0.5);
                    }
                    if (player_move_back) {
                        player.moveFrontBack(-0.5);
                    }
                    if (player_move_left) {
                        player.moveLeftRight(-0.5);
                    }
                    if (player_move_right) {
                        player.moveLeftRight(0.5);
                    }
                } else {
                    //movimento normal
                    if (player_move_front) {
                        player.moveFrontBack(0.2);
                    }
                    if (player_move_back) {
                        player.moveFrontBack(-0.2);
                    }
                    if (player_move_left) {
                        player.moveLeftRight(-0.2);
                    }
                    if (player_move_right) {
                        player.moveLeftRight(0.2);
                    }
                }
                if (player_move_direction_mouse == MOUSE_STATIC) {
                    //rotacao pelo teclado
                    if (player_rotate_left) {
                        player.rotateLeft(0.1);
                    }
                    if (player_rotate_right) {
                        player.rotateRight(0.1);
                    }
                } else {
                    //rotacao pelo mouse
                    switch (player_move_direction_mouse) {
                        case MOUSE_LEFT:
                            player.rotateLeft(0.1);
                            break;
                        case MOUSE_RIGHT:
                            player.rotateRight(0.1);
                            break;
                    }
                }

                double vx = 0;
                double vy = player.getVectorY();
                double vz = 0;
                double yaw = player.getYaw();

                switch (Defense.position_camera) {
                    case Defense.FRONT: {
                        vx = player.getVectorX();
                        vz = player.getVectorZ();
                        yaw += 3.141592653589793;
                    }
                    break;
                    case Defense.BACK: {
                        vx = -player.getVectorX();
                        vz = -player.getVectorZ();
                    }
                    break;
                    case Defense.RIGHT: {
                        vx = player.getVectorSideX();
                        vz = player.getVectorSideZ();
                        yaw += 1.570796326794897;
                    }
                    break;
                    case Defense.LEFT: {
                        vx = -player.getVectorSideX();
                        vz = -player.getVectorSideZ();
                        yaw -= 1.570796326794897;
                    }
                    break;
                }

                Defense.camera1.cameraMan(player.getX(), player.getY(), player.getZ(), vx, vy, vz, yaw);
                Defense.camera1.look(10);
            }
            break;

            //movimento da camera
            case 2: {
                if (camera2_move_front) {
                    Defense.camera2.moveForward(0.8);
                }
                if (camera2_move_back) {
                    Defense.camera2.moveForward(-0.8);
                }
                if (camera2_rotate_left) {
                    Defense.camera2.yawLeft(0.5);
                }
                if (camera2_rotate_right) {
                    Defense.camera2.yawRight(0.5);
                }
                if (camera2_rotate_up) {
                    Defense.camera2.pitchUp(0.5);
                }
                if (camera2_rotate_down) {
                    Defense.camera2.pitchDown(0.5);
                }

                Defense.camera2.look(10);
            }
            break;
        }

        int i = 0;

        //atualiza posicao das pedras
        while (i < list_stone.size()) {
            if (list_stone_exist.get(i)) {
                list_stone.get(i).updatePosition();
                i++;
            } else {
                collision.deleteStone(i);
            }
        }

        //atualiza as verificaçoes de colisoes que podem ocorrer ou nao
        collision.update();

        //se a quantidade de pedras dor abaixo de 50 ele vai criando para manter 50 pedras durante o jogo
        if (list_stone.size() < QUANT_MAX_STONE) {
            int number_stone_to_create = QUANT_MAX_STONE - list_stone.size();
            collision.createStone(number_stone_to_create, 0.5, 30, 0.1);
            quantity_stone += number_stone_to_create;
        }
    }


    public Player getPlayer() {
        return player;
    }


    public ArrayList<Stone> getListStone() {
        return list_stone;
    }


    public Collision getCollision() {
        return collision;
    }


    @Override
    public void doEvent(String event) {
        //movimento do jogador
        if (event.equals("W_PRESS")) {
            player_move_front = true;
        }
        if (event.equals("S_PRESS")) {
            player_move_back = true;
        }
        if (event.equals("A_PRESS")) {
            player_move_left = true;
        }
        if (event.equals("D_PRESS")) {
            player_move_right = true;
        }
        if (event.equals("Q_PRESS")) {
            player_rotate_left = true;
        }
        if (event.equals("E_PRESS")) {
            player_rotate_right = true;
        }
        if (event.equals("SHIFT_PRESS")) {
            player_acelerate_movement = true;
        }
        if (event.equals("W_RELEASE")) {
            player_move_front = false;
        }
        if (event.equals("S_RELEASE")) {
            player_move_back = false;
        }
        if (event.equals("A_RELEASE")) {
            player_move_left = false;
        }
        if (event.equals("D_RELEASE")) {
            player_move_right = false;
        }
        if (event.equals("Q_RELEASE")) {
            player_rotate_left = false;
        }
        if (event.equals("E_RELEASE")) {
            player_rotate_right = false;
        }
        if (event.equals("SHIFT_RELEASE")) {
            player_acelerate_movement = false;
        }
        if (event.equals("MOVE_X_LEFT")) {
            player_move_direction_mouse = MOUSE_LEFT;
        }
        if (event.equals("MOVE_X_RIGHT")) {
            player_move_direction_mouse = MOUSE_RIGHT;
        }
        if (event.equals("MOUSE_DEAD_ZONE")) {
            player_move_direction_mouse = MOUSE_STATIC;
        }

        //movimento da camera
        if (event.equals("UP_PRESS")) {
            camera2_move_front = true;
        }
        if (event.equals("DOWN_PRESS")) {
            camera2_move_back = true;
        }
        if (event.equals("LEFT_PRESS")) {
            camera2_rotate_left = true;
        }
        if (event.equals("RIGHT_PRESS")) {
            camera2_rotate_right = true;
        }
        if (event.equals("PAGE_UP_PRESS")) {
            camera2_rotate_up = true;
        }
        if (event.equals("PAGE_DOWN_PRESS")) {
            camera2_rotate_down = true;
        }
        if (event.equals("UP_RELEASE")) {
            camera2_move_front = false;
        }
        if (event.equals("DOWN_RELEASE")) {
            camera2_move_back = false;
        }
        if (event.equals("LEFT_RELEASE")) {
            camera2_rotate_left = false;
        }
        if (event.equals("RIGHT_RELEASE")) {
            camera2_rotate_right = false;
        }
        if (event.equals("PAGE_UP_RELEASE")) {
            camera2_rotate_up = false;
        }
        if (event.equals("PAGE_DOWN_RELEASE")) {
            camera2_rotate_down = false;
        }

        //configuracoes
        if (event.equals("F9_PRESS")) {
            if (draw_collision) {
                draw_collision = false;
            } else {
                draw_collision = true;
            }
        }
    }
}
