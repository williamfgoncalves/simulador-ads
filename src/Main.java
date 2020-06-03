import java.util.List;

public class Main {

    static String csvPath = "C:\\Users\\vinicius\\IdeaProjects\\simulador-ads\\src\\dados\\dados.csv";

    public static void main(String[] args) {
        LeitorRegistro leitor = new LeitorRegistro();
        final List<Registro> registros = leitor.lerRegistros(csvPath);

        GeradorAtendimento gerador = new GeradorAtendimento(registros);
        gerador.start();
    }
}
