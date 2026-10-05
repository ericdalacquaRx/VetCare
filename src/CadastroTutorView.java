import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Tela de cadastro de tutores (donos dos pets).
 *
 * @author VetCare
 */
public class CadastroTutorView extends JFrame {

    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JTextField campoEndereco;

    public CadastroTutorView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("VetCare - Cadastro de Tutor");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(460, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(EstiloUI.FUNDO);
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        painel.add(EstiloUI.criarTitulo("Cadastro de Tutor"));
        painel.add(Box.createVerticalStrut(20));

        campoNome = EstiloUI.criarCampoTexto();
        campoTelefone = EstiloUI.criarCampoTexto();
        campoEmail = EstiloUI.criarCampoTexto();
        campoEndereco = EstiloUI.criarCampoTexto();

        painel.add(EstiloUI.criarCampoComRotulo("Nome", campoNome));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Telefone", campoTelefone));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("E-mail", campoEmail));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Endereco", campoEndereco));
        painel.add(Box.createVerticalStrut(24));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        JButton btnSalvar = EstiloUI.criarBotaoPrimario("Salvar");
        JButton btnVoltar = EstiloUI.criarBotaoSecundario("Voltar");

        btnSalvar.addActionListener(evt -> salvarTutor());
        btnVoltar.addActionListener(evt -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(Box.createHorizontalStrut(12));
        painelBotoes.add(btnVoltar);

        painel.add(painelBotoes);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painel, BorderLayout.CENTER);
    }

    private void salvarTutor() {
        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String email = campoEmail.getText().trim();
        String endereco = campoEndereco.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos antes de salvar.",
                    "Cadastro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TutorDTO tutor = new TutorDTO();
        tutor.setNome(nome);
        tutor.setTelefone(telefone);
        tutor.setEmail(email);
        tutor.setEndereco(endereco);

        TutorDAO tutorDAO = new TutorDAO();
        boolean sucesso = tutorDAO.cadastrarTutor(tutor);

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                    "Tutor cadastrado com sucesso!",
                    "Cadastro realizado", JOptionPane.INFORMATION_MESSAGE);

            campoNome.setText("");
            campoTelefone.setText("");
            campoEmail.setText("");
            campoEndereco.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nao foi possivel cadastrar o tutor.",
                    "Cadastro nao realizado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new CadastroTutorView().setVisible(true);
        });
    }
}
