/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estrategias;

import interfaces.IEstrategia;

/**
 *
 * @author Admin
 */
public class SolicitudDesuscripcion implements IEstrategia{

    @Override
    public String enviarSolicitud(String solicitud) {
        return "Desuscripcion";
    }
    
}
