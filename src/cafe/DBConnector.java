package cafe;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DBConnector extends Connector{

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7752007";
    private static final String USUARIO = "sql7752007";
    private static final String CONTRASEÑA = "MwdLBNmSV5";

    static Connection conn = null;
    public ArrayList<Slot> slots = new ArrayList<>();

    public void conexion() {
        try {
            System.out.println("Intentando conectarse a la base de datos...");
            conn = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

    }
}