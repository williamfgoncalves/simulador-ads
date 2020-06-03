import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GeradorAtendimento {

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Registro> registros;
    private LocalDateTime time;
    private HashMap<String, Registro> emAtendimento = new HashMap<>();

    public GeradorAtendimento(List<Registro> registros) {
        this.registros = registros;

        final Registro primeiroRegistro = registros.stream().sorted(Comparator.comparing(Registro::getDataHoraAbertura)).findFirst().get();
        this.inicio = primeiroRegistro.getDataHoraAbertura();
    }

    public void start() {
        this.time = inicio;

        HashMap<String, LinkedList> filasAtendimento = new HashMap<>();
        
        int delay = 1000;   // tempo de espera antes da 1ª execução da tarefa.
        long interval = 1;  // intervalo no qual a tarefa será executada.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                final LocalDateTime now = time;
                //System.out.println("Hora atual: " + now);

                final List<Registro> atendimentos = registros.stream().filter(registro -> registro.getDataHoraAbertura().equals(now)).collect(Collectors.toList());
                atendimentos.forEach(atendimento -> {
                    if(filasAtendimento.get(atendimento.getModulo()) == null) {
                        filasAtendimento.put(atendimento.getModulo(), new LinkedList());
                        emAtendimento.put(atendimento.getModulo(), null);
                    }

                    System.out.println("\n" + atendimento.getModulo() + " - Abertura Atendimento: " + atendimento.getProtocolo());
                    filasAtendimento.get(atendimento.getModulo()).addLast(atendimento);

                    System.out.print(atendimento.getModulo() + " - Fila: ");
                    Iterator<Registro> i = filasAtendimento.get(atendimento.getModulo()).iterator();
                    while(i.hasNext())
                        System.out.print(i.next().getProtocolo() + " ");
                });

                for(Map.Entry<String, Registro> modulo : emAtendimento.entrySet()) {
                    if(modulo.getValue() == null) {
                        if(filasAtendimento.get(modulo.getKey()).size() > 0) {
                            modulo.setValue((Registro) filasAtendimento.get(modulo.getKey()).removeFirst());
                            System.out.println("\n" + modulo.getKey() + " - Iniciando Atendimento: " + modulo.getValue().getProtocolo());
                            System.out.print(modulo.getKey() + " - Fila: ");
                            Iterator<Registro> i = filasAtendimento.get(modulo.getKey()).iterator();
                            while(i.hasNext())
                                System.out.print(i.next().getProtocolo() + " ");
                        }
                    }
                    else {
                        if(modulo.getValue().getDataHoraAbertura().plusSeconds(modulo.getValue().getTempoAtendimento()).isBefore(now)) {
                            System.out.print("\n" + modulo.getKey() + " - Finalizando Atendimento: " + modulo.getValue().getProtocolo());
                            modulo.setValue(null);
                        }
                    }
                }

                time = time.plusSeconds(1);
            }
        }, delay, interval);
    }
}