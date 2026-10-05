import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Tela de registro de consultas veterinarias, vinculadas a um pet.
 *
 * @author VetCare
 */
public class ConsultaView extends JFrame {

    private JComboBox<String> comboPet;
    private JTextField campoData;
    private JTextField campoMotivo;
    private JTextField campoDiagnostico;
    private JTextField campoVeterinario;
    private JTextField campoValor;

    private ArrayList<PetDTO> petsCarregados;

    public ConsultaView() {
        initComponents();
        carregarPets();
    }

    private void initComponents() {
        setTitle("VetCare - Registro de Consulta");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(460, 640);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(EstiloUI.FUNDO);
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        painel.add(EstiloUI.criarTitulo("Registro de Consulta"));
        painel.add(Box.createVerticalStrut(20));

        comboPet = new JComboBox<>();
        comboPet.addItem("Selecione o pet...");
        comboPet.setFont(EstiloUI.FONTE_CAMPO);
        comboPet.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        comboPet.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));

        campoData = EstiloUI.criarCampoTexto();
        campoMotivo = EstiloUI.criarCampoTexto();
        campoDiagnostico = EstiloUI.criarCampoTexto();
        campoVeterinario = EstiloUI.criarCampoTexto();
        campoValor = EstiloUI.criarCampoTexto();

        painel.add(EstiloUI.criarCampoComRotulo("Pet", comboPet));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Data da consulta (dd/mm/aaaa)", campoData));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Motivo", campoMotivo));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Diagnostico", campoDiagnostico));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Veterinario responsavel", campoVeterinario));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Valor (R$)", campoValor));
        painel.add(Box.createVerticalStrut(24));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        JButton btnRegistrar = EstiloUI.criarBotaoPrimario("Registrar");
        JButton btnVoltar = EstiloUI.criarBotaoSecundario("Voltar");

        btnRegistrar.addActionListener(evt -> registrarConsulta());
        btnVoltar.addActionListener(evt -> dispose());

        painelBotoes.add(btnRegistrar);
        painelBotoes.add(Box.createHorizontalStrut(12));
        painelBotoes.add(btnVoltar);

        painel.add(painelBotoes);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painel, BorderLayout.CENTER);
    }

    /**
     * Busca todos os pets cadastrados no banco de dados e preenche o combo.
     */
    private void carregarPets() {
        PetDAO petDAO = new PetDAO();
        petsCarregados = petDAO.listarTodosPets();

        comboPet.removeAllItems();
        comboPet.addItem("Selecione o pet...");
        for (PetDTO pet : petsCarregados) {
            comboPet.addItem(pet.getNome());
        }
    }

    private void registrarConsulta() {
        int indicePet = comboPet.getSelectedIndex();
        String data = campoData.getText().trim();
        String motivo = campoMotivo.getText().trim();
        String diagnostico = campoDiagnostico.getText().trim();
        String veterinario = campoVeterinario.getText().trim();
        String valorTexto = campoValor.getText().trim();

        if (indicePet <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione o pet para registrar a consulta.",
                    "Registro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (data.isEmpty() || motivo.isEmpty() || diagnostico.isEmpty() || veterinario.isEmpty() || valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos antes de registrar.",
                    "Registro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorTexto.replace(",", "."));
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this,
                    "O valor informado deve ser numerico (ex: 120.00).",
                    "Registro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PetDTO petSelecionado = petsCarregados.get(indicePet - 1);

        ConsultaDTO consulta = new ConsultaDTO();
        consulta.setPetId(petSelecionado.getId());
        consulta.setDataConsulta(data);
        consulta.setMotivo(motivo);
        consulta.setDiagnostico(diagnostico);
        consulta.setVeterinario(veterinario);
        consulta.setValor(valor);

        ConsultaDAO consultaDAO = new ConsultaDAO();
        boolean sucesso = consultaDAO.registrarConsulta(consulta);

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                    "Consulta registrada com sucesso para " + petSelecionado.getNome() + "!",
                    "Registro realizado", JOptionPane.INFORMATION_MESSAGE);

            campoData.setText("");
            campoMotivo.setText("");
            campoDiagnostico.setText("");
            campoVeterinario.setText("");
            campoValor.setText("");
            comboPet.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nao foi possivel registrar a consulta.",
                    "Registro nao realizado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new ConsultaView().setVisible(true);
        });
    }
}
