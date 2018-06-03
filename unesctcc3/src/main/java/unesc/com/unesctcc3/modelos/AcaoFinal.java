package unesc.com.unesctcc3.modelos;

import java.util.List;

import unesc.com.unesctcc3.motor.RegistroAtividades;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;


public class AcaoFinal {
    private final MetodosUtilitario metodosUtilitario;
    protected Atividade atividade;
    RegistroAtividades registroAtividades;

    public AcaoFinal(RegistroAtividades registroAtividades) {
        this.registroAtividades = registroAtividades;
        this.metodosUtilitario = new MetodosUtilitario();
    }

    public AcaoFinal reproduzirAcoes() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) MetodosUtilitario.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.REPRODUZIR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }

    public AcaoFinal verificarValores() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) MetodosUtilitario.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.VERIFICAR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }
}
