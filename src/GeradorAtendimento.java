import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Timer;
import java.util.stream.Collectors;

public class GeradorAtendimento {

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Registro> registros;
    private LocalDateTime time;
    private HashMap<String, Registro> emAtendimento = new HashMap<>();
    private float metricaMediaTempoFila;
    private float tempoTotalFila;
    private int numeroAtendimentosFila = 0;
    private FormPrincipal formPrincipal;

    public GeradorAtendimento(List<Registro> registros, FormPrincipal formPrincipal) {
        this.registros = registros;

        final Registro primeiroRegistro = registros.stream().sorted(Comparator.comparing(Registro::getDataHoraAbertura)).findFirst().get();
        this.inicio = primeiroRegistro.getDataHoraAbertura();

        this.formPrincipal = formPrincipal;

        this.formPrincipal.listAdministracao.setModel(formPrincipal.listModelAdministracao);
        this.formPrincipal.listComercioExterior.setModel(formPrincipal.listModelComercioExterior);
        this.formPrincipal.listControladoria.setModel(formPrincipal.listModelControladoria);
        this.formPrincipal.listEspecificacoesExclusivas.setModel(formPrincipal.listModelEspecificacoesExclusivas);
        this.formPrincipal.listFinancas.setModel(formPrincipal.listModelFinancas);
        this.formPrincipal.listFiscal.setModel(formPrincipal.listModelFiscal);
        this.formPrincipal.listManufatura.setModel(formPrincipal.listModelManufatura);
        this.formPrincipal.listRecursosHumanos.setModel(formPrincipal.listModelRecursosHumanos);
        this.formPrincipal.listSuprimentos.setModel(formPrincipal.listModelSuprimentos);
        this.formPrincipal.listVendas.setModel(formPrincipal.listModelVendas);
        this.formPrincipal.listWeb.setModel(formPrincipal.listModelWeb);
    }

