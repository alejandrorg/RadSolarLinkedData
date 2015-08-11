package es.uc3m.nadir.sr.measureelements;

public class RadiationMeasure {

	private int value;
	private String hourInterval;
	private String intervalStart;
	private String intervalEnd;

	public RadiationMeasure(int v, String hi) {
		this.value = v;
		this.hourInterval = hi;
		String intervs[] = this.hourInterval.split("-");
		this.intervalStart = intervs[0];
		this.intervalEnd = intervs[1];
	}

	public String getIntervalStart() {
		return this.intervalStart;
	}

	public String getIntervalEnd() {
		return this.intervalEnd;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getHourInterval() {
		return hourInterval;
	}

	public void setHourInterval(String hourInterval) {
		this.hourInterval = hourInterval;
	}

	public String toString() {
		return this.hourInterval + " -> " + value;
	}
}
