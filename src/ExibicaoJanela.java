import javax.swing.*;

public class ExibicaoJanela extends JFrame {
    public ExibicaoJanela() {
        //Seta o local onde a janela aparecerá são definidos em 4 parametros
        setBounds(300, 300, 600, 600);
        setTitle("LText");
        add(new Panel(this));
    }

}
