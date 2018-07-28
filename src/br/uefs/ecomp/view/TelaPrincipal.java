package br.uefs.ecomp.view;

// @author Eduardo

import br.uefs.ecomp.controller.Controller;
import br.uefs.ecomp.model.Noticia;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends javax.swing.JFrame {
    Controller c = new Controller();
    LinkedList<Noticia> noticias;
    
    public TelaPrincipal() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/logonews.png")).getImage());
        this.inicia();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaNoticias = new javax.swing.JTable();
        lerNoticia = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        fakes = new javax.swing.JButton();
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabelaNoticias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Notícias", "Avaliações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaNoticias.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabelaNoticias);
        if (tabelaNoticias.getColumnModel().getColumnCount() > 0) {
            tabelaNoticias.getColumnModel().getColumn(0).setResizable(false);
            tabelaNoticias.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabelaNoticias.getColumnModel().getColumn(1).setResizable(false);
            tabelaNoticias.getColumnModel().getColumn(1).setPreferredWidth(400);
            tabelaNoticias.getColumnModel().getColumn(2).setResizable(false);
            tabelaNoticias.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        lerNoticia.setBackground(new java.awt.Color(0, 204, 0));
        lerNoticia.setForeground(new java.awt.Color(255, 255, 255));
        lerNoticia.setText("Ler Noticia");
        lerNoticia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lerNoticiaActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/news3.png"))); // NOI18N

        fakes.setBackground(new java.awt.Color(255, 0, 0));
        fakes.setForeground(new java.awt.Color(255, 255, 255));
        fakes.setText("Notícias Fakes");
        fakes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fakesActionPerformed(evt);
            }
        });

        refresh.setBackground(new java.awt.Color(204, 255, 255));
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/refresh.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel2)
                        .addGap(166, 166, 166)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fakes, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(236, 236, 236)
                                .addComponent(lerNoticia, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(refresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lerNoticia)
                    .addComponent(fakes)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lerNoticiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lerNoticiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lerNoticiaActionPerformed

    private void fakesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fakesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fakesActionPerformed

// -----------------------------------------------------------------------//
    public void inicia(){
        this.listarNoticias();
    }
    
    public void listarNoticias(){
        this.noticias = c.getNoticias();
        DefaultTableModel tabela  = (DefaultTableModel) tabelaNoticias.getModel();
        
        if (noticias == null) {
            JOptionPane.showMessageDialog(this, "Não foi possível listar as noticias", "Erro", JOptionPane.ERROR_MESSAGE);
            disableButtons(false); //Método utilizado para desativar/ativar os botões;
        }else{
            disableButtons(true);
            tabela.setRowCount(0);
            
            Iterator itr = noticias.iterator();
            while (itr.hasNext()) {
                Noticia n = (Noticia) itr.next();
                String[] s = {""+n.getId(),n.getTitulo(), ""+n.getNota()};
                tabela.addRow(s);
            }
        }
    }
    
    public void disableButtons(boolean set){
        lerNoticia.setEnabled(set);
        fakes.setEnabled(set);
    }
// -----------------------------------------------------------------------//

    
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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fakes;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton lerNoticia;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tabelaNoticias;
    // End of variables declaration//GEN-END:variables
}
