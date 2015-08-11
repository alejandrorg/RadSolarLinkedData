package es.uc3m.nadir.sr.tripletizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SemanticRepoConfig {

	private final String REPOSITORY_CONFIG_FILE = "config/repository.properties";

	private final String REPOSITORY_URL = "REPOSITORY_URL";
	private final String REPOSITORY_NAME = "REPOSITORY_NAME";
	private final String DATASET_BASE_URI = "DATASET_BASE_URI";
	
	private Properties prop;
	
	public SemanticRepoConfig () throws FileNotFoundException, IOException {
		
		this.prop = new Properties();
		this.prop.load(new BufferedReader(new FileReader(REPOSITORY_CONFIG_FILE)));
		
	}

	public String getRepositoryURL() {
		return this.prop.getProperty(REPOSITORY_URL);
	}

	public String getRepositoryName() {
		return this.prop.getProperty(REPOSITORY_NAME);
	}

	public String getDatasetBaseURI() {
		return this.prop.getProperty(DATASET_BASE_URI);
	}

}
