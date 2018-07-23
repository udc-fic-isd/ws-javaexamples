package es.udc.ws.movies.restservice.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class XmlServiceExceptionConversor {

    public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

    public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/movies/xml");

    public static Document toInputValidationExceptionXml(InputValidationException ex) throws IOException {

        Element exceptionElement = new Element("InputValidationException", XML_NS);

        Element messageElement = new Element("message", XML_NS);
        messageElement.setText(ex.getMessage());
        exceptionElement.addContent(messageElement);

        return new Document(exceptionElement);
    }

    public static Document toInstanceNotFoundException(InstanceNotFoundException ex) throws IOException {

        Element exceptionElement = new Element("InstanceNotFoundException", XML_NS);

        if (ex.getInstanceId() != null) {
            Element instanceIdElement = new Element("instanceId", XML_NS);
            instanceIdElement.setText(ex.getInstanceId().toString());

            exceptionElement.addContent(instanceIdElement);
        }

        if (ex.getInstanceType() != null) {
            Element instanceTypeElement = new Element("instanceType", XML_NS);
            instanceTypeElement.setText(ex.getInstanceType());

            exceptionElement.addContent(instanceTypeElement);
        }
        return new Document(exceptionElement);
    }

    public static Document toSaleExpirationException(SaleExpirationException ex, Namespace ns) throws IOException {

        Element exceptionElement = new Element("SaleExpirationException", ns);

        if (ex.getSaleId() != null) {
            Element saleIdElement = new Element("saleId", ns);
            saleIdElement.setText(ex.getSaleId().toString());
            exceptionElement.addContent(saleIdElement);
        }

        if (ex.getExpirationDate() != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(CONVERSION_PATTERN, Locale.ENGLISH);

            Element expirationDateElement = new Element("expirationDate", ns);
            expirationDateElement.setText(dateFormatter.format(ex.getExpirationDate().getTime()));

            exceptionElement.addContent(expirationDateElement);
        }

        return new Document(exceptionElement);
    }
}
