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
 *
 * @author Admin
 */
public class Broker {
//    private String HOST= "127.0.0.1";
//    private int PUERTO= 5001;
    private static Broker broker;
    private List<IObservador> suscriptores= new ArrayList<>();
    
    private Broker(){
        
    }
    
    public static Broker obtenerInstancia(){
        if(broker==null){
            broker= new Broker();
        }
        return broker;
    }
    
    public void suscribirCliente(IObservador suscriptor){
        this.suscriptores.add(suscriptor);
    }
    
    public void desuscribirCliente(IObservador suscriptor){
        this.suscriptores.remove(suscriptor);
    }
    
    public String canalizarSolicitud(String solicitud){
        IEstrategia estrategia= null;
        Solicitud objetoSolicitud = Deserealizador.getInstancia().deserializarSolicitud(solicitud);
        Operacion tipoOperacion= objetoSolicitud.getOperacion();
        switch (tipoOperacion) {
            case registrar_usuario:{
                estrategia= new SolicitudRegistrarUsuario();
                break;
//                return estrategia.enviarSolicitud(solicitud);
                //return this.enviarSolicitudRegistrarUsuario(solicitud);
            }
            case iniciar_sesion:{
                estrategia= new SolicitudIniciarSesion();
                break;
//                return this.enviarSolicitudIniciarSesion(solicitud);
            }
            case iniciar_sesion_facebook:{
                estrategia= new SolicitudIniciarSesionFacebook();
                break;
//                return this.enviarSolicitudIniciarSesionFacebook(solicitud);
            }
            case registrar_publicacion:{
                estrategia= new SolicitudRegistrarPublicacion();
                break;
//                return this.enviarSolicitudRegistrarPublicacion(solicitud);
            }
            case suscribrir_observador_registrarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion registrar publicación";
            }
            case desuscribrir_observador_registrarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion registrar publicación";
            }
            case consultar_publicaciones:{
                estrategia= new SolicitudConsultarPublicaciones();
                break;
//                return this.enviarSolicitudConsultarPublicaciones(solicitud);
            }
            case suscribir_observador_editarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion editar publicación";
            }
            case desuscribir_observador_editarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion editar publicación";
            }
            case editar_publicacion:{
                estrategia= new SolicitudEditarPublicacion();
                break;
//                return this.enviarSolicitudEditarPublicacion(solicitud);
            }
            case suscribir_observador_eliminarPublicacion:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion eliminar publicación";
            }
            case desuscribir_observador_eliminarPublicacion:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion eliminar publicación";
            }
            case eliminar_publicacion:{
                estrategia= new SolicitudEliminarPublicacion();
                break;
//                return this.enviarSolicitudEliminarPublicacion(solicitud);
            }
            case suscribir_observador_registrarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion registar comentario";
            }
            case desuscribir_observador_registrarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion registrar comentario";
            }
            case registrar_comentario:{
                estrategia= new SolicitudRegistrarComentario();
                break;
//                return this.enviarSolicitudRegistrarComentario(solicitud);
            }
            case suscribir_observador_editarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion editar comentario";
            }
            case desuscribir_observador_editarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion editar comentario";
            }
            case editar_comentario:{
                estrategia= new SolicitudEditarComentario();
                break;
//                return this.enviarSolicitudEditarComentario(solicitud);
            }
            case suscribir_observador_eliminarComentario:{
                estrategia= new SolicitudSuscripcion();
                break;
//                return "Suscripcion eliminar comentario";
            }
            case desuscribir_observador_eliminarComentario:{
                estrategia= new SolicitudDesuscripcion();
                break;
//                return "Desuscripcion eliminar comentario";
            }
            case eliminar_comentario:{
                estrategia= new SolicitudEliminarComentario();
                break;
//                return this.enviarSolicitudEliminarComentario(solicitud);
            }
            case editar_perfil:{
                estrategia= new SolicitudEditarUsuario();
                break;
//                return this.enviarSolicitudEditarUsuario(solicitud);
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
    
//    public String enviarSolicitudRegistrarUsuario(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
//            if(usuario!=null){
////                this.notificar(respuesta);
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudEditarUsuario(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudIniciarSesion(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            System.out.println(respuestaServidor.getRespuesta());
//            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
//            if(usuario!=null){
////                this.notificar(respuesta);
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudIniciarSesionFacebook(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            System.out.println(respuestaServidor.getRespuesta());
//            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
//            if(usuario!=null){
////                this.notificar(respuesta);
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
       
//    public String enviarSolicitudRegistrarPublicacion(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
//            if(publicacion!=null){
//                ObservableRegistrarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudEditarPublicacion(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
//            if(publicacion!=null){
//                ObservableEditarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudEliminarPublicacion(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
//            if(publicacion!=null){
//                ObservableEliminarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudRegistrarComentario(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Comentario comentario= Deserealizador.getInstancia().deserealizarComentario(respuestaServidor.getRespuesta());
//            if(comentario!=null){
//                ObservableRegistrarComentario.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudEditarComentario(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Comentario comentario= Deserealizador.getInstancia().deserealizarComentario(respuestaServidor.getRespuesta());
//            if(comentario!=null){
//                ObservableEditarComentario.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudEliminarComentario(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();
////            socket.close();
////            bufferedReader.close();
////            bufferedWriter.close();
//            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
//            Comentario comentario= Deserealizador.getInstancia().deserealizarComentario(respuestaServidor.getRespuesta());
//            if(comentario!=null){
//                ObservableEliminarComentario.getInstancia().notificar(respuestaServidor.getRespuesta());
//            }
//            
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
    
//    public String enviarSolicitudConsultarPublicaciones(String solicitud){
//        String respuesta= "";
//        Socket socket;
//        BufferedReader bufferedReader;
//        BufferedWriter bufferedWriter;
//        try{
//            socket= new Socket(HOST, PUERTO);
//            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            System.out.println(solicitud);
//            bufferedWriter.write(solicitud);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//           
//            respuesta= bufferedReader.readLine();    
//        } catch(IOException e){
//            e.printStackTrace();
//        } finally{
//            return respuesta;
//        }
//    }
}
