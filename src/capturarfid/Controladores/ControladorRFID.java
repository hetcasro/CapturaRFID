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
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Hector
 */
public class ControladorRFID implements Runnable{
    
    private SerialPort puerto;
    private VistaPrincipal vista;
    private Usuarios usuario;
    private final int [] trama = {1,9,0,3,4,65,10,68,187};
    
    public ControladorRFID(){
       System.out.println(trama.length);
       this.iniciarPuertoSerie();
       this.usuario = new Usuarios("tbl_usuarios","pk_id");
       this.vista = new VistaPrincipal();
       this.vista.setVisible(true);
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPort("COM3");
        this.puerto.setBaudRate(9600);
        this.puerto.openPort();
        /*this.puerto.addDataListener(new SerialPortDataListener(){

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
        
        });*/
    }

    @Override
    public void run() {
        while(true){
            try {
                this.conectar();
                Thread.sleep(100);
                this.leer();
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorRFID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void conectar(){
        OutputStream out = puerto.getOutputStream();
        for(byte i=0; i<trama.length; i++){
            try {
                out.write(trama[i]);
                Thread.sleep(8);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ControladorRFID.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(ControladorRFID.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }
    
    private void leer(){
        if(puerto.bytesAvailable() > 0){
            byte[] buffer = new byte[puerto.bytesAvailable()];
            puerto.readBytes(buffer,buffer.length);
            String mensaje = new String(buffer);
            System.out.println(mensaje);
        }
    }
}
