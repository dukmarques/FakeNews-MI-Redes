package br.uefs.ecomp.controller;

 //@author Eduardo
import br.uefs.ecomp.model.Noticia;
import br.uefs.ecomp.util.ManipularArquivo;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    LinkedList<Noticia> noticias;
    
    public LinkedList<Noticia> getNoticias(){
        try {
            noticias = ManipularArquivo.getNoticias();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noticias;
    }
}