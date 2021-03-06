package controlador;

import modelo.afd.AnalizadorAFD;
import modelo.procesar.Analizador;
import modelo.tablas.GeneradorTabla;
import modelo.tokens.Token;
import vista.CargarRutaArchivoGUI;
import vista.InterfazGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfazCTRL implements ActionListener {
    private InterfazGUI interfazGui;
    //Lista con los tokens a ingresar en la tabla
    private List<Token> tokenList = new ArrayList<>();
    //Titulos de la tabla
    private final String[] TITULOS = {"Token", "Tipo de Token", "Fila", "Columna"};
    //Objeto para generar la tabla
    private GeneradorTabla<Token> generadorTabla;


    public InterfazCTRL(InterfazGUI interfazGui) {
        this.interfazGui = interfazGui;

        interfazGui.getBtnLimpiar().addActionListener(this);
        interfazGui.getBtnProcesar().addActionListener(this);
        interfazGui.getBtnCargar().addActionListener(this);
        interfazGui.getBtnGuardar().addActionListener(this);

        //Creamos un objeto tabla y le asignamos la tabla a llenar y sus titulos
        generadorTabla = new GeneradorTabla<>(this.interfazGui.getTblTabla(), TITULOS);
    }

    public void iniciar() {
        interfazGui.setResizable(false);
        interfazGui.setLocationRelativeTo(null);
        interfazGui.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int linea = 0;
        if (e.getSource() == interfazGui.getBtnProcesar()) {
            //limpia la lista de Tokens
            tokenList.clear();

            //Con un SeparadorLineas obtenemos el texto del TxTArea como ArrayList separado por lineas.
            SeparadorLineasCTRL separadorLineasCTRL = new SeparadorLineasCTRL(interfazGui.getTxaTexto());
            List<String> lineas = separadorLineasCTRL.separarLineas();
            //para cada linea usaremos un analizador

            //nuevo analizador
            AnalizadorAFD analizador = new AnalizadorAFD(interfazGui.getTxaTexto().getText());
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
            generadorTabla.generar(tokenList);
        } else if (e.getSource() == interfazGui.getBtnLimpiar()) {
            interfazGui.getTxaTexto().setText("");
            generadorTabla.limpiar();
            //limpia la lista de Tokens
            tokenList.clear();
        } else if (e.getSource() == interfazGui.getBtnCargar()) {
            CargarRutaArchivoGUI cargarRutaArchivoGUI = new CargarRutaArchivoGUI();
            CargarRutaArchivoCTRL cargarRutaArchivoCTRL = new CargarRutaArchivoCTRL(cargarRutaArchivoGUI, interfazGui);
            cargarRutaArchivoCTRL.iniciar();
            /*if (cargarRutaArchivoCTRL.getTexto() != null) {
                interfazGui.getTxaTexto().setText(cargarRutaArchivoCTRL.getTexto());
            }*/
        }
    }
}
