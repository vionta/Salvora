package net.vionta.salvora.config.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fileMappings",
    "transformations"
})
@XmlRootElement(name = "salvora-application")
public class SalvoraApplication {

    @XmlElement(name = "collection", type=FileMapping.class)
    protected List<FileMapping> fileMappings = new ArrayList<FileMapping>();
    @XmlElement(name = "transformation", type=Transformation.class)
    protected List<Transformation> transformations = new ArrayList<Transformation>();
	
    public List<FileMapping> getFileMappings() {
		return fileMappings;
	}
	public void setFileMappings(List<FileMapping> fileMapping) {
		this.fileMappings = fileMapping;
	}
	public List<Transformation> getTransformations() {
		return transformations;
	}
	public void setTransformations(List<Transformation> transformation) {
		this.transformations = transformation;
	}
	@Override
	public String toString() {
		return "SalvoraApplication [fileMappings=" + fileMappings + ", transformation=" + transformations + "]";
	}
    
}