 
package db;
 
public class Ogrenci {
    
     private  int ogrNo, toplamTalep ,talepOnay;
     private String ogrAd, ogrSoyad, ogrSifre;
     private double GNO;
     private String dersad;
     private boolean dersflag;
     
    public Ogrenci(int ogrNo, int toplamTalep, int talepOnay, String ogrAd, String ogrSoyad, String ogrSifre, double GNO,String dersad,boolean dersflag) {
        this.ogrNo = ogrNo;
        this.toplamTalep = toplamTalep;
        this.talepOnay = talepOnay;
        this.ogrAd = ogrAd;
        this.ogrSoyad = ogrSoyad;
        this.ogrSifre = ogrSifre;
        this.GNO = GNO;
        this.dersad=dersad;
        this.dersflag=dersflag;
    }
     
     public Ogrenci(){
         
     }

    public String getDersad() {
        return dersad;
    }

    public void setDersad(String dersad) {
        this.dersad = dersad;
    }
     
     

    public int getOgrNo() {
        return ogrNo;
    }

    public void setOgrNo(int ogrNo) {
        this.ogrNo = ogrNo;
    }

    public int getToplamTalep() {
        return toplamTalep;
    }

    public void setToplamTalep(int toplamTalep) {
        this.toplamTalep = toplamTalep;
    }

    public int getTalepOnay() {
        return talepOnay;
    }

    public void setTalepOnay(int talepOnay) {
        this.talepOnay = talepOnay;
    }

    public String getOgrAd() {
        return ogrAd;
    }

    public void setOgrAd(String ogrAd) {
        this.ogrAd = ogrAd;
    }

    public String getOgrSoyad() {
        return ogrSoyad;
    }

    public void setOgrSoyad(String ogrSoyad) {
        this.ogrSoyad = ogrSoyad;
    }

    public String getOgrSifre() {
        return ogrSifre;
    }

    public void setOgrSifre(String ogrSifre) {
        this.ogrSifre = ogrSifre;
    }

    public double getGNO() {
        return GNO;
    }

    public void setGNO(double GNO) {
        this.GNO = GNO;
    }

    public boolean isDersflag() {
        return dersflag;
    }

    public void setDersflag(boolean dersflag) {
        this.dersflag = dersflag;
    }
    
     
     
}
