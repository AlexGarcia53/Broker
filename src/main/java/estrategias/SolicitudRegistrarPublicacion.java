/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estrategias;

import com.mycompany.broker.Deserealizador;
import dominio.Publicacion;
import dominio.Solicitud;
import interfaces.IEstrategia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import observables.ObservableRegistrarPublicacion;

/**
 *
 * @author Admin
 */
public class SolicitudRegistrarPublicacion implements IEstrategia{
    private String HOST= "127.0.0.1";
    private int PUERTO= 5001; 
    private String respuesta= "";
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
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
    
}
