package Logica;

import Control.EntityMan;
import Control.FactorrhJpaController;
import Control.GruposanguineoJpaController;
import Control.TipodocumentoJpaController;
import Entity.Factorrh;
import Entity.Gruposanguineo;
import Entity.Tipodocumento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franc
 */
public class NomencladorDatosPersonales {
    private List<Tipodocumento> listadoTipoDni;
    private List<Factorrh> listadoFactorRH;
    private List<Gruposanguineo> listadoGrupoSanguineo;
    
    private static NomencladorDatosPersonales unSingleton=new NomencladorDatosPersonales ();
    private NomencladorDatosPersonales (){
        inicializar();
    }
    public static NomencladorDatosPersonales getInstance(){
    return unSingleton;}
    
       /*metodo inicializador*/
     
      private void inicializar(){
          listadoTipoDni=obtenerTipoDni();
          listadoFactorRH=obtenerFactorRH();
          listadoGrupoSanguineo=obtenerGrupoSanguineo();
     
      } 
      
    /*metodos que obtienen los objetos de la BD*/
      private List <Tipodocumento> obtenerTipoDni(){
          TipodocumentoJpaController t= new TipodocumentoJpaController(EntityMan.getInstance());
          List<Tipodocumento> returno= new ArrayList<>();
            for (Tipodocumento unTipo:  t.findTipodocumentoEntities()){
                returno.add(unTipo);
            }
            return returno;
      }
        private List <Factorrh> obtenerFactorRH(){
            FactorrhJpaController t= new FactorrhJpaController(EntityMan.getInstance());
            List<Factorrh> returno= new ArrayList<>();
                for (Factorrh unaPosicion:  t.findFactorrhEntities()){
                    returno.add(unaPosicion);
                }
                return returno;
     }
         private List <Gruposanguineo> obtenerGrupoSanguineo(){
        GruposanguineoJpaController t= new  GruposanguineoJpaController(EntityMan.getInstance());
        List<Gruposanguineo> returno= new ArrayList<>();
                for ( Gruposanguineo unaOcup:  t.findGruposanguineoEntities()){
                    returno.add(unaOcup);
                    
                }
                return returno;
     }
         
        /*metodos nomencladores de Objetos*/
     public List<Tipodocumento> getObjetosTipoDNI() {
     return listadoTipoDni;}
     public List<Factorrh> getObjetosFactorRH() {
     return listadoFactorRH;}
     public List<Gruposanguineo> getObjetosGrupoSanguineo() {
     return listadoGrupoSanguineo;}

     
     
    /*metodos nomencladores de String para COMBOBOX*/
    public List<String> getListadoTipoDni(){
        List <String> r=new ArrayList();
    
         for ( Tipodocumento unTipo: listadoTipoDni ){
                    r.add(unTipo.getDescripcion());}
         return r;
    }
   public List<String> getListadoFactorRH(){
        List <String> r=new ArrayList();
    
         for ( Factorrh unaNac: listadoFactorRH ){
                    r.add(unaNac.getFactor());}
         return r;
    }
   
    public List<String> getListadoGrupoSanguineo(){
        List <String> r=new ArrayList();
    
         for ( Gruposanguineo unaPos: listadoGrupoSanguineo){
                    r.add(unaPos.getGrupo());}
         return r;
    }
    
    //metodos que retornan la instancia del objeto a partir de su string descripcion
    public Tipodocumento getTipoDocumento(String unTipo){
        for( Tipodocumento tipo: listadoTipoDni){
        if(tipo.getDescripcion().equals(unTipo)){
            return tipo;
        }     
    }
    return null;}
    
       public Factorrh getFactorRH(String unaPos){
        for( Factorrh pos: listadoFactorRH){
        if(pos.getFactor().equals(unaPos)){
            return pos;
        }     
    }
    return null;}
       
          public Gruposanguineo getGrupoSanguineo(String unaNac){
        for( Gruposanguineo gs: listadoGrupoSanguineo){
        if(gs.getGrupo().equals(unaNac)){
            return gs;
        }     
    }
    return null;}
          

}
