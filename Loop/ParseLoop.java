package source.Loop;

import source.Types.*;
import source.Types.Number;

import source.Loop.MathLoop;

import java.util.ArrayList;

public class ParseLoop {
	public static Fraction PLoop(String in)
	{	
		if(in.equals("toggleDebug"))
		{
			System.out.println("debug toggled: " + !InputLoop.debugLog);
			InputLoop.debugLog = !InputLoop.debugLog;
			if(!InputLoop.debugLog)
				System.out.println("...How could you leave the debug log? YOU MONSTER!");
			return null;
		}
		
		if(in.toCharArray()[0] == '$')
		{
			Variable v = new Variable(in);
			boolean exists = false;
			for(Variable c:InputLoop.vars)
			{
				if(c.n.equals(v.n))
				{
					exists = true;
				}
			}
			if(!exists)
				InputLoop.vars.add(new Variable(in));
			else
			{
				int i = 0;
				for(Variable c:InputLoop.vars)
				{
					if(c.n == v.n)
					{
						InputLoop.vars.set(i, v);
					}
					i++;
				}
			}
			return null;
		}
		
		String win = in;
		while(win.indexOf(" ") != -1)
		{
			//That should remove all spaces.
			if(InputLoop.debugLog)
				System.out.println("Removed a space for parsing.");
			in = win.substring(0,win.indexOf(" ")) + win.substring(win.indexOf(" ")+1);
			win = in;
		}
		
		ArrayList<Number> Nums = new ArrayList<Number>();
		ArrayList<Fraction> Fracs = new ArrayList<Fraction>();
		ArrayList<Integer> Divs = new ArrayList<Integer>();
		
		//fracLoc is what we're interested in for checkpoint 2.
		int fracLoc = in.indexOf('/');
		
		win = in;
		boolean LPF = false;
		while(fracLoc != -1)
		{
			int ML = win.indexOf('*');
			int AL = win.indexOf('+');
			int SL = win.indexOf('-');
			int LE = win.lastIndexOf('^');
			boolean isNeg = false;
			boolean madeFrac = false;
			if(SL == 0)
			{
				if(InputLoop.debugLog)
					System.out.println("Handled a negative fraction.");
				isNeg = true;
				win = win.substring(1);
			}
			ML = win.indexOf('*');
			AL = win.indexOf('+');
			SL = win.indexOf('-');
			LE = win.lastIndexOf('^');
			//Backwards points of reference
			int[] Locs = {ML,AL,SL,LE};
			int lowest = Integer.MAX_VALUE;
			
			for(int L:Locs)
			{
				if(L < lowest && L != -1)
				{
					lowest = L;
				}
			}
			
			if(lowest < fracLoc)
			{
				win = win.substring(lowest+1);
				LPF = false;
			}
			else
			{
				if(!LPF)
				{
					if(lowest == Integer.MAX_VALUE)
					{
						if(win.indexOf('/') == win.lastIndexOf('/'))
						{
							Fracs.add(ParseFraction(win));
						}
						else
						{
							String tp = win;
							while(tp.indexOf('/') != tp.lastIndexOf('/'))
							{
								tp = tp.substring(0, tp.lastIndexOf('/'));
							}
							if(tp.indexOf('_') != -1)
							{
								if(tp.indexOf('_') > tp.indexOf('/'))
								{
									Nums.add(new Number(Float.parseFloat(tp.substring(0,tp.indexOf('/')))));
								}
								else
								{
									Fracs.add(ParseFraction(tp));
								}
							}
						}
					}
					else
					{
						String win1 = win.substring(0,lowest);
						if(win1.indexOf('/') == win1.lastIndexOf('/'))
						{
							Fracs.add(ParseFraction(win1));
						}
						else
						{
							String tp = win1;
							while(tp.indexOf('/') != tp.lastIndexOf('/'))
							{
								tp = tp.substring(0, tp.lastIndexOf('/'));
							}
							if(tp.indexOf('_') != -1)
							{
								if(tp.indexOf('_') > tp.indexOf('/'))
								{
									Nums.add(new Number(Float.parseFloat(tp.substring(0,tp.indexOf('/')))));
								}
								else
								{
									Fracs.add(ParseFraction(tp));
								}
							}
						}
					}
					LPF = true;
					madeFrac = true;
					win = win.substring(win.indexOf('/')+1);
				}
				else
				{
					Divs.add(fracLoc+(in.length()-win.length()));
					LPF = false;
					win = win.substring(fracLoc+1);
				}
				
			}
			
			if(isNeg && madeFrac)
			{
				Fraction F = Fracs.get(Fracs.size()-1);
				if(F.wNum != 0)
				{
					Fracs.get(Fracs.size()-1).wNum = -Fracs.get(Fracs.size()-1).wNum;
					Fracs.get(Fracs.size()-1).top = -Fracs.get(Fracs.size()-1).top;
				}
				else
				{
					Fracs.get(Fracs.size()-1).top = -Fracs.get(Fracs.size()-1).top;
				}
			}
			
			fracLoc = win.indexOf('/');
		}
		//Fractions should be parsed.
		if(InputLoop.debugLog)
			System.out.println("Parsed " + Fracs.size() + " Fractions.");
		//Start parsing numbers.
		win = in;
		while(StillNums(win))
		{
			int ML = win.indexOf('*');
			int AL = win.indexOf('+');
			int SL = win.indexOf('-');
			int LE = win.lastIndexOf('^');
			boolean isNeg = false;
			fracLoc = win.indexOf('/');
			int[] Locs = {ML,AL,SL,LE};
			int lowest = Integer.MAX_VALUE;
			
			for(int L:Locs)
			{
				if(L < lowest && L != -1 && L != 0)
				{
					lowest = L;
				}
				if(lowest == 0 && SL == 0)
				{
					if(InputLoop.debugLog)
						System.out.println("Handled a negative number");
					win = win.substring(1);
					isNeg = true;
				}
			}
			if(lowest != Integer.MAX_VALUE)
			{
				if(lowest < fracLoc || fracLoc == -1)
				{
					Nums.add(new Number(Float.parseFloat(win.substring(0,lowest))));
					win = win.substring(lowest+1);
				}
				else
				{
					win = win.substring(lowest+1);
				}
			}
			else
			{
				if(fracLoc == -1)
				{
					Nums.add(new Number(Float.parseFloat(win)));
				}
				win = "";
			}
			if(isNeg)
			{
				Nums.get(Nums.size()-1).N = -Nums.get(Nums.size()-1).N;
			}
		}
		//Numbers are parsed!
		if(InputLoop.debugLog)
			System.out.println("Parsed " + Nums.size() + " Numbers.");
		//Time to parse functions. (oh god)
		return ParseFunctions(in, Nums, Fracs, Divs);
	}
	
