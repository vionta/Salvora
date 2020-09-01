package net.vionta.xfserver.contenttype;

/**
 * Utility enum to handle content types 
 * associations and responses.
 */
public enum ContentType {
	
	DEFAULT("DEFAULT", "text/plain"),
	XML(".xml", "text/xml"),
	XSL(".xsl", "application/xml"),
	XSLT(".xslt", "application/xml"),
	JS(".js", "application/javascript"),
	CSS(".css", "text/css"),
	ATOM(".atom", "application/atom+xml"), 
    JPEG(".jpeg", "image/jpeg"),
    JPG(".jpg", "image/jpeg"),
    GIF(".gif", "image/gif"),
    SVG(".svg", "image/svg+xml"),
    SVGZ(".svgz", "image/svg+xml"),
	HTML(".html", "text/html");
	
	public final String key ;
	public final String description;
	
	ContentType(String key, String description) {
		this.key = key; 
		this.description = description; 
	}

}