    public void start() {
        this.time = inicio;

        HashMap<String, LinkedList> filasAtendimento = new HashMap<>();
        
        int delay = 1;   // tempo de espera antes da 1ª execução da tarefa.
        long interval = 1;  // intervalo no qual a tarefa será executada.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                final LocalDateTime now = time;
                formPrincipal.horaAtual.setText("Hora atual: " + now);

                final List<Registro> atendimentos = registros.stream().filter(registro -> registro.getDataHoraAbertura().equals(now)).collect(Collectors.toList());
                atendimentos.forEach(atendimento -> {
                    if(filasAtendimento.get(atendimento.getModulo()) == null) {
                        filasAtendimento.put(atendimento.getModulo(), new LinkedList());
                        emAtendimento.put(atendimento.getModulo(), null);
                    }

                    System.out.println("\n" + atendimento.getModulo() + " - Abertura Atendimento: " + atendimento.getProtocolo());
                    filasAtendimento.get(atendimento.getModulo()).addLast(atendimento);

                    switch (atendimento.getModulo()) {
                        case "Administração": formPrincipal.listModelAdministracao.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Comercio Exterior": formPrincipal.listModelComercioExterior.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Controladoria": formPrincipal.listModelControladoria.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Especificações Exclusivas": formPrincipal.listModelEspecificacoesExclusivas.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Finanças": formPrincipal.listModelFinancas.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Fiscal": formPrincipal.listModelFiscal.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Manufatura": formPrincipal.listModelManufatura.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Recursos Humanos": formPrincipal.listModelRecursosHumanos.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Suprimentos": formPrincipal.listModelSuprimentos.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Vendas": formPrincipal.listModelVendas.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                        case "Web": formPrincipal.listModelWeb.addElement("Atendimento " + atendimento.getProtocolo() + " - " + now); break;
                    }

                    System.out.print(atendimento.getModulo() + " - Fila: ");
                    Iterator<Registro> i = filasAtendimento.get(atendimento.getModulo()).iterator();
                    while(i.hasNext())
                        System.out.print(i.next().getProtocolo() + " ");
                });

                for(Map.Entry<String, Registro> modulo : emAtendimento.entrySet()) {
                    if(modulo.getValue() == null) {
                        if(filasAtendimento.get(modulo.getKey()).size() > 0) {
                            modulo.setValue((Registro) filasAtendimento.get(modulo.getKey()).removeFirst());
                            System.out.println("\nHora atual: " + now);
                            System.out.println("\n" + modulo.getKey() + " - Iniciando Atendimento: " + modulo.getValue().getProtocolo());
                            System.out.print("\n" + modulo.getKey() + " - Fila: ");

                            modulo.getValue().setDataHoraInicioAtendimento(now);

                            numeroAtendimentosFila++;
                            tempoTotalFila = tempoTotalFila + getSecondsBetween(modulo.getValue().getDataHoraAbertura(), now);

                            metricaMediaTempoFila = tempoTotalFila/numeroAtendimentosFila;
                            System.out.println("\nMédia de Tempo na Fila: " + metricaMediaTempoFila);

                            Iterator<Registro> i = filasAtendimento.get(modulo.getKey()).iterator();
                            while(i.hasNext())
                                System.out.print(i.next().getProtocolo() + " ");

                            if(modulo.getValue().getDataHoraInicioAtendimento().plusSeconds(modulo.getValue().getTempoAtendimento() - 1).isEqual(now) ) {
                                System.out.println("\nHora atual: " + now);
                                System.out.print("\n" + modulo.getKey() + " - Finalizando Atendimento: " + modulo.getValue().getProtocolo());

                                switch (modulo.getKey()) {
                                    case "Administração": formPrincipal.listModelAdministracao.remove(0); break;
                                    case "Comercio Exterior": formPrincipal.listModelComercioExterior.remove(0); break;
                                    case "Controladoria": formPrincipal.listModelControladoria.remove(0); break;
                                    case "Especificações Exclusivas": formPrincipal.listModelEspecificacoesExclusivas.remove(0); break;
                                    case "Finanças": formPrincipal.listModelFinancas.remove(0); break;
                                    case "Fiscal": formPrincipal.listModelFiscal.remove(0); break;
                                    case "Manufatura": formPrincipal.listModelManufatura.remove(0); break;
                                    case "Recursos Humanos": formPrincipal.listModelRecursosHumanos.remove(0); break;
                                    case "Suprimentos": formPrincipal.listModelSuprimentos.remove(0); break;
                                    case "Vendas": formPrincipal.listModelVendas.remove(0); break;
                                    case "Web": formPrincipal.listModelWeb.remove(0); break;
                                }

                                modulo.setValue(null);
                            }
                        }
                    }
                    else {
                        if(modulo.getValue().getDataHoraInicioAtendimento().plusSeconds(modulo.getValue().getTempoAtendimento() - 1).isEqual(now) ) {
                            System.out.println("\nHora atual: " + now);
                            System.out.print("\n" + modulo.getKey() + " - Finalizando Atendimento: " + modulo.getValue().getProtocolo());

                            switch (modulo.getKey()) {
                                case "Administração": formPrincipal.listModelAdministracao.remove(0); break;
                                case "Comercio Exterior": formPrincipal.listModelComercioExterior.remove(0); break;
                                case "Controladoria": formPrincipal.listModelControladoria.remove(0); break;
                                case "Especificações Exclusivas": formPrincipal.listModelEspecificacoesExclusivas.remove(0); break;
                                case "Finanças": formPrincipal.listModelFinancas.remove(0); break;
                                case "Fiscal": formPrincipal.listModelFiscal.remove(0); break;
                                case "Manufatura": formPrincipal.listModelManufatura.remove(0); break;
                                case "Recursos Humanos": formPrincipal.listModelRecursosHumanos.remove(0); break;
                                case "Suprimentos": formPrincipal.listModelSuprimentos.remove(0); break;
                                case "Vendas": formPrincipal.listModelVendas.remove(0); break;
                                case "Web": formPrincipal.listModelWeb.remove(0); break;
                            }

                            modulo.setValue(null);
                        }
                    }
                }

                time = time.plusSeconds(1);
            }
        }, delay, interval);
    }

    private static long getSecondsBetween(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);
        return duration.getSeconds();
    }
}