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
public class ObservableRegistrarComentario {
    private static ArrayList<IObservador> suscriptores= new ArrayList<>();
    private static ObservableRegistrarComentario observableRegistrarComentario;
    
    private ObservableRegistrarComentario(){
        
    }
    
    public static ObservableRegistrarComentario getInstancia(){
        if(observableRegistrarComentario==null){
            observableRegistrarComentario= new ObservableRegistrarComentario();
        }
        return observableRegistrarComentario;
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
