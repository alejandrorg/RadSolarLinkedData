package es.uc3m.nadir.sr.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SesameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		Model m = ModelFactory.createDefaultModel();
		
		String sesameServer = "http://localhost:8080/openrdf-sesame/";
		String repositoryID = "sensors-v2";
		
		String triples = "<http://nadir.uc3m.es/solrad/dataset#measurement>\r\n" + 
				"      a       \"http://nadir.uc3m.es/solrad/vocabulary#InfraredSolarRadiationObservation\" , \"http://www.w3.org/2006/time#Interval\" ;\r\n" + 
				"      <http://purl.oclc.org/NET/ssnx/ssn#observationResult>\r\n" + 
				"              <http://nadir.uc3m.es/solrad/dataset#output> ;\r\n" + 
				"      <http://purl.oclc.org/NET/ssnx/ssn#observedBy>\r\n" + 
				"              <http://nadir.uc3m.es/solrad/dataset#12345> ;\r\n" + 
				"      <http://www.w3.org/2006/time#hasBeginning>\r\n" + 
				"              <http://nadir.uc3m.es/solrad/dataset#measurement_begin_time> ;\r\n" + 
				"      <http://www.w3.org/2006/time#hasDurationDescription>\r\n" + 
				"              <http://nadir.uc3m.es/solrad/dataset#duration_1h> .\r\n" + 
				"";
		
		HTTPRepository r = new HTTPRepository(sesameServer, repositoryID);

		
		try {
			r.initialize();
			RepositoryConnection con = r.getConnection();
			InputStream is = new ByteArrayInputStream(triples.getBytes("UTF-8"));
			
			try {
				con.add(is, "", RDFFormat.N3);
			}
			finally {
				con.close();
			}

			
		} 
		catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
