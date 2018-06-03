package unesc.com.unesctcc3.modelos;

import java.util.List;

import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;


public class AcaoFinal {
    protected Atividade atividade;
    Registro registro;

    public AcaoFinal(Registro registro) {
        this.registro = registro;
    }

    public AcaoFinal reproduzirAcoes() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) MetodosUtilitario.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.REPRODUZIR);
        registro.registrar(atividade);
        return this;
    }

    public AcaoFinal verificarValores() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) MetodosUtilitario.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.VERIFICAR);
        registro.registrar(atividade);
        return this;
    }
}
