
package es.udc.ws.movies.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para serviceMovieDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="serviceMovieDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="movieId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="runtime" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceMovieDto", propOrder = {
    "description",
    "movieId",
    "price",
    "runtime",
    "title"
})
public class ServiceMovieDto {

    protected String description;
    protected Long movieId;
    protected float price;
    protected short runtime;
    protected String title;

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Obtiene el valor de la propiedad price.
     * 
     */
    public float getPrice() {
        return price;
    }

    /**
     * Define el valor de la propiedad price.
     * 
     */
    public void setPrice(float value) {
        this.price = value;
    }

    /**
     * Obtiene el valor de la propiedad runtime.
     * 
     */
    public short getRuntime() {
        return runtime;
    }

    /**
     * Define el valor de la propiedad runtime.
     * 
     */
    public void setRuntime(short value) {
        this.runtime = value;
    }

    /**
     * Obtiene el valor de la propiedad title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
