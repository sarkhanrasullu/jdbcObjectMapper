/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.usage;

import lombok.Data;
import com.bsptechs.jdbc.JDBCColumn;
import com.bsptechs.jdbc.JDBCColumnInstance;
import com.bsptechs.jdbc.JDBCEntity;
import com.bsptechs.jdbc.JDBCTableMapping;

/**
 *
 * @author sarkhanrasullu
 */
@Data
@JDBCEntity
public class Payment {

    @JDBCColumn(name = "id")
    private Long id;

    @JDBCColumn(name = "payment_date")
    private String date;

    @JDBCColumn(name = "payment_amount")
    private Double amount;
    
    @JDBCColumnInstance(table = {
      @JDBCTableMapping(varName = "id", columnName = "user_id"),
      @JDBCTableMapping(varName = "name", columnName = "user_name"),
      @JDBCTableMapping(varName = "surname", columnName = "user_surname"),
      @JDBCTableMapping(varName = "company.id", columnName = "company_id"),
      @JDBCTableMapping(varName = "company.name", columnName = "company_name")
    })
    private User user;

}