/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturarfid;

import capturarfid.Controladores.ControladorRFID;
import capturarfid.Vistas.Login;

/**
 *
 * @author Hector
 */
public class CapturaRFID {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //new Thread(new ControladorRFID()).start();
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
}
