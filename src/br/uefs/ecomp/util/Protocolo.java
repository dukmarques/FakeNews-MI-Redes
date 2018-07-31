package br.uefs.ecomp.util;
// @author Eduardo

import br.uefs.ecomp.model.Servidor;
import java.io.Serializable;

public class Protocolo implements Serializable{
    private int protocolo;
    private Servidor servidor;
    private int idNoticia;
    private boolean fakeNews;
    private boolean veredito;
    private Servidor adm;

    public Protocolo(int protocolo) {
        this.protocolo = protocolo;
        this.servidor = new Servidor();
    }

    public int getProtocolo() {
        return protocolo;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public Servidor getAdm() {
        return adm;
    }

    public void setAdm(Servidor adm) {
        this.adm = adm;
    }

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public boolean isVeredito() {
        return veredito;
    }

    public void setVeredito(boolean veredito) {
        this.veredito = veredito;
    }

    public boolean isFakeNews() {
        return fakeNews;
    }

    public void setFakeNews(boolean fakeNews) {
        this.fakeNews = fakeNews;
    }
}