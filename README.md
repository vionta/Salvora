	
# Salvora
<h1>	  Small XForms oriented server/utility						</h1>


<h2>	  Usage:       									</h2>
<ol><li>   Jar : java -jar salvora.jar (Params)*						</li>
  <li>	   Exe : salvora.exe (params)*							</li></ol>


<h2>	  Params									</h2>
<ol><li>   -h: Show this help message							</li>
<li>	   -f:<path> Form folder path. Default form 					</li>
<li>	   -x:<path> XSltForms folder path. Default xsltforms	        		</li>
<li>	   -f:<path> Data folder path. Default data 					</li>
<li>	   -p:<number> Port number (integer). Default 8080				</li>


<h2>	 Example									</h2>
<p>	  java -jar salvora.jar						 
  <br/>	  java -cp salvora.jar net.vionta.xfserver.Salvora  -f:forms -x:xforms -p:8081	</p>