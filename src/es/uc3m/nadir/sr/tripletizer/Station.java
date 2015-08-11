package es.uc3m.nadir.sr.tripletizer;

public class Station {

	String stationID;
	String latitiude;
	String longitude;
	String altitiude;
	String location;


	public Station (String stationID, String latitude, String longitude, String altitude, String location) {
		this.stationID = stationID;
		this.latitiude = latitude;
		this.longitude = longitude;
		this.altitiude = altitude;
		this.location = location;
		
		
	}

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}
	
	public String getLatitiude() {
		return latitiude;
	}

	public void setLatitiude(String latitiude) {
		this.latitiude = latitiude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitiude() {
		return altitiude;
	}

	public void setAltitiude(String altitiude) {
		this.altitiude = altitiude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
