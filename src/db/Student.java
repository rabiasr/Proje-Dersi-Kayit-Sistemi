 
package db;
 
public class Student {

    private   int ogrid;
    private   double not ;
    private String ad;
    
     public Student(int ogrid,double not,String ad) {
        this.ogrid = ogrid;
        this.not = not;
        this.ad=ad;
    }

    public Student() {
    }
     
     
    public int getOgrid() {
        return ogrid;
    }

    public void setOgrid(int ogrid) {
        this.ogrid = ogrid;
    }

    public double getNot() {
        return not;
    }

    public void setNot(double not) {
        this.not = not;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
     
     
    
}
