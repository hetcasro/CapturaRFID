/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class Usuarios extends Tabla{
    
    public Usuarios(String nombreTabla,String llavePrimaria){
        setNombreTabla(nombreTabla);
        setLlavePrimaria(llavePrimaria);
    }
    
    public HashMap datosUsuario(Object id){
        HashMap<String,String> datos = new HashMap<>();
        
        Connection con = Conexion.conectar();
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery(
                           "SELECT * FROM users "
                           +"WHERE "+this.llavePrimaria+" = '"+id+"'" 
                       );
            while(resultado.next()){
                datos.put("nombre", resultado.getString("name"));
                datos.put("apellido",resultado.getString("lastname"));
                datos.put("programa","Sistemas");
                datos.put("sede", "Facatativa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(con != null){
                    con.close();  
                }                       
            } catch (SQLException ex) {  
               Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return datos;
    }
    
    public static HashMap login(String email,String password){
        Connection con = Conexion.conectar();
        HashMap<String,String> session = new HashMap<>();
        int numFilas = 0;
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery(
                        "SELECT * FROM users  WHERE "
                         +"email = '"+email+"'"                                                                        
                    );
            while(resultado.next()){
                session.put("id", Integer.toString(resultado.getInt("id")));
                session.put("nombre", resultado.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(con != null){
                    con.close();  
                }                       
            } catch (SQLException ex) {  
               Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return session;
    }
}
