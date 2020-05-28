package com.company;

import java.util.Random;

public class Server {

    private Estado estado;
    private Atendimento atendimento;

    public Server() {
        this.estado = Estado.LIVRE;
    }

    public void atender(final Atendimento atendimento) throws Exception {

        this.atendimento = atendimento;

        final Random random = new Random();

        if(this.estado == Estado.EM_ATENDIMENTO){
            throw new Exception("Servidor em atendimento");
        }

        System.out.println("Atendendo: " + atendimento.getNome());
        Thread.sleep(random.nextInt(5000));
        System.out.println("Atendido: " + atendimento.getNome());

        this.atendimento = null;
        this.estado = Estado.LIVRE;
    }

    enum Estado {
        EM_ATENDIMENTO,
        LIVRE
    }
}
