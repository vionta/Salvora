package net.vionta.salvora.config.dto;


import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collection", propOrder = {
    "description", "triggers"
})
public class FileMapping implements NetworkPathConfiguration  {

    @XmlElement(required = false)
    protected String description;
    @XmlAttribute(name = "name", required = false)
    protected String name;
    @XmlAttribute(name = "base-internal-path", required = true)
    protected String baseInternalPath;
    @XmlAttribute(name = "base-path", required = true)
    protected String basePath;
    
    @XmlAttribute(name = "internal-path")
    protected String internalPath;
    @XmlAttribute(name = "path")
    protected String path;
    
    
    @XmlAttribute(name = "file-list")
    protected Boolean fileList = false;
    @XmlAttribute(name = "write-allowed")
    protected Boolean writeAllowed = false ;
    @XmlAttribute(name = "delete-allowed")
    protected Boolean deleteAllowed = false;

    @XmlElement(name="trigger", type = Trigger.class)
    protected List<Trigger> triggers = new ArrayList<Trigger>();

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

	public String getBaseInternalPath() {
		if(baseInternalPath == null) return getBasePath();
		else return baseInternalPath;
	}

	public void setBaseInternalPath(String baseInternalPath) {
		this.baseInternalPath = baseInternalPath;
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public Boolean getWriteAllowed() {
		return writeAllowed;
	}

	public String getInternalPath() {
		return internalPath;
	}

	public void setInternalPath(String internalPath) {
		this.internalPath = internalPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getType() {
		return LOCAL_SOURCE_TYPE;
	}

}
