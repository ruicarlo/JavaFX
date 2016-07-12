package dao;

import bean.Secao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSecao;

public class SecaoDAO extends GenericDAO {

    private Secao secao;

    public SecaoDAO() throws ClassNotFoundException {

    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public boolean salvar() {
        String sql = "INSERT INTO secao (numero_sala,codigo_filme,horario) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = getConn().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, secao.getSala().getNumero());
            statement.setString(2, secao.getFilme().getCodigo());
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(secao.getHorario().getTime());
            statement.setString(3, currentTime);
            
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        secao.setCodigo(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar() {
        String sql = "UPDATE secao SET numero_sala = ?, codigo_filme = ?, horario = ? WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt   (1, secao.getSala().getNumero());
            smtp.setString(2, secao.getFilme().getCodigo());

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(secao.getHorario().getTime());
            smtp.setString(3, currentTime);
            smtp.setInt   (4, secao.getCodigo());

            int rowsUpdated = smtp.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean excluir() {
        String sql = "DELETE FROM secao WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, secao.getCodigo());

            int rowsDeleted = smtp.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public RepositorioSecao buscarTodos() {
        String sql = "SELECT * FROM secao";
        RepositorioSecao repositorio = new RepositorioSecao();
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            ResultSet result = smtp.executeQuery();
            while (result.next()) {
                try {
                    repositorio.add(new Secao(new SalaDAO().buscarPorNumero(result.getInt("numero_sala")),
                            new FilmeDAO().buscarPorCodigo(result.getString("codigo_filme")),
                            result.getTimestamp("horario"),
                            result.getInt("codigo"))
                    );
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repositorio;
    }

    public int buscarLugaresPorCodigo(int codigo) {
        String sql = "select sala.capacidade-count(1) livres from secao join sala on sala.numero = secao.numero_sala left join venda on venda.codigo_secao = secao.codigo where secao.codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, codigo);
            ResultSet result = smtp.executeQuery();
            if (result.next()) {
                return result.getInt("livres");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Secao buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM secao WHERE codigo=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, codigo);
            ResultSet result = smtp.executeQuery();
            if (result.next()) {
                try {
                    return (new Secao(new SalaDAO().buscarPorNumero(result.getInt("numero_sala")),
                                      new FilmeDAO().buscarPorCodigo(result.getString("codigo_filme")),
                                      result.getTimestamp("horario"), 
                                      codigo)
                    );
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
