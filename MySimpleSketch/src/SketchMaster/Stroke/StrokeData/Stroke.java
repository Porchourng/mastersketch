package SketchMaster.Stroke.StrokeData;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;




import SketchMaster.system.SystemSettings;
import SketchMaster.Stroke.features.FeatureFunction;
import SketchMaster.Stroke.graphics.shapes.GuiShape;
import SketchMaster.Stroke.graphics.shapes.Line;
import SketchMaster.Stroke.graphics.shapes.SegmentedShape;
import SketchMaster.gui.DrawingDebugUtils;
import SketchMaster.lib.ComputationsGeometry;
import SketchMaster.system.SystemSettings;

/**
 * @author Mahi
 */
public class Stroke extends SimpleInkObject implements Serializable, GuiShape {
	
//	class pointChange{
//		final static int  TYPE_INC=0;
//		 final static  	int TYPE_DEC=1;
//		 final static 	int TYPE_ULTERNATING=2;
//		 final static int 	TURN_X=0;
//		 final static int 	TURN_y=1;
//		 final static int 	TURN_M=2;
//		 
//
//		  final static int 	TURN_YM=3;
//		 final static int 	TURN_XM=4;
//	
//	   final static int 	TURN_XY=5; 
//	   
//		 final static int 	TURN_ALL=6;
//		 
//		double inX;
//		double inY;
//		double inMag;
//		
//		int typeX;
//		int typeY;
//		int typeMag;
//		int turnType=0;
//		int index;
//		boolean turn=false;
//		public void add(double x, double y, double mag){
//			inX=x;
//			if (x>0){
//				typeX=TYPE_INC;
//			}
//			else {
//				typeX=TYPE_DEC;
//			}
//			
//			
//			
//			
//			
//			inY=y;
//			if (y>0){
//				typeY=TYPE_INC;
//			}
//			else {
//				typeY=TYPE_DEC;
//			}
//			
//			inMag=mag;
//			if (mag>0){
//				typeMag=TYPE_INC;
//			}
//			else {
//				typeMag=TYPE_DEC;
//			}
//			
//			
//			
//			
//		}
//		public void testTurn(pointChange temp) {
//		if (temp.turn)
//			return ;
//			
//			if (typeX!=temp.typeX){
//				turn=true;
//				turnType=TURN_X;
//			}
//		
//			if (typeY!=temp.typeY){
//				// if prevvv.
//				if (turn){
//					
//					turnType=TURN_XY;
//				}
//				else {
//					turnType= TURN_y;
//				}
//				
//				turn=true;
//			}
//			
//			if (typeMag!=temp.typeMag){
//				
//				if(turn){
//				
//					// get the current turn .. 
//					if (turnType==TURN_XY){
//						
//						turnType=TURN_ALL;
//						
//					}
//					else {
//						
//						if (turnType==TURN_X){
//							turnType=TURN_XM;
//						}
//						else {
//							turnType=TURN_YM;
//						}
//						
//					}
//					
//				}
//				else {
//					turnType=TURN_M;
//				}
//				
//				turn=true;
//			}
//		}
//		
//		
//		
//	}
	
	
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Stroke.class);
    private static boolean debug=false;
	// private PointData[] points;

	public static double RectangleLoopThreshold = 50;
	private transient static boolean onLine = SystemSettings.OnLineComputations;
    private boolean interpolated=false;
	protected ArrayList<PointData> Orginalpoints = null;
	/**
	 * 
	 */
   // ArrayList<pointChange>  changes=null;
    ArrayList<Integer>  SortedXIndex=null;
    ArrayList<Integer>  SortedYIndex=null;
	int OverTraceHyposes=0;
	ArrayList<Integer> OverTracePoints=null;
	ArrayList<Point >OverTracePair=null;
    
    ArrayList<Integer>  turnsIndex=null;
      ArrayList<Integer> SortedPointIndex=null;
    ArrayList<Double>  DistantFromStart=null;
 //   ArrayList<Rectangle2D>  boxes=null;
    double NDDE;
	double DCR;
	double LongestChordtoLengthRatio;
	double EndPointtoLengthRation;
	double LongestDistanceBetweenPointsInStroke;
		private static final int ORIENTATION_VERTICAL = 0;
	final int  ORIENTATION_HORIZONATAL=1;
	int Orientation=ORIENTATION_HORIZONATAL;
	boolean OverTraced=false;
	boolean TailRemoved=false;
	ArrayList<Integer> TailPartIndex=null;
   
	private static final long serialVersionUID = -4866211701068294061L;
	//private static final int MaxChangeIndex = 10;
	private static final int	DEFAULT_NEIGHBOURS	= 3;
	


	private PointData StartPoint = null;

	private PointData EndPoint = null;

	private boolean dirty = true;// dirty mean not finish editing.

	private transient StrokeStatisticalData StatisticalInfo = null; // stroke


	private PointData MaxDirection;
	private PointData MinDirection;
	private boolean	OverTracePointsDeleted=false;
	private boolean	OverTraceRemoved=false;
	
	// this window is used in testproximity to check if there is over lap
	//surronding pixels and check that are not ins the same locatinos... 
	int 		window=SystemSettings.WINDOW_SCAN_SIZE;
	// distance that decide if the points are near or not. (distance range for testproximity function)
	double LocationRange=	SystemSettings.LOCATION_RANGE;
	
	// computed
																	// StatisticalInfo

	public Stroke() {
		StatisticalInfo = new StrokeStatisticalData();
		StatisticalInfo.initAll();
		points = new ArrayList<PointData>();
		if (SystemSettings.USE_NEW_CHANGES){
		   SortedXIndex=new ArrayList<Integer>();
		      SortedYIndex=new ArrayList<Integer>();
		      SortedPointIndex=new ArrayList<Integer>();
		}
		      DistantFromStart=new ArrayList<Double>();
	}

	public   Stroke(SimpleInkObject ink) {
		// get all info from theh simple ink to the stroke 
		
		this.points=new ArrayList<PointData>();
		int noPoints=ink.getPointsCount();
		StatisticalInfo = null;
		this.setStartPoint((PointData)points.get(0).clone());
		this.setEndPoint((PointData)ink.getPoint(noPoints-1).clone());
		//this.addPoint( (PointData)ink.getPoint(i).clone());
		for (int i = 0; i < noPoints; i++) {
		//	points.add((PointData)ink.getPoint(i).clone());
	     this.addPoint( (PointData)ink.getPoint(i).clone());		
			
		}
		
	
		//StatisticalInfo.initAll();
		
		
	}

	public Stroke InterpolatePoints(){
		if (this.points.size()>0){
		Stroke NewInterploated=new Stroke();
		//  ArrayList pointsa = new ArrayList<PointData>();
		 
	        PointData prev =this.points.get(0);
	        PointData point=null;
	        NewInterploated.addPoint(prev);
	        NewInterploated.setStartPoint(prev);
	        
	        if (debug)
	        {
	        	if (DrawingDebugUtils.DEBUG_GRAPHICALLY){
	        		
	        		DrawingDebugUtils.drawPointPath(DrawingDebugUtils.getGraphics(), DrawingDebugUtils.InkColor, DrawingDebugUtils.PointsColor, this.points);
	        		
	        	}
	        }
	        
	        
	     //   pointsa.add(prev);
	        for(int i = 1; i < points.size(); i++)
	        {
	             point = points.get(i);
	            double dist = point.distance(prev);
	            if(dist > SystemSettings.MaxInterplotionLength)
	            {
	                int n = (int)Math.floor(dist / SystemSettings.MaxInterplotionLength);
	                double theta = prev.getAngle(point)  ;
	                long tStep = (point.getTime() - prev.getTime()) / (long)n;
	                double pStep = (point.getPresureValue() - prev.getPresureValue()) / n;
	                for(int j = 1; j < n + 1; j++)
	                {
	                    PointData p = new PointData(prev.getX() + (double)j * SystemSettings.MaxInterplotionLength * Math.cos(theta),
	                    		prev.getY() + (double)j * SystemSettings.MaxInterplotionLength * Math.sin(theta),
	                    		prev.getTime() + tStep * (long)j,
	                    		(int)((double)prev.getPresureValue() + pStep * (double)j));
	                    NewInterploated.addPoint(p);
	                  //  pointsa.add(p);
	                }
	                prev = point;
	  		            //NewInterploated.addPoint(point);

	            }
	            else{  // point is less than the interploaiton predected 
	            	// skip this point 
	            	//prev = point;
		      //      NewInterploated.addPoint(point);
	            }
	            
	        }
	        if (point!=null)
	        	NewInterploated.setEndPoint(point );
	        NewInterploated.setInterpolated(true);
	        if (debug)
	        {
	        	if (DrawingDebugUtils.DEBUG_GRAPHICALLY){
	        		
	        		DrawingDebugUtils.drawPointPath(DrawingDebugUtils.getGraphics(), Color.cyan, Color.MAGENTA,NewInterploated.points);
	        		
	        	}
	        }
	        return   NewInterploated;
		
		}
		else{
			
			return this;
		}
		
		
	}
	
	
	
	/**
	 * @param endPoint
	 *            the endPoint to set
	 * @uml.property name="endPoint"
	 */
	public void setEndPoint(PointData EndPoint) {
		this.dirty = false;
		this.EndPoint = EndPoint;
          
		if (SystemSettings.USE_NEW_CHANGES){
		logger.info( "  now all point are intered i need to display the sort list of x an y ");
		logger.info( " points   "+points);
		logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
		logger.info("SortedXIndex    " + SortedXIndex);
		logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
		logger.info(" SortedYIndex   "+ SortedYIndex);
		//setLoopingEnd(this.StartPoint, this.EndPoint);

		}
		if (!onLine) {
			updateStatiscal();
		}

		getStatisticalInfo().updateBatchFunctions();
		
	}

	private void updateOtherFeatures() {
		
		//get the maximum hightest direction value,
		
		ArrayList<FeatureFunction> function = getStatisticalInfo().getFunctions();
		FeatureFunction direction=null;
		for (int i = 0; i <function.size(); i++) {
			//get the direction 
			if (function.get(i).getName().startsWith("Direction")){  // direction is better. 
			//if (function.get(i).getName().startsWith("Slope")){
				direction=function.get(i);
			}
		}
		
	// change try using 
	//	direction=getStatisticalInfo().getCurvatureRotation();
			//NDDE
		if (direction!=null){
			
			int max=direction.getMaxLocation();
			int min=direction.getMinLocation();
			// compute dcr 
	 
			DCR=direction.getMax()/direction.getAverage();
			
			logger.info("  the dcr is  =  "+DCR);
			//double revolution=TotalRotation()/(2.0*Math.PI);
			logger.info("  the total rotation of the  = " + TotalRotation());
			logger.info( " The sum of the  drection graph is  "+direction.getSumUpNow() );
			
			logger.info("  revolution is   "+revolution);
			MaxDirection=points.get(max);
			MinDirection=points.get(min);
			
		double distance=points.get(min).distance(points.get(max));
		
		logger.info(" the point of max direction is "+points.get(max)+" and point of min direction is "+points.get(min));
		logger.info(" distance between them is  "+distance);
		
		NDDE=Math.abs(distance/this.getLength());
		
		logger.info(" NDDE is = "+NDDE);
		
		}
		
		
	
		
	}

	@Deprecated
	public void setLoopingEnd(PointData start, PointData end) {
		if (start==null)
			if (points!=null)
				{
				if (points.size()>0)
					start=points.get(0);
		
			else 
				return ;
				}
			else return;
		
		Rectangle2D r = new Rectangle2D.Double(start.getPointLocation().getX(),
				start.getPointLocation().getY(), 0, 0);
		r.add(end.getPointLocation());
		double area = r.getWidth() * r.getHeight();

		if (area < RectangleLoopThreshold) {
			// add the start point to last point in the points array
			points.add((PointData) start.clone());
			this.EndPoint = start;

		}
	}

	/**
	 * @param startPoint
	 *            the startPoint to set
	 * @uml.property name="startPoint"
	 */
	public void setStartPoint(PointData StartPoint) {
		this.StartPoint = StartPoint;
		this.dirty = true;
	}

	public void addPoint(PointData point) {
		// logger.info(point);
		// / adding point to the array
		this.points.add(point);
		// after add point chek that this point 
		if (SystemSettings.USE_NEW_CHANGES){
		addPointToSortedLists(point);
		logger.info(" this is poing number "+points.size());
		//addPointToIncDec(point);
		}
		addPointDistance(point);
		if (onLine) {
			// updateBoundingBox(point);
			// if (onLine)
			// updateStatiscalData(point);
			updateStatiscal(point);
		}
	}


	private void addPointDistance(PointData point){
		Double dis;
		
		if (StartPoint==null)
			return;
		 dis=StartPoint.distance(point);
		this.DistantFromStart.add(dis);
	}
	
	
	private int addToList(  ArrayList<Integer>  list, double value, int type ){
		
		if (	this.points.size()==1){
			// this is the first point... 
			// do the follwoing 
			list.add(0);
		}
		
	 
		int newIndexpoint=BinarySearch(list,value, type);
		 //  logger.info( "  the index found by binary search is "+newIndexpoint);
			if (newIndexpoint==-1){
				// add at the begining 
				list.add(0, this.points.size()-1);
			}
			else if (newIndexpoint==-2){
				list.add(this.points.size()-1);
			}
			else {
				list.add(newIndexpoint, this.points.size()-1);
			}
			
		   return newIndexpoint;
		
		
	}
	private boolean testProximity(PointData point, int insertLoc, ArrayList<Integer> list, Point pairmag){
		
		if (insertLoc>0){// this is insert is in the middle then i need to look for the

			// get the list from windo to window 
			//first make sure that the size is ok 
			int b=insertLoc-window;
			int e=insertLoc+window;
			// now make sure that b >0 and e < size 
			if (b<0)  b=0;			
			if (e>list.size()) e=list.size();
			int pointIndex=points.size()-1;
			int in;
			for (int i = b; i < e; i++) {
				in=list.get(i);
				if (pointIndex-in<window){
					continue;
				}
				// get the index of the
				PointData tempPoint = points.get(in);
			
				if (tempPoint.isNearPoint(point, LocationRange))
				{
						logger.info( " NEEEARRRRRRRRRRRRR" );
								logger.info(" the distance between points of index of  "+(this.points.size()-1)+" and is  "+point);
						logger.info("  with tempoint has index of   "+ list.get(i)+"     and is" +tempPoint);
						
						pairmag.setLocation(pointIndex, in);
						logger.info("  the distance is "+point.distance(tempPoint));
						
						return true;
				}
				
				
			}
			
			
			
			
		}
		
		return false;
	}

	private void addPointToSortedLists(PointData point){
		
		double x,y;
		x=point.x;
		y=point.y;
		double loc=point.magnitude();

//	if (testProximity(point, insertLoc, SortedXIndex)	 ){
//		
//	
//		OverTraceHyposes++;
//		 if (OverTracePoints==null){
//			 OverTracePoints=new ArrayList<Integer>();
//		 }
//		 OverTracePoints.add(points.size()-1);
//	}
		
		Point  pairx=new Point(0,0);
		 		 int insertLocx=addToList( SortedXIndex,x, 0);
		 		 
				 boolean testx=testProximity(point, insertLocx, SortedXIndex, pairx)	;
		 int insertLocy= addToList(SortedYIndex, y, 1);
			Point  pairy=new Point(0,0);
		 
		 boolean testy=testProximity(point, insertLocy, SortedYIndex,pairy)	;
			Point  pairmag=new Point(0,0);
		 int insertLocloc= addToList (SortedPointIndex,loc, 2);
		 
		 boolean testmag=testProximity(point, insertLocloc, SortedPointIndex, pairmag)	;
		 
		 if (testx&testy || testy&testmag || testmag &testx ){
				logger.info( " test correct...........   ."+points.size());
				
				OverTraceHyposes++;
				 if (OverTracePoints==null){
					 OverTracePoints=new ArrayList<Integer>();
					 OverTracePair=new ArrayList<Point>();
				 }
				 OverTracePoints.add(points.size()-1);
				 points.get(points.size()-1).setOvertrace(true);
				 if (testx){
					 
					 OverTracePair.add(pairx);
				 }
				 else if (testy){
					 OverTracePair.add(pairy);
				 }
				 else {
					 OverTracePair.add(pairmag);
				 }
				 
			}
		 
		
//		if (	this.points.size()==1){
//			// this is the first point... 
//			// do the follwoing 
//			SortedXIndex.add(0);
//			SortedYIndex.add(0);
//			SortedPointIndex.add(0);
//			
//			
//		}
//		else{
//			// this point is added to list of point at the end
//			double x,y;
//			x=point.x;
//			y=point.y;
//			double loc=point.magnitude();
//			
////			String str="";
////			for (int i = 0; i < points.size(); i++) {
////				str+=" X ("+i+" )= "+points.get(i).x+" "; 
////			}
////			
////			 logger.info( "  the x is "+x);
////			 
////			 logger.info( str );
//			 
//			int newIndexpoint=BinarySearch(SortedXIndex,x, 0);
//		 //  logger.info( "  the index found by binary search is "+newIndexpoint);
//			if (newIndexpoint==-1){
//				// add at the begining 
//				SortedXIndex.add(0, this.points.size()-1);
//			}
//			else if (newIndexpoint==-2){
//				SortedXIndex.add(this.points.size()-1);
//			}
//			else {
//				SortedXIndex.add(newIndexpoint, this.points.size()-1);
//			}
//			
//		   
//			newIndexpoint=BinarySearch(SortedYIndex,y, 1);
//			if (newIndexpoint==-1){
//				// add at the begining 
//				SortedYIndex.add(0, this.points.size()-1);
//			}
//			else if (newIndexpoint==-2){//at the end 
//				SortedYIndex.add(this.points.size()-1);
//			}else {
//				SortedYIndex.add(newIndexpoint, this.points.size()-1);
//				
//			}
//			
//			
//			
//			newIndexpoint=BinarySearch(SortedPointIndex,loc, 1);
//			if (newIndexpoint==-1){
//				// add at the begining 
//				SortedPointIndex.add(0, this.points.size()-1);
//			}
//			else if (newIndexpoint==-2){//at the end 
//				SortedPointIndex.add(this.points.size()-1);
//			}else {
//				SortedPointIndex.add(newIndexpoint, this.points.size()-1);
//				
//			}
//			
//			
//			
////			logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
////			logger.info("SortedXIndex    " + SortedXIndex);
////			logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
////			logger.info(" SortedYIndex   "+ SortedYIndex);
//		}
		 
	}

	private double getTestValue (ArrayList<Integer> arr, int index, int type ){
		
	     double testValue=0;
		 if (type==0){
	            testValue=this.points.get( arr.get(index)).x;
	        }
	            else if (type==1){ 
	                testValue=this.points.get( arr.get(index)).y;
	            }
	            else if (type==2){ 
	                testValue=this.points.get( arr.get(index)).magnitude();
	            }
	            else if (type==3){
	            	testValue= arr.get(index);
	            }
		 
		 return testValue;
		
	}
    /**
     * Binary search finds item in sorted array.
     * And returns index (zero based) of item
     * If item is not found returns -1
     * Based on C++ example at
     * http://en.wikibooks.org/wiki/Algorithm_implementation/Search/Binary_search#C.2B.2B_.28common_Algorithm.29
     **/
   private int BinarySearch(ArrayList<Integer> arr, double value, int type)
    {       
        int low = 0, high = arr.size()- 1, midpoint = 0;
        double testValue=0,tpointb,tpointa;
        testValue=getTestValue(arr, high, type);
        if (value>testValue){
        	return -2;
        	}

        testValue=getTestValue(arr, low, type);
        
        
        if (value<testValue){
        	return 0;
        	}
       
        
        
        while (low <= high)
        {
        	//logger.info( "  low is "+low+"  hight is "+high);
            midpoint = (low + high) / 2;
                 testValue=getTestValue(arr, midpoint, type);
             if (midpoint>1&&midpoint<arr.size()-1){

            	  tpointb=getTestValue(arr, midpoint-1, type);
            	  tpointa=getTestValue(arr, midpoint+1, type);
   
             }
             else {
                tpointa=testValue;
            	 tpointb=testValue;
             }

             
            // check to see if value is equal to item in array
            if (value == testValue)
            {                    
                return midpoint;
            }
            else if (value>=tpointb && value<=testValue){
            	return midpoint;
            }
            else if (value>=testValue && value<=tpointa){
            	return midpoint+1;
            }
            else if (value < testValue)
                high = midpoint - 1;
            else
                low = midpoint + 1;
        }

        // item was not found
        return -1;
    }

	private void updateStatiscal(PointData point) {
		StatisticalInfo.updateFunctions(point, this);
		StatisticalInfo.updateBoundingBox(point, this);
	}

	private void updateStatiscal() {
		StatisticalInfo.updateFunctionsAndBox(this);
	}

 
	public void calculateStrokeData() {
		// / if the points exist without using the
		// add functions .
		// first compute the bounding box
		//
		
		if (StatisticalInfo == null){
		//	logger.info("genereating the statistical data at firest");
			StatisticalInfo = StrokeStatisticalData.BuildStorkeData(this);
		}
		
		computeRemainigStatists();
		generateAllDominatePoints();
		// StatisticalInfo.getData();
		// for (int i = 0; i < points.size(); i++) {
		//		
		// }
	}

	public void clearAllPoints() {
		this.points.clear();
		StatisticalInfo.clear();
	}

	public String toString() {
		String s = "";

		s = " Start Point " + points.get(0).toString() + "   points are  =  "
				+ points.toString();

		return s;

	}

	public void drawStroke(Graphics g) {
		// for (int i = 0; i < points.size() - 1; i++) {
		// g.drawLine((int) ((PointData) points.get(i)).getX(),
		// (int) ((PointData) points.get(i)).getY(),
		// (int) ((PointData) points.get(i + 1)).getX(),
		// (int) ((PointData) points.get(i + 1)).getY());
		// }
		
		drawStroke(g, Color.BLUE, Color.RED);
//		if (DrawingDebugUtils.DEBUG_GRAPHICALLY)
//			drawStroke(DrawingDebugUtils.getGraphics(),Color.BLUE, Color.RED);
	}

	public void drawStroke(Graphics g, Color color) {
		g.setColor(color);

		for (int i = 0; i < points.size() - 1; i++) {
			g.drawLine((int) ((PointData) points.get(i)).getX(),
					(int) ((PointData) points.get(i)).getY(),
					(int) ((PointData) points.get(i + 1)).getX(),
					(int) ((PointData) points.get(i + 1)).getY());
		}// for
	}// draw stroke

	public void drawStroke(Graphics g, Color linecolor, Color pointColor) {
		g.setColor(linecolor);
		
		// This draw the storke... 
		for (int i = 0; i < points.size() - 1; i++) {
			if ( ((PointData) points.get(i)).isDeleted())
				continue;
			
			g.drawLine((int) ((PointData) points.get(i)).getX(),
					(int) ((PointData) points.get(i)).getY(),
					(int) ((PointData) points.get(i + 1)).getX(),
					(int) ((PointData) points.get(i + 1)).getY());
			
			
			if (SystemSettings.DrawPoints)
			{	
				if ( ((PointData) points.get(i)).isDeleted())
				continue;
				g.setColor(Color.BLUE);
				g.drawRect((int) ((PointData) points.get(i)).getX(),
						(int) ((PointData) points.get(i)).getY(),
						2,2);
				g.fillRect((int) ((PointData) points.get(i)).getX(),
						(int) ((PointData) points.get(i)).getY(),
						2,2);
			}
			
			
			
		}
		

		if (SystemSettings.USE_NEW_CHANGES){
			// i want to draw the places of turns....
		
			 if (OverTracePoints!=null ){
				 
				if (!OverTracePointsDeleted){
				   g.setColor(Color.ORANGE);
				 for (int i = 0; i <OverTracePoints.size(); i++) {
					  int ind =OverTracePoints.get(i);
					 
						g.drawRect((int) ((PointData) points.get(ind)).getX(),
								(int) ((PointData) points.get(ind)).getY(),
								4,4);
						g.fillRect((int) ((PointData) points.get(ind)).getX(),
								(int) ((PointData) points.get(ind)).getY(),
								3,3);
					 
				 }
				}
				 
			 }
			 
			
			
		}
		
		
		PointData temp;
		if (SystemSettings.DrawChords&&SmallestX!=null){
			g.setColor(Color.GREEN);
			
			g.drawLine((int) (SmallestX).getX(),
					(int) (SmallestX).getY(),
					(int) (LargestX).getX(),
					(int) (LargestX).getY());
			
			g.drawRect((int) (SmallestX).getX(),
					(int) (SmallestX).getY(),
					4,4);
			g.drawRect((int) (LargestX).getX(),
					(int) (LargestX).getY(),
					4,4);
			g.setColor(Color.ORANGE);
			g.drawLine((int) (SmallestY).getX(),
					(int) (SmallestY).getY(),
					(int) (LargestY).getX(),
					(int) (LargestY).getY());
			
			g.drawRect((int) (SmallestY).getX(),
					(int) (SmallestY).getY(),
					4,4);
			g.drawRect((int) (LargestY).getX(),
					(int) (LargestY).getY(),
					4,4);
		}
		
		if (MaxDirection!=null){
			g.setColor(Color.BLACK);
				g.drawRect((int) (MaxDirection).getX(),
						(int) (MaxDirection).getY(),
						4,4);
			g.fillRect((int) (MaxDirection).getX(),
					(int) (MaxDirection).getY(),
					4,4);
			
			
			
			g.drawRect((int) (MinDirection).getX(),
					(int) (MinDirection).getY(),
					4,4);
		g.fillRect((int) (MinDirection).getX(),
				(int) (MinDirection).getY(),
				4,4);
			
		}
		// g.setColor(Color.green);
//		if (StatisticalInfo != null) {
//			
//		}// if of teh StatisticalInfo check

		if (StatisticalInfo != null) {

			// now draw the rectangle of bounds
			if (StatisticalInfo.getBox() != null && (!dirty)) {

				if (SystemSettings.STATISTICAL_POINTS_DRAW)
					StatisticalInfo.paint(g);
				// logger.info( " x "+StatisticalInfo.getBox().getX()+" y
				// "+StatisticalInfo.getBox().getY());
				g.setColor(Color.GRAY);
				g.drawRect((int) StatisticalInfo.getBox().getX()-1,
						(int) StatisticalInfo.getBox().getY()+1,
						(int) StatisticalInfo.getBox().getWidth()+1,
						(int) StatisticalInfo.getBox().getHeight()+1);

			}
		}
	}// close of paint

	/**
	 * @return the startPoint
	 * @uml.property name="startPoint"
	 */
	public PointData getStartPoint() {
		return StartPoint;
	}

	/**
	 * @return the endPoint
	 * @uml.property name="endPoint"
	 */
	public PointData getEndPoint() {

		return EndPoint;
	}

	public long getStrokeTime() {
		long returnlong = (EndPoint.getTime() - StartPoint.getTime());
		return returnlong;
	}

	/**
	 * @return the StatisticalInfo
	 * @uml.property name="StatisticalInfo"
	 */
	public StrokeStatisticalData getStatisticalInfo() {
		if (StatisticalInfo == null) {
			calculateStrokeData();

		}
		return StatisticalInfo;
	}

	/**
	 * @param StatisticalInfo
	 *            the StatisticalInfo to set
	 * @uml.property name="StatisticalInfo"
	 */
	public void setStatisticalInfo(StrokeStatisticalData data) {
		this.StatisticalInfo = data;
	}

	public void computeAreaOfStroke() {
		// first are then try to get the centroid
		double area = 0.0;

		double a = 0.0;

		PointData pi, pj;

		// logger.info("------------------------------Error---------
		// -------------------");
		// logger.info(this.toString());
		// logger.info("nubmer of polygong in this
		// solution"+polygonVertices.size());
		// for (int i = 0; i < points.size()-1; i++) {
		// //now i
		// pi=points.get(i);
		// // {
		// pj=points.get(i+1);
		// a = (pi.getX()*pj.getY())-(pj.getX()*pi.getY());
		// area+=a;
		//        
		//		
		// }
		// area*=0.5;
		area = ComputationsGeometry.computeArea(points);
		getStatisticalInfo().setArea(area);
	}

	public void computeRemainigStatists() {
		computeAreaOfStroke();
	}

	public void generateAllDominatePoints() {
		getStatisticalInfo().generateAllDominatePoints();

		// //System.out
		// // .println("---------------Compute critical
		// point-----------------");
		// FileLog
		// .addString("---------------get critical point-----------------");
		// // corners,bounding box ....
		// // StrokeLib.computeSpeedCriticalPoints(Evt.getEventStroke());
		// // StrokeLib.computeCurvatureCriticalPoints(Evt.getEventStroke());
		//			
		// //for sped StatisticalInfo get min of a certian threhold.
		// // for speed make it the average of spped as threshold
		// ArrayList indeces=null;
		// // StrokeStatisticalData tempData=getData();
		// ArrayList tempD=new ArrayList();
		//			
		// FeatureFunction func=getStatisticalInfo().getSpeed();
		// //
		// //
		//			
		//		
		//	
		// func.setDataThreshold(func.getAverage());
		//			
		// tempD=new ArrayList();
		// //
		// indeces=func.calcuateLocalMinPoints();
		//			
		// getFunctionDominatePoints(indeces, null, tempD);
		// //////////////////////finish speed now get time
		//					
		// func=StatisticalInfo.getTimeDiff();
		//		
		//
		// func.setDataThreshold(func.getAverage());
		// ArrayList indeces2=func.calcuateLocalMaxPoints();
		//			
		// //
		// getFunctionDominatePoints(indeces2, indeces, tempD);
		//			
		//
		//			
		// ////////////////now the direction
		// func=StatisticalInfo.getDirection();
		// func.setDataThreshold(func.getAverage());
		//
		// ArrayList indeces3=func.calcuateLocalMaxPoints();
		// getFunctionDominatePoints(indeces3, indeces, tempD);
		//			
		//			
		// ///////////////////now checck curvature
		//			
		// func=StatisticalInfo.getCurvature();
		// func.setDataThreshold(func.getAbsaverage());
		// logger.info("the avergaeof curvature is "+func.getAverage());
		// logger.info("the threshold average is "
		// +func.getAbsaverage());
		// ArrayList tempindeces4=func.calcuateLocalAbsolutePoints(1);
		// ArrayList indeces4= new ArrayList();
		// int orignal=0,newi;
		// for (int i = 0; i < tempindeces4.size(); i++) {
		// orignal=(Integer)tempindeces4.get(i);
		// newi=orignal+SystemSettings.STROKE_CONSTANT_NEIGHBOURS;
		// if (newi>=points.size())
		// indeces4.add(0);
		// else
		// indeces4.add(newi);
		//				
		// }
		// getFunctionDominatePoints(indeces4,indeces, tempD);
		//			
		//
		// StatisticalInfo.setDominatePoints(tempD);
		//			
		// logger.info("number of dominat points is "+tempD.size());
	}

	@Deprecated
	private void addSorted(ArrayList list, int index) {
		int temp;
		for (int i = 0; i < list.size(); i++) {
			temp = (Integer) list.get(i);

			if (temp > index) {
				list.add(i + 1, index);
				return;
			}
		}
		// upp till now it is the largest
		// add it now
		list.add(index);
	}

	@Deprecated
	private boolean indexExist(ArrayList list, int index) {
		int temp;
		for (int i = 0; i < list.size(); i++) {
			temp = (Integer) list.get(i);
			if (temp == index)
				return true;
			if (temp > index)
				return false;
		}
		return false;
	}

	@Deprecated
	private ArrayList getFunctionDominatePoints(ArrayList indeces3,
			ArrayList except, ArrayList tempD) {
		PointData point;

		// ArrayList tempD1;

		// tempD1=new ArrayList();
		if ((indeces3 != null)) {
			// logger.info("frome time "+indeces2.size());
			if (except != null) {

				for (int i = 0; i < indeces3.size(); i++) {
					if (indexExist(except, (Integer) indeces3.get(i))) {
						addSorted(except, (Integer) indeces3.get(i));
						point = points.get((Integer) indeces3.get(i));
						tempD.add(point);
					}

				}
			} else {
				for (int i = 0; i < indeces3.size(); i++) {

					point = points.get((Integer) indeces3.get(i));
					tempD.add(point);

				}

			}
		}
		return tempD;

	}

	/** @link dependency */
	/* # BasicImageStructure lnkBasicImageStructure; */

	public void paint(Graphics2D g) {
		drawStroke(g);
	}

	public void setParam(ArrayList Param) {

	}

	// public InkObject createSubInkObject(int start, int end){
	//		
	// // return a segment from this stroke that will contain the points of the
	// stroke.
	// return createSubSegment(start,end);
	//	
	// }
	@Deprecated
	public Segment createSubSegment(int start, int end) {
		// return a segment from this stroke that will contain the points of the
		// stroke.
		return null;

	}

	@Deprecated
	public SegmentCluster createSubSegments() {
		// from fit or dividtion
		return null;
	}

	@Deprecated
	public SegmentCluster createSubSegments(int count) {
		// each count points create a new segment and add then to the stroke
		return null;
	}

	@Deprecated
	public SegmentCluster createSubSegments(ArrayList vertices) {
		return null;
	}

	public ArrayList<Segment> createSubSegments(SegmentedShape segmentShape) {
		// set subSegments......

		subSegments = new ArrayList<Segment>();
		Segment temp;
		// get segments count
		int Segmentscount = segmentShape.getSegmentsCount();
		if (logger.isDebugEnabled()) {
			//  logger.debug("createSubSegments(SegmentedShape) - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@----  segment `count is " + Segmentscount + " (" + this.getClass().getSimpleName() + "    " + (new Throwable()).getStackTrace()[0].getLineNumber() + "  )  "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		// for each shape
		for (int i = 0; i < Segmentscount; i++) {

			temp = segmentShape.getSegmentOfIndex(i);

			subSegments.add(temp);

		}
		return subSegments;
	}

	private ArrayList<Segment> subSegments;
	private boolean RepeatRemoved;
	private PointData SmallestX=null;
	private PointData SmallestY=null;
	private PointData LargestX=null;
	private PointData LargestY=null;
	private boolean	SelfIntersect;
	private int	SelfIntersectionCount;
	private int	OverTraceBlockCount;

	@Override
	public InkInterface createSubInkObject(int start, int end) {
		// {
		//	
		//	
		//	
		Stroke ink = new Stroke();
		ArrayList<PointData> po = new ArrayList<PointData>();
		if (this.points != null) {
//			logger.info("   number of ponit in this stroke = "+this.points.size()+" (" + this.getClass().getSimpleName()
//					+ "    "
//					+ (new Throwable()).getStackTrace()[0].getLineNumber()
//					+ "  )  ");
			for (int i = start; (i < this.points.size()) && (i < end); i++) {
				
				po.add(this.points.get(i));
			}

		}
		ink.setPoints(po);
		// return a segment from this stroke that will contain the points of the
		// stroke.
		return ink;
		//
		// }

	}

	/**
	 * @return the interpolated
	 */
	public boolean isInterpolated() {
		return interpolated;
	}

	/**
	 * @param interpolated the interpolated to set
	 */
	public void setInterpolated(boolean interpolated) {
		this.interpolated = interpolated;
	}

	public void wirte(Logger log) {
		StringBuilder  str=new StringBuilder();
		str.append("Stroke has ").append( points.size()).append(" points = [");
		 for (int i = 0; i < this.points.size(); i++) {
		str.append ("P(").append(points.get(i).x).append(",").append(points.get(i).y).append("),");
	}
		 log.info(str);
		 log.info(" Bonding box is  = Corner P("+ this.getBox().getX()+", "+this.getBox().getY()+"), w= "+ this.getBox().getWidth()+", h=  "+this.getBox().getHeight());
		 
	}
	public Stroke RemoveRepeatedPoints(){
		if (this.points.size()>0){
			logger.info(" the number of points before removal is "+this.points.size());
		double	thershold=SystemSettings.ThresholdDistancePoint*this.getLength();
			Stroke NewInterploated=new Stroke();
			//  ArrayList pointsa = new ArrayList<PointData>();
			 
		        PointData prev =this.points.get(0);
		        PointData point=null;
		        NewInterploated.addPoint(prev);
		        NewInterploated.setStartPoint(prev);
		        
		        if (debug)
		        {
		        	if (DrawingDebugUtils.DEBUG_GRAPHICALLY){
		        		
		        		DrawingDebugUtils.drawPointPath(DrawingDebugUtils.getGraphics(), DrawingDebugUtils.InkColor, DrawingDebugUtils.PointsColor, this.points);
		        		
		        	}
		        }
		        
		        
		     //   pointsa.add(prev);
		        for(int i = 1; i < points.size(); i++)
		        {
		             point = points.get(i);
	//	            double dist = point.distance(prev);
		             // check the x and y and time of 
		             
		             if (point.x!=prev.x && point.time!=prev.time && point.y!=prev.y  )
		             {  // no x or y or time  
		            	 
		            	 prev=point;
		            	 NewInterploated.addPoint(point);
		             }
		             else {
		            	 logger.info("the point has same in one dimention... "+prev +"prev   point "+point);
		            	 if (point.x==prev.x && point.time==prev.time)
			             {// same x 
			            	 //skip 
			             }
		            	 else if (point.y==prev.y && point.time==prev.time){
			            	 // same y 
			            	// skip  
			             } 
		            	 else {
		            		 // only one is the same...
		            		 
		            		 // check if distance is larger than thershol 
		            	  double dist = point.distance(prev); 
		            		  // Maybe not same x or same y but same time... 
		            		 if (point.time==prev.time){
		            			 //same time 
		            			 if (dist>thershold){
		            				 prev=point;
					            	 NewInterploated.addPoint(point);
		            			 }
		            			 ////////////
		            			// 
		            	 }
		            	  else {/// either x 
		            	//	 logger.info(" in state where not equal time but equal either x or y");
		            		 // x may = x but we not in same time.
		            		 /// y may = prev y but not in same time 
		            			 prev=point;
				            	 NewInterploated.addPoint(point);
		            			 
//		            		 long diffTime=point.time-prev.time;
//		            		 if (diffTime>SystemSettings.ThresholdTimeADD){
//		            			 
//		            			 prev=point;
//				            	 NewInterploated.addPoint(point);
//		            		 }
//		            		 else {
//		            			 
//		            			 
//		            		 }
		            		 // check time. 
		            		 
		            	 }
		            	 }
		            	
		             }
		             

		            
		        }
		        if (point!=null)
		        	NewInterploated.setEndPoint(point );
		        
		        
		        NewInterploated.setRepeatedRemoved(true);
		        logger.info("number of ponits after removal of repeated "+NewInterploated.getPointsCount());
		        
		        if (debug)
		        {
		        	if (DrawingDebugUtils.DEBUG_GRAPHICALLY){
		        		
		        		DrawingDebugUtils.drawPointPath(DrawingDebugUtils.getGraphics(), Color.green, Color.ORANGE,NewInterploated.points);
		        		
		        	}
		        }
		        
		        
		        
		        return   NewInterploated;
			
			}
			else{
				
				return this;
			}
		
	}

	private void setRepeatedRemoved(boolean b) {
	 RepeatRemoved=b;
		
	}
	public PointData getLargestChordStart(){
		if ( Orientation==ORIENTATION_HORIZONATAL)
		{
			return LargestX;
		}
		else {
			return LargestY;
		}
	}
public PointData getLargestChordEnd(){
	if ( Orientation==ORIENTATION_HORIZONATAL)
	{
		return SmallestX;
	}
	else {
		return  SmallestY;
	}
	}
private void computeLongestDistance(){
		LongestDistanceBetweenPointsInStroke =0;
	 
		 if (points==null)
			 return ;
		 
		 SmallestX=points.get(SortedXIndex.get(0));
		 SmallestY=points.get(SortedYIndex.get(0));
		 LargestX=points.get(SortedXIndex.get(SortedXIndex.size()-1));
		 LargestY=points.get(SortedYIndex.get(SortedYIndex.size()-1));
		
		 double dis1=LargestX.distance(SmallestX);
		 double dis2=LargestY.distance(SmallestY);
		 double dis3=this.EndPoint.distance(StartPoint);
		 
		 logger.info(" The distance from last x to first x is "+dis1);
		 logger.info(" The distance from last y to first y is "+dis2);
		 logger.info(" The distance from end point to start   "+dis3);
		 
		 if (dis1>dis2){
			 LongestDistanceBetweenPointsInStroke=dis1;
			 Orientation=ORIENTATION_HORIZONATAL; //|
			 
				 
		 }
		 else {
			 LongestDistanceBetweenPointsInStroke=dis2;
			 Orientation=ORIENTATION_VERTICAL;
			
		 }
		 logger.info("the length is  "+getLength());
		 EndPointtoLengthRation=dis3/getLength();
		 logger.info("  EndPointtoLengthRation  "+ EndPointtoLengthRation);
		 
		 LongestChordtoLengthRatio=LongestDistanceBetweenPointsInStroke/getLength();
		
		 logger.info(" 	 LongestChordtoLengthRatio "+	 LongestChordtoLengthRatio );
		 
		 
		 
}
private void checkClosedShape(){
 
}
 class OverTraceBlock{
	 
	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OverTraceBlock [Overtrace=" + Overtrace + "  F(s,e) = (" +startF 
				+ ","+endF+")    with the O =(" + startO +", "+endO+" ) "+ ", intersection=" + intersection
				+ ", size=" + size +  "]";
	}
	int startF, endF; 
	 int startO, endO;
	 int size;
	 
	 boolean intersection=false;
	 boolean  Overtrace=false; 
	 
	 
 }
private void checkOverTraceAndSelfIntersect(){
	logger.info("  OverTraceHyposes  = "+OverTraceHyposes+"  number of points is "+points.size());
	 logger.info( "   if overtrace/points  "+( (double)OverTraceHyposes/(double)points.size() )+
			 "  if  overtrace/(size/2) " +((double)OverTraceHyposes/((double)points.size()/2.0)));
	 
	 logger.info(  "  and location of point is   "+   OverTracePoints);
	 
	 
	 logger.info( "  the pair points are "+OverTracePair);

	// double OverTracePercent=(double)OverTraceHyposes/(double)points.size();
	 
	 // now in need to add the add the list use overtrace point into a sorted list to get the overstroked sectin....
	 
	// iit should be ordered as it is the sequence of adding 
	 
	 
	 
//	 // make the default that it is deleted...
//		if (SystemSettings.REMOVE_OVER_TRACE){
//			
//			int in;
//			for (int k = 0; k < OverTracePoints.size(); k++) {
//				
//				in=OverTracePoints.get(k);
//				points.get(in).setDeleted( true);
//			}
//	
//		}
//		
		ArrayList<OverTraceBlock> z=new ArrayList<OverTraceBlock>();
		OverTraceBlock zone=null;
		 // now get zones of overtracesss....
		// the sturcture of zone is as follwing 
	 int window=SystemSettings.WINDOW_SCAN_SIZE;
	 int count=0;
	 int prev=0;
	 int prevIndex=0;
	 SelfIntersectionCount=0;
	 OverTraceBlockCount=0;
	 if (OverTracePoints!=null)
		// now to detect part we needd to trace to check if it contious part... 
		 for (int i = 0; i < OverTracePoints.size()-1; i++) {
			 if (zone==null){
			 zone=new OverTraceBlock();
			 zone.startO=OverTracePair.get(i).y;
			 zone.startF=OverTracePair.get(i).x;
			 count=0;
			 }
			 
		 
				 int current=OverTracePoints.get(i);
				 int next=OverTracePoints.get(i+1);
				 
				if ((next-current)>window){// differenct between next overtrace and current is > window
				// jump or single overtrace section...
					logger.info("  BREAAAAA KK of OVERTRACEE>>>>>>>>>>>>>>> at   "+i);
					if (i-prevIndex>window){
						// this is a large over traced part
						zone.Overtrace=true;
						 zone.endO=OverTracePair.get(i).y;
						 zone.endF=OverTracePair.get(i).x;
						 count++;
						 zone.size=count;
						 OverTraced=true;
						 OverTraceBlockCount++;
						// prevIndex=i+1;
						 
					}
					else {
						// this may be a single pont... 
						zone.intersection=true;
						SelfIntersect=true;
						 zone.endO=OverTracePair.get(i).y;
						 zone.endF=OverTracePair.get(i).x;
						 SelfIntersectionCount++;
						 count++;
						 zone.size=count;
					}
					
					z.add(zone);
					zone=null;
					prev=current;
					prevIndex=i+1;
				}
				else {
					// the are less than window 
					// connut to add size 
					 zone.endO=OverTracePair.get(i).y;
					 zone.endF=OverTracePair.get(i).x;
					 count++;
					 zone.size=count;
					
				}
				 
		 }
		 if (zone!=null){
				zone.Overtrace=true;
				OverTraced=true;
			 z.add(zone);
		 }
	// setting the deleted points from the  stroke... 
		 for (int j = 0; j < z.size(); j++) {
			if (z.get(j).Overtrace){
		 
				int s , e; 
				s=z.get(j).startF;
				e=z.get(j).endF;
				for (int j2 = s; j2 < e; j2++) {
					points.get(j2).setDeleted( true);
				}
				
				
				
			}
		}
		 
	 logger.info("  zones of the over trace ... "+z);
	logger.info("  there are "+ SelfIntersectionCount+"   self intersection in this stroke "+"  and  "+ OverTraceBlockCount+"  overtraced blocks.. ");
//	 int cont=0;
//	 int lastindex=0;
//	 boolean breakIn=false;
//	 if (OverTracePercent>0.5){
//		 
//		 OverTraced=true;
//	 }
//	 else 
//	 // now to detect part we needd to trace to check if it contious part... 
//	 for (int i = 0; i < OverTracePoints.size()-1; i++) {
//		 
//		 
//		 int j=OverTracePoints.get(i);
//		 int j_1=OverTracePoints.get(i+1);
//		if ((j_1-j)>window){
//			// this is an end of section // get the last index of part..
//			breakIn=true;
//			
//			logger.info("  BREAAAAA KK of OVERTRACEE>>>>>>>>>>>>>>> at   "+j);
//			if (cont>window){
//				
//				// if cont > window then it is over trace section 
//				OverTraced=true;
//				
//				//  
//				if (SystemSettings.REMOVE_OVER_TRACE){
//					
//					int in=OverTracePoints.get(lastindex);
//					for (int k = lastindex; k < j; k++) {
//						
//						in=OverTracePoints.get(k);
//								points.get(in).setDeleted( true);
//					}
//			
//				}
//				
//			}
//			else {
//				SelfIntersect=true;
//			}
//			cont=0;
//		lastindex=i;	
//		}
//		else {
//			cont++;
//			
//		}
//	}
//	 
//	 // list of o
//	 if (!breakIn ){
//		 if (OverTracePoints.size()>window){
//			 	OverTraced=true;
//			 	
//			 	
//	
//				
//		 }
//	 }
//	 
//		 logger.info("    before delte size is   "+points.size());
//		if (SystemSettings.REMOVE_OVER_TRACE){
//			for (int i = 0; i < points.size(); i++) {
//				if (points.get(i).isDeleted()){
//					points.remove(i);
//				}
//			}
			OverTracePointsDeleted=true;
//		}
//		 logger.info(" after    delte size is "+points.size());
			 logger.info("  this is overtrace  "+OverTraced+"   and self intersection "+SelfIntersect);
}
private boolean isOverTraced( ){
	
	//TODO: Commplet the over traceb by finishing this function ( check if zone is an overtraced by detecting is neear enough and if parallel )
	return OverTraced;
} 
private boolean isSelfIntersect( ){
	//TODO: Commplet the  self intersecting by finishing this function ( check if zone is an overtraced by detecting is neear enough and if intersect )

	return SelfIntersect;
} 
private void checkTails(){
	// check if thre is tails in the 
	//create a parts with the last 10% and first 10 of the stroke...
	int part=(int) (Math.ceil((0.1)*this.points.size()));
	
	if (part<3){
		part=3;
	}
InkInterface start = this.createSubInkObject(0,part);
	InkInterface end = this.createSubInkObject(this.points.size()-part-1,this.points.size()-1);
	 
	if (start.canIntersect(end)){
		
		//TODO: now i need to detect the intersections... 
		
	}
	
	
}

	public void PreProcess() {
	  	//TODO: IMPLEMENT THIS FUNCTION 28 JAN
		 logger.info(" PreProcess	//TODO: IMPLEMENT THIS FUNCTION 28 JAN  ");
		 updateOtherFeatures();
//		 logger.info("  OverTraceHyposes  = "+OverTraceHyposes+"  number of points is "+points.size());
//		 logger.info( "   if overtrace/points  "+( (double)OverTraceHyposes/(double)points.size() )+
//				 "  if  overtrace/(size/2) " +((double)OverTraceHyposes/((double)points.size()/2.0)));
		 
	
		 
		 
		computeLongestDistance();
	

		checkOverTraceAndSelfIntersect();
				checkTails();
			checkClosedShape();
	}
public Stroke getUnTracedStroke(){
	
	if (OverTraced){
	
		// if this stroke is over traced then do the following...
		
		// create a new stroke with the same points... 
		
		Stroke st=new Stroke();
	st.LocationRange=this.LocationRange*2.0;
	st.window=this.window*2;
	st.Orginalpoints=this.points;
for (int i = 0; i <this.points.size(); i++) {
		if (!this.points.get(i).isDeleted())
		{
			st.addPoint(this.points.get(i));

			
			if (st.points.size()==1){
           st.setStartPoint(this.points.get(i));
			}
		}
}


		st.setEndPoint(st.getPoints().get( st.getPointsCount()-1));
                // check if there is another over trace in the new stroke... 
		
			st.PreProcess();
			
			if (st.OverTraced){
				
				
				Stroke st2=new Stroke();
				st2.LocationRange=st.LocationRange*2.0;
				st2.window=st.window*2;
				
			for (int i = 0; i <st.points.size(); i++) {
					if (!st.points.get(i).isDeleted())
					{
						st2.addPoint(st.points.get(i));

						
						if (st2.points.size()==1){
			           st2.setStartPoint(st.points.get(i));
						}
					}
			}


					st2.setEndPoint(st2.getPoints().get( st2.getPointsCount()-1));
					
					
					st2.PreProcess();
					st2.Orginalpoints=this.points;
					return st2;
					
					
			}
//             if (st.OverTraced){
//            	 
//            	 return st.getUnTracedStroke();
//             }
             else {
            	 
            	 st.OverTraceRemoved=true;;
            	 return st;
             }
		
	}
	
	
	return this;
}

	
	
    /**
     * Transform the polyline with the given transform.
     */


}
