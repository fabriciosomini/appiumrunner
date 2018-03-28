package appiumrunner.unesc.net.appiumrunner.helpers;

import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class UtilitarioEstados {

    public static void verificarEstadoCampoTexto(Registro registro, String identificador, String texto, boolean foco) {

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(texto)
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();


    }

    public static void verificarEstadoCampoBarraProgresso(Registro registro, String identificador, int progresso) {

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoProgresso(progresso)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }

    public static void verificarEstadoCampoSelecao(Registro registro, String identificador, String opcao) {

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(opcao)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }

    public static void reproduzirEstadoCampoTexto(Registro registro, String identificador, String texto, boolean foco) {

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(texto)
                .setEstadoFoco(foco)
                .reproduzirPassos()
                .build();


    }

    public static void reproduzirEstadoCampoSelecao(Registro registro, String identificador, String opcao) {
        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(opcao)
                .reproduzirPassos()
                .build();

    }

    public static void reproduzirEstadoCampoBarraProgresso(Registro registro, String identificador, int progresso) {
        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoProgresso(progresso)
                .reproduzirPassos()
                .build();
    }
}
