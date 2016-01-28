package Calc.Types;

public class Fraction {
	public int top;
	public int bottom;
	public int wNum;
	
	public Fraction(int top, int bottom)
	{
		this.top = top;
		this.bottom = bottom;
		wNum = 0;
	}
	
	public Fraction(int top, int bottom, int WholeNum)
	{
		this.top = top;
		this.bottom = bottom;
		wNum = WholeNum;
	}
	
	//We'll make this part later.
	public void Simplify()
	{
		int GCF = 0;
		
		GCF = gcf(top,bottom);
		
		top = top/GCF;
		bottom = bottom/GCF;
		checkCorrect();
	}
	
	public void checkCorrect()
	{
		if(bottom < 0)
		{
			bottom = -bottom;
		}
		if(top < 0 && wNum == 0 && top / -bottom != 0)
		{
			wNum += top/bottom;
			top = -(top%bottom);
		}
		if(top > bottom)
		{
			wNum += top/bottom;
			top = top % bottom;
		}
		if(top == bottom)
		{
			wNum += 1;
			top = 0;
		}
	}
	
	public static int gcf(int a, int b)
	{	
		if(a < 0)
		{
			a = -a;
		}
		if(b < 0)
		{
			b = -b;
		}
		if(a == 0 || b == 0)
		{
			return 1;
		}
	    while (a != b) // while the two numbers are not equal...
	    { 
	        // ...subtract the smaller one from the larger one
	        if (a > b) a -= b; // if a is larger than b, subtract b from a
	        else b -= a; // if b is larger than a, subtract a from b
	    }
	    return a; // or return b, a will be equal to b either way
	}
}