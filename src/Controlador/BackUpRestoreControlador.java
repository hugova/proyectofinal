/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import JavaBeans.clsBackUpRestore;
import Modelo.DataBase;
import Modelo.ModeloBackUpRestore;
import Vista.frmRrestoreBackup;

import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static java.awt.image.ImageObserver.ERROR;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Hugo Villegas Arroyo
 */
public class BackUpRestoreControlador implements ActionListener, WindowListener, KeyListener{
    private frmRrestoreBackup moduloRestoreBackup;
    public frmPrincipal moduloPrincipal;
    private clsBackUpRestore backUpRestore;
    private ModeloBackUpRestore modeloBackUpRestore;
    String path;
    String filename;

    public BackUpRestoreControlador(frmRrestoreBackup moduRestoreBackup, frmPrincipal modPrincipal, clsBackUpRestore backUpRestore, ModeloBackUpRestore modeloBackUpRostore) {
        this.moduloRestoreBackup = moduRestoreBackup;
        this.moduloPrincipal = modPrincipal;
        //this.moduloRestoreBackup.jFileChooser1.addActionListener(this);
        this.backUpRestore = backUpRestore;
        this.modeloBackUpRestore = modeloBackUpRostore;

        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnBackUpRestore.addActionListener(this);
        // Botones mapeados para el la vista del chofer
        this.moduloRestoreBackup.btnBackUp.addActionListener(this);
        this.moduloRestoreBackup.btnBrowseBackUp.addActionListener(this);
        this.moduloRestoreBackup.btnBrowseRestore.addActionListener(this);
        this.moduloRestoreBackup.btnRestore.addActionListener(this);

 
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
         JFileChooser file = new JFileChooser();  
    if(e.getSource()== moduloPrincipal.btnBackUpRestore){
        moduloRestoreBackup.setVisible(true);
        }
    
        if (e.getSource() == moduloRestoreBackup.btnBrowseBackUp) {
           
           // if (e.getSource() == moduloRestoreBackup.jFileChooser1) {

                try {

                   // File f = moduloRestoreBackup.jFileChooser1.getSelectedFile();
                    //path= moduloRestoreBackup.jFileChooser1.getCurrentDirectory();

                    
                   
                   
                    file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = file.showOpenDialog(null);
                    if (returnVal == file.APPROVE_OPTION) {
                       
                        moduloRestoreBackup.txtbackup.setText(file.getSelectedFile().getAbsolutePath()+".bak");
                        backUpRestore.setRuta(moduloRestoreBackup.txtbackup.getText());
                        System.out.println("You chose to open this directory: " + file.getSelectedFile().getAbsolutePath()+".bak");
}
                 //   if (returnVal == moduloRestoreBackup.jFileChooser1.APPROVE_OPTION) {

                       // path = moduloRestoreBackup.jFileChooser1.getSelectedFile().getName();
                        //path = String.valueOf(f.getAbsolutePath()) ;
                        // path = path.replace('\\', '/');
                     
                        // path = path + "_" + date + ".bak";
                        

                    //}
                } catch (Exception ex) {
                    ex.getMessage();
                }

            }

        //}
        if (e.getSource() == moduloRestoreBackup.btnBackUp) {
            if (modeloBackUpRestore.crearBackup(file.getSelectedFile().getAbsolutePath()+".bak")) {
                JOptionPane.showMessageDialog(moduloRestoreBackup, "Se hizo el BackUp sin Errores"+file.getSelectedFile().getAbsolutePath()+".bak");
            } else {
                JOptionPane.showMessageDialog(moduloRestoreBackup, "Error al intentar hacer el BackUp");
            }
        }
    
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
