package com.example.radbeacontestingapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.sql.*;
//import com.example.client.MainActivity;
//import com.example.client.MainActivity.MyClientTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EntryActivity extends Activity {
	/**
 	 * This Activity Acts like a Client . 
 	 * It sends information about the victims and location to the Android server
 	 * 
 	 */ 
	private String position;
	private TextView LatLong;
	ImageButton buttonConnect;
	TextView closeBeak;
	TextView dateTime;
	EditText emergen;
	EditText people;
	EditText priority;
	String EmerCond;
	String victNum;
	String priorLevel;
	String sendString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
	
		Bundle myBundle = getIntent().getExtras();
		position = myBundle.getString("Lat_Long");	
		 String[] str_array = position.split("beak");
		LatLong= (TextView)findViewById(R.id.latlong);
		closeBeak= (TextView)findViewById(R.id.closebeacon);
		dateTime= (TextView)findViewById(R.id.datetime);
		
		Date d=new Date();
		 String[] str1_array = (d.toString()).split(" ");
		dateTime.setText(String.valueOf(str1_array[3]));	
		closeBeak.setText(str_array[0]);
		LatLong.setText(str_array[1]);
		
		emergen = (EditText)findViewById(R.id.emergency);
		priority = (EditText)findViewById(R.id.priority);
		people = (EditText)findViewById(R.id.people);
		
		buttonConnect = (ImageButton) findViewById(R.id.imageButton1);
		buttonConnect.setOnClickListener(buttonConnectOnClickListener);

	
	}	
	/**
 	 * On clicking the button we Append all the values in a string and send the information to the server
 	 *
 	 */ 
	OnClickListener buttonConnectOnClickListener = new OnClickListener() {
		
		  @Override
		  public void onClick(View arg0) {
			  priorLevel=priority.getText().toString();
			  
			  victNum = people.getText().toString();
				EmerCond = emergen.getText().toString();
				Log.i("vitasta",priorLevel);
				Log.i("vitasta",victNum);
				Log.i("vitasta",EmerCond);
				sendString = priorLevel+"priorlev"+victNum+"victNum"+EmerCond+"condition of patient"+position;

		   if(LatLong.equals("")){
		    LatLong = null;
		    Toast.makeText(EntryActivity.this, "No Msg sent", Toast.LENGTH_SHORT).show();
		   }
		   
		  
		   MyClientTask myClientTask = new MyClientTask("72.19.89.147",8080,sendString);
		   myClientTask.execute();
		 
		  }
		 };
		 /**
		 	 * MyClientTask is an AsyncTask that creates a socket connection between the client and server
		 	 * Collect the Data and send the data to the server.
		 	 */ 
		 public class MyClientTask extends AsyncTask<Void, Void, Void> {

			  String dstAddress;
			  int dstPort;
			  String response = "";
			  String msgToServer;

			  MyClientTask(String addr, int port, String msgTo) {
			   dstAddress = addr;
			   dstPort = port;
			   msgToServer = msgTo;
			  }

			  @Override
			  protected Void doInBackground(Void... arg0) {

			   Socket socket = null;
			   DataOutputStream dataOutputStream = null;
			   DataInputStream dataInputStream = null;

			   try {
			    socket = new Socket(dstAddress, dstPort);
			    dataOutputStream = new DataOutputStream(
			      socket.getOutputStream());
			    dataInputStream = new DataInputStream(socket.getInputStream());
			    
			    if(msgToServer != null){
			     dataOutputStream.writeUTF(msgToServer);
			    }
			    
			    response = dataInputStream.readUTF();

			   } catch (UnknownHostException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    response = "UnknownHostException: " + e.toString();
			   } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    response = "IOException: " + e.toString();
			   } finally {
			    if (socket != null) {
			     try {
			      socket.close();
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

			    if (dataInputStream != null) {
			     try {
			      dataInputStream.close();
			     } catch (IOException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			     }
			    }
			   }
			   return null;
			  }
	
			  protected void onPostExecute(Void result) {
				   super.onPostExecute(result);
			  }
	
}		 
}
