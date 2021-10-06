package modelo.tablas;

/**
 * Interfaz que obliga a un objeto a generar el esquema para usar en la calse GeneradorTabla
 * @see GeneradorTabla
 * @author abnerhl
 */
public interface Arrayable {
        String[] toArray();
}
