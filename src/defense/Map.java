/*
 * Nome: Jairo Toshio Tuboi
 * Nro USP: 6427150
 * Curso: Bacharelado em Ciencias de Computação - USP São Carlos
 * Materia: SCC0250 - Computação Gráfica
 *
 * Obs: uso da biblioteca XStream para abrir .xml
 */
package defense;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Map {
    //mapeamento do chao
    private int line;
    private int sizetile;
    private int x;
    private int y;
    private ArrayList<String> typename;
    private ArrayList<Integer> typenumber;
    private ArrayList<Integer> maptype;

    /*     0   1   2   3   4   ...
     *   +---+---+---+---+---+
     * 0 |   |   |   |   |   |
     *   +---+---+---+---+---+
     * 1 |   |   |   |   |   |
     *   +---+---+---+---+---+
     * 2 |   | i |   |   |   |
     *   +---+---+---+---+---+
     * 3 |   |   |   |   |   |
     *   +---+---+---+---+---+
     * 4 |   |   |   |   |   |
     *   +---+---+---+---+---+
     * ...
     * i = (x, z) = (1, 2)
     */

    public Map() {
    }


    public void setLine(int line) {
        this.line = line;
    }


    public void setSizeTile(int size) {
        this.sizetile = size;
    }


    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }


    public void setTypeName(ArrayList<String> type_name) {
        this.typename = type_name;
    }


    public void setTypeNumber(ArrayList<Integer> type_number) {
        this.typenumber = type_number;
    }


    public void setMapType(ArrayList<Integer> map_type) {
        this.maptype = map_type;
    }


    public int getLine() {
        return line;
    }


    public int getSizeTile() {
        return sizetile;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public ArrayList<String> getTypeName() {
        return typename;
    }


    public ArrayList<Integer> getTypeNumber() {
        return typenumber;
    }


    public ArrayList<Integer> getMapType() {
        return maptype;
    }


    public boolean openMap(String name) {
        Map map = null;
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("map", Map.class);
            File arquivo = new File("map/" + name + ".xml");
            FileInputStream arquivoXML = new FileInputStream(arquivo);
            Reader reader = new InputStreamReader(arquivoXML);
            ObjectInputStream in = xstream.createObjectInputStream(reader);

            map = (Map) in.readObject();

            line = map.line;
            sizetile = map.sizetile;
            x = map.x;
            y = map.y;
            typename = map.typename;
            typenumber = map.typenumber;
            maptype = map.maptype;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }


    public void createMap(Map map, String name) {
        //falta adicionar o cabecalho <object-stream> no arquivo
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("map", Map.class);
            FileOutputStream fos;
            fos = new FileOutputStream("map/" + name + ".xml");
            xstream.toXML(map, fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void main(String[] args) {
        Map map = new Map();
        map.setLine(51);

        map.setSizeTile(10);

        map.setX(25);
        map.setY(25);

        ArrayList<String> type = new ArrayList<String>();
        type.add("01sand");
        map.setTypeName(type);

        ArrayList<Integer> number = new ArrayList<Integer>();
        number.add(Integer.valueOf(0));
        map.setTypeNumber(number);

        ArrayList<Integer> maptype = new ArrayList<Integer>();
        for (int i = 0; i < 2601; i++) {
            maptype.add(Integer.valueOf(0));
        }
        map.setMapType(maptype);

        map.createMap(map, "05");

    }
}
