/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observables;

import com.mycompany.broker.Deserealizador;
import dominio.Operacion;
import dominio.Solicitud;
import interfaces.Observador;

/**
 *
 * @author Admin
 */
public class CanalizadorSuscripciones {
    private static CanalizadorSuscripciones canalizadorSuscripciones;
    
    private CanalizadorSuscripciones(){
        
    }
    
    public static CanalizadorSuscripciones getInstancia(){
        if(canalizadorSuscripciones==null){
            canalizadorSuscripciones= new CanalizadorSuscripciones();
        }
        return canalizadorSuscripciones;
    }
    
    public void canalizarSolicitud(String solicitud, Observador suscriptor){
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
            }
            default:{
                break;
            }
        }
    }
}
