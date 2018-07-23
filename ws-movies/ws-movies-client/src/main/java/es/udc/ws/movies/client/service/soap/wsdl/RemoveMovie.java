
package es.udc.ws.movies.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para removeMovie complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="removeMovie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movieId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeMovie", propOrder = {
    "movieId"
})
public class RemoveMovie {

    protected Long movieId;

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

}
