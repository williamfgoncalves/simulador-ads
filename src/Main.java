import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    static String csvPath = "C:\\Users\\vinicius\\IdeaProjects\\simulador-ads\\src\\dados\\dados.csv";

    public static void main(String[] args) {
        LeitorRegistro leitor = new LeitorRegistro();
        final List<Registro> registros = leitor.lerRegistros(csvPath);

        JFrame jframe = new JFrame("Simulador");

        FormPrincipal formPrincipal = new FormPrincipal();

        jframe.setContentPane(formPrincipal.panel1);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setVisible(true);

        GeradorAtendimento gerador = new GeradorAtendimento(registros, formPrincipal);
        gerador.start();
    }
}