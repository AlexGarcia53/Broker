/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker;

import dominio.Solicitud;
import interfaces.SuscriptorMuro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ControladorClientes implements Runnable, SuscriptorMuro{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private Broker broker;
    
    public ControladorClientes(Socket socket){
        try{
            this.socket= socket;
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername= bufferedReader.readLine();
            System.out.println(clientUsername+ "se ha conectado");
            this.broker= Broker.obtenerInstancia();
            broker.agregarNuevoCliente(this);
        } catch (IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
        
    }
    
    @Override
    public void run() {
        String mensajeCliente, respuesta;
        
        while(socket.isConnected()){
            try{
                mensajeCliente= bufferedReader.readLine();
                if(mensajeCliente!=null){
                    System.out.println(mensajeCliente);
                    respuesta= broker.canalizarSolicitud(mensajeCliente);
                    if(respuesta.equalsIgnoreCase("Suscripción")){
                        String respuestaSuscripcion= broker.suscribirClienteMuro(this, mensajeCliente);
                        enviarRespuesta(respuestaSuscripcion);
                    }else if(respuesta.equalsIgnoreCase("Desuscripción")){
                        String respuestaDesuscripcion= broker.desuscribirClienteMuro(this, mensajeCliente);
                        enviarRespuesta(respuestaDesuscripcion);
                    }else{
                        enviarRespuesta(respuesta);
                    }
                }
            }catch(IOException e){
                cerrarTodo(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    
    public void enviarRespuesta(String respuesta){
        for (ControladorClientes controladorCliente: broker.obtenerListaClientes()) {
            try{
                if(controladorCliente.equals(this)){
                    controladorCliente.bufferedWriter.write(respuesta);
                    controladorCliente.bufferedWriter.newLine();
                    controladorCliente.bufferedWriter.flush();
                }
            } catch (IOException e){
                cerrarTodo(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    
    public void retransmitirMensaje(String mensajeAEnviar){
        for (ControladorClientes controladorCliente: broker.obtenerListaClientes()) {
            try{
                if(!controladorCliente.clientUsername.equals(clientUsername)){
                    controladorCliente.bufferedWriter.write(mensajeAEnviar);
                    controladorCliente.bufferedWriter.newLine();
                    controladorCliente.bufferedWriter.flush();
                }
            } catch (IOException e){
                cerrarTodo(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    
    public void eliminarControladorCliente(){
        broker.eliminarCliente(this);
        retransmitirMensaje("Servidor: "+clientUsername+" ha cerrado la aplicación");
    }
    
    public void cerrarTodo(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        eliminarControladorCliente();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String actualizacion) {
        try{
            bufferedWriter.write(actualizacion);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch(IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
}
