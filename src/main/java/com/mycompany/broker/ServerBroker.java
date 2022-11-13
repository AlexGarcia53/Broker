/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ServerBroker {

    private ServerSocket serverSocket;

    public ServerBroker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void inciarServidor(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket= serverSocket.accept();
                System.out.println("Se conecto un cliente");
                ControladorClientes controladorClientes= new ControladorClientes(socket);
                
                Thread client=new Thread(controladorClientes);
                client.start();
                
 
            }
        }catch (IOException e){
            this.cerrarServerSocket();
        }
    }
    
    public void cerrarServerSocket(){
        try{
            if(serverSocket!=null){
                serverSocket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket= new ServerSocket(5000);
        ServerBroker serverBroker=new ServerBroker(serverSocket);
        serverBroker.inciarServidor();
    }
}
