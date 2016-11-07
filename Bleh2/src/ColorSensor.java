import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

//My class!
public class ColorSensor extends Sensor {

	public ColorSensor(Port a_pPort, int a_pSampleSize) {
		Init(a_pSampleSize);
		m_pSensor = new EV3ColorSensor(a_pPort);
	}

	protected void UpdateSampleProvider() {
		m_pSampleProvider = ((EV3ColorSensor) m_pSensor).getColorIDMode();
	}
}