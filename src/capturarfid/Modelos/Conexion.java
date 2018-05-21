/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Modelos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class Conexion {
    
    public static Connection conectar(){
        
        Connection link = null;
        HashMap datos = leerDatosConexion();
        String controlador = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://sql46.main-hosting.eu:3306/"+datos.get("BASE_DE_DATOS").toString().trim();
        try {
            Class.forName(controlador);
            link = DriverManager.getConnection(url,datos.get("USUARIO").toString().trim(),datos.get("CLAVE").toString().trim()); 
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    
    public static HashMap leerDatosConexion(){
        
        int index;
        String linea;
        HashMap<String,String> datos = new HashMap<>();
        File archivo ;
        FileReader fr = null;
        BufferedReader br;
        
        try{ 
            archivo = new File("configuracion.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            do{
               linea = br.readLine();
               index = linea.indexOf("=");
               datos.put(linea.substring(0, index-1), linea.substring(index+1));
            }while(!linea.isEmpty());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally{
            try {  
                if(fr != null){
                    fr.close();    
                }
            }catch(IOException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
        return datos;
    }
}
