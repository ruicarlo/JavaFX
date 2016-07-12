package repositorio;

import java.util.Date;
import bean.Secao;

public class RepositorioSecao extends IRepositorio<Secao> {
  
  public Secao buscarPorHorario(Date horario) {
    for(Secao secao : lista) {
      if(secao.getHorario().equals(horario)) {
        return secao;
      }
    }
    return null;
  }

  public Secao buscarPorCodigo(int codigo) {
    for(Secao secao : lista) {
      if(secao.getCodigo() == codigo) {
        return secao;
      }
    }
    return null;
  }
  
  public boolean existeSecaoPorCodigo(int codigo) {
    return buscarPorCodigo(codigo) != null;
  }

  public boolean existeSecaoPorHorario(Date horario) {
    return buscarPorHorario(horario) != null;
  }
  
}
