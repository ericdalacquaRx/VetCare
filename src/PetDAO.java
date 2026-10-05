import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO (Data Access Object) responsável pelas operações de acesso a
 * dados relacionadas a Pets no sistema VetCare.
 *
 * Observação: a implementação completa e os testes com o banco de dados
 * real serão finalizados na Etapa 4 do projeto integrador.
 *
 * @author VetCare
 */
public class PetDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<PetDTO> listagem = new ArrayList<>();

    public boolean cadastrarPet(PetDTO pet) {

        boolean sucesso = false;

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "INSERT INTO pets (nome, especie, raca, data_nascimento, tutor_id) VALUES (?, ?, ?, ?, ?)";
            prep = conn.prepareStatement(sql);

            prep.setString(1, pet.getNome());
            prep.setString(2, pet.getEspecie());
            prep.setString(3, pet.getRaca());
            prep.setString(4, pet.getDataNascimento());
            prep.setInt(5, pet.getTutorId());

            int linhasAfetadas = prep.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar pet: " + erro.getMessage());
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

    public ArrayList<PetDTO> listarPetsPorTutor(int tutorId) {

        listagem = new ArrayList<>();

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "SELECT id, nome, especie, raca, data_nascimento, tutor_id FROM pets WHERE tutor_id = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, tutorId);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                PetDTO pet = new PetDTO();
                pet.setId(resultset.getInt("id"));
                pet.setNome(resultset.getString("nome"));
                pet.setEspecie(resultset.getString("especie"));
                pet.setRaca(resultset.getString("raca"));
                pet.setDataNascimento(resultset.getString("data_nascimento"));
                pet.setTutorId(resultset.getInt("tutor_id"));

                listagem.add(pet);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar pets: " + erro.getMessage());
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

    public ArrayList<PetDTO> listarTodosPets() {

        listagem = new ArrayList<>();

        try {
            conn = new ConexaoDAO().connectDB();

            String sql = "SELECT id, nome, especie, raca, data_nascimento, tutor_id FROM pets";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                PetDTO pet = new PetDTO();
                pet.setId(resultset.getInt("id"));
                pet.setNome(resultset.getString("nome"));
                pet.setEspecie(resultset.getString("especie"));
                pet.setRaca(resultset.getString("raca"));
                pet.setDataNascimento(resultset.getString("data_nascimento"));
                pet.setTutorId(resultset.getInt("tutor_id"));

                listagem.add(pet);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar pets: " + erro.getMessage());
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
