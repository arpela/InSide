package uy.com.s4b.inside.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uy.com.s4b.inside.core.common.DiffLine;
import uy.com.s4b.inside.core.exception.InSideException;


/** This is the info kept per-string.     */
class stringInfo {    

  static final int MAXLINECOUNT = 30000;
  
  public int maxLine;  	/* After input done, # lines in file.  */
  node symbol[]; 		/* The symtab handle of each line. */
  int other[]; 			/* Map of line# to line# in other string */
               			/* ( -1 means don't-know ).            */
        	   			/* Allocated AFTER the lines are read. */

  
  stringInfo( String filename ) {
    symbol = new node [ MAXLINECOUNT+2 ];
    other  = null;    // allocated later!
  }
  // This is done late, to be same size as # lines in input string.
  void alloc() {
    other  = new int[symbol.length + 2];
  }
};

/**
 * Title: Diff.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

public class Diff {

  /** block len > any possible real block len */
  final int UNREAL=Integer.MAX_VALUE;

  /** Keeps track of information about string1 and string2 */
  stringInfo oldinfo, newinfo;
  
  /** Keeps track of information about diff between string1 and string2 */
  Map<Integer,DiffLine> diffLine = new HashMap<Integer,DiffLine>();

  /** blocklen is the info about found blocks. It will be set to 0, except
   * at the line#s where blocks start in the old file. At these places it
   * will be set to the # of lines in the block. During printout ,
   * this # will be reset to -1 if the block is printed as a MOVE block
   * (because the printout phase will encounter the block twice, but
   * must only print it once.)
   * The array declarations are to MAXLINECOUNT+2 so that we can have two
   * extra lines (pseudolines) at line# 0 and line# MAXLINECOUNT+1
   * (or less).
   */
  int blocklen[];

  /**
   * main - entry point when used standalone.
   * NOTE: no routines return error codes or throw any local
   * exceptions. Instead, any routine may complain
   * to stderr and then exit with error to the system.
 * @throws InSideException 
   */
  public static void main(String argstrings[]) 
  {
    String multiLine1 = "L1\nL2\nL3\nL4\nL5";
    String multiLine2 = "L1\nL2\nL3\nL4\nL6";
    
    String multiLine3 = "L11\nL22\nL33\nL44";
    String multiLine4 = "L11\nL21\nL33\nL44\nL55";
    
    Diff d = new Diff();
    try {
    	  Map<Integer,DiffLine> resDiffLine = d.doDiff(multiLine1, multiLine2);
    	  System.out.println("tamaño"+resDiffLine.size());
    	  
    	  switch (resDiffLine.get(5)) {
			case CHANGE: System.out.println("CHANGE");
					     break;
					
			case DELETE: System.out.println("DELETE");
					     break;
					     
			case NEW: System.out.println("NEW");
					     break;
			
			case MOVE: System.out.println("MOVE");
		     			break;
		     
			default:	 System.out.println("ERROR");
					     break;
    	  }
    	 
    	  
    	  Map<Integer,DiffLine> resDiffLine2 = d.doDiff(multiLine3, multiLine4);
    	  System.out.println("tamaño"+resDiffLine2.size());
    	  
    	  switch (resDiffLine2.get(5)) {
			case CHANGE: System.out.println("CHANGE");
					     break;
					
			case DELETE: System.out.println("DELETE");
					     break;
					     
			case NEW: System.out.println("NEW");
					     break;
			
			case MOVE: System.out.println("MOVE");
		     			break;
		     
			default:	 System.out.println("ERROR");
					     break;
    	  }
    	  
    	 
	} catch (InSideException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return;
  }

  Diff() {
  }

  /** Do one file comparison. Called with both filenames. */
  public Map<Integer,DiffLine> doDiff(String oldString, String newString) throws InSideException {
	  System.out.println("tamaño diff"+diffLine.size());
	  diffLine = new HashMap<Integer,DiffLine>();
	  System.out.println("tamaño diff 2 "+diffLine.size());
	oldinfo = new stringInfo(oldString);
    newinfo = new stringInfo(newString);
      
    inputscan( oldinfo, oldString );
    inputscan( newinfo, newString );
   
    /* Now that we've read all the lines, allocate some arrays.*/
    blocklen = new int[ (oldinfo.maxLine>newinfo.maxLine?oldinfo.maxLine : newinfo.maxLine) + 2 ];
    oldinfo.alloc();
    newinfo.alloc();

    /* Now do the work, and print the results. */
    transform();
    printout();
    
    return diffLine;
  }

  /**
   * inputscan    Reads the string specified by pinfo.file.
   * ---------    Places the lines of that string in the symbol table.
   *              Sets pinfo.maxLine to the number of lines found.
   */
  void inputscan( stringInfo pinfo,  String multiLine  ) throws InSideException {
	  pinfo.maxLine = 0;
	  String linebuffer[] = multiLine.split("\n");
	  
	  int i=0;
	  while(i<linebuffer.length){  
		  storeline( linebuffer[i], pinfo );
		  i++;  
	  }
  }

  /**
   * storeline    Places line into symbol table.
   * ---------    Expects pinfo.maxLine initted: increments.
   *              Places symbol table handle in pinfo.ymbol.
   *              Expects pinfo is either oldinfo or newinfo.
   */
  void storeline( String linebuffer, stringInfo pinfo ) throws InSideException {
	  int linenum = ++pinfo.maxLine;    /* note, no line zero */
      if ( linenum > stringInfo.MAXLINECOUNT ) {
    	  Exception ex = new Exception("MAXLINECOUNT exceeded");
    	  throw new InSideException("reporteDiff.error.maxline", ex.getMessage(), ex);
      }
      pinfo.symbol[ linenum ] = node.addSymbol( linebuffer, pinfo == oldinfo, linenum );
  }

  /*
   * transform    
   * Analyzes the file differences and leaves its findings in
   * the global arrays oldinfo.other, newinfo.other, and blocklen.
   * Expects both files in symtab.
   * Expects valid "maxLine" and "symbol" in oldinfo and newinfo.
   */
  void transform() {                                  
       int oldline, newline;
       int oldmax = oldinfo.maxLine + 2;  /* Count pseudolines at  */
       int newmax = newinfo.maxLine + 2;  /* ..front and rear of file */

       for (oldline=0; oldline < oldmax; oldline++ )
    	   oldinfo.other[oldline]= -1;
       for (newline=0; newline < newmax; newline++ )
    	   newinfo.other[newline]= -1;

       scanunique();  /* scan for lines used once in both files */
       scanafter();   /* scan past sure-matches for non-unique blocks */
       scanbefore();  /* scan backwards from sure-matches */
       scanblocks();  /* find the fronts and lengths of blocks */
  }

  /*
   * scanunique
   * Scans for lines which are used exactly once in each string.
   * Expects both files in symtab, and oldinfo and newinfo valid.
   * The appropriate "other" array entries are set to the line# in
   * the other string.
   * Claims pseudo-lines at 0 and XXXinfo.maxLine+1 are unique.
   */
  void scanunique() {
	  int oldline, newline;
      node psymbol;

      for( newline = 1; newline <= newinfo.maxLine; newline++ ) {
    	  psymbol = newinfo.symbol[ newline ];
    	  if ( psymbol.symbolIsUnique()) {        // 1 use in each file
    		  oldline = psymbol.linenum;
    		  newinfo.other[ newline ] = oldline; // record 1-1 map
    		  oldinfo.other[ oldline ] = newline;
    	  }
       }
       newinfo.other[ 0 ] = 0;
       oldinfo.other[ 0 ] = 0;
       newinfo.other[ newinfo.maxLine + 1 ] = oldinfo.maxLine + 1;
       oldinfo.other[ oldinfo.maxLine + 1 ] = newinfo.maxLine + 1;
  }

  /*
   * scanafter
   * Expects both files in symtab, and oldinfo and newinfo valid.
   * Expects the "other" arrays contain positive #s to indicate
   * lines that are unique in both files.
   * For each such pair of places, scans past in each file.
   * Contiguous groups of lines that match non-uniquely are
   * taken to be good-enough matches, and so marked in "other".
   * Assumes each other[0] is 0.
   */
  void scanafter() {
      int oldline, newline;

      for( newline = 0; newline <= newinfo.maxLine; newline++ ) {
    	  oldline = newinfo.other[ newline ];
	      if ( oldline >= 0 ) {  /* is unique in old & new */
	          for(;;) {  /* scan after there in both files */
	        	  if ( ++oldline > oldinfo.maxLine   ) break; 
	        	  if ( oldinfo.other[ oldline ] >= 0 ) break;
	        	  if ( ++newline > newinfo.maxLine   ) break; 
	        	  if ( newinfo.other[ newline ] >= 0 ) break;
	
	        	  /* oldline & newline exist, and 
	        		aren't already matched */
	
	        	  if ( newinfo.symbol[ newline ] != oldinfo.symbol[ oldline ] ) break;  // not same
	
	        	  newinfo.other[newline] = oldline; // record a match
	        	  oldinfo.other[oldline] = newline;
	           }
	      }
     }
  }

  /**
   * scanbefore
   * As scanafter, except scans towards string fronts.
   * Assumes the off-end lines have been marked as a match.
   */
  void scanbefore() {
       int oldline, newline;

       for( newline = newinfo.maxLine + 1; newline > 0; newline-- ) {
    	  oldline = newinfo.other[ newline ];
	      if ( oldline >= 0 ) {               /* unique in each */
	           for(;;) {
		          if ( --oldline <= 0                ) break;
		          if ( oldinfo.other[ oldline ] >= 0 ) break;
		          if ( --newline <= 0                ) break;
		          if ( newinfo.other[ newline ] >= 0 ) break;
		     
		          /* oldline and newline exist,
		        	and aren't marked yet */
		
		          if ( newinfo.symbol[ newline ] != oldinfo.symbol[ oldline ] ) break;  // not same
		
		          newinfo.other[newline] = oldline; // record a match
		          oldinfo.other[oldline] = newline;
	           }
	      }
       }
  }

  /**
   * scanblocks - Finds the beginnings and lengths of blocks of matches.
   * Sets the blocklen array (see definition).
   * Expects oldinfo valid.
   */
  void scanblocks()  {
       int oldline, newline;
       int oldfront = 0;      // line# of front of a block in old, or 0 
       int newlast = -1;      // newline's value during prev. iteration

       for( oldline = 1; oldline <= oldinfo.maxLine; oldline++ )
           blocklen[ oldline ] = 0;
       blocklen[ oldinfo.maxLine + 1 ] = UNREAL; // starts a mythical blk

       for( oldline = 1; oldline <= oldinfo.maxLine; oldline++ ) {
	      newline = oldinfo.other[ oldline ];
	      if ( newline < 0 ) oldfront = 0;         /* no match: not in block */
	      else {                                   /* match. */
	           if ( oldfront == 0 )         oldfront = oldline;
	           if ( newline != (newlast+1)) oldfront = oldline;
	           ++blocklen[ oldfront ];            
	      }
	      newlast = newline;
       }
  }

  /* The following are global to printout's subsidiary routines */
  // enum{ idle, delete, insert, movenew, moveold,
  // same, change } printstatus;
  public static final int
    idle = 0, delete = 1, insert = 2, movenew = 3, moveold = 4,
    same = 5, change = 6;
  int printstatus;
  boolean anyprinted;
  int printoldline, printnewline;     // line numbers in old & new file

  /**
   * printout - Prints summary to stdout.
   * Expects all data structures have been filled out.
   */
  void printout() {
       printstatus = idle;
       anyprinted = false;
       for( printoldline = printnewline = 1; ; ) {
	      if ( printoldline > oldinfo.maxLine ) { newconsume(); break;}
	      if ( printnewline > newinfo.maxLine ) { oldconsume(); break;}
	      if ( newinfo.other[ printnewline ] < 0 ) {
	           if ( oldinfo.other[ printoldline ] < 0 )
	        	   showchange();
	           else
	        	   showinsert();
	      }
	      else if ( oldinfo.other[ printoldline ] < 0 )
	    	  showdelete();
	      else if ( blocklen[ printoldline ] < 0 )
	    	  skipold();
	      else if ( oldinfo.other[ printoldline ] == printnewline )
	    	  showsame();
	      else
	    	  showmove();
       }
       /*if ( anyprinted == true ) 
    	   println( ">>>> Fin de las diferencias."  );
       else                     
    	   println( ">>>> No se encontraron diferencias." );*/
  }

  /*
   * newconsume Part of printout. Have run out of old string. 
   * Print the rest of the new file, as inserts and/or moves.
   */
  void newconsume() {
      for(;;) {
	      if ( printnewline > newinfo.maxLine )
	    	  break;        /* end of file */
	      if (newinfo.other[ printnewline ] < 0 ) 
	    	  showinsert();
	      else                                    
	    	  showmove();
      }
  }

  /**
   * oldconsume        Part of printout. Have run out of new string.
   * Process the rest of the old file, printing any
   * parts which were deletes or moves.
   */
  void oldconsume() {
      for(;;) {
	      if ( printoldline > oldinfo.maxLine )
	    	  break;       /* end of file */
	      printnewline = oldinfo.other[ printoldline ];
	      if ( printnewline < 0 ) 
	    	  showdelete();
	      else if ( blocklen[ printoldline ] < 0 ) 
	    	  skipold();
	      else 
	    	  showmove();
      }
  }

  /**
   * showdelete        Part of printout.
   * Expects printoldline is at a deletion.
   */
  void showdelete() {
    if ( printstatus != delete ){
      //println( ">>>> ELIMINADA LINEA " + printoldline);
      diffLine.put(Integer.valueOf(printoldline), DiffLine.DELETE); 
    }
    printstatus = delete;
    //oldinfo.symbol[ printoldline ].showSymbol();
    anyprinted = true;
    printoldline++;
  }

  /*
   * showinsert        Part of printout.
   * Expects printnewline is at an insertion.
   */
  void showinsert() {
	   DiffLine dl=null; 
       if ( printstatus == change ) {
    	   //println( ">>>>     MODIFICADO A" );
    	   dl=DiffLine.CHANGE;
       } else if ( printstatus != insert ) {
    	   //println( ">>>> NUEVA LINEA " + printoldline );
    	   dl=DiffLine.NEW;
       }
       //printstatus = insert;
       //newinfo.symbol[ printnewline ].showSymbol();
       diffLine.put(Integer.valueOf(printnewline), dl); 
       anyprinted = true;
       printnewline++;
  }

  /**
   * showchange        Part of printout.
   * Expects printnewline is an insertion.
   *  Expects printoldline is a deletion.
   */
  void showchange() {
       if ( printstatus != change ) {
    	   //println( ">>>> " + printoldline + " MODIFICADA DE");
    	   
       }
       printstatus = change;
       //oldinfo.symbol[ printoldline ].showSymbol();
       anyprinted = true;
       printoldline++;
  }

  /**
   * skipold           Part of printout.
   * Expects printoldline at start of an old block that has 
   * already been announced as a move.
   * Skips over the old block.
   */
  void skipold() {
       printstatus = idle;
       for(;;) {
    	   if ( ++printoldline > oldinfo.maxLine )
    		   break;     /* end of file  */
    	   if ( oldinfo.other[ printoldline ] < 0 )
    		   break;    /* end of block */
    	   if ( blocklen[ printoldline ]!=0)
    		   break;          /* start of another */
       }
  }

  /**
   * skipnew           Part of printout.
   * Expects printnewline is at start of a new block that has
   * already been announced as a move.
   * Skips over the new block.
   */
  void skipnew() {
       int oldline;
       printstatus = idle;
       for(;;) {
    	   if ( ++printnewline > newinfo.maxLine )
    		   break;    /* end of file  */
    	   oldline = newinfo.other[ printnewline ];
    	   if ( oldline < 0 )
    		   break;                         /* end of block */
    	   if ( blocklen[ oldline ] != 0)
    		   break;              /* start of another */
   		}
  }

  /**
   * showsame          Part of printout.
   * Expects printnewline and printoldline at start of
   * two blocks that aren't to be displayed.
   */
  void showsame() {
       int count;
       printstatus = idle;
       if ( newinfo.other[ printnewline ] != printoldline ) {
    	   diffLine.put(Integer.valueOf(printoldline), DiffLine.ERROR);
       }
       count = blocklen[ printoldline ];
       printoldline += count;
       printnewline += count;
  }

  /**
   * showmove          Part of printout.
   * Expects printoldline, printnewline at start of
   * two different blocks ( a move was done).
   */
  void showmove() {
       int oldblock = blocklen[ printoldline ];
       int newother = newinfo.other[ printnewline ];
       int newblock = blocklen[ newother ];

       if ( newblock < 0 ) skipnew();         	// already printed.
       else if ( oldblock >= newblock ) {     	// assume new's blk moved.
    	   blocklen[newother] = -1;         	// stamp block as "printed".
    	   //println( ">>>> " + newother + " DESDE " + (newother + newblock - 1) + " MOVIDA A LINEA " + printoldline );
    	   
    	   for( ; newblock > 0; newblock--, printnewline++ ){
    		   //newinfo.symbol[ printnewline ].showSymbol();
    		   diffLine.put(Integer.valueOf(printnewline), DiffLine.MOVE); 
    	   }
    	   anyprinted = true;
    	   printstatus = idle;

       } else                	/* assume old's block moved */
    	   skipold();      		/* target line# not known, display later */
  }

  /** Convenience wrapper for println */
  public void println(String s) {
    System.out.println(s);
  }
};        // end of main class!

