package es.udc.ws.movies.client.service.rest.xml;

import java.io.InputStream;
import java.util.Calendar;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.movies.client.service.dto.ClientSaleDto;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlClientSaleDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/sales/xml");

    public static ClientSaleDto toClientSaleDto(InputStream saleXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(saleXml);
            Element rootElement = document.getRootElement();

            return toClientSaleDto(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientSaleDto toClientSaleDto(Element saleElement)
            throws ParsingException, DataConversionException,
            NumberFormatException {
        if (!"sale".equals(saleElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + saleElement.getName() + "' ('sale' expected)");
        }
        Element saleIdElement = saleElement.getChild("saleId", XML_NS);
        Long saleId = null;
        if (saleIdElement != null) {
            saleId = Long.valueOf(saleIdElement.getTextTrim());
        }

        Element movieIdElement = saleElement.getChild("movieId", XML_NS);
        Long movieId = null;
        if (movieIdElement != null) {
            movieId = Long.valueOf(movieIdElement.getTextTrim());
        }

        Calendar expirationDate = getExpirationDate(saleElement.getChild(
                "expirationDate", XML_NS));

        String movieUrl = saleElement.getChildTextTrim("movieUrl", XML_NS);

        return new ClientSaleDto(saleId, movieId, expirationDate, movieUrl);
    }

    private static Calendar getExpirationDate(Element expirationDateElement)
            throws DataConversionException {

        if (expirationDateElement == null) {
            return null;
        }
        int day = expirationDateElement.getAttribute("day").getIntValue();
        int month = expirationDateElement.getAttribute("month").getIntValue();
        int year = expirationDateElement.getAttribute("year").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);

        return releaseDate;

    }

}
