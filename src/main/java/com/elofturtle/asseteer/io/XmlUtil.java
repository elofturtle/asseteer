package com.elofturtle.asseteer.io;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;
import com.elofturtle.asseteer.model.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;


/**
 * Stödklass med metoder för persistering i de tinterna dataformatet.
 */
public class XmlUtil {
	/**
	 * Default-konstruktor
	 */
	public XmlUtil() {
		
	}
    /**
     * Konverterar från/till utdataformat för att bearbeta filer.
     * <p>
     * Borde dessa kanske integreras i Asset-klassen direkt?
     *
     */
    private static XmlMapper xmlMapper;
    
    static {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    }

    /**
     * Serialiserar en lista med {@link com.elofturtle.asseteer.model.Asset Assets}, för att t.ex. kunna spara till fil.
     * @param library en samling Assets
     * @return the result of the parsing
     * @throws JsonProcessingException if an error occurs during parsing
     */
    public static String serialize(ArrayList<Asset> library) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(library);
    }

    /**
     * Deserialiserar en XML-sträng till en lista med {@link com.elofturtle.asseteer.model.Asset Assets}, för att t.ex. kunna läsa från fil.
     * @param xml en sträng som innehåller en XML
     * @return en lista med Assets
     * @throws JsonProcessingException om xml:en inte har en korrekt struktur
     */
    public static ArrayList<Asset> deserialize(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, new TypeReference<ArrayList<Asset>>() {});
    }
}
