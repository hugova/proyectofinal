/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Vista.frmLogin;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 *
 * 
 */
public class DataBase {

    
    public static String user;
    public static String password;
    public static boolean status = false;
     private final String url = "jdbc:sqlserver://HYUG-PC:1433;databaseName=DB_Ruiz;";
   // private final String url = "jdbc:sqlserver://HYUG-PC:1433;databaseName=DB_Ruiz;user=admin;password=123;";
    
    private Connection conexion = null;
    private Statement sql;
    private ResultSet datos;

    public ResultSet getDatos() {
        return datos;
    }
    
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

 
    
  
    
    private boolean cargarControlador(){
    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return true;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error...No se cargó el controlador de Bases de Datos " + ex.getMessage());
            return false;
        }
    }
         
    public boolean conectar(){
        this.cargarControlador();
            status = false;
        try {
            this.conexion = (Connection) DriverManager.getConnection(this.url, DataBase.user, DataBase.password);
            this.sql= this.conexion.createStatement();
            System.out.println("CONECTADO!");
            status = true;
            return true;
        } catch (SQLException ex) {
            
                 JOptionPane.showMessageDialog(null, "Error...No hay conexión. Intente Nuevamente " );
                 //+ ex.getMessage());   
                    
            
            return false;
        }
    }
    
    public void desconectar(){
        try {
            this.sql.close();
            this.conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión con la Base de Datos " + ex.getMessage());
        }
    }
      public static void setcuenta(String user , String password){
       DataBase.user = user;
       DataBase.password = password;
    }
    public void ejecutarSqlSelect(String consulta){
        this.conectar();
        try {
            this.datos=this.sql.executeQuery(consulta);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error al ejecutar consulta Select " + ex.getMessage());
        }
        //this.desconectar();   
    }
    
    public void ejecutarSqlUpdate(String sql){
        this.conectar();
        try {
            this.sql.executeUpdate(sql);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error al ejecutar consulta de actualización de datos " + ex.getMessage());
        }
        //this.desconectar();
    }
    public static boolean getstatus(){
        return status;
    }
   
    public ResultSet obtenerRegistro(){
        try {
            this.datos.next();
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.datos;
    }   
}
