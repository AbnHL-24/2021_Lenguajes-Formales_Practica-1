import controlador.InterfazCTRL;
import controlador.TextEditorController;
import vista.InterfazGUI;
import vista.TextEditorView;

public class Main {

    public static void main(String[] args) {

        TextEditorView textEditorView = new TextEditorView();
        TextEditorController textEditorController = new TextEditorController(textEditorView);
        textEditorController.start();
        /*
        InterfazGUI interfazGUI = new InterfazGUI();
        InterfazCTRL interfazCTRL = new InterfazCTRL(interfazGUI);
        interfazCTRL.iniciar(); */

    }
}
