package com.utilties;

import java.util.Arrays;

import com.utilties.util.CourtUtil;

public class Court {
public static int[] courtArray = {30,31,54,55,56,57};
private int time;
private int courtDigit;
private int courtNumber;
private boolean isLast;
private String displayTime;



public Court(int time, int courtDigit) {
	super();
	this.time = time;
	this.courtDigit = courtDigit;
}

public Court(int time, int courtDigit, boolean isLast, String displayTime) {
	super();
	this.time = time;
	this.courtDigit = courtDigit;
	this.isLast = isLast;
	this.displayTime = displayTime;
}

public String getDisplayTime() {
	displayTime = (new CourtUtil()).convertTime(time, isLast);
	return displayTime;
}

public boolean isLast() {
	return isLast;
}
public void setLast(boolean isLast) {
	this.isLast = isLast;
}
public int getTime() {
	return time;
}
public void setTime(int time) {
	this.time = time;
}
public int getCourtDigit() {
	return courtDigit;
}
public void setCourtDigit(int courtDigit) {
	this.courtDigit = courtDigit;
}
public int getCourtNumber() {
	courtNumber = getArrayIndex(courtArray,courtDigit)+1;
	
	return courtNumber;
}
public int getArrayIndex(int[] arr,int value) {

    int k=0;
    for(int i=0;i<arr.length;i++){

        if(arr[i]==value){
            k=i;
            break;
        }
    }
return k;
}

}
