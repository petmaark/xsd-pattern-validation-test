//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.04 at 03:17:23 PM CET 
//


package hu.icellmobilsoft.xsd.pattern.validation.test.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotBlankDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotBlankDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="notBlank" type="{http://icellmobilsoft.hu/pattern/validation/test/dto}SimpleText255NotBlankType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotBlankDataType", propOrder = {
    "notBlank"
})
@XmlSeeAlso({
    NotBlankElement.class
})
public class NotBlankDataType {

    protected String notBlank;

    /**
     * Gets the value of the notBlank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotBlank() {
        return notBlank;
    }

    /**
     * Sets the value of the notBlank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotBlank(String value) {
        this.notBlank = value;
    }

}
