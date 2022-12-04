import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Utilidades {
    //Método para adicionar a próxima linha de um arquivo na área de texto
    public static void append(String linha, JTextPane areaTexto) {
        try {
            Document doc = areaTexto.getDocument();
            doc.insertString(doc.getLength(), linha, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

}
