package bean;

import java.util.Date;
import util.DateUtil;

public class Secao {

    private Sala sala;
    private Filme filme;
    private Date horario;
    private int codigo;

    public Secao(Sala sala, Filme filme, Date horario, int codigo) {
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;

        if (0 != codigo) {
            this.codigo = codigo;
        }
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Sala getSala() {
        return sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Date getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return "Sala: "+sala.getNumero() + "\tHora: "+DateUtil.HourToString(horario) + "\tFilme: " + filme.getNome();
    }
}
