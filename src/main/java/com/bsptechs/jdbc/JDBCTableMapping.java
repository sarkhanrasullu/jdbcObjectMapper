/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.jdbc;

/**
 *
 * @author sarkhanrasullu
 */
public @interface JDBCTableMapping {
  public String varName();
  public String columnName();
}
