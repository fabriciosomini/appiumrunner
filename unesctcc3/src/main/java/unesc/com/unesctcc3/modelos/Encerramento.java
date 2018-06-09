package unesc.com.unesctcc3.modelos;

import java.util.List;

import unesc.com.unesctcc3.motor.RegistroAtividades;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;


public class Encerramento {
    private final MetodosUtilitario metodosUtilitario;
    protected Atividade atividade;
    RegistroAtividades registroAtividades;

    public Encerramento(RegistroAtividades registroAtividades) {
        this.registroAtividades = (RegistroAtividades) MetodosUtilitario.invocarMetodo(new RegistroAtividades(), "getINSTANCE");
        this.metodosUtilitario = new MetodosUtilitario();
    }

    public Encerramento reproduzirAcoes() {
        List<Atividade.TipoAtividade> tipoAtividades = (List<Atividade.TipoAtividade>) MetodosUtilitario.invocarMetodo(atividade, "getTipoAtividades");
        tipoAtividades.add(Atividade.TipoAtividade.REPRODUZIR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }

    public Encerramento verificarValores() {
        List<Atividade.TipoAtividade> tipoAtividades = (List<Atividade.TipoAtividade>) MetodosUtilitario.invocarMetodo(atividade, "getTipoAtividades");
        tipoAtividades.add(Atividade.TipoAtividade.VERIFICAR);
        MetodosUtilitario.invocarMetodo(registroAtividades, "adicionarAtividade", Atividade.class, atividade);
        return this;
    }
}
