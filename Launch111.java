import java.awt.List;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Bool;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
public class Launch111 {

	public static void stop() {
		Motor.B.stop();
		Motor.A.stop();
	}

	public static void moveForward() {
		Motor.A.forward();
		Motor.B.forward();

	}

	public static void moveBackward() {
		Motor.A.backward();
		Motor.B.backward();

	}

	public static void rotate(int time) {
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(time);
		Motor.A.stop();
		Motor.B.stop();
	}

	public static void turnRight() {
		Motor.A.rotate(200);
		Motor.B.rotate(-200);
	}

	public static void turnLeft() {
		Motor.A.rotate(-200);
		Motor.B.rotate(200);
	}

	public static void turn360() {
		Motor.A.rotate(-405);
		Motor.B.rotate(405);
	}
	
	public static void smallTurn() {
		Motor.A.rotate(-50);
		Motor.B.rotate(50);
	}

	public static void openMouth() {
		Motor.C.forward();
		Motor.D.forward();
		Delay.msDelay(5000);
	}

	public static void closeMouth() {
		Motor.C.backward();
		Motor.D.backward();
		Delay.msDelay(5000);
	}

	public static int isPressed() {
		Port p = LocalEV3.get().getPort("S4");
		EV3TouchSensor t = new EV3TouchSensor(p);
		SampleProvider touchProvider = t.getTouchMode();
		float[] tab;
		tab = new float[touchProvider.sampleSize()];
		touchProvider.fetchSample(tab, 0);
		t.close();
		return (int) tab[0];
	}

	public static boolean detect() {

		EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
		final SampleProvider sp = us1.getDistanceMode();
		ArrayList<Integer> distanceValue= new ArrayList<Integer>();

		final int iteration_threshold = 100;
		for(int i = 0; i <= iteration_threshold; i++) {

			float [] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distanceValue.add((int)sample[0]);
			//System.out.println("Iteration: " + i + ", Distance: " + distanceValue);
			//Delay.msDelay(500);
		}
		System.out.println(distanceValue.get(0));
		if(distanceValue.get(0)!=0)
			return true;
		return false;
	}

	public static boolean detect2() {
		//Robot Configuration
		EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
		//Configuration
		int HALF_SECOND = 500;
		SampleProvider sp;
		int distanceValue = 0;
		for(int i = 0; i <= 10; i++) {
			sp = us1.getListenMode();
			int sampleSize = sp.sampleSize();
			float[] sample = new float[sampleSize];
			sp.fetchSample(sample, 0);

			Delay.msDelay(2000);

			sp = us1.getDistanceMode();
			sampleSize = sp.sampleSize();
			sample = new float[sampleSize];
			sp.fetchSample(sample, 0);

			distanceValue = (int)sample[0];

			System.out.println("Iteration: {}, Distance: {}" + i + " "+ distanceValue);
		}
		if(distanceValue!=0)
			return true;
		return false;
	}
	
	public static int distance() {
		EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
		final SampleProvider sp = us1.getDistanceMode();
		ArrayList<Integer> distanceValue= new ArrayList<Integer>();
		int[] distances = new int[100]
		
		final int iteration_threshold = 100;
		for(int i = 0; i <= iteration_threshold; i++) {
			float [] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distanceValue.add((int)sample[0]);
			smallTurn();
		}
		return distanceValue;
	}

	public static void main(String[] args) {
		Boolean detected=false;
		while(!detected) {
			if(detect()) {
				detected=true;
				moveForward();
				Delay.msDelay(1000);
			}
		}
		openMouth();
		closeMouth();
		
	/*	openMouth();
		int press = 0;
		while(press==0 ) {
			moveForward();
			if(isPressed() == 1) {
				press = 1 ;
				stop();
				closeMouth();
				Delay.msDelay(3000);
				turn360();
				//Delay.msDelay(2000);
				moveForward();
				Delay.msDelay(3000);
				stop();
			}

		}
		//detect2();
		//closeMouth();*/
	}
	}