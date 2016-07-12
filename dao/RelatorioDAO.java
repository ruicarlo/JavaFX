package dao;

import bean.Secao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSecao;

public class RelatorioDAO extends GenericDAO {

    public RelatorioDAO() throws ClassNotFoundException {

    }

    public RepositorioSecao relatorioPorSecao() {
        String sql = "select distinct secao.codigo as codigo_secao, " +
                     "       sala.numero as numero_sala," +
                     "       secao.horario as horario_filme," +
                     "       filme.codigo codigo_filme " +
                     "  from venda " +
                     "  join secao on secao.codigo = venda.codigo_secao " +
                     "  join sala on sala.numero = secao.numero_sala" +
                     "  join filme on filme.codigo = secao.codigo_filme";

        RepositorioSecao repositorio = new RepositorioSecao();
        try {
            PreparedStatement smtp = getConn().prepareStatement(sql);
            ResultSet result = smtp.executeQuery();
            while (result.next()) {
                try {
                    repositorio.add(new Secao(new SalaDAO().buscarPorNumero(result.getInt("numero_sala")),
                                              new FilmeDAO().buscarPorCodigo(result.getString("codigo_filme")),
                                              result.getTimestamp("horario_filme"),
                                              result.getInt("codigo_secao"))
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
}