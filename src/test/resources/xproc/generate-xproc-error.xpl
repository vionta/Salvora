<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step
    xmlns:p="http://www.w3.org/ns/xproc"
    xmlns:c="http://www.w3.org/ns/xproc-step"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    name="generate-cerror"
    version="3.0">

  <p:input port="source" primary="true"
	   ><p:inline><documento desc="esto no se va a usar" /></p:inline></p:input>
  <p:output port="result" primary="true"
	    pipe="result@load-non-existing-document" />

<!-- This will fail -->	
  <p:load name="load-non-existing-document"  >
    <p:with-option
	name="href" select="concat('./nonexisting-folder/', 'nonexistingfile.xml')" />
  </p:load>
  

</p:declare-step>
