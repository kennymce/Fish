/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kennymce.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONMapper {
    ObjectMapper mapper = new ObjectMapper();
    private static JSONMapper ourInstance = new JSONMapper();
    public Object readValue(File pathname, Class c){
        Object ret = null;
        try {
            ret = mapper.readValue(pathname, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public TypeFactory getTypeFactory(){
        return mapper.getTypeFactory();
    }

    public List readValue(File pathname, CollectionType c){
        List ret = null;
        try {
            ret =  mapper.readValue(pathname, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return List.class.cast(ret);
        return ret;
    }


    public static JSONMapper getInstance() {
        return ourInstance;
    }

    private JSONMapper() {
    }
}
