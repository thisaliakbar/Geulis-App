/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.ModelCard;

/**
 *
 * @author usER
 */
public class Card extends javax.swing.JPanel {

    public Color getColorGradient() {
        return colorGradient;
    }

    /**
     * Creates new form Card
     */
    public void setColorGradient(Color colorGradient) {
        this.colorGradient = colorGradient;
    }

    private Color colorGradient;
    public Card() {
        initComponents();
        setOpaque(false);
        setBackground(new Color(122, 69, 246));
        colorGradient = new Color(255, 255, 255);
        
    }
    
    public void setData(ModelCard modelCard) {
        DecimalFormat df = new DecimalFormat("#,###");
        lbTitle.setText(modelCard.getTitle());
        lbValues.setText("Rp "+ df.format(modelCard.getValues()));
        lbIcon.setIcon(modelCard.getIcon());
        btnDetail.setText(modelCard.getTextButton());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, getHeight(), getBackground(), getWidth(), 0, colorGradient);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public void setRange(DateChooserAdapter action) {
        DateChooser chooser = new DateChooser();
        chooser.setTextField(txtRange);
        chooser.setLabelCurrentDayVisible(false);
        chooser.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        chooser.setSelectedDate(new Date());
        chooser.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chooser.setBetweenCharacter(" Sampai ");
        chooser.addActionDateChooserListener(action);
    }
    
    public void viewDetail(ActionListener action) {
        btnDetail.addActionListener(action);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbValues = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        lbIcon = new javax.swing.JLabel();
        txtRange = new javax.swing.JTextField();
        btnDetail = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        lbValues.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        lbValues.setForeground(new java.awt.Color(102, 102, 102));
        lbValues.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbValues.setText("VALUES");

        lbTitle.setBackground(new java.awt.Color(102, 102, 102));
        lbTitle.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(102, 102, 102));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Pendapatan Pemeriksaan");

        separator.setBackground(new java.awt.Color(170, 170, 170));

        lbIcon.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbIcon.setForeground(new java.awt.Color(102, 102, 102));
        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sales.png"))); // NOI18N

        txtRange.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtRange.setForeground(new java.awt.Color(102, 102, 102));
        txtRange.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRange.setText("2024-03-28 Sampai 2024-04-28");
        txtRange.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(170, 170, 170)));
        txtRange.setOpaque(false);

        btnDetail.setBackground(new java.awt.Color(153, 153, 153));
        btnDetail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnDetail.setText("Lihat Detail");
        btnDetail.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(lbValues, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(txtRange)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separator)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitle)
                    .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbValues, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRange, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbValues;
    private javax.swing.JSeparator separator;
    private javax.swing.JTextField txtRange;
    // End of variables declaration//GEN-END:variables
}
