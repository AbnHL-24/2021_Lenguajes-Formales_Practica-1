package modelo.afd;

import lombok.Getter;
import lombok.Setter;
import modelo.afd.estados.Estados;
import modelo.afd.tockens.Tockens;
import modelo.tokens.TiposDeTokens;

import java.util.ArrayList;

import static modelo.afd.simbolos.Agrupacion.isAgrupacion;
import static modelo.afd.simbolos.Operador.isOperador;
import static modelo.afd.simbolos.Puntuacion.isPuntuacion;

@Getter
@Setter
public class AnalizadorAFD {
    Estados estado = Estados.S0;
    int indice = 0;
    Tockens tipoTocken = Tockens.NULL;
    String token = "";
    String cadena;

    ArrayList<String> tockens = new ArrayList<>();
    ArrayList<Tockens> tipoTockens = new ArrayList<>();
    ArrayList<Integer> indices = new ArrayList<>();

    public AnalizadorAFD(String cadenaRecibida) {
        this.cadena = cadenaRecibida;
    }

    public void analizar() {
        char[] charsTmp = cadena.toCharArray();
        char[] chars = new char[charsTmp.length + 1];
        //Agrego un espacio al finalizar para asegurar que el ultimo token sea analizado y agregado a las listas
        System.arraycopy(charsTmp, 0, chars, 0, charsTmp.length);
        chars[charsTmp.length] = ' ';

        for (char c : chars) {
            //aunmenta el indice para saber que numero de caracter es
            indice++;
               switch (estado) {
                   case S0 -> {
                       indices.add(indice);
                       if (Character.isLetter(c)){
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (Character.isDigit(c)) {
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if (isAgrupacion(c)) {
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.AGRUPACION;
                       } else if (isOperador(c)) {
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.OPERADOR;
                       } else if (isPuntuacion(c)) {
                           estado = Estados.S4;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.PUNTUACION;
                       } else if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTocken = Tockens.NULL;
                       }
                   }
                   case S1 -> {
                       if (Character.isLetter(c)){
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (Character.isDigit(c)) {
                           estado = Estados.S1;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.IDENTIFICADOR;
                       } else if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                       } else {
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   case S2 -> {
                       if (Character.isDigit(c)) {
                           estado = Estados.S2;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.NUMERO;
                       } else if (".".equals(String.valueOf(c))) {
                           estado = Estados.S3;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.DECIMAL;
                       } else if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                       } else {
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   case S3 -> {
                       if (Character.isDigit(c)) {
                           estado = Estados.S3;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.DECIMAL;
                       } else if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
                       } else {
                           estado = Estados.ERROR;
                           token += String.valueOf(c);
                           tipoTocken = Tockens.ERROR;
                       }
                   }
                   case S4 -> {
                       estado = Estados.S0;
                       tipoTockens.add(tipoTocken);
                       tipoTocken = Tockens.NULL;
                       tockens.add(token);
                       token = "";
                   }
                   case S5 -> {
                       //Es espacio
                       //El VAR dice que aca no ha pasado nada, que se siga jugando.
                   }
                   case ERROR -> {
                       if (Character.isSpaceChar(c)) {
                           estado = Estados.S0;
                           tipoTockens.add(tipoTocken);
                           tipoTocken = Tockens.NULL;
                           tockens.add(token);
                           token = "";
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
