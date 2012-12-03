import java.io.*;
import java.util.Arrays;
/*
 * This class is to produce the card , and put it on the field ,
 *
 * ELEMENT means five elements .
 *
 * Card class means a card composed of four grids , it will use a 2*2 array to do it.
 *
 * Field means a place to put cards , because we will have 56 cards ,
 * so we use 56*56 array and row , col to record the total size of cards' size,
 *
 * Insert function output cards' location and amount around them , output 0 means
 * insert is invalid , output -1 means this card is inserted on the leftmost side or top side.
 *
 * Main will use six cards to text.
 */
/*
 * change match function to let program has correct match
 */
public class code
{
	/*
	 * Code class is to produce a card and table.
	 */

	//ELEMENT array mean five elements.
	 public static final byte[] ELEMENT=new byte[]{1, 2, 3, 4, 5};
	 public static final byte GENERAL_ELEMENT=6;


	 public static class card implements Cloneable, Comparable
	 {
		 /*
		 * card class means component of a card.
		 */
		 private byte[] element;
		 
		 public card()
		 {
			 element=null; // Null array represents general cards.
		 }
		 
		 public card(byte e0, byte e1, byte e2, byte e3)
		 {
				element=new byte[]{e0, e1, e2, e3};
		 }
		 
		 public card clone()
		 {
			 if(element==null) 
				 return new card();
			 else 
				 return new card(element[0], element[1], element[2], element[3]);
		 }
		 
		 public int compareTo(Object another)
		 {
			if(another instanceof card)
			{
				card c=(card)another;

				if(element==null || c.element==null)
				{	
					if(element==null && c.element==null) 
						return 0;
					else 
						return 1;
				}
				else if(upleft()==c.upleft() && upright()==c.upright() && downleft()==c.downleft() && downright()==c.downright())
					return 0;

				else if(upleft()==c.downright() && upright()==c.downleft() && downleft()==c.upright() && downright()==c.upleft())
					return -1;

				else 
					return 1;
			}
			
			else 
				return 1;
		 }
		 
		 public byte upleft()
		 {
                if(element!=null) 
                	return element[0];
                else 
                	return GENERAL_ELEMENT;
		 }

		 public byte upright()
		 {
                        if(element!=null) 
                        	return element[1];
                        else 
                        	return GENERAL_ELEMENT;
		 }

		 public byte downleft()
		 {
                         if(element!=null) 
                        	 return element[2];
                         else
                        	 return GENERAL_ELEMENT;
		 }

		 public byte downright()
		 {
                        if(element!=null)
                        	return element[3];
                        else
                        	return GENERAL_ELEMENT;
		 }
		 
		 public boolean general()
		 {	 
			 return element==null;
		 }
		 
		 public void rotate()// A rotation by 180 degrees.
		 {
			 /*
			  * card can be rotated , this function is to do it.
			  */
			 if(!general())
			 {
				 byte tmp=element[0];
				 element[0]=element[2];
				 element[2]=tmp;
				 tmp=element[1];
				 element[1]=element[3];
				 element[3]=tmp;
			 }
		 }
	 }

	 private card[][] field; //field's array

	 private int row, col; // Current field sizes row*col.

	 private card rm_card; // Previously removed card.

	 private int rm_row, rm_col; // Position of removed card after adjustment.

	 public code(card c)
	 {
		 /*
		  * The game begins with an initial card.
		  */
		 field=new card[56][];
		 field[0]=new card[56];
		 field[0][0]=c;
		 row=col=1;
		 rm_card=null;
		 rm_row=rm_col=0;
	 }

