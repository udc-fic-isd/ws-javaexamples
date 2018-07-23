
package es.udc.ws.movies.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.movies.client.service.soap.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindMoviesResponse_QNAME = new QName("http://soap.ws.udc.es/", "findMoviesResponse");
    private final static QName _UpdateMovie_QNAME = new QName("http://soap.ws.udc.es/", "updateMovie");
    private final static QName _BuyMovieResponse_QNAME = new QName("http://soap.ws.udc.es/", "buyMovieResponse");
    private final static QName _AddMovie_QNAME = new QName("http://soap.ws.udc.es/", "addMovie");
    private final static QName _AddMovieResponse_QNAME = new QName("http://soap.ws.udc.es/", "addMovieResponse");
    private final static QName _BuyMovie_QNAME = new QName("http://soap.ws.udc.es/", "buyMovie");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _FindMovies_QNAME = new QName("http://soap.ws.udc.es/", "findMovies");
    private final static QName _UpdateMovieResponse_QNAME = new QName("http://soap.ws.udc.es/", "updateMovieResponse");
    private final static QName _FindSale_QNAME = new QName("http://soap.ws.udc.es/", "findSale");
    private final static QName _RemoveMovieResponse_QNAME = new QName("http://soap.ws.udc.es/", "removeMovieResponse");
    private final static QName _FindSaleResponse_QNAME = new QName("http://soap.ws.udc.es/", "findSaleResponse");
    private final static QName _SoapSaleExpirationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapSaleExpirationException");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
    private final static QName _RemoveMovie_QNAME = new QName("http://soap.ws.udc.es/", "removeMovie");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.movies.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateMovieResponse }
     * 
     */
    public UpdateMovieResponse createUpdateMovieResponse() {
        return new UpdateMovieResponse();
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link FindMovies }
     * 
     */
    public FindMovies createFindMovies() {
        return new FindMovies();
    }

    /**
     * Create an instance of {@link FindSale }
     * 
     */
    public FindSale createFindSale() {
        return new FindSale();
    }

    /**
     * Create an instance of {@link RemoveMovieResponse }
     * 
     */
    public RemoveMovieResponse createRemoveMovieResponse() {
        return new RemoveMovieResponse();
    }

    /**
     * Create an instance of {@link FindSaleResponse }
     * 
     */
    public FindSaleResponse createFindSaleResponse() {
        return new FindSaleResponse();
    }

    /**
     * Create an instance of {@link SoapSaleExpirationExceptionInfo }
     * 
     */
    public SoapSaleExpirationExceptionInfo createSoapSaleExpirationExceptionInfo() {
        return new SoapSaleExpirationExceptionInfo();
    }

    /**
     * Create an instance of {@link RemoveMovie }
     * 
     */
    public RemoveMovie createRemoveMovie() {
        return new RemoveMovie();
    }

    /**
     * Create an instance of {@link FindMoviesResponse }
     * 
     */
    public FindMoviesResponse createFindMoviesResponse() {
        return new FindMoviesResponse();
    }

    /**
     * Create an instance of {@link UpdateMovie }
     * 
     */
    public UpdateMovie createUpdateMovie() {
        return new UpdateMovie();
    }

    /**
     * Create an instance of {@link BuyMovieResponse }
     * 
     */
    public BuyMovieResponse createBuyMovieResponse() {
        return new BuyMovieResponse();
    }

    /**
     * Create an instance of {@link AddMovie }
     * 
     */
    public AddMovie createAddMovie() {
        return new AddMovie();
    }

    /**
     * Create an instance of {@link AddMovieResponse }
     * 
     */
    public AddMovieResponse createAddMovieResponse() {
        return new AddMovieResponse();
    }

    /**
     * Create an instance of {@link BuyMovie }
     * 
     */
    public BuyMovie createBuyMovie() {
        return new BuyMovie();
    }

    /**
     * Create an instance of {@link ServiceMovieDto }
     * 
     */
    public ServiceMovieDto createServiceMovieDto() {
        return new ServiceMovieDto();
    }

    /**
     * Create an instance of {@link ServiceSaleDto }
     * 
     */
    public ServiceSaleDto createServiceSaleDto() {
        return new ServiceSaleDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindMoviesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findMoviesResponse")
    public JAXBElement<FindMoviesResponse> createFindMoviesResponse(FindMoviesResponse value) {
        return new JAXBElement<FindMoviesResponse>(_FindMoviesResponse_QNAME, FindMoviesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateMovie")
    public JAXBElement<UpdateMovie> createUpdateMovie(UpdateMovie value) {
        return new JAXBElement<UpdateMovie>(_UpdateMovie_QNAME, UpdateMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buyMovieResponse")
    public JAXBElement<BuyMovieResponse> createBuyMovieResponse(BuyMovieResponse value) {
        return new JAXBElement<BuyMovieResponse>(_BuyMovieResponse_QNAME, BuyMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addMovie")
    public JAXBElement<AddMovie> createAddMovie(AddMovie value) {
        return new JAXBElement<AddMovie>(_AddMovie_QNAME, AddMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addMovieResponse")
    public JAXBElement<AddMovieResponse> createAddMovieResponse(AddMovieResponse value) {
        return new JAXBElement<AddMovieResponse>(_AddMovieResponse_QNAME, AddMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buyMovie")
    public JAXBElement<BuyMovie> createBuyMovie(BuyMovie value) {
        return new JAXBElement<BuyMovie>(_BuyMovie_QNAME, BuyMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindMovies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findMovies")
    public JAXBElement<FindMovies> createFindMovies(FindMovies value) {
        return new JAXBElement<FindMovies>(_FindMovies_QNAME, FindMovies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateMovieResponse")
    public JAXBElement<UpdateMovieResponse> createUpdateMovieResponse(UpdateMovieResponse value) {
        return new JAXBElement<UpdateMovieResponse>(_UpdateMovieResponse_QNAME, UpdateMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findSale")
    public JAXBElement<FindSale> createFindSale(FindSale value) {
        return new JAXBElement<FindSale>(_FindSale_QNAME, FindSale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeMovieResponse")
    public JAXBElement<RemoveMovieResponse> createRemoveMovieResponse(RemoveMovieResponse value) {
        return new JAXBElement<RemoveMovieResponse>(_RemoveMovieResponse_QNAME, RemoveMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findSaleResponse")
    public JAXBElement<FindSaleResponse> createFindSaleResponse(FindSaleResponse value) {
        return new JAXBElement<FindSaleResponse>(_FindSaleResponse_QNAME, FindSaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapSaleExpirationExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapSaleExpirationException")
    public JAXBElement<SoapSaleExpirationExceptionInfo> createSoapSaleExpirationException(SoapSaleExpirationExceptionInfo value) {
        return new JAXBElement<SoapSaleExpirationExceptionInfo>(_SoapSaleExpirationException_QNAME, SoapSaleExpirationExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeMovie")
    public JAXBElement<RemoveMovie> createRemoveMovie(RemoveMovie value) {
        return new JAXBElement<RemoveMovie>(_RemoveMovie_QNAME, RemoveMovie.class, null, value);
    }

}
