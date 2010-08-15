/**
 * 
 */
package settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import SketchMaster.lib.GeneralUtils;
import SketchMaster.swarm.polygonApproximations.polygonSolution;
import SketchMaster.system.SystemSettings;
import SketchMaster.system.SystemSettings;
import SketchMaster.system.Recogniziers.RecognizierSystem;

/**
 * @author Maha
 * 
 */
public class TestSketchSetting implements Serializable, Cloneable {
	// constants

	public TestSketchSetting() {
		super();
		SystemSettings.DEBUG_MODE=false;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static transient final Logger logger = Logger
			.getLogger(TestSketchSetting.class);
	public final static int SEGMENTATION_ALGORITHM_TESTED = 0;
	public final static int RECOGNITION_ALGORITHM_TESTED = 1;
	public final static int RUN_MODE_TRAIN = 0;
	public final static int RUN_MODE_TEST = 1;
	public final static int PREV_TRAINING = 1;
	public final static int PREV_NO_TRAIN = 0;
	public final static int DATA_SET_TYPE_XML = 0;
	public final static int DATA_SET_TYPE_DATABASE = 1;

	public static final int RUBINE_RECOGNIZER = 0;
	public static final int SYMBOL_RECOGNIZER = 1;
	public static final int SVM_RECOGNIZER = 2;
	private static final int AgentSize =10;
	private int MaxCatSize = 0;

	   boolean CheckLabel = false;  //used in xml parser to check label _ +
 boolean USE_NEW_CHANGES=false;
	 boolean USE_PRE_RECOGNIZIER =false;	
 boolean USE_PreProcess=false;
 boolean USE_REMOVE_REPEAT= false;
   boolean DIGITAL_CURVE_MULTI_PREMETIVE=false;
	
	
  boolean USE_SWARM_MODIFICATION = false;
 ;
 
 
	// SystemSettings settings=new SystemSettings();

	// options
	private String TestName = "Symbol recgonizier with n files an n features n algorithms";
	private int PreviousTrain = PREV_NO_TRAIN; // if there is a previous train.
	private int RunMode = RUN_MODE_TRAIN;
	private int RecognizierType = SYMBOL_RECOGNIZER;
	private boolean PauseSave = false;
	private int DataSetType = DATA_SET_TYPE_XML;
	private int SketchSystemTested = SEGMENTATION_ALGORITHM_TESTED;
	// this module will save setting for the test
	// the setting i change in the application
	// both on the database and on the sketch system level.
	private boolean displaySketch = false;

	private ArrayList<String> FilesNames = new ArrayList<String>();
	private ArrayList<String> TestFileNames = new ArrayList<String>();
	private int TestSize = 0;
	private int CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_TRAIN;
	private int OSType = SystemSettings.OS_WINDOWS;
	private boolean doTrain = true;
	private boolean FIT_LINE;
	private boolean FIT_CURVE;
	private boolean FIT_POLYGON;
	private boolean FIT_DIVIDE_CURVE;
	private boolean FIT_SEGIZEN;
	private int POLYGON_ADJUST_Default;
	private double BEZIRE_ERROR_THRESHOLD;
	private double CURVE_TEST_THRESHOLD;
	private double HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD;
	private double SOLUTION_ERROR_TOLERANCE;
	private double SOLUTION_INITIAL_TOLERANCE;
	private boolean SEGMENTATION_SWARM_SECOND_PASS;
	private double SWARM_CONSTANTS_C1;
	private double SWARM_CONSTANTS_C2;
	private double SWARM_CONSTANTS_W;
	private double SWARM_CONSTANTS_VMAX;
	private boolean FEATURES_PRIMITIVES;
	private boolean FEATURES_PRIMITIVE_COUNT;
	private boolean FEATURES_CONNECTIONS_COUNT;
	private boolean FEATURES_CENTROID;
	private boolean FEATURES_CONVEXHULL;
	private boolean FEATURES_RATIOS;
	private boolean FEATURES_AREA;
	private boolean FEATURES_LOGSAT;
	private boolean FEATURES_DENSITY;
	private boolean RUBINE_FEATURES;
	private boolean ZENERIK_MOMEMENTS;
	private int AGENT_SIZE;
	private int SWARM_SYSTEM_MAX_ITERATION;
	private int MinSegmentCountDefault;
	private boolean UseResampling;
	private double MaxInterplotionLength;
	private int MIN_STROKE_PIXEL;
	private boolean PRIMITIVE_COUNT_CURVE;
	private boolean FEATURES_PRIMITIVE_COUNT_ELLIPSE;
	private int momentsOrder;

	
	
	
	 
	boolean SaveToFile=true;
	private boolean SaveErrorCorrect=false;
	
	
	/**
	 * @return the uSE_NEW_CHANGES
	 */
	public boolean isUSE_NEW_CHANGES() {
		return USE_NEW_CHANGES;
	}

	/**
	 * @param uSENEWCHANGES the uSE_NEW_CHANGES to set
	 */
	public void setUSE_NEW_CHANGES(boolean uSENEWCHANGES) {
		USE_NEW_CHANGES = uSENEWCHANGES;
	}

	/**
	 * @return the uSE_PRE_RECOGNIZIER
	 */
	public boolean isUSE_PRE_RECOGNIZIER() {
		return USE_PRE_RECOGNIZIER;
	}

	/**
	 * @param uSEPRERECOGNIZIER the uSE_PRE_RECOGNIZIER to set
	 */
	public void setUSE_PRE_RECOGNIZIER(boolean uSEPRERECOGNIZIER) {
		USE_PRE_RECOGNIZIER = uSEPRERECOGNIZIER;
	}

	/**
	 * @return the uSE_PreProcess
	 */
	public boolean isUSE_PreProcess() {
		return USE_PreProcess;
	}

	/**
	 * @param uSEPreProcess the uSE_PreProcess to set
	 */
	public void setUSE_PreProcess(boolean uSEPreProcess) {
		USE_PreProcess = uSEPreProcess;
	}

	/**
	 * @return the uSE_REMOVE_REPEAT
	 */
	public boolean isUSE_REMOVE_REPEAT() {
		return USE_REMOVE_REPEAT;
	}

	/**
	 * @param uSEREMOVEREPEAT the uSE_REMOVE_REPEAT to set
	 */
	public void setUSE_REMOVE_REPEAT(boolean uSEREMOVEREPEAT) {
		USE_REMOVE_REPEAT = uSEREMOVEREPEAT;
	}

	/**
	 * @return the uSE_SWARM_MODIFICATION
	 */
	public boolean isUSE_SWARM_MODIFICATION() {
		return USE_SWARM_MODIFICATION;
	}

	/**
	 * @param uSESWARMMODIFICATION the uSE_SWARM_MODIFICATION to set
	 */
	public void setUSE_SWARM_MODIFICATION(boolean uSESWARMMODIFICATION) {
		USE_SWARM_MODIFICATION = uSESWARMMODIFICATION;
	}

	/**
	 * @return the saveToFile
	 */
	public boolean isSaveToFile() {
		return SaveToFile;
	}

	/**
	 * @param saveToFile the saveToFile to set
	 */
	public void setSaveToFile(boolean saveToFile) {
		SaveToFile = saveToFile;
	}

	/**
	 * @return the saveErrorCorrect
	 */
	public boolean isSaveErrorCorrect() {
		return SaveErrorCorrect;
	}

	/**
	 * @param saveErrorCorrect the saveErrorCorrect to set
	 */
	public void setSaveErrorCorrect(boolean saveErrorCorrect) {
		SaveErrorCorrect = saveErrorCorrect;
	}

	// SystemSettings systemSet=new SystemSettings();
	/**
	 * @return the displaySketch
	 */
	public boolean isDisplaySketch() {
		return displaySketch;
	}

	/**
	 * @param displaySketch
	 *            the displaySketch to set
	 */
	public void setDisplaySketch(boolean displaySketch) {
		this.displaySketch = displaySketch;
	}

	/**
	 * @return the sketchSystemTested
	 */
	public int getSketchSystemTested() {
		return SketchSystemTested;
	}

	/**
	 * @param sketchSystemTested
	 *            the sketchSystemTested to set
	 */
	public void setSketchSystemTested(int sketchSystemTested) {
		SketchSystemTested = sketchSystemTested;
	}

	public int getStudies() {

		return 1;
	}

	public int getAutors() {

		return 1;
	}

	public String getTestName() {
		return TestName;

	}

	public int getPreviousTrain() {

		return PreviousTrain;
	}

	public int getRunMode() {

		return RunMode;
	}

	public int getRecognizierType() {

		return RecognizierType;

	}

	public boolean getPauseSave() {

		return PauseSave;
	}

	public ArrayList<String> getFilesNames() {

		return FilesNames;
	}

	/**
	 * @return the testFileNames
	 */
	public ArrayList<String> getTestFileNames() {
		return TestFileNames;
	}

	/**
	 * @param testFileNames the testFileNames to set
	 */
	public void setTestFileNames(ArrayList<String> testFileNames) {
		TestFileNames = testFileNames;
	}

	public int getTestSize() {
		return this.FilesNames.size();
		// return TestSize;
	}

	public int getDataSet() {

		return DataSetType;
	}

	public int getCurrentRecognizierOperation() {

		return CurrentRecognizierOperation;
	}

	public  int getOsType() {

		return OSType;
	}

	public void ModifyForTrain() {
		ResetAllSettings();
		// now i am in train mode
		this.RunMode = this.RUN_MODE_TRAIN;
		CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_TRAIN;
		SystemSettings.CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_TRAIN;
		PreviousTrain = PREV_NO_TRAIN;

	}

	public void GetStoredSetting() {

		FIT_LINE = SystemSettings.FIT_LINE;// = FIT_LINE;
		FIT_CURVE = SystemSettings.FIT_CURVE;
		FIT_POLYGON = SystemSettings.FIT_POLYGON;
		FIT_DIVIDE_CURVE = SystemSettings.FIT_DIVIDE_CURVE;
		FIT_SEGIZEN = SystemSettings.FIT_SEGIZEN;
		POLYGON_ADJUST_Default = SystemSettings.POLYGON_ADJUST_Default;

		// SystemSettingsSEGZIN_ERROR_THRESHOLD =se;

		BEZIRE_ERROR_THRESHOLD = SystemSettings.BEZIRE_ERROR_THRESHOLD;

		CURVE_TEST_THRESHOLD = SystemSettings.CURVE_TEST_THRESHOLD;

		HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD = SystemSettings.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD;

		SOLUTION_ERROR_TOLERANCE = SystemSettings.SOLUTION_AlgS1_ERROR_TOLERANCE;

		SOLUTION_INITIAL_TOLERANCE = SystemSettings.SOLUTION_INITIAL_TOLERANCE;

		SEGMENTATION_SWARM_SECOND_PASS = SystemSettings.SEGMENTATION_SWARM_SECOND_PASS;

		AGENT_SIZE = SystemSettings.AGENT_SIZE;

		SWARM_SYSTEM_MAX_ITERATION = SystemSettings.SWARM_SYSTEM_MAX_ITERATION;

		MinSegmentCountDefault = SystemSettings.MinSegmentCountDefault;

		UseResampling = SystemSettings.UseResampling;// false;
		// if (MaxInterplotionLength!=-1)
		MaxInterplotionLength = SystemSettings.MaxInterplotionLength;

		MIN_STROKE_PIXEL = SystemSettings.MIN_STROKE_PIXEL;

		SWARM_CONSTANTS_C1 = SystemSettings.SWARM_CONSTANTS_C1;

		SWARM_CONSTANTS_C2 = SystemSettings.SWARM_CONSTANTS_C2;

		SWARM_CONSTANTS_W = SystemSettings.SWARM_CONSTANTS_W;
		// if(SWARM_CONSTANTS_VMAX!=-1)
		SWARM_CONSTANTS_VMAX = SystemSettings.SWARM_CONSTANTS_VMAX;

		FEATURES_PRIMITIVES = SystemSettings.SYMBOL_FEATURES_PRIMITIVES;
		FEATURES_PRIMITIVE_COUNT = SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT;

		PRIMITIVE_COUNT_CURVE = SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE;
		FEATURES_PRIMITIVE_COUNT_ELLIPSE = SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_ELLIPSE;
		FEATURES_CONNECTIONS_COUNT = SystemSettings.SYMBOL_FEATURES_CONNECTIONS_COUNT;

		FEATURES_CENTROID = SystemSettings.SYMBOL_FEATURES_CENTROID;

		FEATURES_CONVEXHULL = SystemSettings.SYMBOL_FEATURES_CONVEXHULL;

		FEATURES_RATIOS = SystemSettings.SYMBOL_FEATURES_RATIOS;
		FEATURES_AREA = SystemSettings.SYMBOL_FEATURES_AREA;

		FEATURES_LOGSAT = SystemSettings.SYMBOL_FEATURES_LOGSAT;
		FEATURES_DENSITY = SystemSettings.SYMBOL_FEATURES_DENSITY;

		RUBINE_FEATURES = SystemSettings.SYMBOL_FEATURES_RUBINE_FEATURES;
		ZENERIK_MOMEMENTS = SystemSettings.SYMBOL_FEATURES_ZENERIK_MOMEMENTS;

		momentsOrder = SystemSettings.DEFAULT_ZERNIKE_ORDER;

	}

	private void ResetAllSettings() {

		SystemSettings.FIT_LINE = FIT_LINE;
		SystemSettings.FIT_CURVE = FIT_CURVE;
		SystemSettings.FIT_POLYGON = FIT_POLYGON;
		SystemSettings.FIT_DIVIDE_CURVE = FIT_DIVIDE_CURVE;
		SystemSettings.FIT_SEGIZEN = FIT_SEGIZEN;
		SystemSettings.POLYGON_ADJUST_Default = POLYGON_ADJUST_Default;

		// SystemSettingsSEGZIN_ERROR_THRESHOLD =se;
		if (BEZIRE_ERROR_THRESHOLD != -1)
			SystemSettings.BEZIRE_ERROR_THRESHOLD = BEZIRE_ERROR_THRESHOLD;
		if (CURVE_TEST_THRESHOLD != -1)
			SystemSettings.CURVE_TEST_THRESHOLD = CURVE_TEST_THRESHOLD;
		if (HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD != -1)
			SystemSettings.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD = HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD;
		if (SOLUTION_ERROR_TOLERANCE != -1)
			SystemSettings.SOLUTION_AlgS1_ERROR_TOLERANCE = SOLUTION_ERROR_TOLERANCE;
		if (SOLUTION_INITIAL_TOLERANCE != -1)
			SystemSettings.SOLUTION_INITIAL_TOLERANCE = SOLUTION_INITIAL_TOLERANCE;

		SystemSettings.SEGMENTATION_SWARM_SECOND_PASS = SEGMENTATION_SWARM_SECOND_PASS;

		if (AGENT_SIZE != -1)
			SystemSettings.AGENT_SIZE = AGENT_SIZE;
		if (SWARM_SYSTEM_MAX_ITERATION != -1)
			SystemSettings.SWARM_SYSTEM_MAX_ITERATION = SWARM_SYSTEM_MAX_ITERATION;

		if (MinSegmentCountDefault != -1)
			SystemSettings.MinSegmentCountDefault = MinSegmentCountDefault;

		SystemSettings.UseResampling = UseResampling;// false;
		if (MaxInterplotionLength != -1)
			SystemSettings.MaxInterplotionLength = MaxInterplotionLength;
		if (MIN_STROKE_PIXEL != -1)
			SystemSettings.MIN_STROKE_PIXEL = MIN_STROKE_PIXEL;

		if (SWARM_CONSTANTS_C1 != -1)

			SystemSettings.SWARM_CONSTANTS_C1 = SWARM_CONSTANTS_C1;
		if (SWARM_CONSTANTS_C2 != -1)
			SystemSettings.SWARM_CONSTANTS_C2 = SWARM_CONSTANTS_C2;
		if (SWARM_CONSTANTS_W != -1)
			SystemSettings.SWARM_CONSTANTS_W = SWARM_CONSTANTS_W;
		if (SWARM_CONSTANTS_VMAX != -1)
			SystemSettings.SWARM_CONSTANTS_VMAX = SWARM_CONSTANTS_VMAX;

		SystemSettings.SYMBOL_FEATURES_PRIMITIVES = FEATURES_PRIMITIVES;
		SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT = FEATURES_PRIMITIVE_COUNT;

		SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE = PRIMITIVE_COUNT_CURVE;
		SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_ELLIPSE = FEATURES_PRIMITIVE_COUNT_ELLIPSE;
		SystemSettings.SYMBOL_FEATURES_CONNECTIONS_COUNT = FEATURES_CONNECTIONS_COUNT;

		SystemSettings.SYMBOL_FEATURES_CENTROID = FEATURES_CENTROID;

		SystemSettings.SYMBOL_FEATURES_CONVEXHULL = FEATURES_CONVEXHULL;

		SystemSettings.SYMBOL_FEATURES_RATIOS = FEATURES_RATIOS;
		SystemSettings.SYMBOL_FEATURES_AREA = FEATURES_AREA;

		SystemSettings.SYMBOL_FEATURES_LOGSAT = FEATURES_LOGSAT;
		SystemSettings.SYMBOL_FEATURES_DENSITY = FEATURES_DENSITY;

		SystemSettings.SYMBOL_FEATURES_RUBINE_FEATURES = RUBINE_FEATURES;
		SystemSettings.SYMBOL_FEATURES_ZENERIK_MOMEMENTS = ZENERIK_MOMEMENTS;

		SystemSettings.DEFAULT_ZERNIKE_ORDER = momentsOrder;
 
		SystemSettings. CheckLabel = CheckLabel ;  //used in xml parser to check label _ +
		SystemSettings. USE_NEW_CHANGES= USE_NEW_CHANGES ;
		SystemSettings. USE_PRE_RECOGNIZIER =USE_PRE_RECOGNIZIER ;	
		SystemSettings. USE_PreProcess= USE_PreProcess ;
		SystemSettings. USE_REMOVE_REPEAT=USE_REMOVE_REPEAT  ;
		SystemSettings. DIGITAL_CURVE_MULTI_PREMETIVE=DIGITAL_CURVE_MULTI_PREMETIVE ;
	 
		SystemSettings. USE_SWARM_MODIFICATION =USE_SWARM_MODIFICATION   ;
		
		

	}

	public void ModifyForTest() {
		ResetAllSettings();
		RunMode = RUN_MODE_TEST;
		SystemSettings.CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_CLASSIFY;
		CurrentRecognizierOperation = RecognizierSystem.RECGONIZE_OPERATION_CLASSIFY;
		PreviousTrain = PREV_TRAINING;
		//FilesNames = TestFileNames;
	}

	/**
	 * @return the dataSetType
	 */
	public int getDataSetType() {
		return DataSetType;
	}

	/**
	 * @param dataSetType
	 *            the dataSetType to set
	 */
	public void setDataSetType(int dataSetType) {
		DataSetType = dataSetType;
	}

	/**
	 * @param testName
	 *            the testName to set
	 */
	public void setTestName(String testName) {
		TestName = testName;
	}

	/**
	 * @param previousTrain
	 *            the previousTrain to set
	 */
	public void setPreviousTrain(int previousTrain) {
		PreviousTrain = previousTrain;
	}

	/**
	 * @param runMode
	 *            the runMode to set
	 */
	public void setRunMode(int runMode) {
		RunMode = runMode;
	}

	/**
	 * @param recognizierType
	 *            the recognizierType to set
	 */
	public void setRecognizierType(int recognizierType) {
		RecognizierType = recognizierType;
	}

	/**
	 * @param pauseSave
	 *            the pauseSave to set
	 */
	public void setPauseSave(boolean pauseSave) {
		PauseSave = pauseSave;
	}

	/**
	 * @param filesNames
	 *            the filesNames to set
	 */
	public void setFilesNames(ArrayList<String> filesNames) {
		if (filesNames == null) {
			TestFileNames = null;
		}

		FilesNames = filesNames;
	}

	/**
	 * @param testSize
	 *            the testSize to set
	 */
	public void setTestSize(int testSize) {
		TestSize = testSize;
	}

	/**
	 * @param currentRecognizierOperation
	 *            the currentRecognizierOperation to set
	 */
	public void setCurrentRecognizierOperation(int currentRecognizierOperation) {
		CurrentRecognizierOperation = currentRecognizierOperation;
	}

	/**
	 * @param linux
	 *            the oSLinux to set
	 */
	public void setOSLinux(int linux) {
		OSType = linux;
	}

	public void setSwarmSettings(int AgentSize, int swarmMaxIteration) {

		// if (AgentSize!=-1)
		AGENT_SIZE = AgentSize;
		// if (swarmMaxIteration!=-1)
		SWARM_SYSTEM_MAX_ITERATION = swarmMaxIteration;

	}

	public void setStrokeLengthSettings(boolean resample, int minSegment,
			int maxinter, int minStrokesize) {

		// if (minSegment!=-1)
		MinSegmentCountDefault = minSegment;
		UseResampling = resample;// false;
		// if (maxinter!=-1)
		MaxInterplotionLength = maxinter;
		// /if (minStrokesize!=-1)
		MIN_STROKE_PIXEL = minStrokesize;

	}

	public void setAlgorithmRunnings(boolean all, boolean noSwarm,
			boolean alg1, boolean alg2, boolean alg3, boolean circle) {
		if (all) {

			FIT_LINE = false;
			FIT_CURVE = true;
			FIT_POLYGON = true;
			FIT_DIVIDE_CURVE = false;
			FIT_SEGIZEN = true;

			return;
		}
		if (noSwarm) {
			FIT_LINE = false;
			FIT_CURVE = false;
			FIT_POLYGON = false;
			FIT_DIVIDE_CURVE = false;
			FIT_SEGIZEN = true;
			return;
		}

		FIT_LINE = false;
		FIT_CURVE = circle;
		FIT_POLYGON = alg1;
		FIT_DIVIDE_CURVE = alg2;
		FIT_SEGIZEN = alg3;
		// System.out.println("  ok alge 3 "+alg3+"   settings  "+
		// SystemSystemSettings
		// .FIT_SEGIZEN+"  and the settings in "+SystemSystemSettings
		// .FIT_POLYGON+" ("
		// + this.getClass().getSimpleName()
		// + "    " + (new Throwable()).getStackTrace()[0].getLineNumber()
		// + "  )  ");
		return;
	}

	public void setSettingsForPolygon(int polygonAdjust) {
		POLYGON_ADJUST_Default = polygonAdjust;
	}

	public void SetErrorThresholds(double d, double e, double curve,
			double errorP, double intialT, boolean SecondPass) {
		// /STROKE_CONSTANT_NEIGHBOURS

		// SEGZIN_ERROR_THRESHOLD =se;
		// if (e!=-1)
		BEZIRE_ERROR_THRESHOLD = e;
		// if (curve!=-1)
		CURVE_TEST_THRESHOLD = curve;
		// if (d!=-1)
		HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD = d;
		// if (errorP!=-1)
		SOLUTION_ERROR_TOLERANCE = errorP;
		// if (intialT!=-1)
		SOLUTION_INITIAL_TOLERANCE = intialT;

		SEGMENTATION_SWARM_SECOND_PASS = SecondPass;
	}

	public void swarmSettings(double c1, double c2, double w, double vmax) {

		if (c1 != -1)

			SWARM_CONSTANTS_C1 = c1;
		if (c2 != -1)
			SWARM_CONSTANTS_C2 = c2;
		if (w != -1)
			SWARM_CONSTANTS_W = w;
		if (vmax != -1)
			SWARM_CONSTANTS_VMAX = vmax;

	}

	public void addDataSetFileName(String filename) {
		if (FilesNames == null) {
			FilesNames = new ArrayList<String>();
		}

		FilesNames.add(filename);
	}

	public void addDTestSetFileName(String filename) {
		if (TestFileNames == null) {
			TestFileNames = new ArrayList<String>();
		}

		TestFileNames.add(filename);
	}

	public void SymbolFeaturesSettings(boolean a, boolean b, boolean c,
			boolean d, boolean e, boolean f, boolean g, boolean h, boolean i,
			boolean j, boolean k) {
		FEATURES_PRIMITIVES = a;
		FEATURES_PRIMITIVE_COUNT = b;
		FEATURES_CONNECTIONS_COUNT = c;

		FEATURES_CENTROID = d;

		FEATURES_CONVEXHULL = e;

		FEATURES_RATIOS = f;
		FEATURES_AREA = g;

		FEATURES_LOGSAT = h;
		FEATURES_DENSITY = i;

		RUBINE_FEATURES = j;
		ZENERIK_MOMEMENTS = k;

	}

	public void setSymbolFeaturesSettingsCOUNTCURVE(boolean cvurvecoutn) {
		PRIMITIVE_COUNT_CURVE = cvurvecoutn;
		FEATURES_PRIMITIVE_COUNT_ELLIPSE = cvurvecoutn;
	}

	public boolean isDoTrain() {

		return doTrain;
	}

	public void setDoTrain(boolean b) {
		doTrain = b;

	}

	public int getMaxCatSize() {

		return MaxCatSize;
	}

	public void setMaxCatSize(int size) {

		MaxCatSize = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CurrentRecognizierOperation;
		result = prime * result + DataSetType;
		result = prime * result
				+ ((FilesNames == null) ? 0 : FilesNames.hashCode());
		result = prime * result + MaxCatSize;
		result = prime * result + (OSType );
		result = prime * result + (PauseSave ? 1231 : 1237);
		result = prime * result + PreviousTrain;
		result = prime * result + RecognizierType;
		result = prime * result + RunMode;
		result = prime * result + SketchSystemTested;
		result = prime * result
				+ ((TestFileNames == null) ? 0 : TestFileNames.hashCode());
		result = prime * result
				+ ((TestName == null) ? 0 : TestName.hashCode());
		result = prime * result + TestSize;
		result = prime * result + (displaySketch ? 1231 : 1237);
		result = prime * result + (doTrain ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestSketchSetting other = (TestSketchSetting) obj;
		if (CurrentRecognizierOperation != other.CurrentRecognizierOperation)
			return false;
		if (DataSetType != other.DataSetType)
			return false;
		if (FilesNames == null) {
			if (other.FilesNames != null)
				return false;
		} else if (!FilesNames.equals(other.FilesNames))
			return false;
		if (MaxCatSize != other.MaxCatSize)
			return false;
		if (OSType != other.OSType)
			return false;
		if (PauseSave != other.PauseSave)
			return false;
		if (PreviousTrain != other.PreviousTrain)
			return false;
		if (RecognizierType != other.RecognizierType)
			return false;
		if (RunMode != other.RunMode)
			return false;
		if (SketchSystemTested != other.SketchSystemTested)
			return false;
		if (TestFileNames == null) {
			if (other.TestFileNames != null)
				return false;
		} else if (!TestFileNames.equals(other.TestFileNames))
			return false;
		if (TestName == null) {
			if (other.TestName != null)
				return false;
		} else if (!TestName.equals(other.TestName))
			return false;
		if (TestSize != other.TestSize)
			return false;
		if (displaySketch != other.displaySketch)
			return false;
		if (doTrain != other.doTrain)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() {

		try {

			TestSketchSetting temp = null;

			temp = (TestSketchSetting) super.clone();
			temp.FilesNames = (ArrayList<String>) this.FilesNames.clone();
			temp.TestFileNames = (ArrayList<String>) TestFileNames.clone();

			// temp.settings= (SystemSettings.) SystemSettings.clone();

			return temp;
		} catch (CloneNotSupportedException e) {

			e.printStackTrace();
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String s = "";
		String newline = System.getProperty("line.separator");
		s += newline;
		s += "-----------" + this.TestName + " ----  " + "\n";
		s += newline;
		s += " Agent size=" + this.AGENT_SIZE;

		s += "   FIT_LINE= " + FIT_LINE;// = FIT_LINE;
		s += "   FIT_CURVE= " + FIT_CURVE;
		s += "   FIT_POLYGON= " + FIT_POLYGON;
		s += "     FIT_DIVIDE_CURVE= " + FIT_DIVIDE_CURVE;
		s += newline;
		s += "     FIT_SEGIZEN  " + FIT_SEGIZEN;
		s += "     POLYGON_ADJUST_Default  " + POLYGON_ADJUST_Default;

		// SystemSettingsSEGZIN_ERROR_THRESHOLD =se;

		s += "    BEZIRE_ERROR_THRESHOLD   " + BEZIRE_ERROR_THRESHOLD;

		s += "    CURVE_TEST_THRESHOLD    " + CURVE_TEST_THRESHOLD;
		s += newline;
		s += "     HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD   "
				+ HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD;

		s += "     SOLUTION_ERROR_TOLERANCE  " + SOLUTION_ERROR_TOLERANCE;

		s += "     SOLUTION_INITIAL_TOLERANCE  " + SOLUTION_INITIAL_TOLERANCE;

		s += "    SEGMENTATION_SWARM_SECOND_PASS   "
				+ SEGMENTATION_SWARM_SECOND_PASS;
		s += newline;

		s += "     AGENT_SIZE  " + AGENT_SIZE;

		s += "    SWARM_SYSTEM_MAX_ITERATION     " + SWARM_SYSTEM_MAX_ITERATION;

		s += "    MinSegmentCountDefault   " + MinSegmentCountDefault;
		s += "    UseResampling   " + UseResampling;// false;
		s += newline;
		// if (MaxInterplotionLength!=-1)
		s += "    MaxInterplotionLength   " + MaxInterplotionLength;

		s += "      MIN_STROKE_PIXEL " + MIN_STROKE_PIXEL;

		s += newline;

		s += "    SWARM_CONSTANTS_C1   " + SWARM_CONSTANTS_C1;

		s += "     SWARM_CONSTANTS_C2  " + SWARM_CONSTANTS_C2;

		s += "     SWARM_CONSTANTS_W  " + SWARM_CONSTANTS_W;
		// if(SWARM_CONSTANTS_VMAX!=-1)
		s += "     SWARM_CONSTANTS_VMAX  " + SWARM_CONSTANTS_VMAX;

		s += newline;

		s += "     FEATURES_PRIMITIVES  " + FEATURES_PRIMITIVES;
		s += "  FEATURES_PRIMITIVE_COUNT    " + FEATURES_PRIMITIVE_COUNT;

		s += "     PRIMITIVE_COUNT_CURVE  " + PRIMITIVE_COUNT_CURVE;
		s += "    FEATURES_CONNECTIONS_COUNT  " + FEATURES_CONNECTIONS_COUNT;
		s += newline;
		s += "    SYMBOL_FEATURES_CENTROID   " + FEATURES_CENTROID;

		s += "     SYMBOL_FEATURES_CONVEXHULL  " + FEATURES_CONVEXHULL;

		s += "    FEATURES_RATIOS   " + FEATURES_RATIOS;
		s += "   FEATURES_AREA   " + FEATURES_AREA;
		s += newline;
		s += "    FEATURES_LOGSAT   " + FEATURES_LOGSAT;
		s += "   FEATURES_DENSITY   " + FEATURES_DENSITY;

		s += "    RUBINE_FEATURES   " + "      " + RUBINE_FEATURES;
		s += "    ZENERIK_MOMEMENTS   " + ZENERIK_MOMEMENTS;
		s += newline;
		s += "----------- end test settings  ----  " + "\n";
		s += newline;
		return super.toString() + s;
	}

	public static String getString() {
		String newline = System.getProperty("line.separator");

		String s = " -------------------------------- ";
		s += newline;
		s += "  SystemSettings.FIT_LINE=  " + SystemSettings.FIT_LINE;// =
		// FIT_LINE;
		s += "  SystemSettings.FIT_CURVE= " + SystemSettings.FIT_CURVE;
		s += "  SystemSettings.FIT_POLYGON= " + SystemSettings.FIT_POLYGON;
		s += "    SystemSettings.FIT_DIVIDE_CURVE  "
				+ SystemSettings.FIT_DIVIDE_CURVE;
		s += "    SystemSettings.FIT_SEGIZEN  " + SystemSettings.FIT_SEGIZEN;
		s += "    SystemSettings.POLYGON_ADJUST_Default  "
				+ SystemSettings.POLYGON_ADJUST_Default;
		s += newline;
		// SystemSettingsSEGZIN_ERROR_THRESHOLD =se;

		s += "   SystemSettings.BEZIRE_ERROR_THRESHOLD   "
				+ SystemSettings.BEZIRE_ERROR_THRESHOLD;

		s += "   SystemSettings.CURVE_TEST_THRESHOLD    "
				+ SystemSettings.CURVE_TEST_THRESHOLD;

		s += "    SystemSettings.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD   "
				+ SystemSettings.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD;

		s += "    SystemSettings.SOLUTION_ERROR_TOLERANCE  "
				+ SystemSettings.SOLUTION_AlgS1_ERROR_TOLERANCE;
		s += newline;
		s += "    SystemSettings.SOLUTION_INITIAL_TOLERANCE  "
				+ SystemSettings.SOLUTION_INITIAL_TOLERANCE;

		s += "   SystemSettings.SEGMENTATION_SWARM_SECOND_PASS   "
				+ SystemSettings.SEGMENTATION_SWARM_SECOND_PASS;

		s += "    SystemSettings.AGENT_SIZE  " + SystemSettings.AGENT_SIZE;

		s += "   SystemSettings.SWARM_SYSTEM_MAX_ITERATION     "
				+ SystemSettings.SWARM_SYSTEM_MAX_ITERATION;

		s += "   SystemSettings.MinSegmentCountDefault   "
				+ SystemSettings.MinSegmentCountDefault;
		s += "   SystemSettings.UseResampling   "
				+ SystemSettings.UseResampling;// false;
		// if (MaxInterplotionLength!=-1)
		s += "   SystemSettings.MaxInterplotionLength   "
				+ SystemSettings.MaxInterplotionLength;
		s += newline;
		s += "     SystemSettings.MIN_STROKE_PIXEL "
				+ SystemSettings.MIN_STROKE_PIXEL;

		s += "   SystemSettings.SWARM_CONSTANTS_C1   "
				+ SystemSettings.SWARM_CONSTANTS_C1;

		s += "    SystemSettings.SWARM_CONSTANTS_C2  "
				+ SystemSettings.SWARM_CONSTANTS_C2;

		s += "    SystemSettings.SWARM_CONSTANTS_W  "
				+ SystemSettings.SWARM_CONSTANTS_W;
		// if(SWARM_CONSTANTS_VMAX!=-1)
		s += "    SystemSettings.SWARM_CONSTANTS_VMAX  "
				+ SystemSettings.SWARM_CONSTANTS_VMAX;
		s += newline;

		s += "    SystemSettings.SYMBOL_FEATURES_PRIMITIVES  "
				+ SystemSettings.SYMBOL_FEATURES_PRIMITIVES;
		s += "  SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT    "
				+ SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT;

		s += "    SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE  "
				+ SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_CURVE;
		s += "   FEATURES_PRIMITIVE_COUNT_ELLIPSE   "
				+ SystemSettings.SYMBOL_FEATURES_PRIMITIVE_COUNT_ELLIPSE;
		s += "    SystemSettings.SYMBOL_FEATURES_CONNECTIONS_COUNT  "
				+ SystemSettings.SYMBOL_FEATURES_CONNECTIONS_COUNT;

		s += "   SystemSettings.SYMBOL_FEATURES_CENTROID   "
				+ SystemSettings.SYMBOL_FEATURES_CENTROID;

		s += "    SystemSettings.SYMBOL_FEATURES_CONVEXHULL  "
				+ SystemSettings.SYMBOL_FEATURES_CONVEXHULL;
		s += newline;
		s += "   SystemSettings.SYMBOL_FEATURES_RATIOS   "
				+ SystemSettings.SYMBOL_FEATURES_RATIOS;
		s += "   SystemSettings.SYMBOL_FEATURES_AREA   "
				+ SystemSettings.SYMBOL_FEATURES_AREA;

		s += "   SystemSettings.SYMBOL_FEATURES_LOGSAT   "
				+ SystemSettings.SYMBOL_FEATURES_LOGSAT;
		s += "   SystemSettings.SYMBOL_FEATURES_DENSITY   "
				+ SystemSettings.SYMBOL_FEATURES_DENSITY;

		s += "   SystemSettings.SYMBOL_FEATURES_RUBINE_FEATURES   " + "      "
				+ SystemSettings.SYMBOL_FEATURES_RUBINE_FEATURES;
		s += "   SystemSettings.SYMBOL_FEATURES_ZENERIK_MOMEMENTS   "
				+ SystemSettings.SYMBOL_FEATURES_ZENERIK_MOMEMENTS;
		s += newline;
		s += " -------------------------------- ";

		return s;
	}

	public void FeatureAdjust(boolean b) {

		if (b == true) {
			FEATURES_PRIMITIVES = false;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = false;
			PRIMITIVE_COUNT_CURVE = false;
			FEATURES_PRIMITIVE_COUNT_ELLIPSE = false;
		} else {

			FEATURES_PRIMITIVES = true;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = false;
			PRIMITIVE_COUNT_CURVE = true;
			// FEATURES_PRIMITIVE_COUNT_ELLIPSE=true;
		}

	}

	public void RunValidate(boolean b) {

		SystemSettings.CROSS_VALIDATE = b;

	}

	public void setFeatures(int i) {

		if (i == 1) {
			FEATURES_PRIMITIVES = true;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = true;

			PRIMITIVE_COUNT_CURVE = true;
			FEATURES_PRIMITIVE_COUNT_ELLIPSE = true;

		} else if (i == 2) {
			RUBINE_FEATURES = true;
		} else if (i == 3) {
			ZENERIK_MOMEMENTS = true;

		} else if (i == 4) {

			FEATURES_CENTROID = true;
			FEATURES_DENSITY = true;
			FEATURES_CONVEXHULL = true;

			FEATURES_RATIOS = true;
			FEATURES_AREA = true;

			FEATURES_LOGSAT = true;

		} else if (i == 5) {// /primivtiv fs1 +fs3 zernerk
			FEATURES_PRIMITIVES = true;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = true;

			PRIMITIVE_COUNT_CURVE = true;
			FEATURES_PRIMITIVE_COUNT_ELLIPSE = true;
			ZENERIK_MOMEMENTS = true;
		} else if (i == 6) {// primitive and composite
			FEATURES_PRIMITIVES = true;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = true;

			PRIMITIVE_COUNT_CURVE = true;
			FEATURES_PRIMITIVE_COUNT_ELLIPSE = true;

			FEATURES_CENTROID = true;
			FEATURES_DENSITY = true;
			FEATURES_CONVEXHULL = true;

			FEATURES_RATIOS = true;
			FEATURES_AREA = true;

			FEATURES_LOGSAT = true;

		} else if (i == 7) {// prmitive composite and zernik

			FEATURES_PRIMITIVES = true;
			FEATURES_PRIMITIVE_COUNT = true;
			FEATURES_CONNECTIONS_COUNT = true;

			PRIMITIVE_COUNT_CURVE = true;
			FEATURES_PRIMITIVE_COUNT_ELLIPSE = true;

			ZENERIK_MOMEMENTS = true;
			FEATURES_CENTROID = true;
			FEATURES_DENSITY = true;
			FEATURES_CONVEXHULL = true;

			FEATURES_RATIOS = true;
			FEATURES_AREA = true;

			FEATURES_LOGSAT = true;

		}

	}

	public void ClearFeat() {
		SymbolFeaturesSettings(false, false, false, false, false, false, false,
				false, false, false, false);
		PRIMITIVE_COUNT_CURVE = false;
		FEATURES_PRIMITIVE_COUNT_ELLIPSE = false;

	}

	public void setMomemtsOrder(int i) {
		momentsOrder = i;

	}

	public static ArrayList<TestSketchSetting> ReadClassifiersDetails(
			String filename) {
		ArrayList<TestSketchSetting> dataArr = null;
		try {
			logger.info("reading the file................ wait");
			File afile = new File(filename);
			Scanner input = new Scanner(new BufferedReader(
					new FileReader(afile)));
			String inputString = "", inputStringInner;
			int inputInt;
			int inputDouble;
			TestSketchSetting data;
			dataArr = new ArrayList<TestSketchSetting>();
			// boolean finishClassifier=false,finishRegion=false;

			while (input.hasNext()) {
				String maininputString = input.nextLine();
				// skip any ## comment line..
				if (maininputString.startsWith("##"))
					continue;

				TestSketchSetting temp = new TestSketchSetting();
				// now check for the follwing
				if (maininputString.startsWith("TestSketchSetting")) {

					while (input.hasNext()) {

						inputString = input.nextLine();

						if (inputString.startsWith("##"))
							continue;

						if (inputString.trim().startsWith("Finish")) {
							break;
						}

						if (inputString.trim().startsWith("Name")) {

							temp.setTestName(input.nextLine());
						}
						if (inputString.trim().startsWith("OS")) {

							temp.OSType = input.nextInt();
							logger.error("  REading os Linux " + temp.OSType);
						}
						if (inputString.trim().startsWith("doTrain")) {

							temp.doTrain = input.nextBoolean();
							logger.error("  REading dp train..  "
									+ temp.doTrain);
						}
						if (inputString.trim().startsWith("PreviousTrain")) {

							temp.PreviousTrain = input.nextInt();
							logger.error("  REading PreviousTrain  "
									+ temp.PreviousTrain);

						}
						if (inputString.trim().startsWith("SaveErrorCorrect")){
						 temp.SaveErrorCorrect= input.nextBoolean();
						}
						if (inputString.trim().startsWith("SaveToFile")){				
					 temp.SaveToFile= input.nextBoolean();
					 }
						if (inputString.trim().startsWith("RunMode")) {

							temp.RunMode = input.nextInt();
							logger.error("  REading RunMode  " + temp.RunMode);
						}
						if (inputString.trim().startsWith("RecognizierType")) {

							temp.RecognizierType = input.nextInt();
							logger.error("  REading RecognizierType  "
									+ temp.RecognizierType);
						}
						if (inputString.trim().startsWith("PauseSave")) {

							temp.PauseSave = input.nextBoolean();
							logger.error("  REading PauseSave  "
									+ temp.PauseSave);
						}
						if (inputString.trim().startsWith("DataSetType")) {

							temp.DataSetType = input.nextInt();
							logger.error("  REading DataSetType "
									+ temp.DataSetType);
						}
						if (inputString.trim().startsWith("SketchSystemTested")) {

							temp.SketchSystemTested = input.nextInt();
							logger.error("  REading RSketchSystemTested "
									+ temp.SketchSystemTested);
						}
						 if (inputString.trim().startsWith("AGENT_SIZE")) {
						 inputInt = input.nextInt();
						 temp.AGENT_SIZE = inputInt;
						 
							logger.error("  AGENT_SIZE " 	+ temp.AGENT_SIZE);
						 }

						///-------------------------------------------Settings--
						// ---------------------------------------
						if (inputString.trim().startsWith("FIT_LINE")) {

							temp.FIT_LINE = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FIT_CURVE")) {

							temp.FIT_CURVE = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FIT_POLYGON")) {
							temp.FIT_POLYGON = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FIT_DIVIDE_CURVE")) {
							temp.FIT_DIVIDE_CURVE = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FIT_SEGIZEN")) {

							temp.FIT_SEGIZEN = input.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"POLYGON_ADJUST_Default")) {

							temp.POLYGON_ADJUST_Default = input.nextInt();
						}

						if (inputString.trim().startsWith(
								"BEZIRE_ERROR_THRESHOLD")) {

							temp.BEZIRE_ERROR_THRESHOLD = input.nextDouble();
						}
						if (inputString.trim().startsWith(
								"CURVE_TEST_THRESHOLD")) {

							temp.CURVE_TEST_THRESHOLD = input.nextDouble();
						}
						if (inputString.trim().startsWith(
								"HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD")) {
							temp.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD = input
									.nextDouble();
						}
						if (inputString.trim().startsWith(
								"SOLUTION_ERROR_TOLERANCE")) {
							temp.SOLUTION_ERROR_TOLERANCE = input.nextDouble();
						}
						if (inputString.trim().startsWith(
								"SOLUTION_INITIAL_TOLERANCE")) {
							temp.SOLUTION_INITIAL_TOLERANCE = input
									.nextDouble();

						}
						if (inputString.trim().startsWith(
								"SEGMENTATION_SWARM_SECOND_PASS")) {
							temp.SEGMENTATION_SWARM_SECOND_PASS = input
									.nextBoolean();

						}

						if (inputString.trim().startsWith(
								"SWARM_SYSTEM_MAX_ITERATION")) {
							inputInt = input.nextInt();
							temp.SWARM_SYSTEM_MAX_ITERATION = inputInt;
						}
						if (inputString.trim().startsWith(
								"MinSegmentCountDefault")) {
							inputInt = input.nextInt();
							temp.MinSegmentCountDefault = inputInt;
						}
						if (inputString.trim().startsWith("UseResampling")) {
							temp.UseResampling = input.nextBoolean();
						}

						// if(MaxInterplotionLength!=-1)
						if (inputString.trim().startsWith(
								"MaxInterplotionLength")) {

							temp.MaxInterplotionLength = input.nextDouble();
						}
						if (inputString.trim().startsWith("MIN_STROKE_PIXEL")) {
							inputInt = input.nextInt();
							temp.MIN_STROKE_PIXEL = inputInt;
						}

						if (inputString.trim().startsWith("SWARM_CONSTANTS_C1")) {
							temp.SWARM_CONSTANTS_C1 = input.nextDouble();
						}

						if (inputString.trim().startsWith("SWARM_CONSTANTS_C2")) {
							temp.SWARM_CONSTANTS_C2 = input.nextDouble();
						}

						if (inputString.trim().startsWith("SWARM_CONSTANTS_W")) {
							temp.SWARM_CONSTANTS_W = input.nextDouble();
						}

						if (inputString.trim().startsWith(
								"SWARM_CONSTANTS_VMAX")) {
							temp.SWARM_CONSTANTS_VMAX = input.nextDouble();
						}

						if (inputString.trim()
								.startsWith("FEATURES_PRIMITIVES")) {
							temp.FEATURES_PRIMITIVES = input.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"FEATURES_PRIMITIVE_COUNT")) {
							temp.FEATURES_PRIMITIVE_COUNT = input.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"PRIMITIVE_COUNT_CURVE")) {
							temp.PRIMITIVE_COUNT_CURVE = input.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"FEATURES_CONNECTIONS_COUNT")) {
							temp.FEATURES_CONNECTIONS_COUNT = input
									.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"SYMBOL_FEATURES_CENTROID")) {
							temp.FEATURES_CENTROID = input.nextBoolean();
						}
						if (inputString.trim().startsWith(
								"SYMBOL_FEATURES_CONVEXHULL")) {
							temp.FEATURES_CONVEXHULL = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FEATURES_RATIOS")) {
							temp.FEATURES_RATIOS = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FEATURES_AREA")) {
							temp.FEATURES_AREA = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FEATURES_LOGSAT")) {
							temp.FEATURES_LOGSAT = input.nextBoolean();
						}
						if (inputString.trim().startsWith("FEATURES_DENSITY")) {
							temp.FEATURES_DENSITY = input.nextBoolean();
						}
						if (inputString.trim().startsWith("RUBINE_FEATURES")) {
							temp.RUBINE_FEATURES = input.nextBoolean();
						}
						if (inputString.trim().startsWith("ZENERIK_MOMEMENTS")) {
							temp.ZENERIK_MOMEMENTS = input.nextBoolean();
						}
						
						

						if (inputString.trim().startsWith("CheckLabel")) {
						 
							
							temp.CheckLabel = input.nextBoolean();
												}		

						if (inputString.trim().startsWith("USE_NEW_CHANGES")) {
						 
							
							temp. USE_NEW_CHANGES= input.nextBoolean();
												}
							
						if (inputString.trim().startsWith("USE_PRE_RECOGNIZIER")) {
						 
									
							temp. USE_PRE_RECOGNIZIER= input.nextBoolean();
												}

						if (inputString.trim().startsWith("USE_PreProcess")) {
						 
								
							temp.USE_PreProcess= input.nextBoolean();
												}

						if (inputString.trim().startsWith(" USE_REMOVE_REPEAT")) {
						 
								
							temp. USE_REMOVE_REPEAT= input.nextBoolean();
												}

						if (inputString.trim().startsWith("DIGITAL_CURVE_MULTI_PREMETIVE")) {
						 
							
							temp. DIGITAL_CURVE_MULTI_PREMETIVE= input.nextBoolean();
												}

						if (inputString.trim().startsWith("USE_SWARM_MODIFICATION")) {
						 
							
							temp. USE_SWARM_MODIFICATION = input.nextBoolean();
												}
						// -------------------------------------------FILESSS
						// s-----------------------------------------
						if (inputString.trim().startsWith("FilesNames")) {
							ArrayList<String> FilesNa = new ArrayList<String>();
							String innerInput = "";
							int size = 0;
							while (input.hasNext()
									&& !innerInput.equalsIgnoreCase("Finish")) {

								innerInput = input.nextLine();
								if (innerInput.startsWith("##"))
									continue;

								if (innerInput.equalsIgnoreCase("Finish")) {

									break;
								}
								if (innerInput.trim().equalsIgnoreCase("size")) {
									size = input.nextInt();
								}// esle if not comment or finish then a file
									// nameee....

								FilesNa.add(innerInput);

							}

							temp.FilesNames = FilesNa;

						}

						if (inputString.trim().startsWith("TestFileNames")) {

							ArrayList<String> FilesNa = new ArrayList<String>();
							String innerInput = "";
							int size = 0;
							while (input.hasNext()
									&& !innerInput.equalsIgnoreCase("Finish")) {

								innerInput = input.nextLine();
								if (innerInput.startsWith("##"))
									continue;

								if (innerInput.equalsIgnoreCase("Finish")) {

									break;
								}
								if (innerInput.trim().equalsIgnoreCase("size")) {
									size = input.nextInt();
								}// esle if not comment or finish then a file
									// nameee....

								FilesNa.add(innerInput);

							}

							temp.TestFileNames = FilesNa;
						}

					}// while loop
					logger.info("  Classifier no.  " + (dataArr.size() + 1)
							+ "  is  " + temp.toString());
					dataArr.add(temp);

				}// iif

			}// large while loop

			input.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		logger.info("Finished reading details.........");
		return dataArr;
	}

