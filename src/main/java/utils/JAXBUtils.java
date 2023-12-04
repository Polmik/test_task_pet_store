package utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class JAXBUtils {

    public static <T> T unmarshalStringToObject(String value, Class<T> inputObject) {
        try {
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(inputObject).createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(new StringReader(value));
        } catch (JAXBException e) {
            throw new RuntimeException(String.format("JAXB Exception occurred during parsing String tp %s", inputObject.toString()));
        }
    }

    public static <T> String marshalObjectToString(T object) {
        StringWriter writer = new StringWriter();
        try {
            Marshaller jaxbMarshaller = JAXBContext.newInstance(object.getClass()).createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(String.format("JAXB Exception occurred during parsing %s to String", object.getClass()));
        }
    }
}
