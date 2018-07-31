package br.uefs.ecomp.util;

import br.uefs.ecomp.model.Noticia;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ManipularArquivo {
    
    //Classe utilizada para ler o arquivo de texto contendo os produtos a venda.
    public static LinkedList<Noticia> getNoticias() throws IOException{
        BufferedReader read; 
        
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream("noticias.txt"), "ISO-8859-1"));
            String l;
            LinkedList<Noticia> noticias = new LinkedList<>();
            
            while ((l = read.readLine()) != null) {
                Noticia p = trataString(l);
                noticias.add(p);
            }
            read.close();
            
            return noticias;
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo txt não encontrado!");
            return null;
        }
    }
    
    private static Noticia trataString(String linha){
        String[] texto = linha.split(";");
        Noticia n = new Noticia(Integer.parseInt(texto[0]), texto[1], texto[2],0, 0);
        return n;
    }
}