package ecust.system.core.manager;import ecust.system.core.algorithm.FCFS;import ecust.system.core.algorithm.HPF;import ecust.system.core.algorithm.ProcessSchedulingAlgorithm;import ecust.system.core.algorithm.RR;import ecust.system.core.metadata.Other;import ecust.system.core.metadata.PCB;import ecust.system.core.metadata.catalog.PCBStateEnum;import java.util.ArrayList;public class ProcessSchedulingManager extends Thread {	// 单例对象	private static ProcessSchedulingManager manager;	// 单例方法	public static ProcessSchedulingManager getProcessSchedulingManager() {		if(manager == null) {			synchronized(ProcessSchedulingManager.class) {				if(manager == null) {					manager = new ProcessSchedulingManager();				}			}		}		return manager;	}	// 获取随机整数	private static int getRandomInteger(int min, int max) {		if(max < min) {			max ^= min;			min ^= max;			max ^= min;		}		return (int)(Math.random() * (max - min) + min);	}	// 获取随机的PCB	private static PCB getNewRandomPCB(int id) {		PCB pcb = new PCB();		pcb.setId(id);		pcb.setName("P" + (id + 1));		pcb.setCreateTime(getRandomInteger(0, 1000));		pcb.setPriority(getRandomInteger(1, 10));		pcb.setRanTime(0);		pcb.setState(PCBStateEnum.WAITING);		pcb.setRequiredTime(getRandomInteger(10, 100));		pcb.setFinishTime(0);		pcb.setOther(new Other());		pcb.setFirstCallTime(0);		return pcb;	}	// 打印所有进程信息	public static void printProcessInfo(ArrayList<PCB> list) {		for(PCB pcb: list) {			System.out.println(pcb.toString());		}	}	// 进程控制块列表	private ArrayList<PCB> pcbList;	// CPU总运行时间	private int CPUTime;	// 进程调度算法	private ProcessSchedulingAlgorithm algorithm;	// 网页命令消息	private String message;	// 运行状态	private boolean running;	// 初始化线程	private ProcessSchedulingManager() {		setPriority(9);		setDaemon(true);		setName("process-schedule-manager");	}	// 发送命令消息	public synchronized void setMessage(String message) {		this.message = message;	}	// 程序是否正在运行	public boolean isRunning() {		return running;	}	// 初始化数据	private void initialize() {		running = false;		algorithm = new FCFS();		pcbList = new ArrayList<>();		for(int i = 0; i < 10; i++) {			pcbList.add(getNewRandomPCB(i));		}		reset();	}	// 重置调度算法	private void reset() {		CPUTime = 0;		setMessage("");		algorithm.initialize(new ArrayList<>(pcbList));		running = true;	}	@Override	public void run() {		super.run();		initialize();		System.out.println("Process Information:");		printProcessInfo(pcbList);		while(++CPUTime < Integer.MAX_VALUE) {			switch(message) {				case "FCFS":					if(!(algorithm instanceof FCFS)) {						running = false;						algorithm = new FCFS();						reset();					}					break;				case "RR":					if(!(algorithm instanceof RR)) {						running = false;						algorithm = new RR();						reset();					}					break;				case "HPF":					if(!(algorithm instanceof HPF)) {						running = false;						algorithm = new HPF();						reset();					}					break;				default: algorithm.schedule(CPUTime);			}		}	}}