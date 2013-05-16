/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 *
 * Obs: Classe retirada do site http://sourceforge.net/projects/jodyssey/
 * e foi modificado para adaptacoes do trabalho
 */
package defense;


public class Clock {
    private int milliseconds;
    private long start_millisec;
    private long start_nanosec;
    private int MILLITOSECCONVERT = 1000;
    private int NANOTOMILLICONVERT = 1000000;
    private boolean timer_started;


    public Clock() {
        milliseconds = 0;
        timer_started = false;
    }


    public void start() {
        start_millisec = getCurrMS();
        start_nanosec = start_millisec * NANOTOMILLICONVERT; // incrase nanosec pass between last line and this one
        timer_started = true;
    }


    public void start(int millisec) {
        milliseconds = millisec;
        start_millisec = getCurrMS();
        start_nanosec = start_millisec * NANOTOMILLICONVERT; // incrase nanosec pass between last line and this one
        timer_started = true;
    }


    public void stop() {
        timer_started = false;
    }


    public boolean started() {
        return timer_started;
    }


    public boolean isCompleted() {
        if (timer_started && timePassedMilliSec() >= milliseconds) {
            timer_started = false;
            return true;
        } else {
            return false;
        }
    }

    // returns how many milliseconds passed

    public int timePassedMilliSec() {
        if (timer_started) {
            return (int) (timePassedNanoSec() / NANOTOMILLICONVERT);
        } else {
            return 0;
        }
    }


    public int timePassedSec() {
        if (timer_started) {
            return (int) ((timePassedNanoSec() / NANOTOMILLICONVERT) / MILLITOSECCONVERT);
        } else {
            return 0;
        }
    }

    // returns how many milliseconds passed

    public int timeLeft() {
        if (timer_started) {
            return (int) (milliseconds - timePassedNanoSec() / NANOTOMILLICONVERT);
        } else {
            return 0;
        }
    }

    // returns how many nanoseconds passed

    public long timePassedNanoSec() {
        if (timer_started) {
            return System.nanoTime() - start_nanosec;
        } else {
            return 0;
        }
    }


    private long getCurrMS() {
        return (System.nanoTime() / NANOTOMILLICONVERT);
    }


    public static void main(String[] args) {
        Clock ot = new Clock();
        ot.start(30000);
        while (true) {
            System.out.println(ot.timePassedMilliSec());
            if (ot.isCompleted()) {
                break;
            }
        }
    }
}
