import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO (Data Access Object) responsável pelas operações de acesso a
 * dados relacionadas a Consultas veterinárias no sistema VetCare.
 *
 * Observação: a implementação completa e os testes com o banco de dados
 * real serão finalizados na Etapa 4 do projeto integrador.
 *
 * @author VetCare
 */
public class ConsultaDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ConsultaDTO> listagem = new ArrayList<>();

    public boolean registrarConsulta(ConsultaDTO consulta) {

        boolean sucesso = false;

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "INSERT INTO consultas (pet_id, data_consulta, motivo, diagnostico, veterinario, valor) VALUES (?, ?, ?, ?, ?, ?)";
            prep = conn.prepareStatement(sql);

            prep.setInt(1, consulta.getPetId());
            prep.setString(2, consulta.getDataConsulta());
            prep.setString(3, consulta.getMotivo());
            prep.setString(4, consulta.getDiagnostico());
            prep.setString(5, consulta.getVeterinario());
            prep.setDouble(6, consulta.getValor());

            int linhasAfetadas = prep.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar consulta: " + erro.getMessage());
            sucesso = false;
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception erroFechar) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + erroFechar.getMessage());
            }
        }

        return sucesso;
    }

    public ArrayList<ConsultaDTO> listarConsultasPorPet(int petId) {

        listagem = new ArrayList<>();

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "SELECT id, pet_id, data_consulta, motivo, diagnostico, veterinario, valor FROM consultas WHERE pet_id = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, petId);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ConsultaDTO consulta = new ConsultaDTO();
                consulta.setId(resultset.getInt("id"));
                consulta.setPetId(resultset.getInt("pet_id"));
                consulta.setDataConsulta(resultset.getString("data_consulta"));
                consulta.setMotivo(resultset.getString("motivo"));
                consulta.setDiagnostico(resultset.getString("diagnostico"));
                consulta.setVeterinario(resultset.getString("veterinario"));
                consulta.setValor(resultset.getDouble("valor"));

                listagem.add(consulta);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar consultas: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception erroFechar) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + erroFechar.getMessage());
            }
        }

        return listagem;
    }
}
