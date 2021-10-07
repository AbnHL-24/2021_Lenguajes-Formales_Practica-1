package modelo.afd.simbolos;

public class Puntuacion {

    public static boolean isPuntuacion(char c) {
        if (".".equals(String.valueOf(c)) | ",".equals(String.valueOf(c))
                | ";".equals(String.valueOf(c)) | ":".equals(String.valueOf(c))) {
            return true;
        } else {
            return false;
        }
    }
}
