import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;


class Panel extends JPanel {
    //---------------------Declaração de variáveis--------------------------------
    private String tipoFundo = "w";
    public boolean numeracao = false;
    private int contadorPanel = 0; //Conta quantos paineis foram criados
    public boolean existePanel = false; //Verifica se existe algum painel criado
    private final JTabbedPane tPane;
    private JPanel janela;
    private final JPanel novoPanel;
    //private JTextPane areaTexto;
    private final ArrayList<JTextPane> listaAreaTexto;
    private final ArrayList<JScrollPane> listaScroll;
    private final ArrayList<File> listaFile;
    private final ArrayList<UndoManager> listaManager;
    private final JMenuBar menuBar;
    private final JMenu arquivo;
    private final JMenu editar;
    private final JMenu selecao;
    private final JMenu ver;
    private final JMenu aparencia;
    private JMenuItem elementoItem;
    private final JToolBar ferramentas;
    private URL url;

    private boolean estadoLabelFixo = false;
    private final JLabel labelFixo;
    private final JSlider slider;

    private final JPopupMenu menuEmergente;

    private JMenuItem itens[];

    //----------------------------------------------------------------------------
    public Panel(JFrame ExibicaoJanela) {
        BorderLayout border = new BorderLayout();
        setLayout(border);
        //-----------Menu-----------
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        itens = new JMenuItem[8];

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
        criaItem("Selecionar Tudo", "selecao", "selecionarTudo");
        //-----------------------------------------------

        //-----------Elementos do Menu Ver-----------
        criaItem("Numeração de Linhas", "ver", "numeracao");
        ver.add(aparencia);
        criaItem("Modo Claro", "aparencia", "claro");
        criaItem("Modo Escuro", "aparencia", "noturno");
        //-----------------------------------------------

        panelMenu.add(menuBar, BorderLayout.NORTH);

        //------------------------------------------------


        //------Area de Texto-------
        tPane = new JTabbedPane();

        listaFile = new ArrayList<File>();
        listaAreaTexto = new ArrayList<JTextPane>();
        listaScroll = new ArrayList<JScrollPane>();
        listaManager = new ArrayList<UndoManager>();


        Utilidades.desativaItens(itens);
        //--------------------------

        //------Barra Ferramentas-------

        ferramentas = new JToolBar(JToolBar.VERTICAL);
        url = Panel.class.getResource("/imagens/close.png");
        Utilidades.adicionaButao(url, ferramentas, "Fechar Janela Atual").addActionListener(e -> {
            int selecao = tPane.getSelectedIndex();

            if (selecao != -1) {
                //Se existem janelas abertas eliminamos a janela selecionada
                listaScroll.get(tPane.getSelectedIndex()).setRowHeader(null);
                tPane.remove(selecao);
                listaAreaTexto.remove(selecao);
                listaScroll.remove(selecao);
                listaManager.remove(selecao);
                listaFile.remove(selecao);
                contadorPanel--;

                if (tPane.getSelectedIndex() == -1) {
                    existePanel = false; //Se tPane retornar -1 significa que não existe mais nenhum painel
                    Utilidades.desativaItens(itens);
                }
            }
        });

        url = Panel.class.getResource("/imagens/add.png");
        Utilidades.adicionaButao(url, ferramentas, "Novo Arquivo").addActionListener(e -> {
            createPanel();
            if (existePanel) {
                Utilidades.ativaItens(itens);
            }
        });
        //------------------------------

        //-------Novo Panel extra-------

        novoPanel = new JPanel();
        novoPanel.setLayout(new BorderLayout());

        JPanel panelEsquerdo = new JPanel();
        labelFixo = new JLabel();
        url = Panel.class.getResource("/imagens/alfiler.png");
        labelFixo.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        labelFixo.addMouseListener(new MouseAdapter() {
            //Quando o curso fica em cima do label, ele muda a imagem
            @Override
            public void mouseEntered(MouseEvent e) {
                url = Panel.class.getResource("/imagens/alfilerseleccion.png");
                labelFixo.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                super.mouseEntered(e);
            }

            //Quando o cursor sai do label, ele muda a imagem
            @Override
            public void mouseExited(MouseEvent e) {
                if (estadoLabelFixo) {
                    url = Panel.class.getResource("/imagens/alfilerseleccion.png");
                    labelFixo.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                } else {
                    url = Panel.class.getResource("/imagens/alfiler.png");
                    labelFixo.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                }
                super.mouseExited(e);
            }

            //Quando o cursor clica no label, ele muda a imagem
            @Override
            public void mousePressed(MouseEvent e) {
                estadoLabelFixo = !estadoLabelFixo;
                ExibicaoJanela.setAlwaysOnTop(estadoLabelFixo);
                super.mousePressed(e);
            }
        });
        panelEsquerdo.add(labelFixo);

        JPanel panelCentro = new JPanel();
        slider = new JSlider(8, 38, 12);
        slider.setMajorTickSpacing(6); //A separação entre as barras maiores será de 12 em 12
        slider.setMinorTickSpacing(2);//A separação entre as barras menores será de 2 em 2
        slider.setPaintTicks(true); //Mostra as barras
        slider.setPaintLabels(true); //Mostra os números

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utilidades.tamanhoTexto(slider.getValue(), contadorPanel, listaAreaTexto);
            }
        });

        panelCentro.add(slider);

        novoPanel.add(panelEsquerdo, BorderLayout.WEST);
        novoPanel.add(panelCentro, BorderLayout.CENTER);

        //------------------------------

        //-------Menu Emergente-------

        menuEmergente = new JPopupMenu();

        JMenuItem cortar = new JMenuItem("Cortar");
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem colar = new JMenuItem("Colar");
        JMenuItem selecionarTudo = new JMenuItem("Selecionar Tudo");

        cortar.addActionListener(new DefaultEditorKit.CutAction());
        copiar.addActionListener(new DefaultEditorKit.CopyAction());
        colar.addActionListener(new DefaultEditorKit.PasteAction());
        selecionarTudo.addActionListener(e -> listaAreaTexto.get(tPane.getSelectedIndex()).selectAll());

        menuEmergente.add(cortar);
        menuEmergente.add(copiar);
        menuEmergente.add(colar);
        menuEmergente.addSeparator();
        menuEmergente.add(selecionarTudo);


        //------------------------------

        add(panelMenu, BorderLayout.NORTH);
        add(tPane, BorderLayout.CENTER);
        add(ferramentas, BorderLayout.WEST);
        add(novoPanel, BorderLayout.SOUTH);
    }

    //Função para criar os itens do menu de forma mais prática
    public void criaItem(String nomeMenu, String menu, String acao) {
        elementoItem = new JMenuItem(nomeMenu);

        switch (menu) {
            case "arquivo" -> {
                arquivo.add(elementoItem);
                switch (acao) {
                    case "novo" -> elementoItem.addActionListener(e -> {
                        createPanel();
                        if (existePanel) {
                            Utilidades.ativaItens(itens);
                        }
                    });
                    case "abrir" -> elementoItem.addActionListener(e -> {
                        createPanel();

                        JFileChooser selecaoDeArquivo = new JFileChooser();
                        selecaoDeArquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int resultado = selecaoDeArquivo.showOpenDialog(listaAreaTexto.get(tPane.getSelectedIndex()));

                        if (resultado == JFileChooser.APPROVE_OPTION) {
                            if (existePanel) {
                                Utilidades.ativaItens(itens);
                            }
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
                                    Utilidades.mudarAparencia(contadorPanel, tipoFundo, slider.getValue(), listaAreaTexto);
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
                    case "salvar" -> {
                        itens[0] = elementoItem;
                        elementoItem.addActionListener(e -> {

                            //Se o arquivo não existe, ele irá salvar como
                            if (listaFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
                                //Pergunta qual diretório o usuário deseja salvar o arquivo
                                arquivoSalvarComo();
                            } else {
                                //Se já existente apenas escreve no local onde o arquivo está
                                arquivoSalvar();
                            }
                        });
                    }
                    case "salvarComo" -> {
                        itens[1] = elementoItem;
                        elementoItem.addActionListener(e -> arquivoSalvarComo());
                    }

                    case "fechar" -> elementoItem.addActionListener(e -> System.exit(0));
                }
            }

            case "editar" -> {
                editar.add(elementoItem);
                switch (acao) {
                    case "desfazer" -> {
                        itens[2] = elementoItem;
                        elementoItem.addActionListener(e -> {
                            if (listaManager.get(tPane.getSelectedIndex()).canUndo()) {
                                listaManager.get(tPane.getSelectedIndex()).undo();
                            }
                        });
                    }

                    case "refazer" -> {
                        itens[3] = elementoItem;
                        elementoItem.addActionListener(e -> {
                            if (listaManager.get(tPane.getSelectedIndex()).canRedo()) {
                                listaManager.get(tPane.getSelectedIndex()).redo();
                            }
                        });
                    }

                    case "cortar" -> {
                        itens[4] = elementoItem;
                        elementoItem.addActionListener(new DefaultEditorKit.CutAction());
                    }

                    case "copiar" -> {
                        itens[5] = elementoItem;
                        elementoItem.addActionListener(new DefaultEditorKit.CopyAction());
                    }

                    case "colar" -> {
                        itens[6] = elementoItem;
                        elementoItem.addActionListener(new DefaultEditorKit.PasteAction());
                    }
                }
            }
            case "selecao" -> {
                selecao.add(elementoItem);
                if (acao.equals("selecionarTudo")) {
                    itens[7] = elementoItem;
                    elementoItem.addActionListener(e -> listaAreaTexto.get(tPane.getSelectedIndex()).selectAll());
                }
            }
            case "ver" -> {
                ver.add(elementoItem);
                if (acao.equals("numeracao")) {
                    elementoItem.addActionListener(e -> {
                        numeracao = !numeracao;
                        Utilidades.verNumeracao(contadorPanel, numeracao, listaAreaTexto, listaScroll);
                    });
                }
            }
            case "aparencia" -> {
                aparencia.add(elementoItem);
                switch (acao) {
                    case "claro" -> elementoItem.addActionListener(e -> {
                        tipoFundo = "w";
                        if (tPane.getTabCount() > 0) {
                            Utilidades.mudarAparencia(contadorPanel, tipoFundo, slider.getValue(), listaAreaTexto);
                        }
                    });
                    case "noturno" -> elementoItem.addActionListener(e -> {
                        tipoFundo = "d";
                        if (tPane.getTabCount() > 0) {
                            Utilidades.mudarAparencia(contadorPanel, tipoFundo, slider.getValue(), listaAreaTexto);
                        }
                    });
                }
            }
        }

    }


    //---------------Métodos utilizados dentro do código da Janela-----------------------
    public void createPanel() {
        janela = new JPanel();
        janela.setLayout(new BorderLayout());

        listaFile.add(new File(""));
        listaAreaTexto.add(new JTextPane());
        listaScroll.add(new JScrollPane(listaAreaTexto.get(contadorPanel)));
        listaManager.add(new UndoManager()); //Serve para rastrear as ações da área de texto

        listaAreaTexto.get(contadorPanel).getDocument().addUndoableEditListener(listaManager.get(contadorPanel));

        listaAreaTexto.get(contadorPanel).setComponentPopupMenu(menuEmergente);

        janela.add(listaScroll.get(contadorPanel), BorderLayout.CENTER);

        tPane.addTab("title", janela);

        Utilidades.verNumeracaoInicial(numeracao, listaAreaTexto.get(contadorPanel), listaScroll.get(contadorPanel));

        tPane.setSelectedIndex(contadorPanel);

        contadorPanel++;
        Utilidades.mudarAparencia(contadorPanel, tipoFundo, slider.getValue(), listaAreaTexto);
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
