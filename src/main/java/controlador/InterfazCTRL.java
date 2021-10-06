package controlador;

import modelo.procesar.Analizador;
import modelo.tablas.GeneradorTabla;
import modelo.tokens.Token;
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
    private final String[] TITULOS = {"Token", "Tipo de Token"};
    //Objeto para generar la tabla
    private GeneradorTabla<Token> generadorTabla;

    public InterfazCTRL(InterfazGUI interfazGui) {
        this.interfazGui = interfazGui;

        interfazGui.getBtnLimpiar().addActionListener(this);
        interfazGui.getBtnProcesar().addActionListener(this);

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
        if (e.getSource() == interfazGui.getBtnProcesar()) {
            //limpia la lista de Tokens
            tokenList.clear();

            //Con un SeparadorLineas obtenemos el texto del TxTArea como ArrayList separado por lineas.
            SeparadorLineasCTRL separadorLineasCTRL = new SeparadorLineasCTRL(interfazGui.getTxaTexto());
            List<String> lineas = separadorLineasCTRL.separarLineas();
            //para cada linea usaremos un analizador
            for (String s : lineas) {
                Analizador analizador = new Analizador(s);
                analizador.analizar();
                //para cada pareja de tokens y sus tipos dentro del analizador creamos un objeto ToKen
                for (int i = 0; i < analizador.getTockens().size(); i++) {
                    Token token = Token.builder()
                            .tocken(analizador.getTockens().get(i))
                            .tipoToken(analizador.getTipoTockens().get(i).name()).build();
                    //agregamos el Token creado a la Lista de Tokens que se agregaran a la tabla.
                    tokenList.add(token);
                }
            }
            //Generamos la tabla a partir de la lista de Tokens
            generadorTabla.generar(tokenList);
        } else if (e.getSource() == interfazGui.getBtnLimpiar()) {
            interfazGui.getTxaTexto().setText("");
            generadorTabla.limpiar();
            //limpia la lista de Tokens
            tokenList.clear();
        }
    }
}
