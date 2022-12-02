/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que representa al servidor del broker, el cual se ejecuta y comienza a escuchar por peticiones, 
 * para el manejo de estas añade estas conexiones a un ControladorClientes y lo ejecuta como un hilo.
 * @author Equipo Broker.
 */
public class ServerBroker {
    /**
     * Atributo del tipo socket servidor.
     */
    private ServerSocket serverSocket;
    /**
     * Método constructor de la clase que inicializa el atributo de esta mediante el parámetro dado.
     * @param serverSocket Socket de tipo servidor.
     */
    public ServerBroker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    /**
     * Método utilizado para que el servidor comience a escuchar peticiones.
     */
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
    /**
     * Método utilizado para cerrar el socket del servidor.
     */
    public void cerrarServerSocket(){
        try{
            if(serverSocket!=null){
                serverSocket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Método main de la clase que crea un socket de tipo servidor, crea una instancia de la clase 
     * y hace que comience a escuchar por peticiones.
     * @param args Línea de argumentos.
     * @throws IOException Excepción que puede ser lanzada a lo largo de la ejecución.
     */
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket= new ServerSocket(5000);
        ServerBroker serverBroker=new ServerBroker(serverSocket);
        serverBroker.inciarServidor();
    }
}
