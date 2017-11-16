/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swp.shafou.qr2buy.POJO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lucaheider
 * 
 * Objekte dieser Klasse werden von App-Client zum Server geschickt. 
 * Bei korrekten Login-Daten wird ein Token-Objekt zum Client geschickt.
 */
public class UserLoginRequest implements Serializable {

   
    
    private static final long serialVersionUID = 3L;

    private String username;
    private String password;
    private Date requestDate;
    
    
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        this.requestDate = new Date();
    }
    
    public UserLoginRequest() {
        
    }
    
    
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    
    
    
    
}
