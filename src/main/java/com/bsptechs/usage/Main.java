/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.usage;

import com.bsptechs.jdbc.utils.ResultSetMapper;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author sarkhanrasullu
 */
public class Main {
    public static void main(String[] args) {
        /*
        Assume that our ResultSet represent such columns. 
        These columns selected from some joins of tables(payment,user,company) and these columns are in one query result:
        id,payment_date,payment_amount, user_id,user_name, user_surname, company_id, company_name 
        
        and we need to map these columns to Payment object.
        By default we need to do:
        Payment payment = new Payment();
        payment.setId(rs.getInt("payment_id");
        ... etc.
        */
        
        ResultSet rs = null;//your result set comes from jdbc query
        
        List<Payment> payments = ResultSetMapper.mapRersultSetToList(rs, Payment.class);// if it is not list use mapRersultSetToObject
       
        //please check inside of Payment class.
    }
}
