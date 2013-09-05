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
public class STST {
	public static final String stst = "http://summerschool2013.eswc-conferences.org/featr/";
	private static Property property(String name) {
		Property result = ResourceFactory.createProperty(stst + name);
		return result;
	}
	
	public static String getURI(){
		return stst;
		}
	
	public Resource getSparqTermClass(){
		return ResourceFactory.createResource(stst + "SparqTerm");
		
	}

	public static final Property sparqlVersion 	= property("SparqTerm");
	public static final Property example 			= property("example");
	public static final Property executable 		= property("executable");

}
