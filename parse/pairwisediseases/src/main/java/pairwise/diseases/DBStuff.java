package pairwise.diseases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * I don't know all that connection new fangled factory stuff but what I do know is that I want a connection and
 * only one connection, I don't want to do it often and I don't want to know how it happened I just want it to work
 * @author nkibbey
 */
public class DBStuff {
    //--------------------DB STUFFS
    public static final String DB_NAME = "w2vdb";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/"+ DB_NAME;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "maninsert";
    private static final String DB_PASSWORD = "insert";

    private Connection conn = null;

    //--------------------SINGLETON STUFFS
    private static DBStuff ourInstance = new DBStuff();

    public static DBStuff getInstance() {
        return ourInstance;
    }

    private DBStuff() {
    }

    /**
     * sets a connection to class defined access of db ; auto commit explicitly on
     * @return new connection
     */
    public Connection getConnection() throws RuntimeException {
        try{
            if (conn == null || conn.isClosed()) {
                Class.forName(DB_DRIVER);
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                conn.setAutoCommit(true);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("class not found in get connection"+e);
        } catch (SQLException e) {
            System.err.println("sql exception in connecting "+e );
        }

        if (conn == null) {
            System.err.println("dead connection");
            throw new RuntimeException("sad boys");
        }

        return conn;
    }

    /**
     * don't give me problems and I won't give you problems... don't try to close a null
     * @throws RuntimeException
     */
    public void endConnection() throws RuntimeException{

        try {
            if (conn == null)//because why not stack exceptions
                throw new SQLException("why would you close a dead connection?!?");
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("connection close issue "+e.getMessage());
        }
    }

}
