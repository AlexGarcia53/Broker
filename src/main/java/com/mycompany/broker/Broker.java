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
import interfaces.Observador;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import observables.ObservableEditarComentario;
import observables.ObservableEditarPublicacion;
import observables.ObservableEliminarPublicacion;
import observables.ObservableRegistrarComentario;
import observables.ObservableRegistrarPublicacion;

/**
 *
 * @author Admin
 */
public class Broker {
    private String HOST= "192.168.0.4";
    private int PUERTO= 5001;
    private static Broker broker;
    private ArrayList<Observador> suscriptores= new ArrayList<>();
    
    private Broker(){
        
    }
    
    public static Broker obtenerInstancia(){
        if(broker==null){
            broker= new Broker();
        }
        return broker;
    }
    
    public void suscribirCliente(Observador suscriptor){
        this.suscriptores.add(suscriptor);
    }
    
    public void desuscribirCliente(Observador suscriptor){
        this.suscriptores.remove(suscriptor);
    }
    
    public String canalizarSolicitud(String solicitud){
        Solicitud objetoSolicitud = Deserealizador.getInstancia().deserializarSolicitud(solicitud);
        Operacion tipoOperacion= objetoSolicitud.getOperacion();
        switch (tipoOperacion) {
            case registrar_usuario:
                return this.enviarSolicitudRegistrarUsuario(solicitud);
            case iniciar_sesion:
                return this.enviarSolicitudIniciarSesion(solicitud);
            case iniciar_sesion_facebook:
                return this.enviarSolicitudIniciarSesionFacebook(solicitud);
            case registrar_publicacion:
                return this.enviarSolicitudRegistrarPublicacion(solicitud);
            case suscribrir_observador_registrarPublicacion:
                return "Suscripcion registrar publicación";
            case desuscribrir_observador_registrarPublicacion:
                return "Desuscripcion registrar publicación";
            case consultar_publicaciones:
                return this.enviarSolicitudConsultarPublicaciones(solicitud);
            case suscribir_observador_editarPublicacion:
                return "Suscripcion editar publicación";
            case desuscribir_observador_editarPublicacion:
                return "Desuscripcion editar publicación";
            case editar_publicacion:
                return this.enviarSolicitudEditarPublicacion(solicitud);
            case suscribir_observador_eliminarPublicacion:
                return "Suscripcion eliminar publicación";
            case desuscribir_observador_eliminarPublicacion:
                return "Desuscripcion eliminar publicación";
            case eliminar_publicacion:
                return this.enviarSolicitudEliminarPublicacion(solicitud);
            case suscribir_observador_registrarComentario:
                return "Suscripcion registar comentario";
            case desuscribir_observador_registrarComentario:
                return "Desuscripcion registrar comentario";
            case registrar_comentario:
                return this.enviarSolicitudRegistrarComentario(solicitud);
            case suscribir_observador_editarComentario:
                return "Suscripcion editar comentario";
            case desuscribir_observador_editarComentario:
                return "Desuscripcion editar comentario";
            case editar_comentario:
                return this.enviarSolicitudEditarComentario(solicitud);
            default:
                return null;
        }
    }
    
    public String enviarSolicitudRegistrarUsuario(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
            if(usuario!=null){
//                this.notificar(respuesta);
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudIniciarSesion(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            System.out.println(respuestaServidor.getRespuesta());
            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
            if(usuario!=null){
//                this.notificar(respuesta);
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudIniciarSesionFacebook(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            System.out.println(respuestaServidor.getRespuesta());
            Usuario usuario= Deserealizador.getInstancia().deserealizarUsuario(respuestaServidor.getRespuesta());
            if(usuario!=null){
//                this.notificar(respuesta);
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
       
    public String enviarSolicitudRegistrarPublicacion(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
            if(publicacion!=null){
                ObservableRegistrarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudEditarPublicacion(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
            if(publicacion!=null){
                ObservableEditarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudEliminarPublicacion(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Publicacion publicacion= Deserealizador.getInstancia().deserealizarPublicacion(respuestaServidor.getRespuesta());
            if(publicacion!=null){
                ObservableEliminarPublicacion.getInstancia().notificar(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudRegistrarComentario(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Comentario comentario= Deserealizador.getInstancia().deserealizarComentario(respuestaServidor.getRespuesta());
            if(comentario!=null){
                ObservableRegistrarComentario.getInstancia().notificar(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudEditarComentario(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();
            Solicitud respuestaServidor= Deserealizador.getInstancia().deserializarSolicitud(respuesta);
            Comentario comentario= Deserealizador.getInstancia().deserealizarComentario(respuestaServidor.getRespuesta());
            if(comentario!=null){
                ObservableEditarComentario.getInstancia().notificar(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudConsultarPublicaciones(String solicitud){
        String respuesta= "";
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        try{
            socket= new Socket(HOST, PUERTO);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();    
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
//    private String serializarSolicitud(Solicitud solicitud){
//        try{
//            ObjectMapper mapper=new ObjectMapper();
//            String solicitudSerializada= mapper.writeValueAsString(solicitud);
//            return solicitudSerializada;
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    
//    private Solicitud deserializarSolicitud(String solicitud){
//        try{
//            ObjectMapper conversion= new ObjectMapper();
//            return conversion.readValue(solicitud, Solicitud.class);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//    
//    private Publicacion deserealizarPublicacion(String publicacion){
//        try{
//            ObjectMapper conversion= new ObjectMapper();
//            return conversion.readValue(publicacion, Publicacion.class);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//    
//    public Usuario deserealizarUsuario(String usuario){
//        try{
//            ObjectMapper conversion= new ObjectMapper();
//            return conversion.readValue(usuario, Usuario.class);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
}
