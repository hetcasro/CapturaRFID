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
        Usuarios usuario = new Usuarios("usuarios");
        ResultSet resultado = usuario.select();
        try {
            while(resultado.next()){
              System.out.println(resultado.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CapturaRFID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
