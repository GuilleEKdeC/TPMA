/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Control.EntityMan;
import java.awt.Image;
import java.awt.Label;
import javax.persistence.EntityManagerFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Victoria
 */

public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        EntityManagerFactory instance = EntityMan.getInstance();
        initComponents();
        crearImagen(lbImagen1,"src/Imagenes/usuario-registrado.png",170,150);
        crearImagen(lbImagen2,"src/Imagenes/licencia.jpg",160,150);
        crearImagen(lbImagen3,"src/Imagenes/imprimir.jpg",160,150);        
        crearImagen(lbImagen4,"src/Imagenes/salir.jpg",160,150);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDarAltaUsuario = new javax.swing.JButton();
        btnEmitirLicencia = new javax.swing.JButton();
        btnImprimirLicencia = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lbImagen1 = new javax.swing.JLabel();
        lbImagen2 = new javax.swing.JLabel();
        lbImagen3 = new javax.swing.JLabel();
        lbImagen4 = new javax.swing.JLabel();
        lbTitulo = new javax.swing.JLabel();
        btnVolverComoSolicitante = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("¡Bienvenidos!");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.white);

        btnDarAltaUsuario.setText("Dar Alta a un Usuario");
        btnDarAltaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarAltaUsuarioActionPerformed(evt);
            }
        });

        btnEmitirLicencia.setText("Emitir Licencia");
        btnEmitirLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmitirLicenciaActionPerformed(evt);
            }
        });

        btnImprimirLicencia.setText("Imprimir Licencia");
        btnImprimirLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirLicenciaActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir del Programa");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lbImagen1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbImagen2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImagen2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbImagen3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImagen3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbImagen4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImagen4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbTitulo.setFont(new java.awt.Font("Sitka Small", 3, 14)); // NOI18N
        lbTitulo.setText("Secretaría de Trasporte - Municipalidad de Santa Fe");

        btnVolverComoSolicitante.setText("Volver al sistema para Solicitantes");
        btnVolverComoSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverComoSolicitanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(lbImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnDarAltaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEmitirLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbImagen3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(btnImprimirLicencia, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbImagen4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(lbTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolverComoSolicitante)
                .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbImagen4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDarAltaUsuario)
                    .addComponent(btnEmitirLicencia)
                    .addComponent(btnImprimirLicencia)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolverComoSolicitante)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDarAltaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarAltaUsuarioActionPerformed
       // AltaTitular at = new AltaTitular();
        //at.setVisible(true);
        AltaUsuario au = new AltaUsuario();
        au.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDarAltaUsuarioActionPerformed

    private void btnEmitirLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirLicenciaActionPerformed
        EmitirLicencia intEmitirLicencia = new EmitirLicencia();
        intEmitirLicencia.setVisible(true);
    }//GEN-LAST:event_btnEmitirLicenciaActionPerformed

    private void btnImprimirLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirLicenciaActionPerformed
        ImprimirLicencia intImprimirLicencia = new ImprimirLicencia();
        intImprimirLicencia.setVisible(true);
    }//GEN-LAST:event_btnImprimirLicenciaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
        //this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnVolverComoSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverComoSolicitanteActionPerformed
        //AltaTitular intAltaTitular = new AltaTitular();
        MenuPrincipalTitulares mpt = new MenuPrincipalTitulares();
        mpt.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverComoSolicitanteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDarAltaUsuario;
    private javax.swing.JButton btnEmitirLicencia;
    private javax.swing.JButton btnImprimirLicencia;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVolverComoSolicitante;
    private javax.swing.JLabel lbImagen1;
    private javax.swing.JLabel lbImagen2;
    private javax.swing.JLabel lbImagen3;
    private javax.swing.JLabel lbImagen4;
    private javax.swing.JLabel lbTitulo;
    // End of variables declaration//GEN-END:variables

    private void crearImagen(JLabel lblLabel, String url,int ancho, int alto){
        ImageIcon imagen4 = new ImageIcon(url);
        Image conversion4 = imagen4.getImage();
        Image tamanio4=conversion4.getScaledInstance(ancho, alto,Image.SCALE_SMOOTH);
        ImageIcon fin4 = new ImageIcon(tamanio4);
        lblLabel.setIcon(fin4);
        lblLabel.getBorder();}

    }

