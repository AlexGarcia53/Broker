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

/**
 *
 * @author Admin
 */
public class Broker {
    public String enviarSolicitud(String solicitud){
        String respuesta= "";
        try{
            Socket socket= new Socket("192.168.100.5", 5001);
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
