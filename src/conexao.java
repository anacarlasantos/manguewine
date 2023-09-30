import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    private static final String USUARIO = "root";
    private static final String SENHA = "1234";
    private static final String URL = "jdbc:mysql://localhost:3306/Estoque";
    private static Connection adega = null;

    public static Connection abrir() {
        try {
            if (adega == null || adega.isClosed()) {
                adega = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
            return adega;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fechar() {
        try {
            if (adega != null && !adega.isClosed()) {
                adega.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

