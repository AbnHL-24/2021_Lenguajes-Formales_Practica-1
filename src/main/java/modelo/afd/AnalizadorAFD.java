package modelo.afd;

import lombok.Getter;
import lombok.Setter;
import modelo.afd.estados.Estados;
import modelo.afd.tockens.Tockens;

import java.util.ArrayList;

@Getter
@Setter
public class AnalizadorAFD {
    Estados estado = Estados.S0;
    int columna = -1;
    int fila = 1;
    Tockens tipoTocken = Tockens.NULL;
    String token = "";
    String cadena;

    ArrayList<String> tockens = new ArrayList<>();
    ArrayList<Tockens> tipoTockens = new ArrayList<>();
    //ArrayList<Integer> indices = new ArrayList<>();
    ArrayList<Integer> filas = new ArrayList<>();
    ArrayList<Integer> columnas = new ArrayList<>();

    public AnalizadorAFD(String cadenaRecibida) {
        this.cadena = cadenaRecibida;
    }

    public void analizar() {
        char[] charsTmp = cadena.toCharArray();
        char[] chars = new char[charsTmp.length + 1];
        //Agrego un salto de linea al finalizar para asegurar que el ultimo token sea analizado y agregado a las listas
        System.arraycopy(charsTmp, 0, chars, 0, charsTmp.length);
        chars[charsTmp.length] = '\n';

        for (Character c : chars) {
            //aunmenta el indice para saber que numero de caracter es
            columna++;
               switch (estado) {
                   case S0 -> {
                       //columnas.add(columna);
                       //filas.add(fila);
                       if (String.valueOf(c).equals("_")) { // _
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (Character.isLetter(c)) {  // letras
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (String.valueOf(c).equals("-")) {  // -
                           estado = Estados.S3;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if (Character.isDigit(c)) {   // digitos
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if (String.valueOf(c).equals("\"")) {
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.LITERAL;
                       } else if ( c.equals('\n')) {
                           estado = Estados.S0;
                           tipoTocken = Tockens.NULL;
                           fila++;
                           columna =-1;
                       } else if (c.equals('/')) {
                           estado = Estados.S5;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.COMENTARIODEUNALINEA;
                       } else if (c.equals('=')) {
                           estado = Estados.S0;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ASIGNACION;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           columnas.add(columna);
                       }
                        // y tambien los signos de asignacion
                       else if (Character.isSpaceChar(c) | Character.isWhitespace(c)) {
                           estado = Estados.S0;
                           tipoTocken = Tockens.NULL;
                       }
                   }
                   //identificadores
                   case S1 -> {
                       if (Character.isLetter(c)){  // letras
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (String.valueOf(c).equals("_")) {  // _
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (String.valueOf(c).equals("-")) {  // -
                       estado = Estados.S2;
                       token += String.valueOf(c);
                       tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (Character.isDigit(c)) {   //digitos
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if ( c.equals('\n')) { // salto de linea
                           estado = Estados.S0;
                           if (token.equals("ESCRIBIR") | token.equals("FIN") | token.equals("REPETIR") |
                                   token.equals("INICIAR") | token.equals("SI") | token.equals("VERDADERO") |
                                   token.equals("FALSO") | token.equals("ENTONCES")) {
                               tipoTocken = Tockens.PALABRARESERVADA;
                           }
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (Character.isSpaceChar(c) | Character.isWhitespace(c)) {   // espacio
                           estado = Estados.S0;
                           if (token.equals("ESCRIBIR") | token.equals("FIN") | token.equals("REPETIR") |
                                token.equals("INICIAR") | token.equals("SI") | token.equals("VERDADERO") |
                                token.equals("FALSO") | token.equals("ENTONCES")) {
                                tipoTocken = Tockens.PALABRARESERVADA;
                           }
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           columnas.add(columna);
                       } else { // error
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   //numeros
                   case S2 -> {
                       if (Character.isDigit(c)) {   //digitos
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if ( c.equals('\n')) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (Character.isSpaceChar(c) | Character.isWhitespace(c)) {   // espacio
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           columnas.add(columna);
                       } else { // error
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   // veridicar que despues del guion venga un numero
                   case S3 -> {
                       if (Character.isDigit(c)) {   //digitos
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if (c.equals('\n')){
                           estado = Estados.S0;
                           tipoTocken = Tockens.IDENTIFICADOR;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (Character.isWhitespace(c)){
                           estado = Estados.S0;
                           tipoTocken = Tockens.IDENTIFICADOR;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           columnas.add(columna);
                       } else { // error
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   // literales
                   case S4 -> {
                       if (String.valueOf(c).equals("\"")){  // captacion comillas
                           estado = Estados.S0;
                           token += String.valueOf(c);
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if ( c.equals('\n')) {
                           /*
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                            */
                           tipoTocken = Tockens.ERROR;
                           estado = Estados.S0;
                           token += String.valueOf(c);
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (c.equals('\t')) {
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.LITERAL;
                       } else { // agrego lo que sea que viene y regresoa  S4
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.LITERAL;
                       }
                   }
                   case S5 -> { //comentarios
                       if (c.equals('/')) { // verifica el segundo / y manda al cuerpo de comentario
                           estado = Estados.S6;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.COMENTARIODEUNALINEA;
                       } else { // error
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   case S6 -> { //cuerpo de los comentarios
                       if ( c.equals('\n')) {
                           tipoTocken = Tockens.COMENTARIODEUNALINEA;
                           estado = Estados.S0;
                           token += String.valueOf(c);
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (c.equals('\t')) {
                           estado = Estados.S6;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.COMENTARIODEUNALINEA;
                       } else { // agrego el resto de lo que viene a los comentarios
                           estado = Estados.S6;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.COMENTARIODEUNALINEA;
                       }
                   }
                   case ERROR -> {
                       if (c.equals('\n')) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           fila++;
                           columnas.add(columna);
                           columna = -1;
                       } else if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                           filas.add(fila);
                           columnas.add(columna);
                       } else {
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
               }
        }
    }
}
