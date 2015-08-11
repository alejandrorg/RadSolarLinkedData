package es.uc3m.nadir.sr.others;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class WeatherStationConfig {

	private final String WEATHER_STATIONS_FILE = "config/stations.properties";
	private final String LONGITUDE = "_LONG";
	private final String LATITUDE = "_LAT";
	private final String ALTITUDE = "_ALT";
	private final String DBPEDIA = "_DBLINK";

	private Properties prop;
	private String climID;

	public WeatherStationConfig(String cid) throws Exception {
		this.climID = cid.trim();
		this.prop = new Properties();
		this.prop
				.load(new BufferedReader(new FileReader(WEATHER_STATIONS_FILE)));
	}

	public String getLongitude() {
		return this.prop.getProperty(this.climID + LONGITUDE);
	}

	public String getLatitude() {
		return this.prop.getProperty(this.climID + LATITUDE);
	}

	public String getDBpediaLink() {
		return this.prop.getProperty(this.climID + DBPEDIA);
	}

	public String getAltitude() {
		return this.prop.getProperty(this.climID + ALTITUDE);
	}

}
