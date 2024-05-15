
package db;

public class Ogretmen {
    
    private int sicilNo, kontenjan;
    private String hAd,hSoyad,hSifre;

    public Ogretmen(int sicilNo, int kontenjan, String hAd, String hSoyad, String hSifre) {
        this.sicilNo = sicilNo;
        this.kontenjan = kontenjan;
        this.hAd = hAd;
        this.hSoyad = hSoyad;
        this.hSifre = hSifre;
    }
    
    public Ogretmen(){
        
        
    }
    
    
    public int getSicilNo() {
        return sicilNo;
    }

    public void setSicilNo(int sicilNo) {
        this.sicilNo = sicilNo;
    }

    public int getKontenjan() {
        return kontenjan;
    }

    public void setKontenjan(int kontenjan) {
        this.kontenjan = kontenjan;
    }

    public String gethAd() {
        return hAd;
    }

    public void sethAd(String hAd) {
        this.hAd = hAd;
    }

    public String gethSoyad() {
        return hSoyad;
    }

    public void sethSoyad(String hSoyad) {
        this.hSoyad = hSoyad;
    }

    public String gethSifre() {
        return hSifre;
    }

    public void sethSifre(String hSifre) {
        this.hSifre = hSifre;
    }
    
    
    
}
