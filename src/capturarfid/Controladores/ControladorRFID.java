/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Controladores;

import capturarfid.Modelos.HashRfid;
import capturarfid.Vistas.VistaPrincipal;
import capturarfid.Modelos.Usuarios;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
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
    private HashMap session;
    
    public ControladorRFID(HashMap session){
       this.session = session;
       this.iniciarPuertoSerie();
       this.usuario = new Usuarios("users","card_code");
       this.vista = new VistaPrincipal();
       this.vista.setVisible(true);
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPort("COM5");
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
                Thread.sleep(300);
                this.leer();
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorRFID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void conectar(){
        OutputStream out = puerto.getOutputStream();
        
            try {
                for(byte i=0; i < trama.length; i++){
                    out.write(trama[i]);
                    Thread.sleep(8);
                }
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
    
    private void leer(){
        if(puerto.bytesAvailable() > 0){
            byte[] buffer = new byte[puerto.bytesAvailable()];
            puerto.readBytes(buffer,buffer.length);
            if(buffer.length > 9){
               HashMap datos = usuario.datosUsuario(this.tramaAString(buffer));
               if(!datos.isEmpty()){
                   this.vista.setDatos(datos);
               }else{
                   this.insertar(this.tramaAString(buffer));
               } 
            }
        }
    }
    
    private String tramaAString(byte[] buffer){
        String id = "";
        for(byte i=10; i < buffer.length; i++){
            if(buffer[i] < 0){
                buffer[i] = (byte)(buffer[i] * (-1));
            }
            id += buffer[i];
        }
        if(id.length() < 17){
          id = "0" +id;
        }
        return id;
    }
    
    private void insertar(String codigo){
        HashRfid hash = new HashRfid("tbl_listen");
        hash.agregar("LI_Codigo",codigo);
        hash.agregar("FK_UsuarioId",session.get("id").toString());
        hash.insert();
    }
}
