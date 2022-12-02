/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observables;

import interfaces.IObservador;
import java.util.ArrayList;

/**
 * Clase encargada de notificar las ediciones de comentarios y llevar una lista de los clientes 
 * que observan a dicho evento.
 * @author Equipo Broker.
 */
public class ObservableEditarComentario {
    /**
     * Lista de suscriptores del evento de la clase.
     */
    private static ArrayList<IObservador> suscriptores= new ArrayList<>();
    /**
     * Instancia estática de la clase.
     */
    private static ObservableEditarComentario observableEditarComentario;
    /**
     * Método constructor de la clase.
     */
    private ObservableEditarComentario(){
        
    }
    /**
     * Método utilizado para obtener la instancia de la clase, en caso de que no este inicializada 
     * el método se encarga de inicializarla.
     * @return Instancia de la clase.
     */
    public static ObservableEditarComentario getInstancia(){
        if(observableEditarComentario==null){
            observableEditarComentario= new ObservableEditarComentario();
        }
        return observableEditarComentario;
    }
    /** 
     * Método utilizado para añadir un suscriptor a la lista de observadores del evento de la clase.
     * @param suscriptor Suscriptor a añadir.
     */
    public void suscribirse(IObservador suscriptor){
        this.suscriptores.add(suscriptor);
        System.out.println("Se agrego un suscriptor, "+suscriptores.size());
    }
    /**
     * Método utilizado para eliminar a un suscriptor de la lista de observadores del evento de la clase.
     * @param suscriptor Suscriptor a remover.
     */
    public void desuscribirse(IObservador suscriptor){
        this.suscriptores.remove(suscriptor);
        System.out.println("Se elimino un suscriptor, "+suscriptores.size());
    }
    /**
     * Método utilizado para notificar una actualización a todos los suscriptores del evento de la clase.
     * @param actualizacion Actualización dada por el evento de la clase.
     */
    public void notificar(String actualizacion){
        System.out.println(suscriptores.size());
        for (IObservador suscriptor: suscriptores){
            suscriptor.notificar(actualizacion);
        }
    }
}
