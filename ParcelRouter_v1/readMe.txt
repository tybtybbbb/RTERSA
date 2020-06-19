1.Fix Problem 2
change the logic of sensor in deciding left or right
//when it's in position 0, turn left for destination 0 and 1, turn right for destination 2 and 3
//when it's in position 1 or 2, turn left for even destination and turn right for odd destination
int Sensor_Actor::computeDirection( int p, int d )
{
	return (p==0&&d/2)||(p>0&&d%2);
}

2.Fix Problem 1 Approach 2
Currently the model can lose parcels because it passes on the parcel regardless of whether or not the next unit (i.e., 
chute1&2, switcher, or bin) is empty and can actually hold that parce. To fix this, we have to modify the model to make 
chute1&2 to defer the message of receiving a new parcel when it's still dealing with the current parcel and switcher to 
defer the message of direction from sensor because there is no delay in sensor and the message of receiving a new parcel 
when it's still handling the current parcel.
1)Chute: when it's occupied, defer the message of transmit from enterP, after parcel is transmitted call enterP.recallFront()
2)Switcher: defer setSwitch(dir) message from sensor until switcher is ready to send parcel to the next instance, before
sending it, we call switcherP.recallFront() to get the direction for the current parcel. We also need to defer the message of 
receiving a new parcel, after the current parcel is sent, call enterP.recallFront(); to get the first deferred parcel.