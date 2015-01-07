package edu.ece671.mapexample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

//import com.example.radbeacontestingapp.R;
//import com.example.radbeacontestingapp.EntryActivity;
//import com.example.server.MainActivity;
//import com.example.server.MainActivity.SocketServerThread;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar.Tab;

public class MainActivity extends Activity implements IOnLandmarkSelectedListener,LocationListener,OnMarkerClickListener {
	/**
	 * This is a Server Which recieves the information about the victims and Display the information on the Google Maps UI
	 * This Activity has a GoogleMaps UI
	 * This Android device will be present with the Rescue team. 
	 * 
	 */	
	public static int landmarknum;
	private GoogleMap mMap;
	String mUrl = "http://percept.ecs.umass.edu/course/marcusbasement/{z}/{x}/{y}.png";
	
	double marcusLat = 42.393985;
	double marcusLng = -72.528622;
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

	public String closebeak="";
	public String title = "Title ";
	public int titleNumber = 0;
	private IOnLandmarkSelectedListener landmarkListener;
	public Uri imageUri;
	private Landmarks landmarks;	
	private Activity activity;
	
	TextView info, infoip, msg;
	 String message = "";
	 ServerSocket serverSocket;
	 public Marker marker1;
	 public Marker marker2;
	 LatLng point1;
	
	 public double currLat=0;
	 public double currLong=0;
	 public static String victNum;
	 public static String emerCond;
	 public static String priorityLevel;
	 public static String closestBeak;
	 public int[] images;
	 public static HashMap<String,String> closeBeakMap;
	 public static HashMap<String,String> victMap;
	 public static HashMap<String,String> priorityMap;
	 public static HashMap<String,String> emergenMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
            landmarkListener = (IOnLandmarkSelectedListener) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
		setupMap();	
		Thread socketServerThread = new Thread(new SocketServerThread());
		  socketServerThread.start();	
		 mMap.setMyLocationEnabled(true);
		  LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		   lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		   mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    .getMap();
		   mMap.setOnMarkerClickListener(this);
	/**
	 * The images Array is used to reference the images of Beacons from the Drawable folder
	 * 
	 */
		   			images = new int[50];
					images[0]=R.drawable.marcus;
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
	
