package com.App;

import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.application.Platform;

public class Timekeeper implements Runnable {
	private int minutes = 0;
	private TicTacToe tic;
	int i;
	
	Timekeeper(TicTacToe tic){
		this.tic = tic;
	}

	public void countGameTime(){
		for(i = 0 ; i < 600; i++){		
			if(i<10){
				 Platform.runLater(new Runnable() {
				     @Override 
				     public void run() {
				    	 tic.updateTime("0 : 0"+ i);
				     }
				 });
			}
			else if(i >= 10 && i < 60){
				Platform.runLater(new Runnable() {
				     @Override 
				     public void run() {
				    	 tic.updateTime("0 : "+ i);
				     }
				 });
			}
			else if(i >= 60 && i <5000){
				int y = i % 60;
					if(y == 0){
						minutes++;
						Platform.runLater(new Runnable() {
						     @Override 
						     public void run() {
						    	 tic.updateTime(minutes + " : 00");
						     }
						 });
					}
					else{
						if(y < 10){
							Platform.runLater(new Runnable() {
							     @Override 
							     public void run() {
							    	 tic.updateTime(minutes+" : 0"+ y);
							     }
							 });
						}
						else{
							Platform.runLater(new Runnable() {
							     @Override 
							     public void run() {
							tic.updateTime(minutes+" : "+ y);
							     }
							 });
						}
					}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Time is elapsed");
				break;
			}
		}		
	}
	
	
	@Override
	public void run() {
		countGameTime();
	}
	
//	public static void main(String[]args){
//		Timekeeper keeper = new Timekeeper();
//		Thread t = new Thread(keeper);
//		t.start();
//	}

}
