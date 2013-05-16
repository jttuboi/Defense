/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 */
package defense;

import java.util.ArrayList;


public class EventDispatcher {
    public static ArrayList<String> list_event = new ArrayList<String>();
    public ArrayList<EventHandler> list_event_handler = new ArrayList<EventHandler>();


    public EventDispatcher() {
        //eventos do teclado
        list_event.add("UP_PRESS");
        list_event.add("UP_RELEASE");
        list_event.add("DOWN_PRESS");
        list_event.add("DOWN_RELEASE");
        list_event.add("LEFT_PRESS");
        list_event.add("LEFT_RELEASE");
        list_event.add("RIGHT_PRESS");
        list_event.add("RIGHT_RELEASE");
        list_event.add("PAGE_UP_PRESS");
        list_event.add("PAGE_UP_RELEASE");
        list_event.add("PAGE_DOWN_PRESS");
        list_event.add("PAGE_DOWN_RELEASE");
        list_event.add("ESCAPE_PRESS");
        list_event.add("ESCAPE_RELEASE");
        list_event.add("A_PRESS");
        list_event.add("A_RELEASE");
        list_event.add("S_PRESS");
        list_event.add("S_RELEASE");
        list_event.add("D_PRESS");
        list_event.add("D_RELEASE");
        list_event.add("W_PRESS");
        list_event.add("W_RELEASE");
        list_event.add("Q_PRESS");
        list_event.add("Q_RELEASE");
        list_event.add("E_PRESS");
        list_event.add("E_RELEASE");
        list_event.add("SPACE_PRESS");
        list_event.add("SPACE_RELEASE");
        list_event.add("F1_PRESS");
        list_event.add("F1_RELEASE");
        list_event.add("F2_PRESS");
        list_event.add("F2_RELEASE");
        list_event.add("F3_PRESS");
        list_event.add("F3_RELEASE");
        list_event.add("F4_PRESS");
        list_event.add("F4_RELEASE");
        list_event.add("F5_PRESS");
        list_event.add("F5_RELEASE");
        list_event.add("F6_PRESS");
        list_event.add("F6_RELEASE");
        list_event.add("F7_PRESS");
        list_event.add("F7_RELEASE");
        list_event.add("F8_PRESS");
        list_event.add("F8_RELEASE");
        list_event.add("F9_PRESS");
        list_event.add("F9_RELEASE");
        list_event.add("SHIFT_PRESS");
        list_event.add("SHIFT_RELEASE");

        //eventos do mouse
        list_event.add("MOVE_X_LEFT");
        list_event.add("MOVE_X_RIGHT");
        list_event.add("MOVE_Y_UP");
        list_event.add("MOVE_Y_DOWN");
        list_event.add("MOUSE_DEAD_ZONE");
    }


    public void addEventHandler(EventHandler newEventHandler) {
        list_event_handler.add(newEventHandler);
    }


    //retorna true se removeu e false se nao existe o evento
    public boolean removeEventHandler(EventHandler newEventHandler) {
        return list_event_handler.remove(newEventHandler);
    }


    public void clearListEventHandler() {
        list_event_handler.clear();
    }


    //envia os eventos para todas as classes que implementa o doEvent
    public void sendEvent(String event) {
        String e = event;

        //envia eventos para todos
        for (int i = 0; i < list_event_handler.size(); i++) {
            list_event_handler.get(i).doEvent(e);
        }
    }


    public static void main(String[] args) {
        EventDispatcher ev = new EventDispatcher();

        for (int i = 0; i < EventDispatcher.list_event.size(); i++) {
            System.out.println(EventDispatcher.list_event.get(i));
        }
    }
}
