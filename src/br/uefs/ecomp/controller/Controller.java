package br.uefs.ecomp.controller;

 //@author Eduardo
import br.uefs.ecomp.model.Noticia;
import br.uefs.ecomp.model.Servidor;
import br.uefs.ecomp.util.ManipularArquivo;
import br.uefs.ecomp.util.Protocolo;
import br.uefs.ecomp.view.TelaPrincipal;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    LinkedList<Noticia> noticias; //lista de noticias;
    LinkedList<Servidor> servidores = new LinkedList<>();
    MulticastSocket socket; //Multicast socket para comunicação em grupo;
    boolean conectado = false;
    private int id; //Para testes;
    
    public LinkedList<Noticia> getNoticias(){
        try {
            if (noticias == null) {
                noticias = ManipularArquivo.getNoticias();
            }else{
                LinkedList<Noticia> lista = ManipularArquivo.getNoticias();
                
                Iterator itr = lista.iterator();
                while (itr.hasNext()) {
                    Noticia n = (Noticia) itr.next();
                    if (!contem(n.getId())) {
                        this.noticias.add(n);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noticias;
    }
    
    public void avaliacao(int nota, int idNoticia){
        Noticia n = getNoticia(idNoticia); //Pega a instancia da noticia na lista;
        n.setNota(n.getNota()+ nota); //Soma a nota dada com as notas já recebidas pela noticia;
        n.setQtdNotas(n.getQtdNotas()+1); //Incrementa a quantidade de classificações que já foram realizadas;
        
        //Verifica se a noticia atingiu o estado de suspeita de fake news;
        if(false){
            
        }
    }
    
    public void setAvaliacao(int idNoticia, int nota, int qtdNota){
        Noticia n = getNoticia(idNoticia); //Recupera a noticia da lista;
        n.setNota(nota); //Atualiza o valor da nota recebida;
        n.setQtdNotas(qtdNota); //Atualiza o valor da quantidade de notas recebidas;
        
        //Verifica se a noticia atingiu o estado de suspeita de fake news;
        if (false) {
            
        }
    }
    
    public Noticia getNoticia(int id){
        Iterator itr = noticias.iterator();
        while (itr.hasNext()) {
            Noticia n = (Noticia) itr.next();
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }
    
    //Verifica se a noticia do id informado já está presente na lista de noticias;
    private boolean contem(int id){
        Iterator itr = noticias.iterator();
        while (itr.hasNext()) {
            Noticia n = (Noticia) itr.next();
            if (n.getId() == id) {
                return true;
            }
        }
        return false;
    }
    // --------------------------Comunicação----------------------------------//
    private void startMulticast(){
        try {
            socket = new MulticastSocket(1223);
            InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.9");
            socket.joinGroup(enderecoMulticast);
            
            byte[] recebe = new byte[1024];
            DatagramPacket datagrama = new DatagramPacket(recebe, recebe.length);
            
            new Thread(){
                @Override
                public void run(){
                    while (true) {
                        try {
                            socket.receive(datagrama);
                            
                            byte[] recebido = datagrama.getData();
                            ByteArrayInputStream bais = new ByteArrayInputStream(recebido);
                            ObjectInputStream ois = new ObjectInputStream(bais);
                            Protocolo p = (Protocolo) ois.readObject();
                            
                            new Thread(){
                                @Override
                                public void run(){
                                    try {
                                        trataProtocolo(p);
                                    } catch (UnknownHostException ex) {
                                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }.start();
                            
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void trataProtocolo(Protocolo p) throws UnknownHostException{
        if (!p.getServidor().getIp().equals(InetAddress.getLocalHost().getAddress())
                || p.getServidor().getId() != this.id) {
            
            //Se o protocolo for 0, significa que um novo servidor entrou no multicast, então todos servidores atualizam
            //sua lista e respondem o multicast para que o novo servidor possa atualizar sua lista igualmente;
            if (p.getProtocolo() == 0) {
                servidores.add(p.getServidor());
                Protocolo resposta = new Protocolo(1);
                resposta.getServidor().setId(id);
                comunicaSala(resposta);
            }
            
            //Se o protocolo for 2, é a resposta dos servidores para o servidor que acabou de se conectar;
            //Verifica se ja está conectado para entrar nessa opção;
            if (p.getProtocolo() == 1) {
                servidores.add(p.getServidor());
            }
            
            
        }
    }
    
    private void comunicaSala(Protocolo p){
        try{
            InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.9");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(p);
            oos.close();
            
            byte[] dados = baos.toByteArray();
            DatagramPacket datagrama = new DatagramPacket(dados, dados.length, enderecoMulticast, 1223);
            socket.send(datagrama);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}