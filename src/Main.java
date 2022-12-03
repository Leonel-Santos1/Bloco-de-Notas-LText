import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Janela janela = new Janela();
        //Quando é clicado no botão do x da janela, ela é fechda com esse m[etodo
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}