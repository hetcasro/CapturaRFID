/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid.Controladores;
import capturarfid.Modelos.Usuarios;
import java.util.HashMap;

/**
 *
 * @author Hector
 */
public class ControladorLogin {
    
    public static HashMap validar(String email,String password){
               
        return Usuarios.login(email, password);
    }
}
