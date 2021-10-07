package controlador;

import vista.CargarRutaArchivoGUI;
import vista.InterfazGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static controlador.archivos.ManejarArchivo.leerArchivo;

public class CargarRutaArchivoCTRL implements ActionListener {
    CargarRutaArchivoGUI cargarRutaArchivoGUI;
    InterfazGUI interfazGUI;
    String texto= null;

    public CargarRutaArchivoCTRL(CargarRutaArchivoGUI cargarRutaArchivoGUI, InterfazGUI interfazGUI) {
        this.cargarRutaArchivoGUI = cargarRutaArchivoGUI;
        this.interfazGUI = interfazGUI;
        cargarRutaArchivoGUI.getBtnCargarRuta().addActionListener(this);
    }

    public void iniciar() {
        cargarRutaArchivoGUI.setResizable(false);
        cargarRutaArchivoGUI.setLocationRelativeTo(null);
        cargarRutaArchivoGUI.setVisible(true);
    }
    public void acabar() {
        cargarRutaArchivoGUI.setResizable(false);
        cargarRutaArchivoGUI.setLocationRelativeTo(null);
        cargarRutaArchivoGUI.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cargarRutaArchivoGUI.getBtnCargarRuta()) {
            String ruta = cargarRutaArchivoGUI.getTxfRutaArchivo().getText();

            ArrayList<String> lineas = leerArchivo(ruta);

            texto = "";

            for (String s : lineas) {
                texto += s + "\n";
            }

            interfazGUI.getTxaTexto().setText(texto);
            acabar();
        }
    }

    public String getTexto() {
        return texto;
    }
}
