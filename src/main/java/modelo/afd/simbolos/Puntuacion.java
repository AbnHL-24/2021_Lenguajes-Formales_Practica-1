package modelo.afd.simbolos;

public class Puntuacion {

    public static boolean isPuntuacion(char c) {
        if (".".equals(c) | ",".equals(c) | ";".equals(c) | ":".equals(c)) {
            return true;
        } else {
            return false;
        }
    }
}
