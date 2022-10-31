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
public class ControladorClientes implements Runnable{
    public static ArrayList<ControladorClientes> clientHandlers= new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private Broker broker;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    
    public ControladorClientes(Socket socket){
        try{
            this.socket= socket;
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream= new ObjectOutputStream(socket.getOutputStream());
            this.inputStream= new ObjectInputStream(socket.getInputStream());
            this.clientUsername= bufferedReader.readLine();
            System.out.println(clientUsername+ "se ha conectado");
            clientHandlers.add(this);
            this.broker=new Broker();
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
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
                    respuesta= broker.enviarSolicitud(mensajeCliente);
                    System.out.println(respuesta);
                    enviarRespuesta(respuesta);
                }
            }catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    
    public void enviarRespuesta(String respuesta){
        for (ControladorClientes clientHandler: clientHandlers) {
            try{
                if(clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(respuesta);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    
    public void broadcastMessage(String messageToSend){
        for (ControladorClientes clientHandler: clientHandlers) {
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("Server: "+clientUsername+" has left the chat");
    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
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
}
