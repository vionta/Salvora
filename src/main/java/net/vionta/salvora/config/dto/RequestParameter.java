package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request-parameter")
public class RequestParameter {

    @XmlAttribute(name = "request-key")
    protected String requestKey;
    @XmlAttribute(name = "xslt-param-name")
    protected String xsltParamName;


    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String value) {
        this.requestKey = value;
    }

    public String getXsltParamName() {
        return xsltParamName;
    }

    public void setXsltParamName(String value) {
        this.xsltParamName = value;
    }

}
