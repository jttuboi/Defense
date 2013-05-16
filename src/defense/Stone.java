/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;


public class Stone {
    //posicao atual
    private double x;
    private double y;
    private double z;
    //posicao final
    private double xf;
    private double yf;
    private double zf;
    //tamanho do deslocamento em cada coordenada
    private double dx;
    private double dy;
    private double dz;
    //vetor direcao da pedra
    private double vx;
    private double vy;
    private double vz;
    //controla se a pedra jah foi reflita 1 vez pelo escudo
    private boolean reflect;
    //tempo de vida depois que houver a colisao com escudo
    private int live_time_after_collision;
    //controla se a pedra pode ser destruida
    private boolean destroi_it;
    //
    TailStone tail;


    public Stone() {
        x = 0;
        y = 0;
        z = 0;

        xf = 0;
        yf = 0;
        zf = 0;

        dx = 0;
        dy = 0;
        dz = 0;

        vx = 0;
        vy = 0;
        vz = 0;

        reflect = false;

        live_time_after_collision = 20;

        destroi_it = false;

        tail = new TailStone(this, 30);
    }


    public void createStone(double position_player_x, double position_player_y, double position_player_z, double velocity, double distance) {
        // -2 < rx, rz < 2
        // Math.random()*intervalo + inicio
        double rx = Math.random() * 4 - 2;
        double rz = Math.random() * 4 - 2;

        //dah as posicoes finais da pedra
        setPositionFinal(position_player_x + rx, position_player_y, position_player_z + rz);

        //randomiza uma posicao inicial da pedra
        randomizeInitialPosition(distance);

        //calcula o movimento da pedra
        calculateMovement(velocity);

        //arruma a direcao da pedra
        //(xf - x)/n
        vx = (xf - x) / n();
        vy = (yf - y) / n();
        vz = (zf - z) / n();

        tail.init();
    }


    private double n() {
        //(xf - x)^2, (yf - y)^2, (zf - z)^2
        double x2 = Math.pow(xf - x, 2);
        double y2 = Math.pow(yf - y, 2);
        double z2 = Math.pow(zf - z, 2);

        //raiz((xf - x)^2 + (yf - y)^2 + (zf - z)^2), jah retorna positivo pq tira a raiz
        return Math.sqrt(x2 + y2 + z2);
    }


    private void randomizeInitialPosition(double distance) {
        double radius = distance;
        double a, b;

        //caso distancia seja negativa, transforma em positivo
        if (radius <= 0) {
            radius = -radius;
        }

        // 5 < a < 45 (0.0872664625997165 < a < 0.7853981633974483 em radianos)
        // Math.random()*intervalo + inicio
        a = Math.random() * 0.6981317007977318 + 0.0872664625997165;

        // y = rad * sen(a) + yf
        y = yf + (radius * Math.sin(a));

        // 180 < b < 360 (3.141592653589793 < b < 6.283185307179586 em radianos)
        // Math.random()*intervalo + inicio
        b = Math.random() * 3.141592653589793 + 3.141592653589793;

        // x = rad * cos(b) + xf
        x = xf + (radius * Math.cos(b));

        // z = rad * sen(b) + zf
        z = zf + (radius * Math.sin(b));
    }


    public void updatePosition() {
        if (y > 0) {
            y = y - dy;
            x = x - dx;
            z = z - dz;
        } else {
            //caso a pedra passe do chao ela nao existe mais
            dx = 0;
            dy = 0;
            dz = 0;
        }

        //se jah refletiu diminui o tempo de vida da pedra
        if (reflect) {
            live_time_after_collision--;
        }

        //se for <= 0 singnifca que a pedra pode ser destruida
        if (live_time_after_collision <= 0) {
            destroi_it = true;
        }

        tail.update();
    }


    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void setPositionFinal(double xf, double yf, double zf) {
        this.xf = xf;
        this.yf = yf;
        this.zf = zf;
    }


    public void calculateMovement(double velocity) {
        double v = velocity;

        //compara a velocidade se eh 0, caso for nao existe movimento
        int test = Double.compare(v, 0.0f);

        if (test != 0) {

            double d = Math.sqrt(Math.pow(x - xf, 2) + Math.pow(y - yf, 2) + Math.pow(z - zf, 2));

            //existe movimento, entao atualiza o tamanho do movimento de cada coordenada
            dx = v * (x - xf) / d;
            dy = v * (y - yf) / d;
            dz = v * (z - zf) / d;
        } else {
            //nao existe movimento, entao nao existe deslocamento
            dx = 0;
            dy = 0;
            dz = 0;
        }
    }


    public void reflectStone() {
        if (!reflect) {
            // -2 <= add <= 2
            // Math.random()*intervalo + inicio
            double add = Math.random() * 4 - 2;

            //muda movimento da pedra
            dx = -dx;
            dy = dy * add;
            dz = -dz;

            //muda o vetor da pedra
            vx = -vx;
            vy = vy * add;
            vz = -vz;

            //jah refletiu e nao reflete mais
            reflect = true;
        }

    }


    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    public double getZ() {
        return z;
    }


    public double getFinalX() {
        return xf;
    }


    public double getFinalY() {
        return yf;
    }


    public double getFinalZ() {
        return zf;
    }


    public boolean getDestroi() {
        return destroi_it;
    }


    public double getVectorX() {
        return vx;
    }


    public double getVectorY() {
        return vy;
    }


    public double getVectorZ() {
        return vz;
    }


    public TailStone getTail() {
        return tail;
    }
}
