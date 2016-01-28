package Calc.Types;

public class Function {
	public static enum FuncType {
		OP_ADDITION,
		OP_SUBTRACTION,
		OP_MULTIPLICATION,
		OP_EXPONENT,
		OP_DIVISION
		//Rare AF case
	}
	
	public int LP;
	public char LPT;
	public FuncType OP;
	
	public Function(int LastPart, char LastPartType, FuncType OperationType)
	{
		LP = LastPart;
		LPT = LastPartType;
		OP = OperationType;
	}
}
