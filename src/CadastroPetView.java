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
 * Tela de cadastro de pets, vinculados a um tutor.
 *
 * @author VetCare
 */
public class CadastroPetView extends JFrame {

    private JTextField campoNome;
    private JTextField campoEspecie;
    private JTextField campoRaca;
    private JTextField campoDataNascimento;
    private JComboBox<String> comboTutor;

    private ArrayList<TutorDTO> tutoresCarregados;

    public CadastroPetView() {
        initComponents();
        carregarTutores();
    }

    private void initComponents() {
        setTitle("VetCare - Cadastro de Pet");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(460, 580);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(EstiloUI.FUNDO);
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        painel.add(EstiloUI.criarTitulo("Cadastro de Pet"));
        painel.add(Box.createVerticalStrut(20));

        campoNome = EstiloUI.criarCampoTexto();
        campoEspecie = EstiloUI.criarCampoTexto();
        campoRaca = EstiloUI.criarCampoTexto();
        campoDataNascimento = EstiloUI.criarCampoTexto();

        comboTutor = new JComboBox<>();
        comboTutor.addItem("Selecione o tutor...");
        comboTutor.setFont(EstiloUI.FONTE_CAMPO);
        comboTutor.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        comboTutor.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));

        painel.add(EstiloUI.criarCampoComRotulo("Nome do pet", campoNome));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Especie", campoEspecie));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Raca", campoRaca));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Data de nascimento (dd/mm/aaaa)", campoDataNascimento));
        painel.add(Box.createVerticalStrut(14));
        painel.add(EstiloUI.criarCampoComRotulo("Tutor", comboTutor));
        painel.add(Box.createVerticalStrut(24));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        JButton btnSalvar = EstiloUI.criarBotaoPrimario("Salvar");
        JButton btnVoltar = EstiloUI.criarBotaoSecundario("Voltar");

        btnSalvar.addActionListener(evt -> salvarPet());
        btnVoltar.addActionListener(evt -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(Box.createHorizontalStrut(12));
        painelBotoes.add(btnVoltar);

        painel.add(painelBotoes);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painel, BorderLayout.CENTER);
    }

    /**
     * Busca os tutores cadastrados no banco de dados e preenche o combo.
     */
    private void carregarTutores() {
        TutorDAO tutorDAO = new TutorDAO();
        tutoresCarregados = tutorDAO.listarTutores();

        comboTutor.removeAllItems();
        comboTutor.addItem("Selecione o tutor...");
        for (TutorDTO tutor : tutoresCarregados) {
            comboTutor.addItem(tutor.getNome());
        }
    }

    private void salvarPet() {
        String nome = campoNome.getText().trim();
        String especie = campoEspecie.getText().trim();
        String raca = campoRaca.getText().trim();
        String dataNascimento = campoDataNascimento.getText().trim();
        int indiceTutor = comboTutor.getSelectedIndex();

        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || dataNascimento.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos antes de salvar.",
                    "Cadastro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (indiceTutor <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione o tutor do pet.",
                    "Cadastro nao realizado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TutorDTO tutorSelecionado = tutoresCarregados.get(indiceTutor - 1);

        PetDTO pet = new PetDTO();
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        pet.setDataNascimento(dataNascimento);
        pet.setTutorId(tutorSelecionado.getId());

        PetDAO petDAO = new PetDAO();
        boolean sucesso = petDAO.cadastrarPet(pet);

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                    "Pet cadastrado com sucesso para o tutor " + tutorSelecionado.getNome() + "!",
                    "Cadastro realizado", JOptionPane.INFORMATION_MESSAGE);

            campoNome.setText("");
            campoEspecie.setText("");
            campoRaca.setText("");
            campoDataNascimento.setText("");
            comboTutor.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nao foi possivel cadastrar o pet.",
                    "Cadastro nao realizado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new CadastroPetView().setVisible(true);
        });
    }
}
