import controlador.InterfazCTRL;
import vista.InterfazGUI;

public class Main {

    public static void main(String[] args) {
        //System.out.println("1" + '\n' + "1");
        InterfazGUI interfazGUI = new InterfazGUI();
        InterfazCTRL interfazCTRL = new InterfazCTRL(interfazGUI);
        interfazCTRL.iniciar();

    }
}
