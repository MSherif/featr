/**
 * 
 */
package endpint;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * @author sherif
 *
 */
public class VoID {
	public static final String uri = "http://vocab.deri.ie/void#";
	private static Property property(String name) {
		Property result = ResourceFactory.createProperty(uri + name);
		return result;
	}
	
	public static String getURI(){
		return uri;
		}
	
	public static Resource getTechnicalFeatureClass(){
		return ResourceFactory.createResource(uri + "TechnicalFeature");
	}
	
	public static Resource getDatasetClass(){
		return ResourceFactory.createResource(uri + "Dataset");
		
	}
	
	public static final Property feature 	= property("feature");
//	public static final Property example 			= property("example");
//	public static final Property executable 		= property("executable");

}
