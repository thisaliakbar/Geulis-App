/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.TableAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelHeaderTable;
import model.ModelPengguna;
import model.ModelRenderTable;
import service.ServicePengguna;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturPengguna extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private TableRowSorter<DefaultTableModel> rowSorter;
    private DefaultTableModel tabmodel;
    private TableAction action;
    private ServicePengguna servicePengguna = new ServicePengguna(); 
    public FiturPengguna() {
        initComponents();
        
        scrollPane.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scrollPane.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(5));
        tabmodel = (DefaultTableModel) table.getModel();
        rowSorter = new TableRowSorter<>(tabmodel);
        table.setRowSorter(rowSorter);
        tampilData();
        tampilLevel();
        actionRenderTable();
        cariData();
    }
    
//  Update,Delete,Detail
    private void actionRenderTable() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            changePanel(panelTambah);
            setComponentUpdate(row);
            btnSimpan.setText("PERBARUI");
        }

        @Override
        public void delete(int row) {
            if(table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            if(JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menghapusnya", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            hapusData(row);  
            tabmodel.removeRow(row);
            }
        }

        @Override
        public void view(int row) {

        }
    };        
        table.getColumnModel().getColumn(5).setCellRenderer(new TableCellActionRender(true, true, false));
        table.getColumnModel().getColumn(5).setCellEditor(new TableCellEditor(action, true, true, false));
    }
    
    private void tambahData(){
    String IdPengguna = TFIdPengguna.getText();
    String NamaPengguna = TFNamaPengguna.getText();
    String UsernamePengguna = TFUsernamePengguna.getText();
    String PasswordPengguna = TFPasswordPengguna.getText();
    String EmailPengguna = TFEmailPengguna.getText();
    String LevelPengguna = (String)cbxLevelPengguna.getSelectedItem();
    
    ModelPengguna modelPengguna = new ModelPengguna(IdPengguna, NamaPengguna, UsernamePengguna, PasswordPengguna, EmailPengguna, LevelPengguna);
    servicePengguna.addData(modelPengguna);
     }
     
    private void setComponentUpdate(int row){
        TFIdPengguna.setText((String) table.getValueAt(row, 0));
        TFNamaPengguna.setText((String) table.getValueAt(row, 1));
        TFUsernamePengguna.setText((String) table.getValueAt(row, 2));
        lbPassword.setVisible(false);
        TFPasswordPengguna.setVisible(false);
        TFEmailPengguna.setText((String) table.getValueAt(row, 3));
        cbxLevelPengguna.setSelectedItem((String)table.getValueAt(row, 4));
    }
    
      private void perbaruiData(){
        String IdPengguna = TFIdPengguna.getText();
        String NamaPengguna = TFNamaPengguna.getText();
        String UsernamePengguna = TFUsernamePengguna.getText();
        String PasswordPengguna = TFPasswordPengguna.getText();
        String EmailPengguna = TFEmailPengguna.getText();
        String LevelPengguna = (String)cbxLevelPengguna.getSelectedItem();
        ModelPengguna modelPengguna =new ModelPengguna(IdPengguna, NamaPengguna, UsernamePengguna, PasswordPengguna, EmailPengguna, LevelPengguna);
        servicePengguna.updateData(modelPengguna);
      } 
    
      
      private void hapusData(int row){
          String IdPengguna = (String) table.getValueAt(row, 0);
          ModelPengguna modelPengguna = new ModelPengguna ();
          modelPengguna.setIdpengguna(IdPengguna);
          servicePengguna.deleteData(modelPengguna);
          
        
    }
      
      private boolean validationAddData(){
        boolean valid = false;
        if(TFIdPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "ID Pengguna Tidak Boleh Kosong");
        }else if (TFNamaPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Nama Pengguna Tidak Boleh Kosong");
        }else if (TFUsernamePengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Username Pengguna Tidak Boleh Kosong");
        }else if (TFPasswordPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Password Pengguna Tidak Boleh Kosong");
        }else if (TFEmailPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Email Pengguna Tidak Boleh Kosong");   
        }else{
            valid = true;
        }
        
        return valid;
    }
      
      private boolean validationUpdateData(){
        boolean valid = false;
        if(TFIdPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "ID Pengguna Tidak Boleh Kosong");
        }else if (TFNamaPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Nama Pengguna Tidak Boleh Kosong");
        }else if (TFUsernamePengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Username Pengguna Tidak Boleh Kosong");
        }else if (TFEmailPengguna.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Email Pengguna Tidak Boleh Kosong");   
        }else{
            valid = true;
        }
        
        return valid;
    }
      
    private void clearField(){
        TFIdPengguna.setText(null);
        TFNamaPengguna.setText(null);
        TFUsernamePengguna.setText(null);
        TFPasswordPengguna.setText(null);
        TFEmailPengguna.setText(null);
    }
    private void tampilData(){
        servicePengguna.loadData(1, tabmodel, pagination);
        pagination.addActionPagination(new action.ActionPagination() {
            @Override
            public void pageChanged(int page) {
                 servicePengguna.loadData(page, tabmodel, pagination);
            }
        });
    }
    
    private void cariData() {
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
    private void tampilLevel() {
        String[] levels = new String[]{"Owner", "Admin"};
        for(String level : levels) {
            cbxLevelPengguna.addItem(level);
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

        panelData = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnTambah = new swing.Button();
        label = new javax.swing.JLabel();
        pagination = new swing.Pagination();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        panelTambah = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        btnSimpan = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        TFIdPengguna = new javax.swing.JTextField();
        TFPasswordPengguna = new javax.swing.JTextField();
        TFNamaPengguna = new javax.swing.JTextField();
        TFUsernamePengguna = new javax.swing.JTextField();
        cbxLevelPengguna = new javax.swing.JComboBox<>();
        TFEmailPengguna = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        table.setBackground(new java.awt.Color(255, 255, 255));
        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengguna", "Nama Pengguna", "Username", "Email", "Level", "Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(185, 185, 185));
        table.setOpaque(false);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setViewportView(table);

        btnTambah.setBackground(new java.awt.Color(135, 15, 50));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("TAMBAH");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(135, 15, 50));
        label.setText("PENGGUNA");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan ID Pasien Atau Nama Pasien");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 654, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(label)
                            .addGap(0, 933, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 522, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addGap(63, 63, 63)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        add(panelData, "card2");

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnSimpan.setBackground(new java.awt.Color(135, 15, 50));
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("SIMPAN");
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nama");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("ID Pengguna");

        lbPassword.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbPassword.setForeground(new java.awt.Color(0, 0, 0));
        lbPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPassword.setText("Password");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Username");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Level");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Email");

        btnBatal.setBackground(new java.awt.Color(0, 153, 0));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("BATAL");
        btnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        TFIdPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFIdPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFPasswordPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFPasswordPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFNamaPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFNamaPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFUsernamePengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFUsernamePengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        cbxLevelPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbxLevelPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFEmailPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFEmailPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TFIdPengguna)
                    .addComponent(TFPasswordPengguna)
                    .addComponent(TFNamaPengguna)
                    .addComponent(TFUsernamePengguna)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(cbxLevelPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 289, Short.MAX_VALUE))
                    .addComponent(TFEmailPengguna))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFIdPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFNamaPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFUsernamePengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFPasswordPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFEmailPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxLevelPengguna)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Pengguna");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(478, Short.MAX_VALUE)
                .addComponent(label1)
                .addContainerGap(477, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTambahLayout = new javax.swing.GroupLayout(panelTambah);
        panelTambah.setLayout(panelTambahLayout);
        panelTambahLayout.setHorizontalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        changePanel(panelTambah);
        clearField();
        TFIdPengguna.setEnabled(false);
        TFIdPengguna.setText(servicePengguna.createId());
        lbPassword.setVisible(true);
        TFPasswordPengguna.setVisible(true);
        cbxLevelPengguna.setSelectedItem("Owner");
        btnSimpan.setText("SIMPAN");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(btnSimpan.getText().equals("SIMPAN")) {
        if(validationAddData()) {
            tambahData();          
            changePanel(panelData); 
        }   
        } else {
            if(validationUpdateData()) {
            perbaruiData();
            changePanel(panelData); 
            }
        }
        tabmodel.setRowCount(0);
        tampilData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
    }//GEN-LAST:event_txtCariFocusGained

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearField();
        tabmodel.setRowCount(0);
        tampilData();
        changePanel(panelData);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void changePanel(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void characterDigit(KeyEvent evt) {
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed)) {
            evt.consume();
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TFEmailPengguna;
    private javax.swing.JTextField TFIdPengguna;
    private javax.swing.JTextField TFNamaPengguna;
    private javax.swing.JTextField TFPasswordPengguna;
    private javax.swing.JTextField TFUsernamePengguna;
    private swing.Button btnBatal;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private javax.swing.JComboBox<String> cbxLevelPengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbPassword;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
