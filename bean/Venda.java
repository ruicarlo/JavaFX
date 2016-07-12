package bean;

public class Venda {

    private Secao Secao;
    private int idVenda;

    public Venda(Secao Secao) {
        this.Secao = Secao;
    }

    public void setSecao(Secao Secao) {
        this.Secao = Secao;
    }

    public Secao getSecao() {
        return Secao;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

}
