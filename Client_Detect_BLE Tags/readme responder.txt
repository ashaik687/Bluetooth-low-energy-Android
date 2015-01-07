********************************************************************
This project plots the location of the responder on the map 
with a bit map image  and marks the ground zero location on the map.
***********************************************************************
Steps involved in the process are:

#1 Bit map images initialization: This involves putting the images in
a bit map and these images are called whenever the appropriate 
beacon image is called.

#2 Distance calculation: The distance of the obatined rssi values are 
plug in the eqaution and the distances are calculated.

#3: Sorting the beacons: The distances are put in a tree map where 
the values are stored in ascending order. this sorting helpa us to find 
the nearest beacon.

#4 Localization : weighted centroid method is used to calculate the 
location of the responder and marks it on the map  with the nearest
beacon image.

#5 Socket initialization: android sockets are initiated where the responder 
sends the data acquired to the rescuer. 