package es.uc3m.nadir.sr.tripletizer;


import com.hp.hpl.jena.datatypes.xsd.impl.XSDDateTimeType;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDDouble;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDFloat;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author Mateusz Radzimski 
 *
 */
public class Measurement {

	Model m;
	
	
	/**
	 * Measurement
	 * 
	 * @param time Time in XSD TimeDate format
	 * @param val Numerical value of measurement 
	 * @param type Type of measurement (MeasurementType enum)
	 * @param station 
	 * @throws Exception 
	 */
	public Measurement(String time, String val, MeasurementType type, Station station, String sensorID, MeasurementDuration duration) throws Exception {
	
		//check data sanity
		if (time == null || val == null || type == null || station == null || sensorID == null ||
				time.length() == 0 || val.length() == 0 || sensorID.length() == 0)
			throw new Exception(); 
			
		m = ModelFactory.createDefaultModel();
		
		//provide ID for measurement (temporarily it's simply time string)
		String measurementID = time.replace(" ", "");
		
		//get weather station geographical details
		
		//create properties
		//todo here go all properties
		Property rdftype = m.createProperty(Namespace.RDF.getName() + "type");
		
		
		//time: duration (1h)
		//TODO consider moving definition of duration time from SRDATA to SR vocabulary
		Resource duration1h = m.createResource(Namespace.SRDATA.getName() + "duration_1h");
		duration1h.addProperty(rdftype, Namespace.TIME.getName() + "DurationDescription");
		Property hasDurationDescription = m.createProperty(Namespace.TIME.getName() + "hasDurationDescription");
		Property minutes = m.createProperty(Namespace.TIME.getName() + "minutes");
		duration1h.addProperty(minutes, "60");

		//time: duration (0,5h)
		//TODO consider moving definition of duration time from SRDATA to SR vocabulary
		Resource duration30m = m.createResource(Namespace.SRDATA.getName() + "duration_30m");
		duration30m.addProperty(rdftype, Namespace.TIME.getName() + "DurationDescription");
		duration30m.addProperty(minutes, "30");

		//time: beginning
		Resource measurementBegin = m.createResource(Namespace.SRDATA.getName() + "measurement" + measurementID + "_begin_time");
		measurementBegin.addProperty(rdftype, Namespace.TIME.getName() + "Instant");
		Property inXSDDateTime = m.createProperty(Namespace.TIME.getName() + "inXSDDateTime");
		measurementBegin.addProperty(inXSDDateTime, time, XSDDateTimeType.XSDdateTime);
		Property hasBeginning = m.createProperty(Namespace.TIME.getName() + "hasBeginning");
		
		
		//weather station
		Resource weatherStation = m.createResource(Namespace.SRDATA.getName() + "weatherstation" + station.getStationID());
		weatherStation.addProperty(rdftype, Namespace.SR.getName() + "WeatherStation");
		//geographical details
		Property geoLat = m.createProperty(Namespace.GEO.getName() + "lat");
		Property geoLong = m.createProperty(Namespace.GEO.getName() + "long");
		Property geoAlt = m.createProperty(Namespace.GEO.getName() + "alt");
		Property geoLocation = m.createProperty(Namespace.GEO.getName() + "location");
		weatherStation.addProperty(geoLat, station.getLatitiude());
		weatherStation.addProperty(geoLong, station.getLongitude());
		weatherStation.addProperty(geoAlt, station.getAltitiude());
		weatherStation.addProperty(geoLocation, station.getLocation());
		
		
		//pyranometer
		Resource sensor = m.createResource(Namespace.SRDATA.getName() + sensorID);
		sensor.addProperty(rdftype, Namespace.SR.getName() + type.getPyranometer());
		//pyranomenter type
		Property observes = m.createProperty(Namespace.SSN.getName() + "observes");
		//TODO check type and add corresponding property value
		sensor.addProperty(observes, Namespace.SR.getName() + type.getProperty());
		//bind pyranometer to its weather station
		Property onPlatform = m.createProperty(Namespace.SSN.getName() + "onPlatform");
		sensor.addProperty(onPlatform, weatherStation);

		
		//observation
		Resource measurement = m.createResource(Namespace.SRDATA.getName() + "measurement" + measurementID);		
		//TODO measurement type
		measurement.addProperty(rdftype, Namespace.SR.getName() + type.getObservation());
		measurement.addProperty(rdftype, Namespace.TIME.getName() + "Interval");
		measurement.addProperty(hasBeginning, measurementBegin);
		switch (duration) {
			case DURATION_1HOUR:  
				measurement.addProperty(hasDurationDescription, duration1h);
				break;
			case DURATION_30MINUTES:
				measurement.addProperty(hasDurationDescription, duration30m);
				break;			
		}
		
		//bind measurement to the pyranometer
		Property observedBy = m.createProperty(Namespace.SSN.getName() + "observedBy");
		measurement.addProperty(observedBy, sensor);
		
		//output
		Resource output = m.createResource(Namespace.SRDATA.getName() + "output" + measurementID);
		Property obsResult = m.createProperty(Namespace.SSN.getName() + "observationResult");
		measurement.addProperty(obsResult, output);
		//TODO measurement type
		output.addProperty(rdftype, Namespace.SR.getName() + type.getOutput());
		
		//measurement value
		Resource value = m.createResource(Namespace.SRDATA.getName() + "value" + measurementID);
		Property hasValue = m.createProperty(Namespace.SSN.getName() + "hasValue");
		output.addProperty(hasValue, value);
		value.addProperty(rdftype, Namespace.SR.getName() + type.getValue());
		value.addProperty(rdftype, Namespace.MUO.getName() + "QualityValue");
		Property hasQuantityValue = m.createProperty(Namespace.SSN.getName() + "hasQuantityValue");
		Property numericalValue = m.createProperty(Namespace.MUO.getName() + "numericalValue");
		
		value.addProperty(hasQuantityValue, val, XSDDouble.XSDdouble);
		value.addProperty(numericalValue, val, XSDDouble.XSDdouble);
		
		
		//measures & units
		//todo - move unit definitions from "data" into "vocabulary" namespace
		
		Property measuredIn = m.createProperty(Namespace.MUO.getName() + "measuredIn");
		
		Property derivesFrom = m.createProperty(Namespace.MUO.getName() + "derivesFrom");
		Property dimensionalSize = m.createProperty(Namespace.MUO.getName() + "dimensionalSize");
		
		Resource meterSquared = m.createResource(Namespace.SR.getName() + "meter_squared");
		meterSquared.addProperty(rdftype, Namespace.MUO.getName() + "SimpleDerivedUnit");
		meterSquared.addProperty(derivesFrom, Namespace.UCUM.getName() + "unit/length/meter");
		meterSquared.addProperty(dimensionalSize, "2", XSDFloat.XSDfloat);
		
		
		Resource wattPerMeterSquared = m.createResource(Namespace.SR.getName() + "watt_per_meter_squared");
		wattPerMeterSquared.addProperty(rdftype, Namespace.MUO.getName() + "ComplexDerivedUnit");
		wattPerMeterSquared.addProperty(derivesFrom, Namespace.UCUM.getName() + "unit/power/Watt");
		wattPerMeterSquared.addProperty(derivesFrom, meterSquared);
		
		value.addProperty(measuredIn, wattPerMeterSquared);
		
		
		
		
		
	}
	
	public Model getModel() {
		
		return m;
	}
}
