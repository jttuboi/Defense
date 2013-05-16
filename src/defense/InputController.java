/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class InputController implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    //contem a lista dos eventos e a lista das classes que receberam os eventos
    public EventDispatcher event_disp;
    public static int cursor_x = Main.WIDTHSCREEN / 2;
    //variacao de pixel pra o mouse nao mexer o jogador na tela
    public int dead_zone = 100;


    public InputController() {
        event_disp = new EventDispatcher();
    }


    public void addEventHandler(EventHandler newEventHandler) {
        event_disp.addEventHandler(newEventHandler);
    }


    public void removeEventHandler(EventHandler newEventHandler) {
        event_disp.removeEventHandler(newEventHandler);
    }


    public void clearEventHandler() {
        event_disp.clearListEventHandler();
    }


    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                event_disp.sendEvent("ESCAPE_PRESS");
                break;
            case KeyEvent.VK_SHIFT:
                event_disp.sendEvent("SHIFT_PRESS");
                break;
            case KeyEvent.VK_W:
                event_disp.sendEvent("W_PRESS");
                break;
            case KeyEvent.VK_S:
                event_disp.sendEvent("S_PRESS");
                break;
            case KeyEvent.VK_A:
                event_disp.sendEvent("A_PRESS");
                break;
            case KeyEvent.VK_D:
                event_disp.sendEvent("D_PRESS");
                break;
            case KeyEvent.VK_Q:
                event_disp.sendEvent("Q_PRESS");
                break;
            case KeyEvent.VK_E:
                event_disp.sendEvent("E_PRESS");
                break;
            case KeyEvent.VK_LEFT:
                event_disp.sendEvent("LEFT_PRESS");
                break;
            case KeyEvent.VK_RIGHT:
                event_disp.sendEvent("RIGHT_PRESS");
                break;
            case KeyEvent.VK_UP:
                event_disp.sendEvent("UP_PRESS");
                break;
            case KeyEvent.VK_DOWN:
                event_disp.sendEvent("DOWN_PRESS");
                break;
            case KeyEvent.VK_PAGE_UP:
                event_disp.sendEvent("PAGE_UP_PRESS");
                break;
            case KeyEvent.VK_PAGE_DOWN:
                event_disp.sendEvent("PAGE_DOWN_PRESS");
                break;
            case KeyEvent.VK_F1:
                event_disp.sendEvent("F1_PRESS");
                break;
            case KeyEvent.VK_F2:
                event_disp.sendEvent("F2_PRESS");
                break;
            case KeyEvent.VK_F3:
                event_disp.sendEvent("F3_PRESS");
                break;
            case KeyEvent.VK_F4:
                event_disp.sendEvent("F4_PRESS");
                break;
            case KeyEvent.VK_F5:
                event_disp.sendEvent("F5_PRESS");
                break;
            case KeyEvent.VK_F6:
                event_disp.sendEvent("F6_PRESS");
                break;
            case KeyEvent.VK_F7:
                event_disp.sendEvent("F7_PRESS");
                break;
            case KeyEvent.VK_F8:
                event_disp.sendEvent("F8_PRESS");
                break;
            case KeyEvent.VK_F9:
                event_disp.sendEvent("F9_PRESS");
                break;
        }
    }


    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                event_disp.sendEvent("ESCAPE_RELEASE");
                break;
            case KeyEvent.VK_SHIFT:
                event_disp.sendEvent("SHIFT_RELEASE");
                break;
            case KeyEvent.VK_W:
                event_disp.sendEvent("W_RELEASE");
                break;
            case KeyEvent.VK_S:
                event_disp.sendEvent("S_RELEASE");
                break;
            case KeyEvent.VK_A:
                event_disp.sendEvent("A_RELEASE");
                break;
            case KeyEvent.VK_D:
                event_disp.sendEvent("D_RELEASE");
                break;
            case KeyEvent.VK_Q:
                event_disp.sendEvent("Q_RELEASE");
                break;
            case KeyEvent.VK_E:
                event_disp.sendEvent("E_RELEASE");
                break;
            case KeyEvent.VK_LEFT:
                event_disp.sendEvent("LEFT_RELEASE");
                break;
            case KeyEvent.VK_RIGHT:
                event_disp.sendEvent("RIGHT_RELEASE");
                break;
            case KeyEvent.VK_UP:
                event_disp.sendEvent("UP_RELEASE");
                break;
            case KeyEvent.VK_DOWN:
                event_disp.sendEvent("DOWN_RELEASE");
                break;
            case KeyEvent.VK_PAGE_UP:
                event_disp.sendEvent("PAGE_UP_RELEASE");
                break;
            case KeyEvent.VK_PAGE_DOWN:
                event_disp.sendEvent("PAGE_DOWN_RELEASE");
                break;
            case KeyEvent.VK_F1:
                event_disp.sendEvent("F1_RELEASE");
                break;
            case KeyEvent.VK_F2:
                event_disp.sendEvent("F2_RELEASE");
                break;
            case KeyEvent.VK_F3:
                event_disp.sendEvent("F3_RELEASE");
                break;
            case KeyEvent.VK_F4:
                event_disp.sendEvent("F4_RELEASE");
                break;
            case KeyEvent.VK_F5:
                event_disp.sendEvent("F5_RELEASE");
                break;
            case KeyEvent.VK_F6:
                event_disp.sendEvent("F6_RELEASE");
                break;
            case KeyEvent.VK_F7:
                event_disp.sendEvent("F7_RELEASE");
                break;
            case KeyEvent.VK_F8:
                event_disp.sendEvent("F8_RELEASE");
                break;
            case KeyEvent.VK_F9:
                event_disp.sendEvent("F9_RELEASE");
                break;
        }
    }


    public void mouseClicked(MouseEvent e) {
    }


    public void mousePressed(MouseEvent e) {
    }


    public void mouseReleased(MouseEvent e) {
    }


    public void mouseEntered(MouseEvent e) {
    }


    public void mouseExited(MouseEvent e) {
    }


    public void mouseDragged(MouseEvent e) {
    }


    public void mouseMoved(MouseEvent e) {
        if (cursor_x + dead_zone < e.getX()) {
            event_disp.sendEvent("MOVE_X_RIGHT");
        } else if (cursor_x - dead_zone > e.getX()) {
            event_disp.sendEvent("MOVE_X_LEFT");
        } else {
            event_disp.sendEvent("MOUSE_DEAD_ZONE");
        }
    }


    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
