package SketchMaster.Stroke.StrokeData;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;




import SketchMaster.system.SystemSettings;
import SketchMaster.Stroke.features.FeatureFunction;
import SketchMaster.Stroke.graphics.shapes.GuiShape;
import SketchMaster.Stroke.graphics.shapes.SegmentedShape;
import SketchMaster.gui.DrawingDebugUtils;
import SketchMaster.lib.ComputationsGeometry;
import SketchMaster.system.SystemSettings;

/**
 * @author Mahi
 */
public class Stroke extends SimpleInkObject implements Serializable, GuiShape {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Stroke.class);
    private static boolean debug=false;
	// private PointData[] points;

	public static double RectangleLoopThreshold = 50;
	private transient static boolean onLine = SystemSettings.OnLineComputations;
    private boolean interpolated=false;
	/**
	 * 
	 */
    ArrayList<Integer>  SortedXIndex=null;
    ArrayList<Integer>  SortedYIndex=null;
    ArrayList<Double>  DistantFromStart=null;
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


	private PointData StartPoint = null;

	private PointData EndPoint = null;

	private boolean dirty = true;// dirty mean not finish editing.

	private transient StrokeStatisticalData StatisticalInfo = null; // stroke


	private PointData MaxDirection;
	private PointData MinDirection;
	
	
	// computed
																	// StatisticalInfo

	public Stroke() {
		StatisticalInfo = new StrokeStatisticalData();
		StatisticalInfo.initAll();
		points = new ArrayList<PointData>();
		   SortedXIndex=new ArrayList<Integer>();
		      SortedYIndex=new ArrayList<Integer>();
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
		  ArrayList pointsa = new ArrayList<PointData>();
		 
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
          
		
		logger.info( "  now all point are intered i need to display the sort list of x an y ");
		logger.info( " points   "+points);
		logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
		logger.info("SortedXIndex    " + SortedXIndex);
		logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
		logger.info(" SortedYIndex   "+ SortedYIndex);
		//setLoopingEnd(this.StartPoint, this.EndPoint);

		
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
			double rot=this.getStatisticalInfo().TotalRotation()/(2.0*Math.PI);
			logger.info("  the total rotation of the  = " + this.getStatisticalInfo().TotalRotation());
			logger.info( " The sum of the  drection graph is  "+direction.getSumUpNow() );
			
			logger.info("  revolution is   "+rot);
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
		// System.out.println(point);
		// / adding point to the array
		this.points.add(point);
		// after add point chek that this point 
		addPointToSortedLists(point);
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
	private void addPointToSortedLists(PointData point){
		if (	this.points.size()==1){
			// this is the first point... 
			// do the follwoing 
			SortedXIndex.add(0);
			SortedYIndex.add(0);
			
		}
		else{
			// this point is added to list of point at the end
			double x,y;
			x=point.x;
			y=point.y;
//			String str="";
//			for (int i = 0; i < points.size(); i++) {
//				str+=" X ("+i+" )= "+points.get(i).x+" "; 
//			}
//			
//			 logger.info( "  the x is "+x);
//			 
//			 logger.info( str );
			 
			int newIndexpoint=BinarySearch(SortedXIndex,x, 0);
		 //  logger.info( "  the index found by binary search is "+newIndexpoint);
			if (newIndexpoint==-1){
				// add at the begining 
				SortedXIndex.add(0, this.points.size()-1);
			}
			else if (newIndexpoint==-2){
				SortedXIndex.add(this.points.size()-1);
			}
			else {
				SortedXIndex.add(newIndexpoint, this.points.size()-1);
			}
			
		   
			newIndexpoint=BinarySearch(SortedYIndex,y, 1);
			if (newIndexpoint==-1){
				// add at the begining 
				SortedYIndex.add(0, this.points.size()-1);
			}
			else if (newIndexpoint==-2){//at the end 
				SortedYIndex.add(this.points.size()-1);
			}else {
				SortedYIndex.add(newIndexpoint, this.points.size()-1);
				
			}
			
//			logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
//			logger.info("SortedXIndex    " + SortedXIndex);
//			logger.info(" $%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
//			logger.info(" SortedYIndex   "+ SortedYIndex);
		}
		 
	}
//	/* BinarySearch.java */
//	public class BinarySearch {
//		public static final int NOT_FOUND = -1;
//	 
//		public static int search(int[] arr, int searchValue) {
//			int left = 0;
//			int right = arr.length;
//			return binarySearch(arr, searchValue, left, right);
//		}
//	 
//		private static int binarySearch(int[] arr, int searchValue, int left, int right) {
//			if (right < left) {
//				return NOT_FOUND;
//			}
//			int mid = (left + right) >>> 1;
//			if (searchValue > arr[mid]) {
//				return binarySearch(arr, searchValue, mid + 1, right);
//			} else if (searchValue < arr[mid]) {
//				return binarySearch(arr, searchValue, left, mid - 1);
//			} else {
//				return mid;
//			}		
//		}
//	}
	
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
        if (type==0){
            testValue=this.points.get( arr.get(high)).x;
        }
            else{ 
                testValue=this.points.get( arr.get(high)).y;
            }
        if (value>testValue){
        	return -2;
        	}
        if (type==0){
            testValue=this.points.get( arr.get(low)).x;
        }
            else{ 
                testValue=this.points.get( arr.get(low)).y;
            }
        
        if (value<testValue){
        	return 0;
        	}
       
        
        
        while (low <= high)
        {
        	//logger.info( "  low is "+low+"  hight is "+high);
            midpoint = (low + high) / 2;
           
             if (type==0){
             testValue=this.points.get( arr.get(midpoint)).x;
             //if (midpoint>0){  
             if (midpoint>1&&midpoint<arr.size()-1){
             tpointb=this.points.get( arr.get(midpoint-1)).x;
             tpointa=this.points.get( arr.get(midpoint+1)).x;
             }
             else {
             tpointa=testValue;
            	 tpointb=testValue;
             }
             
             }
             else{ 
             testValue=this.points.get( arr.get(midpoint)).y;
             if (midpoint>1&&midpoint<arr.size()-1){
            // if (midpoint>0){  
             tpointb=this.points.get( arr.get(midpoint-1)).y;
             tpointa=this.points.get( arr.get(midpoint+1)).y;
             
             }
             else {
            	 tpointa=testValue;
            	 tpointb=testValue;
             }
             
             
             
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

	// @Deprecated
	// private void updateStatiscalData(PointData point){
	//		
	//	  
	// if (points.size()==1)
	// {
	// distanceCalculationFeature function
	// =(distanceCalculationFeature)StatisticalInfo.getDistance().getFunc();
	// function.setNoPointInStroke(points.size());
	// // initalize the functions
	// StatisticalInfo.getDistance().updateFunctionWithPoint(point, this);
	// StatisticalInfo.getSpeed().updateFunctionWithPoint(point, this);
	// StatisticalInfo.getTimeDiff().updateFunctionWithPoint(point, this);
	// StatisticalInfo.getDirection().updateFunctionWithPoint(point, this);
	// curvatureCalculateionFeature funct =
	// (curvatureCalculateionFeature)StatisticalInfo.getCurvature().getFunc();
	// funct.setStroke(this);
	// StatisticalInfo.getCurvature().updateFunctionWithPoint(point, this);
	// }
	// if (points.size()>=3)
	// {
	// distanceCalculationFeature function
	// =(distanceCalculationFeature)StatisticalInfo.getDistance().getFunc();
	// function.setNoPointInStroke(points.size());
	// // start calcuating
	// StatisticalInfo.getDistance().updateFunctionWithPoint(point, this);
	//			
	// StatisticalInfo.getSpeed().updateFunctionWithPoint(point, this);
	// StatisticalInfo.getTimeDiff().updateFunctionWithPoint(point, this);
	// StatisticalInfo.getDirection().updateFunctionWithPoint(point, this);
	// curvatureCalculateionFeature funct =
	// (curvatureCalculateionFeature)StatisticalInfo.getCurvature().getFunc();
	// funct.setStroke(this);
	// StatisticalInfo.getCurvature().updateFunctionWithPoint(point, this);
	// //StatisticalInfo.getCurvature().updateFunctionWithPoint(point, this);
	//			
	// // now every function is updataed
	// }
	//		
	// }
	// private void updateBoundingBox(PointData point){
	//		
	// //if this is the first point then set it with the rectangle both
	// //top left and right low corners
	// if (points.size()==1)
	// {
	// Rectangle r=new Rectangle();
	// r.add(point.getPointLocation());
	// if (StatisticalInfo.getBox()!=null)
	// StatisticalInfo.getBox().setBounds(r);
	// else
	// StatisticalInfo.setBox(r);
	// }
	// // else this is just a new point i have to check
	// else if (points.size()>1){
	// Rectangle currentrectangle= StatisticalInfo.getBox();
	//				
	// //1 )if it is out of the current rectangle or in
	// if(currentrectangle.contains(point.getPointLocation()))
	// {
	// //it is contain
	// // if in do no action
	// }
	// else
	// {
	// // if out
	// //add this point to the rectangle to create a larger one
	// currentrectangle.add(point.getPointLocation());
	//					
	// }
	//			
	//			
	//			
	//		
	// }
	//		
	//		
	// }
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
			g.drawLine((int) ((PointData) points.get(i)).getX(),
					(int) ((PointData) points.get(i)).getY(),
					(int) ((PointData) points.get(i + 1)).getX(),
					(int) ((PointData) points.get(i + 1)).getY());
			
			
			if (SystemSettings.DrawPoints)
			{
				g.setColor(Color.BLUE);
				g.drawRect((int) ((PointData) points.get(i)).getX(),
						(int) ((PointData) points.get(i)).getY(),
						2,2);
				g.fillRect((int) ((PointData) points.get(i)).getX(),
						(int) ((PointData) points.get(i)).getY(),
						2,2);
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
				// System.out.println( " x "+StatisticalInfo.getBox().getX()+" y
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

		// System.out.println("------------------------------Error---------
		// -------------------");
		// System.out.println(this.toString());
		// System.out.println("nubmer of polygong in this
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
		// System.out.println("the avergaeof curvature is "+func.getAverage());
		// System.out.println("the threshold average is "
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
		// System.out.println("number of dominat points is "+tempD.size());
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
			// System.out.println("frome time "+indeces2.size());
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

	@Override
	public InkInterface createSubInkObject(int start, int end) {
		// {
		//	
		//	
		//	
		Stroke ink = new Stroke();
		ArrayList<PointData> po = new ArrayList<PointData>();
		if (this.points != null) {
//			System.out.println("   number of ponit in this stroke = "+this.points.size()+" (" + this.getClass().getSimpleName()
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
		String str="Stroke has "+ points.size() +" points = [";
		 for (int i = 0; i < this.points.size(); i++) {
		str+="P("+points.get(i).x+","+points.get(i).y+"),";
	}
		 log.info(str);
		 log.info(" Bonding box is  = Corner P("+ this.getBox().getX()+", "+this.getBox().getY()+"), w= "+ this.getBox().getWidth()+", h=  "+this.getBox().getHeight());
		 
	}
	public Stroke RemoveRepeatedPoints(){
		if (this.points.size()>0){
			logger.info(" the number of points before removal is "+this.points.size());
		double	thershold=SystemSettings.ThresholdDistancePoint*this.getLength();
			Stroke NewInterploated=new Stroke();
			  ArrayList pointsa = new ArrayList<PointData>();
			 
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


private void procesSortedX(){
	
	
//	self intersectin, overtrace, 
//	process sorted x values to region
//	either 
//	1. decreasing 
//	2. increasing 
//	3. chanign (rapid index changing )
//
//	in decreasing and increasing is straight line or curve... non intersecting , non 
//	in change section (found by first different for example 1 2 3  200 4 203 5 6  )  then "3 200 4 203 5 " is a change region..(also could include 2,6).
//	There can be three explanation...
//	normal drawing  (check the the y values of  the points and if they are noot near (threshold controled by stroke length and max length between points in stroke  ) then it is normal ....
//
//
//	2. stroke is self intersecting.... (check if the segment interstion (create line of the segments 
//	in previous example ... 3 4 5 6 is a segment 200 203 is another ==> create lines and check if theses lines are intersecting or paralle 
//	if parallel then ==> overtrace....
//	if intersecton then ==> self intersection...
//
//	3. the stroke is overtraced.
	int zone=0;  // 0 inc, 1 dec, 2 changing
	boolean zoneChanged=false;
	 
	int start=0;
	int end=0; 
	int prevx=0,x;
	int dif;
	int pervZone;
	int count=0;
	int countDec=0, countInc=0,countChange=0;
	int[] decIncPattern=new int[SortedXIndex.size()-1];
	ArrayList<zone> zones=new ArrayList<zone>();
	if(SortedXIndex!=null){
		for (int i = 0; i < SortedXIndex.size(); i++) {
			zoneChanged=false;
			if(i==0){
				start=i;
				prevx=SortedXIndex.get(i) ;
				continue;
			}
			if (i==1){
				
				x=SortedXIndex.get(i);
				if (prevx>x){
					
					zone=1;//dec
				
				}
				else if (x>prevx)
				{
					
					zone=0;//inc
				}
				decIncPattern[i-1]=zone;
				prevx=x;
				continue;
			}
			/////////////////////////this is just for itinitalize..
			x=SortedXIndex.get(i);
			
			// check if less move in same pattern...
			// make different
			dif=x-prevx;
			if(dif>0){  // then x > prevx 
				decIncPattern[i-1]=0;

				if (zone==0){// was inc and now inc
					prevx=x;
				}
				else if (zone==1){/// was dec but now it is inc
					end=i;
					count=end-start;
					// the new start;;;..
					start=i;
					zone=0; // the new zone...
					prevx=x;
					zoneChanged=true;
					countChange++;
				}

			}// dif>0
			else{  //dec range ...
				
				decIncPattern[i-1]=1;
				
				if (zone==0){// was inc and now dec
					end=i;
					count=end-start;
					// the new start;;;..
					start=i;
					zone=1; // the new zone...
					prevx=x;
					zoneChanged=true;
				
				}
				else if (zone==1){/// was dec but now it is inc
					prevx=x;
				}
			}//else dec
			
			if(zoneChanged){
				zone z=new zone();
				z.start=end-count;
				z.count=count;
				z.end=end;
				if (zone==1){
					countInc++;
					z.type=0;
				}
				else{
					z.type=1;
					countDec++;
				}
				zones.add(z);
			
				//create a zone and add it...
				
			}
			
		}//for loop ...
		
		logger.info(" orignal zones "+zones);
	 logger.info("  number of increase zones. "+countInc+"  count of dec"+countDec);
		zone z;
		int prevtype=0;
		ArrayList<zone> finalzones=new ArrayList<zone>();
		zone temp=null;
		int countz=0;
		// now looop on zones to if zone coutn <2 then merge into 
		for (int j = 0; j < zones.size(); j++) {
			if (j==0){
			prevtype=zones.get(j).type;
			temp=null;
			}
			
			z=zones.get(j);
			
			if(z.count<4){
				if(temp==null){
				// he number ofregion is smakk...
			//now i need to merge this zone to next 	
				temp=new zone();
				temp.start=z.start;
				temp.type=2;
				temp.count=z.count;
				temp.end=z.end;
				}
				else {
					temp.count=temp.count+z.count;
					temp.end=z.end;
				}
			}
			else {
				if (temp==null) //normal zone...
				{
				finalzones.add(z);
				}//
				else{// the previous zones was a changing,,, ones.
					countz++;
					finalzones.add(temp);
					finalzones.add(z);// the current zone...
					temp=null;
					
				}
				
			}
			
			
			
		
		}
		logger.info(" Finalzone...  "+finalzones);
			
			logger.info(" number of changing zone. is  "+countz);
			
			int counti=0;
			int countd=0;
			for (int i = 0; i < finalzones.size(); i++) {
				if (finalzones.get(i).type==0)
					counti++;
				else if (finalzones.get(i).type==1){
					countd++;
				}
				else {
					// the changing values....
					
				}
			}
			
			logger.info("  final  ince of "+counti+"  regions and dec of "+countd);
	}
}
class zone {
	@Override
	public String toString() {
		 String st=" type "+type+" start @ "+start+" end "+end+" count"+count;
		return st;
	}
	int type;
	int start;
	int end;
	int count;
}
private void checkOverTrace(){
	// proces teh stork x 
	procesSortedX();
	
}
private void checkTails(){
	
}

	public void PreProcess() {
	  	//TODO: IMPLEMENT THIS FUNCTION 28 JAN
		 logger.info(" PreProcess	//TODO: IMPLEMENT THIS FUNCTION 28 JAN  ");
		 updateOtherFeatures();
		computeLongestDistance();
		checkClosedShape();
		checkTails();
		checkOverTrace();
		
	}

	
	
	
    /**
     * Transform the polyline with the given transform.
     */


}