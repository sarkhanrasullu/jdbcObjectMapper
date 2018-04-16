/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.jdbc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import com.bsptechs.jdbc.JDBCColumn;
import com.bsptechs.jdbc.JDBCColumnInstance;
import com.bsptechs.jdbc.JDBCEntity;
import com.bsptechs.jdbc.JDBCTableMapping;

public class ResultSetMapper {
 
    public static <T> List<T> mapRersultSetToList(ResultSet rs, Class<T> outputClass) {
        if (rs == null) {
            return null;
        }
        List<T> outputList = new ArrayList<T>();
        try {
            // make sure resultset is not null
            while (rs.next()) {
                System.out.println("step 0");
                // check if outputClass has 'Entity' annotation
                if (outputClass.isAnnotationPresent(JDBCEntity.class)) {
                    System.out.println("step 1");
                    T bean = (T) outputClass.newInstance();
                    // get the resultset metadata
                    Field[] fields = outputClass.getDeclaredFields();
                    System.out.println("fields size=" + fields.length);
                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];

                        JDBCColumn nColumn = getAnnotation(field, JDBCColumn.class);

                        if (nColumn != null) {
                            caseNColumn(nColumn, bean, field, rs);
                        } else {
                            JDBCColumnInstance nColumnInstance = getAnnotation(field, JDBCColumnInstance.class);
                            if (nColumnInstance != null) {
                                caseNColumnInstance(nColumnInstance, bean, field, rs);
                            }
                        }
                    }
                    outputList.add(bean);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return outputList;
    }

    public static <T> T mapRersultSetToObject(ResultSet rs, Class<T> outputClass) {
        List<T> list = mapRersultSetToList(rs, outputClass);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    private static <T> T getAnnotation(Field field, Class clazz) {
        Annotation[] anns = field.getAnnotationsByType(clazz);
        if (anns != null && anns.length > 0) {
            return (T) anns[0];
        }

        return null;
    }

    private static <T> void caseNColumn(JDBCColumn nColumn, T bean, Field field, ResultSet rs) throws Exception {
        Object columnValue = rs.getObject(nColumn.name());
        BeanUtils.setProperty(bean, field.getName(), columnValue);
    }

    private static <T> void caseNColumnInstance(JDBCColumnInstance nColumnInstance, T bean, Field currentField, ResultSet rs) throws Exception {
        JDBCTableMapping[] tbs = nColumnInstance.table();
        if (tbs == null || tbs.length == 0) {
            return;
        }
        Object currentObject = currentField.getType().newInstance();
        BeanUtils.setProperty(bean, currentField.getName(), currentObject);
        System.out.println("current class name=" + currentObject.getClass().getName());
        System.out.println("current field name=" + currentField.getName());
        for (int i = 0; i < tbs.length; i++) {
            System.out.println("---------------------------");
            System.out.println("annotation variables processing began");
            System.out.println("---------------------------");

            JDBCTableMapping tb = tbs[i];
            if (StringUtils.isBlank(tb.varName())) {
                continue;
            }
            String[] variableNames = tb.varName().split("\\.");
            if (variableNames == null || variableNames.length == 0) {
                continue;
            }
            Object value = rs.getObject(tb.columnName());
            System.out.println("db_value=" + value);
            if (variableNames.length == 1) {
                String varName = variableNames[0];
                System.out.println("1.varname=" + varName);
                BeanUtils.setProperty(currentObject, varName, value);
                continue;
            }

            Object internalObject = null;
            for (int j = 0; j < variableNames.length; j++) {
                String varName = variableNames[j];
                System.out.println((j + 1) + ".varname=" + varName);
                if (j == variableNames.length - 1) {
                    BeanUtils.setProperty(internalObject, varName, value);
                } else {
                    if (j == 0) {
                        internalObject = currentObject.getClass().getDeclaredField(varName).getType().newInstance();
                    } else {
                        internalObject = internalObject.getClass().getDeclaredField(varName).getType().newInstance();
                    }
                    BeanUtils.setProperty(currentObject, varName, internalObject);
                    currentObject = internalObject;
                    System.out.println("class name=" + internalObject.getClass().getName());
                }
            }
        }

    }

}
