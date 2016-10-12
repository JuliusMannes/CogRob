import lejos.hardware.port.Port;
import lejos.hardware.sensor.NXTUltrasonicSensor;

//My class!
public class UltraSonicSensor extends Sensor {

	public UltraSonicSensor(Port a_pPort, int a_pSampleSize) {
		Init(a_pSampleSize);
		m_pSensor = new NXTUltrasonicSensor(a_pPort);
	}

	protected void UpdateSampleProvider() {
		m_pSampleProvider = ((NXTUltrasonicSensor) m_pSensor).getDistanceMode();
	}
}
