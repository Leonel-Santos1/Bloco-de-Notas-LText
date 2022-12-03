import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


class Panel extends JPanel {
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

        listFile = new ArrayList<File>();
        listaAreaTexto = new ArrayList<JTextPane>();
        listaScroll = new ArrayList<JScrollPane>();
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

        listFile.add(new File(""));
        listaAreaTexto.add(new JTextPane());
        listaScroll.add(new JScrollPane(listaAreaTexto.get(contadorPanel)));

        janela.add(listaScroll.get(contadorPanel));
        tPane.addTab("title", janela);

        contadorPanel++;
        existePanel = true;

    }

    private int contadorPanel = 0; //Conta quantos paineis foram criados
    private boolean existePanel = false; //Verifica se existe algum painel criado
    private JTabbedPane tPane;
    private JPanel janela;
    //private JTextPane areaTexto;
    private ArrayList<JTextPane> listaAreaTexto;
    private ArrayList<JScrollPane> listaScroll;
    private ArrayList<File> listFile;
    private JMenuBar menuBar;
    private JMenu arquivo, editar, selecao, ver, aparencia;
    private JMenuItem elementoItem;
}
