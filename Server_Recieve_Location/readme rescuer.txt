********************************************************************
This project plots the location acquired from the responder on the map 
with a bit map image  and marks the current location on the map.
***********************************************************************
Steps involved in the process are:

#1 Bit map images initialization: This involves putting the images in
a bit map and these images are called whenever the appropriate 
beacon image is called.

#2 Plot the current location : The current location of the rescuer is 
plotted on the google maps.

#3 Socket initialization: android sockets are initiated where the rescuer 
acquires the data sent by the responder.
 
#4: Data acquisition: The data given by the responder is acquired by the 
rescuer by clicking the marker and is analyzed.

