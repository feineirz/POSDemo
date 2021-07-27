/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.*;
import DBCLS.Receipt.MonthlyIncomeInfo;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Varibles.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import static javax.swing.SwingUtilities.isRightMouseButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import pos.Apps;
import static pos.Apps.newStockForm;

/**
 *
 * @author feine
 */
public class Dashboard extends javax.swing.JInternalFrame {    

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        
        edpBestSelling.setEditable(false);
        edpLowStockNotification.setEditable(false);
        edpSystemLog.setEditable(false);
                
        loadUserInfo();
        loadDashboardInfo();
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "loadUserInfo" -> loadUserInfo();
            case "loadDashboardInfo" -> loadDashboardInfo();
        }
    }

    enum frameState {
        INIT
    }
    
    private void initForm() {
        setFrameState(frameState.INIT);
    }
    
    private void setFrameState(frameState framState) {
        switch(framState) {
            case INIT -> {
		loadUserInfo();
                loadDashboardInfo();	
            }
        }
    }
    
    /*==== Helper Functions ====*/
    private void copySystemLog() {
        
        String content = edpSystemLog.getSelectedText();
        StringSelection ss = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(ss, null);
        
    }
	
    
    /*==== Required Functions ====*/
    private void loadUserInfo() {
        
        if (CURRENT_USER != null) {
            lblWelcomeUser.setText("Welcome "+CURRENT_USER.username);
            lblEmail.setText(CURRENT_USER.email);
        }
        
    }
    
    private void loadDashboardInfo() {
        
        lblStockCost.setText(DFMT_PRICE.format(Stock.getStockCost()));
        lblStockProfit.setText(DFMT_PRICE.format(Stock.getStockProfit()));
        
        String startDatetime = getCurrentDateFormatted()+" 00:00:00";
        String endDatetime = getCurrentDateFormatted()+" 23:59:59";
        
        Double todayIncome = Receipt.getSumTotal("receipt_date between '"+startDatetime+"' and '"+endDatetime+"'");
        Double todayProfit = todayIncome - Receipt.getSumCost("receipt_date between '"+startDatetime+"' and '"+endDatetime+"'");
        
        lblTodayIncome.setText(DFMT_PRICE.format(todayIncome));
        lblTodayProfit.setText(DFMT_PRICE.format(todayProfit));
        
        startDatetime = getFirstDateOfMonth()+" 00:00:00";
        endDatetime = getLastDateOfMonth()+" 23:59:59";
        
        Double monthlyIncome = Receipt.getSumTotal("receipt_date between '"+startDatetime+"' and '"+endDatetime+"'");
        Double monthlyProfit = monthlyIncome - Receipt.getSumCost("receipt_date between '"+startDatetime+"' and '"+endDatetime+"'");
        
        lblMonthlyIncome.setText(DFMT_PRICE.format(monthlyIncome));
        lblMonthlyProfit.setText(DFMT_PRICE.format(monthlyProfit));        
        
        edpBestSelling.setText(getTopBestSellingHTML(10));
        edpLowStockNotification.setText(getLowStockNotificationHTML(100));
        edpSystemLog.setText(getApplicationLogHTML());
        
        edpBestSelling.setSelectionStart(0);
        edpBestSelling.setSelectionEnd(0);
        edpLowStockNotification.setSelectionStart(0);
        edpLowStockNotification.setSelectionEnd(0);        
        edpSystemLog.setSelectionStart(0);
        edpSystemLog.setSelectionEnd(0);
        
        // Charts
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Monthly Income/Profit",
            "Date","",
            createDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);
        
        lineChart.setBackgroundPaint(Color.white);
        
        final CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.lightGray);
        
        final CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
        categoryAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 8));
        
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelPaint(Color.BLUE);
        
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
        
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesFilled(1, true);
         
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension( 457 , 306));
        chartPanel.setOpaque(true);
        
        pnlLineChart.setLayout(new BorderLayout());
        pnlLineChart.removeAll();
        pnlLineChart.add(chartPanel, BorderLayout.CENTER);
        
    }
    
    private DefaultCategoryDataset createDataset( ) {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        
        //
        ArrayList<MonthlyIncomeInfo> miiList = Receipt.getMonthlyIncome(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        MonthlyIncomeInfo[] buff = new MonthlyIncomeInfo[32];
        miiList.forEach(mii -> {
            int id = mii.date_id;
            buff[id] = mii;
        });
        
        for (int i = 1; i <= LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(); i++) {
            if (buff[i] == null) {
                dataset.addValue(0, "Income ", String.valueOf(i));
                dataset.addValue(0, "Profit ", String.valueOf(i));
            } else {
                dataset.addValue(buff[i].income, "Income ", String.valueOf(i));
                dataset.addValue(buff[i].profit, "Profit ", String.valueOf(i));
            }           
        }        

        return dataset;        
        
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popSystemLog = new javax.swing.JPopupMenu();
        popSystemLog_Copy = new javax.swing.JMenuItem();
        popSystemLog_CopyReFormatEL = new javax.swing.JMenuItem();
        popSystemLog_CopyReFormatJF = new javax.swing.JMenuItem();
        popLowStockNTF = new javax.swing.JPopupMenu();
        popLowStockNTF_RefillStock = new javax.swing.JMenuItem();
        pnlMain = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_DASHBOARD, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pnlUserInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblWelcomeUser = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        pnlInStockCost = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblStockCost = new javax.swing.JLabel();
        pnlTodayIncome = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTodayIncome = new javax.swing.JLabel();
        pnlInStockProfit = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblStockProfit = new javax.swing.JLabel();
        pnlBestSelling = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BESTSELLING, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel6 = new javax.swing.JLabel();
        scpBestSelling = new javax.swing.JScrollPane();
        edpBestSelling = new javax.swing.JEditorPane();
        pnlMonthlyIncome = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblMonthlyIncome = new javax.swing.JLabel();
        pnlLowStockNotification = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_LOWSTOCK, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel8 = new javax.swing.JLabel();
        scpLowStockNotification = new javax.swing.JScrollPane();
        edpLowStockNotification = new javax.swing.JEditorPane();
        pnlTodayProfit = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblTodayProfit = new javax.swing.JLabel();
        pnlMonthlyProfit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblMonthlyProfit = new javax.swing.JLabel();
        pnlCharts = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_PROFITCHARTS, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel12 = new javax.swing.JLabel();
        pnlLineChart = new javax.swing.JPanel();
        pnlSystemLog = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_SYSTEMLOG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel13 = new javax.swing.JLabel();
        scpSystemLog = new javax.swing.JScrollPane();
        edpSystemLog = new javax.swing.JEditorPane();
        jLabel11 = new javax.swing.JLabel();

        popSystemLog_Copy.setText("Copy");
        popSystemLog_Copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popSystemLog_CopyActionPerformed(evt);
            }
        });
        popSystemLog.add(popSystemLog_Copy);

        popSystemLog_CopyReFormatEL.setText("Copy and Reformat at elmah.io");
        popSystemLog_CopyReFormatEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popSystemLog_CopyReFormatELActionPerformed(evt);
            }
        });
        popSystemLog.add(popSystemLog_CopyReFormatEL);

        popSystemLog_CopyReFormatJF.setText("Copy and Reformat at jsonformatter");
        popSystemLog_CopyReFormatJF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popSystemLog_CopyReFormatJFActionPerformed(evt);
            }
        });
        popSystemLog.add(popSystemLog_CopyReFormatJF);

        popLowStockNTF_RefillStock.setText("Refill Stock");
        popLowStockNTF_RefillStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popLowStockNTF_RefillStockActionPerformed(evt);
            }
        });
        popLowStockNTF.add(popLowStockNTF_RefillStock);

        setBackground(new java.awt.Color(102, 102, 102));
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setMaximumSize(new java.awt.Dimension(2400, 2000));
        setMinimumSize(new java.awt.Dimension(1900, 950));
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(1900, 950));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(102, 102, 102));

        pnlUserInfo.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(72, 72));
        jLabel1.setMinimumSize(new java.awt.Dimension(72, 72));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(48, 48));

        lblWelcomeUser.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        lblWelcomeUser.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcomeUser.setText("WELCOME user");
        lblWelcomeUser.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblEmail.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("email@system.net");
        lblEmail.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlUserInfoLayout = new javax.swing.GroupLayout(pnlUserInfo);
        pnlUserInfo.setLayout(pnlUserInfoLayout);
        pnlUserInfoLayout.setHorizontalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblWelcomeUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlUserInfoLayout.setVerticalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addComponent(lblWelcomeUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEmail)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInStockCost.setBackground(new java.awt.Color(255, 102, 0));

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("IN STOCK COST");

        lblStockCost.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblStockCost.setForeground(new java.awt.Color(255, 255, 255));
        lblStockCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStockCost.setText("0.00");
        lblStockCost.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlInStockCostLayout = new javax.swing.GroupLayout(pnlInStockCost);
        pnlInStockCost.setLayout(pnlInStockCostLayout);
        pnlInStockCostLayout.setHorizontalGroup(
            pnlInStockCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInStockCostLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInStockCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInStockCostLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInStockCostLayout.setVerticalGroup(
            pnlInStockCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInStockCostLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStockCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlTodayIncome.setBackground(new java.awt.Color(0, 153, 204));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TODAY INCOME");

        lblTodayIncome.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblTodayIncome.setForeground(new java.awt.Color(255, 255, 255));
        lblTodayIncome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTodayIncome.setText("0.00");
        lblTodayIncome.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlTodayIncomeLayout = new javax.swing.GroupLayout(pnlTodayIncome);
        pnlTodayIncome.setLayout(pnlTodayIncomeLayout);
        pnlTodayIncomeLayout.setHorizontalGroup(
            pnlTodayIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodayIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTodayIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTodayIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTodayIncomeLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlTodayIncomeLayout.setVerticalGroup(
            pnlTodayIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodayIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTodayIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlInStockProfit.setBackground(new java.awt.Color(102, 204, 0));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("IN STOCK PROFIT");

        lblStockProfit.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblStockProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblStockProfit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStockProfit.setText("0.00");
        lblStockProfit.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlInStockProfitLayout = new javax.swing.GroupLayout(pnlInStockProfit);
        pnlInStockProfit.setLayout(pnlInStockProfitLayout);
        pnlInStockProfitLayout.setHorizontalGroup(
            pnlInStockProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInStockProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInStockProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInStockProfitLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInStockProfitLayout.setVerticalGroup(
            pnlInStockProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInStockProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStockProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlBestSelling.setBackground(new java.awt.Color(204, 0, 0));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOP 10 BEST SELLING");

        edpBestSelling.setContentType("text/html"); // NOI18N
        scpBestSelling.setViewportView(edpBestSelling);

        javax.swing.GroupLayout pnlBestSellingLayout = new javax.swing.GroupLayout(pnlBestSelling);
        pnlBestSelling.setLayout(pnlBestSellingLayout);
        pnlBestSellingLayout.setHorizontalGroup(
            pnlBestSellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBestSellingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBestSellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBestSellingLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 108, Short.MAX_VALUE))
                    .addComponent(scpBestSelling))
                .addContainerGap())
        );
        pnlBestSellingLayout.setVerticalGroup(
            pnlBestSellingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBestSellingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpBestSelling)
                .addContainerGap())
        );

        pnlMonthlyIncome.setBackground(new java.awt.Color(255, 0, 102));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("MONTHLY INCOME");

        lblMonthlyIncome.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblMonthlyIncome.setForeground(new java.awt.Color(255, 255, 255));
        lblMonthlyIncome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonthlyIncome.setText("0.00");
        lblMonthlyIncome.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlMonthlyIncomeLayout = new javax.swing.GroupLayout(pnlMonthlyIncome);
        pnlMonthlyIncome.setLayout(pnlMonthlyIncomeLayout);
        pnlMonthlyIncomeLayout.setHorizontalGroup(
            pnlMonthlyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonthlyIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMonthlyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMonthlyIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMonthlyIncomeLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 136, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMonthlyIncomeLayout.setVerticalGroup(
            pnlMonthlyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonthlyIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMonthlyIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlLowStockNotification.setBackground(new java.awt.Color(255, 153, 0));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LOW STOCK NOTIFICATION");

        edpLowStockNotification.setContentType("text/html"); // NOI18N
        edpLowStockNotification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edpLowStockNotificationMouseClicked(evt);
            }
        });
        scpLowStockNotification.setViewportView(edpLowStockNotification);

        javax.swing.GroupLayout pnlLowStockNotificationLayout = new javax.swing.GroupLayout(pnlLowStockNotification);
        pnlLowStockNotification.setLayout(pnlLowStockNotificationLayout);
        pnlLowStockNotificationLayout.setHorizontalGroup(
            pnlLowStockNotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLowStockNotificationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLowStockNotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLowStockNotificationLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scpLowStockNotification))
                .addContainerGap())
        );
        pnlLowStockNotificationLayout.setVerticalGroup(
            pnlLowStockNotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLowStockNotificationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpLowStockNotification)
                .addContainerGap())
        );

        pnlTodayProfit.setBackground(new java.awt.Color(0, 204, 204));

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TODAY PROFIT");

        lblTodayProfit.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblTodayProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblTodayProfit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTodayProfit.setText("0.00");
        lblTodayProfit.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlTodayProfitLayout = new javax.swing.GroupLayout(pnlTodayProfit);
        pnlTodayProfit.setLayout(pnlTodayProfitLayout);
        pnlTodayProfitLayout.setHorizontalGroup(
            pnlTodayProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodayProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTodayProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTodayProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTodayProfitLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlTodayProfitLayout.setVerticalGroup(
            pnlTodayProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodayProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTodayProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMonthlyProfit.setBackground(new java.awt.Color(255, 51, 153));

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("MONTHLY PROFIT");

        lblMonthlyProfit.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        lblMonthlyProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblMonthlyProfit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonthlyProfit.setText("0.00");
        lblMonthlyProfit.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlMonthlyProfitLayout = new javax.swing.GroupLayout(pnlMonthlyProfit);
        pnlMonthlyProfit.setLayout(pnlMonthlyProfitLayout);
        pnlMonthlyProfitLayout.setHorizontalGroup(
            pnlMonthlyProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonthlyProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMonthlyProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMonthlyProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMonthlyProfitLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMonthlyProfitLayout.setVerticalGroup(
            pnlMonthlyProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonthlyProfitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMonthlyProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCharts.setBackground(new java.awt.Color(51, 153, 0));

        jLabel12.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("PROFIT CHARTS");

        javax.swing.GroupLayout pnlLineChartLayout = new javax.swing.GroupLayout(pnlLineChart);
        pnlLineChart.setLayout(pnlLineChartLayout);
        pnlLineChartLayout.setHorizontalGroup(
            pnlLineChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlLineChartLayout.setVerticalGroup(
            pnlLineChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlChartsLayout = new javax.swing.GroupLayout(pnlCharts);
        pnlCharts.setLayout(pnlChartsLayout);
        pnlChartsLayout.setHorizontalGroup(
            pnlChartsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlChartsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .addComponent(pnlLineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlChartsLayout.setVerticalGroup(
            pnlChartsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlSystemLog.setBackground(new java.awt.Color(153, 0, 0));

        jLabel13.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("APPLICATION LOG");

        edpSystemLog.setBackground(new java.awt.Color(240, 240, 240));
        edpSystemLog.setContentType("text/html"); // NOI18N
        edpSystemLog.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        edpSystemLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edpSystemLogMouseClicked(evt);
            }
        });
        scpSystemLog.setViewportView(edpSystemLog);

        javax.swing.GroupLayout pnlSystemLogLayout = new javax.swing.GroupLayout(pnlSystemLog);
        pnlSystemLog.setLayout(pnlSystemLogLayout);
        pnlSystemLogLayout.setHorizontalGroup(
            pnlSystemLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSystemLogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSystemLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpSystemLog)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSystemLogLayout.setVerticalGroup(
            pnlSystemLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSystemLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpSystemLog)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("DASHBOARD");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBestSelling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLowStockNotification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCharts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSystemLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMonthlyIncome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlUserInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTodayProfit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTodayIncome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInStockProfit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInStockCost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMonthlyProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlLowStockNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlInStockCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlInStockProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTodayIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTodayProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMonthlyIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMonthlyProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 56, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlCharts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlSystemLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlBestSelling, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        loadDashboardInfo();
        
    }//GEN-LAST:event_formComponentShown

    private void popSystemLog_CopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popSystemLog_CopyActionPerformed
        
        copySystemLog();
        
    }//GEN-LAST:event_popSystemLog_CopyActionPerformed

    private void edpSystemLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edpSystemLogMouseClicked
        
        if (isRightMouseButton(evt)) {
            popSystemLog.show(Apps.mainFrame.mainDesktop, evt.getXOnScreen(),evt.getYOnScreen()-50);
        }
        
    }//GEN-LAST:event_edpSystemLogMouseClicked

    private void popSystemLog_CopyReFormatELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popSystemLog_CopyReFormatELActionPerformed
        
        copySystemLog();
        openURL("https://elmah.io/tools/json-formatter/");
        
    }//GEN-LAST:event_popSystemLog_CopyReFormatELActionPerformed

    private void popLowStockNTF_RefillStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popLowStockNTF_RefillStockActionPerformed
        
        String selText = edpLowStockNotification.getSelectedText().trim();       
        
        if (!selText.equals("")) {
            Product product = Product.isExist("code = '"+selText+"'");
            if (product != null) {
                pullCenter(Apps.mainFrame.mainDesktop, newStockForm);
                newStockForm.show();
                newStockForm.toFront();
                newStockForm.tbxCode.requestFocus();
                newStockForm.tbxCode.setText(selText);
                newStockForm.tbxCode.selectAll();
                REFILL_STOCK_IGNORE_INIT_CODE = true; // Special event
            }
        }
        
    }//GEN-LAST:event_popLowStockNTF_RefillStockActionPerformed

    private void edpLowStockNotificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edpLowStockNotificationMouseClicked
        
        if (isRightMouseButton(evt)) {
            String selText = String.valueOf(edpLowStockNotification.getSelectedText());        
            if (!selText.equals("")) {
                Product product = Product.isExist("code = '"+selText+"'");
                if (product != null) {
                    popLowStockNTF.show(Apps.mainFrame.mainDesktop, evt.getXOnScreen(),evt.getYOnScreen()-50);
                }
            }
        }
        
    }//GEN-LAST:event_edpLowStockNotificationMouseClicked

    private void popSystemLog_CopyReFormatJFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popSystemLog_CopyReFormatJFActionPerformed
        
        copySystemLog();
        openURL("https://jsonformatter.curiousconcept.com/");
        
    }//GEN-LAST:event_popSystemLog_CopyReFormatJFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane edpBestSelling;
    private javax.swing.JEditorPane edpLowStockNotification;
    private javax.swing.JEditorPane edpSystemLog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMonthlyIncome;
    private javax.swing.JLabel lblMonthlyProfit;
    private javax.swing.JLabel lblStockCost;
    private javax.swing.JLabel lblStockProfit;
    private javax.swing.JLabel lblTodayIncome;
    private javax.swing.JLabel lblTodayProfit;
    private javax.swing.JLabel lblWelcomeUser;
    private javax.swing.JPanel pnlBestSelling;
    private javax.swing.JPanel pnlCharts;
    private javax.swing.JPanel pnlInStockCost;
    private javax.swing.JPanel pnlInStockProfit;
    private javax.swing.JPanel pnlLineChart;
    private javax.swing.JPanel pnlLowStockNotification;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMonthlyIncome;
    private javax.swing.JPanel pnlMonthlyProfit;
    private javax.swing.JPanel pnlSystemLog;
    private javax.swing.JPanel pnlTodayIncome;
    private javax.swing.JPanel pnlTodayProfit;
    public static javax.swing.JPanel pnlUserInfo;
    private javax.swing.JPopupMenu popLowStockNTF;
    private javax.swing.JMenuItem popLowStockNTF_RefillStock;
    private javax.swing.JPopupMenu popSystemLog;
    private javax.swing.JMenuItem popSystemLog_Copy;
    private javax.swing.JMenuItem popSystemLog_CopyReFormatEL;
    private javax.swing.JMenuItem popSystemLog_CopyReFormatJF;
    private javax.swing.JScrollPane scpBestSelling;
    private javax.swing.JScrollPane scpLowStockNotification;
    private javax.swing.JScrollPane scpSystemLog;
    // End of variables declaration//GEN-END:variables
}
