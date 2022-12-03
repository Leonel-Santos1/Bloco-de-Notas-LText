import javax.swing.*;

public class Janela extends JFrame {
    public Janela(){
        //Seta o local onde a janela aparecerá são definidos em 4 parametros
        setBounds(300,300,300,300);
        setTitle("LText");
        add(new Panel());
    }

}


