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
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Broker {
    private String HOST= "192.168.0.4";
    private int PUERTO= 5001;
    private static Broker broker;
    private ArrayList<ControladorClientes> clientesConectados= new ArrayList<>();
    private ArrayList<ControladorClientes> suscriptoresMuro= new ArrayList<>();
    
    private Broker(){
        
    }
    
    public static Broker obtenerInstancia(){
        if(broker==null){
            broker= new Broker();
        }
        return broker;
    }
    
    public void agregarNuevoCliente(ControladorClientes cliente){
        this.clientesConectados.add(cliente);
    }
    
    public void eliminarCliente(ControladorClientes cliente){
        this.clientesConectados.remove(cliente);
    }
    
    public ArrayList<ControladorClientes> obtenerListaClientes(){
        return this.clientesConectados;
    }
    
    public String suscribirClienteMuro(ControladorClientes suscriptor, String solicitud){
        this.suscriptoresMuro.add(suscriptor);
        System.out.println("Se suscribió un cliente");
        Solicitud solicitudDeserealizada= this.deserializarSolicitud(solicitud);
        solicitudDeserealizada.setRespuesta("Éxito");
        String solicitudSerializada= this.serializarSolicitud(solicitudDeserealizada);
        return solicitudSerializada;
    }
    
    public String desuscribirClienteMuro(ControladorClientes suscriptor, String solicitud){
        this.suscriptoresMuro.remove(suscriptor);
        Solicitud solicitudDeserealizada= this.deserializarSolicitud(solicitud);
        solicitudDeserealizada.setRespuesta("Éxito");
        String solicitudSerializada= this.serializarSolicitud(solicitudDeserealizada);
        return solicitudSerializada;
    }
    
    public void notificarSuscriptores(String actualizacion){
        for(ControladorClientes suscriptor: suscriptoresMuro){
            suscriptor.actualizar(actualizacion);
        }
    }
    
    public String canalizarSolicitud(String solicitud){
        Solicitud objetoSolicitud = this.deserializarSolicitud(solicitud);
        Operacion tipoOperacion= objetoSolicitud.getOperacion();
        switch (tipoOperacion) {
            case registrar_usuario:
                return this.enviarSolicitudRegistrarUsuario(solicitud);
            case iniciar_sesion:
                return this.enviarSolicitudIniciarSesion(solicitud);
            case registrar_publicacion:
                return this.enviarSolicitudRegistrarPublicacion(solicitud);
            case suscribir_observador_muro:
                return "Suscripción";
            case desuscribir_observador_muro:
                return "Desuscripción";
            default:
                return null;
        }
    }
    
    public String enviarSolicitudRegistrarUsuario(String solicitud){
        String respuesta= "";
        try{
            Socket socket= new Socket(HOST, PUERTO);
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
            
            Solicitud respuestaServidor= deserializarSolicitud(respuesta);
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    public String enviarSolicitudIniciarSesion(String solicitud){
        String respuesta= "";
        try{
            Socket socket= new Socket(HOST, PUERTO);
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

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
    
    public String enviarSolicitudRegistrarPublicacion(String solicitud){
        String respuesta= "";
        try{
            Socket socket= new Socket(HOST, PUERTO);
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(solicitud);
            bufferedWriter.write(solicitud);
            bufferedWriter.newLine();
            bufferedWriter.flush();

           
            respuesta= bufferedReader.readLine();
            Solicitud respuestaServidor= this.deserializarSolicitud(respuesta);
            if(respuestaServidor.getRespuesta().equalsIgnoreCase("Se llevó a cabo el registro")){
                this.notificarSuscriptores(respuestaServidor.getRespuesta());
            }
            
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            return respuesta;
        }
    }
    
    private String serializarSolicitud(Solicitud solicitud){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String solicitudSerializada= mapper.writeValueAsString(solicitud);
            return solicitudSerializada;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private Solicitud deserializarSolicitud(String solicitud){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(solicitud, Solicitud.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    private Publicacion deserealizarPublicacion(String publicacion){
        try{
            ObjectMapper conversion= new ObjectMapper();
            return conversion.readValue(publicacion, Publicacion.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
