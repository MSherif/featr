/**
 * 
 */
package endpint;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

/**
 * @author sherif
 *
 */
public class ConfigReader {
	static Model inputModel;
	
	/**
	 * @return the inputModel
	 */
	public static Model getInputModel() {
		return inputModel;
	}
	
	ConfigReader(String fileNameOrUri){
		inputModel=ModelFactory.createDefaultModel();
		java.io.InputStream in = FileManager.get().open( fileNameOrUri );
		if (in == null) {
			throw new IllegalArgumentException(
					"File: " + fileNameOrUri + " not found");
		}
		if(fileNameOrUri.contains(".ttl")){
			System.out.println("Opening Turtle file ...");
			inputModel.read(in, null, "TTL");
		}else if(fileNameOrUri.contains(".rdf")){
			System.out.println("Opening RDF/XML file ...");
			inputModel.read(in, null);
		}else if(fileNameOrUri.contains(".nt")){
			System.out.println("Opening N-Triples file ...");
			inputModel.read(in, null, "N-TRIPLE");
		}else{
			System.out.println("Content negotiation to get RDF/XML from " + fileNameOrUri + " ...");
			inputModel.read(fileNameOrUri);
		}
		System.out.println("Loading "+ fileNameOrUri + " is done!!");
	}
}
