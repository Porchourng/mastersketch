/**
 * 
 */
package SketchMaster.system;

import java.awt.Component;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import SketchMaster.Stroke.StrokeData.SegmentCluster;
import SketchMaster.gui.DebugMessageWindow;
import SketchMaster.swarm.polygonApproximations.polygonSolution;
import SketchMaster.system.Recogniziers.RecognizierSystem;
import SketchMaster.system.Recogniziers.RubineRecognizier;

/**
 * @author Mahi
 * 
 */
public class SystemSettings implements  Cloneable {

	// public static intBugMeNot  firefox extension SystemModeTRAIN = 0;
	//
	// public static int SystemModeCLASSIFY = 1;
	//
	// public static int SystemModeCOLLECT = 2;
	//
	// public static int SystemModeTEST = 3;

	// public static int CLASSIFIER_IMAGE = 0;
	//
	// public static int CLASSIFIER_STAISTICAL = 1;
	//
	// public static int CLASSIFIER_STRUCTURE = 2;
	//
	// public static int CLASSIFIER_MULTIPLE = 3;

//	public static double Curvature_Thresold = 1.1;

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public static int DEFAULT_ZERNIKE_ORDER=10;
	public static final String StrokeLayerName = "Stroke Layer";
	public static  boolean DEBUG_MODE = true;
	public static final int MODE_DEMO=2;
	
	public static final int  MODE_DEBUG=1;
	public static final int MODE_NORMAL=0;
	 public static int MODE=0;
	public  static void setMode(int mode)
	{
		MODE=mode;		
		if (MODE==MODE_DEBUG)
			DEBUG_MODE=true;
		else 
			DEBUG_MODE=false;
		
	}
       
	public static final String SymbolLayerName = "Symbol Layer";
///////////////////////////////////////////RElated to swarm
	public static boolean Polygon_ACTIV_DEFAULT=false;
	public static boolean Curve_ACTIV_DEFAULT=true;
	public static boolean Symbol_ACTIV_DEFAULT=false;
	public static boolean Sezgin_ACTIV_DEFAULT=false;
	
	public static boolean FIT_LINE = false;
	public static boolean FIT_CURVE = true;
	public static boolean FIT_POLYGON =true;
	public static boolean FIT_DIVIDE_CURVE =  true;
	public static boolean FIT_SEGIZEN = false;
	
	
	public static int AGENT_SIZE = 25;
	public static  int SWARM_SYSTEM_MAX_ITERATION =160;// itried it on
	public static  int SWARM_SYSTEM_MAX_CIRCLE_ITERATION = 30;// itried it on
	
	public static double SWARM_CONSTANTS_C1=2;
	public static double SWARM_CONSTANTS_C2=2;
	public static double SWARM_CONSTANTS_W=5;
	public static double SWARM_CONSTANTS_VMAX=6;
	
	public static boolean SEGMENTATION_SWARM_SECOND_PASS=false;
	//public static double SEGMENTATION_SWARM_THRESHOLD=1e5;
	// ////////////////////////////////other constants related to the stroke
// bett
	public static final String CURVATURENAME = "Curvature of storke Vs. distance Using change in direction";
	public static final int STROKE_CONSTANT_NEIGHBOURS =4;// neary no change 7==> change segmentation dramatically. 
	public static final int STROKE_CONSTANT_CURVATURE_NEIGHBOURS=5;
	public static  double SEGZIN_ERROR_THRESHOLD = 3E4;//3E4
	public static double BEZIRE_ERROR_THRESHOLD = 150.0;//100
	public static  double CURVE_TEST_THRESHOLD = 1.01;
	public static  double HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD = 1e2; // alg 3 
	public static double DIGITAL_CURVE_DIVIDE_THRESHOLD=1e3;  //alg 2 
	public static  boolean DIGITAL_CURVE_MULTI_PREMETIVE=true;
	
	public static double 	SOLUTION_AlgS1_ERROR_TOLERANCE=1e3;  // was 90  I just wanto run.. 
	public static double    	SOLUTION_INITIAL_TOLERANCE=0.7;
	public static int POLYGON_ADJUST_Default = polygonSolution.POLYGON_ADJUST_BOTH;
	
