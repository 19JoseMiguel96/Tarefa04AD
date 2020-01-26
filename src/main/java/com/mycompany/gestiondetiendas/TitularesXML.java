/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
/**
 * @author Jose Miguel
 */
public class TitularesXML extends DefaultHandler {
    private int nivel = 0;
    //Aqui se guardarán todos los datos de los titulares.
    private ArrayList<Titular> titulares;

    //Variable auxiliar para ir guardando los datos del titular del XML.
    private Titular tituloAux;

    //Otra variable para guardar el testo que hay entre las etiquetas.
    private String cadenaTexto;

    public TitularesXML(){
        super();
    }        
    
    //Este metodo se ejecutará al comenzar a leer una etiqueta.    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        //Se atopamos a etiqueta channel creamos un novo arrayList
        if(localName.equalsIgnoreCase("channel")){
            this.titulares = new ArrayList();
        }
        //Se atopamos a etiqueta title, creamos o obxecto auxiliar de Titular onde gardaremos todolos datos
        else if(localName.equalsIgnoreCase("item")){
            this.tituloAux = new Titular();
            nivel = 1;
        }
    }

    //Método que se ejecuta cuando se acaba de leer una etiqueta.
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Terminamos de leer la etiqueta "title" por lo que podemos comenzar a leer el texto entre las etiquetas.
        if(localName.equalsIgnoreCase("title")){
            if(nivel == 1){
                this.tituloAux.setTitular(cadenaTexto);
                nivel = 0;
            }                        
        }        
        //Terminamos de leer la etiqueta "item" por lo que podemos comenzar a leer el texto entre las etiquetas.
        else if(localName.equalsIgnoreCase("item")){
            this.titulares.add(this.tituloAux);
        }
    }

    /*
    Este metodo se ejecuta cuando se lee una cadena de texto.
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Guardamos todo el texto entre caracteres en el String de texto auxiliar.
        this.cadenaTexto = new String(ch,start,length);
    }

    public ArrayList<Titular> getTitulares() {
        return titulares;
    }
}
