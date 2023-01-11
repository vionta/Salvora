package net.vionta.salvora.util.contenttype;

/**
 * Returns the content type key from the path or file name. 
 */
public class ContentTypeResolver {
	
	/**
	 * Calculates the content type taking the url or path as a 
	 * parameter. 
	 * 
	 * @param URL path 
	 * @return Resolved content type key
	 */
	public static String resolvePath(String path) {
		
		String uri = path.split("\\?")[0];
		String resolvedContenttType = ContentType.DEFAULT.description;
		for (ContentType IterationContentType : ContentType.values()) {
			if(uri.indexOf(IterationContentType.key) > -1) resolvedContenttType = IterationContentType.description;
		}
		return resolvedContenttType; 
	}
	
}
