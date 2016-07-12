package dao;

import java.sql.Connection;
import util.ConexaoBD;

public class GenericDAO {

    private Connection conn;

    public GenericDAO() throws ClassNotFoundException {
        conn = new ConexaoBD().getCon();
    }

    public Connection getConn() {
        return conn;
    }

}
