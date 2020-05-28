package com.company;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        final Server suprimentos = new Server();
        final Server vendas = new Server();

        Atendimento at1 = new Atendimento(1l, "n1");
        Atendimento at2 = new Atendimento(2l, "n2");
        Atendimento at3 = new Atendimento(3l, "n3");
        Atendimento at4 = new Atendimento(4l, "n4");
        Atendimento at5 = new Atendimento(5l, "n5");
        final List<Atendimento> atendimentosSuprimentos = new ArrayList<>(asList(at1, at2, at3, at4, at5));

        Atendimento at6 = new Atendimento(6l, "n6");
        Atendimento at7 = new Atendimento(7l, "n7");
        Atendimento at8 = new Atendimento(8l, "n8");
        final List<Atendimento> atendimentosVendas = new ArrayList<>(asList(at6, at7, at8));

        for (Atendimento atd : atendimentosSuprimentos){
            new Thread(() -> {
                try {
                    vendas.atender(atd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (Atendimento atd : atendimentosVendas) {
            new Thread(() -> {
                try {
                    suprimentos.atender(atd);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