/**
 * Class "node". The symbol table routines in this class all
 * understand the symbol table format, which is a binary tree.
 * The methods are: addSymbol, symbolIsUnique, showSymbol.
 */ 
class node{                       /* the tree is made up of these nodes */
  node pleft, pright;
  int linenum;

  static final int freshnode = 0,
  oldonce = 1, newonce = 2, bothonce = 3, other = 4;

  int /* enum linestates */ linestate;
  String line;

  static node panchor = null;    /* symtab is a tree hung from this */

  /**
   * Construct a new symbol table node and fill in its fields.
   * @param        string  A line of the text 
   */
  node( String pline)
  {
       pleft = pright = null;
       linestate = freshnode;
       /* linenum field is not always valid */     
       line = pline;
  }

  /**
   * matchsymbol       Searches tree for a match to the line.
   * @param  String  pline, a line of text
   * If node's linestate == freshnode, then created the node.
   */
  static node matchsymbol( String pline ) {
       int comparison;
       node pnode = panchor;
       if ( panchor == null ) return panchor = new node( pline);
       for(;;) {
    	   comparison = pnode.line.compareTo(pline);
    	   if ( comparison == 0 ) return pnode;          /* found */

    	   if ( comparison < 0 ) {
    		   if ( pnode.pleft == null ) {
    			   pnode.pleft = new node( pline);
    			   return pnode.pleft;
    		   }
    		   pnode = pnode.pleft;
    	   }
    	   if ( comparison > 0 ) {
    		   if ( pnode.pright == null ) {
    			   pnode.pright = new node( pline);
    			   return pnode.pright;
    		   }
    		   pnode = pnode.pright;
    	   }
       }
       /* NOTE: There are return stmts, so control does not get here. */
  }

  /**
   * addSymbol(String pline) - Saves line into the symbol table.
   * Returns a handle to the symtab entry for that unique line.
   * If inoldfile nonzero, then linenum is remembered.
   */
  static node addSymbol( String pline, boolean inoldfile, int linenum ) {
    node pnode;
    pnode = matchsymbol( pline );  /* find the node in the tree */
    if ( pnode.linestate == freshnode ) {
      pnode.linestate = inoldfile ? oldonce : newonce;
    } else {
      if (( pnode.linestate == oldonce && !inoldfile ) ||
          ( pnode.linestate == newonce &&  inoldfile )) 
           pnode.linestate = bothonce;
      else 
    	  pnode.linestate = other;
    }
    if (inoldfile) 
    	pnode.linenum = linenum;
    return pnode;
  }

  /**
   * symbolIsUnique    Arg is a ptr previously returned by addSymbol.
   * --------------    Returns true if the line was added to the
   *                   symbol table exactly once with inoldstring true,
   *                   and exactly once with inoldstring false.
   */
  boolean symbolIsUnique() {
    return (linestate == bothonce );
  }

  /**
   * showSymbol        Prints the line to stdout.
   */
  void showSymbol() {
    System.out.println(line);
  }
}
