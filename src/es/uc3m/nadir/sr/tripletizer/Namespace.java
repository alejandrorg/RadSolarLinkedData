package es.uc3m.nadir.sr.tripletizer;

public enum Namespace {
	SSN ("http://purl.oclc.org/NET/ssnx/ssn#"),
	MUO ("http://purl.oclc.org/NET/muo/muo#"),
	UCUM ("http://purl.oclc.org/NET/ssnx/ssn#"),
	TIME ("http://www.w3.org/2006/time#"),
	SR ("http://nadir.uc3m.es/solrad/vocabulary#"),
	SRDATA ("http://nadir.uc3m.es/solrad/dataset#"),
	DC ("http://purl.org/dc/elements/1.1/"),
	GEO ("http://www.w3.org/2003/01/geo/wgs84_pos#"),
	RDF ("http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
	RDFS ("http://www.w3.org/2000/01/rdf-schema#");
	
	
	private String name;
	
	private Namespace(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
