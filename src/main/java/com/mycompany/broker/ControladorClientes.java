/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker;

import dominio.Solicitud;
import interfaces.IObservador;
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
import observables.CanalizadorSuscripciones;

/**
 * Clase que representa a los clientes que se conectan al broker y contiene los métodos necesarios 
 * para que puedan realizar sus solicitudes y recibir respuestas.
 * @author Equipo Broker.
 */
public class ControladorClientes implements Runnable, IObservador{
    /**
     * Atributo que contiene el socket de la conexión.
     */
    private Socket socket;
    /**
     * Atributo con el buffered reader de la conexión para leer datos.
     */
    private BufferedReader bufferedReader;
    /**
     * Atributo con el buffered writer de la conexión para escribir datos.
     */
    private BufferedWriter bufferedWriter;
    /**
     * Método constructor de la clase que inicializa los atributos.
     * @param socket Socket con la conexión del cliente.
     */
    public ControladorClientes(Socket socket){
        try{
            this.socket= socket;
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            cerrarTodo(this.socket, bufferedReader, bufferedWriter);
        }
    }
    /**
     * Método utilizado para escuchar las peticiones del cliente y canalizarlas para así poder dar una respuesta, 
     * todo esto mediante un hilo.
     */
    @Override
    public void run() {
        String mensajeCliente, respuesta;
        
        while(socket.isConnected()){
            try{
                mensajeCliente= bufferedReader.readLine();
                if(mensajeCliente!=null){
                    System.out.println(mensajeCliente);
                    respuesta= Broker.obtenerInstancia().canalizarSolicitud(mensajeCliente);
                    if(respuesta.startsWith("Suscripcion") || respuesta.startsWith("Desuscripcion")){
                        CanalizadorSuscripciones.getInstancia().canalizarSolicitud(mensajeCliente, this);
                    }else{
                        System.out.println("Entra al else"+respuesta);
                        enviarRespuesta(this, respuesta);
                    }
                }
            }catch(IOException e){
                cerrarTodo(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    /**
     * Método utilizado para enviar una respuesta al cliente para la solicitud que realizó.
     * @param controladorCliente Cliente al que se enviará la respuesta.
     * @param respuesta Respuesta que se enviará al cliente.
     */
    public void enviarRespuesta(ControladorClientes controladorCliente, String respuesta){
            try{
                    System.out.println("Entro a enviar respuesta");
                    controladorCliente.bufferedWriter.write(respuesta);
                    controladorCliente.bufferedWriter.newLine();
                    controladorCliente.bufferedWriter.flush();
            } catch (IOException e){
                cerrarTodo(socket, bufferedReader, bufferedWriter);
            }
    }
    /**
     * Método utilizado para eliminar al cliente de la lista de clientes conectados.
     */
    public void eliminarControladorCliente(){
        Broker.obtenerInstancia().desuscribirCliente(this);
    }
    /**
     * Método utilizado para cerrar la conexión hacia el cliente.
     * @param socket Socket de la conexión.
     * @param bufferedReader Buffered Reader de la conexión.
     * @param bufferedWriter Buffered Writer de la conexión.
     */
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
    /**
     * Método utilizado para notificar al cliente de una actualización de un evento.
     * @param actualizacion Actualización de un evento.
     */
    @Override
    public void notificar(String actualizacion) {
        try{
            bufferedWriter.write(actualizacion);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch(IOException e){
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }
}
