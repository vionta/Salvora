package net.vionta.salvora.util.xml;


import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.xml_project.morganaxproc3.compiler.XProcCompilerException;
import com.xml_project.morganaxproc3.documents.XProcDocument;

import net.vionta.salvora.config.dto.TransformationParameter;
import net.vionta.salvora.config.dto.Trigger;
import net.vionta.salvora.server.response.error.ErrorManager;
import nu.validator.saxtree.Document;

public class XProcRunner {

	static Logger LOGGER = LoggerFactory.getLogger(XProcRunner.class);

	public static void run(Trigger trigger, String content, List<TransformationParameter> parameters) {
		
		LOGGER.debug(" Running trigger "+trigger.getName());
		/* Create a source with the pipeline */
		
		try{
			URI xprocUri = loadXProcFilePath(trigger.getSource());
			XProcSource pipelineSource = new XProcSource(xprocUri);
			LOGGER.debug(" Pipeline Source  "+xprocUri);
			/* Create an XProcEngine */
			XProcEngine engine = XProcEngine.newXProc();	
			/* Create a new compiler */
			LOGGER.debug(" Engine "+engine);
			XProcCompiler compiler = engine.newXProcCompiler();			
			LOGGER.debug(" Compiler :"+compiler);
			
			XProcPipeline pipeline = compiler.compile(pipelineSource);
			LOGGER.debug(" Pipeline "+pipeline);
			
			XProcInput input = new XProcInput(engine.getConfiguration());
			
//			for(Entry<String, String> parameter : parameters.entrySet()) {
//				LOGGER.debug(" Adding parameter "+parameter.getKey()+" -> "+parameter.getValue() );
//				input.setOption(new QName(parameter.getKey()), "'"+parameter.getValue()+"'");
//			}

			for(TransformationParameter parameter : parameters) {
				LOGGER.debug(" Adding parameter "+parameter.getName()+" -> "+parameter.getValue() );
				if(parameter.getInputPort()) input.addInput(parameter.getName(), new XProcSource(loadXProcFilePath(parameter.getValue())));
				else input.setOption(new QName(parameter.getName()), "'"+parameter.getValue()+"'");
			}
			
//			compiler.
//			XProcDocument xProcSource = new XProcDocument();
//			XProcSource pipelineSource = new XProcSource();
//			XProcDocument pipelineSource = new XProcDocument;
//			input.addInput("source" , xProcSource);
			
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
//					/* Get the documents on this port serialized */
//					List<XProcDocument> document = result.getDocuments();
					for(XProcDocument document : result.getDocuments() ) {
						LOGGER.debug(document.getStringRepresentation(true));
					}
				}
			}

			//We don't throw errors on trigger failures. 
		} catch(XProcCompilerException ex) {
			LOGGER.error(" XProc Compilation Error ", ex.getCause());
		}	catch (Exception e) {
			e.printStackTrace();
			ErrorManager.logError(null, "Error ejecutando Xproc  "+trigger.getName()+ "| Causa :"+e.getMessage()+" - "+e.getCause());
			LOGGER.error(" XProc Error: (1)", e.getCause());
			LOGGER.error(" XProc Error: {}", e.getCause());
			LOGGER.error(" XProc Error: "+ e.getMessage());
			e.printStackTrace();
		}

	}

	public static URI loadXProcFilePath(String source) throws URISyntaxException {
		if (calculateLocalFileAbsolutePath(source)==null) throw new IllegalStateException(" Trigger configuration is empty, no url was provided ");
		URI xprocUri = new URI(calculateLocalFileAbsolutePath(source));
		File f = new File(xprocUri);
		if (!f.exists()) throw new IllegalStateException(" XProc file "+source+" not found");
		return xprocUri;
	}

	public static String calculateLocalFileAbsolutePath(String source) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		String userDirectory = new File("").getAbsolutePath().replace("\\", "/");
		System.out.println(" UD "+userDirectory);
		String calculatedURL = "file:///"+userDirectory+"/"+source;
		System.out.println(calculatedURL );
		return calculatedURL;
	}

}
