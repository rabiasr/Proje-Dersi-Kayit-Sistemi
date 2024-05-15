package arayuz;

import static arayuz.hocaFrame.tab;
import baglanti.baglanti;
import db.Hoca;
import db.Ogrenci;
import db.Student;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

public class yoneticiFrame extends javax.swing.JFrame {

    private baglanti bglnt = new baglanti();
    public ArrayList<Student> ful = new ArrayList<>();
    public ArrayList<Integer> hocaaa = new ArrayList<>();
    public ArrayList<Student> ders41 = new ArrayList<>();
    public ArrayList<Student> ders42 = new ArrayList<>();
    public ArrayList<Student> dersbos = new ArrayList<>();
    public ArrayList<Student> dersap = new ArrayList<>();
    public ArrayList<Student> dersbp = new ArrayList<>();
    public ArrayList<Student> dersyok = new ArrayList<>();
    public ArrayList<Student> hepsi = new ArrayList<>();
    public ArrayList<Integer> hoca = new ArrayList<>();
    boolean sectable = false;
    int index;
    DefaultTableModel model;
    public final ArrayList<Hoca> hc = new ArrayList<>();

    public yoneticiFrame() {
        initComponents();
    }

    public ResultSet bilgial(String query) {
        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                //rs.close();
                st.close();

                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirogrenci(JTable table) {

        DefaultTableModel tableogrenci = new DefaultTableModel();
        Object[] ogrenci = {"Öðrenci ID", "Öðrenci Ad", "Öðrenci Soyad", "GNO", "Toplam Talep Sayýsý", "Onaylanan Talep Sayýsý"};

        String query;

        query = "SELECT * FROM \"Ogrenciler\"";
        ResultSet rs = bilgial(query);
        tableogrenci.setColumnIdentifiers(ogrenci);
        try {

            while (rs.next()) {

                ogrenci[0] = rs.getInt("ogrNo");
                ogrenci[1] = rs.getString("ogrAd");
                ogrenci[2] = rs.getString("ogrSoyad");
                ogrenci[3] = rs.getDouble("GNO");
                ogrenci[4] = rs.getInt("toplamTalep");
                ogrenci[5] = rs.getInt("talepOnay");
                tableogrenci.addRow(ogrenci);

            }
            table.setModel(tableogrenci);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirmesaj(JTable table) {

        DefaultTableModel tableogrenci = new DefaultTableModel();
        Object[] ogrenci = {"Mesaj No", "Kim", "Kime", "Mesaj", "Ders ID"};

        String query;

        query = "select * from \"Mesaj\"";
        ResultSet rs = bilgial(query);
        tableogrenci.setColumnIdentifiers(ogrenci);
        try {

            while (rs.next()) {

                ogrenci[0] = rs.getInt("mesajNo");
                ogrenci[1] = rs.getInt("kim");
                ogrenci[2] = rs.getInt("kime");
                ogrenci[3] = rs.getString("mesaj");
                ogrenci[4] = rs.getInt("dersID");

                tableogrenci.addRow(ogrenci);

            }
            table.setModel(tableogrenci);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirgecmis(JTable table) {

        DefaultTableModel tableogrenci = new DefaultTableModel();
        Object[] ogrenci = {"Öðrenci ID", "Öðrenci Ad", "Öðretmen ID", "Öðretmen Ad", "Ders ID", "Ders Ad", "Durum", "Gönderen"};

        String query;

        query = "select * from \"Dersler\" inner join \"Gecmis\" on \"Dersler\".\"dersID\"=\"Gecmis\".\"dersID\""
                + "inner join \"Ogrenciler\" on \"Gecmis\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\""
                + "inner join \"Hocalar\" on \"Gecmis\".\"sicilNo\"=\"Hocalar\".\"sicilNo\" ";

        ResultSet rs = bilgial(query);
        tableogrenci.setColumnIdentifiers(ogrenci);
        try {

            while (rs.next()) {
                String gonderen = null;
                if (rs.getBoolean("gonderen") == false) {
                    gonderen = "Öðrenci";
                } else {
                    gonderen = "Öðretmen";
                }

                ogrenci[0] = rs.getInt("ogrNo");
                ogrenci[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                ogrenci[2] = rs.getInt("sicilNo");
                ogrenci[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                ogrenci[4] = rs.getInt("dersID");
                ogrenci[5] = rs.getString("dersAd");
                ogrenci[6] = rs.getString("durum");
                ogrenci[7] = gonderen;
                tableogrenci.addRow(ogrenci);

            }
            table.setModel(tableogrenci);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirrandom(JTable table) {

        DefaultTableModel tablerandom = new DefaultTableModel();
        Object[] random = {"Öðrenci ID", "Öðrenci Ad", "Öðrenci Soyad", "GNO", "Toplam Talep Sayýsý", "Onaylanan Talep Sayýsý", "Ders ID", "Ders Ad", "Harf Notu"};

        String query;

        query = "select * from \"Ogrenciler\" inner join \"Belge\" on \"Ogrenciler\".\"ogrNo\"=\"Belge\".\"ogrNo\" "
                + "inner join \"Dersler\" on  \"Belge\".\"dersID\"=\"Dersler\".\"dersID\" ";
        ResultSet rs = bilgial(query);
        tablerandom.setColumnIdentifiers(random);
        try {

            while (rs.next()) {

                random[0] = rs.getInt("ogrNo");
                random[1] = rs.getString("ogrAd");
                random[2] = rs.getString("ogrSoyad");
                random[3] = rs.getDouble("GNO");
                random[4] = rs.getInt("toplamTalep");
                random[5] = rs.getInt("talepOnay");
                random[6] = rs.getInt("dersID");
                random[7] = rs.getString("dersAd");
                random[8] = rs.getString("hNot");
                tablerandom.addRow(random);

            }
            table.setModel(tablerandom);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirdersler(JTable table) {

        DefaultTableModel tabledersler = new DefaultTableModel();
        Object[] dersler = {"Ders ID", "Ders Ad", "AKTS"};

        String query;

        query = "SELECT * FROM \"Dersler\"";
        ResultSet rs = bilgial(query);
        tabledersler.setColumnIdentifiers(dersler);
        try {

            while (rs.next()) {

                dersler[0] = rs.getInt("dersID");
                dersler[1] = rs.getString("dersAd");
                dersler[2] = rs.getDouble("AKTS");

                tabledersler.addRow(dersler);

            }
            table.setModel(tabledersler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirkriter(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Hoca Sicil Numarasý", "Ders ID", "Katsayý"};

        String query;

        query = "SELECT * FROM \"HFormul\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("sicilNo");
                liste[1] = rs.getString("ders"
                        + "ID");
                liste[2] = rs.getInt("katsayi");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdiryonetici(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Yönetici ID", "Yonetici Þifre"};

        String query;

        query = "SELECT * FROM \"Yonetici\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("yonID");
                liste[1] = rs.getString("yonSifre");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirdershoca(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Index", "dersID", "Hoca Sicil Numarasý"};

        String query;

        query = "SELECT * FROM \"dersHoca\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("dersIndex");
                liste[1] = rs.getInt("dersID");
                liste[2] = rs.getInt("sicilNo");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdiranlasma(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Anlaþma Numarasý", "Öðrenci Numarasý", "Hoca Sicil Numarasý", "Ders ID", "Talep Durumu"};

        String query;

        query = "SELECT * FROM \"Anlasma\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("anlasmaNo");
                liste[1] = rs.getInt("ogrNo");
                liste[2] = rs.getInt("sicilNo");
                liste[3] = rs.getInt("dersID");
                liste[4] = rs.getString("durum");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirbelge(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Öðrenci Numarasý", "Ders ID", "Harf Notu"};

        String query;

        query = "SELECT * FROM \"Belge\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("ogrNo");
                liste[1] = rs.getInt("dersID");
                liste[2] = rs.getString("hNot");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirnot(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Harf Notu", "Sayýsal Not"};

        String query;

        query = "SELECT * FROM \"Notlar\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getString("hNot");
                liste[1] = rs.getDouble("sNot");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirilgiler(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Ýlgi ID", "Ýlgi Ad"};

        String query;

        query = "SELECT * FROM \"ilgiAlanlari\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("ilgiID");
                liste[1] = rs.getString("ilgiAd");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirhilgi(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Index", "Hoca Sicil Numarasý", "Ýlgi ID"};

        String query;

        query = "SELECT * FROM \"HIlgi\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("hIndex");
                liste[1] = rs.getInt("sicilNo");
                liste[2] = rs.getInt("ilgiID");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdiroilgi(JTable table) {

        DefaultTableModel tablebilgiler = new DefaultTableModel();
        Object[] liste = {"Index", "Öðrenci Numarasý", "Ýlgi ID"};

        String query;

        query = "SELECT * FROM \"OIlgi\"";
        ResultSet rs = bilgial(query);
        tablebilgiler.setColumnIdentifiers(liste);
        try {

            while (rs.next()) {

                liste[0] = rs.getInt("ogrIndex");
                liste[1] = rs.getInt("ogrNo");
                liste[2] = rs.getInt("ilgiID");

                tablebilgiler.addRow(liste);

            }
            table.setModel(tablebilgiler);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirhoca(JTable table) {

        DefaultTableModel tableogretmen = new DefaultTableModel();
        Object[] ogretmen = {"Öðretmen ID", "Öðretmen Ad", "Öðretmen Soyad", "Kontenjan"};

        String query;

        query = "SELECT * FROM \"Hocalar\"";
        ResultSet rs = bilgial(query);
        tableogretmen.setColumnIdentifiers(ogretmen);
        try {

            while (rs.next()) {

                ogretmen[0] = rs.getInt("sicilNo");
                ogretmen[1] = rs.getString("hAd");
                ogretmen[2] = rs.getString("hSoyad");
                ogretmen[3] = rs.getInt("kontenjan");

                tableogretmen.addRow(ogretmen);
            }
            table.setModel(tableogretmen);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void yazdirsistem(JTable table) {
        DefaultTableModel tablesistem = new DefaultTableModel();
        Object[] sistem = {"Mesaj Karakter Sýnýrý", "Aktif-Pasif Durumu", "Varsayýlan Talep"};
        String query;
        query = "select * from \"Sistem\"";
        ResultSet rs = this.bilgial(query);
        tablesistem.setColumnIdentifiers(sistem);

        try {

            while (rs.next()) {

                sistem[0] = rs.getString("karakterSiniri");
                sistem[1] = rs.getBoolean("aktifPasif");
                sistem[2] = rs.getInt("varsayilanTalep");

                tablesistem.addRow(sistem);
            }
            table.setModel(tablesistem);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void dersata() {
        Connection c = bglnt.dbcon();
        Statement st2 = null;
        ArrayList<Integer> dersler = new ArrayList<>();
        ArrayList<String> notlar = new ArrayList<>();
        ArrayList<Integer> ogrenci = new ArrayList<>();
        ArrayList<Integer> belge = new ArrayList<>();
        ResultSet rs = bilgial("select * from \"Dersler\"");
        ResultSet rs1 = bilgial("select * from \"Notlar\"");
        ResultSet rs2 = bilgial("select * from \"Ogrenciler\"");
        ResultSet rs3 = bilgial("select * from \"Belge\" ");
        try {
            st2 = c.createStatement();
            while (rs.next()) {
                dersler.add(rs.getInt("dersID"));

            }
            while (rs1.next()) {
                notlar.add(rs1.getString("hNot"));
            }
            while (rs3.next()) {
                belge.add(rs3.getInt("ogrNo"));
            }
            while (rs2.next()) {
                ogrenci.add(rs2.getInt("ogrNo"));
            }

            if (belge.size() == 0) {

                for (int j = 0; j < ogrenci.size(); j++) {

                    for (int i = 0; i < dersler.size(); i++) {

                        Random r = new Random();

                        int rnot = (int) (Math.random() * notlar.size());

                        String not = notlar.get(rnot);
                        st2.executeUpdate("insert into \"Belge\" (\"ogrNo\",\"dersID\",\"hNot\") values (" + ogrenci.get(j) + "," + dersler.get(i) + ",'" + not + "')");
                    }
                }

            } else {
                for (int i = 0; i < ogrenci.size(); i++) {
                    if (belge.contains(ogrenci.get(i))) {
                    } else {

                        for (int l = 0; l < dersler.size(); l++) {

                            Random r = new Random();

                            int rnot = (int) (Math.random() * notlar.size());

                            String not = notlar.get(rnot);
                            st2.executeUpdate("insert into \"Belge\" (\"ogrNo\",\"dersID\",\"hNot\") values (" + ogrenci.get(i) + "," + dersler.get(l) + ",'" + not + "')");
                        }

                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs1 != null) {
                    rs1.close();
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (rs3 != null) {
                    rs3.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel = new javax.swing.JPanel();
        ogrhocagorbox = new javax.swing.JComboBox<>();
        jpanel = new javax.swing.JScrollPane();
        ogrhocatable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        ogrsilbtn = new javax.swing.JButton();
        ogrguncelbtn = new javax.swing.JButton();
        hocaeklebtn = new javax.swing.JButton();
        hocasilbtn = new javax.swing.JButton();
        hocaguncelbtn = new javax.swing.JButton();
        ogreklebtn = new javax.swing.JButton();
        panel1 = new javax.swing.JPanel();
        tabanitemizlebtn = new javax.swing.JButton();
        rastgeleatamabtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        studenttable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        scroolpaneee = new javax.swing.JScrollPane();
        teachtable = new javax.swing.JTable();
        yukaribtn = new javax.swing.JButton();
        asagibtn = new javax.swing.JButton();
        olusturbtn = new javax.swing.JButton();
        formulbtn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        derssecbtn = new javax.swing.JButton();
        guncellebtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        sistemguncelbox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        tablolarbox = new javax.swing.JComboBox<>();
        asamabtn = new javax.swing.JButton();
        cksbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(229, 213, 234));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        panel.setBackground(new java.awt.Color(173, 137, 217));
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrhocagorbox.setBackground(new java.awt.Color(209, 188, 231));
        ogrhocagorbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Öðrencileri Görüntüle", "Öðretmenleri Görüntüle", " " }));
        ogrhocagorbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogrhocagorbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogrhocagorboxActionPerformed(evt);
            }
        });

        jpanel.setBackground(new java.awt.Color(209, 188, 231));
        jpanel.setAutoscrolls(true);

        ogrhocatable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jpanel.setViewportView(ogrhocatable);

        jButton1.setBackground(new java.awt.Color(173, 137, 217));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Öðrenci Üret ");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ogrsilbtn.setBackground(new java.awt.Color(173, 137, 217));
        ogrsilbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ogrsilbtn.setText("Öðrenci Sil");
        ogrsilbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogrsilbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogrsilbtnActionPerformed(evt);
            }
        });

        ogrguncelbtn.setBackground(new java.awt.Color(173, 137, 217));
        ogrguncelbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ogrguncelbtn.setText("Öðrenci Bilgi Güncelle");
        ogrguncelbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogrguncelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogrguncelbtnActionPerformed(evt);
            }
        });

        hocaeklebtn.setBackground(new java.awt.Color(173, 137, 217));
        hocaeklebtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hocaeklebtn.setText("Öðretmen Ekle");
        hocaeklebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocaeklebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hocaeklebtnActionPerformed(evt);
            }
        });

        hocasilbtn.setBackground(new java.awt.Color(173, 137, 217));
        hocasilbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hocasilbtn.setText("Öðretmen Sil");
        hocasilbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocasilbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hocasilbtnActionPerformed(evt);
            }
        });

        hocaguncelbtn.setBackground(new java.awt.Color(173, 137, 217));
        hocaguncelbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hocaguncelbtn.setText("Öðretmen Bilgi Güncelle");
        hocaguncelbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocaguncelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hocaguncelbtnActionPerformed(evt);
            }
        });

        ogreklebtn.setBackground(new java.awt.Color(173, 137, 217));
        ogreklebtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ogreklebtn.setText("Öðrenci Ekle");
        ogreklebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogreklebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogreklebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ogrhocagorbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(ogrsilbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ogrguncelbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hocaeklebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hocasilbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hocaguncelbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ogreklebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ogrhocagorbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ogrsilbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ogrguncelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hocaeklebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hocasilbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hocaguncelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ogreklebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Öðrenci-Hoca Bilgileri", panel);

        panel1.setBackground(new java.awt.Color(173, 137, 217));
        panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tabanitemizlebtn.setBackground(new java.awt.Color(173, 137, 217));
        tabanitemizlebtn.setText("Tabaný Temizle");
        tabanitemizlebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabanitemizlebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabanitemizlebtnActionPerformed(evt);
            }
        });

        rastgeleatamabtn.setBackground(new java.awt.Color(173, 137, 217));
        rastgeleatamabtn.setText("Rastgele Atama");
        rastgeleatamabtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rastgeleatamabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rastgeleatamabtnActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(209, 188, 231));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studenttable.setBackground(new java.awt.Color(209, 188, 231));
        studenttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(studenttable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jButton2.setBackground(new java.awt.Color(173, 137, 217));
        jButton2.setText("Sonuçlarý Gör");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        scroolpaneee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        teachtable.setBackground(new java.awt.Color(209, 188, 231));
        teachtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        scroolpaneee.setViewportView(teachtable);

        yukaribtn.setBackground(new java.awt.Color(209, 188, 231));
        yukaribtn.setText("Yukarý");
        yukaribtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yukaribtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yukaribtnActionPerformed(evt);
            }
        });

        asagibtn.setBackground(new java.awt.Color(209, 188, 231));
        asagibtn.setText("Aþaðý");
        asagibtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        asagibtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asagibtnActionPerformed(evt);
            }
        });

        olusturbtn.setBackground(new java.awt.Color(209, 188, 231));
        olusturbtn.setText("GNO");
        olusturbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        olusturbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                olusturbtnActionPerformed(evt);
            }
        });

        formulbtn.setBackground(new java.awt.Color(209, 188, 231));
        formulbtn.setText("Formül ");
        formulbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        formulbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroolpaneee, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(olusturbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(yukaribtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(asagibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formulbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(scroolpaneee, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(formulbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(yukaribtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(olusturbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(asagibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setBackground(new java.awt.Color(173, 137, 217));
        jButton4.setText("GNO Atama");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        derssecbtn.setBackground(new java.awt.Color(173, 137, 217));
        derssecbtn.setText("Formül Ders Seç");
        derssecbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        derssecbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derssecbtnActionPerformed(evt);
            }
        });

        guncellebtn.setBackground(new java.awt.Color(173, 137, 217));
        guncellebtn.setText("Tablo Güncelle");
        guncellebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        guncellebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncellebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabanitemizlebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(rastgeleatamabtn, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(derssecbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guncellebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                        .addComponent(tabanitemizlebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rastgeleatamabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(derssecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(guncellebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Random Atama", panel1);

        jPanel2.setBackground(new java.awt.Color(173, 137, 217));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        table.setBackground(new java.awt.Color(209, 188, 231));
        table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tablo Bilgileri");

        sistemguncelbox.setBackground(new java.awt.Color(209, 188, 231));
        sistemguncelbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mesaj Karakter Sýnýrý", "Aktif Pasif Durumu", "Varsayýlan Talep", " " }));
        sistemguncelbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sistemguncelbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sistemguncelboxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Güncellemek Ýstediðiniz Deðeri Seçiniz:");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tablolarbox.setBackground(new java.awt.Color(209, 188, 231));
        tablolarbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Öðrenciler Tablosu", "Öðretmenler Tablosu", "Dersler Tablosu", "Belge Tablosu", "Yönetici Tablosu", "Dersler-Hocalar Tablosu", "Kriter Tablosu", "Anlaþma Tablosu", "Ýlgi Alanlarý Tablosu", "Öðrenci Ýlgi Alanlarý Tablosu", "Öðretmen Ýlgi Alanlarý Tablosu", "Notlar Tablosu", "Sistem Tablosu", "Geçmiþ Tablosu", "Random Öðrenciler", "Mesaj Tablosu" }));
        tablolarbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablolarbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablolarboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sistemguncelbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tablolarbox, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tablolarbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(sistemguncelbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jTabbedPane1.addTab("Sistem Bilgileri", jPanel2);

        asamabtn.setBackground(new java.awt.Color(229, 213, 234));
        asamabtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        asamabtn.setText("2. Aþamaya Geç");
        asamabtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        asamabtn.setDefaultCapable(false);
        asamabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asamabtnActionPerformed(evt);
            }
        });

        cksbtn.setBackground(new java.awt.Color(229, 213, 234));
        cksbtn.setText("ÇIKIÞ");
        cksbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cksbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cksbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1163, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cksbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(asamabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(asamabtn, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cksbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void asamabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asamabtnActionPerformed

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = bilgial("select * from \"Hocalar\" ");

        try {
            st = c.createStatement();
            st.executeUpdate("update \"Sistem\" set \"asama\"=" + "true" + "  ");

            while (rs.next()) {
                Hoca hoca = new Hoca(rs.getInt("sicilNo"), rs.getString("hAd") + " " + rs.getString("hSoyad"), rs.getInt("kontenjan"));

                hc.add(hoca);

            }

            JOptionPane.showMessageDialog(null, "Diðer Kullanýcýlarýn Talep-Onay Anlaþma Ýþlemleri Durdurulmuþtur");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_asamabtnActionPerformed

    private void cksbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cksbtnActionPerformed

        this.dispose();
        menu menu = new menu();

        menu.setVisible(true);


    }//GEN-LAST:event_cksbtnActionPerformed

    private void tablolarboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablolarboxActionPerformed

        String secim = null;

        secim = (String) tablolarbox.getSelectedItem();
        if (secim.equalsIgnoreCase("Öðrenciler Tablosu")) {
            this.yazdirogrenci(this.table);
        } else if (secim.equalsIgnoreCase("Öðretmenler Tablosu")) {
            this.yazdirhoca(this.table);

        } else if (secim.equalsIgnoreCase("Dersler Tablosu")) {
            this.yazdirdersler(this.table);

        } else if (secim.equalsIgnoreCase("Belge Tablosu")) {
            this.yazdirbelge(this.table);

        } else if (secim.equalsIgnoreCase("Yönetici Tablosu")) {
            this.yazdiryonetici(this.table);

        } else if (secim.equalsIgnoreCase("Dersler-Hocalar Tablosu")) {
            this.yazdirdershoca(this.table);

        } else if (secim.equalsIgnoreCase("Kriter Tablosu")) {
            this.yazdirkriter(this.table);

        } else if (secim.equalsIgnoreCase("Anlaþma Tablosu")) {
            this.yazdiranlasma(this.table);

        } else if (secim.equalsIgnoreCase("Ýlgi Alanlarý Tablosu")) {
            this.yazdirilgiler(this.table);

        } else if (secim.equalsIgnoreCase("Öðrenci Ýlgi Alanlarý Tablosu")) {
            this.yazdiroilgi(this.table);

        } else if (secim.equalsIgnoreCase("Öðretmen Ýlgi Alanlarý Tablosu")) {
            this.yazdirhilgi(this.table);

        } else if (secim.equalsIgnoreCase("Notlar Tablosu")) {
            this.yazdirnot(this.table);

        } else if (secim.equalsIgnoreCase("Sistem Tablosu")) {
            this.yazdirsistem(this.table);

        } else if (secim.equalsIgnoreCase("Geçmiþ Tablosu")) {

            this.yazdirgecmis(this.table);

        } else if (secim.equalsIgnoreCase("Random Öðrenciler")) {
            this.yazdirrandom(this.table);
        } else if (secim.equalsIgnoreCase("Mesaj Tablosu")) {
            this.yazdirmesaj(this.table);
        }

    }//GEN-LAST:event_tablolarboxActionPerformed

    private void sistemguncelboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sistemguncelboxActionPerformed

        String secim;
        String genel;
        int ilkgun;

        Boolean durum = null;
        Connection c = bglnt.dbcon();
        Statement st1 = null;
        String query = "select * from \"Sistem\"";
        ResultSet rs = bilgial(query);
        secim = (String) sistemguncelbox.getSelectedItem();

        try {
            st1 = c.createStatement();
            while (rs.next()) {

                if (secim.equalsIgnoreCase(("Mesaj Karakter Sýnýrý"))) {
                    genel = JOptionPane.showInputDialog("Maximum Karakter Sayýsýný Giriniz");
                    ilkgun = Integer.parseInt(genel);
                    st1.executeUpdate("update \"Sistem\" set\"karakterSiniri\"=" + ilkgun + "  where \"sistemIndex\"=0 ");
                    JOptionPane.showMessageDialog(null, "Girdiðiniz Veritabanýna Eklenmiþtir");

                } else if (secim.equalsIgnoreCase(("Varsayýlan Talep"))) {
                    genel = JOptionPane.showInputDialog("Varsayýlan Talep Sayýsýný Giriniz");
                    ilkgun = Integer.parseInt(genel);
                    st1.executeUpdate("update \"Sistem\" set\"varsayilanTalep\"=" + ilkgun + " where \"sistemIndex\"=0 ");
                    JOptionPane.showMessageDialog(null, "Girdiðiniz Veritabanýna Eklenmiþtir");

                } else if (secim.equalsIgnoreCase(("Aktif Pasif Durumu"))) {
                    genel = JOptionPane.showInputDialog("Aktif Pasif Durumunu Giriniz");

                    if (genel.equalsIgnoreCase("Aktif")) {
                        durum = true;
                    } else if (genel.equalsIgnoreCase("Pasif")) {
                        durum = false;
                    }
                    st1.executeUpdate("update \"Sistem\" set\"aktifPasif\"=" + durum + " where \"sistemIndex\"=0 ");
                    JOptionPane.showMessageDialog(null, "Girdiðiniz Veritabanýna Eklenmiþtir");

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_sistemguncelboxActionPerformed

    private void ogreklebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogreklebtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;

        String ad, soyad, sifre, not;
        double gno;
        String id;
        int no;

        id = JOptionPane.showInputDialog("Öðrenci ID Giriniz");
        ad = JOptionPane.showInputDialog("Öðrenci Adýný Giriniz");
        soyad = JOptionPane.showInputDialog("Öðrenci Soyadýný Giriniz");
        sifre = JOptionPane.showInputDialog("Öðrenci Þifresini Giriniz");
        not = JOptionPane.showInputDialog("Öðrenci GNO Giriniz");
        gno = Double.parseDouble(not);

        no = Integer.parseInt(id);
        try {
            st1 = c.createStatement();

            st1.executeUpdate("insert into \"Ogrenciler\" (\"ogrNo\",\"ogrAd\",\"ogrSoyad\",\"ogrSifre\",\"GNO\",\"toplamTalep\",\"talepOnay\" ) values ('" + no + "','" + ad + "','" + soyad + "','" + sifre + "'," + gno + ",0,0)");
            JOptionPane.showMessageDialog(null, "Yeni Öðrenci Veritabanýna Eklenmiþtir");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st1 != null) {
                    st1.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_ogreklebtnActionPerformed

    private void hocaguncelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hocaguncelbtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;

        int sec, yer, sira, veri;
        String kisi, id, secim, genel;
        double gno;
        JComboBox<String> cb1 = new JComboBox<>();
        String liste[] = {"Öðretmen Ad", "Öðretmen Soyad", "Öðretmen Þifre", "Kontenjan"};
        JComboBox cb = new JComboBox(liste);

        ResultSet rs = bilgial("select * from \"Hocalar\" ");

        try {
            while (rs.next()) {

                cb1.addItem(rs.getInt("sicilNo") + "-" + rs.getString("hAd") + " " + rs.getString("hSoyad"));
            }

            sec = JOptionPane.showConfirmDialog(this, cb1, "Bilgisini Güncellemek Ýstediðiniz Kiþiyi Seçiniz ", JOptionPane.DEFAULT_OPTION);
            kisi = (String) cb1.getSelectedItem();
            yer = kisi.indexOf("-");
            System.out.println(yer);
            id = kisi.substring(0, yer);
            System.out.println(id);
            st1 = c.createStatement();

            sira = JOptionPane.showConfirmDialog(this, cb, "Güncellemek Ýstediðiniz Bilgiyi Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();

            if (secim.equalsIgnoreCase("Öðretmen Ad")) {

                genel = JOptionPane.showInputDialog("Yeni Öðretmen Adýný Giriniz");

                st1.executeUpdate("update \"Hocalar\" set\"hAd\"='" + genel + "' where \"sicilNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Ýsim Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Öðretmen Soyad")) {
                genel = JOptionPane.showInputDialog("Yeni ÖÐretmen Soyadýný Giriniz");

                st1.executeUpdate("update \"Hocalar\" set\"hSoyad\"='" + genel + "' where \"sicilNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Soyisim Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Öðretmen Þifre")) {
                genel = JOptionPane.showInputDialog("Yeni Þifreyi Giriniz");

                st1.executeUpdate("update \"Hocalar\" set\"hSifre\"='" + genel + "' where \"sicilNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Þifre Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Kontenjan")) {
                genel = JOptionPane.showInputDialog("Yeni Sayýyý Giriniz");
                veri = Integer.parseInt(genel);
                st1.executeUpdate("update \"Hocalar\" set\"kontenjan\"=" + veri + " where \"sicilNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Deðer Veritabanýnda Güncellenmiþtir");

            }

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_hocaguncelbtnActionPerformed
    }
    private void hocasilbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hocasilbtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;

        String secim, kisi;
        int sira, sec, yer, no;

        Statement st2 = null;
        Statement st3 = null;
        Statement st4 = null;
        Statement st5 = null;
        String id = null;

        JComboBox<String> cb1 = new JComboBox<>();

        ResultSet rs = bilgial("select * from \"Hocalar\" ");
        ResultSet rs1 = bilgial("select * from \"Hocalar\"");

        try {
            while (rs.next()) {

                cb1.addItem(rs.getInt("sicilNo") + "-" + rs.getString("hAd") + " " + rs.getString("hSoyad"));
            }

            sec = JOptionPane.showConfirmDialog(this, cb1, "Lütfen Kiþi Seçiniz", JOptionPane.DEFAULT_OPTION);
            kisi = (String) cb1.getSelectedItem();
            yer = kisi.indexOf("-");
            System.out.println(yer);
            id = kisi.substring(0, yer);
            System.out.println(id);

            no = Integer.parseInt(id);

            st1 = c.createStatement();
            st2 = c.createStatement();
            st3 = c.createStatement();
            st4 = c.createStatement();
            st5 = c.createStatement();
            while (rs1.next()) {

                if (no == (rs1.getInt("sicilNo"))) {

                    st2.executeUpdate("delete from \"dersHoca\" where \"sicilNo\"=" + no + " ");
                    st3.executeUpdate("delete from \"HIlgi\" where \"sicilNo\"=" + no + " ");
                    st4.executeUpdate("delete from \"HFormul\" where \"sicilNo\"=" + no + " ");
                    st5.executeUpdate("delete from \"Anlasma\" where \"sicilNo\"=" + no + " ");
                    st1.executeUpdate("delete from \"Hocalar\" where\"sicilNo\"=" + no + " ");
                    JOptionPane.showMessageDialog(null, "Seçtiðiniz Kiþi Veritabanýndan Silinmiþtir");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs1 != null) {
                    rs1.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (st3 != null) {
                    st3.close();
                }
                if (st4 != null) {
                    st4.close();
                }
                if (st5 != null) {
                    st5.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_hocasilbtnActionPerformed

    private void hocaeklebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hocaeklebtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;
        Statement st2 = null;

        String ad, soyad, sifre, not, no;
        int kota;
        int sicil;
        no = JOptionPane.showInputDialog("Öðretmen Sicil Numarasýný Giriniz");
        sicil = Integer.parseInt(no);
        ad = JOptionPane.showInputDialog("Öðretmen Adýný Giriniz");
        soyad = JOptionPane.showInputDialog("Öðretmen Soyadýný Giriniz");
        sifre = JOptionPane.showInputDialog("Öðretmen Þifresini Giriniz");
        not = JOptionPane.showInputDialog("Kontenjan Sayýsýný Giriniz");
        kota = Integer.parseInt(not);

        try {
            st1 = c.createStatement();
            st2 = c.createStatement();
            st1.executeUpdate("insert into \"Hocalar\" (\"sicilNo\",\"hAd\",\"hSoyad\",\"hSifre\",\"kontenjan\") values (" + sicil + ",'" + ad + "','" + soyad + "','" + sifre + "'," + kota + ")");
            st2.executeUpdate("insert into \"dersHoca\" (\"sicilNo\",\"dersID\") values(" + sicil + ",'41') ");
            st2.executeUpdate("insert into \"dersHoca\" (\"sicilNo\",\"dersID\") values(" + sicil + ",'42') ");

            JOptionPane.showMessageDialog(null, "Yeni Öðretmen Veritabanýna Eklenmiþtir");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st1 != null) {
                    st1.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_hocaeklebtnActionPerformed
    }
    private void ogrguncelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogrguncelbtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;

        int sec, yer, sira, veri;
        String kisi, id, secim, genel;
        double gno;
        JComboBox<String> cb1 = new JComboBox<>();
        String liste[] = {"Öðrenci Ad", "Öðrenci Soyad", "Öðrenci Þifre", "Öðrenci GNO", "Toplam Talep Sayýsý", "Onaylanan Talep Sayýsý"};
        JComboBox cb = new JComboBox(liste);

        ResultSet rs = bilgial("select * from \"Ogrenciler\" ");

        try {
            while (rs.next()) {

                cb1.addItem(rs.getInt("ogrNo") + "-" + rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));
            }

            sec = JOptionPane.showConfirmDialog(this, cb1, "Bilgisini Güncellemek Ýstediðiniz Kiþiyi Seçiniz ", JOptionPane.DEFAULT_OPTION);
            kisi = (String) cb1.getSelectedItem();
            yer = kisi.indexOf("-");
            System.out.println(yer);
            id = kisi.substring(0, yer);
            System.out.println(id);
            st1 = c.createStatement();

            sira = JOptionPane.showConfirmDialog(this, cb, "Güncellemek Ýstediðiniz Bilgiyi Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();

            if (secim.equalsIgnoreCase("Öðrenci Ad")) {

                genel = JOptionPane.showInputDialog("Yeni Öðrenci Adýný Giriniz");

                st1.executeUpdate("update \"Ogrenciler\" set\"ogrAd\"='" + genel + "' where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Ýsim Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Öðrenci Soyad")) {
                genel = JOptionPane.showInputDialog("Yeni Öðrenci Soyadýný Giriniz");

                st1.executeUpdate("update \"Ogrenciler\" set\"ogrSoyad\"='" + genel + "' where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Soyisim Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Öðrenci Þifre")) {
                genel = JOptionPane.showInputDialog("Yeni Þifreyi Giriniz");

                st1.executeUpdate("update \"Ogrenciler\" set\"ogrSifre\"='" + genel + "' where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni Þifre Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Öðrenci Gno")) {
                genel = JOptionPane.showInputDialog("Yeni GNO Giriniz");
                gno = Double.parseDouble(genel);
                st1.executeUpdate("update \"Ogrenciler\" set\"GNO\"=" + gno + " where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Yeni GNO Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Toplam Talep Sayýsý")) {
                genel = JOptionPane.showInputDialog("Yeni Sayýyý Giriniz");
                veri = Integer.parseInt(genel);
                st1.executeUpdate("update \"Ogrenciler\" set\"toplamTalep\"=" + veri + " where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Deðer Veritabanýnda Güncellenmiþtir");
            } else if (secim.equalsIgnoreCase("Onaylanan Talep Sayýsý")) {
                genel = JOptionPane.showInputDialog("Yeni Sayýyý Giriniz");
                veri = Integer.parseInt(genel);
                st1.executeUpdate("update \"Ogrenciler\" set\"talepOnay\"=" + veri + " where \"ogrNo\"=" + id + " ");
                JOptionPane.showMessageDialog(null, "Girdiðiniz Deðer Veritabanýnda Güncellenmiþtir");

            }

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ogrguncelbtnActionPerformed

    private void ogrsilbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogrsilbtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st1 = null;
        Statement st2 = null;
        Statement st3 = null;
        Statement st4 = null;

        String secim, kisi;
        int sira, sec, yer, no;

        String id = null;
        JComboBox<String> cb1 = new JComboBox<>();
        String liste[] = {"Belirli Bir Kiþi Sil", "Tüm Kiþileri Sil"};
        JComboBox cb = new JComboBox(liste);

        ResultSet rs = bilgial("select * from \"Ogrenciler\" ");
        ResultSet rs1 = bilgial("select * from \"Ogrenciler\"");

        sira = JOptionPane.showConfirmDialog(this, cb, "Yapmak Ýstediðiniz Ýþlemi Seçiniz", JOptionPane.DEFAULT_OPTION);
        secim = (String) cb.getSelectedItem();

        if (secim.equalsIgnoreCase("Belirli Bir Kiþi Sil")) {

            try {
                while (rs.next()) {

                    cb1.addItem(rs.getInt("ogrNo") + "-" + rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));
                }

                sec = JOptionPane.showConfirmDialog(this, cb1, "Lütfen Kiþi Seçiniz", JOptionPane.DEFAULT_OPTION);
                kisi = (String) cb1.getSelectedItem();
                yer = kisi.indexOf("-");
                System.out.println(yer);
                id = kisi.substring(0, yer);
                System.out.println(id);

                no = Integer.parseInt(id);

                st1 = c.createStatement();
                st2 = c.createStatement();
                st3 = c.createStatement();
                st4 = c.createStatement();
                while (rs1.next()) {

                    if (no == (rs1.getInt("ogrNo"))) {

                        st2.executeUpdate("delete from \"Belge\" where \"ogrNo\"=" + no + "  ");
                        st3.executeUpdate("delete from \"OIlgi\" where \"ogrNo\"=" + no + "");
                        st4.executeUpdate("delete from \"Anlasma\" where \"ogrNo\"=" + no + " ");
                        st1.executeUpdate("delete from \"Ogrenciler\" where\"ogrNo\"=" + no + " ");

                        JOptionPane.showMessageDialog(null, "Seçtiðiniz Kiþi Veritabanýndan Silinmiþtir");

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (rs1 != null) {
                        rs1.close();
                    }
                    if (st1 != null) {
                        st1.close();
                    }
                    if (st2 != null) {
                        st2.close();
                    }
                    if (st3 != null) {
                        st3.close();
                    }
                    if (st4 != null) {
                        st4.close();
                    }
                    if (c != null) {
                        c.close();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(yoneticiFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else if (secim.equalsIgnoreCase("Tüm Kiþileri Sil")) {

            try {
                st1 = c.createStatement();
                st2 = c.createStatement();
                st3 = c.createStatement();
                st4 = c.createStatement();
                st2.executeUpdate("delete from \"Belge\" ");
                st3.executeUpdate("delete from \"Anlasma\" ");
                st4.executeUpdate("delete from \"OIlgi\" ");
                st1.executeUpdate("delete from \"Ogrenciler\"");

                JOptionPane.showMessageDialog(null, "Tüm Veritabaný Silinmiþtir");

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (rs1 != null) {
                        rs1.close();
                    }
                    if (st1 != null) {
                        st1.close();
                    }
                    if (st2 != null) {
                        st2.close();
                    }
                    if (st3 != null) {
                        st3.close();
                    }
                    if (st4 != null) {
                        st4.close();
                    }
                    if (c != null) {
                        c.close();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(yoneticiFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_ogrsilbtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String secim;
        int sayi;
        Connection c = bglnt.dbcon();
        Statement st1 = null;

        secim = JOptionPane.showInputDialog("Kaç Tane Öðrenci Üretmek Ýstiyorsunuz?");
        sayi = Integer.parseInt(secim);

        String isim[] = {"Musab", "Seha", "Ömer", "Talha", "Yusuf", "Taha", "Buse", "Nurdan", "Beyza", "Nur", "Selin", "Salih", "Esra", "Ayþe",
            "Alparslan", "Merve", "Rahime", "Demet", "Özgür", "Ýbrahim", "Meltem", "Meryem", "Onur", "Ceylan", "Ýlker", "Kaan", "Emir", "Can",
            "Burçin", "Baþak", "Burak", "Bahadýr", "Eyüp", "Emre", "Beyazýd", "Engin", "Cenk", "Funda", "Ecrin", "Defne", "Zümra", "Akif", "Mücahit",
            "Miraç", "Aras", "Çýnar", "Hamza", "Yiðit", "Umut", "Alperen", "Mert", "Safa", "Levent", "Furkan", "Sefer", "Yasir", "Sema", "Melek", "Þeyma",
            "Tuðba", "Neslihan", "Kübra", "Aleyna", "Eda", "Seda", "Ýrem", "Þule", "Sena", "Ece", "Zeynep", "Betül", "Emine", "Elif", "Melike", "Nurþifa",
            "Yaren", "Firdevs", "Berk", "Rabia", "Çaðrý"};

        String soyad[] = {"Þen", "Kandemir", "Çevik", "Öztürk", "Yücel", "Vural", "Akbulut", "Dede", "Yavuz", "Kaya", "Özdemir", "Öcal", "Yýlmaz", "Sezer",
            "Doðan", "Taþ", "Erdemsoy", "Taþer", "Baþ", "Kýlýç", "Gümüþ", "Yaman", "Dal", "Yiðit", "Gökdemir", "San",
            "San", "Sözcü", "Keleþ", "Akyüz", "Sarýyar", "Günay", "Turgut", "Tekin", "Demir", "Çelik",
            "Aydoðan", "Kaleli", "Ýðrek", "Güler", "Kurt", "Ergün", "Aköz", "Baladýn",
            "Güneþ", "Karaman", "Kaplan", "Kartal", "Þahin", "Erduran", "Çekceoðlu", "Tok",
            "Devam", "Koz", "Boylu", "Þenyurt", "Alp", "Gürenci"};

        for (int i = 0; i < sayi; i++) {
            Random r = new Random();
            int risim = (int) (Math.random() * isim.length);
            int rsoyad = (int) (Math.random() * soyad.length);
            double rgno = (Math.random() * 2) + 2.0;
            Ogrenci rndogr = new Ogrenci();
            String str = Double.toString(rgno);

            double str1 = Double.parseDouble(str.substring(0, 4));
            rndogr.setOgrAd(isim[risim]);
            rndogr.setOgrSoyad(soyad[rsoyad]);
            rndogr.setOgrSifre((rndogr.getOgrAd().charAt(0) + rndogr.getOgrSoyad()).toLowerCase());
            rndogr.setGNO(str1);

            try {

                st1 = c.createStatement();
                st1.executeUpdate("insert into\"Ogrenciler\" (\"ogrAd\",\"ogrSoyad\",\"ogrSifre\",\"GNO\",\"toplamTalep\",\"talepOnay\") Values('" + rndogr.getOgrAd() + "','" + rndogr.getOgrSoyad() + "','" + rndogr.getOgrSifre() + "','" + rndogr.getGNO() + "' ,'" + 0 + "','" + 0 + "')       ");
                dersata();
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (st1 != null) {
                        st1.close();
                    }
                    //c.close();

                } catch (SQLException ex) {
                    Logger.getLogger(yoneticiFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void ogrhocagorboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogrhocagorboxActionPerformed
        String secim = null;

        secim = (String) ogrhocagorbox.getSelectedItem();
        if (secim.equalsIgnoreCase("Öðrencileri Görüntüle")) {
            this.yazdirogrenci(this.ogrhocatable);
        } else if (secim.equalsIgnoreCase("Öðretmenleri Görüntüle")) {
            this.yazdirhoca(this.ogrhocatable);

        }
    }//GEN-LAST:event_ogrhocagorboxActionPerformed

    private void tabanitemizlebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabanitemizlebtnActionPerformed
        Connection c = bglnt.dbcon();
        Statement st = null;
        Statement st1 = null;
        ResultSet rs = bilgial("select * from \"Hocalar\" ");
        try {

            st = c.createStatement();
            st1 = c.createStatement();
            st.executeUpdate("delete from \"Random\" ");
            while (rs.next()) {

                for (int i = 0; i < hc.size(); i++) {
                    if (rs.getInt("sicilNo") == hc.get(i).getSicil()) {

                        st1.executeUpdate("update \"Hocalar\" set \"kontenjan\"=" + hc.get(i).getKota() + " where \"sicilNo\"=" + hc.get(i).getSicil() + "  ");

                    }
                }

            }

            JOptionPane.showMessageDialog(null, "Veritabaný Silinmiþtir!");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_tabanitemizlebtnActionPerformed

    private void rastgeleatamabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rastgeleatamabtnActionPerformed
        Connection c = bglnt.dbcon();
        ResultSet rs41 = bilgial("select \"ogrNo\" from \"Anlasma\" where \"dersID\"=41 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs42 = bilgial("select \"ogrNo\" from \"Anlasma\" where \"dersID\"=42 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs = bilgial("select \"ogrNo\" from \"Ogrenciler\"");
        ResultSet rs41hoca = bilgial("select \"sicilNo\" from \"Hocalar\"  ");

        ResultSet kota1 = null;
        ResultSet kota2 = null;
        ResultSet kota3 = null;
        ResultSet kota4 = null;
        Statement st1 = null;
        Statement st = null;
        Statement st2 = null;
        Statement st3 = null;
        Statement st4 = null;

        ArrayList<Integer> ders41 = new ArrayList<>();
        ArrayList<Integer> ders42 = new ArrayList<>();
        ArrayList<Integer> dersbos = new ArrayList<>();
        ArrayList<Integer> hoca = new ArrayList<>();

        ArrayList<Integer> tum = new ArrayList<>();
        try {
            st = c.createStatement();
            st1 = c.createStatement();
            st2 = c.createStatement();
            st3 = c.createStatement();
            st4 = c.createStatement();

            while (rs41.next()) {

                ders41.add(rs41.getInt("ogrNo"));

            }
            while (rs42.next()) {

                ders42.add(rs42.getInt("ogrNo"));
            }
            while (rs41hoca.next()) {

                hoca.add(rs41hoca.getInt("sicilNo"));
            }

            while (rs.next()) {

                if ((!ders41.contains(rs.getInt("ogrNo"))) && (!ders42.contains(rs.getInt("ogrNo")))) {

                    dersbos.add(rs.getInt("ogrNo"));

                }
                if ((ders41.contains(rs.getInt("ogrNo"))) && (ders42.contains(rs.getInt("ogrNo")))) {
                    tum.add(rs.getInt("ogrNo"));
                    ders41.remove(Integer.valueOf(rs.getInt("ogrNo")));
                    ders42.remove(Integer.valueOf(rs.getInt("ogrNo")));
                }

            }

            Random r = new Random();

            int sayac = 0;
            int sayac41 = ders41.size();
            while (true) {

                if (ders41.size() != 0) {
                    int h42 = (int) (Math.random() * hoca.size());
                    kota1 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hoca.get(h42) + " and \"kontenjan\" > 0 ");
                    if (kota1.next()) {
                        int o41 = (int) (Math.random() * ders41.size());
                        st.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hoca.get(h42) + "'  ");
                        st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ders41.get(o41) + "', '" + hoca.get(h42) + "','42' ) ");
                        ders41.remove(o41);
                        sayac++;

                    }
                    if (sayac == sayac41) {
                        break;
                    }

                }
            }
            if (ders42.size() != 0) {
                int sayac2 = 0;
                int sayac42 = ders42.size();
                while (true) {

                    int h41 = (int) (Math.random() * hoca.size());
                    kota2 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hoca.get(h41) + " and \"kontenjan\" > 0 ");
                    if (kota2.next()) {
                        int o42 = (int) (Math.random() * ders42.size());
                        st1.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hoca.get(h41) + "'  ");

                        st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ders42.get(o42) + "', '" + hoca.get(h41) + "','41' ) ");
                        ders42.remove(o42);
                        sayac2++;

                    }
                    if (sayac2 == sayac42) {
                        break;
                    }

                }
            }

            System.out.println("h41 : " + hoca);

            if (dersbos.size() != 0) {
                int sayac3 = 0;
                int sayac43 = dersbos.size();
                while (true) {

                    int h413 = (int) (Math.random() * hoca.size());
                    kota3 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hoca.get(h413) + " and \"kontenjan\" > 0 ");
                    if (kota3.next()) {
                        int h423 = (int) (Math.random() * hoca.size());
                        kota4 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hoca.get(h423) + " and \"kontenjan\" > 0 ");

                        if (kota4.next()) {
                            int otum = (int) (Math.random() * dersbos.size());
                            st2.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hoca.get(h413) + "'  ");
                            st3.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hoca.get(h423) + "'  ");

                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + dersbos.get(otum) + "', '" + hoca.get(h413) + "','41' ) ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + dersbos.get(otum) + "', '" + hoca.get(h423) + "','42' ) ");

                            dersbos.remove(otum);
                            sayac3++;

                        }

                    }
                    if (sayac3 == sayac43) {
                        break;
                    }

                }
            }
            JOptionPane.showMessageDialog(null, "Rastgele Öðrenciler Atanmýþtýr.");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs41 != null) {
                    rs41.close();
                }
                if (rs42 != null) {
                    rs42.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (rs41hoca != null) {
                    rs41hoca.close();
                }
                if (kota1 != null) {
                    kota1.close();
                }
                if (kota2 != null) {
                    kota2.close();
                }
                if (rs41 != null) {
                    kota3.close();
                }
                if (kota4 != null) {
                    kota4.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (st != null) {
                    st.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (st3 != null) {
                    st3.close();
                }
                if (st4 != null) {
                    st4.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_rastgeleatamabtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        bilgipanel();
    }//GEN-LAST:event_jButton2ActionPerformed

    public void ata() {
        Connection c = bglnt.dbcon();
        ResultSet rs41 = bilgial("select * from \"Ogrenciler\" left join \"Anlasma\" on \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"  where \"dersID\"=41 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs42 = bilgial("select * from \"Ogrenciler\" left join \"Anlasma\" on  \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"  where \"dersID\"=42 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs = bilgial("select * from \"Ogrenciler\"");
        ResultSet rs41hoca = bilgial("select *  from \"Hocalar\"  ");
        ful.clear();
        ders41.clear();
        ders42.clear();
        dersbos.clear();
        hoca.clear();
        int no;
        ArrayList<Integer> dr41 = new ArrayList<>();
        ArrayList<Integer> dr42 = new ArrayList<>();

        ArrayList<Student> tum = new ArrayList<>();
        ArrayList<Hoca> hocalar = new ArrayList<>();
        try {
            Hoca hoca1;
            Student student;
            while (rs41.next()) {
                student = new Student(rs41.getInt("ogrNO"), rs41.getDouble("GNO"), rs41.getString("ogrAd") + " " + rs41.getString("ogrSoyad"));
                dr41.add(rs41.getInt("ogrNo"));
                ders41.add(student);

            }
            while (rs42.next()) {
                student = new Student(rs42.getInt("ogrNO"), rs42.getDouble("GNO"), rs42.getString("ogrAd") + " " + rs42.getString("ogrSoyad"));
                dr42.add(rs42.getInt("ogrNo"));

                ders42.add(student);
            }
            while (rs41hoca.next()) {
                hoca1 = new Hoca(rs41hoca.getInt("sicilNo"), rs41hoca.getString("hAd") + " " + rs41hoca.getString("hSoyad"), rs41hoca.getInt("kontenjan"));
                hocalar.add(hoca1);
                hoca.add(rs41hoca.getInt("sicilNo"));
            }

            while (rs.next()) {

                if ((!dr41.contains(rs.getInt("ogrNo"))) && (!dr42.contains(rs.getInt("ogrNo")))) {
                    student = new Student(rs.getInt("ogrNO"), rs.getDouble("GNO"), rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));

                    dersbos.add(student);

                }
                if ((dr41.contains(rs.getInt("ogrNo"))) && (dr42.contains(rs.getInt("ogrNo")))) {

                    student = new Student(rs.getInt("ogrNO"), rs.getDouble("GNO"), rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));

                    tum.add(student);

                    no = rs.getInt("ogrNo");

                    for (int i = 0; i < ders41.size(); i++) {

                        if (no == ders41.get(i).getOgrid()) {
                            ders41.remove(i);
                        }
                    }
                    for (int i = 0; i < ders42.size(); i++) {
                        if (no == ders42.get(i).getOgrid()) {
                            ders42.remove(i);
                        }
                    }

                }
            }

            ful.addAll(ders41);
            ful.addAll(ders42);
            ful.addAll(dersbos);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs41 != null) {
                    rs41.close();
                }
                if (rs42 != null) {
                    rs42.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (rs41hoca != null) {
                    rs41hoca.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        Connection c = bglnt.dbcon();
        ResultSet rs41 = bilgial("select * from \"Ogrenciler\" left join \"Anlasma\" on \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"  where \"dersID\"=41 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs42 = bilgial("select * from \"Ogrenciler\" left join \"Anlasma\" on  \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"  where \"dersID\"=42 and \"durum\"='{Kabul Edildi}'");
        ResultSet rs = bilgial("select * from \"Ogrenciler\"");
        ResultSet rs41hoca = bilgial("select *  from \"Hocalar\"  ");
        ful.clear();
        ders41.clear();
        ders42.clear();
        dersbos.clear();
        hoca.clear();
        int no;
        ArrayList<Integer> dr41 = new ArrayList<>();
        ArrayList<Integer> dr42 = new ArrayList<>();

        ArrayList<Student> tum = new ArrayList<>();
        ArrayList<Hoca> hocalar = new ArrayList<>();
        try {
            Hoca hoca1;
            Student student;
            while (rs41.next()) {
                student = new Student(rs41.getInt("ogrNO"), rs41.getDouble("GNO"), rs41.getString("ogrAd") + " " + rs41.getString("ogrSoyad"));
                dr41.add(rs41.getInt("ogrNo"));
                ders41.add(student);

            }
            while (rs42.next()) {
                student = new Student(rs42.getInt("ogrNO"), rs42.getDouble("GNO"), rs42.getString("ogrAd") + " " + rs42.getString("ogrSoyad"));
                dr42.add(rs42.getInt("ogrNo"));

                ders42.add(student);
            }
            while (rs41hoca.next()) {
                hoca1 = new Hoca(rs41hoca.getInt("sicilNo"), rs41hoca.getString("hAd") + " " + rs41hoca.getString("hSoyad"), rs41hoca.getInt("kontenjan"));
                hocalar.add(hoca1);
                hoca.add(rs41hoca.getInt("sicilNo"));
            }

            while (rs.next()) {

                if ((!dr41.contains(rs.getInt("ogrNo"))) && (!dr42.contains(rs.getInt("ogrNo")))) {
                    student = new Student(rs.getInt("ogrNO"), rs.getDouble("GNO"), rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));

                    dersbos.add(student);

                }
                if ((dr41.contains(rs.getInt("ogrNo"))) && (dr42.contains(rs.getInt("ogrNo")))) {

                    student = new Student(rs.getInt("ogrNO"), rs.getDouble("GNO"), rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));

                    tum.add(student);

                    no = rs.getInt("ogrNo");

                    for (int i = 0; i < ders41.size(); i++) {

                        if (no == ders41.get(i).getOgrid()) {
                            ders41.remove(i);
                        }
                    }
                    for (int i = 0; i < ders42.size(); i++) {
                        if (no == ders42.get(i).getOgrid()) {
                            ders42.remove(i);
                        }
                    }

                }
            }

            ful.addAll(ders41);
            ful.addAll(ders42);
            ful.addAll(dersbos);

            Collections.sort(ful, Comparator.comparingDouble(Student::getNot).reversed());

            yazdirgnoogr(studenttable, ful);

            yazdirhocaa(teachtable, hoca);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs41 != null) {
                    rs41.close();
                }
                if (rs42 != null) {
                    rs42.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (rs41hoca != null) {
                    rs41hoca.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void yukaribtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yukaribtnActionPerformed

        if (sectable == false) {
            model = (DefaultTableModel) teachtable.getModel();
            sectable = true;
        }

        index = teachtable.getSelectedRow();
        if (index > 0) {

            model.moveRow(index, index, index - 1);

            teachtable.setRowSelectionInterval(index - 1, index - 1);

        }


    }//GEN-LAST:event_yukaribtnActionPerformed

    private void asagibtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asagibtnActionPerformed
        if (sectable == false) {
            model = (DefaultTableModel) teachtable.getModel();
            sectable = true;
        }

        index = teachtable.getSelectedRow();
        if (index < model.getRowCount() - 1) {
            model.moveRow(index, index, index + 1);
            teachtable.setRowSelectionInterval(index + 1, index + 1);

        }


    }//GEN-LAST:event_asagibtnActionPerformed


    private void olusturbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_olusturbtnActionPerformed
        Connection c = bglnt.dbcon();
        ResultSet rs = null;
        ResultSet kota1 = null;
        ResultSet kota2 = null;
        Statement st = null;
        Statement st1 = null;
        Statement st2 = null;
        Statement st3 = null;
        Statement st4 = null;
        try {
            int satir = model.getRowCount();
            int sutun = model.getColumnCount();
            hocaaa.clear();
            st = c.createStatement();
            st1 = c.createStatement();
            st2 = c.createStatement();
            st3 = c.createStatement();
            st4 = c.createStatement();
            for (int i = 0; i < satir; i++) {
                Vector rowData = model.getDataVector().get(i);
                for (int j = 0; j < 1; j++) {
                    Object value = rowData.get(j);
                    hocaaa.add((Integer) rowData.get(j));
                }

            }
            int j = 0;
            for (int i = 0; i < ful.size(); i++) {
                {

                    if (ders41.contains(ful.get(i))) {
                        int no = ful.get(i).getOgrid();

                        rs = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (rs.next()) {

                            st.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ful.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','42' ) ");
                            Student scc = new Student(ful.get(i).getOgrid(), ful.get(i).getNot(), ful.get(i).getAd());

                            int scx = indexOf(ders41, scc);

                            ders41.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }
                    if (ders42.contains(ful.get(i))) {
                        int no = ful.get(i).getOgrid();

                        kota1 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (kota1.next()) {

                            st1.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ful.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','41' ) ");
                            Student scc = new Student(ful.get(i).getOgrid(), ful.get(i).getNot(), ful.get(i).getAd());

                            int scx = indexOf(ders42, scc);

                            ders42.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }
                    if (dersbos.contains(ful.get(i))) {
                        int no = ful.get(i).getOgrid();

                        kota2 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (kota2.next()) {

                            st2.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st3.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");

                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ful.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','41' ) ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + ful.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','42' ) ");

                            Student scc = new Student(ful.get(i).getOgrid(), ful.get(i).getNot(), ful.get(i).getAd());

                            int scx = indexOf(dersbos, scc);
                            dersbos.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }

                }

            }
            JOptionPane.showMessageDialog(null, "Ýþlem Baþarýyla Sonlanmýþtýr");
            sectable = false;
            index = 0;

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (kota1 != null) {
                    kota1.close();
                }
                if (st != null) {
                    st.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (st3 != null) {
                    st3.close();
                }
                if (st4 != null) {
                    st4.close();
                }
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_olusturbtnActionPerformed

    private void formulbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulbtnActionPerformed

        Connection c = bglnt.dbcon();
        ResultSet rs = null;
        ResultSet kota1 = null;
        ResultSet kota2 = null;
        Statement st = null;
        Statement st1 = null;
        Statement st2 = null;
        Statement st3 = null;
        Statement st4 = null;
        try {
            int satir = model.getRowCount();
            int sutun = model.getColumnCount();
            hocaaa.clear();
            st = c.createStatement();
            st1 = c.createStatement();
            st2 = c.createStatement();
            st3 = c.createStatement();
            st4 = c.createStatement();
            for (int i = 0; i < satir; i++) {
                Vector rowData = model.getDataVector().get(i);
                for (int j = 0; j < 1; j++) {
                    Object value = rowData.get(j);
                    hocaaa.add((Integer) rowData.get(j));
                }

            }
            int j = 0;
            for (int i = 0; i < hepsi.size(); i++) {
                {

                    if (dersap.contains(hepsi.get(i))) {
                        int no = hepsi.get(i).getOgrid();

                        rs = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (rs.next()) {

                            st.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + hepsi.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','42' ) ");
                            Student scc = new Student(hepsi.get(i).getOgrid(), hepsi.get(i).getNot(), hepsi.get(i).getAd());

                            int scx = indexOf(dersap, scc);

                            dersap.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }
                    if (dersbp.contains(hepsi.get(i))) {
                        int no = hepsi.get(i).getOgrid();

                        kota1 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (kota1.next()) {

                            st1.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + hepsi.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','41' ) ");
                            Student scc = new Student(hepsi.get(i).getOgrid(), hepsi.get(i).getNot(), hepsi.get(i).getAd());

                            int scx = indexOf(dersbp, scc);

                            dersbp.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }
                    if (dersyok.contains(hepsi.get(i))) {
                        int no = hepsi.get(i).getOgrid();

                        kota2 = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + hocaaa.get(j) + " and \"kontenjan\" > 0 ");
                        if (kota2.next()) {

                            st2.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");
                            st3.executeUpdate("update \"Hocalar\" set\"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"='" + hocaaa.get(j) + "'  ");

                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + hepsi.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','41' ) ");
                            st4.executeUpdate("insert into \"Random\" (\"ogrNo\",\"sicilNo\",\"dersID\" ) values ('" + hepsi.get(i).getOgrid() + "', '" + hocaaa.get(j) + "','42' ) ");

                            Student scc = new Student(hepsi.get(i).getOgrid(), hepsi.get(i).getNot(), hepsi.get(i).getAd());

                            int scx = indexOf(dersyok, scc);
                            dersyok.remove(scx);

                        }
                        j++;

                        if (j == hocaaa.size()) {
                            j = 0;
                        }

                    }

                }

            }
            JOptionPane.showMessageDialog(null, "Ýþlem Baþarýyla Sonlanmýþtýr");

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (kota1 != null) {
                    kota1.close();
                }
                if (st != null) {
                    st.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (st3 != null) {
                    st3.close();
                }
                if (st4 != null) {
                    st4.close();
                }
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_formulbtnActionPerformed

    private void derssecbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derssecbtnActionPerformed

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;
        // ata();
        int deger = 0;
        int dersid = 0;
        String secim, secim1 = null;
        String katsayi = null;
        int sec;
        int flag = 0;
        int sayi;
        Statement ders = null;
        ResultSet rsders = null;

        ArrayList<ArrayList<Integer>> dersler = new ArrayList<>();
        ArrayList<Integer> satir;

        JComboBox<String> cb = new JComboBox<>();
        int sayac = 0;

        try {
            ders = c.createStatement();
            st = c.createStatement();
            secim1 = JOptionPane.showInputDialog("Kaç Tane Ders Seçmek Ýstiyorsunuz?");
            sayi = Integer.parseInt(secim1);

            rs = st.executeQuery("select * from \"Dersler\"");
            while (rs.next()) {
                cb.addItem(rs.getString("dersAd"));

            }
            for (int i = 0; i < sayi; i++) {
                satir = new ArrayList<>();
                sec = JOptionPane.showConfirmDialog(this, cb, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
                secim = (String) cb.getSelectedItem();

                rsders = ders.executeQuery("select * from \"Dersler\" left join \"dersHoca\" on \"Dersler\".\"dersID\" =\"dersHoca\".\"dersID\"");
                while (rsders.next()) {

                    if ((secim.equalsIgnoreCase(rsders.getString("dersAd")))) {
                        dersid = rsders.getInt("dersID");

                    }
                }

                katsayi = JOptionPane.showInputDialog("Katsayi Deðerini Giriniz");
                deger = Integer.parseInt(katsayi);
                satir.add(dersid);
                satir.add(deger);
                dersler.add(satir);
            }
            yazdirkritersonuc(studenttable, sayi, dersler);
            yazdirhocaa(teachtable, hoca);

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (rsders != null) {
                    rsders.close();
                }
                if (ders != null) {
                    ders.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_derssecbtnActionPerformed

    private void guncellebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncellebtnActionPerformed
        ResultSet rs = bilgial("select * from \"Hocalar\" ");
        try {

            DefaultTableModel tableders = new DefaultTableModel();
            Object[] ilgi = {"Öðretmen ID", "Öðretmen Ad", "Kontenjan"};

            tableders.setColumnIdentifiers(ilgi);

            while (rs.next()) {

                ilgi[0] = rs.getInt("sicilNo");
                ilgi[1] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                ilgi[2] = rs.getInt("kontenjan");

                tableders.addRow(ilgi);

            }

            teachtable.setModel(tableders);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_guncellebtnActionPerformed
    public void yazdirkritersonuc(JTable table, int sayac, ArrayList<ArrayList<Integer>> dersler) {

        int x = 0;
        double a = 0, b = 0, c = 0, d = 0, e = 0;
        int ak = 0, bk = 0, ck = 0, dk = 0, ek = 0;

        int satir = 0;
        double puan = 0;

        if (sayac == 0) {
            a = 0;
            b = 0;
            c = 0;
            d = 0;
            e = 0;
            ak = 0;
            bk = 0;
            ck = 0;
            dk = 0;
            ek = 0;

        }
        if (sayac == 1) {
            a = dersler.get(0).get(0);
            ak = dersler.get(0).get(1);
        }
        if (sayac == 2) {
            a = dersler.get(0).get(0);
            ak = dersler.get(0).get(1);

            b = dersler.get(1).get(0);
            bk = dersler.get(1).get(1);
        }
        if (sayac == 3) {
            a = dersler.get(0).get(0);
            ak = dersler.get(0).get(1);

            b = dersler.get(1).get(0);
            bk = dersler.get(1).get(1);

            c = dersler.get(2).get(0);
            ck = dersler.get(2).get(1);
        }
        if (sayac == 4) {
            a = dersler.get(0).get(0);
            ak = dersler.get(0).get(1);

            b = dersler.get(1).get(0);
            bk = dersler.get(1).get(1);

            c = dersler.get(2).get(0);
            ck = dersler.get(2).get(1);

            d = dersler.get(3).get(0);
            dk = dersler.get(3).get(1);
        }
        if (sayac == 5) {
            a = dersler.get(0).get(0);
            ak = dersler.get(0).get(1);

            b = dersler.get(1).get(0);
            bk = dersler.get(1).get(1);

            c = dersler.get(2).get(0);
            ck = dersler.get(2).get(1);

            d = dersler.get(3).get(0);
            dk = dersler.get(3).get(1);

            e = dersler.get(4).get(0);
            ek = dersler.get(4).get(1);

        }

        this.sonuc(a, ak, b, bk, c, ck, d, dk, e, ek);

    }

    public void sonuc(double a, int ak, double b, int bk, double c, int ck, double d, int dk, double e, int ek) {

        int no = 0;
        double toplam = 0;
        double not = 0;

        ResultSet belge = null;
        ata();

        Map<String, Double> harfnot = new HashMap<>();
        double an = 0, bn = 0, cn = 0, dn = 0, en = 0;

        harfnot.put("AA", 4.0);
        harfnot.put("BA", 3.5);
        harfnot.put("BB", 3.0);
        harfnot.put("CB", 2.5);
        harfnot.put("CC", 2.0);
        harfnot.put("DC", 1.5);
        harfnot.put("DD", 1.0);
        harfnot.put("FD", 0.5);
        harfnot.put("FF", 0.0);
        harfnot.put("G", 0.0);
        harfnot.put("O", 0.0);

        try {

            for (int i = 0; i < ders41.size(); i++) {
                Student student;
                belge = bilgial("select * from \"Belge\" where \"ogrNo\"= " + ders41.get(i).getOgrid() + "  ");
                while (belge.next()) {

                    if ((belge.getInt("ogrNo") == ders41.get(i).getOgrid()) && (belge.getInt("dersID") == (int) a)) {

                        an = harfnot.get(belge.getString("hNot"));

                        toplam = toplam + (an * ak);

                    }
                    if ((belge.getInt("ogrNo") == ders41.get(i).getOgrid()) && (belge.getInt("dersID") == (int) b)) {

                        bn = harfnot.get(belge.getString("hNot"));

                        toplam += bn * bk;
                    }
                    if ((belge.getInt("ogrNo") == ders41.get(i).getOgrid()) && (belge.getInt("dersID") == (int) c)) {

                        cn = harfnot.get(belge.getString("hNot"));

                        toplam += cn * ck;
                    }
                    if ((belge.getInt("ogrNo") == ders41.get(i).getOgrid()) && (belge.getInt("dersID") == (int) d)) {

                        dn = harfnot.get(belge.getString("hNot"));

                        toplam += dn * dk;
                    }
                    if ((belge.getInt("ogrNo") == ders41.get(i).getOgrid()) && (belge.getInt("dersID") == (int) e)) {

                        en = harfnot.get(belge.getString("hNot"));

                        toplam += en * ek;
                    }

                }

                not = toplam / (ak + bk + ck + dk + ek);
                student = new Student(ders41.get(i).getOgrid(), not, ders41.get(i).getAd());

                dersap.add(student);
                toplam = 0;
            }
            for (int i = 0; i < ders42.size(); i++) {
                Student student;
                belge = bilgial("select * from \"Belge\" where \"ogrNo\"= " + ders42.get(i).getOgrid() + "  ");
                while (belge.next()) {

                    if ((belge.getInt("ogrNo") == ders42.get(i).getOgrid()) && (belge.getInt("dersID") == (int) a)) {

                        an = harfnot.get(belge.getString("hNot"));

                        toplam = toplam + (an * ak);

                    }
                    if ((belge.getInt("ogrNo") == ders42.get(i).getOgrid()) && (belge.getInt("dersID") == (int) b)) {

                        bn = harfnot.get(belge.getString("hNot"));

                        toplam += bn * bk;
                    }
                    if ((belge.getInt("ogrNo") == ders42.get(i).getOgrid()) && (belge.getInt("dersID") == (int) c)) {

                        cn = harfnot.get(belge.getString("hNot"));

                        toplam += cn * ck;
                    }
                    if ((belge.getInt("ogrNo") == ders42.get(i).getOgrid()) && (belge.getInt("dersID") == (int) d)) {

                        dn = harfnot.get(belge.getString("hNot"));

                        toplam += dn * dk;
                    }
                    if ((belge.getInt("ogrNo") == ders42.get(i).getOgrid()) && (belge.getInt("dersID") == (int) e)) {

                        en = harfnot.get(belge.getString("hNot"));

                        toplam += en * ek;
                    }

                }

                not = toplam / (ak + bk + ck + dk + ek);
                student = new Student(ders42.get(i).getOgrid(), not, ders42.get(i).getAd());

                dersbp.add(student);
                toplam = 0;
            }
            for (int i = 0; i < dersbos.size(); i++) {
                Student student;
                belge = bilgial("select * from \"Belge\" where \"ogrNo\"= " + dersbos.get(i).getOgrid() + "  ");
                while (belge.next()) {

                    if ((belge.getInt("ogrNo") == dersbos.get(i).getOgrid()) && (belge.getInt("dersID") == (int) a)) {

                        an = harfnot.get(belge.getString("hNot"));

                        toplam = toplam + (an * ak);

                    }
                    if ((belge.getInt("ogrNo") == dersbos.get(i).getOgrid()) && (belge.getInt("dersID") == (int) b)) {

                        bn = harfnot.get(belge.getString("hNot"));

                        toplam += bn * bk;
                    }
                    if ((belge.getInt("ogrNo") == dersbos.get(i).getOgrid()) && (belge.getInt("dersID") == (int) c)) {

                        cn = harfnot.get(belge.getString("hNot"));

                        toplam += cn * ck;
                    }
                    if ((belge.getInt("ogrNo") == dersbos.get(i).getOgrid()) && (belge.getInt("dersID") == (int) d)) {

                        dn = harfnot.get(belge.getString("hNot"));

                        toplam += dn * dk;
                    }
                    if ((belge.getInt("ogrNo") == dersbos.get(i).getOgrid()) && (belge.getInt("dersID") == (int) e)) {

                        en = harfnot.get(belge.getString("hNot"));

                        toplam += en * ek;
                    }

                }

                not = toplam / (ak + bk + ck + dk + ek);
                student = new Student(dersbos.get(i).getOgrid(), not, dersbos.get(i).getAd());

                dersyok.add(student);
                toplam = 0;
            }

            hepsi.addAll(dersap);
            hepsi.addAll(dersbp);
            hepsi.addAll(dersyok);
            System.out.println("hepsi :" + hepsi);
            Collections.sort(hepsi, Comparator.comparingDouble(Student::getNot).reversed());
            this.yazdirsonuc(studenttable, hepsi);

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (belge != null) {
                    belge.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void yazdirsonuc(JTable table, ArrayList<Student> stler) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Formül Puaný"};

        tableders.setColumnIdentifiers(ilgi);

        for (int i = 0; i < stler.size(); i++) {
            ilgi[0] = stler.get(i).getOgrid();
            ilgi[1] = stler.get(i).getAd();
            ilgi[2] = stler.get(i).getNot();

            tableders.addRow(ilgi);
        }
        table.setModel(tableders);

    }

    public void bilgipanel() {

        JFrame frame = new JFrame("Atama Sonucu Yapýlan Eþleþmeler");

        JTable table = new JTable();
        yazdirrandomx(table);
        JScrollPane scrollPane = new JScrollPane(table);

        // table.setBackground(Color.);
        table.setBackground(new java.awt.Color(229, 213, 234));

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        //  frame.add(table);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void yazdirhocaa(JTable table, ArrayList<Integer> hocalar) {
        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðretmen ID", "Öðretmen Soyad", "Kontenjan"};

        tableders.setColumnIdentifiers(ilgi);
        ResultSet rs = bilgial("select * from \"Hocalar\" ");
        try {
            Hoca hoca;
            while (rs.next()) {
                for (int i = 0; i < hocalar.size(); i++) {
                    if (rs.getInt("sicilNo") == hocalar.get(i)) {
                        hoca = new Hoca(rs.getInt("sicilNo"), rs.getString("hAd") + " " + rs.getString("hSoyad"), rs.getInt("kontenjan"));

                        ilgi[0] = rs.getInt("sicilNo");
                        ilgi[1] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                        ilgi[2] = rs.getInt("kontenjan");
                        tableders.addRow(ilgi);
                    }

                }
            }

            table.setModel(tableders);
            table.setDragEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirgnoogr(JTable table, ArrayList<Student> stler) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "GNO"};

        tableders.setColumnIdentifiers(ilgi);

        for (int i = 0; i < stler.size(); i++) {
            ilgi[0] = stler.get(i).getOgrid();
            ilgi[1] = stler.get(i).getAd();
            ilgi[2] = stler.get(i).getNot();

            tableders.addRow(ilgi);
        }
        table.setModel(tableders);

    }

    public void yazdirrandomx(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Öðrenci ID", "Öðrenci Ad", "Öðretmen ID", "Öðretmen Ad", "Ders ID", "Ders Ad"};
        JTable tb = new JTable();
        String query;

        query = "select \"Ogrenciler\".\"ogrNo\", \"ogrAd\",\"ogrSoyad\",\"Dersler\".\"dersID\",\"dersAd\",\"Hocalar\".\"sicilNo\",\"hAd\",\"hSoyad\" from \"Ogrenciler\" inner join \"Random\" on \"Ogrenciler\".\"ogrNo\"=\"Random\".\"ogrNo\"\n"
                + "inner join \"Hocalar\" on \"Random\".\"sicilNo\"=\"Hocalar\".\"sicilNo\"\n"
                + "inner join \"Dersler\" on \"Random\".\"dersID\"=\"Dersler\".\"dersID\"";

        ResultSet rs = bilgial(query);

        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("ogrNo");
                belge[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                belge[2] = rs.getInt("sicilNo");
                belge[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                belge[4] = rs.getInt("dersID");
                belge[5] = rs.getString("dersAd");

                tableders.addRow(belge);

            }
            table.setModel(tableders);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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
            java.util.logging.Logger.getLogger(yoneticiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(yoneticiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(yoneticiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(yoneticiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new yoneticiFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton asagibtn;
    private javax.swing.JButton asamabtn;
    private javax.swing.JButton cksbtn;
    private javax.swing.JButton derssecbtn;
    private javax.swing.JButton formulbtn;
    private javax.swing.JButton guncellebtn;
    private javax.swing.JButton hocaeklebtn;
    private javax.swing.JButton hocaguncelbtn;
    private javax.swing.JButton hocasilbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane jpanel;
    private javax.swing.JButton ogreklebtn;
    private javax.swing.JButton ogrguncelbtn;
    private javax.swing.JComboBox<String> ogrhocagorbox;
    public static javax.swing.JTable ogrhocatable;
    private javax.swing.JButton ogrsilbtn;
    private javax.swing.JButton olusturbtn;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JButton rastgeleatamabtn;
    private javax.swing.JScrollPane scroolpaneee;
    private javax.swing.JComboBox<String> sistemguncelbox;
    private javax.swing.JTable studenttable;
    private javax.swing.JButton tabanitemizlebtn;
    private javax.swing.JTable table;
    private javax.swing.JComboBox<String> tablolarbox;
    private javax.swing.JTable teachtable;
    private javax.swing.JButton yukaribtn;
    // End of variables declaration//GEN-END:variables

    private int indexOf(ArrayList<Student> ders41, Student scc) {
        int no = 0;
        for (int i = 0; i < ders41.size(); i++) {
            if (ders41.get(i).getOgrid() == scc.getOgrid()) {

                no = i;
            }
        }

        return no;
    }
}