	public static void SaveSystemsSettings(PrintStream out,
			TestSketchSetting temp) {
		out.println("##   ");
		out.println("##   AGENT_SIZE ");
		out.println("AGENT_SIZE");
		out.println(temp.AGENT_SIZE);
		out.println("##  the algorithm used  ");
		out.println("FIT_LINE");
		out.println(temp.FIT_LINE);// =FIT_LINE;
		out.println("FIT_CURVE");
		out.println(temp.FIT_CURVE);
		out.println("FIT_POLYGON");
		out.println(temp.FIT_POLYGON);
		out.println("FIT_DIVIDE_CURVE");
		out.println(temp.FIT_DIVIDE_CURVE);

		out.println("FIT_SEGIZEN");
		out.println(temp.FIT_SEGIZEN);
		out.println("POLYGON_ADJUST_Default");
		out.println(temp.POLYGON_ADJUST_Default);
		out.println("##   the error threshold ");
		out.println("##   ");

		out.println("BEZIRE_ERROR_THRESHOLD");
		out.println(temp.BEZIRE_ERROR_THRESHOLD);

		out.println("CURVE_TEST_THRESHOLD");
		out.println(temp.CURVE_TEST_THRESHOLD);

		out.println("HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD");
		out.println(temp.HYBIRD_ERROR_TOLERANCE_TEST_THRESHOLD);

		out.println("SOLUTION_ERROR_TOLERANCE");
		out.println(temp.SOLUTION_ERROR_TOLERANCE);

		out.println("SOLUTION_INITIAL_TOLERANCE");
		out.println(temp.SOLUTION_INITIAL_TOLERANCE);

		out.println("SEGMENTATION_SWARM_SECOND_PASS");
		out.println(temp.SEGMENTATION_SWARM_SECOND_PASS);
		out.println("##   ");

		// out.println("AGENT_SIZE");
		// out.println(temp.AGENT_SIZE);

		out.println("SWARM_SYSTEM_MAX_ITERATION");
		out.println(temp.SWARM_SYSTEM_MAX_ITERATION);

		out.println("MinSegmentCountDefault");
		out.println(temp.MinSegmentCountDefault);
		out.println("UseResampling");
		out.println(temp.UseResampling);// false;
		out.println("##   ");
		// if(MaxInterplotionLength!=-1)
		out.println("MaxInterplotionLength");
		out.println(temp.MaxInterplotionLength);

		out.println("MIN_STROKE_PIXEL");
		out.println(temp.MIN_STROKE_PIXEL);

		out.println("SWARM_CONSTANTS_C1");
		out.println(temp.SWARM_CONSTANTS_C1);

		out.println("SWARM_CONSTANTS_C2");
		out.println(temp.SWARM_CONSTANTS_C2);

		out.println("SWARM_CONSTANTS_W");
		out.println(temp.SWARM_CONSTANTS_W);

		out.println("SWARM_CONSTANTS_VMAX");
		out.println(temp.SWARM_CONSTANTS_VMAX);

		out.println("FEATURES_PRIMITIVES");
		out.println(temp.FEATURES_PRIMITIVES);
		out.println("FEATURES_PRIMITIVE_COUNT");
		out.println(temp.FEATURES_PRIMITIVE_COUNT);

		out.println("PRIMITIVE_COUNT_CURVE");
		out.println(temp.PRIMITIVE_COUNT_CURVE);
		out.println("FEATURES_CONNECTIONS_COUNT");
		out.println(temp.FEATURES_CONNECTIONS_COUNT);

		out.println("SYMBOL_FEATURES_CENTROID");
		out.println(temp.FEATURES_CENTROID);

		out.println("SYMBOL_FEATURES_CONVEXHULL");
		out.println(temp.FEATURES_CONVEXHULL);
		out.println("##   ");
		out.println("FEATURES_RATIOS");
		out.println(temp.FEATURES_RATIOS);
		out.println("FEATURES_AREA");
		out.println(temp.FEATURES_AREA);

		out.println("FEATURES_LOGSAT");
		out.println(temp.FEATURES_LOGSAT);
		out.println("FEATURES_DENSITY");
		out.println(temp.FEATURES_DENSITY);

		out.println("RUBINE_FEATURES");
		out.println(temp.RUBINE_FEATURES);
		out.println("ZENERIK_MOMEMENTS");
		out.println(temp.ZENERIK_MOMEMENTS);
		out.println("#################  new settings ...   ");
		
		
				out.println("CheckLabel");
		out.println(temp.CheckLabel );		
	
		out.println("USE_NEW_CHANGES");
			out.println(temp. USE_NEW_CHANGES);	
		out.println("USE_PRE_RECOGNIZIER");;		
out.println(temp. USE_PRE_RECOGNIZIER);;
		out.println("USE_PreProcess");	
				out.println(temp.USE_PreProcess);
		out.println(" USE_REMOVE_REPEAT");	
		out.println(temp. USE_REMOVE_REPEAT);
	
		out.println("DIGITAL_CURVE_MULTI_PREMETIVE");
		out.println(temp. DIGITAL_CURVE_MULTI_PREMETIVE);

		
		
			out.println("USE_SWARM_MODIFICATION");

			out.println(temp. USE_SWARM_MODIFICATION );

	

	

	
		
		
	
		
	}

