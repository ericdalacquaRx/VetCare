import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Tela de historico de consultas de um pet, conectada ao banco de dados.
 *
 * @author VetCare
 */
public class HistoricoConsultasView extends JFrame {

    private JComboBox<String> comboPet;
    private JTable tabelaConsultas;

    private ArrayList<PetDTO> petsCarregados;

    public HistoricoConsultasView() {
        initComponents();
        carregarPets();
    }

    private void initComponents() {
        setTitle("VetCare - Historico de Consultas");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(560, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(EstiloUI.FUNDO);
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        painel.add(EstiloUI.criarTitulo("Historico de Consultas"));
        painel.add(Box.createVerticalStrut(20));

        comboPet = new JComboBox<>();
        comboPet.addItem("Selecione o pet...");
        comboPet.setFont(EstiloUI.FONTE_CAMPO);
        comboPet.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboPet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        comboPet.addActionListener(evt -> carregarConsultas());

        painel.add(EstiloUI.criarCampoComRotulo("Pet", comboPet));
        painel.add(Box.createVerticalStrut(16));

        String[] colunas = {"Data", "Motivo", "Diagnostico", "Veterinario", "Valor"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaConsultas = new JTable(modelo);
        tabelaConsultas.setFont(EstiloUI.FONTE_CAMPO);
        tabelaConsultas.setRowHeight(26);
        tabelaConsultas.getTableHeader().setBackground(EstiloUI.PRIMARIA);
        tabelaConsultas.getTableHeader().setForeground(Color.WHITE);
        tabelaConsultas.setGridColor(EstiloUI.BORDA);

        JScrollPane scroll = new JScrollPane(tabelaConsultas);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        scroll.setPreferredSize(new Dimension(480, 180));

        painel.add(scroll);
        painel.add(Box.createVerticalStrut(20));

        JButton btnVoltar = EstiloUI.criarBotaoSecundario("Voltar");
        btnVoltar.addActionListener(evt -> dispose());
        painel.add(btnVoltar);

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

    /**
     * Busca no banco de dados as consultas do pet selecionado no combo e
     * atualiza a tabela.
     */
    private void carregarConsultas() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaConsultas.getModel();
        modelo.setRowCount(0);

        int indicePet = comboPet.getSelectedIndex();
        if (indicePet <= 0) {
            return;
        }

        PetDTO petSelecionado = petsCarregados.get(indicePet - 1);

        ConsultaDAO consultaDAO = new ConsultaDAO();
        ArrayList<ConsultaDTO> consultas = consultaDAO.listarConsultasPorPet(petSelecionado.getId());

        for (ConsultaDTO consulta : consultas) {
            modelo.addRow(new Object[]{
                consulta.getDataConsulta(),
                consulta.getMotivo(),
                consulta.getDiagnostico(),
                consulta.getVeterinario(),
                String.format("R$ %.2f", consulta.getValor())
            });
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new HistoricoConsultasView().setVisible(true);
        });
    }
}
