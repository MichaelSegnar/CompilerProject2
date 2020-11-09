import java.io.*;
import java.util.*;

public class SyntaxAnalyzer {

	public static int i;
	public static LexicalAnalyzer.Token tok;
	public static int size;
	public static ArrayList<LexicalAnalyzer.Token> list;
	
	public static void Start(ArrayList<LexicalAnalyzer.Token> check)
	{
		i = 0;
		list = check;
		tok = list.get(i);
		size = list.size();
		P();
	}
	
	public static void nextTok()
	{
		i++;
		if(i < size) {tok = list.get(i);}
		else {tok = new LexicalAnalyzer.Token("E","E","E");}
	}
	
	public static void P()
	{
		if(tok.a.equals("function"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				nextTok();
				if(tok.a.equals("LParen"))
				{
					nextTok();
					IL();
					if(tok.a.equals("RParen"))
					{
						nextTok();
						if(tok.a.equals("Semicolon"))
						{
							nextTok();
							P1();
						}
						else
						{
							System.out.println("ERROR: Expecting ;");
						}
					}
					else
					{
						System.out.println("ERROR: Expecting )");
					}
				}
				else
				{
					System.out.println("ERROR: Expecting (");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword Program");
		}
	}
	
	public static void P1()
	{
		if(tok.a.equals("var"))
		{
			D();
			P2();
		}
		else if(tok.a.equals("function"))
		{
			SDS();
			CS();
		}
		else if(tok.a.equals("begin"))
		{
			CS();
		}
		else
		{
			System.out.println("ERROR: Expecting Keywords var, function, or begin");
		}
	}
	
	public static void P2()
	{
		if(tok.a.equals("function"))
		{
			SDS();
			CS();
		}
		else if(tok.a.equals("begin"))
		{
			CS();
		}
		else
		{
			System.out.println("ERROR: Expecting Keywords function or begin");
		}
	}
	
	public static void IL()
	{
		if(LexicalAnalyzer.locate(tok.a))
		{
			nextTok();
			IL1();
		}
		else
		{
			System.out.println("ERROR: Expecting ID");
		}
	}
	
	public static void IL1()
	{
		if(LexicalAnalyzer.locate(tok.a))
		{
			nextTok();
			IL1();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting ID or Epsilon");
		}
	}
	
	public static void D()
	{
		if(tok.a.equals("var"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				nextTok();
				if(tok.a.equals("Colon"))
				{
					nextTok();
					T();
					if(tok.a.equals("Semicolon"))
					{
						D1();
					}
					else
					{
						System.out.println("ERROR: Expecting ;");
					}
				}
				else
				{
					System.out.println("ERROR: Expecting :");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword var");
		}
	}
	
	public static void D1()
	{
		if(tok.a.equals("var"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				nextTok();
				if(tok.a.equals("Colon"))
				{
					nextTok();
					T();
					if(tok.a.equals("Semicolon"))
					{
						D1();
					}
					else
					{
						System.out.println("ERROR: Expecting ;");
					}
				}
				else
				{
					System.out.println("ERROR: Expecting :");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting Epsilon or Keyword var");
		}
	}
	
	public static void T()
	{
		if(tok.a.equals("NUM"))
		{
			ST();
		}
		else if(tok.a.equals("array"))
		{
			nextTok();
			if(tok.a.equals("LBrack"))
			{
				nextTok();
				if(tok.a.equals("NUM"))
				{
					nextTok();
					while(tok.a.equals("NUM")) { nextTok(); }
					if(tok.a.equals("RBrack"))
					{
						nextTok();
						if(tok.a.equals("of"))
						{
							nextTok();
							ST();
						}
						else
						{
							System.out.println("ERROR: Expecting Keyword of");
						}
					}
					else
					{
						System.out.println("ERROR: Expecting ]");
					}
				}
				else
				{
					System.out.println("ERROR: Expecting Number");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting [");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting Number or Keyword array");
		}
	}
	
	public static void ST()
	{
		if(tok.b.equals("INT")||tok.b.equals("REAL"))
		{
			nextTok();
		}
		else
		{
			System.out.println("ERROR: Expecting Integer or Real Number");
		}
	}
	
	public static void SDS()
	{
		SD();
		if(tok.a.equals("Semicolon"))
		{
			nextTok();
			SDS1();
		}
		else
		{
			System.out.println("ERROR: Expecting ;");
		}
	}
	
	public static void SDS1()
	{
		if(tok.a.equals("function"))
		{
			SD();
			if(tok.a.equals("Semicolon"))
			{
				nextTok();
				SDS1();
			}
			else
			{
				System.out.println("ERROR: Expecting ;");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword function");
		}
	}
	
	public static void SD()
	{
		if(tok.a.equals("function"))
		{
			SH();
			SD1();
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword function");
		}
	}
	
	public static void SD1()
	{
		if(tok.a.equals("var"))
		{
			D(); SD2();
		}
		else if(tok.a.equals("function"))
		{
			SDS(); CS();
		}
		else if(tok.a.equals("begin"))
		{
			CS();
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword var, function, or begin");
		}
	}
	
	public static void SD2()
	{
		if(tok.a.equals("function"))
		{
			SDS(); CS();
		}
		else if(tok.a.equals("begin"))
		{
			CS();
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword function or begin");
		}
	}
	
	public static void SH()
	{
		if(tok.a.equals("function"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				nextTok();
				SH1();
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword function");
		}
	}
	
	public static void SH1()
	{
		if(tok.a.equals("LParen"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				PL();
				if(tok.a.equals("RParen"))
				{
					nextTok();
					if(tok.a.equals("Colon"))
					{
						nextTok();
						ST();
						if(tok.a.equals("Semicolon"))
						{
							nextTok();
						}
						else
						{
							System.out.println("ERROR: Expecting ;");
						}
					}
					else
					{
						System.out.println("ERROR: Expecting :");
					}
				}
				else
				{
					System.out.println("ERROR: Expecting )");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else if(tok.a.equals("Colon"))
		{
			nextTok();
			ST();
			if(tok.a.equals("Semicolon"))
			{
				nextTok();
			}
			else
			{
				System.out.println("ERROR: Expecting ;");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting ( or :");
		}
	}
	
	public static void PL()
	{
		if(LexicalAnalyzer.locate(tok.a))
		{
			nextTok();
			if(tok.a.equals("Colon"))
			{
				nextTok();
				T();
				PL1();
			}
			else
			{
				System.out.println("ERROR: Expecting :");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting ID");
		}
	}
	
	public static void PL1()
	{
		if(tok.a.equals("Semicolon"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a))
			{
				nextTok();
				if(tok.a.equals("Colon"))
				{
					nextTok();
					T();
					PL1();
				}
				else
				{
					System.out.println("ERROR: Expecting :");
				}
			}
			else
			{
				System.out.println("ERROR: Expecting ID");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting ; or Epsilon");
		}
	}
	
	public static void CS()
	{
		if(tok.a.equals("begin"))
		{
			nextTok();
			CS1();
		}
		else
		{
			System.out.println("ERROR: Expecting Keyword begin");
		}
	}
	
	public static void CS1()
	{
		if(tok.a.equals("end"))
		{
			nextTok();
		}
		else if(LexicalAnalyzer.locate(tok.a)||tok.a.equals("begin")||tok.a.equals("if")||tok.a.equals("while"))
		{
			SL();
		}
		else
		{
			System.out.println("ERROR: Expecting ID or Keyword begin, end, if, or while");
		}
	}
	
	public static void SL()
	{
		if(LexicalAnalyzer.locate(tok.a)||tok.a.equals("begin")||tok.a.equals("if")||tok.a.equals("while"))
		{
			S();
			SL1();
		}
		else
		{
			System.out.println("ERROR: Expecting ID or Keyword begin, if, or while");
		}
	}
	
	public static void SL1()
	{
		if(tok.a.equals("Semicolon"))
		{
			nextTok();
			if(LexicalAnalyzer.locate(tok.a)||tok.a.equals("begin")||tok.a.equals("if")||tok.a.equals("while"))
			{
				S();
				SL1();
			}
			else
			{
				System.out.println("ERROR: Expecting ID or Keyword begin, if, or while");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting ; or Epsilon");
		}
	}
	
	public static void S()
	{
		if(LexicalAnalyzer.locate(tok.a))
		{
			V();
			if(tok.a.equals("AssignOP"))
			{
				nextTok();
				E();
			}
			else
			{
				System.out.println("ERROR: Expecting AssignOP");
			}
		}
		else if(tok.a.equals("begin"))
		{
			CS();
		}
		else if(tok.a.equals("if"))
		{
			nextTok();
			E();
			if(tok.a.equals("then"))
			{
				nextTok();
				S();
				S1();
			}
			else
			{
				System.out.println("ERROR: Expecting Keyword then");
			}
		}
		else if(tok.a.equals("while"))
		{
			nextTok();
			E();
			if(tok.a.equals("do"))
			{
				nextTok();
				S();
			}
			else
			{
				System.out.println("ERROR: Expecting Keyword do");
			}
		}
		else
		{
			System.out.println("ERROR: Expecting ID or Keyword begin, if, or while");
		}
	}
	
	public static void S1()
	{
		if(tok.a.equals("else"))
		{
			nextTok();
			S();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting Epsilon or Keyword else");
		}
	}
	
	public static void V()
	{
		if(LexicalAnalyzer.locate(tok.a))
		{
			nextTok();
			V1();
		}
		else
		{
			System.out.println("ERROR: Expecting ID");
		}
	}
	
	public static void V1()
	{
		if(tok.a.equals("LBrack"))
		{
			nextTok();
			E();
			if(tok.a.equals("RBrack"))
			{
				nextTok();
			}
			else
			{
				System.out.println("ERROR: Expecting ]");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting [ or Epsilon");
		}
	}
	
	public static void EL()
	{
		E();
		EL1();
	}
	
	public static void EL1()
	{
		if(tok.a.equals("Comma"))
		{
			nextTok();
			E();
			EL1();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting , or Epsilon");
		}
	}
	
	public static void E()
	{
		SE();
		E1();
	}
	
	public static void E1()
	{
		if(tok.a.equals("relop"))
		{
			nextTok();
			SE();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting relop or Epsilon");
		}
	}
	
	public static void SE()
	{
		if(tok.a.equals("AddOP"))
		{
			Si();
		}
		if(tok.a.equals("NUM")||tok.a.equals("not")||tok.a.equals("LParen")||LexicalAnalyzer.locate(tok.a))
		{
			Te();
			SE1();
		}
		else
		{
			System.out.println("ERROR: Expecting Number, (, ID, orKeyword not");
		}
	}
	
	public static void SE1()
	{
		if(tok.a.equals("AddOP"))
		{
			nextTok();
			Te();
			SE1();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting AddOP");
		}
	}
	
	public static void Te()
	{
		if(tok.a.equals("NUM")||tok.a.equals("not")||tok.a.equals("LParen")||LexicalAnalyzer.locate(tok.a))
		{
			F();
			Te1();
		}
		else
		{
			System.out.println("ERROR: Expecting Number, (, ID, orKeyword not");
		}
	}
	
	public static void Te1()
	{
		if(tok.a.equals("MulOP"))
		{
			nextTok();
			Te1();
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting MulOP or Epsilon");
		}
	}
	
	public static void F()
	{
		if(tok.a.equals("NUM"))
		{
			nextTok();
		}
		else if(LexicalAnalyzer.locate(tok.a))
		{
			nextTok();
			F1();
		}
		else if(tok.a.equals("LParen"))
		{
			nextTok();
			E();
			if(tok.a.equals("RParen"))
			{
				nextTok();
			}
			else
			{
				System.out.println("ERROR: Expecting )");
			}
		}
		else if(tok.a.equals("not"))
		{
			nextTok();
			F();
		}
	}
	
	public static void F1()
	{
		if(tok.a.equals("LParen"))
		{
			nextTok();
			EL();
			if(tok.a.equals("RParen"))
			{
				nextTok();
			}
			else
			{
				System.out.println("ERROR: Expecting )");
			}
		}
		else if(tok.a.equals("LBrack"))
		{
			nextTok();
			E();
			if(tok.a.equals("RBrack"))
			{
				nextTok();
			}
			else
			{
				System.out.println("ERROR: Expecting ]");
			}
		}
		else if(tok.a.equals("E"))
		{
			return;
		}
		else
		{
			System.out.println("ERROR: Expecting (, [, or Epsilon");
		}
	}
	
	public static void Si()
	{
		if(tok.b.equals("PLUS")||tok.b.equals("MINUS"))
		{
			nextTok();
		}
		else
		{
			System.out.println("ERROR: Expecting + or -");
		}
	}
}
