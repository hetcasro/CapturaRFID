/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Controladores;

import com.fazecast.jSerialComm.SerialPort;



/**
 *
 * @author Hector
 */
public class ControladorRFID {
    
    private static SerialPort puerto;
    
    public ControladorRFID(){
       this.iniciarPuertoSerie();
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPorts()[0];
    }
}
