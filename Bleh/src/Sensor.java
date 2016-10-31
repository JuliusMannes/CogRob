import lejos.hardware.sensor.BaseSensor;
import lejos.robotics.SampleProvider;


public abstract class Sensor {
	//Variables
	protected BaseSensor m_pSensor;
	protected SampleProvider m_pSampleProvider;
	protected int m_iSampleSize = 0;
	protected float[] m_aSamples = null;
	protected int m_iCurrentSample = 0;
	
	protected void Init(int a_pSampleSize) {
		m_iSampleSize = a_pSampleSize;
		m_aSamples = new float[m_iSampleSize];
		for(int i=0; i < m_iSampleSize; i++) {
			m_aSamples[i] = 0.0f;
		}
		System.out.println("Initializing a sensor!");
	}
	
	public float DetectSample() {
		UpdateSampleProvider();
		return FetchSample();
	}
	
	//Private functions
	protected float FetchSample() {
		//Set the sample
		float Sample[] = new float[1];
		m_pSampleProvider.fetchSample(Sample, 0);
		m_aSamples[m_iCurrentSample] = Sample[0];
		
		//Calculate an average
		float Total = 0;
		for(int i=0; i<m_iSampleSize; i++) {
			Total += m_aSamples[i];
		}
		Total /= m_iSampleSize;
		
		//Set Current Sample 
		m_iCurrentSample++;
		if ( m_iCurrentSample >= m_iSampleSize) {
			m_iCurrentSample = 0;
		}
		return Total;
	}
	
	//Abstract functions
	protected abstract void UpdateSampleProvider();
}