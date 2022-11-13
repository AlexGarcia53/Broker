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

/**
 *
 * @author Admin
 */
public class Broker {
    private static Broker broker;
    private ArrayList<ControladorClientes> clientesConectados= new ArrayList<>();
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
    
    public String enviarSolicitud(String solicitud){
        String respuesta= "";
        try{
            Socket socket= new Socket("192.168.0.4", 5001);
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
}
