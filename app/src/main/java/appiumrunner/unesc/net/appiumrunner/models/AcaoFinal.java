package appiumrunner.unesc.net.appiumrunner.models;

import java.util.List;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class AcaoFinal {

    protected Estado estado;
    Registrador registrador;

    protected AcaoFinal() {

    }

    public AcaoFinal(Registrador registrador) {
        this.registrador = registrador;
    }

    public AcaoFinal reproduzir() {
        ((EstadoAcesso) estado).getAcoes().add(Estado.TipoAcao.REPRODUZIR);
        return this;
    }

    public AcaoFinal verificar() {

        ((EstadoAcesso) estado).getAcoes().add(Estado.TipoAcao.VERIFICAR);
        return this;
    }

    public Boolean finalizar() {

        registrador.registrar(estado);
        return true;
    }

    private static class EstadoAcesso extends Estado {

        public EstadoAcesso(Registrador registrador) {
            super(registrador);

        }

        @Override
        public List<TipoAcao> getAcoes() {

            return acoes;
        }
    }
}
