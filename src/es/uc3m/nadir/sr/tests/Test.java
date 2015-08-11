package es.uc3m.nadir.sr.tests;

import com.hp.hpl.jena.rdf.model.Model;

import es.uc3m.nadir.sr.tripletizer.MeasurementDuration;
import es.uc3m.nadir.sr.tripletizer.Measurement;
import es.uc3m.nadir.sr.tripletizer.MeasurementType;
import es.uc3m.nadir.sr.tripletizer.Station;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String time = "";
		String value = "124";
		String sensorID = "12345";
		MeasurementType type = MeasurementType.INFRARED;
				
		Station station = new Station (
				/* stationID = */ "12345", 
				/*lat= */ "123", 
				/* long= */ "123", 
				/* alt= */ "123", 
				/* location = */ "<http://dbpedia.org/page/Valencia,_Spain>");
		
		try {
			Measurement m = new Measurement(time, value, type, station, sensorID, MeasurementDuration.DURATION_1HOUR);
			
			Model model = m.getModel();
			
			model.write(System.out, "N3");
		
			//save to sesame
			//m.saveModel();
			
		} catch (Exception e) {
			System.out.println("Invalid data");
		}
		
	}

	
}
