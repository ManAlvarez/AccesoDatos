/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia.funcionalidades;

import fp.dam.franquicia.objetos.Titular;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author manuel
 */
public class TitularesXML extends DefaultHandler{
    
    private int nivel = 0;
    //Aqui guardamos los datos de los titulares.
    private ArrayList<Titular> titulares;

    //Atributo auxiliar para ir guardando los titulos del XML.
    private Titular tituloAux;

    //Atributo auxiliar para el texto que hay entre las etiquetas.
    private String cadenaTexto;

    public TitularesXML(){
        super();
    }
        
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        //Si encontramos la etiqueta channel creamos un ArrayList.
        if(localName.equalsIgnoreCase("channel")){
            this.titulares = new ArrayList();
        }
        
        //Si encontramos la etiqueta item, guardamos el objeto auxiliar del Titular donde guardamos todos los datos.
        else if(localName.equalsIgnoreCase("item")){
            this.tituloAux = new Titular();
            nivel = 1;
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
               
        //Si encontramos una etiqueta de cierre que contenga title ,guardamos el texto que hay entre las etiquetas.
        if(localName.equalsIgnoreCase("title")){
            if(nivel == 1){
                this.tituloAux.setTitular(cadenaTexto);
                nivel = 0;
            }                        
        }        
        //Si encontramos una etiqueta de cierre que contenga item, guardamos el objeto tituloAux en el ArrayList titulares.
        else if(localName.equalsIgnoreCase("item")){
            this.titulares.add(this.tituloAux);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Guardamos todo el texto que hay entre las etiquetas en cadenaTexto.
        this.cadenaTexto = new String(ch,start,length);
    }

    public ArrayList<Titular> getTitulares() {
        return titulares;
    }
}
