/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Controladores;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;



/**
 *
 * @author Hector
 */
public class ControladorRFID {
    
    private SerialPort puerto;
    
    public ControladorRFID(){
       this.iniciarPuertoSerie();
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPorts()[1];
        this.puerto.setBaudRate(9600);
    }
    
    public void escuchar(){
        this.puerto.openPort();
        while(true){
           if(this.puerto.bytesAvailable() != 0){
               byte [] buffer = new byte[this.puerto.bytesAvailable()];
               this.puerto.readBytes(buffer, buffer.length);
               String datos = new String(buffer);
               System.out.print(datos);
           }
        }
    }
}