	public static Fraction ParseFunctions(String in, ArrayList<Number> Nums, ArrayList<Fraction> Fracs, ArrayList<Integer> Divs)
	{
		ArrayList<Function> Funcs = new ArrayList<Function>();
		int CFC = 0;
		int CNC = 0;
		String win = in;
		int fracLoc;
		char FNT;
		
		{	//Always ran, Just making another scope to escape the while loop's scope.
			int ML = win.indexOf('*');
			int AL = win.indexOf('+');
			int SL = win.indexOf('-');
			int LE = in.lastIndexOf('^');
			fracLoc = win.indexOf('/');
			int[] Locs = {ML,AL,SL,LE};
			
			if(SL == 0)
			{
				win = win.substring(1);
			}
			
			ML = win.indexOf('*');
			AL = win.indexOf('+');
			SL = win.indexOf('-');
			LE = in.lastIndexOf('^');
			Locs[2] = SL;
			
			int lowest = Integer.MAX_VALUE;
			
			for(int L:Locs)
			{
				if(L < lowest && L != -1)
				{
					lowest = L;
				}
			}
			
			if(fracLoc > lowest)
			{
				FNT = 'N';
			}
			else if(fracLoc != -1)
			{
				FNT = 'F';
			}
			else
			{
				FNT = 'N';
			}
		}
		
		while(win.indexOf('+') != -1 || win.indexOf('-') != -1 || win.indexOf('*') != -1 || win.indexOf('^') != -1 || win.indexOf('/') != -1)
		{
			int ML = win.indexOf('*');
			int AL = win.indexOf('+');
			int SL = win.indexOf('-');
			int LE = win.indexOf('^');
			fracLoc = win.indexOf('/');
			int[] Locs = {ML,AL,SL,LE};
			
			if(SL == 0)
			{
				if(InputLoop.debugLog)
					System.out.println("Dropped a negative sign from function parsing");
				win = win.substring(1);
			}
			
			{
				if(ML==0 || AL==0 || LE==0)
				{
					System.err.println("ERROR: Input is invalid!");
					return null;
				}
			}
			
			ML = win.indexOf('*');
			AL = win.indexOf('+');
			SL = win.indexOf('-');
			LE = win.indexOf('^');
			Locs[2] = SL;
			
			int lowest = Integer.MAX_VALUE;
			
			for(int L:Locs)
			{
				if(L < lowest && L != -1)
				{
					lowest = L;
				}
				
			}
			
			String win2;
			
			if(lowest == Integer.MAX_VALUE)
			{
				win2 = win;
			}
			else
			{
				win2 = win.substring(0, lowest);
			}
			
			while(win2.indexOf('/') != -1 && Divs.size() != 0)
			{
				if(win2.indexOf('/') + (in.length() - win2.length()) != Divs.get(0))
				{
					//Ignore, move on
					win2 = win2.substring(win2.indexOf('/')+1);
				}
				else
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a division function");
					CFC++;
					Funcs.add(new Function(CFC,'F',Function.FuncType.OP_DIVISION));
					win2 = win2.substring(win2.indexOf('/')+1);
					win = win.substring(Divs.get(0)+1);
					Divs.remove(0);
				}
			}
			
			if(lowest == Integer.MAX_VALUE)
			{
				win = "";
			}
			
			if(lowest > fracLoc && fracLoc != -1)
			{
				
				if(ML == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a multiplication function");
					CFC++;
					Funcs.add(new Function(CFC,'F',Function.FuncType.OP_MULTIPLICATION));
					win = win.substring(ML+1);
				}
				if(AL == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a addition function");
					CFC++;
					Funcs.add(new Function(CFC,'F',Function.FuncType.OP_ADDITION));
					win = win.substring(AL+1);
				}
				if(SL == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a subtraction function");
					CFC++;
					Funcs.add(new Function(CFC,'F',Function.FuncType.OP_SUBTRACTION));
					win = win.substring(SL+1);
				}
				if(LE == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a exponent function");
					CFC++;
					Funcs.add(new Function(CFC,'F',Function.FuncType.OP_EXPONENT));
					win = win.substring(LE+1);
				}
			}
			else
			{
				if(ML == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a multiplication function");
					CNC++;
					Funcs.add(new Function(CNC,'N',Function.FuncType.OP_MULTIPLICATION));
					win = win.substring(ML+1);
				}
				if(AL == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a addition function");
					CNC++;
					Funcs.add(new Function(CNC,'N',Function.FuncType.OP_ADDITION));
					win = win.substring(AL+1);
				}
				if(SL == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a subtraction function");
					CNC++;
					Funcs.add(new Function(CNC,'N',Function.FuncType.OP_SUBTRACTION));
					win = win.substring(SL+1);
				}
				if(LE == lowest)
				{
					if(InputLoop.debugLog)
						System.out.println("Noted a exponent function");
					CNC++;
					Funcs.add(new Function(CNC,'N',Function.FuncType.OP_EXPONENT));
					win = win.substring(LE+1);
				}
			}
		}
		
