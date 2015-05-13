/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import Modelo.UML.Centro;
import Modelo.UML.Localidad;
import Modelo.UML.Via;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import proyectoudaleku.Main;


/**
 *
 * @author 1gprog07
 */
public class panLupa extends javax.swing.JFrame {

    private ArrayList<Localidad> localidades;


    /**
     * Creates new form panelLupa
     */
    public panLupa() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public panLupa(String tipo, String prov) {
        initComponents();
        setLocationRelativeTo(null);
        lTitulo.setText("MUNICIPIOS DE"); lProv.setText(prov.toUpperCase());

    public panLupa(String tipo, String loc) {
        initComponents();
        setLocationRelativeTo(null);
        lTitulo.setText(tipo.toUpperCase()); lProv.setText(loc.toUpperCase());
        switch(tipo){
            case "calles":
                DefaultListModel modeloCalles = new DefaultListModel();
                for(int x=0;x<Main.getVias().size();x++)
                {
                    modeloCalles.add(x, Main.getVias().get(x).getNombrevia());
                }
                list.setModel(modeloCalles);
                break;
                
            case "centros":
                DefaultListModel modeloCentros = new DefaultListModel();
                for(int x=0;x<Main.getCentros().size();x++)
                {
                    modeloCentros.add(x, Main.getCentros().get(x).getNombrecent());
                }
                list.setModel(modeloCentros);
                break;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
<<<<<<< HEAD
        jList1 = new javax.swing.JList();
        lTitulo = new javax.swing.JLabel();
        lProv = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setViewportView(jList1);

        lTitulo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lTitulo.setText("MUNICIPIOS DE");
=======
        list = new javax.swing.JList();
        lTitulo = new javax.swing.JLabel();
        lProv = new javax.swing.JLabel();
        bAceptar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        lDe = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setViewportView(list);

        lTitulo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lTitulo.setText("CALLES");
>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc

        lProv.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lProv.setText("ARABA");

<<<<<<< HEAD
=======
        bAceptar.setText("Aceptar");
        bAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAceptarActionPerformed(evt);
            }
        });

        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        lDe.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lDe.setText("DE");

>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
<<<<<<< HEAD
            .addGroup(layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(lTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lProv)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
=======
>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
<<<<<<< HEAD
=======
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(lTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lProv))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(bAceptar)
                        .addGap(224, 224, 224)
                        .addComponent(bCancelar)))
                .addContainerGap(233, Short.MAX_VALUE))
>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTitulo)
<<<<<<< HEAD
                    .addComponent(lProv))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(42, 42, 42))
=======
                    .addComponent(lProv)
                    .addComponent(lDe))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAceptar)
                    .addComponent(bCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Main.cancelarLupa();
    }//GEN-LAST:event_bCancelarActionPerformed

    private void bAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAceptarActionPerformed
        switch (lTitulo.getText()){
            case "CALLES":
                // El ArrayList de municipios del Main es coincidente con esta lista. Así sabemos qué municipio seleccionamos.
                Main.setViaSelected(Main.getVias().get(list.getSelectedIndex()));
                Main.sendViaToInscripcion();
                break;
            case "CENTROS":
                // El ArrayList de centros del Main es coincidente con esta lista. Así sabemos qué municipio seleccionamos.
                Main.setCentSelected(Main.getCentros().get(list.getSelectedIndex()));
                System.out.println(Main.getCentSelected().getNombrecent());
                break;
        }
    }//GEN-LAST:event_bAceptarActionPerformed


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
            java.util.logging.Logger.getLogger(panLupa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panLupa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panLupa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panLupa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panLupa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
<<<<<<< HEAD
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lProv;
    private javax.swing.JLabel lTitulo;
=======
    private javax.swing.JButton bAceptar;
    private javax.swing.JButton bCancelar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lDe;
    private javax.swing.JLabel lProv;
    private javax.swing.JLabel lTitulo;
    private javax.swing.JList list;
>>>>>>> c9946228d182cd16818acfb1503e9d13b7b1acfc
    // End of variables declaration//GEN-END:variables
}
