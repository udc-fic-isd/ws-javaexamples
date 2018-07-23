
package es.udc.ws.jaxwstutorial.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para incorrectTickerSymbolExceptionInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="incorrectTickerSymbolExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="incorrectTickerSymbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incorrectTickerSymbolExceptionInfo", propOrder = {
    "incorrectTickerSymbol"
})
public class IncorrectTickerSymbolExceptionInfo {

    protected String incorrectTickerSymbol;

    /**
     * Obtiene el valor de la propiedad incorrectTickerSymbol.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncorrectTickerSymbol() {
        return incorrectTickerSymbol;
    }

    /**
     * Define el valor de la propiedad incorrectTickerSymbol.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncorrectTickerSymbol(String value) {
        this.incorrectTickerSymbol = value;
    }

}
