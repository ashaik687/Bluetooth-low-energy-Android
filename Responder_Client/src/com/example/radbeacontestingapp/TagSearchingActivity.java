package com.example.radbeacontestingapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;


//import com.example.myapp.R;
import com.example.radbeacontestingapp.TagSearchingActivity.MyUrlTileProvider;
//import com.example.calculator_main.R;
import com.google.common.collect.ImmutableSet;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
//import com.example.radbeacontestingapp.TestingMap.MyUrlTileProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.common.collect.ImmutableSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
/**
 * This Activity performs the searching for tags 
 * It has Google maps UI. This will point to Marcus basement blue print on Starting the Activity
 */
public class TagSearchingActivity extends Activity implements BeaconConsumer,IOnLandmarkSelectedListener,GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,OnMapLongClickListener,OnMarkerClickListener {
	
	/** A set of valid Gimbal beacon identifier */
	
	
	private final Set<String> validGimbalIdentifiers = ImmutableSet.of(
			"00100001", "10011101");

	/** Log for TagSearchingActivity. */
	private static final String TAG_SEARCHING_ACTIVITY_LOG = "TAG_SEA_ACT_LOG";

	private ListView tagSearchListView;
	private ArrayAdapter<Beacon> tagSearchListAdapter;
	private List<Beacon> discoveredBeaconList;
	public static  HashMap<String,Integer> sortedBeaconmap ;

	/** The map used for storing discovered beacons */
	public static HashMap<String, Beacon> discoveredBeaconMap;

	/** Declare and initiate the a BeaconManager object.*/
	private BeaconManager beaconManager = BeaconManager
			.getInstanceForApplication(this);

	public static List<Integer> rSSI;  
	public static List<String> kEYS;
	
	 public static Map<Integer, String> RSkey  ;
	 public static TreeMap<Integer, String> treeMap ;
	
	 public static int rad1;
		public static int rad2;
		public static int rad3;
		public static String key1;
		public static String key2;
		public static String key3;
		public static String test;
		
		public static double[] LatArray ;
		public static double[] LongArray;
		public static String[] KeyValues;
		public static int[] BeaconNumber;
	
		 public static double latitude1=0;
		    public static double longitude1=0;
		    
		    public static double latitude2=0;
		    public static double longitude2=0;
		    
		    public static double latitude3=0;
		    public static double longitude3=0;
		    

		    public static int BeaconNum1=0;
		    public static int BeaconNum2=0;
		    
		  public static double FinalLatitude;
		public static double FinalLongitude;
			public static TextView finallat;
			public static TextView finallong;
			public static TextView beaconnum;
		
		
			public static int landmarknum;
			private GoogleMap mMap;
			private GoogleMap mMap1;
			//private UiSettings mUiSettings;
			String mUrl = "http://percept.ecs.umass.edu/course/marcusbasement/{z}/{x}/{y}.png";
			
			double marcusLat = 42.3939;
			double marcusLng =-72.5285;
			int knowlesZoom = 25;
			
			public int currentMode;

			public final String ACTION_VIEW_MAP = "Overview Map";
			public final String ACTION_ADD_LANDMARKS = "Add Landmarks";
			public final String ACTION_REMOVE_LANDMARKS = "Remove Landmarks";

			public final int MODE_VIEW = 0;
			public final int MODE_ADD_LANDMARK = 1;
			public final int MODE_GENERATE_PATH = 2;
			public final int MODE_REMOVE_LANDMARK = 3;
			public final int MODE_SELECT_PATH = 4;

			
			public String title = "Title ";
			public int titleNumber = 0;
			private IOnLandmarkSelectedListener landmarkListener;
			public Uri imageUri;
			private Landmarks landmarks;	
			private Activity activity;
			
			
			public double latfinal=0;
			public double longfinal=0;
			public int closeBeacon=0;
			public int[] images;
			
			public Handler handler = new Handler();
			public Runnable refresh;
			
			public static Intent starterIntent;
		
