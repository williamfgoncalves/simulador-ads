import java.time.LocalDateTime;

public class Registro {

    private String protocolo;
    private LocalDateTime dataHoraAbertura;
    private LocalDateTime dataHoraInicioAtendimento;
    private Long tempoAtendimento;
    private String modulo;

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraInicioAtendimento() {
        return dataHoraInicioAtendimento;
    }

    public void setDataHoraInicioAtendimento(LocalDateTime dataHoraInicioAtendimento) {
        this.dataHoraInicioAtendimento = dataHoraInicioAtendimento;
    }

    public Long getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(Long tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
}
