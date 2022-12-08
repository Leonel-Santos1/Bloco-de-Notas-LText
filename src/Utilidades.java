import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.*;
import java.util.*;

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
        } else {
            for (int i = 0; i < contador; i++) {
                scroll.get(i).setRowHeaderView(null);
            }
        }
    }
    //----------------------------------------------------------------------

    //----------------------Aparencia da janela-----------------------------

    public static void mudarAparencia(int contador, String tipo, ArrayList<JTextPane> listAreaTexto) {
        if (tipo.equals("w")) {
            for (int i = 0; i < contador; i++) {

                listAreaTexto.get(i).selectAll();

                StyleContext sc = StyleContext.getDefaultStyleContext();

                //Cor do Texto
                AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

                //O estilo do texto
                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

                listAreaTexto.get(i).setCharacterAttributes(aset, false);
                listAreaTexto.get(i).setBackground(Color.WHITE);
            }
        } else if (tipo.equals("d")) {
            for (int i = 0; i < contador; i++) {

                listAreaTexto.get(i).selectAll();

                StyleContext sc = StyleContext.getDefaultStyleContext();

                //Cor do Texto
                AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(255, 255, 255));

                //O estilo do texto
                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

                listAreaTexto.get(i).setCharacterAttributes(aset, false);
                listAreaTexto.get(i).setBackground(new Color(77, 77, 77));

            }
        }
    }
    //----------------------------------------------------------------------

    //----------------------Botão------------------------------------------

    public static JButton adicionaButao(URL url, Object objContainer, String rotulo){
        JButton botao = new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        botao.setToolTipText(rotulo);
        ((Container) objContainer).add(botao);
        return botao;
    }


    //---------------------------------------------------------------------
}
