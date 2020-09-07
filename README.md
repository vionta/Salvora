
<html><body>	
# Salvora
<h1>	  Small XForms oriented server/utility						</h1>

<h2>	  Introduction.									</h2>
<p>	  Salvora is a small and simple utility to serve XSLTForms (see
	  http://www.agencexml.com/xsltforms.htm for more details about XSLTForms)
	  applications locally, without embedding them in a full application server.
	  Current browsers tend to limit some of the functionalities of XSLT and
	  XForms when run directly from local file systems.
</p>
<p>	Salvora utility server also exposes a data folder with REST read and write
	access.										</p>  

<h2> 	Use cases. 	       	    	      	   	       	    	     	   	</h2>
<p>	Salvora is a simple and easy way to run XForms directly from local folders. 
<ul>
<li>	Salvora can be used to expose forms that are used at some point in time
	to capture small pieces of data. 	       	       	  	       	    	</li>
<li>	Salvora can also be used for testing and developing Forms and browser XSLT’s
	without deployments.</li>
<li>    Run simple XForms on controlled environments with simple configurations.	</li></ul>
</p>

<h2>	Usage.       									</h2>
<p>	Download and run the executable or Jar file in the desired folder.              </p>

<ol>
  <li>  Jar : java -jar salvora.jar (Params)*							</li>
  <li>  java :java -cp salvora.jar net.vionta.xfserver.Salvora  -f:forms -x:xforms -p:8081	</li>
  <li>  Exe : salvora.exe (params)*								</li>
</ol>

<p>    The application will serve only the following folder files:				</p>
<ul>
  <li>  xsltforms [Read only  Configurable] : Predefined xsltform folder, intended for storing
        and exposing the xsltforms distributions from www.agencexml.com/xsltforms.             </li>
  <li> form [Read only  Configurable] : Predefined folder for your form.                       </li>
  <li>  data [Read + Write  Configurable] : Predefined writeable data folder.                   </li>
</ul>
<p>     Open a browser on http://localhos:8080/<path> and start browsing.                       </p>
  
<p>    Example paths: 	       				    	     	       		</p>
<ul>
  <li>	 http://localhos:8080/xsltforms/xsltforms.xslt					</li>
  <li> 	 http://localhos:8080/form/myform.xml						</li>
  <li>	 http://194.12.45.68:8080/data/mydata.xml					</li> 
</ul>

<h2>    Configuration.									</h2>
<p>	The following parameters can be passed to the XFServer : 			</p>

<h3>	  Params.									</h3>
<ol>
  <li>     -h: Show this help message							</li>
  <li>	   -f:<path> Form folder path. Default form 					</li>
  <li>	   -x:<path> XSltForms folder path. Default xsltforms	        		</li>
  <li>	   -f:<path> Data folder path. Default data 					</li>
  <li>	   -p:<number> Port number (integer). Default 8080				</li>
</ol>


<h3>	 Examples.									</h3>
<p>	  java -jar salvora.jar						 
  <br/>	  java -cp salvora.jar net.vionta.xfserver.Salvora  -f:forms -x:xforms -p:8081	</p>


<p>	Salvora utility does not generate the folder content list at this moment.	</p>

<h2>	Requirements.									</h2>
<p>	Salvora requires at least a Java 12 runtime. 					</p>

<h2>	Frequent Questions and answers.							</h2>
<p><b>	¿ Can the server be accessed from external computers ? 				</b>
<br/>   Yes, this is a small utility intended for occasional use. Write access is
	prevented for any other folder outside the data folder. The continuous usage
	in production machines is discouraged.						</p>

<p><b>	¿ Can I open two instances on the same machine ?				</b>
<br/> 	Yes, you can configure different ports on different routes at the same time
      	in the same machine.
</p>

<h2>	Contact.									</h2>
<p>     Salvora is provided by www.neonbluetide.com. Neon Blue Tide is part of
        www.vionta.net.                                                                 </p>
</body>
</html>
