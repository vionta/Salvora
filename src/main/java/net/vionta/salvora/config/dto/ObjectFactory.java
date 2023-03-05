package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.vionta.salvora.mapping
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SalvoraApplication }
     * 
     */
    public SalvoraApplication createSalvoraApplication() {
        return new SalvoraApplication();
    }

    /**
     * Create an instance of {@link FileMapping }
     * 
     */
    public FileMapping createFileMapping() {
        return new FileMapping();
    }

    /**
     * Create an instance of {@link Transformation }
     * 
     */
    public NetworkPathConfiguration createTransformation() {
        return new Transformation();
    }

    /**
     * Create an instance of {@link TransformationStep }
     * 
     */
    public ParameterSetElement createTransformationStep() {
        return new TransformationStep();
    }

    /**
     * Create an instance of {@link PathParameter }
     * 
     */
    public PathParameter createPathParameter() {
        return new PathParameter();
    }

    /**
     * Create an instance of {@link RequestParameter }
     * 
     */
    public RequestParameter createRequestParameter() {
        return new RequestParameter();
    }

}
