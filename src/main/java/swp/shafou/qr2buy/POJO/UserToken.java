/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swp.shafou.qr2buy.POJO;

import static swp.shafou.qr2buy.POJO.TokenStatusEnum.ACCESS_DENIED;
import static swp.shafou.qr2buy.POJO.TokenStatusEnum.AUTHENTICATED;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author lucaheider
 */
public class UserToken implements Serializable{
    
    private final String TOKEN_HASH;
    private final Date START_DATE;
    private int TIME_OUT;
    private TokenStatusEnum status;

    public UserToken(String tokenHash) {
        this.status = AUTHENTICATED;
        this.TOKEN_HASH = tokenHash;
        this.START_DATE = new Date();
        this.TIME_OUT = 10 * 60 * 1000; //ms (default 10 min)
    }
    
    public UserToken() {
        this.status = ACCESS_DENIED;
        this.TOKEN_HASH = null;
        this.START_DATE = null;
    }


    public int getTIME_OUT() {
        return TIME_OUT;
    }

    public void setTIME_OUT(int TIME_OUT) {
        this.TIME_OUT = TIME_OUT;
    }


    public String getTOKEN_HASH() {
        return TOKEN_HASH;
    }


    public Date getSTART_DATE() {
        return START_DATE;
    }


    public TokenStatusEnum getStatus() {
        return status;
    }


    public void setStatus(TokenStatusEnum status) {
        this.status = status;
    }
    
    
    
}
