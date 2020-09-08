/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsBus;
import JavaBeans.clsChofer;
import JavaBeans.clsProveedor;
import Modelo.DataBase;
import Modelo.ModeloProveedor;
import Modelo.ModeloChofer;
import Vista.frmAgregarProveedor;
import Vista.frmModuloProveedores;
import Vista.frmModuloChofer;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Manyor
 */
public class ProveedorControlador implements ActionListener, WindowListener, KeyListener {

    private frmModuloProveedores moduloProveedor;
    public frmPrincipal moduloPrincipal;
    private frmAgregarProveedor agregarProveedor;
    private clsProveedor proveedor;
    private ModeloProveedor modeloProveedor;

    DefaultTableModel modelT;

    public ProveedorControlador(frmModuloProveedores modProveedor, frmPrincipal modPrincipal, frmAgregarProveedor agProveedor, clsProveedor proveedor, ModeloProveedor modelProveedor) {
        this.moduloProveedor = modProveedor;
        this.moduloPrincipal = modPrincipal;
        this.agregarProveedor = agProveedor;
        this.proveedor = proveedor;
        this.modeloProveedor = modelProveedor;

        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnModChoferes.addActionListener(this);
        // Botones mapeados para el la vista del chofer
        this.moduloProveedor.btnAgregar.addActionListener(this);
        this.moduloProveedor.btnEditar.addActionListener(this);
        this.moduloProveedor.btnEliminar.addActionListener(this);
        this.moduloProveedor.addWindowListener(this);
        this.moduloProveedor.txtBuscar.addKeyListener(this);

        //Botones mapeados con Listener modulo Agregar bus Vista
        this.agregarProveedor.btnGuardar.addActionListener(this);
        this.agregarProveedor.btnCancelar.addActionListener(this);
        this.agregarProveedor.btnLimpiar.addActionListener(this);
        this.agregarProveedor.txtFechaingreso.addKeyListener(this);
        this.agregarProveedor.txtCorreoElectronico.addKeyListener(this);
    }
    java.util.Date date = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnModProveedor) {
            moduloProveedor.setVisible(true);
        }
        // Acción para el botón que abre el módulo del agregar proveedor. 
        if (e.getSource() == moduloProveedor.btnAgregar) {
            agregarProveedor.setVisible(true);
            agregarProveedor.setTitle("Agregar Proveedor");
            agregarProveedor.txtId.setText(String.valueOf(moduloProveedor.tblDatos.getModel().getRowCount() + 1));
            agregarProveedor.txtFechaingreso.setText(sqlDate.toString());
            limpiarVistaNuevo();

//
//            java.util.Date date = new java.util.Date();
//            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//            agregarProveedor.txtFechaingreso.setText(sqlDate.toString());
        }
        // Acción para el botón guardar dentro del form agregarProveedor proveedor. 
        if (e.getSource() == agregarProveedor.btnGuardar) {
            if (validarTexto()) {

                proveedor.setIDPROVEEDOR(agregarProveedor.txtId.getText());
                proveedor.setNOMBRE(agregarProveedor.txtNombre.getText());
                proveedor.setDIRECCION(agregarProveedor.txtDireccion.getText());
                proveedor.setTELEFONO(agregarProveedor.txtTelefono.getText());
                proveedor.setCORREO_ELECTRONICO(agregarProveedor.txtCorreoElectronico.getText());
                proveedor.setFECHA_REGISTRO(sqlDate);
//                try {
//                    Date date1 = (Date) formatter1.parse(sDate1);
//                    
//                } catch (ParseException ex) {
//                    Logger.getLogger(ProveedorControlador.class.getName()).log(Level.SEVERE, null, ex);
//                }
                proveedor.setPAIS(agregarProveedor.txtPais.getText());
                proveedor.setCEDULA_JURIDICA(agregarProveedor.txtCedulJuridica.getText());
                if (agregarProveedor.getTitle().equals("Agregar Proveedor")) {
                    //Agregar chofer
                    if (modeloProveedor.insertarProveedor(proveedor)) {
                        JOptionPane.showMessageDialog(agregarProveedor, "Proveedor Registrado");
                    } else {
                        JOptionPane.showMessageDialog(agregarProveedor, "Error al guardar");
                    }
                } else {
                    //Editar CHofer
                    if (!modeloProveedor.editarProveedor(proveedor)) {
                        JOptionPane.showMessageDialog(agregarProveedor, "Proveedor actualizado con exito");
                        agregarProveedor.dispose();

                    } else {
                        JOptionPane.showMessageDialog(agregarProveedor, "Error al guardar");
                    }

                }

            } else {
                JOptionPane.showMessageDialog(agregarProveedor, "Debe completar los campos solicitados");

            }

        }
        // Acción para el botón que limpia  la pantalla en el form agregarchofer. 
        if (e.getSource() == agregarProveedor.btnLimpiar) {
            limpiarVistaNuevo();
        }
