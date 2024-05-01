import javax.swing.*;

public class Test extends JFrame {

    private Model model;
    private View view;
    private Controller controller;

    public Test() {
        model = new Model();
        view = new View(model);
        controller = new Controller(model, view);

        add(view);
        setTitle("MVC Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }
}
