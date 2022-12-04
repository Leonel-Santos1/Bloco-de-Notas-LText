import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.ArrayList;

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
    //----------------------------------------------------------------------

    //Métodos para mostrar a numeração das linhas das páginas

    public static void verNumeracaoInicial(boolean numeracao, JTextPane areaTexto, JScrollPane scroll) {
        if (numeracao) {
            scroll.setRowHeaderView(new TextLineNumber(areaTexto));
        } else {
            scroll.setRowHeaderView(null);
        }
    }

    public static void verNumeracao(int contador, boolean numeracao, ArrayList<JTextPane> areaTexto, ArrayList<JScrollPane> scroll) {
        if (numeracao) {
            for (int i = 0; i < contador; i++) {
                scroll.get(i).setRowHeaderView(new TextLineNumber(areaTexto.get(i)));
            }
        }else {
            for(int i = 0; i < contador; i++) {
                scroll.get(i).setRowHeaderView(null);
            }
        }
    }
}
