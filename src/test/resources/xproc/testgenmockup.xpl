<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step
    xmlns:p="http://www.w3.org/ns/xproc"
    xmlns:c="http://www.w3.org/ns/xproc-step"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    name="prototypedefinitionproc"
    version="3.0">

  <p:input port="source" primary="true"
	   ><p:inline><documento desc="esto no se va a usar" /></p:inline></p:input>

<!--
  <p:option name="mockup-document" />
-->
  <!--<p:inline><documento/></p:inline>z-->
  <p:output port="result" primary="true"
	    pipe="result@savetest" />



    <p:load name="loadtest" >
    <p:with-option	name="href"
	select="concat('../mockup/','inicio', '.svg')" >	</p:with-option>
    </p:load>
    
  <p:store name="savetest" >
    <p:with-option	name="href"
			select="concat('../','test-out_mainform', '.svg')" >	</p:with-option>

<!-- <p:inline><documento/></p:inline>
-->
    <p:with-input port="source"
		  pipe="result@loadtest"
		  />

  </p:store>
  

</p:declare-step>
