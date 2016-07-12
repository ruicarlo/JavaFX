package dao;

import bean.Sala;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSala;

public class SalaDAO extends GenericDAO  {

    private Sala sala;

    public SalaDAO() throws ClassNotFoundException {

    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public boolean salvar() {
        String sql = "INSERT INTO sala (numero,capacidade) VALUES (?, ?)";
        try {
            PreparedStatement statement = getConn().prepareStatement(sql);
            statement.setInt(1, sala.getNumero());
            statement.setInt(2, sala.getCapacidade());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar() {
        String sql = "UPDATE sala SET capacidade=? WHERE numero=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, sala.getCapacidade());
            smtp.setInt(2, sala.getNumero());


            int rowsUpdated = smtp.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean excluir() {
        String sql = "DELETE FROM sala WHERE numero=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, sala.getNumero());

            int rowsDeleted = smtp.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public RepositorioSala buscarTodos() {
        String sql = "SELECT * FROM sala";
        RepositorioSala repositorio = new RepositorioSala();
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            ResultSet result = smtp.executeQuery();
            while (result.next()) {
                repositorio.add(new Sala(result.getInt("numero"), result.getInt("capacidade")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repositorio;
    }

    public Sala buscarPorNumero(int numero) {
        String sql = "SELECT * FROM sala WHERE numero=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, numero);
            ResultSet result = smtp.executeQuery();
            if (result.next()) {
                return (new Sala(result.getInt("numero"), result.getInt("capacidade")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
