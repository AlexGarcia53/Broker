/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 * Interfaz que contiene la operación necesaria para poder ejecutar las estrategias que la 
 * implementen.
 * @author Equipo Broker.
 */
public interface IEstrategia {
    /**
     * Método encargado de enviar la solicitud al servidor y devolver la respuesta que le dió este último.
     * @param solicitud String con la solicitud a enviar.
     * @return Respuesta del servidor.
     */
    public String enviarSolicitud(String solicitud);
}
