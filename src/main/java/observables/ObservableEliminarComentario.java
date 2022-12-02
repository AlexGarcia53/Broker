/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observables;

import interfaces.IObservador;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ObservableEliminarComentario {
    private static ArrayList<IObservador> suscriptores= new ArrayList<>();
    private static ObservableEliminarComentario observableEliminarComentario;
    
    private ObservableEliminarComentario(){
        
    }
    
    public static ObservableEliminarComentario getInstancia(){
        if(observableEliminarComentario==null){
            observableEliminarComentario= new ObservableEliminarComentario();
        }
        return observableEliminarComentario;
    }
    
    public void suscribirse(IObservador suscriptor){
        this.suscriptores.add(suscriptor);
        System.out.println("Se agrego un suscriptor, "+suscriptores.size());
    }
    
    public void desuscribirse(IObservador suscriptor){
        this.suscriptores.remove(suscriptor);
        System.out.println("Se elimino un suscriptor, "+suscriptores.size());
    }
    
    public void notificar(String actualizacion){
        System.out.println(suscriptores.size());
        for (IObservador suscriptor: suscriptores){
            suscriptor.notificar(actualizacion);
        }
    }
}
