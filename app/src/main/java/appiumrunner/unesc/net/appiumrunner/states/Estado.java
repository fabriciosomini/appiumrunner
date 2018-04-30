package appiumrunner.unesc.net.appiumrunner.states;

import java.util.ArrayList;
import java.util.Calendar;
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
    private Estado.Marcacao estadoMarcacaoOpcao;
    private int estadoProgresso;
    private Tecla estadoTecla;
    private Foco estadoDesfoque;
    private StringBuilder estadoTextoLimpo;
    private StringBuilder estadoLeitura;
    public Estado(Registrador registrador) {
        super(registrador);
        this.registrador = registrador;
        this.acoes = new ArrayList<>();
        estado = this;
    }

    public Estado escolherValor(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        acoes.add(TipoAcao.SELECIONAR);
        return this;
    }

    public Estado focarCampo() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Estado limparValor() {
        this.estadoTextoLimpo = new StringBuilder("");
        acoes.add(TipoAcao.LIMPAR);
        return this;
    }

    public Estado escreverValor(String estadoTexto) {
        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }
    public Estado lerValor(String estadoLeitura) {
        this.estadoLeitura = estadoLeitura == null ? null : new StringBuilder(estadoLeitura);
        acoes.add(TipoAcao.LER);
        return this;
    }

    public Estado rolarAteCampo() {
        acoes.add(TipoAcao.ROLAR);
        return this;
    }

    public Estado clicarCampo() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.CLICAR);
        return this;
    }

    public Estado desfocarCampo() {
        this.estadoDesfoque = Foco.SEM_FOCO;
        acoes.add(TipoAcao.DESFOCAR);
        return this;
    }

    public Estado deslizarBarraProgresso(int estadoProgresso) {
        this.estadoProgresso = new Integer(estadoProgresso);
        acoes.add(TipoAcao.PROGREDIR);
        return this;
    }


    public Estado marcarOpcaoDesmarcavel(Estado.Marcacao marcacao) {
        this.estadoMarcacaoOpcao = marcacao;
        acoes.add(TipoAcao.MARCAR_OPCAO_DESMARCAVEL);
        return this;
    }

    public Estado marcarOpcao() {

        acoes.add(TipoAcao.MARCAR_OPCAO);
        return this;
    }

    public Estado pressionarTeclas(Tecla tecla) {
        this.estadoTecla = tecla;
        acoes.add(TipoAcao.PRESSIONAR);
        return this;
    }

    public Estado selecionarData(Calendar estadoData) {

        return this;
    }

    //-------------------------------------------


    private List<TipoAcao> getAcoes() {
        return acoes;
    }

    public Tecla getEstadoTecla() {
        return estadoTecla;
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

    private Estado.Marcacao getEstadoMarcacaoOpcao() {
        return estadoMarcacaoOpcao;
    }

    private Integer getEstadoProgresso() {
        return estadoProgresso;
    }
    private Foco getEstadoDesfoque() {
        return estadoDesfoque;
    }
    private StringBuilder getEstadoTextoLimpo() {
        return estadoTextoLimpo;
    }
    private StringBuilder getEstadoLeitura() {
        return estadoLeitura;
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
        VERIFICAR, REPRODUZIR, ROLAR, CLICAR, PROGREDIR, MARCAR_OPCAO, MARCAR_OPCAO_DESMARCAVEL, PRESSIONAR, SELECIONAR, DESFOCAR, LIMPAR, LER
    }


    public enum Marcacao {
        MARCADO,
        DESMARCADO
    }
    public enum Tecla {ENTER, VOLTAR}
}
