import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static java.util.Objects.isNull;

public class LeitorRegistro {

    private static final String CSV_SEPARATOR = ";";
    private static final int INDEX_DADO_PROTOCOLO = 0;
    private static final int INDEX_DADO_DATA_HORA_ABERTURA = 1;
    private static final int INDEX_DADO_TEMPO_ATENDIMENTO = 6;
    private static final int INDEX_DADO_MODULO = 9;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public List<Registro> lerRegistros(final String pathCsv) {
        final List<Registro> registros = new ArrayList<>();

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(pathCsv));
            String line;
            int linesRead = 0;
            while ((line = reader.readLine()) != null) {
                if (++linesRead == 1) { continue; } // Skips csv header line.

                final String[] lineParts = line.split(CSV_SEPARATOR);
                final Registro registro = mapLinesPartsToRegistro(lineParts);
                if (registroEstaValido(registro)) {
                    registros.add(registro);
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return registros;
    }

    private Registro mapLinesPartsToRegistro(final String[] lineParts) {
        final Registro registro = new Registro();

        try {
            registro.setProtocolo(lineParts[INDEX_DADO_PROTOCOLO]);
            registro.setDataHoraAbertura(stringToLocalDataTime(lineParts[INDEX_DADO_DATA_HORA_ABERTURA]));
            registro.setTempoAtendimento(Long.parseLong(lineParts[INDEX_DADO_TEMPO_ATENDIMENTO]));
            registro.setModulo(lineParts[INDEX_DADO_MODULO]);
        } catch (ArrayIndexOutOfBoundsException exception) {}

        return registro;
    }

    private LocalDateTime stringToLocalDataTime(final String str) {
        return LocalDateTime.parse(str, DATE_FORMATTER);
    }

    private boolean registroEstaValido(final Registro registro) {
        return !isNull(registro.getDataHoraAbertura()) && !isNull(registro.getTempoAtendimento());
    }
}