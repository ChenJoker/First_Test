import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;



public class codeTest 
{
	private code obj = new code(new code.card(code.ELEMENT[0],code.ELEMENT[0], code.ELEMENT[1], code.ELEMENT[2]));
	

	@Test
	public void testInsert()
	{
		 /*
		  * To test whether our functions are ok.
		  * We will initial a card on the field , and put it on (0,0) ,
		  * this card composed of four elements , 
		  * like 1 1
		  *      2 3
		  * 1~5 means five elements , players need to put other cards around the first card,
		  * and match elements , such as
		  * 1 1 1 1                                           2 3 1 1
		  * 2 2 2 3  correct! because two cards match 1, but  2 3 2 3  incorrect! because no elements match.  
 		  * so we will test whether players put valid card around first card. 
		  */		
		
			
		 boolean result = false;
		 
		 Vector match = new Vector();
		 
		 /*
		  * Card will be create by card(ELEMENT,ELEMENT,ELEMENT,ELEMENT,r,c)
		  * r,c means coordinate.
		  * ELEMENT[0]=1
		  * ELEMENT[1]=2
		  * ELEMENT[2]=3
		  * ELEMENT[3]=4
		  * ELEMENT[4]=5
		  * TA can add many card to test
		  * follow  match.add(obj.insert(new code.card(code.ELEMENT[x], code.ELEMENT[y], code.ELEMENT[z], code.ELEMENT[k]), r, c));
		  */
		 	 
 		 match.add(obj.insert(new code.card(code.ELEMENT[0], code.ELEMENT[2], code.ELEMENT[1], code.ELEMENT[1]), 1, 0));
 		 match.add(obj.insert(new code.card(code.ELEMENT[3], code.ELEMENT[1], code.ELEMENT[3], code.ELEMENT[1]), 1, -1));
		 
 		 /*
 		  * Test all instances
 		  */
 		 for(int i=0;i<match.size();i++)
 		 {
 			 if((int)match.get(i) > 0)
 				 result=true;
 			 
 			 assertTrue(result);
 			 
 			 result=false;
 		 }
	}



}