					closeBeakMap = new HashMap<String,String>();
					victMap = new HashMap<String,String>();
					priorityMap = new HashMap<String,String>();
					emergenMap = new HashMap<String,String>();
	
	}
	
	
	protected void onDestroy() {
		  super.onDestroy();

		  if (serverSocket != null) {
		   try {
		    serverSocket.close();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		  }
		 }	
	
	/**
	 * SocketServerThread class runs a thread on the background which can read the Lat & Long values, and the information sent from the client
	 * @author ashaik
	 */
	private class SocketServerThread extends Thread {

		  static final int SocketServerPORT = 8080;
		  int count = 0;
		  double lat1;
		  double long1;
		  String messageFromClient = "";
		  String stringa;
		  String stringb;
		  String stringc;
		  Polyline route;
		  String LatAndLong;
		  @Override
		  public void run() {
		   Socket socket = null;
		   DataInputStream dataInputStream = null;
		   DataOutputStream dataOutputStream = null;

		   try {
		    serverSocket = new ServerSocket(SocketServerPORT);
		    while (true) {
		     socket = serverSocket.accept();
		     dataInputStream = new DataInputStream(
		       socket.getInputStream());
		     dataOutputStream = new DataOutputStream(
		       socket.getOutputStream());

		     messageFromClient = dataInputStream.readUTF();
		     
		     count++;
		   /**
		    * The Server Receives a string from client 
		    * While sending from the client side we have appended all the information into one string using Delimiters
		    * On the server side we to split the string based on delimiter and extract the information
		    * 
		    */
		     String[] str_array1 = messageFromClient.split("condition of patient");
		     String[] str1_array = str_array1[1].split("and");  
		     String[] str5_array = str1_array[0].split("beak");
		   
		     lat1 = Double.parseDouble(str5_array[1]);
		     long1 = Double.parseDouble(str1_array[1]);
		     LatAndLong = String.valueOf(lat1)+"And"+String.valueOf(long1);
		     closestBeak=str5_array[0];
		  
		     String[] str2_array = str_array1[0].split("victNum");
		     emerCond = str2_array[1];
		     String[] str3_array = str2_array[0].split("priorlev");
		 		  
		     victNum = str3_array[1];
		 		     
		     priorityLevel= str3_array[0];
		     /**
		 	 * The information about the Victims sent from the client side is stored in a HashMap
		 	 * The key used to store the Victim information is the unique string "latitude"+"And"+"longitude" 
		 	 * This key is unique to each location
		 	 */	 
		     closeBeakMap.put(LatAndLong, closestBeak);
		     victMap.put(LatAndLong, victNum);
		     priorityMap.put(LatAndLong, priorityLevel);
		     emergenMap.put(LatAndLong, emerCond);
		     
		     
		     
			     
		     MainActivity.this.runOnUiThread(new Runnable() {

		      @Override
		      public void run() {
		    	  /**
				    * **********Procedure for placing a Custom image Marker and Polyline on Server Android tablet*********
				    * ---> Obtain the Value of Closest beacon from the information obtained from the string
				    * ---> Based on the Closest Beacon value select the image of that corresponding beacon
				    * ---> Place the Image as a Marker on the Google maps UI of the Server
				    * ---> Current location of the Rescue Team is found out using GoogleMaps Location services
				    * ---> A Line joining the Current location of the Rescue team and the victim location recieved from 
				    * 						server is drawn using PolyLine
				    */ 
		    	Bitmap.Config conf = Bitmap.Config.ARGB_8888;
				Bitmap bmp = Bitmap.createBitmap(100,100, conf);

				Canvas canvas1 = new Canvas(bmp);

				Paint color = new Paint();
				color.setTextSize(35);
				color.setColor(Color.BLACK);

				canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
						images[Integer.parseInt(closestBeak)]
						), 10,10, color);
				canvas1.drawText("", 5, 5, color);
				String s = "Closest to \t" + Integer.parseInt(closestBeak);

				//String s = LatAndLong;
				LatLng position = new LatLng(lat1,long1);
				Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(s).draggable(true).icon(BitmapDescriptorFactory.fromBitmap(bmp)));

				landmarks.addMarker("Your Location", marker);  
		       route =   mMap.addPolyline(new PolylineOptions().width(5).color(Color.RED).geodesic(true)
		                .add(new LatLng(lat1, long1)) 
		                .add(new LatLng(currLat, currLong)) 
					 );
		      }
		     });

		     String msgReply = "message #" + count;
		     dataOutputStream.writeUTF(msgReply);

		    }
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    
		   } finally {
		    if (socket != null) {
		     try {
		      socket.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }

		    if (dataInputStream != null) {
		     try {
		      dataInputStream.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }

		    if (dataOutputStream != null) {
		     try {
		      dataOutputStream.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }
		   }
		  }

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
		mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mTileProvider).zIndex(-1));
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

	@Override
	public void onLandmarkSelected(Marker landmark) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModeChange() {
		// TODO Auto-generated method stub
		
	}
	@Override
/**
 * As the Rescue team moves,A Marker indicating the current location of the Rescue team will also move 
 */	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (marker2 != null) {
            marker2.remove();
        }
	
		currLat = location.getLatitude();
		currLong = location.getLongitude();
		LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
		marker2=mMap.addMarker(new MarkerOptions()
	        .position(point)
	        .title("start here")           
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); 
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * On Clicking the Marker placed by the server from the Value received from the client 
	 * ---> A NewActivity called as ShowActivity will be started
	 * ---> Show Activity Displays the victim information that is entered on the client side  
	 */	
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		LatLng point = marker.getPosition();
		double lati = point.latitude;
		double longi = point.longitude;
		String la = Double.toString(lati);
		String lo = Double.toString(longi);
		String LatiLongi = la +"And"+lo;
		Intent myintent = new Intent(getApplicationContext(), ShowActivity.class);
		  /**
	 	 * **********Sending the information to ShowActivity**************
	 	 * ---> The Image Marker has information of latitude and longitude on which it is placed
	 	 * ---> We have already stored in HashMap the information about the victim using key containing Lat and Long
	 	 * ---> Here we extract that data from the HashMap using the particular key value and bundle
	 	 * 
	 	 */	
		
		Bundle myBundlemain = new Bundle();
		myBundlemain.putString("closebeak",closeBeakMap.get(LatiLongi));
		myBundlemain.putString("emergen", emergenMap.get(LatiLongi));
		myBundlemain.putString("prior", priorityMap.get(LatiLongi));
		myBundlemain.putString("vict", victMap.get(LatiLongi));
		
		

		myintent.putExtras(myBundlemain);
		startActivity(myintent);
		return false;	
	}

	
}
