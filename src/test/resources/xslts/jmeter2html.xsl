<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:con="http://eviware.com/soapui/config" version="1.0">
<xsl:output method="xml" encoding="utf-8" indent="yes"/>


<xsl:template match="/"><html><head>
<link rel="stylesheet" type="text/css" href="style.css" ></link>
	<style type="text/css">
		body {
		  font-family: Helvetica,sans-serif,Arial;
		}
		/*
		.section {
			margin-left:3em;
		}
		div {
			padding-left:45px;
		}
		
		td , th{padding-left : 2em ; padding-right : 2em }
		th { background-color: #9999ff}*/
   </style>
</head>
<body>
    <xsl:apply-templates /><div class="help"/></body></html>

  </xsl:template><xsl:template match="con:soapui-project">
   <div><h1>Project Name : <xsl:value-of select="@testname"/></h1></div>
   <xsl:apply-templates select="."/>
   
</xsl:template>
   
<xsl:template match="ThreadGroup">
  <div class="section project">
  <hr></hr>
	<h2> Grupo : <xsl:value-of select="@testname"/> </h2>
	<p> Descripción : <xsl:value-of select="stringProp[@name='TestPlan.comments']"/></p>
   <xsl:apply-templates  />
    <hr></hr>
  </div>
    
</xsl:template>

   
<xsl:template match="HTTPSamplerProxy">
  <div class="section interface" >
    
  <h3>Petición HTTP  : <xsl:value-of select="@testname"/></h3>
  
	<p>
		<xsl:value-of select="stringProp[@name='TestPlan.comments']"/>
	</p>
    
	<!--
	
	  <HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy"
	  testname="Eliminar todos los documentos" enabled="false">
          <elementProp name="HTTPsampler.Arguments" elementType="Arguments"
		  guiclass="HTTPArgumentsPanel" testclass="Arguments" testname="Variables pré-définies" enabled="true">
            <collectionProp name="Arguments.arguments">
              <elementProp name="stream.body" elementType="HTTPArgument">
                <boolProp name="HTTPArgument.always_encode">true</boolProp>
                <stringProp name="Argument.value">&lt;delete&gt;&lt;query&gt;*:*&lt;/query&gt;&lt;/delete&gt;</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
                <boolProp name="HTTPArgument.use_equals">true</boolProp>
                <stringProp name="Argument.name">stream.body</stringProp>
              </elementProp>
              <elementProp name="commit" elementType="HTTPArgument">
                <boolProp name="HTTPArgument.always_encode">true</boolProp>
                <stringProp name="Argument.value">true</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
                <boolProp name="HTTPArgument.use_equals">true</boolProp>
                <stringProp name="Argument.name">commit</stringProp>
              </elementProp>
            </collectionProp>
          </elementProp>
          <stringProp name="HTTPSampler.domain"></stringProp>
          <stringProp name="HTTPSampler.port"></stringProp>
          <stringProp name="HTTPSampler.protocol"></stringProp>
          <stringProp name="HTTPSampler.contentEncoding"></stringProp>
          
          HTTPSamplerProxy>
	  -->
	
	
    <table class="horizontal summary">
      <tr>
	  <th>TYPE</th> 
		<th>PATH</th>
    <th>BODY</th>
    </tr>
	
	  <tr>
        <td><xsl:value-of select="stringProp[@name='HTTPSampler.method']"/></td>
		<td><xsl:value-of select="stringProp[@name='HTTPSampler.path']"/></td>
        <td><xsl:value-of select="stringProp[@name='HTTPSampler.value']"/></td>
      </tr>
    </table>
	
	<br></br>
	<br></br>
	
	<table class="horizontal summary">
      <tr>
	  <th>PARAM</th>
		<th>VALUE</th>
	  </tr>
    
	<xsl:for-each select=".//elementProp">
	  <tr>
        <td><xsl:value-of select="stringProp[@name='Argument.name']"/></td>
		<td><xsl:value-of select="stringProp[@name='Argument.value']"/></td>

      </tr>
	</xsl:for-each>
    </table>
    
    
    <xsl:apply-templates> </xsl:apply-templates>
  </div>
    
</xsl:template>
   
   
   
   
     <xsl:template match="con:resource">
      <div class="section resource">
        
      <h3>Resource</h3>
		<p>
			<xsl:value-of select="con:description/text()"/>
		</p>
      
       <table class="horizontal summary">
      <tr><th>NAME</th><th>PATH</th></tr>
      <tr>
        <td><xsl:value-of select="@name"/></td>
        <td><xsl:value-of select="@path"/></td>
        
      </tr>
    </table>
       
       
     <xsl:apply-templates> </xsl:apply-templates>

      </div>
      
     </xsl:template>
    
<xsl:template match="con:parameters">
    <div class="section  parameters">
      <xsl:if test="con:parameter" >
        <h3>Parameters</h3>
      <dl>
      <dt>Parameters</dt>
       <table>
          <tr><th>NAME</th>  <th>STYLE </th><th>DESCRIPTION</th>
      </tr>
      <xsl:for-each select="./con:parameter" >
        <tr>
           <td><xsl:value-of select="con:name/text()"/></td>  <td><xsl:value-of select="con:style/text()"/></td><td><xsl:value-of select="con:description/text()"/></td>
        </tr>
      </xsl:for-each>
       </table>
        <xsl:apply-templates> </xsl:apply-templates>
      </dl>
    </xsl:if>
  </div>
</xsl:template>
     
     
<xsl:template match="con:method">
  <div class="section method">
  
    <h3>Method</h3>
	
       
    <table class="horizontal summary">
      <tr><th>NAME</th><th>METHOD</th><th>DESC</th></tr>
      <tr>
        <td><xsl:value-of select="@name"/></td>
        <td><xsl:value-of select="@method"/></td>
        <td><xsl:value-of select="con:description/text()"/></td>
      </tr>
    </table>
       
 <xsl:apply-templates> </xsl:apply-templates>
        
  </div>
      </xsl:template>
      
<xsl:template match="con:request[@name]">
	<div class="section request">
		<h3>Request</h3>
		<table class="horizontal summary">
			<tr><th>NAME</th><th>TYPE</th><th>POST</th><th>DESC</th></tr>
			<tr>
				<td><xsl:value-of select="@name"/></td>
				<td><xsl:value-of select="@mediaType"/></td>
				<td><xsl:value-of select="@postQueryString"/></td>
				<td><xsl:value-of select="con:description/text()"/></td>
			</tr>
		</table>
		<xsl:if test="con:request/text()" >
			<dl>
				<dt>
				  Request
				</dt>
				<dd>
				  <div class="request code"><xsl:value-of select="con:request/text()"/></div>       
				</dd>
			</dl>
		</xsl:if>
			<xsl:apply-templates> </xsl:apply-templates>
	</div>
</xsl:template>
      
      
      
<xsl:template match="text()">
</xsl:template>
</xsl:stylesheet>
