
package es.udc.ws.movies.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para updateMovie complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="updateMovie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movieDto" type="{http://soap.ws.udc.es/}serviceMovieDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateMovie", propOrder = {
    "movieDto"
})
public class UpdateMovie {

    protected ServiceMovieDto movieDto;

    /**
     * Obtiene el valor de la propiedad movieDto.
     * 
     * @return
     *     possible object is
     *     {@link ServiceMovieDto }
     *     
     */
    public ServiceMovieDto getMovieDto() {
        return movieDto;
    }

    /**
     * Define el valor de la propiedad movieDto.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceMovieDto }
     *     
     */
    public void setMovieDto(ServiceMovieDto value) {
        this.movieDto = value;
    }

}
