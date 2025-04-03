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
	    pipe="result@load-mockup" />

  <p:load name="load-mockup"  >
    <p:with-option
	name="href" select="concat('../../data/data', '.xml')" />
  </p:load>
  <!-- ...... Referencia para siguientes pasos ......
  <p:identity  name="prepareddata" /> 
  -->
  
  <!-- .......... Listado de links..........  -->


  <!--
  <p:xslt
      name="linksdenormalization"
      >
    <p:with-input port="stylesheet" href="../xsl/structuremockupdata.xsl" />
  </p:xslt>

  <p:for-each name="pages" >
    <p:with-input select="/*:pages/*:page"  />

    <p:identity  name="iterationinit" /> 

    <p:xslt name="includesvg" >
      <p:with-input port="source" pipe="result@iterationinit"/>
      <p:with-input port="stylesheet" href="../xsl/svg2html-simple.xsl"/>
    </p:xslt>

    <p:group name="svgfile" >
      <p:output port="result" />
      
      <p:load>
	<p:with-option
	    name="href"
	    select="concat('../../mockup/src/', 
		    //*:key, '.svg')" >	</p:with-option>
      </p:load>
    </p:group>

    <p:insert name="insertsvg"
	      match="//*:body[1]" position="last-child">    
      <p:with-input port="insertion" pipe="result@svgfile"  />
      <p:with-input port="source" pipe="result@includesvg"  />
    </p:insert>

    <p:filter  name="pagelinks" select="./*:links"/>

    <p:insert name="htmlwithlinks"  match="//*:body[1]" position="last-child" >    
      <p:with-input port="insertion" pipe="result@pagelinks"  />
      <p:with-input port="source" pipe="result@svgfile"  />
    </p:insert>
    
    <p:xslt
	name="rebuildlinks"
	>
      <p:with-input port="stylesheet"  href="../xsl/buildlinks.xsl" />
    </p:xslt>
    
    <p:store name="step2">
      <p:with-option
	  name="href"
	  select="concat('../../mockup/web/', 
		  //group, '/', //*:key, '.html')" >
      </p:with-option>
    </p:store>

  </p:for-each>
-->
  <p:store
      name="logfile1"
      href="../../build/prepareddatalog.xml"
      >
    <p:with-input port="source"
	pipe="result@load-mockup" />

  </p:store>
  

</p:declare-step>
