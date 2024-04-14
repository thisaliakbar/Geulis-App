/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import action.ActionMenu;
import action.ActionMenuSelected;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import model.ModelMenu;
import model.ModelPengguna;
import net.miginfocom.swing.MigLayout;
import swing.MenuAnimation;
import swing.MenuItem;

/**
 *
 * @author usER
 */
public class Sidebar extends javax.swing.JPanel {

    /**
     * Creates new form Menu
     */
    private MigLayout layout;
    private ActionMenuSelected action;
    private boolean enableMenu = true;
    private boolean showMenu = true;

    public void addAction(ActionMenuSelected action) {
        this.action = action;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
    public Sidebar() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]","[]0[]");
        panelMenu.setLayout(layout);
    }
    
    public void initiationMenu(ModelPengguna modelPengguna) {
        String level = modelPengguna.getLevel();
        if(level.equals("Owner")) {
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/dashboard.png")), "Dashboard"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/briefcase.png")), "Master", "Barang","Tindakan","Pasien","Supplier","Karyawan","Pengguna"));    
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/clock.png")), "Reservasi"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/money-exchange.png")), "Transaksi","Pemeriksaan","Penjualan","Pemesanan"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/expenditure.png")), "Pengeluaran"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/medical-records.png")), "Riwayat Pasien"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/menu.png")), "Lain lain","Restok","Absensi","Cetak Kartu","Laporan"));
        } else {
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/clock.png")), "Reservasi"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/money-exchange.png")), "Transaksi","Pemeriksaan","Penjualan","Pemesanan"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/medical-records.png")), "Riwayat Pasien"));
            addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/image/menu.png")), "Lain lain","Restok","Absensi","Cetak Kartu","Laporan"));  
        }
    }
    
    private void addMenu(ModelMenu menu) {
        panelMenu.add(new MenuItem(menu, getActionMenu(), action, panelMenu.getComponentCount()), "h 40!");
    }
    
    private ActionMenu getActionMenu() {
        ActionMenu action =  new ActionMenu() {
            @Override
            public boolean menuPressed(Component componet, boolean open) {
                if(enableMenu) {
                    if(isShowMenu()) {
                        if(open) {
                            new MenuAnimation(layout, componet).openMenu();
                        } else {
                            new MenuAnimation(layout, componet).closeMenu();
                        }
                        
                        return true;
                    }
                }
                return false;
            }
        };
        
        return action;
    }
    
    public void hidenMenu() {
        for(Component component : panelMenu.getComponents()) {
            MenuItem item = (MenuItem) component;
            if(item.isOpen()) {
                new MenuAnimation(layout, component, 500).closeMenu();
                item.setOpen(false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 15, 50), getWidth(), 0 ,new Color(135, 15, 50));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogo = new javax.swing.JPanel();
        lbIcon1 = new javax.swing.JLabel();
        lbIcon2 = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();

        panelLogo.setOpaque(false);

        lbIcon1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbIcon1.setForeground(new java.awt.Color(255, 255, 255));
        lbIcon1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/geulis64.png"))); // NOI18N

        lbIcon2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/geulis64.png"))); // NOI18N

        javax.swing.GroupLayout panelLogoLayout = new javax.swing.GroupLayout(panelLogo);
        panelLogo.setLayout(panelLogoLayout);
        panelLogoLayout.setHorizontalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        panelLogoLayout.setVerticalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbIcon1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(lbIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelMenu.setOpaque(false);

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbIcon1;
    private javax.swing.JLabel lbIcon2;
    private javax.swing.JPanel panelLogo;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
}
