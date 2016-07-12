package repositorio;

import bean.Filme;

public class RepositorioFilme extends IRepositorio<Filme> {

  public Filme buscarPorCodigo(String codigo) {
    for (Filme filme : lista) {
      if (filme.getCodigo().equalsIgnoreCase(codigo)) {
        return filme;
      }
    }
    return null;
  }

  public Filme buscarPorNome(String nome) {
    for (Filme filme : lista) {
      if (filme.getNome().equalsIgnoreCase(nome)) {
        return filme;
      }
    }
    return null;
  }

  public boolean existeFilmePorCodigo(String codigo) {
    return buscarPorCodigo(codigo) != null;
  }

  public boolean existeFilmePorNome(String nome) {
    return buscarPorNome(nome) != null;
  }
}