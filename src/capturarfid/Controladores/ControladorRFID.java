/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Controladores;

import capturarfid.Vistas.VistaPrincipal;
import capturarfid.Modelos.Usuarios;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;



/**
 *
 * @author Hector
 */
public class ControladorRFID {
    
    private SerialPort puerto;
    private VistaPrincipal vista;
    private Usuarios usuario;
    
    public ControladorRFID(){
       this.iniciarPuertoSerie();
       this.usuario = new Usuarios("tbl_usuarios","pk_id");
       this.vista = new VistaPrincipal();
       this.vista.setVisible(true);
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPort("COM4");
        this.puerto.setBaudRate(9600);
        this.puerto.openPort();
        this.puerto.addDataListener(new SerialPortDataListener(){

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if(event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                    byte [] buffer = new byte[puerto.bytesAvailable()];
                    puerto.readBytes(buffer, buffer.length);
                    String datos = new String(buffer);
                    if(datos != null){
                        vista.setDatos(usuario.datosUsuario(datos));
                    }   
                }
            }
        
        });
    }
}
