/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Comentario;
import dominio.Publicacion;
import dominio.Solicitud;
import dominio.Usuario;

/**
 * Clase encargada de serializar y deserializar objetos.
 * @author Equipo Broker.
 */
public class Deserealizador {
    /**
     * Atributo con una instancia estática de la clase.
     */
    private static Deserealizador deserealizador;
    /**
     * Método constructor de la clase.
     */
    private Deserealizador(){
        
    }
    /**
     * Método utilizado para obtener la instancia estática de la clase, en caso de que no este 
     * inicializada se inicializa.
     * @return Instancia de la clase.
     */
    public static Deserealizador getInstancia(){
        if(deserealizador==null){
            deserealizador= new Deserealizador();
        }
        return deserealizador;
    }
    /**
     * Método utilizado para serializar un objeto del tipo solicitud a formato JSON.
     * @param solicitud Solicitud a serializar.
     * @return String con la solicitud serializada.
     */
    public String serializarSolicitud(Solicitud solicitud){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(solicitud);
            return solicitudSerializada;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Método utilizado para deserializar un String en formato JSON a un objeto del tipo Solicitud.
     * @param solicitud String de la solicitud.
     * @return Objeto solicitud deserializado.
     */
    public Solicitud deserializarSolicitud(String solicitud){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(solicitud, Solicitud.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Método utilizado para deserializar un String en formato JSON a un objeto del tipo Publicación.
     * @param publicacion String de la publicación.
     * @return Objeto publicación deserializado.
     */
    public Publicacion deserealizarPublicacion(String publicacion){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(publicacion, Publicacion.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Método utilizado para deserializar un String en formato JSON a un objeto del tipo Usuario.
     * @param usuario String del usuario.
     * @return Objeto usuario deserializado.
     */
    public Usuario deserealizarUsuario(String usuario){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(usuario, Usuario.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Método utilizado para deserializar un String en formato JSON a un objeto del tipo Comentario.
     * @param comentario String del comentario.
     * @return Objeto comentario deserializado.
     */
    public Comentario deserealizarComentario(String comentario){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(comentario, Comentario.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
