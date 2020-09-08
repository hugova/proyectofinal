/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Modelo.DataBase;
import java.sql.CallableStatement;
import java.sql.SQLException;
import JavaBeans.clsBackUpRestore;
/**
 *
 * @author Hugo Villegas Arroyo
 */
public class ModeloBackUpRestore {
    
    public boolean crearBackup (String backup ){
    
    DataBase bd = new DataBase();
    
        try {
            bd.conectar();
            //se prepara la llamada al preocedimiento
            CallableStatement cst
                    = bd.getConexion().prepareCall("{CALL pabackUp(?)}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            cst.setString(1, backup);
          
            // se ejecuta
            cst.execute();
            System.out.println("Se hizo el BackUp Exitosamente");
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(clsProveedor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
      return false;
    
    }
    
}
