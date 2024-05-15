 
package db;

 
public class Hoca {
    
     private   int sicil;
    private   int kota ;
    private String ad;

    public Hoca(int sicil, String ad, int kota) {
        this.sicil = sicil;
        this.kota = kota;
        this.ad = ad;
    }
    
    public Hoca(){
        
    }

    public int getSicil() {
        return sicil;
    }

    public void setSicil(int sicil) {
        this.sicil = sicil;
    }

    public int getKota() {
        return kota;
    }

    public void setKota(int kota) {
        this.kota = kota;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
    
    
    
    
    
    
    
    
}
