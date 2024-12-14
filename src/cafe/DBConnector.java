package cafe;

import java.sql.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

;

public class DBConnector extends Connector {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7752007";
    private static final String USUARIO = "sql7752007";
    private static final String CONTRASEÑA = "MwdLBNmSV5";

    static Connection conn = null;
    public ArrayList<Slot> slots = new ArrayList<>();

    public void connect() {
        try {
            System.out.println("Intentando conectarse a la base de datos...");
            conn = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            conn.close();
            System.out.println("CONEXION CERRADA");
        } catch (SQLException e) {
            System.err.println("Error al desconectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Document consultMake(String nombre) {
        Document doc = null;
        try{
        Statement stmt = (Statement) conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM HotDrinks WHERE name= '" + nombre + "'");
           
        if (rs.next()) {
            String name = rs.getString("name");
            int stock = rs.getInt("stock");

            // Crear documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            // Elemento raíz
            Element rootElement = doc.createElement("drink");
            doc.appendChild(rootElement);

            // Elemento nombre
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));
            rootElement.appendChild(nameElement);

            // Elemento stock
            Element stockElement = doc.createElement("stock");
            stockElement.appendChild(doc.createTextNode(String.valueOf(stock)));
            rootElement.appendChild(stockElement);

            // Imprimir XML
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(System.out);
            transformer.transform(source, result);
        } else {
            System.out.println("No se encontraron resultados.");
        }
        rs.close();
        stmt.close();
        return doc;
        }catch (SQLException ex){
            System.out.println("Error al realizar la consulta: ");
            ex.printStackTrace();
        }finally{
        return doc;
        }
    }
}