	public static void SaveClassifiersDetails(
			ArrayList<TestSketchSetting> ClassesData, String Filename) {

		FileOutputStream file;
		PrintStream out; // declare a print stream object
		try {
			// if(sort){
			// Collections.sort(ClassesData,
			// new Comparator<ClassifierData>() {
			// public int compare(ClassifierData o1, ClassifierData o2) {
			// return Double.compare( o1.Accuracy, o2.Accuracy);
			// //return (int) (o2.Accuracy- o1.Accuracy);
			// //return o2.size() - o1.size();
			// }
			// });
			// }
			// Create a new file output stream
			file = new FileOutputStream(Filename);

			// Connect print stream to the output stream
			out = new PrintStream(file);

			// wirte the type of database
			out.println("## This is a comment line ");
			out.println("## The First few Lines define the format");

			out.println("## Maximum number of classifiers..  ");
			out
					.println("## Now classifier Data  and actual number of classifier is "
							+ ClassesData.size());

			out.println("##   ");
			out.println("##   ");
			out.println("##   ");
			out.println("##   ");

			out.println("##   ");
			out.println("##--------------------------------- ");

			out
					.println("## Now classifier Data  and actual number of classifier is "
							+ ClassesData.size());
			TestSketchSetting temp;

			for (int i = 0; i < ClassesData.size(); i++) {
				out
						.println("##TestSketchSetting r======================================================");
				out.println("##TestSketchSetting  " + i);
				logger.info("writing  TestSketchSetting" + i + "   "
						+ ClassesData.get(i).toString());
				// get the digit
				temp = ClassesData.get(i);
				out.println("TestSketchSetting");
				out.println("##writing the name  ");

				out.println("Name");
				out.println(temp.getTestName());
				out.println("##  OS is either the   linux  "+SystemSettings.OS_LINUX+"   or windows  "+SystemSettings.OS_WINDOWS);
				out.println("OS");
				out.println(temp.OSType);
				out.println("doTrain");
				out.println(temp.doTrain);

				out.println("PreviousTrain");
				out.println(temp.PreviousTrain);
				out.println("RunMode");
				out.println(temp.RunMode);
				out.println("RecognizierType");
				out.println(temp.RecognizierType);
				out.println("PauseSave");
				out.println(temp.PauseSave);
				out.println("DataSetType");
				out.println(temp.DataSetType);
				out.println("SketchSystemTested");
				out.println(temp.SketchSystemTested);
				out.println("SaveErrorCorrect");	
				out.println(temp.SaveErrorCorrect);
				out.println("SaveToFile");				
				out.println(temp.SaveToFile);
				out
						.println("#####--------------------------SETTTINGS-----------------------------------------");
				SaveSystemsSettings(out, temp);

				out
						.println("######---------------------------FILESSS---------------------------------");

				if (temp.FilesNames != null) {
					out.println("## Now TRian File namessssss...."
							+ temp.FilesNames.size());
					out.println("##   ");
					// out.println("sizeF");
					// out.println(temp.FilesNames.size());
					out.println("FilesNames");
					for (int j = 0; j < temp.FilesNames.size(); j++) {
						out.println(temp.FilesNames.get(j));
					}
					out.println("Finish");

				}
				out.println("##   ");
				if (temp.TestFileNames != null) {
					out.println("## Now TRian File names ...."
							+ temp.FilesNames.size());

					// out.println("sizeT");
					// out.println(temp.TestFileNames.size());
					out.println("TestFileNames");
					out.println("##   ");
					for (int j = 0; j < temp.TestFileNames.size(); j++) {
						out.println(temp.TestFileNames.get(j));
					}
					out.println("Finish");

				}

				out.println("Finish");

			}

			out.println("## This is The end of file.......");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in writing to file");
		}
		logger.info("Finished writing the properties..........");
	}

	public void CorrectPaths() {
		
		for (int i = 0; i < FilesNames.size(); i++) {
			FilesNames.set(i, GeneralUtils.CorrectPath(FilesNames.get(i), OSType)+"");
		}
		
		for (int i = 0; i < TestFileNames.size(); i++) {
			TestFileNames.set(i, GeneralUtils.CorrectPath(TestFileNames.get(i), OSType)+"");
		}
		//GeneralUtils
		
	}
}