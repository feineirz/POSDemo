/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLOBAL;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author feinz
 */
public class Settings {
    
    public static final String SECRET_KEY = "Hige wo Soru Soshite Joshikousei wo Hirou";
    
    public static final Font MAIN_FONT = new Font("Tahoma", Font.PLAIN, 14);
    
    public static final Color BG_LIGHT = new Color(250, 250, 250);
    public static final Color BG_DARK_ALT = new Color(45, 45, 45);
    public static final Color BG_DARK = new Color(57, 57, 57);
    
    ImageIcon imageHeaderIcon = new ImageIcon(getClass().getResource("/images/header-bg.jpg"));
    ImageIcon imageBodyIcon = new ImageIcon(getClass().getResource("/images/body-bg.jpg"));
    
    ImageIcon imageDashboardIcon = new ImageIcon(getClass().getResource("/images/dashboard-bg.jpg"));
    ImageIcon imageBestSellingIcon = new ImageIcon(getClass().getResource("/images/bestselling-bg.jpg"));
    ImageIcon imageLowStockIcon = new ImageIcon(getClass().getResource("/images/lowstock-bg.jpg"));
    ImageIcon imageProfitChartsIcon = new ImageIcon(getClass().getResource("/images/profitcharts-bg.jpg"));
    ImageIcon imageSystemLogIcon = new ImageIcon(getClass().getResource("/images/systemlog-bg.jpg"));
    
    ImageIcon imageCreditsIcon = new ImageIcon(getClass().getResource("/images/credits-bg.jpg"));
    
    public final Image BACKGROUND_IMAGE_HEADER = imageHeaderIcon.getImage();    
    public final Image BACKGROUND_IMAGE_BODY = imageBodyIcon.getImage();  
    
    public final Image BACKGROUND_IMAGE_DASHBOARD = imageDashboardIcon.getImage();
    public final Image BACKGROUND_IMAGE_BESTSELLING = imageBestSellingIcon.getImage();    
    public final Image BACKGROUND_IMAGE_LOWSTOCK = imageLowStockIcon.getImage();  
    public final Image BACKGROUND_IMAGE_PROFITCHARTS = imageProfitChartsIcon.getImage();  
    public final Image BACKGROUND_IMAGE_SYSTEMLOG = imageSystemLogIcon.getImage();
    
    public final Image BACKGROUND_IMAGE_CREDITS = imageCreditsIcon.getImage();
    
}


