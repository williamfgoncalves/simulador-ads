package com.company.v2;

import java.util.List;

public class MainV2 {

    static String csvPath = "C:\\Users\\alvaro.schmidt\\Documents\\Alvaro\\feevs\\Analise_Desempenho_Sistemas\\CRM.csv";

    public static void main(String[] args) {
        LeitorRegistro leitor = new LeitorRegistro();
        final List<Registro> registros = leitor.lerRegistros(csvPath);

        GeradorAtendimento gerador = new GeradorAtendimento(registros);
        gerador.start();
    }
}
