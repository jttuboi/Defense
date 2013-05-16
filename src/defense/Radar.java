/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class Radar {
    //imagem do radar
    private Image radar_grid;
    private Image radar_background;
    private Image radar_stone1;
    private Image radar_stone2;
    //posicao do radar na tela
    private int x;
    private int y;
    //raio e centro do radar
    private int radius;
    //player e lista de pedras para pegar suas posicoes
    private Player player;
    private ArrayList<Stone> list_stone;
    //posicao das pedras em pixel
    ArrayList<Integer> stone_x = new ArrayList<Integer>();
    ArrayList<Integer> stone_y = new ArrayList<Integer>();
    //controle de imagens das pedras para ter efeito de piscar
    private int count_change_image = 0;
    private boolean print_stone_1 = true;


    public Radar(int x, int y) {
        //inicia imagens
        String path = null;
        try {
            path = "/image/radar/radar1.png";
            radar_background = new ImageIcon(getClass().getResource(path)).getImage();
        } catch (Exception e) {
            System.out.println("nao achou a imagem: " + path);
        }
        try {
            path = "/image/radar/radar2.png";
            radar_grid = new ImageIcon(getClass().getResource(path)).getImage();
        } catch (Exception e) {
            System.out.println("nao achou a imagem: " + path);
        }
        try {
            path = "/image/radar/radar_stone1.png";
            radar_stone1 = new ImageIcon(getClass().getResource(path)).getImage();
        } catch (Exception e) {
            System.out.println("nao achou a imagem: " + path);
        }
        try {
            path = "/image/radar/radar_stone2.png";
            radar_stone2 = new ImageIcon(getClass().getResource(path)).getImage();
        } catch (Exception e) {
            System.out.println("nao achou a imagem: " + path);
        }

        this.x = x;
        this.y = y;

        this.radius = 90;
    }


    void init(Player player, ArrayList<Stone> list_stone) {
        this.player = player;
        this.list_stone = list_stone;
    }


    public void paint(Graphics g) {
        //desenha o fundo do radar
        g.drawImage(radar_background, x, y, null);

        //converte as posicoes do mundo em de radar
        convertPositionStoneToPositionRadar();

        //desenha as pedras
        for (int i = 0; i < list_stone.size(); i++) {
            int distance = (int) Math.sqrt(Math.pow(stone_x.get(i).doubleValue(), 2) + Math.pow(stone_y.get(i).doubleValue(), 2));

            if (distance < radius) {
                if (print_stone_1) {
                    g.drawImage(radar_stone1, x + stone_x.get(i).intValue() + 97, y + stone_y.get(i).intValue() + 97, null);
                } else {
                    g.drawImage(radar_stone2, x + stone_x.get(i).intValue() + 97, y + stone_y.get(i).intValue() + 97, null);
                }
            }
        }

        //desenha a grade do radar
        g.drawImage(radar_grid, x, y, null);

        //desenha para onde o jogador esta olhando de acordo com a camera
        switch (Defense.position_camera) {
            case Defense.BACK: {
                g.drawImage(radar_stone1, x + 97, y, null);
            }
            break;
            case Defense.LEFT: {
                g.drawImage(radar_stone1, x, y + 97, null);
            }
            break;
            case Defense.FRONT: {
                g.drawImage(radar_stone1, x + 97, y + 194, null);
            }
            break;
            case Defense.RIGHT: {
                g.drawImage(radar_stone1, x + 194, y + 97, null);
            }
            break;
        }
    }


    public void update() {
        //faz a imagem da pedra trocar de imagem
        count_change_image++;
        if (count_change_image > 5) {
            print_stone_1 = false;
            count_change_image = 0;
        } else {
            print_stone_1 = true;
        }


    }


    private void convertPositionStoneToPositionRadar() {
        /* remove para restartar as posicoes */

        stone_x.removeAll(stone_x);
        stone_y.removeAll(stone_y);


        /* pega os valores dos jogadores e da pedra do mundo */

        double pxd = player.getX();
        double pzd = player.getZ();

        double yaw = player.getYaw();

        //double yaw = player.getYaw();

        ArrayList<Double> sxd = new ArrayList<Double>();
        ArrayList<Double> szd = new ArrayList<Double>();
        for (int i = 0; i < list_stone.size(); i++) {
            sxd.add(Double.valueOf(list_stone.get(i).getX()));
            szd.add(Double.valueOf(list_stone.get(i).getZ()));
        }



        /* posicao inicial em double
         *
         *       |
         *       |
         *       |
         * ------+------ x
         *       |
         *       |   j
         *       |
         *       z
         *
         */


        /* muda as posicoes das pedras e a posicao do jogador vai para o centro */

        for (int i = 0; i < list_stone.size(); i++) {
            sxd.set(i, Double.valueOf(sxd.get(i).doubleValue() - pxd));
            szd.set(i, Double.valueOf(szd.get(i).doubleValue() - pzd));
        }

        /* posicao em double
         *
         *   |   |
         * --+---|------ x
         *   |   |
         * ------j------ x'
         *   |   |
         *   |   |
         *   |   |
         *   z   z'
         *
         */


        /* gira para corrigir a visao do jogador para o norte do radar */

        for (int i = 0; i < list_stone.size(); i++) {
            double vx = sxd.get(i).doubleValue();
            double vz = szd.get(i).doubleValue();

            //acha a distancia entre o jogador e a pedra
            //distancia = raiz((vx - 0)^2 + (vz - 0)^2)
            double distance = Math.sqrt(Math.pow(vx, 2) + Math.pow(vz, 2));

            //acha o angulo entre o jogador e o eixo z negativo
            //angulo = 270 - yaw_jogador
            double angle_player = 4.71238898038469 - yaw;

            //acha o angulo entre a pedra e o eixo x positivo
            //vetor_stone.vetor_eixoX = (vx - 0)*(1 - 0) + (vz - 0)*(0 - 0)
            double uv = vx;
            //|vetor_stone|.|vetor_eixoX| = raiz((vx - 0)^2 + (vz - 0)^2) * raiz((1 - 0)^2 + (0 - 0)^2)
            double muv = distance;
            //angulo = arcos(vetor_stone.vetor_eixoX / |vetor_stone|.|vetor_eixoX|) = arcos(uv / muv)
            double angle_stone = Math.acos(uv / muv);

            //caso a pedra esteja na regiao -z entao o angulo eh maior que 180
            if (Double.compare(vz, 0) > 0) {
                //angulo = 360 - angulo
                angle_stone = 6.283185307179586 - angle_stone;
            }

            //o angulo de acordo com a camera
            double yaw_camera = 0; //caso seja default, a visao pra frente
            switch (Defense.position_camera) {
                case Defense.BACK: {
                    yaw_camera = 0;
                }
                break;
                case Defense.LEFT: {
                    yaw_camera = 1.570796326794897;
                }
                break;
                case Defense.FRONT: {
                    yaw_camera = 3.141592653589793;
                }
                break;
                case Defense.RIGHT: {
                    yaw_camera = 4.71238898038469;
                }
                break;
            }

            sxd.set(i, Double.valueOf(-distance * Math.sin(angle_player + angle_stone + yaw_camera)));
            szd.set(i, Double.valueOf(-distance * Math.cos(angle_player + angle_stone + yaw_camera)));
        }

        /* posicao em double
         *
         * \     |    / x'
         *   \   |  /
         *     \ |/
         * ------j------ x''
         *      /| \
         *    /  |   \
         *  /    |     \
         *       z''    z'
         *
         */


        /* converte as coordenadas em pixel */

        //distancia radar / distancia real = 10 pixel / 1 radiano
        for (int i = 0; i < list_stone.size(); i++) {
            stone_x.add(Integer.valueOf((int) (10 * sxd.get(i).doubleValue())));
            stone_y.add(Integer.valueOf((int) (10 * szd.get(i).doubleValue())));
        }

        /* posicao em pixel
         *
         *       |
         *       |
         *       |
         * ------j------ x
         *       |
         *       |
         *       |
         *       y
         *
         */


        /* agora transfere as posicoes para o canto superior sendo que o deslocamento eh de 100 pixels */

        for (int i = 0; i < list_stone.size(); i++) {
            stone_x.set(i, Integer.valueOf(stone_x.get(i).intValue()));
            stone_y.set(i, Integer.valueOf(stone_y.get(i).intValue()));
        }

        /* posicao em pixel + 100
         *
         * +------------ x'
         * |
         * |
         * |     j
         * |
         * |
         * |
         * y'
         *
         */

        //o radar estará na parte positiva de x e y sendo que na hora de desenhar as pedras irah
        //descartar o que estiver fora da area circular de 100 pixels do jogador
    }
}
