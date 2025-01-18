package debdb;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTPBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class BackendService {

    @Async 
    public ListenableFuture<String> longRunningTask() { 
        String wikidata = "https://query.wikidata.org/bigdata/namespace/wdq/sparql";
		String dbpedia = "https://dbpedia.org/sparql/";
		
		String types = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>\n" 
				+ "PREFIX dbp: <http://dbpedia.org/property/>\n"
				+ "PREFIX dbr: <http://dbpedia.org/resource/>\n" 
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
				+ "SELECT DISTINCT ?t " 
				+ "where { ?t rdfs:subClassOf owl:Thing }";
		
		String q1 ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>\n" 
				+ "PREFIX dbp: <http://dbpedia.org/property/>\n"
				+ "PREFIX dbr: <http://dbpedia.org/resource/>\n" 
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
				+ "SELECT ?object (COUNT(*) as ?total) \n"
				+ "WHERE {   \n"
				+ "         ?object rdf:type dbo:Film .\n"
				+ "         ?object dbo:starring ?actor .\n"	
				+ "}"
				+ "GROUP BY ?object "
				+ "ORDER BY DESC(?total) "
				+ "LIMIT 20";
		
		String q2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>\n" 
				+ "PREFIX dbp: <http://dbpedia.org/property/>\n"
				+ "PREFIX dbr: <http://dbpedia.org/resource/>\n" 
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
				+ "SELECT ?object (COUNT(*) AS ?total)\n"
				+ "WHERE {   \n"
				+ "         ?object rdf:type dbo:Person .\n"
				+ "         ?object ?p1 ?v .\n"
				+ "         ?object ?p2 ?v .\n"
				+ "         FILTER(!STR(?p1)=STR(?p2) ) \n"
				+ "         FILTER( ! IsLiteral(?v) )\n"
				+ "          \n"
				+ "FILTER(CONTAINS(STR(?p1),\"property\"))\n"
				+ "FILTER(CONTAINS(STR(?p2),\"ontology\"))\n"
				+ "FILTER(!CONTAINS(STR(?p1),\"wiki\"))\n"
				+ "FILTER(!CONTAINS(STR(?p2),\"wiki\"))\n"
				+ "FILTER(!CONTAINS(STR(?p1),\"Wiki\"))\n"
				+ "FILTER(!CONTAINS(STR(?p2),\"Wiki\"))\n"
				+ "}\n"
				+ "GROUP BY ?object\n"
				+ "ORDER BY DESC(?total)"
				+ "LIMIT 20";
		
		ResultSet r = SPARQLRemote(q1,dbpedia);
		 
		
		String[] vxaxis = new String[20];
		Number[] vyaxis = new Number[20];
		int i=0;
		while (r.hasNext()) {
			QuerySolution sol = r.next();
			vxaxis[i]=sol.get("?object").toString(); //.split("resource/")[1];
			vyaxis[i]=sol.get("?total").asLiteral().getInt();
			System.out.println(sol.get("?total").asLiteral().getInt());
			i++;
			
		}
        return AsyncResult.forValue("Some result"); 
    }

    
    public ResultSet SPARQLRemote(String queryStr,String service) {

		Query query = QueryFactory.create(queryStr);
		QueryExecutionHTTPBuilder qexec = QueryExecutionFactory.createServiceRequest(service,
				query);
		qexec.urlGetLimit(1000000000);

		if (query.isSelectType()) {
			ResultSet result = qexec.select();
			return result;
		}
		return null;

	}
}
