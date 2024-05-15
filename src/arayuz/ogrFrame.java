package arayuz;

import static arayuz.hocaFrame.tab;
import baglanti.baglanti;
import db.Ogrenci;
import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ogrFrame extends javax.swing.JFrame {

    private baglanti bglnt = new baglanti();
    private String no = null;

    public Ogrenci ogrenci = new Ogrenci();
    static public boolean sayac = false;
    private String dersad;
    private int dersid;

    public static int talepap = 0;
    public static int talepbp = 0;
    public static int hocasayacbp = 0;
    public static int hocasayacap = 0;
    public static ArrayList<Integer> hocalaridap = new ArrayList<>();
    public static ArrayList<Integer> hocalaridbp = new ArrayList<>();
    int karakter = 0;

    boolean flagap = false;
    boolean flagbp = false;
    static int tab1 = 0;

    public ogrFrame() {
        initComponents();
        if ((dersid != 0)) {
            filtrebtn.setEnabled(false);

        } else {
            filtrebtn.setEnabled(true);
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
//                if (rs != null) {
//                    rs.close();
//                }
                st.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void yazdirbelge(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Harf Notu"};

        String query;

        query = "select * from \"Dersler\" left join \"Belge\" on \"Dersler\".\"dersID\"=\"Belge\".\"dersID\"  where \"ogrNo\"=" + ogrenci.getOgrNo() + " ";
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

    public void yazdirproje(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen"};

        String query;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" inner join \"Hocalar\" on \"Anlasma\".\"sicilNo\"= \"Hocalar\".\"sicilNo\" where \"ogrNo\"=" + ogrenci.getOgrNo() + " and \"durum\"='" + "{Kabul Edildi}" + "'";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getString("hAd") + " " + rs.getString("hSoyad");

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

    public void yazdirgonderilentalep(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen", "Durum"};

        String query;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" inner join \"Hocalar\" on \"Anlasma\".\"sicilNo\"= \"Hocalar\".\"sicilNo\" where  \"ogrNo\"='" + ogrenci.getOgrNo() + "' and \"gonderen\"=" + "false" + "";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getString("hAd") + " " + rs.getString("hSoyad");
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

    public void yazdironaylanantalep(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen", "Durum"};

        String query;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" inner join \"Hocalar\" on \"Anlasma\".\"sicilNo\"= \"Hocalar\".\"sicilNo\" where  \"ogrNo\"='" + ogrenci.getOgrNo() + "' and \"durum\"='" + "{Kabul Edildi}" + "' and \"gonderen\"=" + "false" + " ";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getString("hAd") + " " + rs.getString("hSoyad");
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

    public void yazdironaylanmayantalep(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen ID", "Öðretmen Ad", "Durum"};

        String query;
        query = "select * from \"Dersler\" inner join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" inner join \"Hocalar\" on \"Anlasma\".\"sicilNo\"= \"Hocalar\".\"sicilNo\" where  \"ogrNo\"='" + ogrenci.getOgrNo() + "' and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "false" + " ";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getInt("sicilNo");
                belge[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                belge[4] = rs.getString("durum");

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

    public void yazdirgelentalep(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] belge = {"Ders ID", "Ders Ad", "Öðretmen ID", "Öðretmen Ad", "Durum"};

        String query;
        query = "select * from \"Dersler\" left join \"Anlasma\" on \"Dersler\".\"dersID\"=\"Anlasma\".\"dersID\" left join \"Hocalar\" on \"Anlasma\".\"sicilNo\"= \"Hocalar\".\"sicilNo\" where  \"ogrNo\"='" + ogrenci.getOgrNo() + "' and \"durum\"='" + "{Cevap Bekleniyor}" + "' and \"gonderen\"=" + "true" + " ";

        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(belge);
        try {

            while (rs.next()) {

                belge[0] = rs.getInt("dersID");
                belge[1] = rs.getString("dersAd");
                belge[2] = rs.getInt("sicilNo");
                belge[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                belge[4] = rs.getString("durum");

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

    public void tablo(JTable table) {

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                Connection c = bglnt.dbcon();
                int dersid = 0;
                Object hid = 0;
                Statement talep = null;
                Statement mesaj = null;
                Statement s = null;
                ResultSet ogrders = null;
                ResultSet sistem = null;
                Statement mesajlar = null;
                ResultSet hocakota = null;
                Statement kota = null;
                ResultSet onay = null;
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();

                    if (selectedRow != -1) {

                        dersid = (int) table.getValueAt(selectedRow, 0);

                        hid = table.getValueAt(selectedRow, 2);

                    }

                    if (tab1 == 1) {

                        Object[] secenek = {"Evet", "Hayýr"};

                        int cevap = JOptionPane.showOptionDialog(
                                null,
                                "Talebinizi  Geri Çekmek Ýstiyor Musunuz?",
                                "Talep",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                secenek,
                                secenek[0]
                        );

                        if (cevap == JOptionPane.YES_NO_OPTION) {

                            try {
                                s = c.createStatement();
                                s.executeUpdate("delete from \"Anlasma\" where \"ogrNo\"=" + ogrenci.getOgrNo() + " and \"dersID\"=" + dersid + " and \"sicilNo\"=" + (int) hid + " ");

                                talep = c.createStatement();
                                talep.executeUpdate("update \"Ogrenciler\" set\"toplamTalep\"=\"toplamTalep\"-1 where \"ogrNo\"=" + ogrenci.getOgrNo() + "  ");

                                mesaj = c.createStatement();
                                mesaj.executeUpdate("delete from \"Mesaj\" where \"kim\"=" + ogrenci.getOgrNo() + " and \"dersID\"=" + dersid + " and \"kime\"=" + (int) hid + " ");

                                talepap = 0;
                                talepbp = 0;
                                flagap = false;
                                flagbp = false;
                                hocasayacap = 0;
                                hocasayacbp = 0;
                                hocalaridap.clear();
                                hocalaridbp.clear();

                                JOptionPane.showMessageDialog(null, "Talebiniz Baþarýlý Bir Þekilde Geri Çekilmiþtir!");

                            } catch (SQLException ex) {
                                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    if (s != null) {
                                        s.close();
                                    }
                                    if (talep != null) {
                                        talep.close();
                                    }
                                    if (mesaj != null) {
                                        mesaj.close();
                                    }
                                    if (c != null) {
                                        c.close();
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }

                    }

                    if (tab1 == 4) {
                        ogrders = bilgial("select * from \"Anlasma\" where \"ogrNo\"=" + ogrenci.getOgrNo() + " and \"dersID\"=" + dersid + " and\"durum\"='" + "{Kabul Edildi}" + "'  ");

                        try {
                            if (!ogrders.next()) {

                                Object[] secenek = {"Evet", "Beklet"};
                                Object[] secenek1 = {"Evet", "Hayýr"};

                                int cevap = JOptionPane.showOptionDialog(
                                        null,
                                        "Talebi Kabul Etmek Ýstiyor Musunuz?",
                                        "Talep",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        secenek,
                                        secenek[0]
                                );
                                hocakota = bilgial("select * from \"Hocalar\" where \"sicilNo\"=" + (int) hid + " and \"kontenjan\">0 ");

                                if (!hocakota.next()) {
                                    JOptionPane.showMessageDialog(null, "Öðretmenin Kontenjaný Dolu");

                                } else {

//                                    onay = bilgial("seelct * from \"Anlasma\" where \"ogrNo\"=" + ogrenci.getOgrNo() + " and \"dersID\"=" + dersid + " and \"durum\"='" + "{Kabul Edildi}" + "' ");
//                                    if (onay.next()) {
//                                        JOptionPane.showMessageDialog(null, "Bu Dersi Baþka Bir Öðretmenden Alýyorsunuz. Talebi Kabul Edemezsiniz!");
//
//                                    } else {
                                    if (cevap == JOptionPane.YES_NO_OPTION) {
                                        String mesaj1;

                                        sistem = bilgial("select * from \"Sistem\" ");
                                        while (sistem.next()) {

                                            karakter = sistem.getInt("karakterSiniri");

                                        }

                                        int cevap1 = JOptionPane.showOptionDialog(
                                                null,
                                                "Mesaj Göndermek Ýstiyor Musunuz?",
                                                "Talep",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                secenek1,
                                                secenek1[0]
                                        );

                                        if (cevap1 == JOptionPane.YES_OPTION) {
                                            mesaj1 = sinirGetir("Lütfen en fazla " + karakter + " karakterlik bir metin girin:", karakter);

                                            mesajlar = c.createStatement();
                                            mesajlar.executeUpdate("insert into \"Mesaj\" (\"kim\",\"kime\",\"mesaj\",\"dersID\") values ('" + ogrenci.getOgrNo() + "','" + (int) hid + "','" + mesaj + "','" + (int) dersid + "') ");

                                            JOptionPane.showMessageDialog(null, "Talep Onaylanmýþ ve Mesajýnýz Ýletilmiþtir");
                                            taleponay(ogrenci.getOgrNo(), dersid, (int) hid);

                                            talep = c.createStatement();

                                            talep.executeUpdate("update \"Ogrenciler\" set\"talepOnay\"=\"talepOnay\"+1 where \"ogrNo\"=" + ogrenci.getOgrNo() + " ");

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Talep Onaylanmýþtýr");
                                            taleponay(ogrenci.getOgrNo(), dersid, (int) hid);

                                            talep = c.createStatement();

                                            talep.executeUpdate("update \"Ogrenciler\" set\"talepOnay\"=\"talepOnay\"+1 where \"ogrNo\"=" + ogrenci.getOgrNo() + " ");

                                        }

                                    }
                                }
//                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Bu Dersi Baþka Bir Hocadan Alýyorsunuz!");

                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                if (ogrders != null) {
                                    ogrders.close();
                                }
                                if (hocakota != null) {
                                    hocakota.close();
                                }
                                if (sistem != null) {
                                    sistem.close();
                                }
                                if (mesajlar != null) {
                                    mesajlar.close();
                                }
                                if (talep != null) {
                                    talep.close();
                                }
                                if (c != null) {
                                    c.close();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }

            }
        });

    }

    public void taleponay(int ogrid, int dersid, int sicilno) {

        int index = 0;

        Connection c = bglnt.dbcon();
        Statement st = null;
        Statement kota = null;
        Statement onay = null;
        Statement gecmis = null;

        ResultSet rs = bilgial("select \"anlasmaNo\" from \"Anlasma\" where \"ogrNo\"=" + ogrid + " and \"dersID\"=" + dersid + " and \"sicilNo\"=" + sicilno + " and \"gonderen\"=" + "true" + " ");
        try {
            while (rs.next()) {
                index = rs.getInt("anlasmaNo");

            }

            st = c.createStatement();
            kota = c.createStatement();
            kota.executeUpdate("update \"Hocalar\" set \"kontenjan\"=\"kontenjan\"-1 where \"sicilNo\"=" + sicilno + "  ");
//            onay = c.createStatement();
//            onay.executeUpdate("update \"Anlasma\" set \"durum\"='" + "{Baþkasý Onayladý}" + "' where \"dersID\"=" + dersid + " and \"ogrNo\"=" + ogrid + " ");
            st.executeUpdate("update \"Anlasma\" set \"durum\"='" + "{Kabul Edildi}" + "' where \"anlasmaNo\"=" + index + "  ");

            gecmis = c.createStatement();
            gecmis.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrid + "' , '" + sicilno + "' , '" + dersid + "' ,'{Kabul Edildi}','" + true + "')");

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

    public void tablotalep(JTable table) {
        Connection c = bglnt.dbcon();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int secdersid;
                String secdersad;
                int sechocaid;
                String sechocaad;
                ResultSet rs = null;
                ResultSet rs1 = null;
                ResultSet abc = null;
                if (!e.getValueIsAdjusting()) {
                    int sec = table.getSelectedRow();

                    if (sec != -1) {

                        secdersid = (int) table.getValueAt(sec, 0);

                        secdersad = (String) table.getValueAt(sec, 1);

                        sechocaid = (int) table.getValueAt(sec, 2);
                        sechocaad = (String) table.getValueAt(sec, 3);

                        Object[] secenek = {"Evet", "Hayýr"};

                        int a = 0;
                        int b = 0;
                        ResultSet aptalep = bilgial("select * from \"Anlasma\" where \"dersID\"=41 and \"ogrNo\"=" + ogrenci.getOgrNo() + "");
                        ResultSet bptalep = bilgial("select * from \"Anlasma\" where \"dersID\"=42 and \"ogrNo\"=" + ogrenci.getOgrNo() + " ");

                        try {

                            while (aptalep.next()) {
                                a = aptalep.getRow();

                            }

                            talepap = a;
                            while (bptalep.next()) {
                                b = bptalep.getRow();
                            }

                            talepbp = b;
                        } catch (SQLException ex) {
                            Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        int cevap = JOptionPane.showOptionDialog(
                                null,
                                "Seçilen Ders ve Hocaya Talep Oluþturmak Ýstiyor musunuz?",
                                "Talep",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                secenek,
                                secenek[0]
                        );

                        if (cevap == JOptionPane.YES_OPTION) {

                            try {
                                abc = bilgial("select * from \"Anlasma\" where \"dersID\"=" + secdersid + " and \"ogrNo\"=" + ogrenci.getOgrNo() + " and \"durum\"='" + "{Kabul Edildi}" + "' ");

                                if (abc.next()) {
                                    JOptionPane.showMessageDialog(null, "Bu Dersi Daha Önce Almýþsýnýz Talep Gönderemezsiniz!");

                                } else {
                                    rs = bilgial("select * from \"Anlasma\" where \"dersID\"=41 and \"ogrNo\"=" + ogrenci.getOgrNo() + "");
                                    if (!rs.next()) {

                                        if (secdersad.equalsIgnoreCase("Araþtýrma Problemleri")) {

                                            talep(secdersid, secdersad, sechocaid, sechocaad);

                                        }

                                    } else {

                                        if (secdersad.equalsIgnoreCase("Araþtýrma Problemleri")) {

                                            ap(secdersid, secdersad, sechocaid, sechocaad);

                                        }

                                    }

                                    rs1 = bilgial("select * from \"Anlasma\" where \"dersID\"=42 and \"ogrNo\"=" + ogrenci.getOgrNo() + " ");
                                    if (!rs1.next()) {

                                        if (secdersad.equalsIgnoreCase("Bitirme Çalýþmasý")) {

                                            talep(secdersid, secdersad, sechocaid, sechocaad);

                                        }

                                    } else {

                                        if (secdersad.equalsIgnoreCase("Bitirme Çalýþmasý")) {

                                            bp(secdersid, secdersad, sechocaid, sechocaad);

                                        }

                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    if (rs != null) {
                                        rs.close();
                                    }
                                    if (rs1 != null) {
                                        rs1.close();
                                    }
                                    if (abc != null) {
                                        abc.close();
                                    }
                                    if (aptalep != null) {
                                        aptalep.close();
                                    }
                                    if (bptalep != null) {
                                        bptalep.close();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }

                    } else {

                    }
                }
            }

            private void talep(int secdersid, String secdersad, int sechocaid, String sechocaad) {
                Statement anlasma = null;
                Statement mesajlar = null;
                Statement gecmis = null;
                String mesaj;
                Statement talep = null;

                ResultSet sistem = null;
                try {
                    anlasma = c.createStatement();
                    sistem = bilgial("select * from \"Sistem\" ");
                    while (sistem.next()) {

                        karakter = sistem.getInt("karakterSiniri");

                    }

                    Object[] secenek = {"Evet", "Hayýr"};

                    int msjcvp = JOptionPane.showOptionDialog(
                            null,
                            "Öðretmene Bir Mesaj Ýletmek Ýstiyor musunuz?",
                            "Talep",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            secenek,
                            secenek[0]
                    );

                    if (msjcvp == JOptionPane.YES_OPTION) {

                        mesaj = sinirGetir("Lütfen en fazla " + karakter + " karakterlik bir metin girin:", karakter);

                        mesajlar = c.createStatement();
                        anlasma.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrenci.getOgrNo() + "' , '" + sechocaid + "' , '" + secdersid + "' ,'{Cevap Bekleniyor}','" + false + "')");
                        mesajlar.executeUpdate("insert into \"Mesaj\" (\"kim\",\"kime\",\"mesaj\",\"dersID\") values ('" + ogrenci.getOgrNo() + "','" + sechocaid + "','" + mesaj + "','" + secdersid + "') ");
                        talep = c.createStatement();
                        talep.executeUpdate("update \"Ogrenciler\" set \"toplamTalep\"=\"toplamTalep\"+1 where \"ogrNo\"='" + ogrenci.getOgrNo() + "'  ");
                        gecmis = c.createStatement();
                        gecmis.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrenci.getOgrNo() + "' , '" + sechocaid + "' , '" + secdersid + "' ,'{Cevap Bekleniyor}','" + false + "')");

                        if (secdersid == 41) {
                            talepap++;
                        }
                        if (secdersid == 42) {
                            talepbp++;
                        }

                        JOptionPane.showMessageDialog(null, "Measajýnýz ve Talebiniz Baþarýlý Bir Þekilde Ýletilmiþtir");

                    } else {

                        anlasma.executeUpdate(" insert into \"Anlasma\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrenci.getOgrNo() + "' , '" + sechocaid + "' , '" + secdersid + "' ,'{Cevap Bekleniyor}','" + false + "')");
                        talep = c.createStatement();
                        talep.executeUpdate("update \"Ogrenciler\" set \"toplamTalep\"=\"toplamTalep\"+1 where \"ogrNo\"='" + ogrenci.getOgrNo() + "'  ");
                        gecmis = c.createStatement();
                        gecmis.executeUpdate("insert into \"Gecmis\" ( \"ogrNo\",\"sicilNo\",\"dersID\",\"durum\",\"gonderen\" ) values( '" + ogrenci.getOgrNo() + "' , '" + sechocaid + "' , '" + secdersid + "' ,'{Cevap Bekleniyor}','" + false + "')");

                        if (secdersid == 41) {
                            talepap++;
                        }
                        if (secdersid == 42) {
                            talepbp++;
                        }

                        JOptionPane.showMessageDialog(null, "Talebiniz Baþarýlý Bir Þekilde Ýletilmiþtir");

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
//finally {
//                    try {
//                        sistem.close();
//                        if(anlasma!=null)
//                        anlasma.close();
//                        if(mesajlar!=null)
//                        mesajlar.close();
//                        if(gecmis!=null)
//                        gecmis.close();
//                            if(talep!=null)
//                            talep.close();
//                      
//
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
            }

            private void ap(int secdersid, String secdersad, int sechocaid, String sechocaad) {
                ResultSet anlasmaap = null;
                int hocasayacap = 0;
                ResultSet sistem = null;
                ResultSet xxx = null;
                int varsayilanTalep = 0;
                try {
                    sistem = bilgial("select * from \"Sistem\" ");
                    while (sistem.next()) {

                        varsayilanTalep = sistem.getInt("varsayilanTalep");
                    }

                    anlasmaap = bilgial("select * from \"Anlasma\" where \"dersID\"=41 ");
                    while (anlasmaap.next()) {
                        if (ogrenci.getOgrNo() == anlasmaap.getInt("ogrNo")) {

                            if ((secdersid == anlasmaap.getInt("dersID")) && (secdersad.equalsIgnoreCase("Araþtýrma Problemleri"))) {
                                {
                                    hocalaridap.add(anlasmaap.getInt("sicilNo"));
                                }

                                if (hocalaridap.contains(sechocaid)) {
                                    hocasayacap++;
                                }

                                if (talepap >= (varsayilanTalep)) {
                                    flagap = false;

                                } else {

                                    flagap = true;
                                }
                            }

                        }

                    }
                    if ((hocasayacap != 0) && (secdersad.equalsIgnoreCase("Araþtýrma Problemleri"))) {
                        xxx = bilgial("select * from \"Anlasma\" where \"dersID\"=41  and \"sicilNo\"=" + sechocaid + "  and \"ogrNo\"=" + ogrenci.getOgrNo() + "  ");

                        while (xxx.next()) {
                            if (xxx.getBoolean("gonderen") == false) {
                                JOptionPane.showMessageDialog(null, "Daha Önce Talep Gönderildi!");
                            }
                            if (xxx.getBoolean("gonderen") == true) {
                                JOptionPane.showMessageDialog(null, "Bu Öðretmen Bu Ders Ýçin Size Talep Göndermiþtir. Gelen Taleplerim Sayfasýndan Kabul Edebilirsiniz!");

                            }
                        }

                    }
                    if ((hocasayacap == 0) && (secdersad.equalsIgnoreCase("Araþtýrma Problemleri"))) {

                        if (flagap == false) {
                            JOptionPane.showMessageDialog(null, "Ayný Ders Ýçin En Fazla" + " " + varsayilanTalep + " Kadar Talep Gönderebilirsiniz!");

                        } else if (flagap == true) {
                            talep(secdersid, secdersad, sechocaid, sechocaad);
                        }

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
//finally {
//                    try {
//                        anlasmaap.close();
//                        sistem.close();
//                       
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }

            }

            private void bp(int secdersid, String secdersad, int sechocaid, String sechocaad) {
                ResultSet anlasmabp = null;
                int hocasayacbp = 0;
                ResultSet xxx = null;
                ResultSet sistem = null;
                int varsayilanTalep = 0;
                try {

                    sistem = bilgial("select * from \"Sistem\" ");
                    while (sistem.next()) {

                        varsayilanTalep = sistem.getInt("varsayilanTalep");

                    }

                    anlasmabp = bilgial("select * from \"Anlasma\" where \"dersID\"=42");
                    while (anlasmabp.next()) {
                        if (ogrenci.getOgrNo() == anlasmabp.getInt("ogrNo")) {

                            if ((secdersid == anlasmabp.getInt("dersID")) && (secdersad.equalsIgnoreCase("Bitirme Çalýþmasý"))) {
                                {
                                    hocalaridbp.add(anlasmabp.getInt("sicilNo"));
                                }

                                if (hocalaridbp.contains(sechocaid)) {
                                    hocasayacbp++;
                                }

                                if (talepbp >= (varsayilanTalep)) {
                                    flagbp = false;
                                } else {
                                    flagbp = true;
                                }

                            }

                        }

                    }

                    if ((hocasayacbp != 0) && (secdersad.equalsIgnoreCase("Bitirme Çalýþmasý"))) {
                        xxx = bilgial("select * from \"Anlasma\" where \"dersID\"=42  and \"sicilNo\"=" + sechocaid + " and \"ogrNo\"=" + ogrenci.getOgrNo() + "  ");

                        while (xxx.next()) {
                            if (xxx.getBoolean("gonderen") == false) {
                                System.out.println("gonderen: " + xxx.getBoolean("gonderen"));
                                JOptionPane.showMessageDialog(null, "Daha Önce Talep Gönderildi!");
                            }
                            if (xxx.getBoolean("gonderen") == true) {
                                System.out.println("gonderentrue : " + xxx.getBoolean("gonderen"));

                                JOptionPane.showMessageDialog(null, "Bu Öðretmen Bu Ders Ýçin Size Talep Göndermiþtir. Gelen Taleplerim Sayfasýndan Kabul Edebilirsiniz!");

                            }
                        }

                    }

                    if ((hocasayacbp == 0) && (secdersad.equalsIgnoreCase("Bitirme Çalýþmasý"))) {

                        if (flagbp == false) {
                            JOptionPane.showMessageDialog(null, "Ayný Ders Ýçin En Fazla" + " " + varsayilanTalep + " Kadar Talep Gönderebilirsiniz!");

                        } else if (flagbp == true) {
                            talep(secdersid, secdersad, sechocaid, sechocaad);
                        }

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
//                finally {
//                    try {
//                        anlasmabp.close();
//                        sistem.close();
//                        c.close();
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
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

    public void yazdirdersler(JTable table, int a, String str) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Ders ID", "Ders Ad", "Öðretmen ID", "Öðretmen"};

        String query;

        dersad = str;
        ogrenci.setDersad(str);
        dersid = a;

        query = "select * from \"dersHoca\" left join \"Hocalar\" on\"dersHoca\".\"sicilNo\" =\"Hocalar\".\"sicilNo\" where\"dersID\"=" + a + "";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {

                ders[0] = a;
                ders[1] = str;
                ders[2] = rs.getInt("sicilNo");
                ders[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");

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

    public void yazdirfiltre(JTable table, int b) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Ders ID", "Ders Ad", "Öðretmen ID", "Öðretmen"};

        String query = "select * from \"dersHoca\" inner join \"Hocalar\" on\"dersHoca\".\"sicilNo\" =\"Hocalar\".\"sicilNo\""
                + "inner join \"HIlgi\" on \"Hocalar\".\"sicilNo\"=\"HIlgi\".\"sicilNo\"  where \"ilgiID\"=" + b + " and \"dersID\"=" + dersid + " ";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {

                ders[0] = dersid;
                ders[1] = dersad;
                ders[2] = rs.getInt("sicilNo");
                ders[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");

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

    public void yazdirtum(JTable table, int b) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Ders ID", "Ders Ad", "Öðretmen ID", "Öðretmen", "Ýlgi Alaný"};

        String query = "select distinct \"HIlgi\".\"sicilNo\",\"dersHoca\".\"dersID\",\"dersAd\",\"hAd\",\"hSoyad\",\"ilgiAd\" from \"ilgiAlanlari\" inner join \"HIlgi\"  on \"ilgiAlanlari\".\"ilgiID\"=\"HIlgi\".\"ilgiID\" inner join \"Hocalar\" on\"HIlgi\".\"sicilNo\" =\"Hocalar\".\"sicilNo\""
                + "inner join \"dersHoca\" on \"Hocalar\".\"sicilNo\"=\"dersHoca\".\"sicilNo\" "
                + " inner join \"Dersler\" on \"dersHoca\".\"dersID\" =\"Dersler\".\"dersID\" where \"HIlgi\".\"ilgiID\"=" + b + "";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {

                ders[0] = rs.getInt("dersID");
                ders[1] = rs.getString("dersAd");
                ders[2] = rs.getInt("sicilNo");
                ders[3] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                ders[4] = rs.getString("ilgiAd");

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

    public void yazdirilgi(JTable table, int b) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Öðretmen ID", "Öðretmen", "Ders Ad"};

        String query = "select * from \"dersHoca\" inner join \"Hocalar\" on\"dersHoca\".\"sicilNo\" =\"Hocalar\".\"sicilNo\""
                + "inner join \"HIlgi\" on \"Hocalar\".\"sicilNo\"=\"HIlgi\".\"sicilNo\"  where \"ilgiID\"=" + b + " ";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {
                ders[0] = rs.getInt("sicilNo");
                ders[1] = rs.getString("hAd") + " " + rs.getString("hSoyad");
                ders[2] = dersad;
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

    public void bilgiyaz(Ogrenci ogr) {
        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = c.createStatement();
            rs = st.executeQuery("select * from \"Ogrenciler\"");

            while (rs.next()) {

                if (ogr.getOgrNo() == rs.getInt("ogrNo")) {

                    ogrisimfld.setText(rs.getString("ogrAd") + " " + rs.getString("ogrSoyad"));

                    no = Integer.toString(rs.getInt("ogrNo"));
                    ogrnofld.setText(no);
                    ogradfld.setText(rs.getString("ogrAd"));
                    ogrsoyadfld.setText(rs.getString("ogrSoyad"));
                    ogrbilginofld.setText(no);
                    ogrsifrefld.setText(rs.getString("ogrSifre"));
                    String gno = Double.toString(rs.getDouble("GNO"));
                    ogrgnofld.setText(gno);

                    ogrenci = ogr;

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ogrFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ogrFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
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

        jPanel3 = new javax.swing.JPanel();
        ogrisimfld = new javax.swing.JTextField();
        ogrnofld = new javax.swing.JTextField();
        ogrmesajbtn = new javax.swing.JButton();
        cksbtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ogrbilgipanel = new javax.swing.JPanel();
        belgebtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        derstable = new javax.swing.JTable();
        donemcombo = new javax.swing.JComboBox<>();
        ogrprojepanel = new javax.swing.JPanel();
        derssecbtn = new javax.swing.JButton();
        ilgisecbtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        derssecimtable = new javax.swing.JTable();
        filtrebtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ilgihocatable = new javax.swing.JTable();
        ogrkisisepanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ogradfld = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ogrsoyadfld = new javax.swing.JTextField();
        ogrbilginofld = new javax.swing.JTextField();
        ogrsifrefld = new javax.swing.JTextField();
        ogrgnofld = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ogrtaleplertable = new javax.swing.JTable();
        taleplerbox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(229, 213, 234));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrisimfld.setBackground(new java.awt.Color(229, 213, 234));
        ogrisimfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrnofld.setBackground(new java.awt.Color(229, 213, 234));
        ogrnofld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrmesajbtn.setBackground(new java.awt.Color(229, 213, 234));
        ogrmesajbtn.setText("MESAJ");
        ogrmesajbtn.setToolTipText("");
        ogrmesajbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogrmesajbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogrmesajbtnActionPerformed(evt);
            }
        });

        cksbtn.setBackground(new java.awt.Color(229, 213, 234));
        cksbtn.setText("ÇIKIÞ");
        cksbtn.setToolTipText(" ");
        cksbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cksbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cksbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ogrisimfld)
                    .addComponent(ogrnofld, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cksbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ogrmesajbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ogrmesajbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ogrisimfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ogrnofld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cksbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        ogrbilgipanel.setBackground(new java.awt.Color(173, 137, 217));
        ogrbilgipanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        belgebtn.setText("Transkript Yükle");
        belgebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        belgebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                belgebtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(209, 188, 231));
        jScrollPane1.setAutoscrolls(true);

        derstable.setBackground(new java.awt.Color(204, 204, 255));
        derstable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        derstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(derstable);

        donemcombo.setBackground(new java.awt.Color(253, 223, 223));
        donemcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alýnan Dersler", "Proje Dersleri" }));
        donemcombo.setToolTipText("");
        donemcombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        donemcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donemcomboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ogrbilgipanelLayout = new javax.swing.GroupLayout(ogrbilgipanel);
        ogrbilgipanel.setLayout(ogrbilgipanelLayout);
        ogrbilgipanelLayout.setHorizontalGroup(
            ogrbilgipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrbilgipanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ogrbilgipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1073, Short.MAX_VALUE)
                    .addGroup(ogrbilgipanelLayout.createSequentialGroup()
                        .addComponent(belgebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(donemcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ogrbilgipanelLayout.setVerticalGroup(
            ogrbilgipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrbilgipanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ogrbilgipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(belgebtn)
                    .addComponent(donemcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ders Bilgileri", ogrbilgipanel);

        ogrprojepanel.setBackground(new java.awt.Color(173, 137, 217));
        ogrprojepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        derssecbtn.setBackground(new java.awt.Color(229, 213, 234));
        derssecbtn.setText("DERS SEÇ");
        derssecbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        derssecbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derssecbtnActionPerformed(evt);
            }
        });

        ilgisecbtn.setBackground(new java.awt.Color(229, 213, 234));
        ilgisecbtn.setText("ÝLGÝ ALANI SEÇ");
        ilgisecbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ilgisecbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilgisecbtnActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(173, 137, 217));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        derssecimtable.setBackground(new java.awt.Color(204, 204, 255));
        derssecimtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        derssecimtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(derssecimtable);

        filtrebtn.setBackground(new java.awt.Color(253, 223, 223));
        filtrebtn.setText("Ýlgi Alanýna Göre Filtrele");
        filtrebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filtrebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filtrebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filtrebtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(173, 137, 217));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ilgihocatable.setBackground(new java.awt.Color(204, 204, 255));
        ilgihocatable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ilgihocatable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(ilgihocatable);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ogrprojepanelLayout = new javax.swing.GroupLayout(ogrprojepanel);
        ogrprojepanel.setLayout(ogrprojepanelLayout);
        ogrprojepanelLayout.setHorizontalGroup(
            ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrprojepanelLayout.createSequentialGroup()
                .addGroup(ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ogrprojepanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(derssecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ogrprojepanelLayout.createSequentialGroup()
                        .addComponent(ilgisecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ogrprojepanelLayout.setVerticalGroup(
            ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrprojepanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ogrprojepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(derssecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ilgisecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );

        jTabbedPane1.addTab("Proje Seçimi", ogrprojepanel);

        ogrkisisepanel.setBackground(new java.awt.Color(173, 137, 217));
        ogrkisisepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Ad:");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogradfld.setBackground(new java.awt.Color(173, 137, 217));
        ogradfld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ogradfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ogradfld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ogradfldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Soyad:");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Öðrenci Numarasý:");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Þifre:");
        jLabel4.setToolTipText("");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("GNO:");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrsoyadfld.setBackground(new java.awt.Color(173, 137, 217));
        ogrsoyadfld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ogrsoyadfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrbilginofld.setBackground(new java.awt.Color(173, 137, 217));
        ogrbilginofld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ogrbilginofld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrsifrefld.setBackground(new java.awt.Color(173, 137, 217));
        ogrsifrefld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ogrsifrefld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ogrgnofld.setBackground(new java.awt.Color(173, 137, 217));
        ogrgnofld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ogrgnofld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ogrkisisepanelLayout = new javax.swing.GroupLayout(ogrkisisepanel);
        ogrkisisepanel.setLayout(ogrkisisepanelLayout);
        ogrkisisepanelLayout.setHorizontalGroup(
            ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrkisisepanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ogrkisisepanelLayout.createSequentialGroup()
                        .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ogrsoyadfld)
                            .addComponent(ogradfld)))
                    .addGroup(ogrkisisepanelLayout.createSequentialGroup()
                        .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ogrbilginofld)
                            .addComponent(ogrsifrefld)
                            .addComponent(ogrgnofld, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
                .addContainerGap(717, Short.MAX_VALUE))
        );
        ogrkisisepanelLayout.setVerticalGroup(
            ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ogrkisisepanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ogradfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ogrsoyadfld, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ogrbilginofld, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ogrsifrefld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(ogrkisisepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ogrgnofld, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kiþisel Bilgilerim", ogrkisisepanel);

        jPanel1.setBackground(new java.awt.Color(173, 137, 217));

        ogrtaleplertable.setBackground(new java.awt.Color(209, 188, 231));
        ogrtaleplertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(ogrtaleplertable);

        taleplerbox.setBackground(new java.awt.Color(229, 213, 234));
        taleplerbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gönderilen Talepler", "Onaylanan Talepler", "Onaylanmayan Talepler", "Gelen Talepler", " " }));
        taleplerbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        taleplerbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taleplerboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(911, Short.MAX_VALUE)
                .addComponent(taleplerbox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(519, Short.MAX_VALUE)
                .addComponent(taleplerbox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(27, 27, 27)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                    .addGap(63, 63, 63)))
        );

        jTabbedPane1.addTab("Talep Bilgileri", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Öðrenci Bilgileri");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void belgebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_belgebtnActionPerformed

        JFileChooser sec = new JFileChooser();
        int durum = sec.showOpenDialog(null);
        File path = null;
        if (durum == JFileChooser.APPROVE_OPTION) {
            path = new File(sec.getSelectedFile().getAbsolutePath());

        }

        PDDocument dosya = null;
        String pdfmetni = null;
        String[] gnoparcala = null;
        try {
            File pdfDosya = new File("" + path + "");

            dosya = PDDocument.load(pdfDosya);
            PDFTextStripper sayfasil = new PDFTextStripper();

            sayfasil.setStartPage(1); // Baþlangýç sayfasý
            sayfasil.setEndPage(2);   // Bitiþ sayfasý
            sayfasil.setSortByPosition(true); // Pozisyona göre sýrala

            pdfmetni = sayfasil.getText(dosya);

            String[] metinsatirlar = pdfmetni.split("\n");

            String[] adiparcala = metinsatirlar[6].split(":");

            String[] soyadiparcala = metinsatirlar[7].split(":");

            gnoparcala = metinsatirlar[18].split(":");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dosya != null) {
                try {
                    dosya.close();

                } catch (IOException ex) {
                    Logger.getLogger(ogrFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        List<String> dersAdlari = new ArrayList<>();
        List<String> harfNotlari = new ArrayList<>();
        List<String> dersAdlariUE = new ArrayList<>();
        List<String> harfNotlariUE = new ArrayList<>();
        List<String> dersAdlaricizgi = new ArrayList<>();
        List<String> harfNotlaricizgi = new ArrayList<>();

        // dersleri çeken desen : 
        String regex = "([A-Z0-9]+)\\s+(\\w+(?:\\s+\\w+)*)\\s+([A-Z])\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+([A-Z]+)\\s+((?:\\d+\\.)?\\d+)\\s+([A-Z]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(pdfmetni);

        // ders adýnnda (UE) ifadesi içerenler için desen:
        String regex1 = "([A-Z0-9]+)\\s+(\\w+(?:\\s+\\w+)*)\\s+\\(UE\\)\\s+([A-Z])\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+([A-Z]+)\\s+((?:\\d+\\.)?\\d+)\\s+([A-Z]+)";
        Pattern pattern1 = Pattern.compile(regex1, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher1 = pattern1.matcher(pdfmetni);

        // ders adýndan sonra - ifadesi içerenler için desen:
        String regex2 = "([A-Z0-9]+)\\s+(.*?)\\s+(\\-)\\s+(.*?)\\s+([A-Z]+)\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+([A-Z]+)\\s+((?:\\d+\\.)?\\d+)\\s+([A-Z]+)";
        Pattern pattern2 = Pattern.compile(regex2, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher2 = pattern2.matcher(pdfmetni);

        if (pdfmetni.contains("BLM210")) {
            dersAdlaricizgi.add("Programlama Laboratuvarý-II");
            harfNotlaricizgi.add("CB");
        }

        while (matcher.find()) {

            String dersID = matcher.group(1);
            String dersAd = matcher.group(2);
            String dersTuru = matcher.group(3);
            String TR = matcher.group(4);
            int sayi1 = Integer.parseInt(matcher.group(5));
            int sayi2 = Integer.parseInt(matcher.group(6));
            int sayi3 = Integer.parseInt(matcher.group(7));
            int aktsdegeri = Integer.parseInt(matcher.group(8));
            String notHarf = matcher.group(9);
            double notPuan = Double.parseDouble(matcher.group(10));
            String gecer = matcher.group(11);

            dersAdlari.add(dersAd);
            harfNotlari.add(notHarf);
        }

        while (matcher1.find()) {

            String dersIDUE = matcher1.group(1);
            String dersAdUE = matcher1.group(2);
            String dersTuruUE = matcher1.group(3);
            String TRUE = matcher1.group(4);
            int sayi1ue = Integer.parseInt(matcher1.group(5));
            int sayi2ue = Integer.parseInt(matcher1.group(6));
            int sayi3ue = Integer.parseInt(matcher1.group(7));
            int aktsdegeriue = Integer.parseInt(matcher1.group(8));
            String notharfue = matcher1.group(9);
            double notpuanue = Double.parseDouble(matcher1.group(10));
            String gecerue = matcher1.group(11);

            dersAdlariUE.add(dersAdUE + " (UE)");
            harfNotlariUE.add(notharfue);
        }

        while (matcher2.find()) {

            String dersKodu = matcher2.group(1);
            String dersAdi = matcher2.group(2);
            String xxx = matcher2.group(3);
            String yyy = matcher2.group(4);
            String Z = matcher2.group(5);
            String TR = matcher2.group(6);
            int sayi1 = Integer.parseInt(matcher2.group(7));
            int sayi2 = Integer.parseInt(matcher2.group(8));
            int sayi3 = Integer.parseInt(matcher2.group(9));
            int sayi4 = Integer.parseInt(matcher2.group(10));
            String harfNotuxxx = matcher2.group(11);
            double notPuanxxx = Double.parseDouble(matcher2.group(12));
            String gecerxxx = matcher2.group(13);

            dersAdlaricizgi.add(dersAdi + "" + xxx + "" + yyy);
            harfNotlaricizgi.add(harfNotuxxx);
        }

        ArrayList<String> geneldersler = new ArrayList<>();
        geneldersler.addAll(dersAdlari);
        geneldersler.addAll(dersAdlariUE);
        geneldersler.addAll(dersAdlaricizgi);

        ArrayList<String> genelharfler = new ArrayList<>();
        genelharfler.addAll(harfNotlari);
        genelharfler.addAll(harfNotlariUE);
        genelharfler.addAll(harfNotlaricizgi);

        ArrayList<String> dersler = new ArrayList<>();
        ArrayList<Integer> idler = new ArrayList<>();

        if (sayac == true) {

        } else {
            Connection c = bglnt.dbcon();
            Statement st = null;
            Statement st1 = null;

            ResultSet rs = bilgial("select * from \"Dersler\" ");
            ResultSet rs1 = bilgial("select * from \"Ogrenciler\" ");

            sayac = true;
            try {
                while (rs.next()) {
                    dersler.add(rs.getString("dersAd"));
                    idler.add(rs.getInt("dersID"));

                }

                st1 = c.createStatement();
                while (rs1.next()) {
                    if (rs1.getInt("ogrNo") == ogrenci.getOgrNo()) {
                        st1.executeUpdate("update \"Ogrenciler\" set \"GNO\"='" + gnoparcala[2].trim() + "' where \"ogrNo\"=" + ogrenci.getOgrNo() + " ");

                    }
                }

                st = c.createStatement();

                for (int i = 0; i < dersler.size(); i++) {
                    for (int j = 0; j < geneldersler.size(); j++) {

                        if (dersler.get(i).equalsIgnoreCase(geneldersler.get(j))) {
                            st.executeUpdate("insert into \"Belge\" (\"ogrNo\",\"dersID\",\"hNot\") values('" + this.ogrenci.getOgrNo() + "','" + idler.get(i) + "','" + genelharfler.get(j) + "' ) ");

                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(ogrFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (rs1 != null) {
                        rs1.close();
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
                    Logger.getLogger(ogrFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


    }//GEN-LAST:event_belgebtnActionPerformed

    private void derssecbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derssecbtnActionPerformed
        String secim, secimilgi, str = null;
        int sec, secilgi;

        Connection c = bglnt.dbcon();
        ResultSet rs = bilgial("select * from \"ilgiAlanlari\"");

        JComboBox<String> cb = new JComboBox<>();
        JComboBox<String> cbilgi = new JComboBox<>();

        try {
            while (rs.next()) {
                cbilgi.addItem(rs.getInt("ilgiID") + "-" + rs.getString("ilgiAd"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ogrFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        cb.addItem("Araþtýrma Problemleri");
        cb.addItem("Bitirme Çalýþmasý");

        sec = JOptionPane.showConfirmDialog(this, cb, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
        secim = (String) cb.getSelectedItem();
        if (secim.equalsIgnoreCase("Araþtýrma Problemleri")) {

            yazdirdersler(derssecimtable, 41, "Araþtýrma Problemleri");

            filtrebtn.setEnabled(true);
            ogrenci.setDersflag(true);

        } else if (secim.equalsIgnoreCase("Bitirme Çalýþmasý")) {
            yazdirdersler(derssecimtable, 42, "Bitirme Çalýþmasý");

            filtrebtn.setEnabled(true);
            ogrenci.setDersflag(true);

        }


    }//GEN-LAST:event_derssecbtnActionPerformed

    private void ilgisecbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilgisecbtnActionPerformed

        String secim = null;
        int sec;
        int ilgiid = 0;
        int sicil = Integer.parseInt(no);

        Connection c = bglnt.dbcon();
        Statement st = null;
        ResultSet rs = null;

        Statement st1 = null;
        ResultSet rs1 = null;
        Statement OIlgi = null;

        JComboBox<String> cb = new JComboBox<>();

        try {
            st = c.createStatement();
            rs = st.executeQuery("select * from \"ilgiAlanlari\"");
            while (rs.next()) {
                cb.addItem(rs.getString("ilgiAd"));

            }

            sec = JOptionPane.showConfirmDialog(this, cb, "Ýlgi Alaný Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();

            st1 = c.createStatement();
            rs1 = st1.executeQuery("select * from \"ilgiAlanlari\" ");
            while (rs1.next()) {
                if (secim.equalsIgnoreCase(rs1.getString("ilgiAd"))) {
                    ilgiid = rs1.getInt("ilgiID");
                }
            }

            OIlgi = c.createStatement();
            OIlgi.executeUpdate("INSERT INTO \"OIlgi\" (\"ogrNo\",\"ilgiID\")VALUES(" + sicil + "," + ilgiid + ") ");
            // JOptionPane.showMessageDialog(null, "Seçtiðiniz Ýlgi Alaný Veritabanýna Eklenmiþtir. Hocalar Ýlgi Alanýna Göre Filtrelenmiþtir.");

        } catch (SQLException ex) {
            Logger.getLogger(ogrFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (c != null) {
                    c.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (rs1 != null) {
                    rs1.close();
                }
                if (OIlgi != null) {
                    OIlgi.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(menu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        yazdirtum(ilgihocatable, ilgiid);
        
    }//GEN-LAST:event_ilgisecbtnActionPerformed

    private void donemcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donemcomboActionPerformed

        String secim = null;

        secim = (String) donemcombo.getSelectedItem();
        if (secim.equalsIgnoreCase("Alýnan Dersler")) {
            yazdirbelge(derstable);

        } else if (secim.equalsIgnoreCase("Proje Dersleri")) {

            yazdirproje(derstable);
        }


    }//GEN-LAST:event_donemcomboActionPerformed

    private void taleplerboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taleplerboxActionPerformed

        String secim = null;
        secim = (String) taleplerbox.getSelectedItem();
        if (secim.equalsIgnoreCase("Gönderilen Talepler")) {
            this.tab1 = 3;
            yazdirgonderilentalep(ogrtaleplertable);
        }
        if (secim.equalsIgnoreCase("Onaylanan Talepler")) {
            this.tab1 = 2;
            yazdironaylanantalep(ogrtaleplertable);

        }
        if (secim.equalsIgnoreCase("Onaylanmayan Talepler")) {
            this.tab1 = 1;
            yazdironaylanmayantalep(ogrtaleplertable);

        }
        if (secim.equalsIgnoreCase("Gelen Talepler")) {
            this.tab1 = 4;
            yazdirgelentalep(ogrtaleplertable);

        }


    }//GEN-LAST:event_taleplerboxActionPerformed

    private void ogradfldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogradfldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ogradfldActionPerformed

    private void filtrebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrebtnActionPerformed
        String secim, str = null;
        int sec;
        String ders = ogrenci.getDersad();
        JComboBox<String> cb = new JComboBox<>();

        ResultSet rs = bilgial("select * from \"ilgiAlanlari\" ");

        try {
            while (rs.next()) {
                cb.addItem(rs.getInt("ilgiID") + "-" + rs.getString("ilgiAd"));
            }
            sec = JOptionPane.showConfirmDialog(this, cb, " Ders Seçiniz", JOptionPane.DEFAULT_OPTION);
            secim = (String) cb.getSelectedItem();

            int d = secim.indexOf("-");
            str = secim.substring(0, d);
            int ilgiid = Integer.parseInt(str);
            yazdirfiltre(derssecimtable, ilgiid);

            if (ogrenci.isDersflag()) {

            } else {
                filtrebtn.setEnabled(false);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ogrFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null)
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(ogrFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_filtrebtnActionPerformed

    private void cksbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cksbtnActionPerformed

        this.talepap = 0;
        this.talepbp = 0;
        this.flagap = false;
        this.flagbp = false;
        this.hocasayacap = 0;
        this.hocasayacbp = 0;
        this.hocalaridap.clear();
        this.hocalaridbp.clear();
        this.dispose();
        menu menu = new menu();
        System.out.println(talepap);
        menu.setVisible(true);

    }//GEN-LAST:event_cksbtnActionPerformed

    public void yazdirmesaj(JTable table) {

        DefaultTableModel tableders = new DefaultTableModel();
        Object[] ders = {"Gönderen ID", "Gönderen Ad", "Ders ID", "Ders Ad", "Mesaj"};

        String query = "select * from \"Mesaj\"left join \"Dersler\" on \"Dersler\".\"dersID\"=\"Mesaj\".\"dersID\" "
                + "left join \"Hocalar\" on \"Mesaj\".\"kim\"=\"Hocalar\".\"sicilNo\" where \"kime\"=" + ogrenci.getOgrNo() + "  ";
        ResultSet rs = bilgial(query);
        tableders.setColumnIdentifiers(ders);
        try {

            while (rs.next()) {

                ders[0] = rs.getInt("kim");
                ders[1] = rs.getString("hAd") + " " + rs.getString("hSoyad");
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
            try {if (rs != null)
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(yoneticiFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private void ogrmesajbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ogrmesajbtnActionPerformed

        JFrame frame = new JFrame("Mesajlarým");

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        yazdirmesaj(table);

        // table.setBackground(Color.);
        table.setBackground(new java.awt.Color(229, 213, 234));

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        //frame.add(table);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }//GEN-LAST:event_ogrmesajbtnActionPerformed

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
            java.util.logging.Logger.getLogger(ogrFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ogrFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ogrFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ogrFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ogrFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton belgebtn;
    private javax.swing.JButton cksbtn;
    public javax.swing.JButton derssecbtn;
    public javax.swing.JTable derssecimtable;
    private javax.swing.JTable derstable;
    private javax.swing.JComboBox<String> donemcombo;
    public javax.swing.JButton filtrebtn;
    public javax.swing.JTable ilgihocatable;
    private javax.swing.JButton ilgisecbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField ogradfld;
    private javax.swing.JTextField ogrbilginofld;
    private javax.swing.JPanel ogrbilgipanel;
    private javax.swing.JTextField ogrgnofld;
    private javax.swing.JTextField ogrisimfld;
    private javax.swing.JPanel ogrkisisepanel;
    private javax.swing.JButton ogrmesajbtn;
    private javax.swing.JTextField ogrnofld;
    private javax.swing.JPanel ogrprojepanel;
    private javax.swing.JTextField ogrsifrefld;
    private javax.swing.JTextField ogrsoyadfld;
    public javax.swing.JTable ogrtaleplertable;
    private javax.swing.JComboBox<String> taleplerbox;
    // End of variables declaration//GEN-END:variables
}
