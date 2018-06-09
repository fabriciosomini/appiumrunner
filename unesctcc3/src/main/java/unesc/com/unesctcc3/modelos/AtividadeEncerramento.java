package unesc.com.unesctcc3.modelos;

import java.util.List;

import unesc.com.unesctcc3.motor.RegistroAtividades;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;


public class AtividadeEncerramento {
    private final MetodosUtilitario metodosUtilitario;
    protected Atividade atividade;
    RegistroAtividades registroAtividades;

    public AtividadeEncerramento(RegistroAtividades registroAtividades) {
        this.registroAtividades = (RegistroAtividades) MetodosUtilitario.invocarMetodo(new RegistroAtividades(), "getINSTANCE");
        this.metodosUtilitario = new MetodosUtilitario();
    }

    public AtividadeEncerramento reproduzirAcoes() {
        List<Atividade.TipoAtividade> tipoAtividades = (List<Atividade.TipoAtividade>) MetodosUtilitario.invocarMetodo(atividade, "getTipoAtividades");
        tipoAtividades.add(Atividade.TipoAtividade.REPRODUZIR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }

    public AtividadeEncerramento verificarValores() {
        List<Atividade.TipoAtividade> tipoAtividades = (List<Atividade.TipoAtividade>) MetodosUtilitario.invocarMetodo(atividade, "getTipoAtividades");
        tipoAtividades.add(Atividade.TipoAtividade.VERIFICAR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }
}