		if(Funcs.size() == 1)
		{
			if(InputLoop.debugLog)
				System.out.println("Performed a hack of a fix since there is 1 function");
			if(FNT == 'N')
				Funcs.get(0).LPT = 'F';
			else
				Funcs.get(0).LPT = 'N';
		}
		
		if(InputLoop.debugLog)
			System.out.println("Parsed " + Funcs.size() + " Functions.");
		return MathLoop.MLoop(Nums, Fracs, Funcs, FNT,in);
	}
	
	public static boolean StillNums(String in)
	{
		if(in.indexOf('1') != -1 || in.indexOf('2') != -1 || in.indexOf('3') != -1 || in.indexOf('4') != -1 || in.indexOf('5') != -1 || in.indexOf('6') != -1 || in.indexOf('7') != -1 || in.indexOf('8') != -1 || in.indexOf('9') != -1 || in.indexOf('0') != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Fraction ParseFraction(String in)
	{
		Fraction frac;
		
		int WNL = in.indexOf("_");
		int HL = in.indexOf("/");
		
		int WN;
		int TN;
		int BN;
		
		if(WNL != -1)
		{
			WN = Integer.parseInt(in.substring(0,WNL));
			TN = Integer.parseInt(in.substring(WNL+1,HL));
			BN = Integer.parseInt(in.substring(HL+1));
			frac = new Fraction(TN, BN, WN);
		}
		else
		{
			TN = Integer.parseInt(in.substring(WNL+1,HL));
			BN = Integer.parseInt(in.substring(HL+1));
			frac = new Fraction(TN, BN);
		}
		
		return frac;
	}
}
