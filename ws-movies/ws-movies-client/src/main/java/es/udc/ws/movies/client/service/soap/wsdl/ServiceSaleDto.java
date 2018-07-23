
package es.udc.ws.movies.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para serviceSaleDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="serviceSaleDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="movieId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="movieUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saleId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceSaleDto", propOrder = {
    "expirationDate",
    "movieId",
    "movieUrl",
    "saleId"
})
public class ServiceSaleDto {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    protected Long movieId;
    protected String movieUrl;
    protected Long saleId;

    /**
     * Obtiene el valor de la propiedad expirationDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Define el valor de la propiedad expirationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Obtiene el valor de la propiedad movieId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * Define el valor de la propiedad movieId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMovieId(Long value) {
        this.movieId = value;
    }

    /**
     * Obtiene el valor de la propiedad movieUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovieUrl() {
        return movieUrl;
    }

    /**
     * Define el valor de la propiedad movieUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovieUrl(String value) {
        this.movieUrl = value;
    }

    /**
     * Obtiene el valor de la propiedad saleId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSaleId() {
        return saleId;
    }

    /**
     * Define el valor de la propiedad saleId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSaleId(Long value) {
        this.saleId = value;
    }

}
