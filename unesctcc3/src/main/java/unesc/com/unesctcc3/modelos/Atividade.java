package unesc.com.unesctcc3.modelos;

import java.util.ArrayList;
import java.util.List;

import unesc.com.unesctcc3.motor.RegistroAtividades;


/**
 * Created by fabri on 18/03/2018.
 */
public class Atividade extends Encerramento {

    private final RegistroAtividades registroAtividades;
    private List<TipoAtividade> tipoAtividades;
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
    private StringBuilder estadoSelecaoLista;

    public Atividade(RegistroAtividades registroAtividades) {
        super(registroAtividades);
        this.registroAtividades = registroAtividades;
        this.tipoAtividades = new ArrayList<>();
        atividade = this;
    }

    public Atividade selecionarOpcao(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        tipoAtividades.add(TipoAtividade.SELECIONAR_COMBO);
        return this;
    }

    public Atividade selecionarItemListagem(String estadoSelecaoLista) {
        this.estadoSelecaoLista = estadoSelecaoLista == null ? null : new StringBuilder(estadoSelecaoLista);
        tipoAtividades.add(TipoAtividade.SELECIONAR_LISTA);
        return this;
    }

    public Atividade focarCampo() {
        this.estadoFoco = Foco.FOCADO;
        tipoAtividades.add(TipoAtividade.FOCAR);
        return this;
    }

    public Atividade limparValor() {
        this.estadoTextoLimpo = new StringBuilder("");
        tipoAtividades.add(TipoAtividade.LIMPAR);
        return this;
    }

    public Atividade escreverValor(String estadoTexto) {
        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        tipoAtividades.add(TipoAtividade.ESCREVER);
        return this;
    }

    public Atividade lerValor(String estadoLeitura) {
        this.estadoLeitura = estadoLeitura == null ? null : new StringBuilder(estadoLeitura);
        tipoAtividades.add(TipoAtividade.LER);
        return this;
    }

    public Atividade rolarAteCampo() {
        tipoAtividades.add(TipoAtividade.ROLAR);
        return this;
    }

    public Atividade clicarBotao() {
        this.estadoFoco = Foco.FOCADO;
        tipoAtividades.add(TipoAtividade.CLICAR);
        return this;
    }

    public Atividade desfocarCampo() {
        this.estadoDesfoque = Foco.SEM_FOCO;
        tipoAtividades.add(TipoAtividade.DESFOCAR);
        return this;
    }

    public Atividade deslizarBarraProgresso(int estadoProgresso) {
        this.estadoProgresso = new Integer(estadoProgresso);
        tipoAtividades.add(TipoAtividade.PROGREDIR);
        return this;
    }

    public Atividade marcarOpcaoDesmarcavel(Atividade.Marcacao marcacao) {
        this.estadoMarcacaoOpcao = marcacao;
        tipoAtividades.add(TipoAtividade.MARCAR_OPCAO_DESMARCAVEL);
        return this;
    }

    public Atividade marcarOpcao() {
        tipoAtividades.add(TipoAtividade.MARCAR_OPCAO);
        return this;
    }

    public Atividade pressionarTeclas(Tecla tecla) {
        this.estadoTecla = tecla;
        tipoAtividades.add(TipoAtividade.PRESSIONAR);
        return this;
    }

    public Atividade visibilidade(Visibilidade estadoVisibilidade) {
        this.estadoVisibilidade = estadoVisibilidade;
        tipoAtividades.add(TipoAtividade.VISUALIZAR);
        return this;
    }

    //-------------------------------------------
    private List<TipoAtividade> getTipoAtividades() {
        return tipoAtividades;
    }

    public Tecla getEstadoTecla() {
        return estadoTecla;
    }

    private StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }


    private StringBuilder getEstadoSelecaoLista() {
        return estadoSelecaoLista;
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

    public enum TipoAtividade {
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
        SELECIONAR_COMBO,
        DESFOCAR,
        LIMPAR,
        VISUALIZAR, SELECIONAR_LISTA, ACESSO_TELA, LER
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
