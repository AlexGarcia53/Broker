/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observables;

import interfaces.Observador;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ObservableEditarComentario {
    private static ArrayList<Observador> suscriptores= new ArrayList<>();
    private static ObservableEditarComentario observableEditarComentario;
    
    private ObservableEditarComentario(){
        
    }
    
    public static ObservableEditarComentario getInstancia(){
        if(observableEditarComentario==null){
            observableEditarComentario= new ObservableEditarComentario();
        }
        return observableEditarComentario;
    }
    
    public void suscribirse(Observador suscriptor){
        this.suscriptores.add(suscriptor);
        System.out.println("Se agrego un suscriptor, "+suscriptores.size());
    }
    
    public void desuscribirse(Observador suscriptor){
        this.suscriptores.remove(suscriptor);
        System.out.println("Se elimino un suscriptor, "+suscriptores.size());
    }
    
    public void notificar(String actualizacion){
        System.out.println(suscriptores.size());
        for (Observador suscriptor: suscriptores){
            suscriptor.notificar(actualizacion);
        }
    }
}
