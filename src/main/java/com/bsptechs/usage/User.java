/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.usage;

import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class User {

    private int id;
    private String name;
    private String surname;
    private Company company;
}
