import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        ExibicaoJanela janela = new ExibicaoJanela();
        //Quando é clicado no botão do x da janela, ela é fechda com esse método
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}

