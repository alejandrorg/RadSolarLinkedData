package es.uc3m.nadir.sr.tripletizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;

import com.hp.hpl.jena.rdf.model.Model;

public class RepositoryWriter {

	String sesameServer;
	String repositoryName;
	RepositoryConnection con;
	
	public RepositoryWriter(SemanticRepoConfig rc) {
		this.sesameServer = rc.getRepositoryURL();
		this.repositoryName = rc.getRepositoryName();

		HTTPRepository r = new HTTPRepository(this.sesameServer, this.repositoryName);
		
		try {
			r.initialize();
			con = r.getConnection();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeN3(String triples) throws Exception {
		InputStream is = new ByteArrayInputStream(triples.getBytes("UTF-8"));
		
		con.add(is, "", RDFFormat.N3);
		
	}
	
	public void writeJenaModel(Model m) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.write(out, "N3");
		
		
		
		InputStream is = new ByteArrayInputStream(out.toByteArray());
		
		con.add(is, "", RDFFormat.N3);
	}
	
	public void close() throws RepositoryException {
		con.close();
	}
	
}
