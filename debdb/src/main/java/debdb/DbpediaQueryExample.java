package debdb;
import org.apache.jena.query.*;

public class DbpediaQueryExample {
	
	

	 
	    public static void main(String[] args) {
	        // Define the SPARQL query
	        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "SELECT ?label WHERE { "
	                           + "  <http://dbpedia.org/resource/Java_(programming_language)> rdfs:label ?label ."
	                           + "  FILTER (lang(?label) = 'en')"
	                           + "}";

	        // Define the DBpedia SPARQL endpoint
	        String service = "http://dbpedia.org/sparql";

	        // Create a query execution object
	        Query query = QueryFactory.create(sparqlQuery);
	        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query)) {
	            // Execute the query and obtain results
	            ResultSet results = qexec.execSelect();

	            // Output query results
	            while (results.hasNext()) {
	                QuerySolution soln = results.nextSolution();
	                System.out.println(soln.get("label"));
	            }
	        }
	    }
	 


}
