package es.udc.ws.movies.client.service.rest.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.movies.client.service.exceptions.ClientSaleExpirationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlClientExceptionConversor {

    public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

    public final static Namespace XML_NS = 
            Namespace.getNamespace("http://ws.udc.es/movies/xml");

    public static InputValidationException fromInputValidationExceptionXml(InputStream ex) 
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new InputValidationException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(InputStream ex) 
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType = rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(), instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static ClientSaleExpirationException fromSaleExpirationExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("saleId", XML_NS);
            Element expirationDate = rootElement.getChild("expirationDate", XML_NS);

            Calendar calendar = null;
            if (expirationDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(CONVERSION_PATTERN, Locale.ENGLISH);
                calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(expirationDate.getText()));
            }

            return new ClientSaleExpirationException(Long.parseLong(instanceId.getTextTrim()), calendar);
        } catch (JDOMException | IOException | ParseException | NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
