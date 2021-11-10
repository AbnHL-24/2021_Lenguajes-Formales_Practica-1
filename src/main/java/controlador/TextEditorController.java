package controlador;


import modelo.afd.AnalizadorAFD;
import modelo.tablas.GeneradorTabla;
import modelo.tokens.TiposDeTokens;
import modelo.tokens.Token;
import vista.TextEditorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import static controlador.archivos.ManejarArchivo.*;

/**
 *
 * @author abner
 */
public class TextEditorController extends WindowAdapter implements ActionListener,
        DocumentListener, UndoableEditListener, CaretListener {

    private final UndoManager undoManager = new UndoManager();
    private final TextEditorView textEditorV;
    private boolean hasChanges = false;
    private String path = "";
    boolean erroresLexico = false;

    private List<Token> tokenList = new ArrayList<>();
    //Titulos de la tabla
    private final String[] TITULOS = {"Token", "Tipo de Token", "Fila", "Columna"};
    //Objeto para generar la tabla
    private GeneradorTabla<Token> generadorTabla;

    public TextEditorController(TextEditorView textEditorV) {
        this.textEditorV = textEditorV;

        this.textEditorV.getItmAbrir().addActionListener(this);
        this.textEditorV.getItmSave().addActionListener(this);
        this.textEditorV.getItmSaveAs().addActionListener(this);
        this.textEditorV.getItmNuevo().addActionListener(this);

        this.textEditorV.getItmRehacer().addActionListener(this);
        this.textEditorV.getItmDeshacer().addActionListener(this);
        this.textEditorV.getItmCopiar().addActionListener(this);
        this.textEditorV.getItmCortar().addActionListener(this);
        this.textEditorV.getItmPegar().addActionListener(this);

        this.textEditorV.getItmManual().addActionListener(this);
        this.textEditorV.getTxaTexto().addCaretListener(this);

        this.textEditorV.addWindowListener(this);

        this.textEditorV.getBtmProcesar().addActionListener(this);
        this.textEditorV.getBtnLimpiar().addActionListener(this);

        //Creamos un objeto tabla y le asignamos la tabla a llenar y sus titulos
        generadorTabla = new GeneradorTabla<>(this.textEditorV.getTblTabla(), TITULOS);
    }

    public void start() {
        this.textEditorV.pack();
        textEditorV.setResizable(false);
        textEditorV.setLocationRelativeTo(null);
        this.textEditorV.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.textEditorV.setTitle("Documento sin guardar");
        this.textEditorV.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.textEditorV.getItmAbrir() == e.getSource()) {
            abrir();
        } else if (this.textEditorV.getItmNuevo() == e.getSource()) {
            nuevo();
        } else if (this.textEditorV.getItmSave() == e.getSource()) {
            guardar();
        } else if (this.textEditorV.getItmSaveAs() == e.getSource()) {
            guardarComo();
        } else if (this.textEditorV.getItmRehacer() == e.getSource()) {
            rehacer();
        } else if (this.textEditorV.getItmDeshacer() == e.getSource()) {
            deshacer();
        } else if (this.textEditorV.getItmManual() == e.getSource()) {

        } else if (e.getSource() == textEditorV.getBtmProcesar()) {
            //limpia la lista de Tokens
            tokenList.clear();

            //Con un SeparadorLineas obtenemos el texto del TxTArea como ArrayList separado por lineas.
            SeparadorLineasCTRL separadorLineasCTRL = new SeparadorLineasCTRL(textEditorV.getTxaTexto());
            List<String> lineas = separadorLineasCTRL.separarLineas();
            //para cada linea usaremos un analizador

            //nuevo analizador
            AnalizadorAFD analizador = new AnalizadorAFD(textEditorV.getTxaTexto().getText());
            analizador.analizar();
            //para cada pareja de tokens y sus tipos dentro del analizador creamos un objeto ToKen
            for (int i = 0; i < analizador.getTockens().size(); i++) {
                Token token = Token.builder()
                        .tocken(analizador.getTockens().get(i))
                        .tipoToken(analizador.getTipoTockens().get(i).name())
                        .indice(analizador.getColumnas().get(i))
                        .fila(analizador.getFilas().get(i))
                        .build();
                //agregamos el Token creado a la Lista de Tokens que se agregaran a la tabla.
                tokenList.add(token);
            }
            /*
            for (String s : lineas) {
                linea++;
                AnalizadorAFD analizador = new AnalizadorAFD(s);
                analizador.analizar();
                //para cada pareja de tokens y sus tipos dentro del analizador creamos un objeto ToKen
                for (int i = 0; i < analizador.getTockens().size(); i++) {
                    Token token = Token.builder()
                            .tocken(analizador.getTockens().get(i))
                            .tipoToken(analizador.getTipoTockens().get(i).name())
                            .indice(analizador.getIndices().get(i))
                            .fila(analizador.getFila())
                            .build();
                    //agregamos el Token creado a la Lista de Tokens que se agregaran a la tabla.
                    tokenList.add(token);
                    System.out.println("prueba");
                }
            }*/
            //Generamos la tabla a partir de la lista de Tokens

            for (Token t : tokenList) {
                if (t.getTipoToken().equals(TiposDeTokens.ERROR.name())) {
                    erroresLexico = true;
                    break;
                }
            }
            if (erroresLexico) {
                List<Token> tokenListImp = new ArrayList<>();
                for (Token t : tokenList) {
                    if (t.getTipoToken().equals(TiposDeTokens.ERROR.name())) {
                        tokenListImp.add(t);
                    }
                }
                generadorTabla.generar(tokenListImp);
            } else {
                generadorTabla.generar(tokenList);
            }
        } else if (e.getSource() == textEditorV.getBtnLimpiar()) {
            textEditorV.getTxaTexto().setText("");
            generadorTabla.limpiar();
            //limpia la lista de Tokens
            tokenList.clear();
        }
    }

    private String getContentFile() {
        String content = "";

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("FORM Documents", "form"));
        fc.setAcceptAllFileFilterUsed(false);
        fc.showOpenDialog(this.textEditorV);
        try {
            path = fc.getSelectedFile().getAbsolutePath();
            content = readFile(path);
        } catch (Exception ex) {
            System.out.println("se cancelo");
        }

        return content;
    }

    private void abrir() {
        if (!hasChanges) {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(this.textEditorV);
            try {
                path = fc.getSelectedFile().getAbsolutePath();
                this.textEditorV.getTxaTexto().setText(readFile(path));
                this.textEditorV.setTitle(path);
                hasChanges = false;
            } catch (Exception ex) {
                System.out.println("se cancelo");
            }
        } else {
            cambiosSinGuardar(1);
        }
    }

    private void nuevo() {
        if (!hasChanges) {
            this.textEditorV.getTxaTexto().setText("");
            this.textEditorV.setTitle("Documento sin guardar");
            path = "";
            hasChanges = false;
        } else {
            cambiosSinGuardar(2);
        }
    }

    private void guardar() {
        String texto = this.textEditorV.getTxaTexto().getText();
        if (verifyFile(path)) {
            saveFile(path, texto);
            this.textEditorV.setTitle(path);
            hasChanges = false;
        } else {
            JFileChooser fc = new JFileChooser();
            try {
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        path = fc.getSelectedFile().getAbsolutePath() + ".txt";
                        saveFile(path, texto);
                        hasChanges = false;
                    } catch (Exception ex) {
                        System.out.println("se cancelo");
                        ex.printStackTrace(System.out);
                    }
                }
            } catch (HeadlessException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    private void guardarComo() {
        String texto = this.textEditorV.getTxaTexto().getText();
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Guardar Como");
        try {
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    path = fc.getSelectedFile().getAbsolutePath() + ".txt";
                    saveFile(path, texto);
                    this.textEditorV.setTitle(path);
                    hasChanges = false;
                } catch (Exception e) {
                    System.out.println("se cancelo");
                }
            }
        } catch (HeadlessException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void cambiosSinGuardar(int op) {
        String[] options = {"Guardar Cambios", "Desechar Cambios", "Cancelar"};
        int selection = JOptionPane.showOptionDialog(null, "Hay cambios sin guardar!",
                "Informacion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (selection) {
            case 0 ->
                guardar();
            case 1 -> {
                switch (op) {
                    case 0 ->
                        System.exit(0);
                    case 1 -> {
                        hasChanges = false;
                        abrir();
                    }
                    case 2 -> {
                        hasChanges = false;
                        nuevo();
                    }
                }
            }
            case 2 -> {
            }
        }
    }

    private void rehacer() {
        try {
            undoManager.redo();
        } catch (CannotRedoException e) {
        }
    }

    private void deshacer() {
        try {
            undoManager.undo();
        } catch (CannotUndoException e) {
        }
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        hasChanges = true;
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        hasChanges = true;
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        hasChanges = true;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!hasChanges) {
            System.exit(0);
        } else {
            cambiosSinGuardar(0);
        }
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        int linea = 1;
        int columna = 1;

        try {
            int caretPos = this.textEditorV.getTxaTexto().getCaretPosition();
            linea = this.textEditorV.getTxaTexto().getLineOfOffset(caretPos);
            columna = caretPos - this.textEditorV.getTxaTexto().getLineStartOffset(linea);
            linea++;
            columna++;

        } catch (BadLocationException e) {
        }
            this.textEditorV.getLblPosCaret().setText("Linea: " + linea + "  -  Columna: " + columna);
    }
}
