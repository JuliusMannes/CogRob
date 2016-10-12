import lejos.hardware.port.Port;
import lejos.hardware.sensor.NXTLightSensor;


public class LightSensor extends Sensor {
	public LightSensor(Port a_pPort, int a_pSampleSize) {
		Init(a_pSampleSize);
		m_pSensor = new NXTLightSensor(a_pPort);
	}

	@Override
	protected void UpdateSampleProvider() {
		m_pSampleProvider = ((NXTLightSensor) m_pSensor).getAmbientMode();
	}
}
