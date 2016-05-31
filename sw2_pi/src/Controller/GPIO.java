package Controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GPIO {

	GpioPinDigitalOutput relais;		// gpio pin fuers relais
	GpioController gpio;				// Instanz fuer den gpioController
	
	public GPIO(){
		//Create Instance
		this.gpio = GpioFactory.getInstance();
		
		//Setze GPIO auf Pin 12(GPIO 18, wiring 01) und setze auf Low
		this.relais = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,PinState.LOW);
	}
	
	/**
	 * Schalte Relais an
	 */
	public void relaisOn(){
		this.relais.high();
	}
	
	/**
	 * Schalte Relais aus
	 */
	public void relaisOff(){
		this.relais.low();
	}	
}
