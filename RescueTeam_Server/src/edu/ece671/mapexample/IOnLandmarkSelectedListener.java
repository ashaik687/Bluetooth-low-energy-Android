package edu.ece671.mapexample;

import com.google.android.gms.maps.model.Marker;

public interface IOnLandmarkSelectedListener {
	public void onLandmarkSelected(Marker landmark);
	public void onModeChange();
}
