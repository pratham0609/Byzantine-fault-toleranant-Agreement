import java.util.ArrayList;
import demo.*;
import common.*;

public class Main {
		public static void main(String[] args) {
		
		Game game = new Game_0038();
		ArrayList<Machine> machineArrray = new ArrayList<>();
		
		// add a bunch of different machines. For now, all demo machines
		// These will be replaced by machines of different students
		// Add as many as you would like, and change numFaults accordingly (but less than 1/3 of total machines)
		
		machineArrray.add(new Machine_0038());
		machineArrray.add(new Machine_0038("Machine 2"));
		machineArrray.add(new Machine_0038("Machine 3"));
		machineArrray.add(new Machine_0038("Machine 4"));
		machineArrray.add(new Machine_0038("Machine 5"));
		machineArrray.add(new Machine_0038("Machine 6"));
		machineArrray.add(new Machine_0038("Machine 7"));
		machineArrray.add(new Machine_0038("Machine 8"));
		machineArrray.add(new Machine_0038("Machine 9"));
		machineArrray.add(new Machine_0038("Machine 10"));
		machineArrray.add(new Machine_0038("Machine 11"));
		machineArrray.add(new Machine_0038("Machine 12"));
		machineArrray.add(new Machine_0038("Machine 13"));
		// change the following as needed
		int numFaults = 4;
		int stepSize = 1;
		int sleepTime = 2000;
		
		// Try not to change anything beyond this
		
		for(Machine machine: machineArrray) {
			machine.setStepSize(stepSize); // pixels to move in each step
			machine.start(); // starts the machine running in its own thread
		}
		
		game.addMachines(machineArrray, numFaults);
		
		for(int i=0;i<100;i++) {
			for(Machine machine: machineArrray) {
				Location pos = machine.getPosition();
				System.out.println(machine.name() + ":" + pos.getX() + "," + pos.getY() ); 
			}
			game.startPhase();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
