package es.uc3m.nadir.sr.cvsprocessor;

import java.util.LinkedList;

import es.uc3m.nadir.sr.measureelements.DirectRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.FuzzyRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.GlobalRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.InfraredRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.UVBRadiationMeasure;

public class SRMeasure {

	private String location;
	private String climatologic_id;
	private LinkedList<GlobalRadiationMeasure> globalRadiation;
	private LinkedList<FuzzyRadiationMeasure> fuzzyRadiation;
	private LinkedList<DirectRadiationMeasure> directRadiation;
	private LinkedList<UVBRadiationMeasure> uvbRadiation;
	private LinkedList<InfraredRadiationMeasure> infraredRadiation;

	public SRMeasure() {
		this.globalRadiation = new LinkedList<GlobalRadiationMeasure>();
		this.fuzzyRadiation = new LinkedList<FuzzyRadiationMeasure>();
		this.directRadiation = new LinkedList<DirectRadiationMeasure>();
		this.uvbRadiation = new LinkedList<UVBRadiationMeasure>();
		this.infraredRadiation = new LinkedList<InfraredRadiationMeasure>();
	}

	public String getLocation() {
		return location;
	}

	public String getClimatologicID() {
		return this.climatologic_id;
	}

	public void setClimatologicID(String c) {
		this.climatologic_id = c;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LinkedList<GlobalRadiationMeasure> getGlobalRadiation() {
		return globalRadiation;
	}

	public LinkedList<FuzzyRadiationMeasure> getFuzzyRadiation() {
		return fuzzyRadiation;
	}

	public LinkedList<DirectRadiationMeasure> getDirectRadiation() {
		return directRadiation;
	}

	public LinkedList<UVBRadiationMeasure> getUvbRadiation() {
		return uvbRadiation;
	}

	public LinkedList<InfraredRadiationMeasure> getInfraredRadiation() {
		return infraredRadiation;
	}

	public void addGlobalRadiationValue(int v, int c) {
		this.globalRadiation.add(new GlobalRadiationMeasure(v,
				SRConstants.GLOBAL_RADIATION_HOUR_INTERVALS[c]));

	}

	public void addFuzzyRadiationValue(int v, int c) {
		this.fuzzyRadiation.add(new FuzzyRadiationMeasure(v,
				SRConstants.FUZZY_RADIATION_HOUR_INTERVALS[c]));
	}

	public void addDirectRadiationValue(int v, int c) {
		this.directRadiation.add(new DirectRadiationMeasure(v,
				SRConstants.DIRECT_RADIATION_HOUR_INTERVALS[c]));
	}

	public void addUVBRadiationValue(int v, int c) {
		this.uvbRadiation.add(new UVBRadiationMeasure(v,
				SRConstants.UVB_RADIATION_HOUR_INTERVALS[c]));
	}

	public void addIRRadiationValue(int v, int c) {
		this.infraredRadiation.add(new InfraredRadiationMeasure(v,
				SRConstants.INFRARED_RADIATION_HOUR_INTERVALS[c]));
	}

}
