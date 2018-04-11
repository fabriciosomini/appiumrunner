package appiumrunner.unesc.net.appiumrunner.states;

import java.util.ArrayList;
import java.util.List;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.models.AcaoFinal;

/**
 * Created by fabri on 18/03/2018.
 */

public class Estado extends AcaoFinal {
    private final Registrador registrador;

    private List<Estado.TipoAcao> acoes;
    private Foco estadoFoco;
    private String identificadorElemento;
    private StringBuilder estadoTexto;
    private StringBuilder estadoSelecao;
    private boolean estadoMarcacaoOpcao;
    private int estadoProgresso;


    public Estado(Registrador registrador) {
        super(registrador);
        this.registrador = registrador;
        this.acoes = new ArrayList<>();
        estado = this;
    }

    public Estado selecionarValor(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        acoes.add(TipoAcao.SELECIONAR);
        return this;
    }

    public Estado focarElemento() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Estado limparValor() {
        this.estadoTexto = new StringBuilder("");
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }

    public Estado escreverValor(String estadoTexto) {

        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }

    public Estado rolarAteElemento() {
        acoes.add(TipoAcao.ROLAR);
        return this;
    }

    public Estado clicarElemento() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.CLICAR);
        return this;
    }

    public Estado desfocarCampo() {
        this.estadoFoco = Foco.SEM_FOCO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Estado deslizarBarraProgresso(int estadoProgresso) {
        this.estadoProgresso = new Integer(estadoProgresso);
        acoes.add(TipoAcao.PROGREDIR);
        return this;
    }


    public Estado marcarOpcaoDesmarcavel(boolean marcar) {
        this.estadoMarcacaoOpcao = marcar;
        acoes.add(TipoAcao.MARCAR_OPCAO_DESMARCAVEL);
        return this;
    }

    public Estado marcarOpcao() {

        acoes.add(TipoAcao.MARCAR_OPCAO);
        return this;
    }

    //-------------------------------------------
    private List<TipoAcao> getAcoes() {
        return acoes;
    }

    private StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }

    private Foco getEstadoFoco() {
        return estadoFoco;
    }

    private StringBuilder getEstadoTexto() {
        return estadoTexto;
    }


    private String getIdentificadorElemento() {
        return identificadorElemento;
    }

    private Estado setIdentificadorElemento(String identificador) {

        this.identificadorElemento = identificador;
        return this;
    }

    private Integer getEstadoProgresso() {
        return estadoProgresso;
    }

    public enum Verificao {
        FINAL_ESTADO,
        POR_PROPRIEDADE
    }

    public enum Foco {
        FOCADO,
        SEM_FOCO,
        IGNORAR
    }

    public enum TipoAcao {
        FOCAR,
        ESCREVER,
        VERIFICAR, REPRODUZIR, ROLAR, CLICAR, PROGREDIR, MARCAR_OPCAO, MARCAR_OPCAO_DESMARCAVEL, SELECIONAR
    }


}
