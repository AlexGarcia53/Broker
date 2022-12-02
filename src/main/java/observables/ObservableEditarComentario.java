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
public class ObservableEditarComentario {
    private static ArrayList<IObservador> suscriptores= new ArrayList<>();
    private static ObservableEditarComentario observableEditarComentario;
    
    private ObservableEditarComentario(){
        
    }
    
    public static ObservableEditarComentario getInstancia(){
        if(observableEditarComentario==null){
            observableEditarComentario= new ObservableEditarComentario();
        }
        return observableEditarComentario;
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
