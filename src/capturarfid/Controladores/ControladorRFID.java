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
import java.io.IOException;
import java.io.OutputStream;
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
    private final int [] trama = {0xAA,0xBB,0x06,0x00,0x00,0x00,0x01,0x01,0x03,0x03};
    private final int [] trama1 = {0xAA,0xBB,0x06,0x00,0x00,0x00,0x01,0x02,0x52,0x51};
    private final int [] trama2 = {0xAA,0xBB,0x06,0x00,0x00,0x00,0x02,0x02,0x04,0x04};
    private HashMap session;
    
    public ControladorRFID(HashMap session){
       this.session = session;
       this.iniciarPuertoSerie();
       this.usuario = new Usuarios("users","card_code");
       this.vista = new VistaPrincipal();
       this.vista.setVisible(true);
    }
    
    private void iniciarPuertoSerie(){
        this.puerto = SerialPort.getCommPort("COM7");
        this.puerto.setBaudRate(19200);
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
                //this.leer();
                //Thread.sleep(300);
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
                    Thread.sleep(1);
                }
                Thread.sleep(50);
                this.leer();
                for(byte i=0; i < trama1.length; i++){
                    out.write(trama1[i]);
                    Thread.sleep(1);
                }
                Thread.sleep(50);
                this.leer();
                for(byte i=0; i < trama2.length; i++){
                    out.write(trama2[i]);
                    Thread.sleep(1);
                }
                Thread.sleep(50);
                this.leer();
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
           if(buffer.length >= 14){
              HashMap datos = usuario.datosUsuario(this.enteroAString(buffer));
              if(!datos.isEmpty()){
                   this.vista.setDatos(datos);
              }else{
                   this.insertar(this.enteroAString(buffer));
              } 
           }
        }
      
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControladorRFID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insertar(String codigo){
        HashRfid hash = new HashRfid("TBL_Listen");
        hash.agregar("LI_Codigo",codigo);
        hash.agregar("FK_UsuarioId",session.get("id").toString());
        hash.insert();
    }
    
    private String enteroAString(byte [] buffer){
         String trama ="";
         for(byte i=0; i < buffer.length;i++){
            trama += Integer.toHexString(buffer[i]);
         }
         if(trama.contains("ffffff")){
          trama = trama.replaceAll("ffffff","");
         }
         if(trama.length() == 23){
             trama = trama.substring(13,trama.length()-2);
         }else{
             trama = trama.substring(13,trama.length()-1);
         }
         return trama;
    }
}
