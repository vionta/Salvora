<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step
    xmlns:p="http://www.w3.org/ns/xproc"
    xmlns:c="http://www.w3.org/ns/xproc-step"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    name="prototypedefinitionproc"
    version="3.0">

  <p:input 
  	port="source" >
  	</p:input>

  <p:output port="result"  />
	
	 <!--
 	<p:load name="loadtest" >	
    <p:with-option	name="href"
		select="concat('../mockup/','inicio', '.svg')" >	</p:with-option>
  	</p:load>
 
    -->
    
    <p:insert match="/*" position="last-child">
    <p:with-input port="insertion">
      <location>London {year-from-date(current-date())}</location>
    </p:with-input>
  </p:insert>
  <!--
    <p:identity name="prev" >
    <p:with-input>
      <hello-xproc timestamp="{current-dateTime()}"/>
    </p:with-input>
  </p:identity>
    -->
    
  <p:store name="savetest" >
    <p:with-option	name="href"
			select="concat('../','test-input1', '.xml')" >	</p:with-option>
    <p:with-input port="source"
		  pipe="result@prev"
		  />
  </p:store>
   
</p:declare-step>
