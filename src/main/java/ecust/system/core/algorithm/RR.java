package ecust.system.core.algorithm;import ecust.system.core.metadata.PCB;import java.util.ArrayList;public class RR implements ProcessSchedulingAlgorithm {	private int timeSliceSize;	@Override	public void initialize(ArrayList<PCB> list) {		timeSliceSize = 10;	}	@Override	public void schedule(int currentTime) {	}	@Override	public void disuse() {	}}