/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estrategias;

import interfaces.IEstrategia;

/**
 * Clase encargada del envío de una solicitud del tipo desuscribirse a un evento.
 * @author Equipo Broker.
 */
public class SolicitudDesuscripcion implements IEstrategia{
    /**
     * Método encargado de enviar la solicitud al servidor y devolver la respuesta que le dió este último.
     * @param solicitud String con la solicitud a enviar.
     * @return Respuesta del servidor.
     */
    @Override
    public String enviarSolicitud(String solicitud) {
        return "Desuscripcion";
    }
    
}
