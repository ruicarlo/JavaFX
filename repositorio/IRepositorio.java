package repositorio;

import java.util.ArrayList;

public class IRepositorio<T> {

    protected ArrayList<T> lista;

    public IRepositorio() {
        lista = new ArrayList<>();
    }

    public void setLista(ArrayList<T> lista) {
        this.lista = lista;
    }

    public void add(T atributo) {
        lista.add(atributo);
    }

    public ArrayList<T> getLista() {
        return lista;
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }
}
