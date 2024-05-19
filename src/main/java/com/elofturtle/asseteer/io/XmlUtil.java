package com.elofturtle.asseteer.io;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;
import com.elofturtle.asseteer.model.Asset;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;


public class XmlUtil {
    private static XmlMapper xmlMapper = new XmlMapper();
    
    static {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        
        /*BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        	    .allowIfBaseType(Asset.class) 
        	    .build();
        
        xmlMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
*/
    }

    public static String serialize(ArrayList<Asset> library) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(library);
    }

    public static ArrayList<Asset> deserialize(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, new TypeReference<ArrayList<Asset>>() {});
    }
    
    public static ArrayList<Asset> deserialize2(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, new TypeReference<ArrayList<Asset>>() {});
    }
}
