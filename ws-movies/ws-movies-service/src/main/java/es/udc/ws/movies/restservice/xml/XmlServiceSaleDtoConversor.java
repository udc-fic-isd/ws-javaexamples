package es.udc.ws.movies.restservice.xml;

import java.io.IOException;
import java.util.Calendar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import es.udc.ws.movies.dto.ServiceSaleDto;

public class XmlServiceSaleDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/sales/xml");

    public static Document toResponse(ServiceSaleDto sale)
            throws IOException {

        Element saleElement = toXml(sale);

        return new Document(saleElement);
    }

    public static Element toXml(ServiceSaleDto sale) {

        Element saleElement = new Element("sale", XML_NS);

        if (sale.getSaleId() != null) {
            Element saleIdElement = new Element("saleId", XML_NS);
            saleIdElement.setText(sale.getSaleId().toString());
            saleElement.addContent(saleIdElement);
        }

        if (sale.getMovieId() != null) {
            Element movieIdElement = new Element("movieId", XML_NS);
            movieIdElement.setText(sale.getMovieId().toString());
            saleElement.addContent(movieIdElement);
        }

        if (sale.getExpirationDate() != null) {
            Element expirationDateElement = getExpirationDate(sale
                    .getExpirationDate());
            saleElement.addContent(expirationDateElement);
        }

        Element movieUrlElement = new Element("movieUrl", XML_NS);
        movieUrlElement.setText(sale.getMovieUrl());
        saleElement.addContent(movieUrlElement);

        return saleElement;
    }

    private static Element getExpirationDate(Calendar expirationDate) {

        Element releaseDateElement = new Element("expirationDate", XML_NS);
        int day = expirationDate.get(Calendar.DAY_OF_MONTH);
        int month = expirationDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = expirationDate.get(Calendar.YEAR);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));

        return releaseDateElement;

    }

}
