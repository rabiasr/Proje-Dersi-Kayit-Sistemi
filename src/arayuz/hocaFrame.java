package arayuz;

import baglanti.baglanti;
import db.Ogretmen;
import db.Student;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class hocaFrame extends javax.swing.JFrame {

    private baglanti bglnt = new baglanti();
    private String no;
    public DefaultTableModel tabledersler = new DefaultTableModel();
    Object[] dersler = {"Ders ID", "Ders Ad", "AKTS"};
    public Ogretmen ogretmen = new Ogretmen();

    public JTable ogrtable;
    static int tab = 0;
    static int kontenjan = 0;

    public hocaFrame() {
        initComponents();
    }

    public void dersler(Ogretmen hoca) {
        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery("select * from \"Dersler\" left join \"dersHoca\" on \"Dersler\".\"dersID\" =\"dersHoca\".\"dersID\"");
            tabledersler.setColumnIdentifiers(dersler);
            while (rs.next()) {
                if (hoca.getSicilNo() == rs.getInt("sicilNo")) {
                    dersler[0] = rs.getInt("dersID");
                    dersler[1] = rs.getString("dersAd");
                    dersler[2] = rs.getDouble("AKTS");
                    tabledersler.addRow(dersler);

                }

            }
            this.hocadersleritable.setModel(tabledersler);

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

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
                // rs.close(); 
                st.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void yazdirilgi(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Ýlgi ID", "Ýlgi Ad"};

        String query;

        query = "select * from \"HIlgi\" left join \"ilgiAlanlari\" on \"HIlgi\".\"ilgiID\"=\"ilgiAlanlari\".\"ilgiID\"  where \"sicilNo\"=" + ogretmen.getSicilNo() + " ";
        ResultSet rs = bilgial(query);

        tableders.setColumnIdentifiers(ilgi);
        try {

            while (rs.next()) {

                ilgi[0] = rs.getInt("ilgiID");
                ilgi[1] = rs.getString("ilgiAd");

                tableders.addRow(ilgi);
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

    public void yazdirkriter(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Ders ID", "Ders Ad", "Katsayi"};

        String query;

        query = "select * from \"HFormul\" left join \"Dersler\" on \"HFormul\".\"dersID\"=\"Dersler\".\"dersID\"  where \"sicilNo\"=" + ogretmen.getSicilNo() + " ";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ilgi);
        try {

            while (rs.next()) {

                ilgi[0] = rs.getInt("dersID");
                ilgi[1] = rs.getString("dersAd");
                ilgi[2] = rs.getInt("katsayi");

                tableders.addRow(ilgi);
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

    public void yazdirderslerim(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Ders ID", "Ders Ad", "Durum", "Mesajý"};

        ResultSet mesaj = null;

        String query;
        int a = 0;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" "
                + "inner join \"Ogrenciler\" on \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "false" + "";
        ResultSet rs = bilgial(query);

        tableders.setColumnIdentifiers(ilgi);
        try {

            while (rs.next()) {
                ilgi[0] = rs.getInt("ogrNo");
                ilgi[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                ilgi[2] = rs.getInt("dersID");
                ilgi[3] = rs.getString("dersAd");
                ilgi[4] = rs.getString("durum");
                mesaj = bilgial("select * from \"Mesaj\" where \"kime\"=" + rs.getInt("sicilNo") + " and \"kim\"=" + rs.getInt("ogrNo") + " and \"dersID\"=" + rs.getInt("dersID") + " ");
                while (mesaj.next()) {
                    a = 1;
                    ilgi[5] = mesaj.getString("mesaj");
                }
                if (a == 0) {
                    ilgi[5] = "";
                }

                tableders.addRow(ilgi);
            }
            table.setModel(tableders);
        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (mesaj != null) {
                    mesaj.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirdersalan(JTable table, int dersid) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Ders ID", "Ders Ad"};

        String query;
        int a = 0;
        query = "select * from \"Anlasma\" left join \"Dersler\" on \"Anlasma\".\"dersID\"=\"Dersler\".\"dersID\" left join \"Ogrenciler\" on \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"  where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"durum\"='" + "{Kabul Edildi}" + "' and \"Anlasma\".\"dersID\"=" + dersid + " ";
        ResultSet rs = bilgial(query);

        tableders.setColumnIdentifiers(ilgi);
        try {

            while (rs.next()) {
                ilgi[0] = rs.getInt("ogrNo");
                ilgi[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                ilgi[2] = rs.getInt("dersID");
                ilgi[3] = rs.getString("dersAd");

                tableders.addRow(ilgi);
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

    public void taleponay(int ogrid, int dersid, int sicilno) {

        Connection c = bglnt.dbcon();
        Statement st = null;
        Statement kota = null;
        Statement onay = null;
        Statement gecmis = null;

        int index = 0;

        ResultSet rs = bilgial("select \"anlasmaNo\" from \"Anlasma\" where \"ogrNo\"=" + ogrid + " and \"dersID\"=" + dersid + " and \"sicilNo\"=" + sicilno + " and \"gonderen\"=" + "false" + " ");

        try {
            while (rs.next()) {
                index = rs.getInt("anlasmaNo");

            }

            st = c.createStatement();
            kota = c.createStatement();
            kota.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"=" + sicilno + "  ");
            onay = c.createStatement();
            onay.executeUpdate("update \"Anlasma\" set \"durum\"='" + "{Baþkasý Onayladý}" + "' where \"dersID\"=" + dersid + " and \"ogrNo\"=" + ogrid + " and  \"gonderen\"=" + "false" + "  ");
            st.executeUpdate("update \"Anlasma\" set \"durum\"='" + "{Kabul Edildi}" + "' where \"anlasmaNo\"=" + index + "  ");

            gecmis = c.createStatement();
            gecmis.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + sicilno + "' , '" + dersid + "' ,'{Kabul Edildi}','" + false + "')");

            this.bilgiyaz(ogretmen);
        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (kota != null) {
                    kota.close();
                }
                if (onay != null) {
                    onay.close();
                }
                if (gecmis != null) {
                    gecmis.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void tablobilgigor(JTable table) {

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int ogrid = 0;
                Object dersid = 0;
                JComboBox<String> talepbox = new JComboBox<>();
                JComboBox<String> dersbox = new JComboBox<>();
                Statement talep = null;
                int karakter = 0;
                Statement s = null;
                Statement gecmis = null;
                ResultSet sistem = null;
                Statement hocatalep = null;
                Statement gecmiss = null;
                Statement mesajlar = null;
                ResultSet aaa = null;
                ResultSet bbb = null;
                ResultSet ccc = null;
                ResultSet dcc = null;
                ResultSet dershoca = null;
                ResultSet rs = null;

                Connection c = bglnt.dbcon();

                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();

                    if (selectedRow != -1) {

                        try {
                            ogrid = (int) table.getValueAt(selectedRow, 0);

                            dersid = table.getValueAt(selectedRow, 2);
                            rs = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " ");

                            while (rs.next()) {
                                kontenjan = rs.getInt("kontenjan");

                            }

                            if (tab == 2) {

                                if (kontenjan > 0) {
                                    int sec;

                                    String secimders = null;
                                    talepbox.addItem("Öðrenci Bilgi Görüntüle");
                                    talepbox.addItem("Talep Ýþlemleri");
                                    sec = JOptionPane.showConfirmDialog(table, talepbox, "Yapmak Ýstediðiniz Ýþlemi Seçiniz", JOptionPane.DEFAULT_OPTION);
                                    secimders = (String) talepbox.getSelectedItem();
                                    if (secimders.equalsIgnoreCase("Öðrenci Bilgi Görüntüle")) {
                                        bilgipanel(ogrid);
                                    }
                                    if (secimders.equalsIgnoreCase("Talep Ýþlemleri")) {

                                        int cevap = JOptionPane.showConfirmDialog(
                                                null,
                                                "Talebi Kabul Etmek Ýstiyor Musunuz?",
                                                "Talep",
                                                JOptionPane.YES_NO_OPTION
                                        );

                                        if (cevap == JOptionPane.YES_OPTION) {

                                            String mesaj;

                                            sistem = bilgial("select * from \"Sistem\" ");
                                            while (sistem.next()) {

                                                karakter = sistem.getInt("karakterSiniri");

                                            }

                                            int cevap1 = JOptionPane.showConfirmDialog(
                                                    null,
                                                    "Mesaj Göndermek Ýstiyor Musunuz?",
                                                    "Talep",
                                                    JOptionPane.YES_NO_OPTION
                                            );
                                            if (cevap1 == JOptionPane.YES_OPTION) {
                                                mesaj = sinirGetir("Lütfen en fazla " + karakter + " karakterlik bir metin girin:", karakter);

                                                mesajlar = c.createStatement();
                                                mesajlar.executeUpdate("insert into \"Mesaj\" (\"kim\",\"kime\",\"mesaj\",\"dersID\") values ('" + ogretmen.getSicilNo() + "','" + ogrid + "','" + mesaj + "','" + (int) dersid + "') ");

                                                JOptionPane.showMessageDialog(null, "Talebiniz Onaylanmýþ ve Mesajýnýz Ýletilmiþtir");
                                                taleponay(ogrid, (int) dersid, ogretmen.getSicilNo());

                                                talep = c.createStatement();

                                                talep.executeUpdate("update \"Ogrenciler\" set\"talepOnay\"=\"talepOnay\"+1 where \"ogrNo\"=" + ogrid + " ");

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Talep Onaylanmýþtýr.");
                                                taleponay(ogrid, (int) dersid, ogretmen.getSicilNo());

                                                talep = c.createStatement();

                                                talep.executeUpdate("update \"Ogrenciler\" set\"talepOnay\"=\"talepOnay\"+1 where \"ogrNo\"=" + ogrid + " ");

                                            }

                                        } else {

                                            s = c.createStatement();
                                            s.executeUpdate("update \"Anlasma\" set\"durum\"='" + "{Red Edildi}" + "' where \"ogrNo\"=" + ogrid + " and \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"dersID\"=" + dersid + " and \"gonderen\"=" + "false" + " ");
                                            gecmis = c.createStatement();
                                            gecmis.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + dersid + "' ,'{Red Edildi}','" + false + "')");

                                            JOptionPane.showMessageDialog(null, "Talep Red Edilmiþtir");

                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Kontenjanýnýz Bitmiþtir!");

                                }
                            }

                            if ((tab == 3) || (tab == 4) || (tab == 5)) {

                                if (kontenjan > 0) {
                                    int sec;
                                    int ders = 0;
                                    int ss;
                                    int say = 0;
                                    String ss1 = null;
                                    String secimders = null;
                                    talepbox.addItem("Öðrenci Bilgi Görüntüle");
                                    talepbox.addItem("Talep Ýþlemleri");
                                    sec = JOptionPane.showConfirmDialog(table, talepbox, "Yapmak Ýstediðiniz Ýþlemi Seçiniz", JOptionPane.DEFAULT_OPTION);
                                    secimders = (String) talepbox.getSelectedItem();
                                    if (secimders.equalsIgnoreCase("Öðrenci Bilgi Görüntüle")) {
                                        bilgipanel(ogrid);
                                    }
                                    if (secimders.equalsIgnoreCase("Talep Ýþlemleri")) {

                                        int cevap = JOptionPane.showConfirmDialog(
                                                null,
                                                "Öðrenciye Talep Atmak Ýstiyor Musunuz?",
                                                "Talep",
                                                JOptionPane.YES_NO_OPTION
                                        );
                                        if (cevap == JOptionPane.YES_OPTION) {
                                            dersbox.addItem("41-Araþtýrma Problemleri");
                                            dersbox.addItem("42-Bitirme Çalýþmasý");

                                            ss = JOptionPane.showConfirmDialog(table, dersbox, "Talep Atmak Ýstediðiniz Dersi Seçiniz", JOptionPane.DEFAULT_OPTION);
                                            ss1 = (String) dersbox.getSelectedItem();

                                            if (ss1.equalsIgnoreCase("41-Araþtýrma Problemleri")) {

                                                ders = 41;
                                            }
                                            if (ss1.equalsIgnoreCase("42-Bitirme Çalýþmasý")) {

                                                ders = 42;
                                            }

                                            dershoca = bilgial("select * from \"dersHoca\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"dersID\"=" + ders + " ");

                                            if (!dershoca.next()) {

                                                JOptionPane.showMessageDialog(null, "Bu Dersi Vermiyorsunuz!");

                                            } else {

                                                bbb = bilgial("select * from \"Anlasma\" where \"ogrNo\"=" + ogrid + " and \"dersID\"=" + ders + " and \"durum\"='" + "{Kabul Edildi}" + "'and \"gonderen\"= " + "false" + " ");

                                                if (bbb.next()) {
                                                    int sicil = 0;
                                                    sicil = bbb.getInt("sicilNo");
                                                    if (sicil == ogretmen.getSicilNo()) {
                                                        JOptionPane.showMessageDialog(null, "Bu Öðrenci Zaten Dersi Sizden Alýyor. Ýstek Atamazsýnýz!");

                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Bu Öðrenci Bu Dersi Baþka Birisinden Alýyor. Ýstek Atamazsýnýz!");

                                                    }

                                                } else {
                                                    int sayac = 0;

                                                    ccc = bilgial("select * from \"Anlasma\"  where \"ogrNo\"=" + ogrid + " and \"dersID\"=" + ders + " and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "false" + "   ");

                                                    while (ccc.next()) {
                                                        sayac = ccc.getRow();
                                                    }

                                                    if (sayac == 0) {
                                                        aaa = bilgial("select * from \"Anlasma\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"ogrNo\"=" + ogrid + " and \"dersID\"=" + ders + " and \"gonderen\"=" + "true" + " ");

                                                        while (aaa.next()) {
                                                            say = aaa.getRow();

                                                        }

                                                        if (say == 0) {

                                                            String mesaj;

                                                            sistem = bilgial("select * from \"Sistem\" ");
                                                            while (sistem.next()) {

                                                                karakter = sistem.getInt("karakterSiniri");

                                                            }

                                                            int cevap1 = JOptionPane.showConfirmDialog(
                                                                    null,
                                                                    "Mesaj Göndermek Ýstiyor Musunuz?",
                                                                    "Talep",
                                                                    JOptionPane.YES_NO_OPTION
                                                            );
                                                            if (cevap1 == JOptionPane.YES_OPTION) {
                                                                mesaj = sinirGetir("Lütfen en fazla " + karakter + " karakterlik bir metin girin:", karakter);

                                                                mesajlar = c.createStatement();
                                                                mesajlar.executeUpdate("insert into \"Mesaj\" (\"kim\",\"kime\",\"mesaj\",\"dersID\") values ('" + ogretmen.getSicilNo() + "','" + ogrid + "','" + mesaj + "','" + ders + "') ");
                                                                hocatalep = c.createStatement();
                                                                hocatalep.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");
                                                                gecmiss = c.createStatement();
                                                                gecmiss.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");

                                                                JOptionPane.showMessageDialog(null, "Talebiniz ve Mesajýnýz Ýletilmiþtir");

                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Talebiniz Ýletilmiþtir");

                                                                hocatalep = c.createStatement();
                                                                hocatalep.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");
                                                                gecmiss = c.createStatement();
                                                                gecmiss.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");

                                                            }

                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Ayný Talep Daha Önce Gönderildi!");

                                                        }

                                                    } else {

                                                        dcc = bilgial("select * from \"Anlasma\"  where \"ogrNo\"=" + ogrid + " and \"dersID\"=" + ders + " and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "false" + " and \"sicilNo\"=" + ogretmen.getSicilNo() + "  ");

                                                        if (dcc.next()) {
                                                            JOptionPane.showMessageDialog(null, "Bu Öðrenci Zaten Bu Ders Ýçin Size Talep Atmýþ. Gelen Talepler Kýsmýndan Kabul Edebilirsiniz!");

                                                        } else {
                                                            aaa = bilgial("select * from \"Anlasma\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"ogrNo\"=" + ogrid + " and \"dersID\"=" + ders + " and \"gonderen\"=" + "true" + " ");

                                                            while (aaa.next()) {
                                                                say = aaa.getRow();
                                                            }

                                                            if (say == 0) {

                                                                String mesaj;

                                                                sistem = bilgial("select * from \"Sistem\" ");
                                                                while (sistem.next()) {

                                                                    karakter = sistem.getInt("karakterSiniri");

                                                                }

                                                                int cevap1 = JOptionPane.showConfirmDialog(
                                                                        null,
                                                                        "Mesaj Göndermek Ýstiyor Musunuz?",
                                                                        "Talep",
                                                                        JOptionPane.YES_NO_OPTION
                                                                );
                                                                if (cevap1 == JOptionPane.YES_OPTION) {
                                                                    mesaj = sinirGetir("Lütfen en fazla " + karakter + " karakterlik bir metin girin:", karakter);

                                                                    mesajlar = c.createStatement();
                                                                    mesajlar.executeUpdate("insert into \"Mesaj\" (\"kim\",\"kime\",\"mesaj\",\"dersID\") values ('" + ogretmen.getSicilNo() + "','" + ogrid + "','" + mesaj + "','" + ders + "') ");
                                                                    hocatalep = c.createStatement();
                                                                    hocatalep.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");
                                                                    gecmiss = c.createStatement();
                                                                    gecmiss.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");

                                                                    JOptionPane.showMessageDialog(null, "Talebiniz ve Mesajýnýz Ýletilmiþtir");

                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "Talebiniz Ýletilmiþtir");

                                                                    hocatalep = c.createStatement();
                                                                    System.out.println("ogr: " + ogrid);
                                                                    hocatalep.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");
                                                                    gecmiss = c.createStatement();
                                                                    gecmiss.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + ogretmen.getSicilNo() + "' , '" + ders + "' ,'{Cevap Bekleniyor}','" + true + "')");

                                                                }

                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Ayný Talep Daha Önce Gönderildi!");

                                                            }

                                                        }

                                                    }

                                                }
                                            }

                                        } else {

                                        }

                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Kontenjanýnýz Bitmiþtir!");

                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {

                                if (rs != null) {
                                    rs.close();
                                }
                                if (sistem != null) {
                                    sistem.close();
                                }
                                if (aaa != null) {
                                    aaa.close();
                                }
                                if (bbb != null) {
                                    bbb.close();
                                }
                                if (ccc != null) {
                                    ccc.close();
                                }
                                if (dcc != null) {
                                    dcc.close();
                                }
                                if (dershoca != null) {
                                    dershoca.close();
                                }
                                if (talep != null) {
                                    talep.close();
                                }
                                if (s != null) {
                                    s.close();
                                }
                                if (gecmis != null) {
                                    gecmis.close();
                                }
                                if (hocatalep != null) {
                                    hocatalep.close();
                                }
                                if (gecmiss != null) {
                                    gecmiss.close();
                                }
                                if (mesajlar != null) {
                                    mesajlar.close();
                                }
                                if (c != null) {
                                    c.close();
                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                    }

                }

            }
        }
        );

    }

    private static String sinirGetir(String str, int i) {
        String mesaj = null;
        boolean sinir = false;
        while (!sinir) {
            mesaj = JOptionPane.showInputDialog(null, str);
            if (mesaj == null || mesaj.length() <= i) {
                sinir = true;
            } else {
                JOptionPane.showMessageDialog(null, "Giriþ çok uzun. En fazla " + i + " karakter girebilirsiniz.", "Hata", JOptionPane.ERROR_MESSAGE);

            }

        }
        return mesaj;

    }

    public void yazdirmesaj(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Gönderen ID", "Gönderen Ad", "Ders ID", "Ders Ad", "Mesaj"};

        String query = "select * from \"Mesaj\"left join \"Dersler\" on \"Dersler\".\"dersID\"=\"Mesaj\".\"dersID\" "
                + "left join \"Ogrenciler\" on \"Mesaj\".\"kim\"=\"Ogrenciler\".\"ogrNo\" where \"kime\"=" + ogretmen.getSicilNo() + "  ";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {

                ders[0] = rs.getInt("kim");
                ders[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                ders[2] = rs.getInt("dersID");
                ders[3] = rs.getString("dersAd");
                ders[4] = rs.getString("mesaj");

                tableders.addRow(ders);
            }
            table.setModel(tableders);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void bilgipanel(int ogrid) {

        if (tab != 1) {

            JFrame frame = new JFrame("Öðrenci Bilgi Paneli");

            JTable table = new JTable();
            JScrollPane scrollPane = new JScrollPane(table);

            yazdirbelge(table, ogrid);

            table.setBackground(new java.awt.Color(229, 213, 234));

            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    }

    public void yazdirsonuc(JTable table, ArrayList<Student> stler) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Kriter Puan"};

        tableders.setColumnIdentifiers(ilgi);

        for (int i = 0; i < stler.size(); i++) {
            ilgi[0] = stler.get(i).getOgrid();
            ilgi[1] = stler.get(i).getAd();
            ilgi[2] = stler.get(i).getNot();

            tableders.addRow(ilgi);
        }
        table.setModel(tableders);

    }

    public void sonuc(double a, int ak, double b, int bk, double c, int ck, double d, int dk, double e, int ek) {

        int no = 0;
        double toplam = 0;
        double not = 0;

        ResultSet belge = null;
        ResultSet ogrenciler = null;

        ArrayList<Student> stler = new ArrayList<>();
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

            ogrenciler = bilgial("select distinct \"Ogrenciler\".\"ogrNo\" ,\"ogrAd\",\"ogrSoyad\" from \"Anlasma\" right join \"Ogrenciler\" on \"Anlasma\".\"ogrNo\"=\"Ogrenciler\".\"ogrNo\"\n"
                    + "WHERE \"Anlasma\".\"anlasmaNo\" IS NULL OR \"Anlasma\".\"durum\"= '{Cevap Bekleniyor}' ");
            while (ogrenciler.next()) {
                Student student;
                belge = bilgial("select * from \"Belge\" where \"ogrNo\"= " + ogrenciler.getInt("ogrNo") + "  ");

                while (belge.next()) {

                    if ((belge.getInt("ogrNo") == ogrenciler.getInt("ogrNo")) && (belge.getInt("dersID") == (int) a)) {

                        an = harfnot.get(belge.getString("hNot"));

                        toplam = toplam + (an * ak);

                    }
                    if ((belge.getInt("ogrNo") == ogrenciler.getInt("ogrNo")) && (belge.getInt("dersID") == (int) b)) {

                        bn = harfnot.get(belge.getString("hNot"));

                        toplam += bn * bk;
                    }
                    if ((belge.getInt("ogrNo") == ogrenciler.getInt("ogrNo")) && (belge.getInt("dersID") == (int) c)) {

                        cn = harfnot.get(belge.getString("hNot"));

                        toplam += cn * ck;
                    }
                    if ((belge.getInt("ogrNo") == ogrenciler.getInt("ogrNo")) && (belge.getInt("dersID") == (int) d)) {

                        dn = harfnot.get(belge.getString("hNot"));

                        toplam += dn * dk;
                    }
                    if ((belge.getInt("ogrNo") == ogrenciler.getInt("ogrNo")) && (belge.getInt("dersID") == (int) e)) {

                        en = harfnot.get(belge.getString("hNot"));

                        toplam += en * ek;
                    }

                }

                not = toplam / (ak + bk + ck + dk + ek);
                student = new Student(ogrenciler.getInt("ogrNO"), not, ogrenciler.getString("ogrAd") + " " + ogrenciler.getString("ogrSoyad"));

                stler.add(student);
                toplam = 0;
            }

            Collections.sort(stler, Comparator.comparingDouble(Student::getNot).reversed());
            this.yazdirsonuc(hocataleptable, stler);

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ogrenciler != null) {
                    ogrenciler.close();
                }
                if (belge != null) {
                    belge.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void yazdirgonderilentalep(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen", "Durum"};

        String query;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" inner join \"Ogrenciler\" on \"Anlasma\".\"ogrNo\"= \"Ogrenciler\".\"ogrNo\" where  \"sicilNo\"='" + ogretmen.getSicilNo() + "' and \"gonderen\"=" + "true" + "";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                belge[3] = rs.getString("durum");

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

    public void yazdirkritersonuc(JTable table) {

        String query;
        int x = 0;
        double a = 0, b = 0, c = 0, d = 0, e = 0;
        int ak = 0, bk = 0, ck = 0, dk = 0, ek = 0;
        query = "select * from \"HFormul\" where \"sicilNo\"=" + ogretmen.getSicilNo() + " ";

        ResultSet hformul = bilgial(query);

        int satir = 0;
        double puan = 0;

        try {

            while (hformul.next()) {
                satir = hformul.getRow();
                if (satir == 0) {
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
                if (satir == 1) {
                    a = hformul.getDouble("dersID");
                    ak = hformul.getInt("katsayi");
                }
                if (satir == 2) {
                    b = hformul.getDouble("dersID");
                    bk = hformul.getInt("katsayi");
                }
                if (satir == 3) {
                    c = hformul.getDouble("dersID");
                    ck = hformul.getInt("katsayi");
                }
                if (satir == 4) {
                    d = hformul.getDouble("dersID");
                    dk = hformul.getInt("katsayi");
                }
                if (satir == 5) {
                    e = hformul.getDouble("dersID");
                    ek = hformul.getInt("katsayi");
                }

            }
            this.sonuc(a, ak, b, bk, c, ck, d, dk, e, ek);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (hformul != null) {
                    hformul.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void yazdirsecders(JTable table, int secdersid) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Ders ID", "Ders Ad", "Durum"};

        String query;
        int ogrid = 0;
        int sayac = 0;
        query = "select * from \"Ogrenciler\"  ";
        ResultSet rs = bilgial(query);
        ResultSet rs1 = null;

        tableders.setColumnIdentifiers(ilgi);
        try {

            while (rs.next()) {

                ilgi[0] = rs.getInt("ogrNo");
                ilgi[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");
                ogrid = rs.getInt("ogrNo");
                System.out.println("ogr: " + ogrid);
                rs1 = bilgial("select * from \"Anlasma\" left join \"Dersler\" on \"Anlasma\".\"dersID\" =\"Dersler\".\"dersID\"   where \"ogrNo\"=" + ogrid + " and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "false" + "  ");
                if (!rs1.next()) {

                    ilgi[2] = 0;
                    ilgi[3] = 0;
                    ilgi[4] = "";
                    sayac = 0;

                } else {
                    ilgi[2] = rs1.getInt("dersID");
                    ilgi[3] = rs1.getString("dersAd");
                    ilgi[4] = rs1.getString("durum");
                    sayac = 1;

                }
                tableders.addRow(ilgi);

            }
            table.setModel(tableders);

        } catch (SQLException ex) {
            Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs1 != null) {
                    rs1.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void yazdirbelge(JTable table, int ogrno) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Harf Notu"};
        JTable tb = new JTable();
        String query;

        query = "select * from \"Dersler\" left join \"Belge\" on \"Dersler\".\"dersID\"=\"Belge\".\"dersID\"  where \"ogrNo\"=" + ogrno + " ";

        ResultSet rs = bilgial(query);

        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getString("hNot");

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

    public void yazdirdersharf(JTable table, int dersid, String harf) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ilgi = {"Öðrenci ID", "Öðrenci Ad", "Ders ID", "Ders Ad"};
        String query;

        query = "select * from \"Ogrenciler\" inner join \"Belge\" on \"Ogrenciler\".\"ogrNo\"=\"Belge\".\"ogrNo\" "
                + "inner join \"Dersler\" on \"Belge\".\"dersID\"=\"Dersler\".\"dersID\"  where \"Belge\".\"dersID\"='" + dersid + "' and \"hNot\"='" + harf + "' ";

        ResultSet rs = null;

        tableders.setColumnIdentifiers(ilgi);
        try {
            rs = bilgial(query);
            while (rs.next()) {

                ilgi[0] = rs.getInt("ogrNo");
                ilgi[1] = rs.getString("ogrAd") + " " + rs.getString("ogrSoyad");

                ilgi[2] = rs.getInt("dersID");
                ilgi[3] = rs.getString("dersAd");

                tableders.addRow(ilgi);
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

    public void bilgiyaz(Ogretmen hoca) {

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = c.createStatement();
            rs = st.executeQuery("select * from \"Hocalar\"");
            while (rs.next()) {

                if (hoca.getSicilNo() == rs.getInt("sicilNo")) {
                    hocaisimfld.setText(rs.getString("hAd") + " " + rs.getString("hSoyad"));
                    no = Integer.toString(rs.getInt("sicilNo"));
                    hocanofld.setText(no);
                    hocaadfld.setText(rs.getString("hAd"));
                    hocasoyadfld.setText(rs.getString("hSoyad"));
                    hocasicilnofld.setText(no);
                    hocasifrefld.setText(rs.getString("hSifre"));
                    String kota = Integer.toString(rs.getInt("kontenjan"));
                    hocakontenjanfld.setText(kota);

                    ogretmen = hoca;
                }
                yazdirilgi(this.hocailgitable);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        jPanel1 = new javax.swing.JPanel();
        hocaisimfld = new javax.swing.JTextField();
        hocanofld = new javax.swing.JTextField();
        hocamesajbtn = new javax.swing.JButton();
        cksbtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        hocadersleritable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        talepsecenekbox = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hocataleptable = new javax.swing.JTable();
        kriterbtn = new javax.swing.JButton();
        kritersilbtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        hocaadfld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        hocasoyadfld = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        hocasifrefld = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        hocakontenjanfld = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        hocailgitable = new javax.swing.JTable();
        ilgibtn = new javax.swing.JButton();
        hocasicilnofld = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        dersalantable = new javax.swing.JTable();
        dersalanbox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(229, 213, 234));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocaisimfld.setBackground(new java.awt.Color(229, 213, 234));
        hocaisimfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocanofld.setBackground(new java.awt.Color(229, 213, 234));
        hocanofld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocamesajbtn.setBackground(new java.awt.Color(229, 213, 234));
        hocamesajbtn.setText("Mesaj");
        hocamesajbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocamesajbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hocamesajbtnActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hocaisimfld, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocanofld, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cksbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hocamesajbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hocamesajbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cksbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hocaisimfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hocanofld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(173, 137, 217));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocadersleritable.setBackground(new java.awt.Color(209, 188, 231));
        hocadersleritable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocadersleritable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(hocadersleritable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Derslerim", jPanel3);

        jPanel4.setBackground(new java.awt.Color(173, 137, 217));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        talepsecenekbox.setBackground(new java.awt.Color(173, 137, 217));
        talepsecenekbox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        talepsecenekbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Derslerimi Talep Edenler", "Seçilen Dersi Talep Edenler", "Kriter Tablosunu Göster", "Kriter Puana Göre Sýrala", "Ders-Harf Notuna Göre Sýrala", "Gönderilen Talepler" }));
        talepsecenekbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        talepsecenekbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                talepsecenekboxActionPerformed(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(204, 204, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocataleptable.setBackground(new java.awt.Color(204, 204, 255));
        hocataleptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(hocataleptable);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );

        kriterbtn.setBackground(new java.awt.Color(173, 137, 217));
        kriterbtn.setText("Kriter Ders Seç");
        kriterbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        kriterbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kriterbtnActionPerformed(evt);
            }
        });

        kritersilbtn.setBackground(new java.awt.Color(173, 137, 217));
        kritersilbtn.setText("Kriter Ders Sil");
        kritersilbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        kritersilbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kritersilbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(talepsecenekbox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kritersilbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kriterbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(talepsecenekbox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kriterbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kritersilbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Talep Ýþlemleri", jPanel4);

        jPanel2.setBackground(new java.awt.Color(173, 137, 217));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setAutoscrolls(true);

        hocaadfld.setBackground(new java.awt.Color(173, 137, 217));
        hocaadfld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hocaadfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Ad :");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Soyad: ");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocasoyadfld.setBackground(new java.awt.Color(173, 137, 217));
        hocasoyadfld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hocasoyadfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Þifre: ");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocasifrefld.setBackground(new java.awt.Color(173, 137, 217));
        hocasifrefld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hocasifrefld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Kontenjan");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hocakontenjanfld.setBackground(new java.awt.Color(173, 137, 217));
        hocakontenjanfld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hocakontenjanfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBackground(new java.awt.Color(108, 153, 206));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("ÝLGÝ ALANLARIM");

        hocailgitable.setBackground(new java.awt.Color(209, 188, 231));
        hocailgitable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hocailgitable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(hocailgitable);

        ilgibtn.setBackground(new java.awt.Color(108, 153, 206));
        ilgibtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ilgibtn.setText("ÝLGÝ ALANI SEÇ");
        ilgibtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ilgibtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilgibtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ilgibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ilgibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        hocasicilnofld.setBackground(new java.awt.Color(173, 137, 217));
        hocasicilnofld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hocasicilnofld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Sicil No:");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hocakontenjanfld, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocasifrefld, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocasicilnofld, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocaadfld, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocasoyadfld, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hocaadfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hocasoyadfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocasicilnofld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hocasifrefld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hocakontenjanfld, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kiþisel Bilgilerim ", jPanel2);

        jPanel7.setBackground(new java.awt.Color(173, 137, 217));

        jScrollPane4.setBackground(new java.awt.Color(204, 204, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dersalantable.setBackground(new java.awt.Color(204, 204, 255));
        dersalantable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(dersalantable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1130, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        );

        dersalanbox.setBackground(new java.awt.Color(229, 213, 234));
        dersalanbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Araþtýrma Problemleri", "Bitirme Çalýþmasý" }));
        dersalanbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dersalanbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dersalanboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dersalanbox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dersalanbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Derslerimi Alan Öðrenciler", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ilgibtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilgibtnActionPerformed
        String secim = null;
        int sec = 0;
        int ilgiid = 0;
        int flag = 0;

        int sicil = Integer.parseInt(no);

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;
        ResultSet hilgi = bilgial("select * from \"HIlgi\" where \"sicilNo\"= '" + ogretmen.getSicilNo() + "'");

        ArrayList<Integer> ilgiidler = new ArrayList<>();

        Statement Hilgi = null;

        JComboBox<String> cb = new JComboBox<>();

        try {
            st = c.createStatement();
            Hilgi = c.createStatement();
            rs = st.executeQuery("select * from \"ilgiAlanlari\"");
            while (rs.next()) {
                cb.addItem(rs.getInt("ilgiID") + "-" + rs.getString("ilgiAd"));

            }
            sec = JOptionPane.showConfirmDialog(this, cb, "Ýlgi Alanýnýzý Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();
            int index = secim.indexOf("-");

            ilgiid = Integer.parseInt(secim.substring(0, index));
            while (hilgi.next()) {
                ilgiidler.add(hilgi.getInt("ilgiID"));
            }

            if (ilgiidler.size() == 0) {

                Hilgi.executeUpdate("INSERT INTO \"HIlgi\" (\"sicilNo\",\"ilgiID\")VALUES(" + sicil + "," + ilgiid + ") ");
                JOptionPane.showMessageDialog(null, "Seçtiðiniz Ýlgi Alaný Veri Tabanýna Eklenmiþtir");
            } else {

                if (!ilgiidler.contains(ilgiid)) {

                    flag = 1;

                } else {
                    flag = 2;

                }

            }
            if (flag == 1) {
                Hilgi.executeUpdate("INSERT INTO \"HIlgi\" (\"sicilNo\",\"ilgiID\")VALUES(" + sicil + "," + ilgiid + ") ");
                JOptionPane.showMessageDialog(null, "Seçtiðiniz Ýlgi Alaný Veri Tabanýna Eklenmiþtir");
            } else if (flag == 2) {
                JOptionPane.showMessageDialog(null, "Seçtiðiniz Ýlgi Alaný Daha Önce Seçilmiþtir!");

            }

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (hilgi != null) {
                    hilgi.close();
                }
                if (Hilgi != null) {
                    Hilgi.close();
                }
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        yazdirilgi(hocailgitable);

    }//GEN-LAST:event_ilgibtnActionPerformed

    private void talepsecenekboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_talepsecenekboxActionPerformed
        String talep = (String) talepsecenekbox.getSelectedItem();
        String str;

        ResultSet ders = null;
        ResultSet harfnotlari = null;

        int dersid = 0;

        JComboBox<String> dersler = new JComboBox<>();
        JComboBox<String> harfnotlaricb = new JComboBox<>();
        if (talep.equalsIgnoreCase("Kriter Tablosunu Göster")) {
            this.yazdirkriter(hocataleptable);
            tab = 1;
        }

        if (talep.equalsIgnoreCase("Derslerimi Talep Edenler")) {
            this.yazdirderslerim(hocataleptable);
            tab = 2;

        }

        if (talep.equalsIgnoreCase("Seçilen Dersi Talep Edenler")) {
            int secders = 0;
            int secdersid = 0;
            String secimders = null;
            dersler.addItem("41-Araþtýrma Problemleri");
            dersler.addItem("42-Bitirme Çalýþmasý");
            secders = JOptionPane.showConfirmDialog(this, dersler, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
            secimders = (String) dersler.getSelectedItem();
            if (secimders.equalsIgnoreCase("41-Araþtýrma Problemleri")) {
                secdersid = 41;
            }
            if (secimders.equalsIgnoreCase("42-Bitirme Çalýþmasý")) {
                secdersid = 42;
            }

            this.yazdirsecders(hocataleptable, secdersid);
            tab = 3;

        }

        if (talep.equalsIgnoreCase("Ders-Harf Notuna Göre Sýrala")) {

            int sec;
            String secim, secim2;
            int sec2;

            try {
                ders = bilgial("select * from \"Dersler\" ");
                harfnotlari = bilgial("select * from \"Notlar\" ");
                while (ders.next()) {
                    dersler.addItem(ders.getInt("dersID") + "-" + ders.getString("dersAd"));

                }
                while (harfnotlari.next()) {
                    harfnotlaricb.addItem(harfnotlari.getString("hNot"));
                }

                sec = JOptionPane.showConfirmDialog(this, dersler, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
                secim = (String) dersler.getSelectedItem();
                int d = secim.indexOf("-");
                str = secim.substring(0, d);
                dersid = Integer.parseInt(str);

                sec2 = JOptionPane.showConfirmDialog(this, harfnotlaricb, "Harf Notunu Seçiniz", JOptionPane.DEFAULT_OPTION);
                secim2 = (String) harfnotlaricb.getSelectedItem();

                this.yazdirdersharf(hocataleptable, dersid, secim2);
                tab = 4;

            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (ders != null) {
                        ders.close();
                    }
                    if (harfnotlari != null) {
                        harfnotlari.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if (talep.equalsIgnoreCase("Kriter Puana Göre Sýrala")) {
            this.yazdirkritersonuc(hocataleptable);
            tab = 5;
        }
        if (talep.equalsIgnoreCase("Gönderilen Talepler")) {
            this.yazdirgonderilentalep(hocataleptable);
            tab = 6;
        }


    }//GEN-LAST:event_talepsecenekboxActionPerformed

    private void cksbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cksbtnActionPerformed
        this.kontenjan = 0;
        this.dispose();
        menu menu = new menu();

        menu.setVisible(true);
    }//GEN-LAST:event_cksbtnActionPerformed

    private void kriterbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kriterbtnActionPerformed

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;

        int deger = 0;
        int dersid = 0;
        String secim = null;
        String katsayi = null;
        int sec;
        int flag = 0;

        Statement kriter = null;
        Statement ders = null;
        ResultSet rsders = null;

        Statement formul = null;
        ResultSet rsformul = null;

        int sicil = Integer.parseInt(no);
        JComboBox<String> cb = new JComboBox<>();
        int sayac = 0;

        ResultSet hformul = null;
        try {

            formul = c.createStatement();
            rsformul = formul.executeQuery("select * from \"HFormul\" ");
            while (rsformul.next()) {
                if (sicil == rsformul.getInt("sicilNo")) {
                    flag++;
                }
            }

            if (flag >= 5) {
                JOptionPane.showMessageDialog(null, "En fazla 5 ders seçebilirsiniz!");
            } else {

                st = c.createStatement();
                kriter = c.createStatement();
                ders = c.createStatement();

                rs = st.executeQuery("select * from \"Dersler\"");
                while (rs.next()) {
                    cb.addItem(rs.getString("dersAd"));

                }

                sec = JOptionPane.showConfirmDialog(this, cb, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
                secim = (String) cb.getSelectedItem();

                rsders = ders.executeQuery("select * from \"Dersler\" left join \"dersHoca\" on \"Dersler\".\"dersID\" =\"dersHoca\".\"dersID\"");
                while (rsders.next()) {

                    if ((secim.equalsIgnoreCase(rsders.getString("dersAd")))) {
                        dersid = rsders.getInt("dersID");

                    }
                }

                hformul = bilgial("select * from \"HFormul\" ");
                while (hformul.next()) {
                    if ((hformul.getInt("sicilNo") == sicil) && (hformul.getInt("dersID") == dersid)) {
                        sayac++;
                    }
                }

                if (sayac == 0) {
                    katsayi = JOptionPane.showInputDialog("Katsayi Deðerini Giriniz");
                    deger = Integer.parseInt(katsayi);

                    kriter.executeUpdate("INSERT INTO \"HFormul\" VALUES(" + sicil + "," + dersid + "," + katsayi + ")");
                    JOptionPane.showMessageDialog(null, "Seçtiðiniz Ders Veritabanýnda Güncellenmiþtir");
                } else {
                    JOptionPane.showMessageDialog(null, "Daha Önce Ders Seçildi!");

                }

            }
            this.yazdirkriter(hocataleptable);
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
                if (rsformul != null) {
                    rsformul.close();
                }
                if (hformul != null) {
                    hformul.close();
                }
                if (st != null) {
                    st.close();
                }
                if (kriter != null) {
                    kriter.close();
                }
                if (ders != null) {
                    ders.close();
                }
                if (formul != null) {
                    formul.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_kriterbtnActionPerformed

    private void kritersilbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kritersilbtnActionPerformed

        String secim = null;
        int sec, dersid = 0;

        Connection c = bglnt.dbcon();
        ResultSet rs = null;

        JComboBox<String> cb = new JComboBox<>();

        Statement st = null;
        try {
            rs = bilgial("select * from \"HFormul\" left join \"Dersler\" on \"HFormul\".\"dersID\"=\"Dersler\".\"dersID\"  where \"sicilNo\"=" + ogretmen.getSicilNo() + "");
            st = c.createStatement();
            while (rs.next()) {
                cb.addItem(rs.getInt("dersID") + "-" + rs.getString("dersAd"));
            }

            sec = JOptionPane.showConfirmDialog(this, cb, "Silmek Ýstediðiniz Dersi Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();
            int index = secim.indexOf("-");

            dersid = Integer.parseInt(secim.substring(0, index));

            st.executeUpdate("delete from \"HFormul\"  where \"sicilNo\"=" + ogretmen.getSicilNo() + " and \"dersID\"=" + dersid + "");
            JOptionPane.showMessageDialog(null, "Seçtiðiniz Ders Veritabanýndan Silinmiþtir");

        } catch (SQLException ex) {
            Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs!= null)
                rs.close();
                if (st != null)
                st.close();
                if (c != null)
                c.close();

            } catch (SQLException ex) {
                Logger.getLogger(hocaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.yazdirkriter(hocataleptable);

    }//GEN-LAST:event_kritersilbtnActionPerformed

    private void dersalanboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dersalanboxActionPerformed
        String talep = (String) dersalanbox.getSelectedItem();

        if (talep.equalsIgnoreCase("Araþtýrma Problemleri")) {
            yazdirdersalan(dersalantable, 41);

        }
        if (talep.equalsIgnoreCase("Bitirme Çalýþmasý")) {
            yazdirdersalan(dersalantable, 42);
        }
    }//GEN-LAST:event_dersalanboxActionPerformed

    private void hocamesajbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hocamesajbtnActionPerformed

        JFrame frame = new JFrame("Mesajlarým");

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        yazdirmesaj(table);

         
        table.setBackground(new java.awt.Color(229, 213, 234));

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_hocamesajbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(hocaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hocaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hocaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hocaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new hocaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cksbtn;
    private javax.swing.JComboBox<String> dersalanbox;
    private javax.swing.JTable dersalantable;
    private javax.swing.JTextField hocaadfld;
    private javax.swing.JTable hocadersleritable;
    private javax.swing.JTable hocailgitable;
    private javax.swing.JTextField hocaisimfld;
    private javax.swing.JTextField hocakontenjanfld;
    private javax.swing.JButton hocamesajbtn;
    private javax.swing.JTextField hocanofld;
    private javax.swing.JTextField hocasicilnofld;
    private javax.swing.JTextField hocasifrefld;
    private javax.swing.JTextField hocasoyadfld;
    public javax.swing.JTable hocataleptable;
    public javax.swing.JButton ilgibtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JButton kriterbtn;
    public javax.swing.JButton kritersilbtn;
    private javax.swing.JComboBox<String> talepsecenekbox;
    // End of variables declaration//GEN-END:variables
}