	 private void clear(int r, int c)
	 {
		 rm_card=field[rm_row=r][rm_col=c].clone();

		 field[r][c]=null;
	 }
	 private int count(final Object[] m)
	 {
		 Object[] t=new Object[m.length];
		 int result=0;
		 for(int i=0; i<m.length; i++)
		 {
			 if(Arrays.asList(t).indexOf(m[i])<0) t[result++]=m[i];
		 }
		 return result;
	 }
	 public int insert(card n, int r, int c)
	 {
		 /*
	  * To insert card on the field.
		  */

		 int match=0; //means whether the card can be put , because different elements can's be put together.
		 Object[] matching_element=new Object[4];
		 
		 if(rm_card!=null)
		 {
			 switch(rm_card.compareTo(n))
			 {
			 	case 1: // Different cards.
			 		return 0;

			 	case 0: // The same card without rotation.
			 		if(r==rm_row && c==rm_col)
 			return 0;
			 		break;

			 	case -1: // The same card with rotation.
			 		break;
			 }
		 }
		 if(r<0)
		 {
			 if(c<0)
				 return 0; // It is impossible that both r and c are negative.
			 if(field[0][c]!=null)
			 {
				 if(n.general()) 
					 match=1;
				 else
				 {
					 if(n.downleft()==field[0][c].upleft())
						 matching_element[2]=n.downleft();

					 if(n.downright()==field[0][c].upright())
						 matching_element[3]=n.downright();

					 match=count(matching_element);
				 }
				 if(match>0)// At least one adjacent matching element.
				 {
					 for(int i=field.length-2; i>0; i--)
						 field[i]=field[i-1];
					 field[0]=new card[56];
					 field[0][c]=n;
					 row++;
				 }
			 }
		 }

		 else if(c<0)// Only c is less than 0.
		 {
			 if(field[r][0]!=null)
			 {
				 if(n.general()) 
					 match=1;
				 else
				 {
					 if(n.upright()==field[r][0].upleft())
						 matching_element[1]=n.upright();

					 if(n.downright()==field[r][0].downleft())
						 matching_element[3]=n.downright();

					 match=count(matching_element);
				 }
				 if(match>0)// At least one adjacent matching element.
				 {
					 for(int i=0; i<row; i++)
					 {
						 for(int j=field[i].length-2; j>0; j--)
							 field[i][j]=field[i][j-1];
						 field[i][0]=null;
					 }
					 field[r][0]=n;
					 col++;
				 }
			 }
		 }
		 else if(r>=row)
		 {
			 if(c>=col)
				 return 0; // It is impossible that both r and c are out of bound.
			 if(field[row-1][c]!=null)
			 {
				 if(n.general())
					 match=1;
				 else
				 {
					 if(n.upleft()==field[row-1][c].downleft())
						 matching_element[0]=n.upleft();

					 if(n.upright()==field[row-1][c].downright())
						 matching_element[1]=n.upright();

					 match=count(matching_element);
				 }
				 if(match>0) // At least one adjacent matching element.
				 {
					 field[row]=new card[56];
					 field[row][c]=n;
					 row++;
				 }
			 }
		 }
		 else if(c>=col) // Only c is less than 0.
		 {
			 if(field[r][col-1]!=null)
			 {
				 if(n.general()) match=1;
				 else
				 {
					 if(n.upleft()==field[r][col-1].upright())
						 matching_element[0]=n.upleft();
					 if(n.downleft()==field[r][col-1].downright())
						 matching_element[2]=n.downleft();

					 match=count(matching_element);
				 }
				 if(match>0) // At least one adjacent matching element.
				 {
					 field[r][col]=n;
					 col++;
				 }
			 }
		 }
		 else
		 {
			 if(field[r][c]==null)
			 {
				 if(r>0 && field[r-1][c]!=null)
				 {
					 if(n.general()) match=1;
					 else
					 {
						 if(n.upleft()==field[r-1][c].downleft()) 
							 matching_element[0]=n.upleft();
						 if(n.upright()==field[r-1][c].downright())
							 matching_element[1]=n.upright();
					 }
				 }
				 
				 if(c>0 && field[r][c-1]!=null)
				 {
					 if(n.general()) 
						 match=1;
					 else
					 {
						 if(n.upleft()==field[r][c-1].upright()) 
							 matching_element[0]=n.upleft();
						 if(n.downleft()==field[r][c-1].downright()) 
							 matching_element[2]=n.downleft();
					 }
				 }
				 
				 if(r<row-1 && field[r+1][c]!=null)
				 {
					 if(n.general()) 
						 match=1;
					 else
					 {

						 if(n.downleft()==field[r+1][c].upleft()) 
							 matching_element[2]=n.downleft();
						 if(n.downright()==field[r+1][c].upright()) 
							 matching_element[3]=n.downright();
					 }
				 }
				 
				 if(c<col-1 && field[r][c+1]!=null)
				 {
					 if(n.general()) 
						 match=1;
					 else
					 {

						 if(n.upright()==field[r][c+1].upleft())
							 matching_element[1]=n.upright();
						 if(n.downright()==field[r][c+1].downleft()) 
							 matching_element[3]=n.downright();
					 }
				 }
				 if(!n.general()) 
					 match=count(matching_element);
				 
				 if(match>0)
					 field[r][c]=n;
			 }
		 }
		 
		 if(match>0)
		 { // Successful insertion.
			 if(rm_card!=null)
			 {
				 rm_card=null;
				 rm_row=rm_col=0;
			 }
		 }
		  return match;
	 }