	//////////////////////////////////////////////RElated to storke points 
	// count is used as min counts between segments. in the polygon soluions 
	public static int MinSegmentCountDefault=10;//5
	public static boolean UseResampling=false;// decrease when true better false
	//false;87.7237851662404
	public static boolean OnLineComputations =true;  // no change to output											// 500
	public static  double MaxInterplotionLength=15.0; // 88.7468030690537	
	public static final int DRAW_SYMBOL_OPTION = SegmentCluster.DRAW_ORIGINAL;

	public static   int   MIN_STROKE_PIXEL = 5;//

	public static final double ZERO = 1E-5;
	public static final double ZERO_COMPARE = 1E-2;
	public static final double ZERO_SLOPE = 0.001;
//--------------------------------drawing points 
	public static boolean STATISTICAL_POINTS_DRAW = false;
	
	
	
	public static final boolean DrawPoints = true;
	public static final boolean  DrawChords=true;

 
	public static int STROKE_DOMINATE_COLORING = 1;// 1 only dominate 2
													// onlycolor for each
													// functin // 3 both. //4 for different types 

	// ////////////////////////////////////
	public static boolean CURVEVATURE_ESTIMATION_1 = false;  //  Algorithm  FD
	public static boolean CURVEVATURE_ESTIMATION_2 = false;  // Algorithm HKAlg
	public static boolean CURVEVATURE_ESTIMATION_3 = true;  // Change in rotation
	public static boolean  USE_SPEED=true;
	public static boolean USE_DIREC=true;
	public static boolean USE_TIME_DIFF=true;
	
	public static final boolean CURVEVATURE_ESTIMATION_4 = true; // chang in direction eq.line  624
	
////////////////////////////////////Related to classsification 
	public static int RubineDefaultLoadOption = RubineRecognizier.RUBINE_LOAD_SYS;
	public static int CurrentRubineOperation = RubineRecognizier.Rubine_OPERATION_TRAINING;

	

	public static int CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_TRAIN;

	public static int CurrentRecognizierLoadOption = RecognizierSystem.RECGONIZE_LOAD_SYS;
	public static boolean CROSS_VALIDATE=false;
	
	public static double SVM_C=150; //SVM_C=145;
	public static double  SVM_GAMMA=0.1;//SVM_GAMMA=1.07;

	/***************************************************************************
	 * this line i wrote in windows
	 * 
	 * and this in linux after the set up and this again in windows then again
	 * in linuuuuuxxxxxxxxxxxxxxxat 11:24
	 **************************************************************************/
	// ////////////////STROKE TYPES
	public static final int STROKE_CURVE = 1;
	public static final int STROKE_CIRCLE = 2;
	public static final int STROKE_ELLIPSE = 3;
	public static final int STROKE_POLYGON = 4;
	public static final int STROKE_LINE = 5;
	public static final int STROKE_MULITSEG = 6;

	public static final int STROKE_ELLIPSE2 = 7;

	// ///////////////// SEGMENT TYPE
	public static final int SEGMENT_LINE = 7;
	public static final int SEGMENT_CURVE = 8;
	public static final int SEGMENT_ELLIPSE = 9;
	public static final int SEGMENT_CIRCLE = 10;
	public static final double EQUAL_LIMIT = 0.1;
	public static final double DISTANCE_LIMIT = 20;


	
	public static final double  collinearRange=200;
	public static final boolean CheckLabel = true;  //used in xml parser to check label _ +
	public static final boolean USE_PRE_RECOGNIZIER = true;
	public static final boolean USE_REMOVE_REPEAT= false;
	public static final double ThresholdDistancePoint = 0.0005;
	public static final double THERSHOLD_PRE_RECOGNITION_LINE_FIT_ERROR = 4;
	
	public static final double THERSHOLD_RECOGNITION_LINE_FIT_ERROR = 2 ;
	
