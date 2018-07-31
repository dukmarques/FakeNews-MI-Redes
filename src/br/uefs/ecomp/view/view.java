/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.view;

import br.uefs.ecomp.model.Servidor;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Eduardo
 */
public class view {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        Servidor s = new Servidor();
        
        System.out.println("Servidor: " + s.getIp());
        System.out.println("Ip: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Nome:" + InetAddress.getLocalHost().getHostName());
        
        if (s.getIp().equals(InetAddress.getLocalHost().getHostAddress())) {
            System.out.println(true);
        }
    }
    
}
