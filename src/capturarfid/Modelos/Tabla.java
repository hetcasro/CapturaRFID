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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class Tabla {
    
    protected String nombreTabla;
    protected String llavePrimaria;
    protected HashMap<String, String> columnas = new HashMap<>();
    
    protected void setNombreTabla(String nombreTabla){
        this.nombreTabla = nombreTabla;
    }
    
    protected String getNombreTabla(){
      return this.nombreTabla;
    }
    
    protected void setLlavePrimaria(String llavePrimaria){
        this.llavePrimaria = llavePrimaria;  
    }
    
    protected String getLlavePrimaria(){
        return this.llavePrimaria;
    }
    
    public void agregar(String columna, String valor){
        this.columnas.put(columna,valor);
    }
    
    public ResultSet select(){
        Connection con = Conexion.conectar();
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery("SELECT * FROM "+this.nombreTabla);
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
        return resultado;
    }
    
    public ResultSet select(Object llavePrimaria){
        Connection con = Conexion.conectar();
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery(
                        "SELECT * FROM "+this.nombreTabla+
                        " WHERE "+this.llavePrimaria+" = "+llavePrimaria.toString()
                        );
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
        return resultado;
    }
    
    public void delete(Object llavePrimaria){
        Connection con = Conexion.conectar();
        try {
            Statement sql = con.createStatement();
            sql.executeUpdate(
                "DELETE FROM "+this.nombreTabla+
                " WHERE "+this.llavePrimaria+" = "+llavePrimaria.toString()
            );
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
    }
    
    public void insert(){
        String columnas = "INSERT INTO "+this.nombreTabla+"(";
        String valores = " VALUES(";
        for(Map.Entry<String,String> entry : this.columnas.entrySet()){
            columnas += (entry.getKey()+",");
            valores += ("'"+entry.getValue()+"',");
        }
        String query = columnas.substring(0,columnas.length()-1)+")"+
                     valores.substring(0,valores.length()-1)+")";
 
        Connection con = Conexion.conectar();
        try {
            Statement sql = con.createStatement();
            sql.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                this.columnas.clear();
                if(con != null){
                    con.close();  
                }                       
            } catch (SQLException ex) {  
               Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update(Object llavePrimaria){
        String query = "UPDATE "+this.nombreTabla+" SET ";
        for(Map.Entry<String,String> entry : this.columnas.entrySet()){
           query += entry.getKey() + " = " + "'"+entry.getValue()+"',";
        }
        query = query.substring(0, query.length() - 1);
        query += " WHERE "+this.llavePrimaria+" = "+llavePrimaria.toString();
        
        Connection con = Conexion.conectar();
        try {
            Statement sql = con.createStatement();
            sql.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                this.columnas.clear();
                if(con != null){
                    con.close();  
                }                       
            } catch (SQLException ex) {  
               Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
