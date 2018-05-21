package unesc.com.unesctcc3.modelos;

import java.util.List;

import unesc.com.unesctcc3.motor.AlgoritmoRegistro;
import unesc.com.unesctcc3.utilitarios.UtilitarioMetodos;


public class AcaoFinal {
    protected Atividade atividade;
    AlgoritmoRegistro algoritmoRegistro;

    public AcaoFinal(AlgoritmoRegistro algoritmoRegistro) {
        this.algoritmoRegistro = algoritmoRegistro;
    }

    public AcaoFinal reproduzirAcoes() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) UtilitarioMetodos.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.REPRODUZIR);
        algoritmoRegistro.registrar(atividade);
        return this;
    }

    public AcaoFinal verificarValores() {
        List<Atividade.TipoAcao> acoes = (List<Atividade.TipoAcao>) UtilitarioMetodos.invocarMetodo(atividade, "getAcoes");
        acoes.add(Atividade.TipoAcao.VERIFICAR);
        algoritmoRegistro.registrar(atividade);
        return this;
    }
}
