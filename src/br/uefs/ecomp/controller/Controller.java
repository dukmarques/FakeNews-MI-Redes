package br.uefs.ecomp.controller;

 //@author Eduardo
import br.uefs.ecomp.model.Noticia;
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
    //--------Listas-------------------//
    LinkedList<Noticia> noticias; //lista de noticias;
    LinkedList<Noticia> fakeNews = new LinkedList<>();        //Lista de noticias atribuidas como fake news;
    LinkedList<Noticia> suspeitas = new LinkedList<>();      //Lista de noticias suspeitas de fake news;
    LinkedList<String> candidatos = new LinkedList<>();   //Lista de ordem para servidores se tornarem admins;
    MulticastSocket socket; //Multicast socket para comunicação em grupo;
    
    String nomeLocal;
    String adm;
    
    boolean fimMandato = true;
    int votoNFN = 0, votoFK = 0; 

    public Controller() {
        try {
            this.nomeLocal = InetAddress.getLocalHost().getHostName();
            System.out.println("Nome Servidor: " + nomeLocal);
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
// ----------------------- NOTICIAs -------------------------------------------//
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
    
    public LinkedList<Noticia> getFakeNews(){
        return this.fakeNews;
    }
    
    public void avaliacao(int nota, int idNoticia) throws InterruptedException{
        Noticia n = getNoticia(idNoticia); //Pega a instancia da noticia na lista;
        n.setNota(n.getNota()+ nota); //Soma a nota dada com as notas já recebidas pela noticia;
        n.setQtdNotas(n.getQtdNotas()+1); //Incrementa a quantidade de classificações que já foram realizadas;
        
        //Verifica se a noticia atingiu o estado de suspeita de fake news;
        if(n.getQtdNotas() >= 10 && (n.getNota()/n.getQtdNotas()) < 3){
            this.suspeitas.add(n);
            
            if (this.adm.equals(this.nomeLocal)) {
                this.analiseFN();
            }else{
                Protocolo p = new Protocolo(2, nomeLocal);
                comunicaSala(p);
                this.candidatos.add(this.adm);
            }
        }
    }
    
    public void setAvaliacao(int idNoticia, int nota, int qtdNota) throws InterruptedException{
        Noticia n = getNoticia(idNoticia); //Recupera a noticia da lista;
        n.setNota(nota); //Atualiza o valor da nota recebida;
        n.setQtdNotas(qtdNota); //Atualiza o valor da quantidade de notas recebidas;
        
        //Verifica se a noticia atingiu o estado de suspeita de fake news;
        if(n.getQtdNotas() >= 10 && (n.getNota()/n.getQtdNotas()) < 3){
            this.suspeitas.add(n);
            
            if (this.adm.equals(this.nomeLocal)) {
                this.analiseFN();
            }else{
                Protocolo p = new Protocolo(2, nomeLocal);
                comunicaSala(p);
                this.candidatos.add(this.adm);
            }
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
    
    public boolean getSuspeita(int id){
        Iterator itr = suspeitas.iterator();
        while (itr.hasNext()) {
            Noticia n = (Noticia) itr.next();
            if (n.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public void removeSuspeita(int idNoticia){
        Iterator itr = this.suspeitas.iterator();
        while (itr.hasNext()) {
            Noticia n = (Noticia) itr.next();
            if (n.getId() == idNoticia) {
                this.suspeitas.remove(n);
            }
        }
    }
    public boolean getFake(int idNoticia){
        Iterator itr = fakeNews.iterator();
        while (itr.hasNext()) {
            Noticia n = (Noticia) itr.next();
            if (n.getId() == idNoticia) {
                return true;
            }
        }
        return false;
    }
// ------------------------------Comunicação----------------------------------//
    public void startMulticast(){
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
                                    } catch (InterruptedException ex) {
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
    
    public void trataProtocolo(Protocolo p) throws UnknownHostException, InterruptedException{
        if (!p.getNomeServidor().equals(this.nomeLocal)) {
            System.out.println("Servidor " + p.getNomeServidor() + " requisita: " + p.getProtocolo());
            
            //Se o protocolo for 0, significa que um novo servidor entrou no multicast, então todos servidores atualizam
            //sua lista e o adm da rodada responde o multicast para que o novo servidor possa atualizar sua lista igualmente;
            if (p.getProtocolo() == 0) {
                if (this.adm == null) { //Verifica se o adm é nulo, caso o servidor seja o primeiro a iniciar o multicast;
                    adm = this.nomeLocal;
                    System.out.println("Adm: " + this.adm);
                }
                if (this.adm.equals(this.nomeLocal)) {
                    Protocolo resposta = new Protocolo(1, this.nomeLocal);
                    resposta.setAdm(adm);
                    resposta.setFilaAdm(candidatos);
                    comunicaSala(resposta);
                }
            }
            
            //Se o protocolo for 1, é a resposta do adm da rodada para o servidor que acabou de se conectar;
            if (p.getProtocolo() == 1 && this.adm == null) {
                this.adm = p.getAdm();
                this.candidatos = p.getFilaAdm();
                System.out.println("Adm da sala: " + this.adm);
            }
            
            //Se o protocolo for 2, algum servidor está solicitando ser adm;
            if (p.getProtocolo() == 2) {
                this.candidatos.add(p.getNomeServidor()); //Insere o servidor na lista de candidatos;
                
                //Verifica se o servidor local é o ADM e se já concluiu seu mandato;
                if (this.adm.equals(this.nomeLocal) && this.fimMandato == true) {
                    this.candidatos.removeFirst();
                    Protocolo concluirMandato = new Protocolo(3, this.nomeLocal);
                    comunicaSala(concluirMandato);
                }
            }
            
            //Se o protocolo for 3, o adm da rodada atual está passando o cargo para o proximo servidor da lista;
            if (p.getProtocolo() == 3) {
                if (!this.candidatos.isEmpty()) {
                    this.candidatos.removeFirst(); //Remove o primeiro da lista que é o Adm;
                    this.adm = this.candidatos.getFirst(); //Torna o novo primeiro da lista como adm;
                    
                    //Verifica se o localhost é o admin atual e se possui alguma suspeita na sua lista;
                    if (this.adm.equals(this.nomeLocal) && !this.suspeitas.isEmpty()) {
                        this.fimMandato = false;
                        this.analiseFN();
                    }else{
                        Protocolo passaMandato = new Protocolo(3, this.nomeLocal);
                        comunicaSala(passaMandato);
                    }
                }
            }
            
            //Se o protocolo for 4, o adm está enviando uma suspeita;
            if (p.getProtocolo() == 4) {
                Noticia suspeita = getNoticia(p.getIdNoticia());
                float media = (float) suspeita.getNota()/suspeita.getQtdNotas();
                
                Protocolo respSuspeita = new Protocolo(5, this.nomeLocal);
                if (media > 3) {
                    respSuspeita.setFakeNews(false);
                }else{
                    respSuspeita.setFakeNews(true);
                }
                comunicaSala(respSuspeita);
                System.out.println("Id Noticia suspeita: " + suspeita.getId() + "Analise: " + respSuspeita.isFakeNews());
            }
            
            //Se o protocolo for 5, são as respostas dos servidores sobre o a suspeita;
            if (p.getProtocolo() == 5 && adm.equals(this.nomeLocal)) {
                if (p.isFakeNews()) {
                    this.votoFK++;
                }
                if (!p.isFakeNews()) {
                    this.votoNFN++;
                }
                System.out.println("Resposta do Servidor " + p.getNomeServidor() + " sobre a notica: " + p.isFakeNews());
            }
            
            //Se o protocolo for 6, é o veredito do adm sobre a suspeita analisada;
            if (p.getProtocolo() == 6) {
                //Verifica o veredito da noticia passada pelo adm da rodada;
                System.out.println("Veredito da notica de id "+p.getIdNoticia()+": "+p.isVeredito());
                if (p.isVeredito()) {
                    Noticia n = getNoticia(p.getIdNoticia());
                    this.fakeNews.add(n); //Se for fake news adiciona a noticia a lista de fakenews;
                }
                //Verifica se a noticia está na lista de suspeitas, caso esteja é removida;
                if (getSuspeita(p.getIdNoticia())) {
                    this.removeSuspeita(p.getIdNoticia());
                }
            }
        }
    }
    
    public void avisaSala(){
        Protocolo p = new Protocolo(0, this.nomeLocal);
        comunicaSala(p);
    }
    
    private void analiseFN() throws InterruptedException{
        //Verifica se o localhost é adm, se for inicia a analise da noticia;
        Noticia suspeita = this.suspeitas.getFirst();
        System.out.println("Iniciando análise de Fake news da Noticia de id: " + suspeita.getId());

        Protocolo p = new Protocolo(4, this.nomeLocal);
        p.setIdNoticia(suspeita.getId());
        comunicaSala(p); //Envia para o multicast o id da suspeita;

        Thread.sleep(5000); //tempo de espera das respostas dos demais servidores;

        boolean veredito;
        if (votoNFN >= ((67*5)/100) ) {
            p.setVeredito(false);
        }else{
            p.setVeredito(true);
            this.fakeNews.add(suspeita);//Adciona a noticia na lista de fake news;
        }
        p.setProtocolo(6);
        comunicaSala(p); //Envia o veredito para os demais servidores;
        this.suspeitas.removeFirst(); //Remove a noticia da lista de suspeitas;

        //Remove-se da lista de candidataos a adm;
        if (!this.candidatos.isEmpty()) {
            this.candidatos.removeFirst();
            this.adm = candidatos.getFirst();
            this.fimMandato = true;
            //Comunica aos demais a conclusão do seu mandato;
            p.setProtocolo(3);
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