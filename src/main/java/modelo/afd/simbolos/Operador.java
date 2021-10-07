package modelo.afd.simbolos;

public class Operador {

    public static boolean isOperador(char c) {
        if ("+".equals(c) | "-".equals(c) | "*".equals(c) | "/".equals(c) | "%".equals(c)) {
            return true;
        } else {
            return false;
        }
    }
}
