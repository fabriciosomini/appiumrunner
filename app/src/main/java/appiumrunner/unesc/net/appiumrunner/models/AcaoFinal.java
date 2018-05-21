package appiumrunner.unesc.net.appiumrunner.models;

import java.util.List;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.motor.AlgoritmoRegistro;
import unesc.com.unesctcc3.utilitarios.UtilitarioMetodos;


public class AcaoFinal {
    protected Atividade estado;
    AlgoritmoRegistro registrador;

    public AcaoFinal(AlgoritmoRegistro registrador) {
        this.registrador = registrador;
    }

    public AcaoFinal reproduzirAcoes() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) UtilitarioMetodos.invocarMetodo(estado, "getAcoes");
        acoes.add(Atividade.TipoAcao.REPRODUZIR);
        registrador.registrar(estado);
        return this;
    }

    public AcaoFinal verificarValores() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) UtilitarioMetodos.invocarMetodo(estado, "getAcoes");
        acoes.add(Atividade.TipoAcao.VERIFICAR);
        registrador.registrar(estado);
        return this;
    }
}
