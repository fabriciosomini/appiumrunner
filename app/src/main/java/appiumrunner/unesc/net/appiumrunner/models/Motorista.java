package appiumrunner.unesc.net.appiumrunner.models;

import java.io.Serializable;

public class Motorista implements Serializable {
    private String nome;
    private String cpf;
    private String estado;
    private int volumeCarga;
    private TipoCarga tipoCarga;
    private boolean bitrem;
    private boolean ativo;
    private long codigo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getVolumeCarga() {
        return volumeCarga;
    }

    public void setVolumeCarga(int volumeCarga) {
        this.volumeCarga = volumeCarga;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public boolean isBitrem() {
        return bitrem;
    }

    public void setBitrem(boolean bitrem) {
        this.bitrem = bitrem;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public enum TipoCarga {
        ALIMENTICIA, PERIGOSA, VIVA
    }

    @Override
    public String toString() {
        return nome == null ? "" : nome;
    }

}
