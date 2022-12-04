import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.io.*;
import java.util.ArrayList;


class Panel extends JPanel {
    //---------------------Declaração de variáveis--------------------------------
    private int contadorPanel = 0; //Conta quantos paineis foram criados
    public boolean existePanel = false; //Verifica se existe algum painel criado
    private JTabbedPane tPane;
    private JPanel janela;
    //private JTextPane areaTexto;
    private ArrayList<JTextPane> listaAreaTexto;
    private ArrayList<JScrollPane> listaScroll;
    private ArrayList<File> listaFile;
    private ArrayList<UndoManager> listaManager;
    private JMenuBar menuBar;
    private JMenu arquivo, editar, selecao, ver, aparencia;
    private JMenuItem elementoItem;
    //----------------------------------------------------------------------------
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
        criaItem("Fechar LText", "arquivo", "fechar");
        //-----------------------------------------------

        //-----------Elementos do Menu Editar-----------
        criaItem("Desfazer", "editar", "desfazer");
        criaItem("Refazer", "editar", "refazer");
        editar.addSeparator();
        criaItem("Cortar", "editar", "cortar");
        criaItem("Copiar", "editar", "copiar");
        criaItem("Colar", "editar", "colar");
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

        //------------------------------------------------


        //------Area de Texto-------
        tPane = new JTabbedPane();

        listaFile = new ArrayList<File>();
        listaAreaTexto = new ArrayList<JTextPane>();
        listaScroll = new ArrayList<JScrollPane>();
        listaManager = new ArrayList<UndoManager>();
        //--------------------------

        add(panelMenu);
        add(tPane);
    }

    //Função para criar os itens do menu de forma mais prática
    public void criaItem(String nomeMenu, String menu, String acao) {
        elementoItem = new JMenuItem(nomeMenu);

        switch (menu) {
            case "arquivo" -> {
                arquivo.add(elementoItem);
                switch (acao) {
                    case "novo" -> elementoItem.addActionListener(e -> createPanel());
                    case "abrir" -> elementoItem.addActionListener(e -> {
                        createPanel();

                        JFileChooser selecaoDeArquivo = new JFileChooser();
                        selecaoDeArquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int resultado = selecaoDeArquivo.showOpenDialog(listaAreaTexto.get(tPane.getSelectedIndex()));

                        if (resultado == JFileChooser.APPROVE_OPTION) {
                            try {
                                boolean existePath = false;
                                for (int i = 0; i < tPane.getTabCount(); i++) {
                                    File f = selecaoDeArquivo.getSelectedFile();
                                    if (listaFile.get(i).getPath().equals(f.getPath())) {
                                        existePath = true;
                                    }
                                }

                                if (!existePath) {
                                    File archivo = selecaoDeArquivo.getSelectedFile();
                                    listaFile.set(tPane.getSelectedIndex(), archivo);

                                    FileReader entrada = new FileReader(listaFile.get(tPane.getSelectedIndex()).getPath());

                                    BufferedReader miBuffer = new BufferedReader(entrada);
                                    String linha = "";

                                    //O título da aba é o nome do arquivo do usuário
                                    String titulo = listaFile.get(tPane.getSelectedIndex()).getName();

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
                                        if (listaFile.get(i).getPath().equals(f.getPath())) {
                                            //Seleciona o panel que já tem o arquivo aberto
                                            tPane.setSelectedIndex(i); //É passado o parametro da posição do panel que tem o diretorio

                                            excluiPanel();
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
                                excluiPanel();
                            }

                        }

                    });
                    case "salvar" -> elementoItem.addActionListener(e -> {
                        //Se o arquivo não existe, ele irá salvar como
                        if (listaFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
                            //Pergunta qual diretório o usuário deseja salvar o arquivo
                            arquivoSalvarComo();
                        } else {
                            //Se já existente apenas escreve no local onde o arquivo está
                            arquivoSalvar();
                        }
                    });
                    case "salvarComo" -> elementoItem.addActionListener(e -> arquivoSalvarComo());

                    case "fechar" -> elementoItem.addActionListener(e -> System.exit(0));
                }
            }

            case "editar" -> {
                editar.add(elementoItem);
                switch (acao){
                    case "desfazer" -> elementoItem.addActionListener(e -> {
                        if(listaManager.get(tPane.getSelectedIndex()).canUndo()){
                            listaManager.get(tPane.getSelectedIndex()).undo();
                        }
                    });

                    case "refazer" -> elementoItem.addActionListener(e ->{
                        if(listaManager.get(tPane.getSelectedIndex()).canRedo()){
                            listaManager.get(tPane.getSelectedIndex()).redo();
                        }
                    });

                    case "cortar" -> elementoItem.addActionListener(new DefaultEditorKit.CutAction());

                    case "copiar" -> elementoItem.addActionListener(new DefaultEditorKit.CopyAction());

                    case "colar" -> elementoItem.addActionListener(new DefaultEditorKit.PasteAction());
                }
            }
            case "selecao" -> selecao.add(elementoItem);
            case "ver" -> ver.add(elementoItem);
            case "aparencia" -> aparencia.add(elementoItem);
        }

    }


    //---------------Métodos utilizados dentro do código----------------------
    public void createPanel() {
        janela = new JPanel();

        listaFile.add(new File(""));
        listaAreaTexto.add(new JTextPane());
        listaScroll.add(new JScrollPane(listaAreaTexto.get(contadorPanel)));
        listaManager.add(new UndoManager()); //Serve para rastrear as ações da área de texto

        listaAreaTexto.get(contadorPanel).getDocument().addUndoableEditListener(listaManager.get(contadorPanel));

        janela.add(listaScroll.get(contadorPanel));
        tPane.addTab("title", janela);
        tPane.setSelectedIndex(contadorPanel);

        contadorPanel++;
        existePanel = true;

    }

    public void excluiPanel() {
        listaAreaTexto.remove(tPane.getTabCount() - 1);
        listaScroll.remove(tPane.getTabCount() - 1);
        listaFile.remove(tPane.getTabCount() - 1);
        tPane.remove(tPane.getTabCount() - 1);
        contadorPanel--;
    }

    public void arquivoSalvarComo() {
        //Pergunta qual diretório o usuário deseja salvar o arquivo
        JFileChooser salvarArquivo = new JFileChooser();
        int opcao = salvarArquivo.showSaveDialog(null);

        if (opcao == JFileChooser.APPROVE_OPTION) {
            File fArquivo = salvarArquivo.getSelectedFile();
            listaFile.set(tPane.getSelectedIndex(), fArquivo);
            tPane.setTitleAt(tPane.getSelectedIndex(), fArquivo.getName());

            arquivoSalvar();
        }
    }

    public void arquivoSalvar() {
        //Função para salvar o arquivo. Ela lê cada linha e escreve no diretório do arquivo onde será salvo
        try {
            FileWriter fw = new FileWriter(listaFile.get(tPane.getSelectedIndex()).getPath());
            String texto = listaAreaTexto.get(tPane.getSelectedIndex()).getText();

            for (int i = 0; i < texto.length(); i++) {
                fw.write(texto.charAt(i));
            }

            fw.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //-----------------------------------------------------------------------
}
