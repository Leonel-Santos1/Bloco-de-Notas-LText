import javax.swing.*;
import java.awt.*;

public class ExibicaoJanela extends JFrame {
    public ExibicaoJanela() {
        //Seta o local onde a janela aparecerá são definidos em 4 parametros
        setBounds(300, 300, 300, 300);
        setTitle("LText");
        add(new Panel());
    }

}
