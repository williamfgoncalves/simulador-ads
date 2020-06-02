package com.company.v2;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GeradorAtendimento {

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Registro> registros;

    public GeradorAtendimento(List<Registro> registros) {
        this.registros = registros;

        final Registro primeiroRegistro = registros.stream().sorted(Comparator.comparing(Registro::getDataHoraAbertura)).findFirst().get();
        this.inicio = primeiroRegistro.getDataHoraAbertura();

        final Registro ultimoRegistro = registros.stream().sorted((o1, o2) -> o2.getDataHoraEncerramento().compareTo(o1.getDataHoraEncerramento())).findFirst().get();
        this.fim = ultimoRegistro.getDataHoraEncerramento();
    }

    public void start() {
        LocalDateTime time = inicio;
        while (true) {
            if (time.isAfter(fim)) {
                break;
            }

            final LocalDateTime now = time;
            final List<Registro> atendimentosParaAbrir = registros.stream().filter(registro -> registro.getDataHoraAbertura().equals(now)).collect(Collectors.toList());
            atendimentosParaAbrir.forEach(atendimento -> {
                System.out.println("Abrindo atendimento " + atendimento.getProtocolo());
            });

            final List<Registro> atendimentosParaEncerrar = registros.stream().filter(registro -> registro.getDataHoraEncerramento().equals(now)).collect(Collectors.toList());
            atendimentosParaEncerrar.forEach(atendimento -> {
                System.out.println("Encerrando atendimento " + atendimento.getProtocolo());
            });

            try {
                TimeUnit.NANOSECONDS.sleep(1);
                time = time.plusSeconds(1);
            } catch (InterruptedException exception) {}
        }
    }
}


