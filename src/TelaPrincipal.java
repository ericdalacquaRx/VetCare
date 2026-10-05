import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Tela principal (menu) do sistema VetCare. A partir dela, o usuário
 * navega para as demais telas do sistema.
 *
 * @author VetCare
 */
public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("VetCare - Menu Principal");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(480, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(EstiloUI.FUNDO);
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(32, 36, 32, 36));

        painel.add(EstiloUI.criarTitulo("VetCare"));
        painel.add(Box.createVerticalStrut(4));
        painel.add(EstiloUI.criarSubtitulo("Selecione uma opcao:"));
        painel.add(Box.createVerticalStrut(24));

        JButton btnTutor = criarBotaoMenu("Cadastrar Tutor");
        JButton btnPet = criarBotaoMenu("Cadastrar Pet");
        JButton btnConsulta = criarBotaoMenu("Registrar Consulta");
        JButton btnHistorico = criarBotaoMenu("Historico de Consultas");

        btnTutor.addActionListener(evt -> new CadastroTutorView().setVisible(true));
        btnPet.addActionListener(evt -> new CadastroPetView().setVisible(true));
        btnConsulta.addActionListener(evt -> new ConsultaView().setVisible(true));
        btnHistorico.addActionListener(evt -> new HistoricoConsultasView().setVisible(true));

        painel.add(btnTutor);
        painel.add(Box.createVerticalStrut(14));
        painel.add(btnPet);
        painel.add(Box.createVerticalStrut(14));
        painel.add(btnConsulta);
        painel.add(Box.createVerticalStrut(14));
        painel.add(btnHistorico);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painel, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton b = new JButton(texto);
        b.setFont(EstiloUI.FONTE_BOTAO);
        b.setForeground(EstiloUI.PRIMARIA);
        b.setBackground(EstiloUI.BRANCO);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(JButton.LEFT);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(EstiloUI.BORDA, 1),
                javax.swing.BorderFactory.createEmptyBorder(14, 20, 14, 20)));
        return b;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
