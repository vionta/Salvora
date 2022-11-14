package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "file-mapping", propOrder = {
    "description"
})
public class FileMapping {

    @XmlElement(required = true)
    protected String description;
    @XmlAttribute(name = "name", required = false)
    protected String name;
    @XmlAttribute(name = "base-url", required = true)
    protected String baseUrl;
    @XmlAttribute(name = "base-path", required = true)
    protected String basePath;
    @XmlAttribute(name = "file-list")
    protected Boolean fileList = false;
    @XmlAttribute(name = "write-allowed")
    protected Boolean writeAllowed = false ;
    @XmlAttribute(name = "delete-allowed")
    protected Boolean deleteAllowed = false;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String value) {
        this.basePath = value;
    }

    public Boolean isFileList() {
        return fileList;
    }

    public void setFileList(Boolean value) {
        this.fileList = value;
    }

    public boolean isWriteAllowed() {
        if (writeAllowed == null) {
            return false;
        } else {
            return writeAllowed;
        }
    }
    
    public void setWriteAllowed(Boolean value) {
        this.writeAllowed = value;
    }

	public Boolean getDeleteAllowed() {
		if (deleteAllowed == null) {
			return false;
		} else {
			return deleteAllowed;
		}
	}

	public void setDeleteAllowed(Boolean deleteAllowed) {
		this.deleteAllowed = deleteAllowed;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public String toString() {
		return "FileMapping [description=" + description + ", name=" + name + ", basePath=" + basePath + ", fileList="
				+ fileList + ", writeAllowed=" + writeAllowed + "]";
	}

    
}