	//////////////////////////FEATURES SELECTIONS
//My good features 
//	//alone 92.07161125319693 
//	public static boolean SYMBOL_FEATURES_ZENERIK_MOMEMENTS=true;
//	
//	public static boolean SYMBOL_FEATURES_PRIMITIVES=false;  //generally do nothing but can decrease  
//	public static boolean SYMBOL_FEATURES_PRIMITIVE_COUNT=false;
//	public static boolean SYMBOL_FEATURES_CONNECTIONS_COUNT=false	;
//	public static boolean SYMBOL_FEATURES_CENTROID=false;  // no change 
//	////////////////////////////////////////
//	public static boolean SYMBOL_FEATURES_CONVEXHULL=true;//good 
//	public static boolean SYMBOL_FEATURES_RATIOS=true;//very good with zen  95.39641943734016 
//	public static boolean SYMBOL_FEATURES_AREA=true;//good  93.60613810741688
//	public static boolean SYMBOL_FEATURES_DENSITY=true;  //good 
//	// all previous three  96.16368286445012 
//	public static boolean SYMBOL_FEATURES_LOGSAT=false;// decrease recognition
//	public static boolean SYMBOL_FEATURES_RUBINE_FEATURES=false;  //decrease recognition
	//set.SymbolFeaturesSettings(,,false,   false,      false,true,false,true,false,true );
	
	public static boolean SYMBOL_FEATURES_PRIMITIVES=true;//false the rpimitvewas
	public static boolean SYMBOL_FEATURES_PRIMITIVE_COUNT=true;// 
	
		public static boolean  SYMBOL_FEATURES_PRIMITIVE_COUNT_LINE=true;//
		public static boolean SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE=true;//false // addd this for set of list to cchck 
		public static boolean  SYMBOL_FEATURES_PRIMITIVE_COUNT_ELLIPSE=true;//false//
		
	public static boolean SYMBOL_FEATURES_CONNECTIONS_COUNT=true;//false//true;
	
		public static boolean SYMBOL_FEATURES_CONNECTIONS_COUNT_TYPE=true; //false
	
	public static boolean SYMBOL_FEATURES_CENTROID=true;//false //true;
	public static boolean SYMBOL_FEATURES_CONVEXHULL=true;//true;
	public static boolean SYMBOL_FEATURES_RATIOS=true;//true;
	public static boolean SYMBOL_FEATURES_AREA=true;
	public static boolean SYMBOL_FEATURES_LOGSAT=true; //was false
	public static boolean SYMBOL_FEATURES_DENSITY=true;
	public static boolean SYMBOL_FEATURES_RUBINE_FEATURES=true;//was false
	public static boolean SYMBOL_FEATURES_ZENERIK_MOMEMENTS=true;
	public static boolean  SYMBOL_FEATURES_COMPOSITE=true;//false;
	
	
//	public static boolean SYMBOL_FEATURES_CONNECTIONS_COUNT=true;
//	
//	public static boolean SYMBOL_FEATURES_CONNECTIONS_COUNT_TYPE=false;
//
//public static boolean SYMBOL_FEATURES_CENTROID=true;
//public static boolean SYMBOL_FEATURES_CONVEXHULL=true;
//public static boolean SYMBOL_FEATURES_RATIOS=true;
//public static boolean SYMBOL_FEATURES_AREA=true;
//public static boolean SYMBOL_FEATURES_LOGSAT=false;
//public static boolean SYMBOL_FEATURES_DENSITY=true;
//public static boolean SYMBOL_FEATURES_RUBINE_FEATURES=false;
//public static boolean SYMBOL_FEATURES_ZENERIK_MOMEMENTS=true;
	//-----------------------------------------------
	public static boolean CLUSTERING_AUTOMATIC=true;// 
	public static boolean CLUSTERING_USE_TIME=false;
	public static boolean CLUSTERING_USE_NSTROKE=true;
	public static boolean CLUSTERING_USE_LOCATIONS=true;
	public static double CLUSTERING_TIME_THRESHOLD=1000;
	public static double CLUSTERING_NSTROKE_THRESHOLD=20;
	public static double CLUSTERING_LOCATIONS_THRESHOLD=100;
	//--------------------------------------------------
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
	
		return super.clone();
	}


	
