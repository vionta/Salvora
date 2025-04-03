<p:declare-step xmlns:p="http://www.w3.org/ns/xproc" version="3.0">

  <p:input port="source"/>
  <p:output port="result"/>

  <p:insert name="prev" match="/*" position="last-child">
    <p:with-input port="insertion">
      <location>London {year-from-date(current-date())}</location>
    </p:with-input>
  </p:insert>
  
    <p:store name="savetest" >
    <p:with-option	name="href"
			select="concat('../../../../target/generated-test-sources/','test-input1', '.xml')" >	</p:with-option>
    <p:with-input port="source"
		  pipe="result@prev"
		  />
  </p:store>

</p:declare-step>