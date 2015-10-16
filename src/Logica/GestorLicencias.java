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
    
    public Calendar calcularVigencia(String FN){
       //edadTitularParaLicencia no es la edad del titular en si, sino que será la edad que tendrá en el supuesto caso de que esté cerca de su fecha de nacimiento 
        
        Calendar fechaVigencia= Calendar.getInstance();
        
        int edadTitularParaLicencia;
        int aniosLicencia;
        edadTitularParaLicencia = calcularAniosTitular(FN);
        aniosLicencia = calcularAniosLicencia(edadTitularParaLicencia);    
        fechaVigencia = calcularFechaVigencia(FN, aniosLicencia, edadTitularParaLicencia);
        return fechaVigencia;
    }
    
    
    public int calcularAniosTitular(String FN){ //Metodo que calcula los anios del titular
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
            año++;
        }
        
        if(mes <= 3){ //si hay una diferencia en meses se toma un año mas, el año del titular no es su edad exacta, sino el año con los que renovará su licencia
            año++;
        }
        //Regresa la edad en base a la fecha de nacimiento + 1 año en el caso de que este cerca de su fecha de nacimiento y desea renovar licencia
        return año;
    } 
    
    public int calcularAniosLicencia(int edadTitular) { //Metodo que calcula los anios de licencia que se le otorga a un titular a partir de su edad
        
        if(edadTitular <17){
            return 0;
        }
        else if(edadTitular>=17 && edadTitular < 21){
            return 1;
        }
        else if(edadTitular >=21 && edadTitular<46){
            return 5;
        }
        else if(edadTitular>=46 && edadTitular<60){
            return 4;
        }
        else if(edadTitular>=61 && edadTitular<70){
            return 3;
        }
        else if(edadTitular>=71){
            return 1;
        }   
        return 0;
    }  
    
    public Calendar calcularFechaVigencia(String fechaNacimiento, int aniosLicencia, int edadTitularLicencia){
        Date fechaVigenciaLicencia = null;
        
        try {
            fechaVigenciaLicencia = new SimpleDateFormat("dd-MM-yyyy").parse(fechaNacimiento);
        } catch (ParseException ex) {
            System.out.println("Error: No se pudo calcular vigencia. "+ex);
        }
        
        Calendar fechaVigencia = Calendar.getInstance();
        
        fechaVigencia.setTime(fechaVigenciaLicencia);
        fechaVigencia.add(Calendar.YEAR,(aniosLicencia + edadTitularLicencia - 1));
        
        return fechaVigencia;
        
    
    }
}
