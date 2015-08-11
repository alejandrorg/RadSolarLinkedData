package es.uc3m.nadir.sr.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class Unzip {

	public String unzip(String f) throws Exception {
		System.out.println("Unzipping file: " + f);
		FileInputStream in = new FileInputStream(f);
		GZIPInputStream zipin = new GZIPInputStream(in);
		byte[] buffer = new byte[8192];
		String outFile = f + ".csv";
		FileOutputStream out = new FileOutputStream(outFile);
		int length;
		while ((length = zipin.read(buffer, 0, 8192)) != -1)
			out.write(buffer, 0, length);
		out.close();
		zipin.close();
		System.out.println("Unzip finished!");
		return outFile;
	}
}
