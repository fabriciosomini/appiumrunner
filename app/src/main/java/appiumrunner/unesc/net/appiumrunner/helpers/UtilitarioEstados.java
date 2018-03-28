package appiumrunner.unesc.net.appiumrunner.helpers;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class UtilitarioEstados {

    //Verificar
    public static void verificarEstadoCampoTexto(Registrador registrador, String identificador, Estado.Foco foco, String texto) {
        if (texto == null) {
            texto = "";
        }

        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(new StringBuilder(texto))
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();


    }

    public static void verificarEstadoCampoBarraProgresso(Registrador registrador, String identificador, Estado.Foco foco, int progresso) {

        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoProgresso(progresso)
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }

    public static void verificarEstadoCampoSelecao(Registrador registrador, String identificador, Estado.Foco foco, String opcao) {
        if (opcao == null) {
            opcao = "";
        }
        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(new StringBuilder(opcao))
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }

    public static void verificarEstadoCampoToggle(Registrador registrador, String motorista_ativo, Estado.Foco foco, boolean ativo) {

    }

    public static void verificarEstadoCampoSelecaoUnica(Registrador registrador, String identificador, Estado.Foco foco, int opcao) {

    }


    //Reproduzir

    public static void reproduzirEstadoCampoTexto(Registrador registrador, String identificador, Estado.Foco foco, String texto) {

        if (texto == null) {
            texto = "";
        }

        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(new StringBuilder(texto))
                .setEstadoFoco(foco)
                .reproduzirPassos()
                .build();


    }

    public static void reproduzirEstadoCampoBotao(Registrador registrador, String identificador) {
        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoFoco(Estado.Foco.FOCADO)
                .reproduzirPassos()
                .build();
    }

    public static void reproduzirEstadoCampoSelecao(Registrador registrador, String identificador, String opcao) {

        if (opcao == null) {
            opcao = "";
        }

        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(new StringBuilder(opcao))
                .reproduzirPassos()
                .build();

    }

    public static void reproduzirEstadoCampoBarraProgresso(Registrador registrador, String identificador, int progresso) {
        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(identificador)
                .setEstadoProgresso(progresso)
                .reproduzirPassos()
                .build();
    }

    public static void reproduzirEstadoCampoToggle(Registrador registrador, String identificador, boolean ativo) {

    }

    public static void reproduzirEstadoCampoSelecaoUnica(Registrador registrador, String identificador, int opcao) {

    }
}
