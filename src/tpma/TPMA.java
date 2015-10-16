/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tpma;

import Interfaz.MenuPrincipal;
import Interfaz.MenuPrincipalTitulares;
import Logica.GestorLicencias;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author franc
 */
public class TPMA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MenuPrincipalTitulares mp = new MenuPrincipalTitulares();
        mp.setVisible(true);
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        GestorLicencias gl = new GestorLicencias();
        Calendar x = gl.calcularVigencia("10-09-1993");
        
        String formatted = format.format(x.getTime());
        
        System.out.println("Su fecha de caducidad es: "+formatted);
    }
    
}
