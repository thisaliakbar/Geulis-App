/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package features;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelHeaderTable;
import model.ModelPemesanan;
import model.ModelRenderTable;
import service.Koneksi;

/**
 *
 * @author usER
 */
public class PilihPemesanan extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private Connection connection;
    private DefaultTableModel tabemodel2;
    public ModelPemesanan modelPemesanan;
    public PilihPemesanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        connection = Koneksi.getConnection();
                
        styleTable(scroll2, table2, 5);
        tabemodel2 = (DefaultTableModel) table2.getModel();
        tabemodel2.setRowCount(0);
        tampilData();
        modelPemesanan = new ModelPemesanan();
        TableRowSorter rowSorter = new TableRowSorter<>(tabemodel2);
        table2.setRowSorter(rowSorter);
        
        search(txtCari2, rowSorter);
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        JPanel pn = new JPanel();
        pn.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, pn);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
        table.setRowHeight(35);
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }
        
//  Tampil Data Tindakan
    private void tampilData() {
        String query = "SELECT No_Pemesanan, Tanggal, Total_Pemesanan, Keterangan FROM pemesanan";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemesanan = rst.getString("No_Pemesanan");
                String tglPemesanan = rst.getString("Tanggal");
                int total = rst.getInt("Total_Pemesanan");
                String keterangan = rst.getString("Keterangan");
                tabemodel2.addRow(new Object[]{noPemesanan, tglPemesanan, total, keterangan});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }    
    
//  Pilih Tindakan
    private void pilihPemesanan() {
        int selectRow = table2.getSelectedRow();
        if(selectRow != -1) {
            String noPemesanan = (String) table2.getValueAt(selectRow, 0);
            int total = (int) table2.getValueAt(selectRow, 2);
            modelPemesanan.setNoPemesanan(noPemesanan);
            modelPemesanan.setTotal(total);
            dispose();
        } else {
            JOptionPane.showMessageDialog(panel2, "Silahkan Pilih Pemesanan Terlebih Dahulu");
        }
    }
    
//    Cari tindakan
    private void search(JTextField field, TableRowSorter rowSorter) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                } 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponents(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        scroll2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        label2 = new javax.swing.JLabel();
        button2 = new swing.Button();
        txtCari2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        table2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No Pemesanan", "Tanggal", "Total", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(table2);

        label2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label2.setForeground(new java.awt.Color(135, 15, 50));
        label2.setText("Data Pemesanan Barang");

        button2.setBackground(new java.awt.Color(135, 15, 50));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Pilih");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        txtCari2.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari2.setForeground(new java.awt.Color(185, 185, 185));
        txtCari2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari2.setText("Cari Berdasarkan No Pemesanan atau Tanggal");
        txtCari2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCari2.setOpaque(false);
        txtCari2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCari2FocusGained(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll2)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(panel2, "card2");

        add(panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    
    private void focusGained(JTextField txtField) {
        txtField.setText(null);
        txtField.setFont(new Font("sansserif", 0, 14));
        txtField.setForeground(new Color(0, 0,0));
    }
    
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        pilihPemesanan();
    }//GEN-LAST:event_button2ActionPerformed

    private void txtCari2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCari2FocusGained
        focusGained(txtCari2);
    }//GEN-LAST:event_txtCari2FocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PilihPemesanan dialog = new PilihPemesanan(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button button2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JTable table2;
    private javax.swing.JTextField txtCari2;
    // End of variables declaration//GEN-END:variables

}
