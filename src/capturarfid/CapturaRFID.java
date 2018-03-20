/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid;

import capturarfid.Modelos.Usuarios;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class CapturaRFID {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Usuarios usuario = new Usuarios("tbl_usuarios","pk_id");
        usuario.agregar("pk_id", "3");
        usuario.agregar("nombreUsuario","Paisa");
        usuario.agregar("fk_programaId","1");
        usuario.insert();
        /*try {
            while(resultado.next()){
              System.out.println(resultado.getString("nombreUsuario"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CapturaRFID.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
}
