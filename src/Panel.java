import javax.swing.*;
import java.awt.event.*;

public class Panel extends JPanel {
    public Panel() {

        //-----------Menu-----------
        JPanel panelMenu = new JPanel();

        menuBar = new JMenuBar();

        arquivo = new JMenu("Arquivo");
        editar = new JMenu("Editar");
        selecao = new JMenu("Seleção");
        ver = new JMenu("Visualização");
        aparencia = new JMenu("Aparência");

        menuBar.add(arquivo);
        menuBar.add(editar);
        menuBar.add(selecao);
        menuBar.add(ver);

        //-----------Elementos do Menu Arquivo-----------
        criaItem("Novo Arquivo", "arquivo", "novo");
        criaItem("Abrir Arquivo", "arquivo", "abrir");
        criaItem("Salvar", "arquivo", "salvar");
        criaItem("Salvar Como", "arquivo", "salvarComo");
        criaItem("Fechar", "arquivo", "fechar");
        //-----------------------------------------------

        //-----------Elementos do Menu Editar-----------
        criaItem("Desfazer", "editar", "");
        criaItem("Refazer", "editar", "");
        editar.addSeparator();
        criaItem("Recortar", "editar", "");
        criaItem("Copiar", "editar", "");
        criaItem("Colar", "editar", "");
        //-----------------------------------------------

        //-----------Elementos do Menu Seleção-----------
        criaItem("Selecionar Tudo", "selecao", "");
        //-----------------------------------------------

        //-----------Elementos do Menu Ver-----------
        criaItem("Numeração de Linhas", "ver", "");
        ver.add(aparencia);
        criaItem("Modo Claro", "aparencia", "");
        criaItem("Modo Escuro", "aparencia", "");
        //-----------------------------------------------

        panelMenu.add(menuBar);
        //------Area de Texto-------
        tPane = new JTabbedPane();

        //--------------------------

        creatPanel();

        add(panelMenu);
        add(tPane);
    }

    //Função para criar os itens do menu de forma mais prática
    public void criaItem(String nomeMenu, String menu, String acao) {
        elementoItem = new JMenuItem(nomeMenu);

        switch (menu) {
            case "arquivo" -> {
                arquivo.add(elementoItem);
                if (acao.equals("novo")) {
                    elementoItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            creatPanel();
                        }
                    });
                }
            }
            case "editar" -> editar.add(elementoItem);
            case "selecao" -> selecao.add(elementoItem);
            case "ver" -> ver.add(elementoItem);
            case "aparencia" -> aparencia.add(elementoItem);
        }

    }


    public void creatPanel() {
        janela = new JPanel();
        areaTexto = new JTextPane();

        janela.add(areaTexto);

        tPane.addTab("title", janela);
    }


    private JTabbedPane tPane;
    private JPanel janela;
    private JTextPane areaTexto;
    private JMenuBar menuBar;
    private JMenu arquivo;
    private JMenu editar;
    private JMenu selecao;
    private JMenu ver;
    private JMenu aparencia;
    private JMenuItem elementoItem;

}
