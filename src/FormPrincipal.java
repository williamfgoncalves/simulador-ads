import javax.swing.*;

public class FormPrincipal {
    public JPanel panel1;
    public JList listAdministracao;
    public JList listFinancas;
    public JList listManufatura;
    public JList listRecursosHumanos;
    public JList listSuprimentos;
    public JList listFiscal;
    public JList listEspecificacoesExclusivas;
    public JList listControladoria;
    public JList listComercioExterior;
    public JList listVendas;
    public JList listWeb;
    public JLabel horaAtual;
    private JLabel labelAdministracao;
    private JLabel labelComercioExterior;
    private JLabel labelControladoria;
    private JLabel labelEspecificacoesExclusivas;
    private JLabel labelFinancas;
    private JLabel labelFiscal;
    private JLabel labelManufatura;
    private JLabel labelRecursosHumanos;
    private JLabel labelSuprimentos;
    private JLabel labelVendas;
    private JLabel labelWeb;

    public DefaultListModel listModelAdministracao = new DefaultListModel();
    public DefaultListModel listModelComercioExterior = new DefaultListModel();
    public DefaultListModel listModelControladoria = new DefaultListModel();
    public DefaultListModel listModelEspecificacoesExclusivas = new DefaultListModel();
    public DefaultListModel listModelFinancas = new DefaultListModel();
    public DefaultListModel listModelFiscal = new DefaultListModel();
    public DefaultListModel listModelManufatura = new DefaultListModel();
    public DefaultListModel listModelRecursosHumanos = new DefaultListModel();
    public DefaultListModel listModelSuprimentos = new DefaultListModel();
    public DefaultListModel listModelVendas = new DefaultListModel();
    public DefaultListModel listModelWeb = new DefaultListModel();
}