	 public card remove(int r, int c)
	 {
		 if(r<0 || c<0 || r>=row || c>=col)
			 return null;

		 else if(field[r][c]==null)
			 return rm_card;

		 clear(r, c);

		 if(r==0)
		 { 	// Target card is in the top row.
			 if(0<c && c<col-1 && field[r][c-1]==null && field[r][c+1]==null)
			 {
				 boolean shrink=true;

				 for(int i=0; shrink && i<col; i++)
					 if(field[r][i]!=null)
						 shrink=false;

				 if(shrink)
				 {
					 row--;
					 for(int i=0; i<row; i++)
						 field[i]=field[i+1];
					 field[row]=null;
					 rm_row--;
				 }
			 }
		 }

		 else if(c==0)
		 { // Target card is in the leftmost column.
			 if(0<r && r<row-1 && field[r-1][c]==null && field[r+1][c]==null)
			 {
				 boolean shrink=true;

				 for(int i=0; shrink && i<row; i++)
					 if(field[i][c]!=null)
						 shrink=false;
				 if(shrink)
				 {
					 col--;
					 for(int i=0; i<row; i++)
					 {
						 for(int j=0; j<col; j++)
							 field[i][j]=field[i][j+1];
						 field[i][col]=null;
					 }
					 rm_col--;
				 }
			 }
		 }

		 else if(r==row-1)
		 { // Target card is in the bottom row.
			 if(0<c && c<col-1 && field[r][c-1]==null && field[r][c+1]==null)
			 {
				 boolean shrink=true;

				 for(int i=0; shrink && i<col; i++)
					 if(field[r][i]!=null)
						 shrink=false;

				 if(shrink) field[--row]=null;
			 }
	 }
		 else if(c==col-1)
		 { // Target card is in the rightmost column.
			 if(0<r && r<row-1 && field[r-1][c]==null && field[r+1][c]==null)
			 {
				 boolean shrink=true;

				 for(int i=0; shrink && i<row; i++)
					 if(field[i][c]!=null)
						 shrink=false;

				 if(shrink)
				 {
					 col--;
					 for(int i=0; i<row; i++)
						 field[i][col]=null;
				 }
			 }
		 }
		 return rm_card;
 	}

	 public boolean undo()
	 {
		 if(rm_card==null)
			 return false;

		 rm_card=null;

		 return insert(rm_card, rm_row, rm_col)>0;
	 }
	 
	 public void print()
	 {
		 /*
		  * To print cards' states on the field.
		  */
		 byte[][] element=null;

		 for(int i=0; i<row; i++)
		 {
			 element=new byte[2][112];

			 for(int j=0; j<col; j++)
			 {
				 if(field[i][j]!=null)
				 {
					 element[0][2*j]=field[i][j].upleft();
					 element[0][2*j+1]=field[i][j].upright();
					 element[1][2*j]=field[i][j].downleft();
					 element[1][2*j+1]=field[i][j].downright();
				 }
			 }
			 for(int j=0; j<2; j++)
			 {
				 for(int k=0; k<2*col; k++)
				 {
					 if(element[j][k]==0)
						 System.out.print("  ");
					 else
						 System.out.printf("%d ", element[j][k]);
				 }
				 System.out.println("");
			 }
		 }
	 }

	 public static void main(String[] args)
	 {
         /*
          * Put cards.
          */
		 code o=new code(new card(ELEMENT[0], ELEMENT[0], ELEMENT[1], ELEMENT[2]));
		 code.card tmp_c=null;
		 o.insert(new card(ELEMENT[3], ELEMENT[2], ELEMENT[0], ELEMENT[2]), -1, 0);
		 o.insert(new card(ELEMENT[3], ELEMENT[1], ELEMENT[3], ELEMENT[1]), 1, -1);
		 o.insert(new card(ELEMENT[0], ELEMENT[0], ELEMENT[0], ELEMENT[0]), 0, 0);
		 o.insert(new card(ELEMENT[2], ELEMENT[4], ELEMENT[1], ELEMENT[0]), 0, 2);
		 o.insert(new card(ELEMENT[0], ELEMENT[2], ELEMENT[4], ELEMENT[1]), 2, 1);
		 tmp_c=o.remove(0, 2);
		 o.insert(tmp_c, 2, 2);
		 o.insert(new card(ELEMENT[4], ELEMENT[3], ELEMENT[4], ELEMENT[2]), 2, 3);
		 o.insert(new card(), 1, 3);
		 o.print();


	 }
}