package es.uc3m.nadir.sr.ftp;

import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SRFTP {
	private FTPClient client;
	private final String FTP_SERVER = "ftpdatos.aemet.es";
	private final String FTP_USER = "anonymous";
	private final String FTP_PASS = "anonymous";
	private final String SR_DIR = "radiacion_solar";
	private final String FILE_END = "_RadSolar.csv.gz";
	private final String OUTDIR = "C:\\";
	private FileOutputStream fos;
	private String day;
	private String month;
	private String year;

	public SRFTP(String d, String m, String y) {
		this.client = new FTPClient();
		this.day = d;
		this.month = m;
		this.year = y;
	}

	public String connectAndDownload() throws Exception {
		System.out.println("Connecting to FTP Server... [" + FTP_SERVER + "]");
		client.connect(FTP_SERVER);
		boolean login = client.login(FTP_USER, FTP_PASS);
		if (login) {
			String ftd = "" + year + month + day + FILE_END;
			System.out.println("Logged!");
			String outFile = OUTDIR + day + month + year + "_RadSolar.gz";
			fos = new FileOutputStream(outFile);
			client.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("Downloading file: " + ftd);
			client.retrieveFile("/" + SR_DIR + "/" + ftd, fos);
			System.out.println("Saving file as: " + outFile);
			System.out.println("Finished!");
			return outFile;
		}
		return null;
	}
}
