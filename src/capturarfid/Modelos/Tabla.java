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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class Tabla {
    
    protected String nombreTabla;
    
    protected void setNombreTabla(String nombreTabla){
        this.nombreTabla = nombreTabla;
    }
    
    protected String getNombreTabla(){
      return this.nombreTabla;
    }
    
    public ResultSet select(){
        Connection con = Conexion.conectar();
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery("SELECT * FROM "+this.nombreTabla);
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return resultado;
    }
}
