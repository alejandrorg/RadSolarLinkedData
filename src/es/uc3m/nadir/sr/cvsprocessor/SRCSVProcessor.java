package es.uc3m.nadir.sr.cvsprocessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class SRCSVProcessor {

	private BufferedReader bufferReader;
	private int lineCounter = 0;
	private String date;
	private LinkedList<SRMeasure> measures;
	private int currentRadiation = -1;
	private int currentPositionValueToKnowTimeZone;
	private String file;

	public SRCSVProcessor(String f) {
		this.file = f;
	}

	public LinkedList<SRMeasure> getMeasures() {
		return this.measures;
	}

	public void execute() throws Exception {
		this.measures = new LinkedList<SRMeasure>();
		this.bufferReader = new BufferedReader(new FileReader(file));
		while (this.bufferReader.ready()) {
			String rl = this.bufferReader.readLine();
			this.lineCounter++;
			process(rl);
		}
		this.bufferReader.close();
	}

	/**
	 * Método para procesar una línea de datos del fichero CSV.
	 * 
	 * @param rl
	 *            Recibe la línea.
	 * @throws Exception
	 *             Puede lanzar excepción.
	 */
	private void process(String rl) throws Exception {
		System.out.println("Processing: " + rl);
		String parts[] = rl.split(";");
		if (this.lineCounter > 3) {
			System.out.println("Date line:");
			SRMeasure srm = new SRMeasure();
			srm.setLocation(checkNull(parts[0]));
			System.out.println("\tLocation: " + srm.getLocation());
			srm.setClimatologicID(checkNull(parts[1]));
			System.out.println("\tClimatological ID: "
					+ srm.getClimatologicID());
			for (int i = 2; i < parts.length; i++) {
				if (isARadiationIdentifier(parts[i])) {
					System.out.println("\tRadiation ID found: " + parts[i]);
					this.currentRadiation = chooseRadiationIdentifier(parts[i]);
					this.currentPositionValueToKnowTimeZone = 0;
					/*
					 * Establecemos el tipo de radiación con el que estamos
					 * trabajando.
					 */

				} else {
					/* Si no, procesamos el valor. */
					processRadiationValue(parts[i], srm);
					this.currentPositionValueToKnowTimeZone++;
				}
			}
			this.measures.add(srm);
		} else {
			if (this.lineCounter == 2) {
				/* En la segunda está la fecha. */
				this.date = parts[0];
				System.out.println("Date found: " + date);
			}
		}
	}

	private void processRadiationValue(String rv, SRMeasure srm)
			throws Exception {
		switch (this.currentRadiation) {
		case 0: /* GL */
			srm.addGlobalRadiationValue(getIntValue(rv),
					this.currentPositionValueToKnowTimeZone);
			break;
		case 1: /* DF */
			srm.addFuzzyRadiationValue(getIntValue(rv),
					this.currentPositionValueToKnowTimeZone);
			break;
		case 2: /* DT */
			srm.addDirectRadiationValue(getIntValue(rv),
					this.currentPositionValueToKnowTimeZone);
			break;
		case 3: /* UVB */
			srm.addUVBRadiationValue(getIntValue(rv),
					this.currentPositionValueToKnowTimeZone);
			break;
		case 4: /* IR */
			srm.addIRRadiationValue(getIntValue(rv),
					this.currentPositionValueToKnowTimeZone);
			break;
		}

	}

	public String toString() {
		String ret = "";
		ret += "Date: " + date + "\n";
		for (int i = 0; i < measures.size(); i++) {
			SRMeasure srm = measures.get(i);
			ret += "\t[!] Location: " + srm.getLocation() + "\n";
			ret += "\t[!] Clim ID: " + srm.getClimatologicID() + "\n";
			ret += "\t[!] Global radiation: "
					+ srm.getGlobalRadiation().toString() + "\n";
			ret += "\t[!] Fuzzy radiation: "
					+ srm.getFuzzyRadiation().toString() + "\n";
			ret += "\t[!] Direct radiation: "
					+ srm.getDirectRadiation().toString() + "\n";
			ret += "\t[!] UVB radiation: " + srm.getUvbRadiation().toString()
					+ "\n";
			ret += "\t[!] Infrared radiation: "
					+ srm.getInfraredRadiation().toString() + "\n";
			ret += "\n\n";
		}
		return ret;
	}

	/**
	 * Método para obtener el valor entero.
	 * 
	 * @param rv
	 *            Recibe la cadena.
	 * @return Devuelve el int.
	 */
	private int getIntValue(String rv) throws Exception {
		if (isEmptyOrNull(rv.trim()))
			return -1;
		return Integer.parseInt(rv.trim());
	}

	/**
	 * Método para saber si una cadena es nula o vacía.
	 * 
	 * @param rv
	 *            Recibe la cadena.
	 * @return Devuelve un booleano.
	 */
	private boolean isEmptyOrNull(String rv) {
		return ((rv == null) || (rv.equalsIgnoreCase("")));
	}

	private int chooseRadiationIdentifier(String r) throws Exception {
		for (int i = 0; i < SRConstants.RADIATIONS_TYPES.length; i++) {
			if (SRConstants.RADIATIONS_TYPES[i].equalsIgnoreCase(r)) {
				return i;
			}
		}
		throw new Exception("Radiation type not found!");
	}

	/**
	 * Método para comprobar si una cadena coincide con un identificador de tipo
	 * de radiación.
	 * 
	 * @param r
	 *            Recibe la cadena.
	 * @return Devuelve un booleano
	 */
	private boolean isARadiationIdentifier(String r) {
		for (int i = 0; i < SRConstants.RADIATIONS_TYPES.length; i++) {
			if (SRConstants.RADIATIONS_TYPES[i].equalsIgnoreCase(r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método para comprobar si es nulo. Si lo es, lanza excepción.
	 * 
	 * @param string
	 *            Recibe la cadena.
	 * @return Devuelve la misma cadena si no es nula.
	 */
	private String checkNull(String s) throws Exception {
		if (s == null) {
			throw new Exception("Null string found");
		}
		return s;
	}
}
