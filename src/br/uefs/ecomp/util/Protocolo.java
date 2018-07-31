package br.uefs.ecomp.util;
// @author Eduardo

import br.uefs.ecomp.model.Servidor;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.LinkedList;

public class Protocolo implements Serializable{
    private int protocolo;
    private String nomeServidor;
    private int idNoticia;
    private boolean fakeNews;
    private boolean veredito;
    private String adm;
    private LinkedList<String> filaAdm;

    public Protocolo(int protocolo, String nomeServidor) {
        this.protocolo = protocolo;
        this.nomeServidor = nomeServidor;
    }

    public int getProtocolo() {
        return protocolo;
    }

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
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

    public LinkedList<String> getFilaAdm() {
        return filaAdm;
    }

    public void setFilaAdm(LinkedList<String> filaAdm) {
        this.filaAdm = filaAdm;
    }
}