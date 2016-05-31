package Sensoren;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HumSensor extends Meassensor {
	
	private int pin;	// Angeschlossener Pin
	
	public HumSensor(int pin){
		super();
		this.pin = pin;
	}
	
	/**
	 * Lese die Feuchtigkeit ein. Es wird das Skript aus der Bibliothek fuer den Feuchtigkeitssensor gestartet mit den angeschlossenen Pin.
	 * Anschließend erzeugt das Skript eine Ausgabe und diese wird durch den BufferedReader eingelesen. Da die Ausgabe immer nachdem selben prinzip
	 * aufgebaut ist, kann der wert für den Feuchtigkeitssensor aus dem String ermittelt werden.
	 * Ausgabe-bsp: "temp=x°C humditiy=y%"
	 * 
	 * Setzt den Wert in dieser Klasse
	 * @return: double value;
	 */
	public double readHum(){
		String cmd = "sudo /home/pi/Programme/Adafruit_Python_DHT/examples/AdafruitDHT.py 22 " + pin;
	        try {
	            String line = "", output = "";		            
	            Process p = Runtime.getRuntime().exec(cmd.split(" "));		            
	            p.waitFor();
	            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            while ((line = input.readLine()) != null) {
	            	output += (line + '\n');
	            }
	            input.close();
	            String[] parts = output.split("%");	            
	            String[] split = parts[0].split("=");
	            
	            
	            double humidity = Double.valueOf(split[2]);
	            
	            //Filterung. Es können Fehler bei der auslesung auftreten, wodurch der Wert nicht zwischen 0 und 100 liegt.
	            if( (humidity>0 && humidity <100) ){
	            	
	            	if( (this.getValue() - humidity <= 10 && this.getValue() - humidity >= -10) || this.getValue() == 0 ){            		
	            		this.setValue(humidity);
	            		return this.getValue();
	            	}else{
	            		return -1;
	            	}
	            	
	            }

	        }
	        catch(ArrayIndexOutOfBoundsException e){
	        	System.out.println("Array out of bound - HumSensor");
	        }	        
	        catch (Exception ex) {
	        	System.out.println("Sensor konnte nicht richtig ausgelesen werden.");
//	            ex.printStackTrace();
	        }
	        return this.getValue();
	}
}