public static  String getString() {
		 
	String s="  ";
	String newline = System.getProperty("line.separator");
	s+=newline;
	s+="---------------------System settings-------------------------------";
	s+=newline;
	s+="   FIT_LINE=  " +	 FIT_LINE;// = FIT_LINE;
	s+="   FIT_CURVE= " + FIT_CURVE  ;
	s+="   FIT_POLYGON= " +	 FIT_POLYGON ;
	s+="     FIT_DIVIDE_CURVE  " +	 FIT_DIVIDE_CURVE  ;
	s+=newline;
	s+="     FIT_SEGIZEN  " +	 FIT_SEGIZEN ;
	s+="     POLYGON_ADJUST_Default  " + POLYGON_ADJUST_Default;
		
		//SystemSettingsSEGZIN_ERROR_THRESHOLD =se;

	s+="    BEZIRE_ERROR_THRESHOLD   " + BEZIRE_ERROR_THRESHOLD  ;

	s+="    CURVE_TEST_THRESHOLD    " +	 CURVE_TEST_THRESHOLD  ;
	s+=newline;
	s+="     HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD   " + HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD ;

	s+="     SOLUTION_ERROR_TOLERANCE  " + SOLUTION_AlgS1_ERROR_TOLERANCE;
		
	s+="     SOLUTION_INITIAL_TOLERANCE  " + SOLUTION_INITIAL_TOLERANCE;
		
	s+="    SEGMENTATION_SWARM_SECOND_PASS   " + SEGMENTATION_SWARM_SECOND_PASS;
		
	s+=newline;
		
	s+="     AGENT_SIZE  " +	 AGENT_SIZE;

	s+="    SWARM_SYSTEM_MAX_ITERATION     " + SWARM_SYSTEM_MAX_ITERATION  ;
		
	s+="    MinSegmentCountDefault   " + MinSegmentCountDefault;
		s+="    UseResampling   " + UseResampling;//false;
		s+=newline;
			//if (MaxInterplotionLength!=-1)
			s+="    MaxInterplotionLength   " + MaxInterplotionLength;
			
			s+="      MIN_STROKE_PIXEL " + MIN_STROKE_PIXEL;
			s+=newline;
				
			s+="    SWARM_CONSTANTS_C1   " + SWARM_CONSTANTS_C1;

			s+="     SWARM_CONSTANTS_C2  " + SWARM_CONSTANTS_C2;

			s+="     SWARM_CONSTANTS_W  " + SWARM_CONSTANTS_W;
//		if(SWARM_CONSTANTS_VMAX!=-1)
			s+="     SWARM_CONSTANTS_VMAX  " + SWARM_CONSTANTS_VMAX;
			s+=newline;
		
		
			s+="     SYMBOL_FEATURES_PRIMITIVES  " + SYMBOL_FEATURES_PRIMITIVES;
			s+="   SYMBOL_FEATURES_PRIMITIVE_COUNT    " + SYMBOL_FEATURES_PRIMITIVE_COUNT;
		
			s+="     SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE  " + SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE;
			s+="     SYMBOL_FEATURES_CONNECTIONS_COUNT  " + SYMBOL_FEATURES_CONNECTIONS_COUNT;
			s+=newline;
			s+="    SYMBOL_FEATURES_CENTROID   " + SYMBOL_FEATURES_CENTROID;
		
			s+="     SYMBOL_FEATURES_CONVEXHULL  " + SYMBOL_FEATURES_CONVEXHULL;
		
			s+="    SYMBOL_FEATURES_RATIOS   " + SYMBOL_FEATURES_RATIOS;
			s+="    SYMBOL_FEATURES_AREA   " + SYMBOL_FEATURES_AREA;
			s+=newline;
			s+="    SYMBOL_FEATURES_LOGSAT   " + SYMBOL_FEATURES_LOGSAT;
			s+="    SYMBOL_FEATURES_DENSITY   " + SYMBOL_FEATURES_DENSITY;
		
			s+="    SYMBOL_FEATURES_RUBINE_FEATURES   " +"      " + SYMBOL_FEATURES_RUBINE_FEATURES;
			s+="    SYMBOL_FEATURES_ZENERIK_MOMEMENTS   " + SYMBOL_FEATURES_ZENERIK_MOMEMENTS;
			s+=newline;
			 s+=" --------------------------------------------------------------- ";
			 s+=newline;
		return s;
	}
	
//	if (SystemSettings.DEBUG_GRAPHICALLY){
//	 paint(SystemSettings.getGraphics());
//	}

}