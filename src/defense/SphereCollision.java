/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;


public class SphereCollision {
    private double x;
    private double y;
    private double z;
    private double radius;


    public SphereCollision() {
        this.x = 0;
        this.y = 0;
        this.z = 0;

        this.radius = 1;
    }


    public SphereCollision(double x, double y, double z, double radius) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.radius = radius;
    }


    public void updateCoordenate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public boolean testCollideSphere(double x, double y, double z, double radius) {
        //dx = (x1 - x2)^2, dy = (y1 - y2)^2, dz = (z1 - z2)^2
        double dx = Math.pow(this.x - x, 2);
        double dy = Math.pow(this.y - y, 2);
        double dz = Math.pow(this.z - z, 2);

        //raiz(dx + dy + dz), jah retorna positivo pq tira a raiz
        double distance1 = Math.sqrt(dx + dy + dz);

        //soma dos raios
        double distance2 = this.radius + radius;

        //comp = 0, d1 = d2. colide
        //comp < 0, d1 < d2. colide
        //comp > 0, d1 > d2. nao colide
        if (Double.compare(distance1, distance2) > 0) {
            //significa que nao colidiu com esse objeto
            return false;
        }

        //significa que colidiu com esse objeto
        return true;
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


    public double getRadius() {
        return radius;
    }
}
