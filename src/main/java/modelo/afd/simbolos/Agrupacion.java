package modelo.afd.simbolos;

public class Agrupacion {

    public static boolean isAgrupacion(char c) {
        if ("(".equals(c) | ")".equals(c) | "[".equals(c) | "]".equals(c)
        | "{".equals(c) | "}".equals(c)) {
            return true;
        } else {
            return false;
        }
    }
}
