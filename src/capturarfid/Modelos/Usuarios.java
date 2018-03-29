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
    
    public HashMap datosUsuario(Object llavePrimaria){
        HashMap<String,String> datos = new HashMap<>();
        
        Connection con = Conexion.conectar();
        ResultSet resultado = null;
        try {
            Statement sql = con.createStatement();
            resultado = sql.executeQuery(
                            "SELECT  u.nombreUsuario, "
                            + "p.nombre_progrma,"
                            + "r.nombreMostrar "
                            + "FROM mydb.tbl_programas AS p JOIN mydb.tbl_usuarios AS u "
                            + "ON p.pk_id = u.fk_programaId AND u.pk_id = '"+llavePrimaria.toString()+"'"
                            + "JOIN mydb.tbl_usuario_rol AS ur ON ur.fk_usuarioId = u.pk_id "
                            + "JOIN mydb.tbl_roles AS r ON ur.fk_rolId = r.pk_id"
                    );
            while(resultado.next()){
                datos.put("nombre", resultado.getString("nombreUsuario"));
                datos.put("cargo",resultado.getString("nombreMostrar"));
                datos.put("programa", resultado.getString("nombre_progrma"));
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
}
