import javax.swing.*;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
=======
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
>>>>>>> 85a43a6 (Correçao dos arquivos)
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

<<<<<<< HEAD
        creatPanel();

=======
>>>>>>> 85a43a6 (Correçao dos arquivos)
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
<<<<<<< HEAD
                    elementoItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            creatPanel();
                        }
=======
                    elementoItem.addActionListener(e -> createPanel());
                } else if (acao.equals("abrir")) {
                    elementoItem.addActionListener(e -> {
                        createPanel();

                        JFileChooser selecaoDeArquivo = new JFileChooser();
                        selecaoDeArquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int resultado = selecaoDeArquivo.showOpenDialog(listaAreaTexto.get(tPane.getSelectedIndex()));

                        if (resultado == JFileChooser.APPROVE_OPTION) {
                            try {
                                boolean existePath = false;
                                for (int i = 0; i < tPane.getTabCount(); i++) {
                                    File f = selecaoDeArquivo.getSelectedFile();
                                    if (listFile.get(i).getPath().equals(f.getPath())) {
                                        existePath = true;
                                    }
                                }

                                if (!existePath) {
                                    File archivo = selecaoDeArquivo.getSelectedFile();
                                    listFile.set(tPane.getSelectedIndex(), archivo);

                                    FileReader entrada = new FileReader(listFile.get(tPane.getSelectedIndex()).getPath());

                                    BufferedReader miBuffer = new BufferedReader(entrada);
                                    String linha = "";

                                    //O título da aba é o nome do arquivo do usuário
                                    String titulo = listFile.get(tPane.getSelectedIndex()).getName();

                                    //Nossa área de texto, onde irá o texto do arquivo que o usuário selecionou
                                    tPane.setTitleAt(tPane.getSelectedIndex(), titulo);

                                    while (linha != null) {
                                        linha = miBuffer.readLine(); //Lê linha por linha do arquivo selecionado pelo usuário
                                        if (linha != null) {
                                            Utilidades.append(linha + "\n", listaAreaTexto.get(tPane.getSelectedIndex()));
                                        }
                                    }

                                } else {
                                    //Se o diretório do arquivo já existe y está aberto
                                    //O laço for irá percorrer todos os panels para ver qual deles tem o mesmo diretório
                                    //e selecionar o arquivo e esse panel
                                    for (int i = 0; i < tPane.getTabCount(); i++) {
                                        File f = selecaoDeArquivo.getSelectedFile();
                                        if (listFile.get(i).getPath().equals(f.getPath())) {
                                            //Seleciona o panel que já tem o arquivo aberto
                                            tPane.setSelectedIndex(i); //É passado o parametro da posição do panel que tem o diretorio

                                            listaAreaTexto.remove(tPane.getTabCount() - 1);
                                            listaScroll.remove(tPane.getTabCount() - 1);
                                            listFile.remove(tPane.getTabCount() - 1);
                                            tPane.remove(tPane.getTabCount() - 1);
                                            contadorPanel--;
                                            break;
                                        }
                                    }
                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            //Si se oprime el boton cancelar en la ventana de abrir archivo
                            //eliminamos el panel del area de texto que se crea por defecto

                            int seleccion = tPane.getSelectedIndex();
                            if (seleccion != -1) {
                                listaAreaTexto.remove(tPane.getTabCount() - 1);
                                listaScroll.remove(tPane.getTabCount() - 1);
                                listFile.remove(tPane.getTabCount() - 1);
                                tPane.remove(tPane.getTabCount() - 1);
                                contadorPanel--;
                            }

                        }

>>>>>>> 85a43a6 (Correçao dos arquivos)
                    });
                }
            }
            case "editar" -> editar.add(elementoItem);
            case "selecao" -> selecao.add(elementoItem);
            case "ver" -> ver.add(elementoItem);
            case "aparencia" -> aparencia.add(elementoItem);
        }

    }


<<<<<<< HEAD
    public void creatPanel() {
=======
    public void createPanel() {
>>>>>>> 85a43a6 (Correçao dos arquivos)
        janela = new JPanel();

        listFile.add(new File(""));
        listaAreaTexto.add(new JTextPane());
        listaScroll.add(new JScrollPane(listaAreaTexto.get(contadorPanel)));

        janela.add(listaScroll.get(contadorPanel));
        tPane.addTab("title", janela);
<<<<<<< HEAD
=======
        tPane.setSelectedIndex(contadorPanel);
>>>>>>> 85a43a6 (Correçao dos arquivos)

        contadorPanel++;
        existePanel = true;

    }

<<<<<<< HEAD
    private int contadorPanel = 0; //Conta quantos paineis foram criados
=======
    private int contadorPanel; //Conta quantos paineis foram criados
>>>>>>> 85a43a6 (Correçao dos arquivos)
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
