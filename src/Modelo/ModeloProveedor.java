/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsProveedor;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manyor
 */
public class ModeloProveedor {

    public boolean insertarProveedor(clsProveedor proveedor) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            //se prepara la llamada al preocedimiento
            CallableStatement cst
                    = bd.getConexion().prepareCall("{CALL paInsertarProveedor(?,?,?,?,?,?,?,?)}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            cst.setString(1, proveedor.getIDPROVEEDOR());
            cst.setString(2, proveedor.getNOMBRE());
            cst.setString(3, proveedor.getDIRECCION());
            cst.setString(4, proveedor.getTELEFONO());
            cst.setString(5, proveedor.getCORREO_ELECTRONICO());
            cst.setDate(6, proveedor.getFECHA_REGISTRO());
            cst.setString(7, proveedor.getPAIS());
            cst.setString(8, proveedor.getCEDULA_JURIDICA());
            // se ejecuta
            cst.execute();
            System.out.println("Insertado Exitosamente");
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
//====================== para la busqueda por parametro

    public Boolean buscarProveedor(String pBuscar) {
        Modelo.DataBase bd = new Modelo.DataBase();

        try {
            //se prepara la llamada al preocedimiento
            CallableStatement cst
                    = bd.getConexion().prepareCall("{CALL paBuscarProveedor(?)}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            cst.setString(1, pBuscar);

            cst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(clsProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //======================================para cargar toda la tabla sin parametro
    public String buscarTodoProveedor() {
        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            //se prepara la llamada al preocedimiento
            CallableStatement cst
                    = bd.getConexion().prepareCall("{CALL paBuscarTodoProveedor()}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            // cst.setString(1,"");

            cst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(clsProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";

    }

    public boolean editarProveedor(clsProveedor proveedor) {
        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            //se prepara la llamada al preocedimiento
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call paActualizarProveedor(?,?,?,?,?,?,?,?)}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            cst.setString(1, proveedor.getIDPROVEEDOR());
            cst.setString(2, proveedor.getNOMBRE());
            cst.setString(3, proveedor.getDIRECCION());
            cst.setString(4, proveedor.getTELEFONO());
            cst.setString(5, proveedor.getCORREO_ELECTRONICO());
            cst.setDate(6, proveedor.getFECHA_REGISTRO());
            cst.setString(7, proveedor.getPAIS());
            cst.setString(8, proveedor.getCEDULA_JURIDICA());
            // se ejecuta
            cst.execute();
            System.out.println("actuallizado exitosamente");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Logger.getLogger(ClsFacturas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }

    public boolean eliminarProveedor(String IDproveedor) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            //se prepara la llamada al preocedimiento
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call paEliminarProveedor(?)}");
            // Se definen las entradas(parámetros) de los datos con sus caracteristicas
            cst.setString(1, IDproveedor);
            // se ejecuta
            cst.execute();

            System.out.println("Eliminado Exitosamente");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Logger.getLogger(ClsFacturas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }
}
