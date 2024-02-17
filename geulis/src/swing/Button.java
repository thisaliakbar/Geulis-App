/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author usER
 */
public class Button extends JButton{

    private Animator animator;
    private int targetSize;
    private float animateSize;
    private Point pressedPoint;
    private float alpha;
    private Color colorEffect = new Color(165, 165, 165);

    public Color getColorEffect() {
        return colorEffect;
    }

    public void setColorEffect(Color colorEffect) {
        this.colorEffect = colorEffect;
    }
    
    public Button() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                targetSize = Math.max(getWidth(), getHeight());
                animateSize = 0;
                pressedPoint = e.getPoint();
                alpha = 0.5f;
                
                if(animator.isRunning()) {
                    animator.stop();
                }
                
                animator.start();
            }
            
        });
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if(fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                
                animateSize = fraction * targetSize;
                repaint();
            }
            
        };
        
        animator = new Animator(400, target);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setBackground(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        
        if(pressedPoint != null) {
            g2.setColor(colorEffect);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int)(pressedPoint.x - animateSize / 2), (int)(pressedPoint.y - animateSize / 2), (int)(animateSize), (int)(animateSize));
        }
        
        g2.dispose();
        g.drawImage(image, 0, 0, null);
        
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