//        // Acción para el botón que cancela el modo de agregación del chofer. 
        if (e.getSource() == agregarProveedor.btnCancelar) {
            agregarProveedor.dispose();
        }

        if (e.getSource().equals(moduloProveedor.btnEditar)) {
            if (moduloProveedor.tblDatos.getSelectedRowCount() > 0) {
                agregarProveedor.setTitle("Editar Proveedor");
                agregarProveedor.txtId.setEnabled(false);
                agregarProveedor.txtId.setText(proveedor.getIDPROVEEDOR());
                agregarProveedor.txtNombre.setText(proveedor.getNOMBRE());
                agregarProveedor.txtDireccion.setText(proveedor.getDIRECCION());
                agregarProveedor.txtTelefono.setText(proveedor.getTELEFONO());
                agregarProveedor.txtCorreoElectronico.setText(proveedor.getCORREO_ELECTRONICO());
                agregarProveedor.txtFechaingreso.setText(String.valueOf(proveedor.getFECHA_REGISTRO()));
                agregarProveedor.txtPais.setText(proveedor.getPAIS());
                agregarProveedor.txtCedulJuridica.setText(proveedor.getCEDULA_JURIDICA());
                agregarProveedor.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(agregarProveedor, "Debe seleccionar al menos una fila para editar");
            }
        }
        if (e.getSource().equals(moduloProveedor.btnEliminar)) {
            if (moduloProveedor.tblDatos.getSelectedRowCount() >= 0) {
                int opcion = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar ese registro ?");
                if (opcion == 0) {
                    if (modeloProveedor.eliminarProveedor(moduloProveedor.tblDatos.getValueAt(moduloProveedor.tblDatos.getSelectedRow(), 0).toString())) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                    } else {
                        System.out.println("No Elimnar");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(agregarProveedor, "Debe seleccionar al menos una fila para eliminar");
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String titulos[] = {"ID", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "FECHA REGISTROS", "PAIS", "CEDULA JURIDICA"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();
        ResultSet rs;

        try {

            // bd.ejecutarSqlSelect("SELECT * FROM TBLProveedor");
            bd.ejecutarSqlSelect("{call paBuscarTodoProveedor()}");
            rs = bd.obtenerRegistro();

            do {
                proveedor = new clsProveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8));
                Object newRow[] = {proveedor.getIDPROVEEDOR(), proveedor.getNOMBRE(), proveedor.getDIRECCION(), proveedor.getTELEFONO(), proveedor.getCORREO_ELECTRONICO(), proveedor.getFECHA_REGISTRO(), proveedor.getPAIS(), proveedor.getCEDULA_JURIDICA()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloProveedor.tblDatos.setModel(modelT);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            System.out.println(ex);
            moduloProveedor.tblDatos.setModel(modelT);
        }

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(agregarProveedor.txtTelefono) || e.getSource().equals(agregarProveedor.txtId)) {
            isDigit(e);
        }
    }

    private void isDigit(KeyEvent e) {
        char tecla;
        tecla = e.getKeyChar();
        if (!Character.isDigit(tecla) && tecla != com.sun.glass.events.KeyEvent.VK_BACKSPACE) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {

        String titulos[] = {"ID", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "FECHA REGISTROS", "PAIS", "CEDULA JURIDICA"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            //modeloProveedor.buscarProveedor(moduloProveedor.txtBuscar.getText());

            bd.ejecutarSqlSelect("{call paBuscarProveedor("+moduloProveedor.txtBuscar.getText()+")}");
            rs = bd.obtenerRegistro();

            do {
                proveedor = new clsProveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8));
                Object newRow[] = {proveedor.getIDPROVEEDOR(), proveedor.getNOMBRE(), proveedor.getDIRECCION(), proveedor.getTELEFONO(), proveedor.getCORREO_ELECTRONICO(), proveedor.getFECHA_REGISTRO(), proveedor.getPAIS(), proveedor.getCEDULA_JURIDICA()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloProveedor.tblDatos.setModel(modelT);

        } catch (SQLException ex) {

        }
    }

    private boolean validarTexto() {
        return !agregarProveedor.txtId.getText().isEmpty()
                && !agregarProveedor.txtDireccion.getText().isEmpty()
                && !agregarProveedor.txtCedulJuridica.getText().isEmpty();

    }

    public void limpiarVistaNuevo() {
        if (agregarProveedor.getTitle().equals("Agregar Proveedor")) {
            //agregarProveedor.txtId.setText(null);
            agregarProveedor.txtId.setEnabled(false);
            agregarProveedor.txtFechaingreso.setEnabled(false);
        }

        agregarProveedor.txtNombre.setText(null);
        agregarProveedor.txtDireccion.setText(null);
        agregarProveedor.txtTelefono.setText(null);
        agregarProveedor.txtCorreoElectronico.setText(null);
        agregarProveedor.txtPais.setText(null);
        agregarProveedor.txtCedulJuridica.setText(null);
    }
    /* 
    private void cargarComboBus() {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        DataBase bd = new DataBase();

        if (!agregarBus.getTitle().equals("Editar Bus")) {
            cmbModel.addElement("Seleccionar.....");
        } else {
            ModeloChofer modeloChofer = new ModeloChofer();
            clsChofer chfer;
            chfer = modeloChofer.buscarChofer(moduloBus.tblDatos.getValueAt(moduloBus.tblDatos.getSelectedRow(), 5).toString());
            cmbModel.addElement(chfer.getCedula() + " " + chfer.getNombre());
        }

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("SELECT chofer.Cedula,chofer.nombre FROM chofer WHERE chofer.Cedula NOT IN (SELECT bus.chofer FROM bus)");
            rs = bd.obtenerRegistro();
            do {
                cmbModel.addElement(rs.getString(1) + " " + rs.getString(2));
            } while (rs.next());
            agregarBus.cmbChoferBus.setModel(cmbModel);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {

            if (!agregarBus.getTitle().equals("Editar Bus")) {
                cmbModel.removeAllElements();
                cmbModel.addElement("No hay choferes disponibles");
            }
            agregarBus.cmbChoferBus.setModel(cmbModel);
        }
    }
     */
}
