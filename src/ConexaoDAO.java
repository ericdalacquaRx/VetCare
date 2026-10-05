import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe responsável por estabelecer a conexão com o banco de dados MySQL
 * do sistema VetCare.
 *
 * @author VetCare
 */
public class ConexaoDAO {

    public Connection connectDB() throws Exception {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost/vetcare?user=root&password=ca931748&serverTimezone=America/Sao_Paulo");
        return conn;
    }
}