			public Marker marker1;
			@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_searching);

		/**
		 * Initialize some Data structures for computing the closest beacon
		 */
		
		sortedBeaconmap = new HashMap<String,Integer>();
		discoveredBeaconMap = new HashMap<String, Beacon>();
		discoveredBeaconList = new ArrayList<Beacon>();
		
		rSSI = new ArrayList<Integer>();
		kEYS = new ArrayList<String>();
		RSkey	= new HashMap<Integer, String>();
		
		treeMap= new TreeMap<Integer, String>();

		LatArray= new double[20];
	     LongArray= new double[20];
	     KeyValues = new String[20];
	     BeaconNumber=new int[20];
	
	     /**
	 	 * The Lattitude , Logitude and Key values of Beacons are specified in Oncreate method so that they can be accessed later while calculating closest beacon. 
	 	 */  
	       BeaconNumber[0]=1;
		   BeaconNumber[1]=2;
		   BeaconNumber[2]=3;
		   BeaconNumber[3]=4;
		   BeaconNumber[4]=5;
		   BeaconNumber[5]=6;
		   BeaconNumber[6]=7;
		   BeaconNumber[7]=8;
		   BeaconNumber[8]=9;
		   BeaconNumber[9]=10;
		   BeaconNumber[10]=11;
		   BeaconNumber[11]=12;
		   BeaconNumber[12]=13;
		   BeaconNumber[13]=14;
		   BeaconNumber[14]=15;
		   BeaconNumber[15]=16;
		   BeaconNumber[16]=17;
		  
		   		LatArray[0] = 42.39354668258381;
			    LatArray[1] = 42.39359447166392;
			    LatArray[2] = 42.393661574404135;
			    LatArray[3] = 42.39370936339676;
			    LatArray[4] = 42.39377498034809;
			    LatArray[5] = 42.39382895952455;
			    LatArray[6] = 42.39389135741379;
			    LatArray[7] = 42.393932460751394;
			    LatArray[8] =42.393999315520105;
			    LatArray[9] = 42.39404537098601;
			    LatArray[10] = 42.394103806904866;
			    LatArray[11] = 42.39417412802318;
			    LatArray[12] = 42.39423256382214;
			    LatArray[13] = 42.39422736402869;
			    LatArray[14] = 42.39416001428392;
			    LatArray[15] = 42.39402060998703;
			    LatArray[16] = 42.39397727821532;
			    			  
			   LongArray[0] = -72.52833187580109;			 
			   LongArray[1] = -72.52839054912329;
			   LongArray[2] =-72.52840027213097;
			   LongArray[3] = -72.52845861017704;
			   LongArray[4] = -72.52846665680408;
			   LongArray[5] = -72.52852533012629;
			   LongArray[6] = -72.52853605896235;
			   LongArray[7] = -72.52859741449356;
			   LongArray[8] = -72.52860512584448;
			   LongArray[9] = -72.52866346389055;
			   LongArray[10] = -72.52867016941309;
			   LongArray[11] = -72.52873621881008;
			   LongArray[12] = -72.52873655408621;
			   LongArray[13] = -72.52877611666918;
			   LongArray[14] = -72.52882674336433;
			   LongArray[15] = -72.52868391573429;
			   LongArray[16] = -72.52871174365282;
			    		   
		   KeyValues[0]="00100001-0110-0000-1100-0111111001001001";
		   KeyValues[1]="00100001-0110-0000-1100-0111111001001002"; 
		   KeyValues[2]="00100001-0110-0000-1100-0111111001001003";
		   KeyValues[3]="00100001-0110-0000-1100-0111111001001004";
		   KeyValues[4]="00100001-0110-0000-1100-0111111001001005";
		   KeyValues[5]="00100001-0110-0000-1100-0111111001001006";
		   KeyValues[6]="00100001-0110-0000-1100-0111111001001007";
		   KeyValues[7]="00100001-0110-0000-1100-0111111001001008";
		   KeyValues[8]="00100001-0110-0000-1100-0111111001001009";
		   KeyValues[9]="00100001-0110-0000-1100-01111110010010010";
		   KeyValues[10]="00100001-0110-0000-1100-01111110010010011";
		   KeyValues[11]="00100001-0110-0000-1100-01111110010010012";
		   KeyValues[12]="00100001-0110-0000-1100-01111110010010013";
		   KeyValues[13]="00100001-0110-0000-1100-01111110010010014";
		   KeyValues[14]="00100001-0110-0000-1100-01111110010010015";
		   KeyValues[15]="00100001-0110-0000-1100-01111110010010016";
		   KeyValues[16]="00100001-0110-0000-1100-01111110010010017";
	
		   /**
			 * We have implemented a new feature in our project which includes adding Images of the Beacon location as markers in the location
			 * The Array images stores the reference to images that are present in the drawable folder.
			 * These image reference variables are placed in a loop so when we try to place markers dynamically on google maps 
			 * 			we can refer the image of location of the closest beacon and will thus place the appropritate marker on the closest location.
			 * 			  
			 */
		   	images = new int[50];
			images[1]=R.drawable.image01;
			images[2]=R.drawable.image02;
			images[3]=R.drawable.image03;
			images[4]=R.drawable.image04;
			images[5]=R.drawable.image05;
			images[6]=R.drawable.image06;
			images[7]=R.drawable.image07;
			images[8]=R.drawable.image08;
			images[9]=R.drawable.image09;
			images[10]=R.drawable.image10;
			images[11]=R.drawable.image11;
			images[12]=R.drawable.image12;
			images[13]=R.drawable.image13;
			images[14]=R.drawable.image14;
			images[15]=R.drawable.image15;
			images[16]=R.drawable.image16;
			images[17]=R.drawable.image17;
		   
		   try {
	            landmarkListener = (IOnLandmarkSelectedListener) this;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
	        }

			setupMap();
		
			LatLng position = new LatLng(0,0); // We had to move the marker dynamically so we had to remove the already existing marker and place new marker
											   // This was creating a NullPointer exception in the first iteration as we were trying to remove without placing any marker
												// To avoid that exception we are placing a marker in 0,0 location initially which is removed and gets placed in 
												//	current location in next iteration. Its fixing a bug . Nothing serious 
			Marker marker = mMap.addMarker(new MarkerOptions().position(position).title("Your Location").draggable(true));
			
			landmarks.addMarker("Your Location", marker);
	
			mMap.setOnMarkerClickListener(this);
			mMap.setOnMapLongClickListener(this); 

			
			}
			/**
			 * onMapLongClick places a Marker on GoogleMaps  
			 */
			public void onMapLongClick(LatLng point) {
			 try{ 
				 if (marker1 != null) {
                     marker1.remove();
                 }
			
				marker1=mMap.addMarker(new MarkerOptions()
			        .position(point)
			        .title("You are here")           
			        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));  
			 }catch(NullPointerException e){
				 
			 }
			 
			 }		
			protected void onStart(){
				super.onStart();
			
				refresh = new Runnable() {
				    public void run() {
				      
				        handler.postDelayed(refresh, 2000);
				    }
				};
				handler.post(refresh);			
			}			
			@Override
	protected void onResume() {
		super.onResume();
		beaconManager
				.getBeaconParsers()
				.add(new BeaconParser()
						.setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
		beaconManager.bind(this);
		Timer autoUpdate = new Timer();
		  autoUpdate.schedule(new TimerTask() {
		   @Override
		   public void run() {
		    runOnUiThread(new Runnable() {
		     public void run() {
		    	 updateDiscoveredList();
		     }
		    });
		   }
		  }, 0, 2000); 
			}

	@Override
	protected void onPause() {
		super.onPause();
		beaconManager.unbind(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Refresh the list of beacon according to current values in the map and
	 * then notify the list UI to change.
	 */
	private void updateDiscoveredList() {
		/**
		 * Flush all the values in the Arraylists and Hashtables when we start this method. 
		 * This avoids Buffer overflow problems caused when beacons try to put all values in the datastructure.
		 */
		treeMap.clear(); 
		kEYS.clear();
		rSSI.clear();
		RSkey.clear();
		discoveredBeaconList.clear();
		Iterator<Beacon> bIter = discoveredBeaconMap.values().iterator();
		while (bIter.hasNext()) {
			discoveredBeaconList.add(bIter.next());
		}
		/**
	 	 * runOnUiThread is the major part of the program .
	 	 * It takes the values of detected beacons and their RSSi values and process them dynamically on the UI thread
	 	 *
	 	 */ 
		
		runOnUiThread(new Runnable() {
			public void run() {
					try{
						for(String keys: sortedBeaconmap.keySet()){
						 kEYS.add(keys);
						rSSI.add(sortedBeaconmap.get(keys));
					
					}

					for(int i =0;i<=kEYS.size()-1;i++){
						
						int x = (int) ( (-0.00903*rSSI.get(i)*rSSI.get(i))-(2.171*rSSI.get(i))-94);    //Compute Distance using RSSI value
							
							RSkey.put(x,(String) kEYS.get(i));   //Place all the values in a a hash map
							String r=x+"";
							Log.i("ash_hee",r);
						}
					treeMap.putAll(RSkey);
					/**
				 	 * Place the HashMap into a TreeMap
				 	 * TreeMap automatically sorts the beacons according to their distance from the user and places the beacon with least distance on Top
				 	 * 
				 	 * ***********Sorting Procedure***********
				 	 * ---> Read the first entry of TreeMap to get the Closest Beacon 
				 	 * ---> Delete the first entry of TreeMap
				 	 * ---> TreeMap automatically sorts the remaining Beacons and places the second closest Beacon on top of list.
				 	 * ---> Again Read the next closest Beacon from Top of TreeMap
				 	 * ---> Repeat this procedure till you get 3 closest Beacons 
				 	 * ---> This Three closest Beacons are used for Localization Technique.
				 	 *
				 	 */ 				
					
					try{
					key1 =treeMap.get(treeMap.firstKey()); 
					rad1=treeMap.firstKey();
					String z1 =treeMap.get(treeMap.firstKey());
					Log.i("ash_hee",z1);
					
					treeMap.remove(treeMap.firstKey());
					String	z2 =treeMap.get(treeMap.firstKey()) ;
					Log.i("ash_hee",z2);
					key2 =treeMap.get(treeMap.firstKey()); 
					rad2=treeMap.firstKey();
					
					treeMap.remove(treeMap.firstKey());
					String z3 =treeMap.get(treeMap.firstKey()) ;
					Log.i("ash_hee",z3);
					key3 =treeMap.get(treeMap.firstKey()); 
					rad3=treeMap.firstKey();
					}
					
					catch(NoSuchElementException exception){
					
						Log.i("ash_hee", "not enough number of beacons no such element");
					}
					
					for(int i=0;i<17;i++)
					   { 
					   if(key1.equals(KeyValues[i]))
						   {
							 latitude1=LatArray[i];
							 longitude1=LongArray[i];
							 BeaconNum1=BeaconNumber[i];
						   }
					    else if(key2.equals(KeyValues[i]))
						   {
							   latitude2=LatArray[i];
							   longitude2=LongArray[i];
							   BeaconNum2=BeaconNumber[i];
						   }
						 else if(key3.equals(KeyValues[i]))
						   {
							   latitude3=LatArray[i];
							   longitude3=LongArray[i];
						   }
					   FinalLatitude=0;
					   FinalLongitude=0;
			/**
			 * Weighted centroid method to find the current location		   
			 */
			 FinalLatitude = ((latitude1*rad1)+(rad2*latitude2)+(rad3*latitude3))/(rad1+rad2+rad3);
			 FinalLongitude = ((longitude1*rad1)+(rad2*longitude2)+(rad3*longitude3))/(rad1+rad2+rad3);
										   
					   }
			
					/**
				 	 * Images of the closest beacon are used as markers and placed on Google Maps at the current position
				 	 *
				 	 **************Procedure for Placing Custom Image Markers***********
				 	 *---> Use BitMap to Build image needed with the required pixel dimensions.
				 	 *---> We have already stored the reference of all images of all the beacon location in the Drawable folder in an Array called Images
				 	 *---> From the Code above we will get Latitude and Longitude values of the Beacon closest to the user location and the closest Beacon Number.
				 	 *---> Depending upon the closest Beacon number we select the particular image corresponding to the Beacon and place the 
				 	 *				custom marker at our current location
				 	 *							
				 	 */ 
					
					Bitmap.Config conf = Bitmap.Config.ARGB_8888;
					Bitmap bmp = Bitmap.createBitmap(100,100, conf);
					Canvas canvas1 = new Canvas(bmp);
					Paint color = new Paint();
					color.setTextSize(35);
					color.setColor(Color.BLACK);
					canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
							images[BeaconNum1]
					   ), 10,10, color);
					canvas1.drawText("", 5, 5, color);
					
					landmarks.removeMarker("Your Location");   // Our requirement is a Dynamically Moving Custom image marker. 
															  // So per each Iteration we remove the Already existing Marker and then place the new marker
					String s = "Closest to \t" + BeaconNum1;
				
					
					LatLng position = new LatLng(FinalLatitude,FinalLongitude);
					Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(s).draggable(true).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
					landmarks.addMarker("Your Location", marker);
					
					}
					catch (IndexOutOfBoundsException e) {
				    Toast.makeText(getApplicationContext(), "Please wait till Beacons are detected..Go Back and Refresh.",Toast.LENGTH_LONG).show();
				 
				}
				   catch(NullPointerException exception){
						
						Log.i("ash_hee", "not enough number of beacons null pointer");
				}
			}
			
		});
	}

	/**
	 * Called when the beacon service is successfully connected to beacon.
	 */
	@Override
	public void onBeaconServiceConnect() {
	
		
		beaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(Collection<Beacon> beacons,
					Region region) {
				if (beacons.size() > 0) {
					Log.i(TAG_SEARCHING_ACTIVITY_LOG, "Found " + beacons.size()
							+ "beacons");
					for (Iterator<Beacon> bIterator = beacons.iterator(); bIterator
							.hasNext();) {
						final Beacon beacon = bIterator.next();
						if (isGimbalTag(beacon)) {
							final String key = new StringBuilder()
									.append(beacon.getId1())
									.append(beacon.getId2())
									.append(beacon.getId3()).toString();
							discoveredBeaconMap.put(key, beacon);
							if(key.substring(0,3).equals("001")){        // Filter out the Student Beacons from the Beacons present in Marcus 
								sortedBeaconmap.put(key,beacon.getRssi()); // All the beacon values are stored in HashMap which is used later for sorting.
								}
						}
					}
					updateDiscoveredList();
				}
			}
		});

		try {
			beaconManager.startRangingBeaconsInRegion(new Region(
					"myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {
		
		}	
	}

	/**
	 * A filter check whether the detected beacon is a Gimbal tag used for
	 * project.
	 * 
	 * @param beacon
	 *            The detected beacon
	 * @return Whether the beacon is a Gimbal tag for project or not.
	 */
	private boolean isGimbalTag(Beacon beacon) {
		final String uuid = beacon.getId1().toString();
		final String tagIdentifier = uuid.split("-")[0];
		if (validGimbalIdentifiers.contains(tagIdentifier)) {
			return true;
		}
		return false;
	}

	@Override
	public void onLandmarkSelected(Marker landmark) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModeChange() {
		// TODO Auto-generated method stub
		
	}

	private void InitializeMarker(){
		String marcusBeacons = "" +
		"@Dis001,42.39354668258381,-72.52833187580109," +
		"@Dis002,42.39359447166392,-72.52839054912329," +
		"@Dis003,42.393661574404135,-72.52840027213097," +
		"@Dis004,42.39370936339676,-72.52845861017704," +
		"@Dis005,42.39377498034809,-72.52846665680408," +
		"@Dis006,42.39382895952455,-72.52852533012629," +
		"@Dis007,42.39389135741379,-72.52853605896235," +
		"@Dis008,42.393932460751394,-72.52859741449356," +
		"@Dis009,42.393999315520105,-72.52860512584448," +
		"@Dis010,42.39404537098601,-72.52866346389055," +
		"@Dis011,42.394103806904866,-72.52867016941309," +
		"@Dis012,42.39417412802318,-72.52873621881008," +
		"@Dis013,42.39423256382214,-72.52873655408621," +
		"@Dis014,42.39422736402869,-72.52877611666918," +
		"@Dis015,42.39416001428392,-72.52882674336433," +
		"@Dis016,42.39402060998703,-72.52868391573429," +
		"@Dis017,42.39397727821532,-72.52871174365282,";

	
		String[] marcusBeaconsArray = marcusBeacons.split("@");
		for(String marcusBeacon : marcusBeaconsArray){
			
			if(marcusBeacon.equals("")){
				continue;
			}
			int titleIndex = 0;
			int latitudeIndex = 1;
			int longitutdeIndex = 2;
			
			String[] beaconComponents = marcusBeacon.split(",");
			String beaconTitle = beaconComponents[titleIndex];
			double beaconLat = Double.parseDouble(beaconComponents[latitudeIndex]);
			double beaconLong = Double.parseDouble(beaconComponents[longitutdeIndex]);
			
			LatLng position = new LatLng(beaconLat,beaconLong);
			Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(beaconTitle).draggable(true));
			landmarks.addMarker(beaconTitle, marker);
		}
	}	
	private void setupMap(){
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		changeMapPositionAndZoom(new LatLng(marcusLat,marcusLng), knowlesZoom);
		MyUrlTileProvider mTileProvider = new MyUrlTileProvider(256, 256, mUrl);
		mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mTileProvider).zIndex(0));
		 landmarks = new Landmarks();
		 InitializeMarker();
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		return true;
	}
	
	public class MyUrlTileProvider extends UrlTileProvider {

		private String baseUrl;

		public MyUrlTileProvider(int width, int height, String url) {
		    super(width, height);
		    this.baseUrl = url;
		}

		@Override
		public URL getTileUrl(int x, int y, int zoom) {
		    try {
		        return new URL(baseUrl.replace("{z}", ""+zoom).replace("{x}",""+x).replace("{y}",""+y));
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    }
		    return null;
		}
	}
	
	private void changeMapPositionAndZoom(LatLng moveToPosition, int zoomLevel){
		changeMapPosition(moveToPosition);
		changeMapZoom(zoomLevel);
	}
	
	private void changeMapPosition(LatLng moveToPosition){
		CameraUpdate center = CameraUpdateFactory.newLatLng(moveToPosition);
		mMap.moveCamera(center);
	}
	
	private void changeMapZoom(int zoomLevel){
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(zoomLevel);
		mMap.animateCamera(zoom);
	}	
public void updateHTML(){}
@Override
public void onConnectionFailed(ConnectionResult arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void onConnected(Bundle arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void onDisconnected() {
	// TODO Auto-generated method stub
	
}
@Override
/**
 * ---> on Clicking the Marker we will be able to opens a New Activity called EntryActivity
 * ---> we can enter the Information about the victims, Their criticality, and information about How many injured.
 * ---> We will be able to send the data about from the EntryActivity to the Android Server which we built.
 * ---> We Bundle the Closest beacon value,Latitude and Longitude value and send it to EntryActivity
 * 
 */
public boolean onMarkerClick(Marker marker) {
	// TODO Auto-generated method stub
	LatLng point = marker.getPosition();
	
	double lati = point.latitude;
	double longi = point.longitude;
	String la = Double.toString(lati);
	String lo = Double.toString(longi);
	String cb = Integer.toString(BeaconNum1);
	String Position = cb+"beak"+la+"and"+lo;		
	
	Intent myintent = new Intent(getApplicationContext(), EntryActivity.class);
	
	Bundle myBundlemain = new Bundle();
	myBundlemain.putString("Lat_Long",Position);
	myintent.putExtras(myBundlemain);
	startActivity(myintent);
	return false;
}

	}
	







		
		
