//Corresponde a la logica a utilizar para calculos de licencia

package Logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Victoria
 */
public class GestorLicencias {
    public int calcularAñosTitular(String FN){
        Date fechaNac= null;
        try {
            fechaNac = new SimpleDateFormat("dd-MM-yyyy").parse(FN);
        } catch (ParseException ex) {
            System.out.println("Error: No se pudo calcular vigencia. "+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance(); //creamos un objeto con la fecha de nacimiento
        Calendar fechaActual = Calendar.getInstance(); //creamos un objeto con la fecha actual
        
        fechaNacimiento.setTime(fechaNac);
        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);        
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;
    }
}
