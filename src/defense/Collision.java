/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import com.sun.opengl.util.GLUT;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class Collision {
    //lista das pedras
    private ArrayList<Stone> stone;
    //lista de esferas de colisoes das pedras sendo cada 1 uma esfera de colisao
    private ArrayList<SphereCollision> stone_sphere;
    //lista de booleans se a pedra colidiu ou nao
    private ArrayList<Boolean> stone_collide;
    //lista de booleans se a pedra existe ou nao
    private ArrayList<Boolean> stone_exist;
    //jogador
    private Player player;
    //lista de esferas de colisoes (6 com tamanhos diferentes, cabeça, corpo, maos e pes)
    private ArrayList<SphereCollision> player_sphere;
    //lista de distancias de cada parte de colisao do jogador com o jogador
    private ArrayList<Double> player_distance_front;
    private ArrayList<Double> player_distance_side;
    //lista de esferas de colisoes (49 com tamanhos iguais)
    private ArrayList<SphereCollision> shield_sphere;
    //lista de distancias de cada parte de colisao do escudo com o jogador
    private ArrayList<Double> shield_distance_front;
    private ArrayList<Double> shield_distance_side;
    //armazena se houve colisao naquele momento com o jogador
    private boolean collision_player;
    //conta quantas pedras colidiram com o escudo
    public int count_collision_shield;
    //Glut para desenhar esfera para ver posicao da colisao
    private GLUT glut = new GLUT();


    public Collision(Player player, ArrayList<Stone> stone, ArrayList<Boolean> stone_exist) {
        this.player = player;
        this.stone = stone;
        this.stone_exist = stone_exist;

        stone_sphere = new ArrayList<SphereCollision>();
        stone_collide = new ArrayList<Boolean>();

        player_sphere = new ArrayList<SphereCollision>();
        player_distance_front = new ArrayList<Double>();
        player_distance_side = new ArrayList<Double>();
        shield_sphere = new ArrayList<SphereCollision>();
        shield_distance_front = new ArrayList<Double>();
        shield_distance_side = new ArrayList<Double>();

        collision_player = false;
        count_collision_shield = 0;
    }


    public void init() {
        SphereCollision sc;

        //inicia as posicoes das colisoes do jogador
        //cabeca
        sc = new SphereCollision(player.getX(), player.getY() + 0.4, player.getZ(), 0.57);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(0));
        player_distance_side.add(Double.valueOf(0));

        //corpo
        sc = new SphereCollision(player.getX(), player.getY() - 0.4, player.getZ(), 0.38);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(0));
        player_distance_side.add(Double.valueOf(0));

        //mao direita
        sc = new SphereCollision(player.getX() - 0.12, player.getY() - 0.2, player.getZ() - 0.4, 0.152);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(-0.4));
        player_distance_side.add(Double.valueOf(-0.12));

        //mao esquerda
        sc = new SphereCollision(player.getX() + 0.12, player.getY() - 0.2, player.getZ() - 0.4, 0.152);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(-0.4));
        player_distance_side.add(Double.valueOf(0.12));

        //pe direito
        sc = new SphereCollision(player.getX() - 0.2, player.getY() - 0.8, player.getZ(), 0.19);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(0));
        player_distance_side.add(Double.valueOf(-0.2));

        //pe esquerdo
        sc = new SphereCollision(player.getX() + 0.2, player.getY() - 0.8, player.getZ(), 0.19);
        player_sphere.add(sc);
        player_distance_front.add(Double.valueOf(0));
        player_distance_side.add(Double.valueOf(0.2));

        //inicia as posicoes das colisoes do escudo
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                double z = 0;
                switch (i) {
                    case 0:
                    case 6: {
                        z = 0.076;
                    }
                    break;
                    case 1:
                    case 5: {
                        z = 0.049;
                    }
                    break;
                    case 2:
                    case 4: {
                        z = 0.022;
                    }
                    break;
                }
                sc = new SphereCollision(player.getX() - 0.45 + i * 0.15,
                        player.getY() - 0.64 + j * 0.15, player.getZ() - 0.64 + z, 0.05);
                shield_sphere.add(sc);
                shield_distance_front.add(Double.valueOf(-0.64 + z));
                shield_distance_side.add(Double.valueOf(-0.45 + i * 0.15));
            }
        }
    }


    public void update() {
        ArrayList<Integer> position_colide_now = new ArrayList<Integer>();

        //confere as colisoes das pedras com o escudo ou jogador e dah update nas booleans
        for (int i = 0; i < stone.size(); i++) {
            //checa toda as pedras que ainda nao colidiu com escudo
            if (!stone_collide.get(i).booleanValue()) {
                //se ainda nao colidiu com escudo checa colisao com escudo
                for (int j = 0; j < shield_sphere.size(); j++) {
                    if (shield_sphere.get(j).testCollideSphere(stone_sphere.get(i).getX(),
                            stone_sphere.get(i).getY(), stone_sphere.get(i).getZ(), stone_sphere.get(i).getRadius())) {
                        //significa que colidiu com o escudo a pedra i
                        stone_collide.set(i, Boolean.TRUE);
                        //armazena no vetor as posicoes que acabaram de colidir com escudo para mudar a direcao da pedra
                        position_colide_now.add(Integer.valueOf(i));
                        //atualiza o contador de pedras de colisao com o escudo
                        count_collision_shield++;
                        //se colidiu nao precisa comparar mais
                        break;
                    }
                }

                //se ainda nao colidiu com escudo checa colisao com jogador
                for (int j = 0; j < player_sphere.size(); j++) {
                    if (player_sphere.get(j).testCollideSphere(stone_sphere.get(i).getX(),
                            stone_sphere.get(i).getY(), stone_sphere.get(i).getZ(), stone_sphere.get(i).getRadius())) {
                        //significa que colidiu com o jogador a pedra i e o jogador perde vida
                        collision_player = true;
                        //destroi a pedra que colidiu com jogador pra evitar ficar dentro do jogador
                        stone_exist.set(i, Boolean.FALSE);
                    }
                }
            } else {
                //se colidiu com o escudo entao ve o tempo de vida se jah pode ser destruido
                if (stone.get(i).getDestroi()) {
                    //se pode ser destruido ele seta pra destruir pedra
                    stone_exist.set(i, Boolean.FALSE);
                }
            }

            //confere se a pedra caiu no chao, ou seja y <= 0
            if (Double.compare(stone_sphere.get(i).getY(), 0) <= 0) {
                //se caiu ele seta pra destruir pedra
                stone_exist.set(i, Boolean.FALSE);
            }
        }

        //muda as direcoes das pedras colididas
        if (!position_colide_now.isEmpty()) {
            for (int i = 0; i < position_colide_now.size(); i++) {
                int index = position_colide_now.get(i);
                //muda posicao da pedra
                stone.get(index).reflectStone();
            }
        }

        //dah update nas posicoes das colisoes das pedras
        updatePositionStone();

        //dah update nas posicoes das colisoes do jogador
        updatePositionPlayer();

        //dah update nas posicoes das colisoes do escudo
        updatePositionShield();

//        System.out.println("Colisoes com o escudo: ");
//        for (int i = 0; i < stone.size(); i++) {
//            System.out.println(i+": colidiu?("+stone_collide.get(i).toString()+") existe?("+stone_exist.get(i).toString()+")");
//        }
//        System.out.println("pedras colididas: "+count_collision_shield);
//        System.out.println("colidiu com jogador?("+Boolean.toString(collision_player)+")");

    }


    public void updatePositionStone() {
        //atualiza a posicao de colisao da pedra
        for (int i = 0; i < stone.size(); i++) {
            //se a pedra existe entao ela muda coordenadas
            if (stone_exist.get(i)) {
                stone_sphere.get(i).updateCoordenate(stone.get(i).getX(), stone.get(i).getY(), stone.get(i).getZ());
            }
        }
    }
    //essa funcao existe para evitar problemas na criacao de pedras durante o jogo


    void createStone(int number_stone_to_create, double velocity, double distance, double size_stone) {
        for (int i = 0; i < number_stone_to_create; i++) {
            Stone s = new Stone();

            s.createStone(player.getX(), player.getY(), player.getZ(), velocity, distance);
            stone.add(s);
            stone_exist.add(Boolean.TRUE);
            stone_sphere.add(new SphereCollision(s.getX(), s.getY(), s.getZ(), size_stone));
            stone_collide.add(Boolean.FALSE);
        }
    }

    //essa funcao existe para evitar problemas na remocao de pedras durante o jogo

    public void deleteStone(int index) {
        stone.remove(index);
        stone_collide.remove(index);
        stone_sphere.remove(index);
        stone_exist.remove(index);
    }


    private void updatePositionPlayer() {
        //atualiza a posicao de colisao do jogador
        for (int i = 0; i < player_sphere.size(); i++) {
            double x = player.getX() - player_distance_front.get(i) * player.getVectorX()
                    + player_distance_side.get(i) * player.getVectorSideX();
            double z = player.getZ() - player_distance_front.get(i) * player.getVectorZ()
                    + player_distance_side.get(i) * player.getVectorSideZ();
            player_sphere.get(i).updateCoordenate(x, player_sphere.get(i).getY(), z);
        }
    }


    private void updatePositionShield() {
        //atualiza a posicao de colisao do escudo
        for (int i = 0; i < shield_sphere.size(); i++) {
            double x = player.getX() - shield_distance_front.get(i) * player.getVectorX()
                    + shield_distance_side.get(i) * player.getVectorSideX();
            double z = player.getZ() - shield_distance_front.get(i) * player.getVectorZ()
                    + shield_distance_side.get(i) * player.getVectorSideZ();
            shield_sphere.get(i).updateCoordenate(x, shield_sphere.get(i).getY(), z);
        }
    }


    public void setCollisionPlayer(boolean collision_player) {
        this.collision_player = collision_player;
    }


    public boolean getCollisionPlayer() {
        return collision_player;
    }


    public int getCountCollisionShield() {
        return count_collision_shield;
    }


    public void renderer(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //player
        for (int i = 0; i < player_sphere.size(); i++) {
            gl.glPushMatrix();
            gl.glTranslated(player_sphere.get(i).getX(), player_sphere.get(i).getY(), player_sphere.get(i).getZ());
            glut.glutWireSphere(player_sphere.get(i).getRadius(), 20, 20);
            gl.glPopMatrix();
        }

        //shield
        for (int i = 0; i < shield_sphere.size(); i++) {
            gl.glPushMatrix();
            gl.glTranslated(shield_sphere.get(i).getX(), shield_sphere.get(i).getY(), shield_sphere.get(i).getZ());
            glut.glutWireSphere(shield_sphere.get(i).getRadius(), 20, 20);
            gl.glPopMatrix();
        }

        //stone
        for (int i = 0; i < stone_sphere.size(); i++) {
            gl.glPushMatrix();
            gl.glTranslated(stone_sphere.get(i).getX(), stone_sphere.get(i).getY(), stone_sphere.get(i).getZ());
            glut.glutWireSphere(stone_sphere.get(i).getRadius(), 20, 20);
            gl.glPopMatrix();
        }
    }
}
