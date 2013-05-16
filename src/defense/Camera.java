/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 * 
 * Obs: Classe retirada do site http://www.leolol.com/tutorials/graphics/lesson05.php
 * e foi modificado para adaptacoes do trabalho
 */
package defense;


public class Camera {
    //posicao da camera
    private double xPos;
    private double yPos;
    private double zPos;
    //posicao da visao da camera
    private double xLPos;
    private double yLPos;
    private double zLPos;
    //angulos de rotacao
    private double pitch;
    private double yaw;
    //distancia do jogador
    private double distance_player;


    public Camera() {
        xPos = 0;
        yPos = 0;
        zPos = 0;

        xLPos = 0;
        yLPos = 0;
        zLPos = 10;

        distance_player = 3;
    }


    public Camera(double xPos, double yPos, double zPos, double xLPos, double yLPos, double zLPos, double distance_player) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;

        this.xLPos = xLPos;
        this.yLPos = yLPos;
        this.zLPos = zLPos;

        this.distance_player = distance_player;
    }


    public void setPitch(double pitch) {
        this.pitch = pitch;
    }


    public void setYaw(double yaw) {
        this.yaw = yaw;
    }


    public void updatePosition(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }


    public void lookPosition(double xLPos, double yLPos, double zLPos) {
        this.xLPos = xLPos;
        this.yLPos = yLPos;
        this.zLPos = zLPos;
    }

    // Moves the entity forward according to its pitch and yaw and the magnitude.

    public void moveForward(double magnitude) {
        double xCurrent = this.xPos;
        double yCurrent = this.yPos;
        double zCurrent = this.zPos;

        // Spherical coordinates maths
        double xMovement = magnitude * Math.cos(pitch) * Math.cos(yaw);
        double yMovement = magnitude * Math.sin(pitch);
        double zMovement = magnitude * Math.cos(pitch) * Math.sin(yaw);

        double xNew = xCurrent + xMovement;
        double yNew = yCurrent + yMovement;
        double zNew = zCurrent + zMovement;

        updatePosition(xNew, yNew, zNew);
    }


    public void strafeLeft(double magnitude) {
        double pitchTemp = pitch;
        pitch = 0;

        yaw = yaw - (0.5 * Math.PI);
        moveForward(magnitude);
        yaw = yaw + (0.5 * Math.PI);

        pitch = pitchTemp;
    }


    public void strafeRight(double magnitude) {
        double pitchTemp = pitch;
        pitch = 0;

        yaw = yaw + (0.5 * Math.PI);
        moveForward(magnitude);
        yaw = yaw - (0.5 * Math.PI);

        pitch = pitchTemp;
    }


    public void look(double distanceAway) {
        if (pitch > 1.0) {
            pitch = 0.99f;
        }

        if (pitch < -1.0) {
            pitch = -0.99f;
        }

        moveForward(10);

        double xLook = xPos;
        double yLook = yPos;
        double zLook = zPos;

        moveForward(-10);

        lookPosition(xLook, yLook, zLook);
    }


    public double getXPos() {
        return xPos;
    }


    public double getYPos() {
        return yPos;
    }


    public double getZPos() {
        return zPos;
    }


    public double getXLPos() {
        return xLPos;
    }


    public double getYLPos() {
        return yLPos;
    }


    public double getZLPos() {
        return zLPos;
    }


    public double getPitch() {
        return pitch;
    }


    public double getYaw() {
        return yaw;
    }


    public void pitchUp(double amount) {
        this.pitch += amount;
    }


    public void pitchDown(double amount) {
        this.pitch -= amount;
    }


    public void yawRight(double amount) {
        this.yaw += amount;
    }


    public void yawLeft(double amount) {
        this.yaw -= amount;
    }


    public void cameraMan(double position_player_x, double position_player_y, double position_player_z,
            double vector_player_x, double vector_player_y, double vector_player_z, double yaw) {
        //corrige posicao
        xPos = position_player_x + distance_player * vector_player_x;
        zPos = position_player_z + distance_player * vector_player_z;

        //corrige yaw
        this.yaw = -yaw + 4.71238898038469; //270 graus
    }
}
