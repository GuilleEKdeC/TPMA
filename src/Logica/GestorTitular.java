//Corresponde a la logica a utilizar para la interfaz "AltaTitular"

package Logica;
import Control.DireccionJpaController;
import Control.EntityMan;
import Control.FactorrhJpaController;
import Control.GruposanguineoJpaController;
import Control.TipodocumentoJpaController;
import Control.TitularJpaController;
import Entity.Direccion;
import Entity.Factorrh;
import Entity.Gruposanguineo;
import Entity.Tipodocumento;
import Entity.Titular;
import Interfaz.AltaUsuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
/**
 *
 * @author Victoria
 */
public class GestorTitular {
    private Date fecha;

    //JTextField textField = new JTextField();
    public Date obtenerFecha(JFormattedTextField fecha1){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            fecha = formateador.parse(fecha1.getText());
        }
        catch (ParseException e){
            //Error, la cadena de texto no se puede convertir en fecha.
        }
        return fecha;
    }

    public String obtenerClaseSolicitada(int selectedIndex) {
        String letra="A";
        //ACA VA EL CODIGO QUE COMPARA LOS INDICES CON LAS LETRAS Y DEVUELVE LA CLASE.
        return letra;
    }

    public boolean obtenerValorDonante(Object selectedItem) {
        boolean valor=true;
        //ACA VA EL CODIGO QE COMPARA LOS INDICES
        return valor;
    }
    
    public boolean darAltaTitular(String nombre, String apellido, Date fecha, String nroDoc, String calle,String dpto, String nroCalle, String piso, int cmbClaseSolicitada, int cmbDonante, int cmbFactorRH, int cmbGrupoSanguineo,int cmbTipoDocumento){
    
        TitularJpaController t = new TitularJpaController(EntityMan.getInstance());
        Titular titular = new Titular();
        DireccionJpaController d = new DireccionJpaController(EntityMan.getInstance());
        Direccion direccion = new Direccion();
        NomencladorDatosPersonales nomDP=NomencladorDatosPersonales.getInstance();
        /*TipodocumentoJpaController td = new TipodocumentoJpaController(EntityMan.getInstance());
        Tipodocumento tipoDocumento = new Tipodocumento();
        GruposanguineoJpaController gs = new GruposanguineoJpaController(EntityMan.getInstance());
        Gruposanguineo grupoSanguineo = new Gruposanguineo();
        FactorrhJpaController fr = new FactorrhJpaController(EntityMan.getInstance());
        Factorrh factorRH = new Factorrh();*/
        
        direccion.setCalle(calle);
        direccion.setDepartamento(dpto);
        direccion.setNumero(Integer.parseInt(nroCalle));
        direccion.setPiso(Integer.parseInt(piso));
        
        titular.setNombre(nombre);
        titular.setApellido(apellido);
        titular.setFechaNacimiento(fecha);
        titular.setNroDocumento(Integer.parseInt(nroDoc));
        titular.setClaseSolicitada(obtenerClaseSolicitada(cmbClaseSolicitada));
        titular.setDonante(obtenerValorDonante(cmbDonante));
        titular.setDireccionFK(direccion);
        
       
       
        //titular.setFkfactorRH(nomDP.getFactorRH(cmbFactorRH));
       //titular.setFkgrupoSanguineo(1);
       //titular.setFktipoDocumento(1);
        
       /*Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR, 1993);
        myCal.set(Calendar.MONTH, 10);
        myCal.set(Calendar.DAY_OF_MONTH, 12);
        Date theDate = myCal.getTime();*/

//Aca guarda la direccion al dar aceptar
       try {
            d.create(direccion);
            JOptionPane.showMessageDialog(null, "Se agregó correctamente la direc");
        } catch (Exception ex) {
            Logger.getLogger(AltaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return true;
    }
    
   
}
