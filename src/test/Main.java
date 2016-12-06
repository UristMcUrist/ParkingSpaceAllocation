package test;

import java.io.IOException;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) throws IOException {
		Function<Integer, Integer> function = new Function<Integer, Integer>() {
			
			@Override
			public Integer apply(Integer t) {
				double distanceTo8 = Math.abs(480-t);
				return (int) (800/(distanceTo8+1));
			}
		};
		
		for(int i=0;i<1440;i+=30){
			System.out.print(function.apply(i)+",");
		}
		
//		DriverGenerator.generateData(function, "driverData.txt");
	}
}
