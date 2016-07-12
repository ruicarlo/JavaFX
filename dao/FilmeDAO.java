package dao;

import bean.Filme;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioFilme;

public class FilmeDAO extends GenericDAO {

    private Filme filme;

    public FilmeDAO() throws ClassNotFoundException {

    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public boolean salvar() {
        String sql = "INSERT INTO filme (codigo, nome, genero, sinopse) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = getConn().prepareStatement(sql);
            statement.setString(1, filme.getCodigo());
            statement.setString(2, filme.getNome());
            statement.setString(3, filme.getGenero());
            statement.setString(4, filme.getSinopse());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar() {
        String sql = "UPDATE filme SET nome=?, genero=?, sinopse=? WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setString(1, filme.getNome());
            smtp.setString(2, filme.getGenero());
            smtp.setString(3, filme.getSinopse());
            smtp.setString(4, filme.getCodigo());

            int rowsUpdated = smtp.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean excluir() {
        String sql = "DELETE FROM filme WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setString(1, filme.getCodigo());

            int rowsDeleted = smtp.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public RepositorioFilme buscarTodos() {
        String sql = "SELECT * FROM filme";
        RepositorioFilme repositorio = new RepositorioFilme();
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            ResultSet result = smtp.executeQuery();
            while (result.next()) {
                repositorio.add(new Filme(result.getString("codigo"), result.getString("nome"), result.getString("genero"), result.getString("sinopse")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repositorio;
    }

    public Filme buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM filme WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setString(1, codigo);
            ResultSet result = smtp.executeQuery();
            if (result.next()) {
                return (new Filme(result.getString("codigo"), result.getString("nome"), result.getString("genero"), result.getString("sinopse")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
