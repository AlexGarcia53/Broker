/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker;

import dominio.Solicitud;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Comentario;
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Usuario;
import estrategias.*;
import interfaces.IEstrategia;
import interfaces.IObservador;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import observables.ObservableEditarComentario;
import observables.ObservableEditarPublicacion;
import observables.ObservableEliminarComentario;
import observables.ObservableEliminarPublicacion;
import observables.ObservableRegistrarComentario;
import observables.ObservableRegistrarPublicacion;

/**
 * Clase encargada de canalizar las solicitudes que llegan.
 * @author Equipo Broker.
 */
public class Broker {
    /**
     * Instancia estática de la clase.
     */
    private static Broker broker;
    /**
     * Lista con los clientes conectados actualmente.
     */
    private List<IObservador> suscriptores= new ArrayList<>();
    /**
     * Constructor de la clase.
     */
    private Broker(){
        
    }
    /**
     * Método utilizado para acceder a la única instancia de la clase, en caso de que esta no 
     * este inicializada la inicializa.
     * @return Instancia de la clase.
     */
    public static Broker obtenerInstancia(){
        if(broker==null){
            broker= new Broker();
        }
        return broker;
    }
    /**
     * Método utilizado para añadir a la lista de clientes conectados un cliente.
     * @param suscriptor Cliente a suscribir.
     */
    public void suscribirCliente(IObservador suscriptor){
        this.suscriptores.add(suscriptor);
    }
    /**
     * Método utilizado para eliminar de la lista de clientes conectados a un cliente.
     * @param suscriptor Cliente a desuscribir.
     */
    public void desuscribirCliente(IObservador suscriptor){
        this.suscriptores.remove(suscriptor);
    }
    /**
     * Método utilizado para canalizar la solicitud a otro método que pueda atenderla, dependiendo de la 
     * operación que se busca realizar.
     * @param solicitud Solicitud a realizar.
     * @return Respuesta de la solicitud.
     */
    public String canalizarSolicitud(String solicitud){
        IEstrategia estrategia= null;
        Solicitud objetoSolicitud = Deserealizador.getInstancia().deserializarSolicitud(solicitud);
        Operacion tipoOperacion= objetoSolicitud.getOperacion();
        switch (tipoOperacion) {
            case registrar_usuario:{
                estrategia= new SolicitudRegistrarUsuario();
                break;
            }
            case iniciar_sesion:{
                estrategia= new SolicitudIniciarSesion();
                break;
            }
            case iniciar_sesion_facebook:{
                estrategia= new SolicitudIniciarSesionFacebook();
                break;
            }
            case registrar_publicacion:{
                estrategia= new SolicitudRegistrarPublicacion();
                break;
            }
            case suscribrir_observador_registrarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribrir_observador_registrarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case consultar_publicaciones:{
                estrategia= new SolicitudConsultarPublicaciones();
                break;
            }
            case suscribir_observador_editarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribir_observador_editarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case editar_publicacion:{
                estrategia= new SolicitudEditarPublicacion();
                break;
            }
            case suscribir_observador_eliminarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribir_observador_eliminarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case eliminar_publicacion:{
                estrategia= new SolicitudEliminarPublicacion();
                break;
            }
            case suscribir_observador_registrarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribir_observador_registrarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case registrar_comentario:{
                estrategia= new SolicitudRegistrarComentario();
                break;
            }
            case suscribir_observador_editarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribir_observador_editarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case editar_comentario:{
                estrategia= new SolicitudEditarComentario();
                break;
            }
            case suscribir_observador_eliminarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
            }
            case desuscribir_observador_eliminarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
            }
            case eliminar_comentario:{
                estrategia= new SolicitudEliminarComentario();
                break;
            }
            case editar_perfil:{
                estrategia= new SolicitudEditarUsuario();
                break;
            }
            case consultar_usuarioNombre:{
                estrategia= new SolicitudConsultarUsuarioNombre();
                break;
            }
            case registrar_mensaje:{
                estrategia= new SolicitudEnviarMensaje();
                break;
            }
            case consultar_publicacionesHashtag:{
                estrategia= new SolicitudConsultarPublicacionesHashtag();
                break;
            }
        }
        return estrategia.enviarSolicitud(solicitud);
    }
    
}
