/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observables;

import com.mycompany.broker.Deserealizador;
import dominio.Operacion;
import dominio.Solicitud;
import interfaces.IObservador;

/**
 * Clase encargada de canalizar las suscripciones a los respectivos observables de cada evento.
 * @author Equipo Broker.
 */
public class CanalizadorSuscripciones {
    /**
     * Instancia estática de la clase.
     */
    private static CanalizadorSuscripciones canalizadorSuscripciones;
    /**
     * Método constructor de la clase.
     */
    private CanalizadorSuscripciones(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase, en caso de que no este inicializada 
     * el método se encargá de inicializarla.
     * @return Instancia de la clase.
     */
    public static CanalizadorSuscripciones getInstancia(){
        if(canalizadorSuscripciones==null){
            canalizadorSuscripciones= new CanalizadorSuscripciones();
        }
        return canalizadorSuscripciones;
    }
    /**
     * Método encargado de canalizar la suscripción al observable correcto, dependiendo del evento.
     * @param solicitud Solicitud que contiene el evento específico.
     * @param suscriptor Suscriptor que se añadirá o removerá de los suscriptores del observable.
     */
    public void canalizarSolicitud(String solicitud, IObservador suscriptor){
        Solicitud solicitudSuscripcion= Deserealizador.getInstancia().deserializarSolicitud(solicitud);
        Operacion operacion= solicitudSuscripcion.getOperacion();
        switch(operacion){
            case suscribrir_observador_registrarPublicacion:{
                ObservableRegistrarPublicacion.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribrir_observador_registrarPublicacion:{
                ObservableRegistrarPublicacion.getInstancia().desuscribirse(suscriptor);
                break;
            }
            case suscribir_observador_editarPublicacion:{
                ObservableEditarPublicacion.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribir_observador_editarPublicacion:{
                ObservableEditarPublicacion.getInstancia().desuscribirse(suscriptor);
                break;
            }
            case suscribir_observador_eliminarPublicacion:{
                ObservableEliminarPublicacion.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribir_observador_eliminarPublicacion:{
                ObservableEliminarPublicacion.getInstancia().desuscribirse(suscriptor);
                break;
            }
            case suscribir_observador_registrarComentario:{
                ObservableRegistrarComentario.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribir_observador_registrarComentario:{
                ObservableRegistrarComentario.getInstancia().desuscribirse(suscriptor);
                break;
            }
            case suscribir_observador_editarComentario:{
                ObservableEditarComentario.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribir_observador_editarComentario:{
                ObservableEditarComentario.getInstancia().desuscribirse(suscriptor);
                break;
            }
            case suscribir_observador_eliminarComentario:{
                ObservableEliminarComentario.getInstancia().suscribirse(suscriptor);
                break;
            }
            case desuscribir_observador_eliminarComentario:{
                ObservableEliminarComentario.getInstancia().desuscribirse(suscriptor);
            }
            default:{
                break;
            }
        }
    }
}
