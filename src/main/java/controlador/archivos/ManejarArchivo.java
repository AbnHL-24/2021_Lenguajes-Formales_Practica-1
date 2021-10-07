package controlador.archivos;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase se encarga de leer el archivo de carga de datos del sistema anterior de la empresa AeroBalamDevs.
 * @author abnerhl
 */
public class ManejarArchivo {

    /**
     * Obtiene un ArrayList con los datos provenientes de un archivo de texto.
     * @param path Ruta donde se encuentra el archivo de texto.
     * @return ArrayList de los datos leidos.
     */
    public static ArrayList<String> leerArchivo(String path) {
        ArrayList<String> datos = new ArrayList<>();
        try {
            File archivo = new File(path);
            Scanner lectura = new Scanner(archivo);
            while (lectura.hasNext()) {
                datos.add(lectura.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontró el archivo");
        }
        return datos;
    }

    /**
     * Guarda una linea de texro en un archivo.
     * @param path direcicon con nombre del archivo a crear.
     * @param texto cadena que se va a guardar.
     */
    public static void guardarArchivo(String path, String texto) {
        try ( PrintWriter escribir = new PrintWriter(path) ) {
            escribir.print(texto);
        } catch (IOException ex) {
            System.out.println("No se encontró el archivo");
        }
    }

    /**
     *
     * @param path
     * @param texto
     */
    public static void agregarAUnArchivoTXT(String path, String texto) {
        try {
            FileWriter archivo = new FileWriter(path, true);
            try(BufferedWriter almacen = new BufferedWriter(archivo)) {
                almacen.write(texto + "\n");
                almacen.close();
            }
            archivo.close();
        } catch (Exception ex) {
            System.out.println("Errores al escribir el archivo");
        }
    }

    public static void guardarArrayListStringEnTXT(String path, ArrayList<String> textos) {
        for (String texto : textos) {
            try ( PrintWriter escribir = new PrintWriter(path) ) {
                escribir.print(texto + "\n");
            } catch (IOException ex) {
                System.out.println("No se encontró el archivo");
            }
        }
    }

    /**
     * Borra un archivo.
     * @param path direccion del archivo.
     * @return Booleano que indica si un archivo fue borrado.
     */
    public static boolean borrarArchivo(String path) {
        return new File(path).delete();
    }

    public static boolean borrarDirectorio(File file) {

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                borrarDirectorio(f);
            }
        }
        return file.delete();
    }

    /**
     *  Si un directorio no existe, lo crea.
     * @param directory directorio a crear.
     */
    public static void crearDirectorio(String directory) {
        File directorio = new File(directory);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    public static List<String> obtenerNombresArchivos(String path) {
        File file = new File(path);
        List<String> fileNames = new ArrayList();

        if (file.exists()) {
            fileNames = Arrays.asList(file.list());
        }
        return fileNames;
    }

    public static boolean verificarArchivo(String archivo) {
        return new File(archivo).exists();
    }
}
