package unesc.com.unesctcc3.modelos;

import java.util.ArrayList;
import java.util.List;

import unesc.com.unesctcc3.motor.AlgoritmoRegistro;


/**
 * Created by fabri on 18/03/2018.
 */
public class Atividade extends AcaoFinal {
    private final AlgoritmoRegistro algoritmoRegistro;
    private List<TipoAcao> acoes;
    private Foco estadoFoco;
    private String identificadorElemento;
    private StringBuilder estadoTexto;
    private StringBuilder estadoSelecao;
    private Atividade.Marcacao estadoMarcacaoOpcao;
    private int estadoProgresso;
    private Tecla estadoTecla;
    private Foco estadoDesfoque;
    private StringBuilder estadoTextoLimpo;
    private StringBuilder estadoLeitura;
    private Visibilidade estadoVisibilidade;

    public Atividade(AlgoritmoRegistro algoritmoRegistro) {
        super(algoritmoRegistro);
        this.algoritmoRegistro = algoritmoRegistro;
        this.acoes = new ArrayList<>();
        atividade = this;
    }

    public Atividade escolherValor(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        acoes.add(TipoAcao.SELECIONAR);
        return this;
    }

    public Atividade focarCampo() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Atividade limparValor() {
        this.estadoTextoLimpo = new StringBuilder("");
        acoes.add(TipoAcao.LIMPAR);
        return this;
    }

    public Atividade escreverValor(String estadoTexto) {
        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }

    public Atividade lerValor(String estadoLeitura) {
        this.estadoLeitura = estadoLeitura == null ? null : new StringBuilder(estadoLeitura);
        acoes.add(TipoAcao.LER);
        return this;
    }

    public Atividade rolarAteCampo() {
        acoes.add(TipoAcao.ROLAR);
        return this;
    }

    public Atividade clicarCampo() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.CLICAR);
        return this;
    }

    public Atividade desfocarCampo() {
        this.estadoDesfoque = Foco.SEM_FOCO;
        acoes.add(TipoAcao.DESFOCAR);
        return this;
    }

    public Atividade deslizarBarraProgresso(int estadoProgresso) {
        this.estadoProgresso = new Integer(estadoProgresso);
        acoes.add(TipoAcao.PROGREDIR);
        return this;
    }

    public Atividade marcarOpcaoDesmarcavel(Atividade.Marcacao marcacao) {
        this.estadoMarcacaoOpcao = marcacao;
        acoes.add(TipoAcao.MARCAR_OPCAO_DESMARCAVEL);
        return this;
    }

    public Atividade marcarOpcao() {
        acoes.add(TipoAcao.MARCAR_OPCAO);
        return this;
    }

    public Atividade pressionarTeclas(Tecla tecla) {
        this.estadoTecla = tecla;
        acoes.add(TipoAcao.PRESSIONAR);
        return this;
    }

    public Atividade visibilidade(Visibilidade estadoVisibilidade) {
        this.estadoVisibilidade = estadoVisibilidade;
        acoes.add(TipoAcao.VISUALIZAR);
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

    private Atividade setIdentificadorElemento(String identificador) {
        this.identificadorElemento = identificador;
        return this;
    }

    private Atividade.Marcacao getEstadoMarcacaoOpcao() {
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

    private Visibilidade getEstadoVisibilidade() {
        return estadoVisibilidade;
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
        VERIFICAR,
        REPRODUZIR,
        ROLAR,
        CLICAR,
        PROGREDIR,
        MARCAR_OPCAO,
        MARCAR_OPCAO_DESMARCAVEL,
        PRESSIONAR,
        SELECIONAR,
        DESFOCAR,
        LIMPAR,
        VISUALIZAR, LER
    }

    public enum Marcacao {
        MARCADO,
        DESMARCADO
    }

    public enum Tecla {ENTER, VOLTAR}

    public enum Visibilidade {
        VISIVEL,
        OCULTO
    }
}
