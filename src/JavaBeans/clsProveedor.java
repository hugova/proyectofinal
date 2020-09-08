/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

import java.sql.Date;

/**
 *
 * @author Darío
 */
public class clsProveedor {
    private String IDPROVEEDOR;
    String NOMBRE;
    String DIRECCION; 
    String TELEFONO; 
    String CORREO_ELECTRONICO; 
    Date FECHA_REGISTRO; 
    String PAIS; 
    String CEDULA_JURIDICA;

    public clsProveedor(String IDPROVEEDOR, String NOMBRE, String DIRECCION, String TELEFONO, String CORREO_ELECTRONICO, Date FECHA_REGISTRO, String PAIS, String CEDULA_JURIDICA) {
        this.IDPROVEEDOR = IDPROVEEDOR;
        this.NOMBRE = NOMBRE;
        this.DIRECCION = DIRECCION;
        this.TELEFONO = TELEFONO;
        this.CORREO_ELECTRONICO = CORREO_ELECTRONICO;
        this.FECHA_REGISTRO = FECHA_REGISTRO;
        this.PAIS = PAIS;
        this.CEDULA_JURIDICA = CEDULA_JURIDICA;
    }
    
    public clsProveedor() {
        this.IDPROVEEDOR = "";
        this.NOMBRE = "";
        this.DIRECCION = "";
        this.TELEFONO = "";
        this.CORREO_ELECTRONICO = "";
        this.FECHA_REGISTRO = null;
        this.PAIS = "";
        this.CEDULA_JURIDICA ="";
    }
    
    public String getIDPROVEEDOR() {
        return IDPROVEEDOR;
    }

    public void setIDPROVEEDOR(String IDPROVEEDOR) {
        this.IDPROVEEDOR = IDPROVEEDOR;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getCORREO_ELECTRONICO() {
        return CORREO_ELECTRONICO;
    }

    public void setCORREO_ELECTRONICO(String CORREO_ELECTRONICO) {
        this.CORREO_ELECTRONICO = CORREO_ELECTRONICO;
    }

    public Date getFECHA_REGISTRO() {
        return FECHA_REGISTRO;
    }

    public void setFECHA_REGISTRO(Date FECHA_REGISTRO) {
        this.FECHA_REGISTRO = FECHA_REGISTRO;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    public String getCEDULA_JURIDICA() {
        return CEDULA_JURIDICA;
    }

    public void setCEDULA_JURIDICA(String CEDULA_JURIDICA) {
        this.CEDULA_JURIDICA = CEDULA_JURIDICA;
    }
}
