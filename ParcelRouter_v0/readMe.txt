1.Fix Problem 2
change the logic of sensor in deciding left or right
//when it's in position 0, turn left for destination 0 and 1, turn right for destination 2 and 3
//when it's in position 1 or 2, turn left for even destination and turn right for odd destination
int Sensor_Actor::computeDirection( int p, int d )
{
	return (p==0&&d/2)||(p>0&&d%2);
}

2.Fix Problem 1 Approach 2
Currently the model can lose parcels because it passes on the parcel regardless of whether or not the next unit (i.e., chute1&2, switcher, 
or bin) is empty and can actually hold that parce. To fix this, we have to modify the model to make it only pass on the parcel to the next 
capsule instance if the next instance is not currently occupied. We add a message to the TransmissionProt by which the subsequent capsule 
instance can let the preceeding capsule instance know that it is free or occupied. If the subsequent instance is free, then pass the parcel, 
if not, then hold on for 0.5 second and ask again. 
Under TransmissionProt, the previous instance send test() to the next instance and the next instance returns free() or occupied().
1)Gen: when moreParcelsToGenerate, send test() to chute1 in position=0, if receives free, generate a new parcel and pass it, otherwise, wait
for 0.5 second and send test() again.
2)Chute: when it receives a parcel and chute1Delay/chute2Delay is over, send a test() message to chute2/switcher, if receives free, generate 
a new parcel and pass it, otherwise, wait for 0.5 second and send test() again. We need to add self-transitions when chute is free, occupied
or waiting for the subsequent instance to be free if it receives test() message from the previous instance, and responses free(), occupied(),
occupied() respectively. 
3)Switcher: we need to defer setSwitch(dir) message from sensor when switch has already got direction and received a parcel, because there
is no delay for sensor while chute2 may experience a delay. The setSwitch(message) may arrive before the parcel arrives, so we need to defer
it. When the parcel is sent to either left or right exit, we need to recallFront() to get message from defer queue. After we get direction from
sensor and get parcel from chute2, we need to send test() to leftP or rightP according to the direction to see whether the subsequent instance
is free. If receives free, generate a new parcel and pass it, otherwise, wait for 0.5 second and send test() again. We need to add self-
transitions when switcher is free, occupied or waiting for the subsequent left or right instance to be free if it receives test() message from 
the previous instance, and responses free(), occupied(), occupied(), occupied() respectively.
4)Bin: since bin has no time delay in processing the parcel and it only has one waitforparcel state, we just need to add a self-transition
to send free() to the previous instance when it receives test() message.
