package source.Types;

public class Variable {
	public boolean isFrac;
	public Fraction s;
	public String n;
	
	public Variable(Fraction frac, String name)
	{
		isFrac = true;
		s = frac;
		n = name;
	}
	
	public Variable(String toParse)
	{
		while(toParse.indexOf(' ') != -1)
		{
			toParse = toParse.substring(0, toParse.indexOf(' ')) + toParse.substring(toParse.indexOf(' ') + 1);
		}
		
		int[] numLocs = {toParse.indexOf('1') , toParse.indexOf('2') , toParse.indexOf('3') , toParse.indexOf('4') , toParse.indexOf('5') , toParse.indexOf('6') , toParse.indexOf('7') , toParse.indexOf('8') , toParse.indexOf('9') , toParse.indexOf('0')};
		int lowest = Integer.MAX_VALUE;
		
		for(int n:numLocs)
		{
			if(n < lowest && n != -1)
			{
				lowest = n;
			}
		}
		
		if(lowest == Integer.MAX_VALUE)
		{
			System.err.println("Invalid input; set variable to 0");
			n = toParse.substring(1);
			s = new Fraction(0,1);
		}
		
		n = toParse.substring(1,lowest);
		s = source.Loop.ParseLoop.PLoop(toParse.substring(lowest));
		isFrac = true;
	}
}
