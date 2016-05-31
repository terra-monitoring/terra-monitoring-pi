package Controller;

import Sensoren.HumSensor;
import Sensoren.Relais;
import Sensoren.TempSensor;



public class TerrariumMeasurement{
	
	private TempSensor s1;
	private TempSensor s2;
	private TempSensor s3;
	private HumSensor h4;
	private Relais relais;
	private long looptime = 5000;
	
	
	/** Constructor for TerrariumMeasurement
	 * 	Initializes the sensors
	 */
	public TerrariumMeasurement(){
		s1 = new TempSensor("28-04162201eaff");
		s2 = new TempSensor("28-03162293e2ff");
		s3 = new TempSensor("28-031622428bff");
		h4 = new HumSensor(17);
		relais = new Relais(new GPIO());
	}
	
	/**	Diese Methode steuert das Programm. In einer Dauerschleife werden die Messdaten von den Sensoren
	 * 	ausgelesen und in die Datenbank gespeichert. Zudem wird der Lüfterstatus ausgelesen und in Klasse Relais
	 * 	gesetzt.
	 */
	public void start(){
		while(true){
			long starttime=System.currentTimeMillis();			
			//Lese Temperaturen
			s1.readTemp();
			s2.readTemp();
			s3.readTemp();
		
			long dTemp = System.currentTimeMillis();
			System.out.println("Dauer auslesen der Tempsensoren: " + (dTemp-starttime));
			
			//Falls der Feuchtigkeitssensor nicht im realistischen Bereich ist, wird kein Datenbank eintrag vorgenommen (nachträglicher Kundenwunsch)
			if(h4.readHum() != -1){
				//Schreibe Temperaturen und Luftfeuchtigkeit in die DB table seven_day
				DBInterface.writeTemp(s1.getValue(), s2.getValue(), s3.getValue(), h4.getValue());
			}else{
				System.out.println("Datensatz nicht erstellt, da Feuchtigkeitssensor nicht ausgelesen werden konnte.");
			}
			System.out.println("Dauer auslesen HumSensor: " + (System.currentTimeMillis() - dTemp));
			//Lese aus der Datenbank den Lüfterstatus und setze ihn.
			relais.setStatus( DBInterface.queryLuefter() );
			
			System.out.println("TempSensoren: s1: " + s1.getValue() +" s2: "+ s2.getValue() + " s3: "+ s3.getValue());
			System.out.println("HumSensor: " + h4.getValue());
			
			try {
				//Wartezeit
				if((System.currentTimeMillis()-starttime)<looptime){
					Thread.sleep(looptime-(System.currentTimeMillis() - starttime));
				}else{
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Kompletter Durchlauf : "+(System.currentTimeMillis()-starttime) + "\n");
		}		
	}
	
	public static void main(String[] args) {
		TerrariumMeasurement st = new TerrariumMeasurement();
		st.start();

	}
}
