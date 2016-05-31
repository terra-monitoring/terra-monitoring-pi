package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class DBInterface {

	/**
	 * Schreibt einen Eintrag in die Datenbank mit den Werten der einzelnen Sensoren. Dafür wird ein Script mit den übergebenen parametern 
	 * gestartet und dem aktuellen Datum, sowie Uhrzeit. 
	 * @param t1 temperature for the sensor s1 in the database
	 * @param t2 temperature for the sensor s2 in the database
	 * @param t3 temperature for the sensor s3 in the database
	 * @param h4 humidity for the sensor s4 in the database
	 */
	public static void writeTemp(double t1, double t2, double t3, double h4){
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat time = new java.text.SimpleDateFormat("HH:mm:ss");
		try {
			Process p =Runtime.getRuntime().exec(new String[] { "/home/pi/dummy_sql/insert.sh",date.format(dt),time.format(dt),String.valueOf(t1),String.valueOf(t2),String.valueOf(t3),String.valueOf(h4) });
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Startet ein Skript zum auslesen des Lüfterstatus aus der Datenbank. Im Terminal wird ein Ausgabe-String ausgegeben und dieser wird mit dem 
	 * BufferedReader eingelesen. Der Ausgabe-String enthält den Wert true oder false und das wird rausgefiltert. 
	 * @return boolean true für Lüfter an, false für Lüfter aus
	 */
	public static boolean queryLuefter(){
		try {
			Process p =Runtime.getRuntime().exec(new String[] { "/home/pi/dummy_sql/queryLuefter.sh"});	//Startet das skript
			p.waitFor();
			String line = "", output = "";
	        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while ((line = input.readLine()) != null) {
	            output += (line);	//Lese die Zeile ein 
	        }
	        input.close();
	        
	        if(output.equalsIgnoreCase("false")){
	        	return false;
	        }else if(output.equalsIgnoreCase("true")){
	        	return true;
	        }else{
	        	System.err.println("Unknown status from database table luefter");
	        }
	        
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
