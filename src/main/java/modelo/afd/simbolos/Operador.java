package modelo.afd.simbolos;

public class Operador {

    public static boolean isOperador(char c) {
        if ("+".equals(String.valueOf(c)) | "-".equals(String.valueOf(c))
                | "*".equals(String.valueOf(c)) | "/".equals(String.valueOf(c))
                | "%".equals(String.valueOf(c))) {
            return true;
        } else {
            return false;
        }
    }
}
