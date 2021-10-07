package modelo.afd.simbolos;

public class Agrupacion {

    public static boolean isAgrupacion(char c) {
        if ("(".equals(String.valueOf(c)) | ")".equals(String.valueOf(c))
                | "[".equals(String.valueOf(c)) | "]".equals(String.valueOf(c))
        | "{".equals(String.valueOf(c)) | "}".equals(String.valueOf(c))) {
            return true;
        } else {
            return false;
        }
    }
}
