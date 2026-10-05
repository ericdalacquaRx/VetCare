import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO (Data Access Object) responsável pelas operações de acesso a
 * dados relacionadas a Tutores no sistema VetCare.
 *
 * Observação: a implementação completa e os testes com o banco de dados
 * real serão finalizados na Etapa 4 do projeto integrador.
 *
 * @author VetCare
 */
public class TutorDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<TutorDTO> listagem = new ArrayList<>();

    public boolean cadastrarTutor(TutorDTO tutor) {

        boolean sucesso = false;

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "INSERT INTO tutores (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";
            prep = conn.prepareStatement(sql);

            prep.setString(1, tutor.getNome());
            prep.setString(2, tutor.getTelefone());
            prep.setString(3, tutor.getEmail());
            prep.setString(4, tutor.getEndereco());

            int linhasAfetadas = prep.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar tutor: " + erro.getMessage());
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

    public ArrayList<TutorDTO> listarTutores() {

        listagem = new ArrayList<>();

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "SELECT id, nome, telefone, email, endereco FROM tutores";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                TutorDTO tutor = new TutorDTO();
                tutor.setId(resultset.getInt("id"));
                tutor.setNome(resultset.getString("nome"));
                tutor.setTelefone(resultset.getString("telefone"));
                tutor.setEmail(resultset.getString("email"));
                tutor.setEndereco(resultset.getString("endereco"));

                listagem.add(tutor);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar tutores: " + erro.getMessage());
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
