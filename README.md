# Introduction {#introduction}

Salvora is a simple utility to serve XSLTForms \(see http://www.agencexml.com/xsltforms.htm for more details about XSLTForms\) applications locally, or manage small xforms applications without embedding them in a full application server.

Current browsers tend to limit some of the functionalities of XSLT and XForms when run directly from local file systems. It can also be helpful if you need to capture or expose a small set of data in a protected environment like a VPN.

Salvora utility server can expose both read only or read/write folders as a REST service. It is also able to print the folder file lists or even configure transformatios or small transformation chains.

# Use Cases {#use-cases}

Salvora is a simple and easy way to run XForms directly from local folders.

-   Salvora can be used to build small applications to capture or share xforms based data like team work forms, ddocument collections data from some department or group, polls, etc.
-   Salvora can also be used for testing and developing Forms and browser XSLTs without deployments.
-   Run simple XForms in controed environments with simple configurations.

# Quick Start {#quick-start}

1.  Download and paste the "fat" Jar file in the desired folder.
2.  Add three subfolders \(data, forms and xsltforms\) with your data, xforms and the xsltforms distributions
3.  Run the command : java -jar salvora-fat-<your-versio\>.jar
4.  Your application should be running and your form should be available on localhost:8080/forms/<your-form-name\>.xml

You can also dowload the sample application an run it with the mentioned command.

# Command line usage {#command-line-usage}

Salvora can be configured at start-time using several command line options.

The usual data, forms and xsltforms folders can be configured with the -d -f and -x options. The output port can be configured with -p \(example: -p:8081\). By default Salvora runs on the 8080 port. Also you can prevent connections from other computers with the -e:false flag.

Download and run the executable or Jar file in the desired folder. The following examples show you several configuration combinations:

-   java -jar salvora-fat-<version\>.jar \(Params\)\*
-   java -jar salvora-fat-%lt;version\>.jar -f:forms -x:xforms -p:8081
-   java -jar salvora-fat-%lt;version\>.jar -p:8085 -e:false

Using the command line can be configured Salvora manages three folders with different functions:

-   xsltforms \[Read only Configurable\] : Predefined xsltform folder, intended for storing and exposing the xsltforms distributions from www.agencexml.com/xsltforms.
-   form \[Read only Configurable\] : Predefined folder for your form.
-   data \[Read + Write Configurable\] : Predefined writeable data folder.

Once the server is started you can open a browser on http://localhos:8080/*your\_path* and start browsing.

Example paths:

-   http://localhos:8080/xsltforms/xsltforms.xslt
-   http://localhos:8080/form/myform.xml
-   http://194.12.45.68:8080/data/mydata.xml

# Mapping file configuration {#configuration-file}

Salvora provides a more powerful way to define http call mappings and transformations. With a mapping file you can define:

-   File folders, with or without write access and with or without printing the folder file list.
-   Transformations and small transformation chains over local files and file collections.
-   Transformations and small transformation chains over local http files and file collections.
-   Transformations and small transformation chains over remote http files and file collections \(SOON, not yet available\).

At this moment the file mapping is being developed and documented. Please come back soon for improvements.

The mapping file structure is described with a Xsd Schema provided in the documentation of every Salvora version.

# Configuration {#configuration}

The following parameters can be passed to the XFServer :

**Parameters.**

-   -h: Show this help message
-   -f:<path\> Form folder path. Default form. This param is not considered if a mapping file \(-m\) is provided.
-   -x:<path\> XSltForms folder path. Default xsltforms. This param is not considered if a mapping file \(-m\) is provided.
-   -f:<path\> Data folder path. Default data. This param is not considered if a mapping file \(-m\) is provided.
-   -p:<number\> Port number \(integer\). Default 8080
-   -e:<boolean\> true/false Enabled or disabled external access. Default true
-   -m:<file path/name\> The name of the mapping file. If the mapping file is provided, the command line defined folders are not taken in consideration. The port and external access ports are considered even if a mapping file is provided.

**Examples.**

-   java -jar salvora.jar
-   java -cp salvora.jar net.vionta.xfserver.Salvora -f:forms -x:xforms -p:8081
-   java -jar salvora.jar -e:false

# Mapping Sample {#mapping-sample}

The following element shows a listing sample of a request/mapping file, with folders configuration, transformations, etc.

The elements that maps requests have path \(external exposed http paths\) and urls \(internal consumed urls of the resources\). We use two variants, BASE paths that expose an initial key of a set of files or resources and single paths that map to a single urls or resource. When you use the "base-" paths it is meant for sets of paths and "path"/"rul" it is meant for single resources. For every base-path or single path there should be a corresponding base-url or url.

```

<?xml version="1.0" encoding="UTF-8"?>
<sal:salvora-application
    xmlns:sal="net:vionta:schemas:salvora:mapping:v1.0"
    >
  <file-mapping
      name="configuration" base-path="configuration"
      write-allowed="false" file-list="false" >
    <description>Configuration path</description>
  </file-mapping>
  <file-mapping
      name="form" base-path="form"
      write-allowed="false"
      >
  </file-mapping>
  <file-mapping
      name="xsltforms" base-path="xsltforms"
      write-allowed="false" />
  <file-mapping
      name="data" base-path="data"
      file-list="true"
      write-allowed="true" />
    <file-mapping
      name="config" base-path="config"
      file-list="false"
      write-allowed="true" />

    <transformation name="primera"
		    url="transformations/archivos-sample.xml"
		    path="issues"
		    type="local_file"
		    >

	<step name="primera" 
	    source="transformations/lista-archivos.xsl" />

    </transformation>


</sal:salvora-application>

      
```

# Troubleshooting {#reqs-faqs-troubles}

## Requirements. { .section}

Salvora requires at least a Java 12 runtime.

## Frequent Questions and answers. { .section}

**Can the server be accessed from external computers ?**

Yes, this is a small utility intended for occasional use. Write access is prevented for any other folder outside the data folder. The continuous usage in production machines is discouraged.

**Can I open two instances on the same machine ?**

Yes, you can configure different ports on different routes at the same time in the same machine.

## Troubleshooting. { .section}

**Address already bind.**

This message indicates that the port number is in use. Choose a different port number or shutdown the other process and restart.

**Wrong Java Version. "Class file version XX".**

Salvora requires at least a Jdk 12 or newer. The following message indicates that a previous version of the Jdk is being used or configured. Review the Jdk configuration and try again.

**Stack trace message:**

java.lang.UnsupportedClassVersionError: net/vionta/xfserver/Salvora has been compiled by a more recent version of the Java Runtime \(class file version 55.0\), this version of the Java Runtime only recognizes class file versions up to 52.0

**JAXB schema binding limitations:**

The current version of the JAXB implementation has issues with the configuration file format and the way the schemas are referenced.

This schema reference will FAIL

```

	<?xml version="1.0" encoding="UTF-8"?>
	<salvora-application
	  xmlns="net:vionta:schemas:salvora:mapping:v1.0"
	  >
	  <file-mapping
	    name="configuration" base-path="configuration"
	    write-allowed="false" file-list="false" >
	    <description>Configuration path</description>
	  </file-mapping>
	  ...
	</salvora-application>
      
```

This schema reference WORKS

```

	<?xml version="1.0" encoding="UTF-8"?>
	<sal:salvora-application
	  xmlns:sal="net:vionta:schemas:salvora:mapping:v1.0"
	  >
	  <file-mapping
	    name="configuration" base-path="configuration"
	    write-allowed="false" file-list="false" >
	    <description>Configuration path</description>
	  </file-mapping>
	  ...
	</salvora-application>
      
```

We recommend you to use the supplied config xml files as templates and repport every problem you find and it does not figure on this documentation.

# Schema Description {#schema-desc}

1.  **salvora-application**

    Salvora Application Mapping Description. It takes file mappings \(http requests over system files\) and transformations \(initially xslt chains\).

    -   **file-mapping**

        -   A file based mapping to describe files and file based http requests. It also takes the option to serve folder file list files.

            -   **description-info**

            -   **file-mapping-atts**

                -   @ name

                -   @ base-url \[required\]

                    Request base path.

                -   @ base-path \[required\]

                    Folder base path for file access.

                -   @ file-list \[optional\]

                    Describes if a file list should be returned when requesting the folder path. Defaults to false.

                -   @ write-allowed \[optional\]

                    Describes if POST and PUT operations \(write opperations\) are allowed.

                -   @ delete-allowed \[optional\]

                    Describes if DELETE operations \(write opperations\) are allowed.

        -   **transformation**

            -   At least one of base-path or network-path should be defined, Base path defines a file access path, while the base url is used for network access calls. The content is afterwards transformed with the xslt.

                -   **transformation-attributes**

                    -   @ name \[required\]

                    -   @ path

                        Base file path, as a source of transformation content.

                    -   @ base-path

                        Base file path, as a source of transformation content.

                    -   @ base-url

                        Base network path \(local or remote\) that can be used to request the source of the transformation content.

                    -   @ url

                        Base network path \(local or remote\) that can be used to request the source of the transformation content.

                    -   @ type

                        network or local \(local by default\)

                        -   Values : \( local\_file \| local\_network \| remote\_network \| \)

                -   **transformation-elements**

                    -   **step**

                        -   **transformation-step**

                            -   **transformation-step-attributes**

                                -   @ name

                                -   @ source \[required\]

                                -   @ type \[required\]

                            -   **transformation-step-elements**

                                -   **path-parameter**

                                    -   **path-parameter**

                                        Parameter element, used to map request url path parts to xslt parameters.

                                        -   @ request-key

                                        -   @ xslt-param-name

                                -   **request-parameter**

                                    -   **request-parameter**

                                        -   @ request-key

                                        -   @ xslt-param-name


# Contact {#contact}

Salvora is provided by www.neonbluetide.com. Neon Blue Tide is part of www.vionta.net.

