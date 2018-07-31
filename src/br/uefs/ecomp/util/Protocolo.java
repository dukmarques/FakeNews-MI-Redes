package br.uefs.ecomp.util;
// @author Eduardo

import br.uefs.ecomp.model.Servidor;
import java.io.Serializable;

public class Protocolo implements Serializable{
    private int protocolo;
    private Servidor servidor;
    private int idNoticia;
    private float notaNoticia;
    private int rodada;

    public Protocolo(int protocolo) {
        this.protocolo = protocolo;
        this.servidor = new Servidor();
    }

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public float getNotaNoticia() {
        return notaNoticia;
    }

    public void setNotaNoticia(float notaNoticia) {
        this.notaNoticia = notaNoticia;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }
}