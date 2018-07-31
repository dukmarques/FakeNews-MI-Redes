package br.uefs.ecomp.model;
// @author Eduardo

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements Serializable{
    private String ip;
    private int id;

    public Servidor() {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}