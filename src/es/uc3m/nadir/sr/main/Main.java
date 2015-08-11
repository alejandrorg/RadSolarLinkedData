package es.uc3m.nadir.sr.main;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import com.ibm.icu.util.Calendar;

import es.uc3m.nadir.sr.cvsprocessor.SRCSVProcessor;
import es.uc3m.nadir.sr.cvsprocessor.SRMeasure;
import es.uc3m.nadir.sr.ftp.SRFTP;
import es.uc3m.nadir.sr.ftp.Unzip;
import es.uc3m.nadir.sr.measureelements.DirectRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.FuzzyRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.GlobalRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.InfraredRadiationMeasure;
import es.uc3m.nadir.sr.measureelements.UVBRadiationMeasure;
import es.uc3m.nadir.sr.others.WeatherStationConfig;
import es.uc3m.nadir.sr.tripletizer.Measurement;
import es.uc3m.nadir.sr.tripletizer.MeasurementDuration;
import es.uc3m.nadir.sr.tripletizer.MeasurementType;
import es.uc3m.nadir.sr.tripletizer.RepositoryWriter;
import es.uc3m.nadir.sr.tripletizer.SemanticRepoConfig;
import es.uc3m.nadir.sr.tripletizer.Station;

public class Main {

	private final String TIMEZONE = "+01:00";
	private final String ZERO = "0";
	private static Logger logger = Logger.getLogger("Logger");
	private FileHandler fh;
	
	public Main(String args[]) throws Exception {
		prepareLogger();
		if (args.length == 0) {
			executeNormal();
		}
		if (args.length == 2) {
			executeWithFile(args[0], args[1]);
		} else {
			logger.log(Level.SEVERE, "Error in execution. Wrong number of parameters");
			System.err
					.println("Error. Ejecutar sin parámetro o con dos parámetros:\n");
			System.err
					.println("RadSolar <fichero> <fecha en formato DD:MM:AAA>");
		}
	}

	private void prepareLogger() throws Exception {
		  Calendar cal = Calendar.getInstance();
	      fh = new FileHandler("log\\" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.YEAR) + ".log", true);
	      logger.addHandler(fh);
	      logger.setLevel(Level.ALL);
	      SimpleFormatter formatter = new SimpleFormatter();
	      fh.setFormatter(formatter);  
	      logger.log(Level.FINE,"Logging. Date: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));
	}

