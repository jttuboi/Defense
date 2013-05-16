/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package defense;


public class Life {
    //quantidade de vida do jogador
    private int life;
    //quantidade maxima da vida do jogador
    private int max_life;


    public Life() {
        max_life = 100;
        life = max_life;
    }


    public Life(int qty_life) {
        max_life = qty_life;
        life = max_life;
    }


    public void increaseLife(int increase_life) {
        life += increase_life;
    }


    public void decreaseLife(int decrease_life) {
        life -= decrease_life;
    }


    public void set(int new_life) {
        life = new_life;
    }


    public void setMax(int new_max_life) {
        max_life = new_max_life;
    }


    public int get() {
        return life;
    }


    public int getMax() {
        return max_life;
    }


    public double getPercetageLife() {
        return ((100 * life) / max_life);
    }


    public boolean isDead() {
        return ((life < 0) ? true : false);
    }
}
