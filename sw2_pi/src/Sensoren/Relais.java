package Sensoren;

import Controller.GPIO;

public class Relais {

	private boolean status;
	private GPIO gpio;
	
	public Relais(GPIO gpio){
		this.gpio = gpio;
	}
 
	/**
	 * getStatus
	 * @return status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Setzt den status im relais und schalte den PIN durch den gpio.
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
		if(status){
			gpio.relaisOn();
		}else if(!status){
			gpio.relaisOff();
		}
	}
}
