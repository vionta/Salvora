package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 	Parameter element, used to map request url path parts to xslt parameters. 
 *       
 * 
 * <p>Clase Java para path-parameter complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="path-parameter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="request-key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="xslt-param-name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "path-parameter")
public class PathParameter {

    @XmlAttribute(name = "key")
    protected String requestKey;
    @XmlAttribute(name = "transformation-param-name")
    protected String transformationParamName;
    @XmlAttribute(name = "default")
    protected String defaultValue;


	/**
     * Obtiene el valor de la propiedad requestKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestKey() {
        return requestKey;
    }

    /**
     * Define el valor de la propiedad requestKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestKey(String value) {
        this.requestKey = value;
    }

    /**
     * Obtiene el valor de la propiedad xsltParamName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformationParamName() {
        return transformationParamName;
    }

    /**
     * Define el valor de la propiedad xsltParamName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformationParamName(String value) {
        this.transformationParamName = value;
    }

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "PathParameter [requestKey=" + requestKey + ", transformationParamName=" + transformationParamName + "]";
	}

}
