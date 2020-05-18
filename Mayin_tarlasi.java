/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mayin_tarlasi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;



//Mayın tarlasına ait ekranı oluşturmamız için JFrame sınıfından kalıtım almamız gerekiyoz
public class Mayin_tarlasi extends JFrame {
    
    //Gereken değişkenleri tanımlıyoruz hepsini kullanım alanlarında açıklayacağım
    
    public static JPanel contentPane;
    int bayrak_sayisi;
    int mayin_sayisi=40;
    int dogru = 0;
    int koyulan_hatali_bayrak;
    int puan = 0;
    public static JLabel statusbar;
    public static JMenuBar menubar;
    public JMenu oyun;
    public JMenuItem yeni;
    public JMenuItem cikis;
    public JMenu zorluk;
    public JMenuItem kolay;
    public JMenuItem orta;
    public JMenuItem zor;
    public JMenuItem ozel;
    public JMenu sure;
    public JMenu puan_menu;
    public int satir;
    public int sutun;
    public int mayin;
    public int kontrol = 1;
    public static Timer  myTimer;
    public static TimerTask gorev;
    int time =0;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
           @Override
           public void run() {
            try {
             //Öncelikle bir jframe oluşturuyoruz
             Mayin_tarlasi frame = new Mayin_tarlasi();
             frame.setTitle("Mayın Tarlası");
             frame.setVisible(true);
             
            } catch (Exception e) {
             e.printStackTrace();
            }
           }
          });
    }
    /*Oyuncu her zorluk seviyesini değiştirdiğinde her oyunu kaybettiğinde veya 
      yeniden başlattığında çağırmak üzere bir yeni_oyun() fonksiyonu oluşturuyoruz.
      Bu fonksiyona her çağırıldığında satır sutun ve mayın sayısı bilgilerini parametre olarak göndereceğiz.
    */
    private void yeni_oyun(int satir, int sutun, int mayin_sayisi, int size_x, int size_y){
        
            this.satir = satir;
            this.sutun = sutun;
            this.mayin_sayisi = mayin_sayisi;
              puan_menu.setText("Puanınız: 0");
              time =1;
              puan =0;
              //Kullanıcının yerleştirdiği doğru bayrakları tutan değişken
              dogru = 0;
              bayrak_sayisi = mayin_sayisi;
              //Kullanıcının yerleştirdiği hatalı bayrakları tutan değişken
              koyulan_hatali_bayrak=0;
              setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
              setSize(size_x, size_y);
              setLocationRelativeTo(null);
              //Dinamik butonlarımızı içinde tutacağımız JPanel'i tanımlayıp JFrame'ye ekliyoruz
              contentPane = new JPanel();
              contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
              setContentPane(contentPane);
              contentPane.setLayout(null);
              //Oluşturduğumuz frame'i dinamik buton sayısına oranlı bir şekilde resize ediyoruz
              this.setSize(new Dimension(satir*31+20, sutun*31+80));
              
              
              int index = 0;
              //Kullanacağımız bayrak ve mayın resimlerini dahil ediyoruz
              ImageIcon icon_mayin = new ImageIcon("src/resim/mayin.png");
              ImageIcon icon_bayrak = new ImageIcon("src/resim/bayrak.png");
              Random r = new Random();
              
              ArrayList<Integer> mayin_dizisi = new ArrayList<Integer>();
              //Dinamik butonlar için daha sonra üzerinde foreach ile gezeceğimiz arraylist'i oluşturuyoruz
              ArrayList<JButton> mayinlar = new ArrayList<JButton>();
              //Random şekilde mayınların bulunacağı diziyi tanımlayıp içine mayın sayısı kadar değeri atıyoruz
              for (int i = 0; i < mayin_sayisi; i++) {
                  mayin_dizisi.add(r.nextInt(satir*sutun-1));
              }
              //İç içe iki adet for döngüsü ile Dinamik butonlarımızı oluşturuyoruz ve JPanel'e ekliyoruz
              for(int i=1;i<=satir;i++)
              {
                   for(int j=1;j<=sutun;j++)
                   {
                       final JButton btn_ij = new JButton();
                        mayinlar.add(btn_ij);
                        btn_ij.setFont(new Font("Tahoma", Font.PLAIN, 12));
                        btn_ij.setBounds(i*31-30, j*31-30, 30, 30);
                        btn_ij.setBackground(Color.GRAY);
                        btn_ij.setForeground(Color.black);
                        btn_ij.setName(Integer.toString(index));
                        index++;
                        contentPane.add(btn_ij);
                    //Oluşturulan her bir dinamik butona MouseListener ekliyoruz
                    btn_ij.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //Sağ tıklama eventi
                        if (e.getButton() == 3) {
                            //Kullanıcı sağ tıkladığı zaman eğer bayrağı varsa tıkladığı butonun üzerine bayrağı yerleştirecek
                            if (bayrak_sayisi<=0) {
                                //Bayrağı yoksa bu mesajı alacak
                                JOptionPane.showMessageDialog(null, "\nMayın sayısı kadarnız vardır dikkatli harcayın.\n", " Bayrağınız kalmadı", 1, icon_bayrak);
 
                            }else{
                            boolean b = true;
                            btn_ij.setIcon(icon_bayrak);
                            //Kullanıcının mayını tespit edip etmediğini bir döngü kontrol ediyoruz bunun için yukarıda tanımladığımız random mayın dizisinin elemanları ile butonun index numarasını karşılaştıracağız
                            for (int k = 0; k < mayin_sayisi; k++) {
                                //Kullanıcı her tespit ettiği mayın için 10 puan kazanacak
                                if (btn_ij.getName().equals(Integer.toString(mayin_dizisi.get(k)))) {
                                    dogru++;
                                    puan+=10;
                                    puan_menu.setText("Puanınız:"+puan);
                                    b = false;
                                    koyulan_hatali_bayrak=0;
                                }  
                            }
                            if(b){
                               koyulan_hatali_bayrak++; 
                               //Kullanıcı art arda 5 kez hatalı bayrak koyduğunda ceza alacak
                               if(koyulan_hatali_bayrak>4){
                                   try {
                                       puan-=3;
                                       puan_menu.setText("Puanınız:"+puan);
                                       JOptionPane.showMessageDialog(null, "Art arda 5 hatalı bayrak yerleştirdiniz\n\n3 saniye ceza aldınız ve 3 puanınız silindi.\n", " CEZA", 1, icon_mayin);
                                        Thread.sleep(3000);
                                       
                                   } catch (InterruptedException ex) {
                                       Logger.getLogger(Mayin_tarlasi.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   koyulan_hatali_bayrak=0;
                                }
                            }
                            
                            bayrak_sayisi--;
                            System.out.println("Bulnan mayin sayisi : "+dogru);
                            System.out.println("Kalan bayrak sayisi : "+bayrak_sayisi);
                            }
                        } 
                        //Sol tıklama eventi
                        else if(e.getButton() == 1) {
                            
                            btn_ij.setBackground(Color.green);
                            //Aynı sağ tıklama eventinde olduğu gibi sol tıklama eventinde de 
                            //kullanıcının mayına tıklayıp tıklamadığını kontrol ediyoruz
                            for (int k = 0; k < mayin_dizisi.size(); k++) {
                                //Eğer kullanıcı mayına tıklamışsa bu if bloğu çalışıyor
                                if (btn_ij.getName().equals(Integer.toString(mayin_dizisi.get(k)))) {
                                    //İç içe iki for döngüsü ile bütün mayınları açıyoruz
                                    for (JButton button : mayinlar) {
                                        for (int l = 0; l < mayin_dizisi.size(); l++) {
                                           if (button.getName().equals(Integer.toString(mayin_dizisi.get(l)))){
                                               button.setIcon(icon_mayin);
                                           } 
                                        }
                                    }
                                    //Daha sonra kullanıcıya mesajını verip yeni oyunu başlatıyoruz
                                    JOptionPane.showMessageDialog(null, "Mayına bastınız!!!\n\nPuanınız: "+puan+"\nGeçen süre: "+time, "  Oyun bitti", 1, icon_mayin);
                                    yeni_oyun(satir, sutun, bayrak_sayisi, satir*31+18, satir*31-71);
    
                                }    
                            }
                        }
                    }});        
            }     
        }   
    }
                   
    public Mayin_tarlasi(){
        //JMenubarın fontu çok küçük olduğu için fontunu biraz büyütüyoruz
        Font f = new Font("sans-serif", Font.PLAIN, 18);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        //Zorluk ayarları, yeniden başlatma çıkış, geçen süre ve puan seçenekleri ve değerleri için menubar oluşturup itemleri tek tek ekliyoruz 
        menubar = new JMenuBar();
        oyun = new JMenu("Oyun");
        this.setJMenuBar(menubar);
        yeni = new JMenuItem("Yeni");
        cikis = new JMenuItem("Çıkış");
        menubar.add(oyun);
        oyun.add(yeni);
        oyun.add(cikis);
        zorluk = new JMenu("Zorluk");
        kolay = new JMenuItem("Kolay");
        orta = new JMenuItem("Orta");
        zor = new JMenuItem("Zor");
        ozel = new JMenuItem("Özel");
        sure = new JMenu("Süre");
        puan_menu = new JMenu("Puanınız:0");
        
        menubar.add(zorluk);
        menubar.add(Box.createHorizontalGlue());
        zorluk.add(kolay);
        zorluk.add(orta);
        zorluk.add(zor);
        zorluk.add(ozel);
        menubar.add(sure);
        menubar.add(Box.createHorizontalGlue());
        menubar.add(puan_menu);
        
        kolay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                satir = 13;
                sutun = 9;
                mayin = 10;
               if (kontrol>0) {
                   yeni_oyun(satir,sutun,mayin,298,350); 
                   kontrol*=-1;
                }
                else{
                    yeni_oyun(satir,sutun,mayin,298,351); 
                   kontrol*=-1;
                }
            } 
        });
        orta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                satir = 16;
                sutun = 16;
                mayin = 40;
                if (kontrol>0) {
                   yeni_oyun(satir,sutun,mayin,515,568); 
                   kontrol*=-1;
                }
                else{
                    yeni_oyun(satir,sutun,mayin,515,567); 
                   kontrol*=-1;
                }
            } 
        });
        zor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                satir = 30;
                sutun = 16;
                mayin = 99;
                if (kontrol>0) {
                   yeni_oyun(satir,sutun,mayin,949,567); 
                   kontrol*=-1;
                }
                else{
                    yeni_oyun(satir,sutun,mayin,950,567); 
                   kontrol*=-1;
                }
                
               
            } 
        });
        ozel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int yukseklik = 0,genislik = 0,mayın_sayisii = 0;
                try {
                yukseklik = Integer.parseInt(JOptionPane.showInputDialog("Yükseklik","En az 9 en fazla 16"));                
                genislik = Integer.parseInt(JOptionPane.showInputDialog("Genişlik","En az 9 en fazla 30"));                
                mayın_sayisii = Integer.parseInt(JOptionPane.showInputDialog("Mayın Sayısı","En az 1 en fazla 98"));
                
                }
                catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Sadece tam sayı giriniz!!!");
                }if(yukseklik <9 || yukseklik > 16 || genislik < 9 || genislik > 30 || mayın_sayisii < 1 || mayın_sayisii >98){
                    JOptionPane.showMessageDialog(null, "Lütfen Belirlenen aralıklarda bir tamsayı giriniz");
                    //actionPerformed(e);
                }else if(mayın_sayisii > yukseklik*genislik) {
                    JOptionPane.showMessageDialog(null, "Mayın sayısı hücre hücre sayısından fazla olamaz.");
                    //actionPerformed(e);
                }else if(yukseklik >9 || yukseklik < 16 || genislik > 9 || genislik < 30 || mayın_sayisii > 1 || mayın_sayisii > 98 || mayın_sayisii > yukseklik*genislik){
                    satir = yukseklik;
                    sutun = genislik;
                    mayin = mayın_sayisii;
                    yeni_oyun(satir, sutun, mayin, satir*sutun+260, satir*sutun+315);
                    
                    
                }
            
                
                
            } 
        });
        cikis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });
        yeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kontrol<0) {
                   yeni_oyun(satir, sutun, mayin, satir*31+19, sutun*31+71); 
                   kontrol*=-1;
                }
                else{
                    yeni_oyun(satir, sutun, mayin, satir*31+19, sutun*31+72); 
                   kontrol*=-1;
                }
                
            } 
        });
        yeni_oyun(16,16,40,515,567);
         myTimer=new Timer();
                   gorev =new TimerTask() {
 
                    @Override
                    public void run() {
                        String yeni_sure = "Geçen süre: "+time; 
                        sure.setText(yeni_sure);
                        time++;
                    }
             };
             myTimer.schedule(gorev,0,1000);
    }
}
