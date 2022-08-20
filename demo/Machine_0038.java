
import common.Location;
import common.Machine;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class Machine_0038 extends Machine 
{
    private int step;
	private Location position = new Location(0,0);
	private Location dir = new Location(0,1); // using Location as a 2d vector. Bad!
    
	private String name;

	private Random rand;
	private int phaseNum = -1;
	private int numMachines;
	private int numFaulty;

	private int id;
	private int roundNum;

	private int lcount1;
    private int lcount2;
	private int rcount1;
	private int rcount2;

    private ArrayList<Machine> machineArray;
    private boolean boolState;

	public Machine_0038(String name) 
    {
		this.name = name;
		this.rand = new Random();
        this.machineArray = new ArrayList<Machine>();
		lcount1 = lcount2 = rcount1 = rcount2 = 0;
	}

    @Override
    public void setMachines(ArrayList<Machine> machineArray)
    {
        this.machineArray = machineArray;
		numMachines = machineArray.size();
		numFaulty = numMachines / 3;
        this.id = machineArray.indexOf(this);
    }

	@Override
	public void setStepSize(int stepSize) {step = stepSize;}

	@Override
	public void setState(boolean isCorrect) 
    {
		this.boolState = isCorrect;
		phaseNum++;
		roundNum = 0;
		lcount1 = lcount2 = rcount1 = rcount2 = 0;
	}

	@Override
	public void setLeader() {round0();}

	void round0()
	{
		int decision = rand.nextInt(2);
		System.out.println("Leader Decision: " + decision);

		if(boolState)
			for(Machine m : machineArray)
			    m.sendMessage(id, phaseNum, 0, decision);
			
		else
		{
			int numcorrectMessage = 2 * numFaulty + 1 + rand.nextInt(numMachines - (2*numFaulty + 1) + 1);
			Set <Integer> samemessageMachines = new HashSet <Integer> ();


			while(samemessageMachines.size() != numcorrectMessage)
            	samemessageMachines.add(rand.nextInt(numMachines));
        	

			for(int i = 0; i < numMachines; i++)
				if(samemessageMachines.contains(i))
					machineArray.get(i).sendMessage(id, phaseNum, 0, decision);	
		}
	}

	void round1(int round1ValueGot)
	{
		roundNum = 1;

		if(boolState)
			for(Machine m : machineArray)			
				m.sendMessage(id, phaseNum, 1, round1ValueGot);
	
		else
		{
			int staySilent = rand.nextInt(2);
			round1ValueGot = rand.nextInt(2);

			if(staySilent == 0)			
				for(Machine m : machineArray)				
					m.sendMessage(id, phaseNum, 1, round1ValueGot);		
		}
	}

	void round2start(int round1ValueSet)
	{
		if(boolState)
			for(Machine m : machineArray)
				m.sendMessage(id, phaseNum, 2, round1ValueSet);
		
		else
		{
			int staySilent = rand.nextInt(2);
			round1ValueSet = rand.nextInt(2);

			if(staySilent == 0)	
				for(Machine m : machineArray)				
					m.sendMessage(id, phaseNum, 2, round1ValueSet);		
		}
	}

	void round2end(int finalDecision)
	{
		if(finalDecision == 0)
		{
			if(dir.getY() == 0)
				dir.setLoc(0, dir.getX());
			else
				dir.setLoc(dir.getY() * -1, 0);
		}
		else
		{
			if(dir.getY() == 0)
				dir.setLoc(0, dir.getX() * -1);
			else
				dir.setLoc(dir.getY(), 0);
		}
		System.out.println(name + ": Final decision " + finalDecision);
		lcount1 = lcount2 = rcount1 = rcount2 = 0;
	}

	@Override
	public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) 
    {
		if(roundNum == 0)        //round 0
		{
			roundNum = 1;
			round1(decision);
		}

		else if(roundNum == 1 && roundNum < 2)    // round 1
		{
			if(decision == 0)
				lcount1++;
			else
				rcount1++;

			if(lcount1 + rcount1 >= (2 * numFaulty + 1))
			{
				roundNum = 2;
				int round2decision = (lcount1 >= numFaulty + 1) ? 0 : 1;
				round2start(round2decision);
			}
		}
		
		else if(roundNum == 2 && roundNum != -1)		//round 2
		{
			if(decision == 0)
				lcount2++;
			else
				rcount2++;

			if(lcount2 >= 2*numFaulty + 1)
			{
				roundNum = -1;
				round2end(0);
			}
	
			else if(rcount2 >= 2*numFaulty + 1)
			{
				roundNum = -1;
				round2end(1);
			}
		}
	}

	@Override
	public void move()			  {position.setLoc(position.getX() + dir.getX()*step, position.getY() + dir.getY()*step);}
	@Override
	public String name() 		  {return name;}
	@Override
	public Location getPosition() {return new Location(position.getX(), position.getY());}
}
