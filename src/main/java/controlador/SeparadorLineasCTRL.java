package controlador;

import lombok.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SeparadorLineasCTRL {
    JTextArea jTxtArea;

    public List separarLineas() {
        String[] lineas = jTxtArea.getText().split("\n");
        List<String> lineasR = new ArrayList();

        for (String s : lineas) {
            lineasR.add(s);
        }

        return lineasR;
    }

}
