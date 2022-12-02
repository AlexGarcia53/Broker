/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estrategias;

import interfaces.IEstrategia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Clase encargada del envío de una solicitud del tipo consultar a un usuario por el nombre.
 * @author Equipo Broker.
 */
public class SolicitudConsultarUsuarioNombre implements IEstrategia {
    /**
     * Atributo con la ip del HOST al que se conectará para enviar la solicitud.
     */
    private String HOST= "127.0.0.1";
    /** 
     * Atributo con el puerto al que se conectará para enviar la solicitud.
     */
    private int PUERTO= 5001; 
    /**
     * Atributo que contiene la respuesta a la solicitud.
     */
    private String respuesta= "";
    /**
     * Atributo con el socket de la conexión.
     */
    private Socket socket;
    /** 
     * Atributo con el buffered reader de la conexión.
     */
    private BufferedReader bufferedReader;
    /**
     * Atributo con el buffered writer de la conexión.
     */
    private BufferedWriter bufferedWriter;
    /**
     * Método encargado de enviar la solicitud al servidor y devolver la respuesta que le dió este último.
     * @param solicitud String con la solicitud a enviar.
     * @return Respuesta del servidor.
     */
    @Override
    public String enviarSolicitud(String solicitud) {
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
    
}
