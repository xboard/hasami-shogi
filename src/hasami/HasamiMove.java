/**
 Java implementation of a computer engine to play the 
 Hasami Shogi game. See: http://en.wikipedia.org/wiki/Hasami_shogi

 Copyright (C) 2010 Flavio Regis de Arruda <https://bitbucket.org/flavio_regis>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hasami;

public class HasamiMove {

	public int startLine;
	public int startCol;
	public int endLine;
	public int endCol;
	public double val;
	
	public HasamiMove(HasamiMove move, double val){
		if(move!=null){
			this.startLine =  move.startLine;
			this.startCol  =  move.startCol;
			this.endLine   =  move.endLine;
			this.endCol	   =  move.endCol;
		}
		this.val	   =  val;
	}
	
	public HasamiMove(int startLine,
					  int startCol,
					  int endLine,
					  int endCol){
		this.startLine =  startLine;
		this.startCol  =  startCol;
		this.endLine   =  endLine;
		this.endCol	   =  endCol;
		
	}
	
	public HasamiMove(int startLine,
			  int startCol,
			  int endLine,
			  int endCol,
			  double val){
		this.startLine =  startLine;
		this.startCol  =  startCol;
		this.endLine   =  endLine;
		this.endCol	   =  endCol;
		this.val 	   =  val;
	}
	
	//@Override
	//public String toString(){
		//return "("+startLine+","+startCol+")->("+endLine+","+endCol+")";
	//}
	
	@Override
	public String toString(){
		return " "+getCol(startCol)+""+getLine(startLine)+"-"+getCol(endCol)+""+getLine(endLine)+" ";
	}
	private char getLine(int l){
		return (char)('1'+l);
	}
	private char getCol(int c){
		return (char)('A'+c);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof HasamiMove)) {
			return false;
		}
		HasamiMove hm = (HasamiMove) obj;
		return hm.hashCode()==this.hashCode();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}	
}//end of class.
