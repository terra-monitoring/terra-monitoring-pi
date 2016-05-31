package Sensoren;

public abstract class Meassensor {
	private double value;

	/**
	 * getValue
	 */
	public double getValue() {
		return value;
	}

	/**
	 * setValue
	 * @param humidity
	 */
	public void setValue(double humidity) {
		this.value = humidity;
	}
}
