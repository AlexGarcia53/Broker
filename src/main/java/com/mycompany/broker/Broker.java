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
import dominio.Operacion;
import dominio.Publicacion;
import dominio.Usuario;
import interfaces.Suscriptor;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Broker {
    private String HOST= "192.168.100.5";
    private int PUERTO= 5001;
    private static Broker broker;
//    private ArrayList<ControladorClientes> clientesConectados= new ArrayList<>();
    private ArrayList<Suscriptor> suscriptores= new ArrayList<>();
    private Suscriptor detectorNotificaciones;
    
    private Broker(){
        
    }
    
    public static Broker obtenerInstancia(){
        if(broker==null){
            broker= new Broker();
        }
        return broker;
    }
    
    public String agregarDetectorNotificaciones(Suscriptor cliente, String solicitud){
        this.detectorNotificaciones= cliente;
        System.out.println("Se suscribió un detector de notificaciones");
        Solicitud solicitudDeserealizada= Deserealizador.getInstancia().deserializarSolicitud(solicitud);
        solicitudDeserealizada.setRespuesta("Éxito");
        String solicitudSerializada= Deserealizador.getInstancia().serializarSolicitud(solicitudDeserealizada);
        return solicitudSerializada;
    }
//    
//    public void eliminarCliente(ControladorClientes cliente){
//        this.clientesConectados.remove(cliente);
//    }
//    
//    public ArrayList<ControladorClientes> obtenerListaClientes(){
//        return this.clientesConectados;
//    }
    
    public void suscribirCliente(Suscriptor suscriptor){
        this.suscriptores.add(suscriptor);
//        System.out.println("Se suscribió un cliente");
//        Solicitud solicitudDeserealizada= this.deserializarSolicitud(solicitud);
//        solicitudDeserealizada.setRespuesta("Éxito");
//        String solicitudSerializada= this.serializarSolicitud(solicitudDeserealizada);
//        return solicitudSerializada;
    }
    
    public void desuscribirCliente(Suscriptor suscriptor){
        this.suscriptores.remove(suscriptor);
//        Solicitud solicitudDeserealizada= this.deserializarSolicitud(solicitud);
//        solicitudDeserealizada.setRespuesta("Éxito");
//        String solicitudSerializada= this.serializarSolicitud(solicitudDeserealizada);
//        return solicitudSerializada;
    }
    
    public void notificar(String actualizacion){
        this.detectorNotificaciones.actualizar(actualizacion);
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
            case registrar_detector_notificaciones:
                return "Suscripción";
//            case desuscribir_observador_muro:
//                return "Desuscripción";
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
                this.notificar(respuesta);
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
                this.notificar(respuesta);
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
                this.notificar(respuesta);
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
            if(respuestaServidor.getRespuesta().equalsIgnoreCase("Se llevó a cabo el registro")){
                this.notificar(respuesta);
            }
            
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
