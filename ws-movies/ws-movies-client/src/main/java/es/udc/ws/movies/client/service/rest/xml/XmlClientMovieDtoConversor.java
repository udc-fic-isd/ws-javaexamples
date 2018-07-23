package es.udc.ws.movies.client.service.rest.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class XmlClientMovieDtoConversor {

    public final static Namespace XML_NS = 
            Namespace.getNamespace("http://ws.udc.es/movies/xml");

    public static Document toXml(ClientMovieDto movie)
            throws IOException {

        Element movieElement = toJDOMElement(movie);

        return new Document(movieElement);
    }

    public static Element toJDOMElement(ClientMovieDto movie) {

        Element movieElement = new Element("movie", XML_NS);

        if (movie.getMovieId() != null) {
            Element identifierElement = new Element("movieId", XML_NS);
            identifierElement.setText(movie.getMovieId().toString());
            movieElement.addContent(identifierElement);
        }

        Element runtimeElement = new Element("runtime", XML_NS);
        runtimeElement.setText(Integer.toString(
                movie.getRuntimeHours() * 60 + movie.getRuntimeMinutes()));
        movieElement.addContent(runtimeElement);

        Element priceElement = new Element("price", XML_NS);
        priceElement.setText(Double.toString(movie.getPrice()));
        movieElement.addContent(priceElement);

        Element titleElement = new Element("title", XML_NS);
        titleElement.setText(movie.getTitle());
        movieElement.addContent(titleElement);

        Element descriptionElement = new Element("description", XML_NS);
        descriptionElement.setText(movie.getDescription());
        movieElement.addContent(descriptionElement);

        return movieElement;
    }

    public static ClientMovieDto toClientMovieDto(InputStream movieXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(movieXml);
            Element rootElement = document.getRootElement();

            return toClientMovieDto(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientMovieDto> toClientMovieDtos(InputStream movieXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(movieXml);
            Element rootElement = document.getRootElement();

            if (!"movies".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                        + rootElement.getName() + "' ('movies' expected)");
            }
            List<Element> children = rootElement.getChildren();
            List<ClientMovieDto> movieDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                movieDtos.add(toClientMovieDto(element));
            }

            return movieDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientMovieDto toClientMovieDto(Element movieElement)
            throws ParsingException, DataConversionException {
        if (!"movie".equals(
                movieElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + movieElement.getName() + "' ('movie' expected)");
        }
        Element identifierElement = movieElement.getChild("movieId", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String description = movieElement
                .getChildTextNormalize("description", XML_NS);

        String title = movieElement.getChildTextNormalize("title", XML_NS);

        short runtime = Short.valueOf(
                movieElement.getChildTextTrim("runtime", XML_NS));

        float price = Float.valueOf(
                movieElement.getChildTextTrim("price", XML_NS));

        return new ClientMovieDto(identifier, title, (short) (runtime / 60), (short) (runtime % 60),
                description, price);
    }

}
