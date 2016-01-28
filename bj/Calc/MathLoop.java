package Calc;

import java.util.ArrayList;

import Calc.Types.Fraction;
import Calc.Types.Function;
import Calc.Types.Number;

public class MathLoop {
	public static Fraction MLoop(ArrayList<Number> Nums, ArrayList<Fraction> Fracs, ArrayList<Function> Funcs, char FNT, String in)
	{
		//Prepare functions, they're a bit on the wonky side.
		int x = 0;
		if(InputLoop.debugLog)
			System.out.println("Performing another hack of a fix; Initially functions relied upon the number/fraction before them.");
		while(x < Funcs.size())
		{
			if(x == Funcs.size()-1)
			{
				int LM = in.lastIndexOf('*');
				int LS = in.lastIndexOf('-');
				int LA = in.lastIndexOf('+');
				int LE = in.lastIndexOf('^');
				int fracLoc = in.lastIndexOf('/');
				int[] Locs = {LM,LS,LA,LE};
				int highest = 0;
				
				for(int L:Locs)
				{
					if(L > highest)
					{
						highest = L;
					}
				}
				if(highest > fracLoc)
				{
					Funcs.get(Funcs.size()-1).LPT = 'N';
					Funcs.get(Funcs.size()-1).LP = Nums.size() - 1;
				}
				else
				{
					Funcs.get(Funcs.size()-1).LPT = 'F';
					Funcs.get(Funcs.size()-1).LP = Fracs.size() - 1;
				}
			}
			else
			{
				Funcs.get(x).LPT = Funcs.get(x+1).LPT;
			}
			x++;
		}
		
		//Trashy fix!
		int z = 0;
		for(Function f:Funcs)
		{
			if(f.OP == Function.FuncType.OP_DIVISION)
			{
				if(Funcs.get(z-1).LPT == 'N')
				{
					Funcs.get(z-1).OP = Function.FuncType.OP_DIVISION;
					Funcs.remove(z);
				}
			}
			z++;
		}
		
		
		//Fractions are ready. Prepare answer.
		Fraction answer = new Fraction(0,1);
		if(InputLoop.debugLog)
			System.out.println("Another hack of a fix that does god knows what, but it stops the program from crashing");
		if(FNT == 'N')
		{
			answer.wNum = (int) Nums.get(0).N;
			for(Function F:Funcs)
			{
				if(F.LPT == 'F')
				{
					F.LP -= 1;
				}
			}
		}
		else
		{
			answer = Fracs.get(0);
			for(Function F:Funcs)
			{
				if(F.LPT == 'N')
				{
					F.LP -= 1;
				}
			}
		}
		if(InputLoop.debugLog)
			System.out.println("Fixes performed, doing the math.");
		//Answer is prepared, let's get mathing.
		for(Function F:Funcs)
		{
			switch(F.OP)
			{
				case OP_ADDITION:
					if(InputLoop.debugLog)
						System.out.println("Adding");
					if(F.LPT == 'F')
					{
						Fraction W;
						if(Fracs.size() == 1)
						{
							W = Fracs.get(F.LP+1);
						}
						else
						{
							W = Fracs.get(F.LP);
						}
						answer.wNum += W.wNum;
						W.top *= answer.bottom;
						answer.top *= W.bottom;
						answer.bottom *= W.bottom;
						answer.top += W.top;
						answer.Simplify();
					}
					else
					{
						if(Nums.size() == 1)
							answer.wNum += Nums.get(F.LP+1).N;
						else
							answer.wNum += Nums.get(F.LP).N;
					}
					break;
				case OP_MULTIPLICATION:
					if(InputLoop.debugLog)
						System.out.println("Multiplying");
					if(F.LPT == 'F')
					{
						Fraction W;
						if(Fracs.size() == 1)
						{
							W = Fracs.get(F.LP+1);
						}
						else
						{
							W = Fracs.get(F.LP);
						}
						W = new Fraction((W.wNum*W.bottom)+W.top,W.bottom);
						answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
						answer.bottom *= W.bottom;
						answer.top = W.top * answer.top;
						
						answer.Simplify();
					}
					else
					{
						if(Nums.size() == 1)
						{
							answer.wNum *= Nums.get(F.LP+1).N;
							answer.top *= Nums.get(F.LP+1).N;
						}
						else
						{
							answer.wNum *= Nums.get(F.LP).N;
							answer.top *= Nums.get(F.LP).N;
						}
						answer.Simplify();
					}
					break;
				case OP_SUBTRACTION:
					if(InputLoop.debugLog)
						System.out.println("Subtracting");
					if(F.LPT == 'F')
					{
						Fraction W;
						if(Fracs.size() == 1)
						{
							W = Fracs.get(F.LP+1);
						}
						else
						{
							W = Fracs.get(F.LP);
						}
						W = new Fraction((W.wNum*W.bottom)+W.top,W.bottom);
						answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
						answer.top *= W.bottom;
						W.top *= answer.bottom;
						answer.bottom *= W.bottom;
						
						answer.top -= W.top;
						
						answer.Simplify();
					}
					else
					{
						if(Nums.size() == 1)
							answer.wNum -= Nums.get(F.LP+1).N;
						else
							answer.wNum -= Nums.get(F.LP).N;
					}
					break;
				case OP_EXPONENT:
					if(F.LPT == 'F')
					{
						System.err.println("ERROR: tried to exponentially multiply with a fraction");
						System.out.println("");
						return null;
					}
					if(Nums.get(F.LP).N < 0)
					{
						System.err.println("ERROR: tried to exponentially multiply by a negative number");
						System.out.println("");
						return null;
					}
					if(Nums.get(F.LP).N == 0)
					{
						answer = new Fraction(0,1);
					}
					if(InputLoop.debugLog)
						System.out.println("Exponentially multiplying");
					int i = 2;
					if(Nums.size() == 1)
					{
						
						while(i <= Nums.get(F.LP+1).N)
						{
							if(InputLoop.debugLog)
								System.out.println(answer.top);
							answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
							answer.top *= answer.top;
							answer.bottom *= answer.bottom;
							i++;
						}
					}
					else
					{
						while(i <= Nums.get(F.LP).N)
						{
							if(InputLoop.debugLog)
								System.out.println(answer.top);
							answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
							answer.top *= answer.top;
							answer.bottom *= answer.bottom;
							i++;
						}
					}
					break;
				case OP_DIVISION:
					if(InputLoop.debugLog)
						System.out.println("Dividing");
					if(F.LPT == 'F')
					{
						answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
						Fraction W;
						if(Fracs.size() == 1)
						{
							W = Fracs.get(F.LP+1);
						}
						else
						{
							W = Fracs.get(F.LP);
						}
						W = new Fraction(W.bottom,(W.wNum*W.bottom)+W.top);
						answer.bottom *= W.bottom;
						answer.top = W.top * answer.top;
						answer.Simplify();
					}
					else
					{
						answer = new Fraction((answer.wNum*answer.bottom)+answer.top,answer.bottom);
						Fraction W;
						if(Nums.size() == 1)
						{
							W = new Fraction(1,Nums.get(F.LP+1).N);
						}
						else
						{
							W = new Fraction(1,Nums.get(F.LP).N);
						}
						answer.bottom *= W.bottom;
						answer.top = W.top * answer.top;
						answer.Simplify();
					}
					break;
				default:
					break;
				
			}
		}
		if(InputLoop.debugLog)
			System.out.println("Answer pre-simplification: " + answer.wNum + " " + answer.top + "/" + answer.bottom);
		answer.Simplify();
		
		return answer;
	}
}
