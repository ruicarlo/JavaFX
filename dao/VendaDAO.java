package dao;

import bean.Venda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioVenda;

public class VendaDAO extends GenericDAO {

    private Venda venda;

    public VendaDAO() throws ClassNotFoundException {

    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public boolean salvar() {
        String sql = "INSERT INTO venda (codigo_secao) VALUES (?)";
        try {
            PreparedStatement statement = getConn().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, venda.getSecao().getCodigo());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        venda.setIdVenda(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar() {
        String sql = "UPDATE venda SET codigo_secao = ? WHERE id_venda=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, venda.getSecao().getCodigo());
            smtp.setInt(2, venda.getIdVenda());

            int rowsUpdated = smtp.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean excluir() {
        String sql = "DELETE FROM venda WHERE id_venda=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, venda.getIdVenda());

            int rowsDeleted = smtp.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public RepositorioVenda buscarTodos() {
        String sql = "SELECT * FROM venda";
        RepositorioVenda repositorio = new RepositorioVenda();
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            ResultSet result = smtp.executeQuery();
            while (result.next()) {
                try {
                    Venda vendaSecao = new Venda(new SecaoDAO().buscarPorCodigo(result.getInt("codigo_secao")));
                    vendaSecao.setIdVenda(result.getInt("id_venda"));
                    repositorio.add(vendaSecao);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repositorio;
    }

    public Venda buscarPorId(int idVenda) {
        String sql = "SELECT * FROM venda WHERE id_venda=?";
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            smtp.setInt(1, idVenda);
            ResultSet result = smtp.executeQuery();
            if (result.next()) {
                try {
                    Venda vendaSecao = new Venda(new SecaoDAO().buscarPorCodigo(result.getInt("codigo_secao")));
                    vendaSecao.setIdVenda(result.getInt("id_venda"));
                    return vendaSecao;
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
