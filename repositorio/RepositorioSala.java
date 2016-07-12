package repositorio;

import bean.Sala;

public class RepositorioSala extends IRepositorio<Sala> {

  public Sala buscarPorNumero(Integer numero) {
    for (Sala sala : lista) {
      if (sala.getNumero().equals(numero)) {
        return sala;
      }
    }
    return null;
  }

  public boolean existeSala(Integer numero) {
    return buscarPorNumero(numero) != null;
  }
}
