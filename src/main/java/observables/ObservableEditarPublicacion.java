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
public class ObservableEditarPublicacion {
    private static ArrayList<Observador> suscriptores= new ArrayList<>();
    private static ObservableEditarPublicacion observableEditarPublicacion;
    
    private ObservableEditarPublicacion(){
        
    }
    
    public static ObservableEditarPublicacion getInstancia(){
        if(observableEditarPublicacion==null){
            observableEditarPublicacion= new ObservableEditarPublicacion();
        }
        return observableEditarPublicacion;
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