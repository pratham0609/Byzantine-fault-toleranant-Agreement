import java.*;
import common.Game;
import common.Machine;

public class Game_0038 extends Game 
{
    private int numFaulty;
    private int numMachines;
    private int phaseNum;
    private ArrayList<Machine> machineArray;

    Game_0038()
    {
        this.numFaulty = 0;
        this.machineArray = new ArrayList<Machine>();
        this.phaseNum = 0;
    }

	@Override
	public void addMachines(ArrayList<Machine> machineArray, int numFaulty) 
    {
        this.machineArray.addAll(machineArray);
        this.numFaulty = numFaulty;
        this.numMachines = machineArray.size();
	}

	@Override
	public void startPhase()
    {
        System.out.println("\n...................................................\nPHASE " + phaseNum+":");
        phaseNum++;

        Random r = new Random();

        Set <Integer> faulty = new HashSet <Integer> ();            //stores indices of faulty machines 

        while(faulty.size() != numFaulty)                           //random indices for faulty machines
            faulty.add(r.nextInt(numMachines));

        System.out.print("[Faulty: ");                   
        for(int i = 0; i < numMachines; i++)                        //sets states of machines
        {
            if(faulty.contains(i))
            {
                machineArray.get(i).setState(false);
                System.out.print(machineArray.get(i).name() + " ");
            }
            else   
                machineArray.get(i).setState(true); 
        }

        for(Machine m : machineArray)
            m.setMachines(this.machineArray);
        
        int leader = r.nextInt(numMachines);                        //for setting leader
        System.out.print("] [Leader : " + leader + "]\n...................................................\n");
        machineArray.get(leader).setLeader();
	}
}
