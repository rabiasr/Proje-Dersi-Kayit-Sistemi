 
package baglanti;
import java.sql.*;
 
 
import java.util.logging.Level;
import java.util.logging.Logger;
 

 
public class baglanti {
   
    Connection c=null;
    
    public baglanti(){
        
    }   
    public Connection dbcon() {
        
        try {
         
          this.c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/obs", "postgres", "rabia123");
        
          return c;
        }catch(SQLException e){
                        Logger.getLogger(baglanti.class.getName()).log(Level.SEVERE, null, e); 
                   }
        
        
        return c;
    }
    
}
