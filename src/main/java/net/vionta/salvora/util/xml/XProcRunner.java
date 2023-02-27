package net.vionta.salvora.util.xml;


import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xml_project.morganaxproc3.XProcCompiler;
import com.xml_project.morganaxproc3.XProcEngine;
import com.xml_project.morganaxproc3.XProcInput;
import com.xml_project.morganaxproc3.XProcOutput;
import com.xml_project.morganaxproc3.XProcPipeline;
import com.xml_project.morganaxproc3.XProcResult;
import com.xml_project.morganaxproc3.XProcSource;
import com.xml_project.morganaxproc3.documents.XProcDocument;

import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.Trigger;

public class XProcRunner {

	static Logger LOGGER = LoggerFactory.getLogger(XProcRunner.class);

	public static void run(Trigger trigger, String content) {
		
		LOGGER.debug(" Running trigger "+trigger.getName());
		/* Create a source with the pipeline */
		//XProcSource pipelineSource = new XProcSource("file:///mypipelines/pipeline.xpl");
		
		try{
			URI xprocUri = new URI(trigger.getSource());
			File f = new File(xprocUri);
			LOGGER.debug(" Uri "+xprocUri +" > "+f.exists());
			XProcSource pipelineSource = new XProcSource(xprocUri);
			LOGGER.debug(" pipeline Source  "+xprocUri);
			/* Create an XProcEngine */
			XProcEngine engine = XProcEngine.newXProc();	
			/* Create a new compiler */
			LOGGER.debug(" Engine "+engine);
			XProcCompiler compiler = engine.newXProcCompiler();			
			/* Compile the pipeline */
			XProcPipeline pipeline = compiler.compile(pipelineSource);
			LOGGER.debug(" Pipeline "+pipeline);
			
			XProcInput input = new XProcInput(engine.getConfiguration());
			for(Parameter parameter : trigger.getParameters()) {
				LOGGER.debug(" Adding parameter "+parameter.getName());
				input.setOption(new QName(parameter.getName()), parameter.getValue());
			}
			
			/* Execute the pipeline */
			XProcOutput output = pipeline.run(input);
	
			LOGGER.debug(" Trigger "+trigger.getName()+" was ()"+output.wasSuccessful());

			if(!output.wasSuccessful()) {
				LOGGER.debug(output.getErrorDocumentSerialized(Boolean.TRUE));
			} 
			
//			/* Check if execution was successful */
			if (output.wasSuccessful()){
//				/* Get names of output ports */
				Iterable<String> ports = output.getPortNames(); 
				for (String portName : output.getPortNames()) {
					LOGGER.debug("Port > "+portName);
					XProcResult result = output.getResults(portName);
//					//XProcResult result = output.getResults(ports[i]);
//					//XProcResult result = output.getResults(ports[i]);
//					/* Get the documents on this port serialized */
//					List<XProcDocument> document = result.getDocuments();
					for(XProcDocument document : result.getDocuments() ) {
						LOGGER.debug(document.getStringRepresentation(true));
					}
				}
//			//for (int j=0; j < document.length; j++)
//			//LOGGER.debug(documents[j]);
			}
//
//			} else {
//	
//			/* Examine runtime errors */
//			LOGGER.debug(output.getErrorDocumentSerialized());
//			}
			//We don't throw errors on trigger failures. 
//		} catch(XProcCompilerException ex) {
//			/* Examine compilation error */
//			LOGGER.error(" Cause: {1}", ex.getCause());
//		} catch (IOException ex) {
//			LOGGER.error(" A failure was detected on the trigger file management");
//			LOGGER.error(" Cause: {1}", ex.getCause());
//		} catch (XProcInterfaceException e) {
//			LOGGER.error(" XProc Error: {1}", e.getCause());
		}	catch (Exception e) {
			LOGGER.error(" XProc Error: (1)", e.getCause());
			LOGGER.error(" XProc Error: {}", e.getCause());
			LOGGER.error(" XProc Error: "+ e.getCause());
			e.printStackTrace();
		}

	}

}
