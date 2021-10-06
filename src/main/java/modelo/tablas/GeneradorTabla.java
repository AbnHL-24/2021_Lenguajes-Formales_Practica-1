package modelo.tablas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Clase que se utiliza para llenar una JTable
 * @param <T> Objeto T que extiende de Arrayable
 * @see Arrayable
 * @author abnerhl
 */
public class GeneradorTabla<T extends Arrayable> {
    private JTable tabla;
    private String[] titulos;

    public GeneradorTabla(JTable tabla, String[] titulos) {
        this.tabla = tabla;
        this.titulos = titulos;
    }
    public void generar(List<T> objetos) {
        limpiar();
        DefaultTableModel model = new DefaultTableModel();
        for (String t : titulos) {
            model.addColumn(t);
        }
        for (T o : objetos) {
            model.addRow(o.toArray());
        }
        tabla.setModel(model);
    }

    public void limpiar() {
        tabla.setModel(new DefaultTableModel());
    }

}
