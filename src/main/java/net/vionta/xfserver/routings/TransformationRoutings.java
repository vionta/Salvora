package net.vionta.xfserver.routings;

import java.io.IOException;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import net.vionta.salvora.config.dto.Transformation;
import net.vionta.salvora.config.dto.TransformationStep;
import net.vionta.salvora.launch.Options;
import net.vionta.salvora.util.contenttype.ContentTypeResolver;
import net.vionta.salvora.util.file.NetworkFileReader;
import net.vionta.salvora.util.xml.XsltTransform;
import net.vionta.xfserver.ServerImpl;

/**
 * Creates the vertx mapping for the 
 * transformations, according to the mapping 
 * file.
 */
public class TransformationRoutings {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	/**
	 * Configures a vertx route for a determined 
	 * transformation.
	 * 
	 * @param router
	 * @param transformation the defined transformation.
	 * @param options The options object. 
	 */
	public static void routeTransformation(Router router, Transformation transformation, Options options) {		
		LOGGER.info("Logging transformation "+transformation);
		String vertxRoute = TransformationUrlCalculation.calculateVertxRoute(transformation);
		LOGGER.debug("Vertx Route : "+vertxRoute);
		router.route().path(vertxRoute).method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			LOGGER.debug("Logging transformation "+transformation);
			try {
				if(Transformation.LOCAL_SOURCE_TYPE.equals(transformation.getType())) {
					try {
						LOGGER.debug("Local Source: 1");
						LOGGER.debug(transformation.toString());
						LOGGER.debug(" Steps " +(transformation.getTransformationSteps() != null ));
						LOGGER.debug(" Steps " +transformation.getTransformationSteps().size());
						
						Iterator<TransformationStep> it = transformation.getTransformationSteps().iterator();
						
						TransformationStep step = it.next();
						LOGGER.debug("Step 2"+step.getName() +" "+step.getSource());
						String content;	
						content = XsltTransform.transformDocument(TransformationUrlCalculation.convertInternalUrl(request.normalisedPath(), transformation), step.getSource());
						LOGGER.debug("conent:  "+content);
						content = chainNextTransformations(it, content);
						LOGGER.debug("conent (chained) :  "+content);
						LOGGER.debug("Writing content: ");
						response.putHeader("Content-length", Integer.toString(content.getBytes().length));
						response.putHeader("Content-type", ContentTypeResolver.resolvePath(request.request().path()));
						response.write(content);
					} catch (Exception e) {
						LOGGER.warn("Error in transformation: "+e.getMessage());
						ErrorManager.notifyError(response, "Error handling transformation");
					}
				} else if (Transformation.LOCAL_NETWORK_SOURCE_TYPE.equals(transformation.getType()) || Transformation.REMOTE_NETWORK_SOURCE_TYPE.equals(transformation.getType())) {
					LOGGER.info("Transforming "+transformation.getName() +" | "+transformation.getPath()+" -> "+transformation.getUrl());
					URL initialContentUrl = new URL(TransformationUrlCalculation.calculateCallUrl(transformation , request.normalisedPath(), String.valueOf(options.getPort()), TransformationUrlCalculation.HTTP_SCHEME));
					String initialContent = NetworkFileReader.readNetworkUrlToString(initialContentUrl);
					String content = chainTransformations(transformation.getTransformationSteps(), initialContent);	
					response.putHeader("Content-length", Integer.toString(content.getBytes().length));
					response.putHeader("Content-type", ContentTypeResolver.resolvePath(request.request().path()));
					response.write(content);
				}
			} catch (TransformerException | SAXException | IOException | ParserConfigurationException e) {
				LOGGER.warn("Error while performing transformation : " , e.getMessage());
				ErrorManager.notifyError(response, "Transformation type not recognised or not informad");
			}
				response.setStatusCode(200);
				response.end();
		});
	}



	/**
	 * Performs a chain of transformations over 
	 * a content string. 
	 * 
	 * @param transformationSteps
	 * @param initialContent
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static String chainTransformations(List<TransformationStep> transformationSteps, String initialContent) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		String content = initialContent;
		LOGGER.info("Initial Content: "+content);
		for( TransformationStep step: transformationSteps) {
			content = XsltTransform.transformDocumentFromContent(content, step.getSource());
			LOGGER.info("Step : "+step.getName());
		} 
		return content;
	}


	/**
	 * Executes a local transformation on the first element and 
	 * afterwards it chains the transformations using the generated
	 * content. 
	 * 
	 * TODO : Add parameter binding from url routes, etc.
	 * 
	 * @param it
	 * @param content
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static String chainNextTransformations(Iterator<TransformationStep> it, String content)
		throws TransformerException, SAXException, IOException, ParserConfigurationException {
		TransformationStep step;
		if(it.hasNext()) 
			do  {
				step =  it.next();
				content = XsltTransform.transformDocumentFromContent(content, step.getSource());
				LOGGER.info("Content n :"+content);
			} while (it.hasNext());
		return content;
	}

}