package es.uc3m.nadir.sr.tripletizer;

public enum MeasurementType {

	//All measurements types and corresponding classes
	//
	// TYPE: (Pyranometer, Observation, Output, MeasurementValue, ObservedProperty)
	
	
	INFRARED ("IRRadiationPyranometer", 
			"InfraredSolarRadiationObservation", 
			"InfraredSolarRadiationOutput",
			"InfraredSolarRadiationMeasurementValue",
			"InfraredSolarRadiation"
			),
	UVB 	("UVBRadiationPyranometer", 
			"UVBSolarRadiationObservation", 
			"UVBSolarRadiationOutput",
			"UVBSolarRadiationMeasurementValue",
			"UVBSolarRadiation"
			),
	GLOBAL ("GlobalIrradiancePyranometer", 
			"GlobalSolarRadiationObservation",
			"GlobalSolarRadiationOutput",
			"GlobalSolarRadiationMeasurementValue",
			"GlobalSolarRadiation"
			),
	DIRECT ("DirectIrradiancePyranometer",
			"DirectSolarRadiationObservation",
			"DirectSolarRadiationOutput",
			"DirectSolarRadiationMeasurementValue",
			"DirectSolarRadiation"
			),
	DIFFUSE ("DiffuseIrradiancePyranometer", 
			"DiffuseSolarRadiationObservation",
			"DiffuseSolarRadiationOutput",
			"DiffuseSolarRadiationMeasurementValue",
			"DiffuseSolarRadiation"
			);
	
	private String pyranometer, observation, output, value, property;
	
	MeasurementType(String pyranometer, String observation, String output, String value, String property) {
		this.setPyranometer(pyranometer);
		this.setObservation(observation);
		this.setOutput(output);
		this.setValue(value);
		this.setProperty(property);
		
	}

	public String getPyranometer() {
		return pyranometer;
	}

	public void setPyranometer(String pyranometer) {
		this.pyranometer = pyranometer;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	
}
