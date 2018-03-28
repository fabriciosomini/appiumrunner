package appiumrunner.unesc.net.appiumrunner.helpers;

import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class UtilitarioEstados {

    //Verificar
    public static void verificarEstadoCampoTexto(Registro registro, String identificador, Estado.Foco foco, String texto) {
        if (texto == null) {
            texto = "";
        }

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(new StringBuilder(texto))
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();


    }

    public static void verificarEstadoCampoBarraProgresso(Registro registro, String identificador, Estado.Foco foco, int progresso) {

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoProgresso(progresso)
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }

    public static void verificarEstadoCampoSelecao(Registro registro, String identificador, Estado.Foco foco, String opcao) {
        if (opcao == null) {
            opcao = "";
        }
        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(new StringBuilder(opcao))
                .setEstadoFoco(foco)
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();

    }


    //Reproduzir
    public static void reproduzirEstadoCampoTexto(Registro registro, String identificador, Estado.Foco foco, String texto) {

        if (texto == null) {
            texto = "";
        }

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoTexto(new StringBuilder(texto))
                .setEstadoFoco(foco)
                .reproduzirPassos()
                .build();


    }

    public static void reproduzirEstadoCampoBotao(Registro registro, String identificador) {
        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoFoco(Estado.Foco.FOCADO)
                .reproduzirPassos()
                .build();
    }

    public static void reproduzirEstadoCampoSelecao(Registro registro, String identificador, String opcao) {

        if (opcao == null) {
            opcao = "";
        }

        Estado estado = new Estado(registro);
        estado.setIdentificadorElemento(identificador)
                .setEstadoSelecao(new StringBuilder(opcao))
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
