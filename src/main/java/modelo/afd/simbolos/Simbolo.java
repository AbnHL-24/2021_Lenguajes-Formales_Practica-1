package modelo.afd.simbolos;

import static modelo.afd.simbolos.Agrupacion.isAgrupacion;
import static modelo.afd.simbolos.Operador.isOperador;
import static modelo.afd.simbolos.Puntuacion.isPuntuacion;

public class Simbolo {

    public static boolean isSimbolo(char c) {
        if (isAgrupacion(c) | isOperador(c) | isPuntuacion(c)) {
            return true;
        } else {
            return false;
        }
    }
}
