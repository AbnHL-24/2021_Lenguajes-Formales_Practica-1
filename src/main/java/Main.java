import controlador.InterfazCTRL;
import vista.InterfazGUI;

public class Main {

    public static void main(String[] args) {
        InterfazGUI interfazGUI = new InterfazGUI();
        InterfazCTRL interfazCTRL = new InterfazCTRL(interfazGUI);
        interfazCTRL.iniciar();
    }
}
