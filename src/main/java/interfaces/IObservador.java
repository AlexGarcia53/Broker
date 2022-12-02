/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Solicitud;

/**
 * Interfaz que contiene el método necesario para que las clases que la implementen puedan 
 * notificar.
 * @author Equipo Broker.
 */
public interface IObservador {
    /**
     * Método utilizado para notificar la actualización de un evento.
     * @param actualizacion Actualización de un evento.
     */
    public void notificar(String actualizacion);
}
