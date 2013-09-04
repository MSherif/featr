package endpint;



import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class Tester
{
	String endpoint;
	private Model configModel;
	private Model outputModel = ModelFactory.createDefaultModel();
	
	/**
	 * 
	 *@author sherif
	 */
	public Tester(String ep, String configFile) {
		endpoint = ep;
		configModel = new ConfigReader(configFile).getInputModel();
		outputModel.add(configModel);
		outputModel.setNsPrefixes(configModel);
	}

	/**
	 * @param endpoint to set
	 * @author sherif
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	/**
	 * @return the outputModel
	 */
	public Model getTestResultModel() {
		return testEndpoint();
	}

	
	/**
	 * @return resultModel containing the input configuration model + <?s STST.executable {"TRUE"|"FALSE"}>
	 * @author sherif
	 */
	private Model testEndpoint() {
		StmtIterator exampleItr = configModel.listStatements(null, STST.example, (RDFNode) null);
		while (exampleItr.hasNext()) {
			Statement st = exampleItr.nextStatement();
			String sparqlString = st.getObject().toString();
			RDFNode exampleSubject = st.getSubject();
			if(isExecutable(sparqlString)){
				outputModel.add((Resource) exampleSubject, STST.executable, "TRUE");
			}else{
				outputModel.add((Resource) exampleSubject, STST.executable, "FALSE");
			}
		}
		return outputModel;
	}

	
	/**
	 * @param exampleString: to chick wither it executable of not
	 * @return true if exampleString is executable, false otherwise
	 * @author sherif
	 */
	public boolean isExecutable(String exampleString) {
		QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, exampleString);
		
		if(exampleString.toLowerCase().contains("select")){
			ResultSet results= qe.execSelect();
			if(results.hasNext()){
				return true;
			}else{
				return false;
			}
		}
		if(exampleString.toLowerCase().contains("construct")){
			if(!qe.execConstruct().isEmpty()){
				return true;
			}else{
				return false;
			}
		}
		if(exampleString.toLowerCase().contains("ask")){
			if(qe.execAsk()){
				return true;
			}else{
				return false;
			}
		}
		if(exampleString.toLowerCase().contains("describe")){
			if(!qe.execDescribe().isEmpty()){
				return true;
			}else{
				return false;
			}
		}
		//TODO check for all other cases
		//default return
		return false;
	}

	/**
	 * @param args endpoint as String and fileName as String
	 * @author sherif
	 */
	public static void main( String[] args ){	
		Tester t = new Tester(args[0], args[1]);
		System.out.println("\n-------------- RESULT MODEL --------------");
		t.getTestResultModel().write(System.out,"TTL");

	}
}
