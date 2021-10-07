package controlador.archivos;

import java.io.*;

/**
 * Esta clase se encarga de crear y leer archivos bianrios.
 * @param <T> Tipo de objeto con el que se va a trabajar.
 * @author abnerhl
 */
public class ManejarArchivoBinario<T> {

    //Escribe un objeto T (de cualquier tipo) en una ruta
    public void crearArchivoBinario(String ruta, T t) {
        File archivo = new File(ruta);
        try (FileOutputStream fileOutputStream = new FileOutputStream(archivo);
             ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    //Lee (retorna) un objeto T (de cualquier tipo) de una ruta
    public T leerArchivoBinario(String ruta) {
        File archivo  = new File(ruta);
        try (FileInputStream fileInputStream = new FileInputStream(archivo);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