	private void executeWithFile(String fi, String fe) {
		try {
			String[] fec = fe.split(":");
			if (fec.length == 3) {
				String day = fec[0];
				String month = fec[1];
				String year = fec[2];
				if (fi != null) {
					SRCSVProcessor sp = new SRCSVProcessor(fi);
					sp.execute();
					for (int i = 0; i < sp.getMeasures().size(); i++) {
						processMeasure(sp.getMeasures().get(i), day, month,
								year, TIMEZONE);
					}
				} else {
					logger.log(Level.SEVERE, "File not exists in processing a local CSV file: " + fi);
				}
			} else {
				throw new Exception("Date is in incorrect format!");
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception found in executeWithFile: " + e.getMessage());
		}
	}

	private void executeNormal() {
		try {
			Calendar cal = Calendar.getInstance();
			String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			String month = Integer.toString(cal.get(Calendar.MONTH));
			String year = Integer.toString(cal.get(Calendar.YEAR));
			if (day.length() == 1) {
				day = ZERO + day;
			}
			if (month.length() == 1) {
				month = ZERO + month;
			}
			SRFTP sr = new SRFTP(day, month, year);
			String f = sr.connectAndDownload();
			if (f != null) {
				Unzip uz = new Unzip();
				String rf = uz.unzip(f);
				SRCSVProcessor sp = new SRCSVProcessor(rf);
				sp.execute();
				for (int i = 0; i < sp.getMeasures().size(); i++) {
					processMeasure(sp.getMeasures().get(i), day, month, year,
							TIMEZONE);
				}
			} else {
				logger.log(Level.SEVERE, "Error downloading file: " + f);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception found in executeNormal: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			new Main(args);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception found in main: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void processMeasure(SRMeasure srM, String day, String month,
			String year, String timezone) throws Exception {
		System.out.println("Date: " + day + "/" + month + "/" + year);
		System.out.println("Weather Station: " + srM.getClimatologicID());
		System.out.println("Location: " + srM.getLocation());
		SemanticRepoConfig rc = new SemanticRepoConfig();
		RepositoryWriter rw = new RepositoryWriter(rc);
		WeatherStationConfig wsc = new WeatherStationConfig(
				srM.getClimatologicID());
		Station s = new Station(srM.getClimatologicID(), wsc.getLatitude(),
				wsc.getLongitude(), wsc.getAltitude(), wsc.getDBpediaLink());
		Measurement m;
		System.out.print("Creating and uploading Direct Radiation triples...");
		for (int i = 0; i < srM.getDirectRadiation().size(); i++) {
			DirectRadiationMeasure drm = srM.getDirectRadiation().get(i);
			String time = year + "-" + month + "-" + day + "T"
					+ drm.getIntervalStart() + ":00:00" + timezone;
			m = new Measurement(time, Integer.toString(drm
					.getValue()), MeasurementType.DIRECT, s, s.getStationID()
					+ "DR", MeasurementDuration.DURATION_1HOUR);
			rw.writeJenaModel(m.getModel());
		}
		System.out.println("Done!");
		System.out.print("Creating and uploading Fuzzy Radiation triples...");
		for (int i = 0; i < srM.getFuzzyRadiation().size(); i++) {
			FuzzyRadiationMeasure frm = srM.getFuzzyRadiation().get(i);
			String time = year + "-" + month + "-" + day + "T"
					+ frm.getIntervalStart() + ":00:00" + timezone;
			m = new Measurement(time, Integer.toString(frm
					.getValue()), MeasurementType.DIFFUSE, s, s.getStationID()
					+ "FR", MeasurementDuration.DURATION_1HOUR);
			rw.writeJenaModel(m.getModel());
		}
		System.out.println("Done!");
		System.out.print("Creating and uploading Global Radiation triples...");
		for (int i = 0; i < srM.getGlobalRadiation().size(); i++) {
			GlobalRadiationMeasure grm = srM.getGlobalRadiation().get(i);
			String time = year + "-" + month + "-" + day + "T"
					+ grm.getIntervalStart() + ":00:00" + timezone;
			m = new Measurement(time, Integer.toString(grm
					.getValue()), MeasurementType.GLOBAL, s, s.getStationID()
					+ "GR", MeasurementDuration.DURATION_1HOUR);
			rw.writeJenaModel(m.getModel());
		}
		System.out.println("Done!");
		System.out.print("Creating and uploading Infrared Radiation triples...");
		for (int i = 0; i < srM.getInfraredRadiation().size(); i++) {
			InfraredRadiationMeasure irm = srM.getInfraredRadiation().get(i);
			String time = year + "-" + month + "-" + day + "T"
					+ irm.getIntervalStart() + ":00:00" + timezone;
			m = new Measurement(time, Integer.toString(irm
					.getValue()), MeasurementType.INFRARED, s, s.getStationID()
					+ "IR", MeasurementDuration.DURATION_1HOUR);
			rw.writeJenaModel(m.getModel());
		}
		System.out.println("Done!");
		System.out.print("Creating and uploading UVB Radiation triples...");
		for (int i = 0; i < srM.getUvbRadiation().size(); i++) {
			UVBRadiationMeasure urm = srM.getUvbRadiation().get(i);
			String time = year + "-" + month + "-" + day + "T"
					+ urm.getIntervalStart() + ":00" + timezone;
			m = new Measurement(time, Integer.toString(urm
					.getValue()), MeasurementType.UVB, s, s.getStationID()
					+ "UR", MeasurementDuration.DURATION_30MINUTES);
			rw.writeJenaModel(m.getModel());
		}
		System.out.println("Done!");
	}

}
