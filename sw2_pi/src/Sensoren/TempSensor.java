package Sensoren;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TempSensor extends Meassensor{
	
	private String hardwareID;	//HardwareID fuer den Temperatursensor
	
	public TempSensor(String hardwareID){
		super();
		this.hardwareID = hardwareID;
	}

	/**	readTemp() liest die Messdaten für den Temperatursensor aus. Es wird durch den aufruf des Pfades eine String ausgabe erzeugt, die mit dem Bufferedreader
	 * 	eingelesen wird. Die zweite Zeile enthält den Temperaturwert nach dem "t=". Dieser Wert muss noch durch eintausend geteilt werden. Anschließend wird 
	 * 	der value in dieser instanz gesetzt.
	 * 
	 */
	public void readTemp(){
    	String filePath = "/sys/bus/w1/devices"+ "/" + this.hardwareID + "/w1_slave";
    	File f = new File(filePath);
    	try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		String output;
    		int count=0;
    		while((output = br.readLine()) != null) {
    			if(count==1){
    				double temp = Double.parseDouble(output.substring(output.indexOf("t=") + 2)) / 1000;
        			//Filter für die Temperatur
        			if(temp > 0 && temp < 100) {
        				this.setValue(temp);
        			}
    			}
    			count++;
    		}
    	}
    	catch(Exception ex) {
        System.out.println(ex.getMessage());
    	}		
	}
}
