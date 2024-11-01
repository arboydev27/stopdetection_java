/**
 * @author Arboy Magomba
 * Version 1.0
 * Project 3
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TripPoint {

    private double lat;    // latitude
    private double lon;    // longitude
    private int time;    // time in minutes
    
    private static ArrayList<TripPoint> trip;    // ArrayList of every point in a trip
    private static ArrayList<TripPoint> movingTrip = new ArrayList<>();

    // constructor given time, latitude, and longitude
    public TripPoint(int time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }
    
    // returns time
    public int getTime() {
        return time;
    }
    
    // returns latitude
    public double getLat() {
        return lat;
    }
    
    // returns longitude
    public double getLon() {
        return lon;
    }
    
    // returns a copy of trip ArrayList
    public static ArrayList<TripPoint> getTrip() {
        return new ArrayList<>(trip);
    }
    
    public static ArrayList<TripPoint> getMovingTrip() {
        return new ArrayList<>(movingTrip);
    }
    
    // uses the haversine formula for great sphere distance between two points
    public static double haversineDistance(TripPoint first, TripPoint second) {
        // distance between latitudes and longitudes
        double lat1 = first.getLat();
        double lat2 = second.getLat();
        double lon1 = first.getLon();
        double lon2 = second.getLon();
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
 
        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.pow(Math.sin(dLon / 2), 2) *
                   Math.cos(lat1) *
                   Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
    
    // finds the average speed between two TripPoints in km/hr
    public static double avgSpeed(TripPoint a, TripPoint b) {
        
        int timeInMin = Math.abs(a.getTime() - b.getTime());
        
        double dis = haversineDistance(a, b);
        
        double kmpmin = dis / timeInMin;
        
        return kmpmin*60;
    }
    
    public static double avgMovingSpeed()
	{
		double dist = 0; 
		for (int i = 1; i < movingTrip.size(); i++)
		{
			dist += haversineDistance(movingTrip.get(i) , movingTrip.get(i - 1));
		}
		
		return dist / movingTime(); 
	}
    

    
    // returns the total time of trip in hours
    public static double totalTime() {
        int minutes = trip.get(trip.size()-1).getTime();
        double hours = minutes / 60.0;
        return hours;
    }
    
    public static double movingTime()
	{
		int hoursInMinutes = 60; 
		double totalTime = movingTrip.size() * 5; 
		totalTime -= 5; 
		double hours = totalTime / hoursInMinutes; 
		return hours; 
	}
        
    public static double stoppedTime() {
    	return totalTime() - movingTime();
    }
       
    
    // finds the total distance traveled over the trip
    public static double totalDistance() throws FileNotFoundException, IOException {
        
        double distance = 0.0;
        
        if (trip.isEmpty()) {
            readFile("triplog.csv");
        }
        
        for (int i = 1; i < trip.size(); ++i) {
            distance += haversineDistance(trip.get(i-1), trip.get(i));
        }
        
        return distance;
    }
    
    public static void readFile(String filename) throws FileNotFoundException, IOException
	{

		File file = new File(filename);
		Scanner fileScanner = new Scanner(file);
		trip = new ArrayList<TripPoint>();
		String[] fileData = null;
		
		while (fileScanner.hasNextLine()) 
		{
			String line = fileScanner.nextLine();

	
			fileData = line.split(",");

			if (!line.contains("Time")) 
			{
				trip.add(new TripPoint(Integer.parseInt(fileData[0]), Double.parseDouble(fileData[1]), Double.parseDouble(fileData[2])));
			}
			
		}

		fileScanner.close();
		
	}
    
    public static int h1StopDetection() throws FileNotFoundException, IOException 
	{
		
		readFile("triplog.csv"); 
		final double FIRST_HOUR = .6; 
		int counter = 0; 
		movingTrip = new ArrayList<TripPoint>(); 
		
		movingTrip.add(trip.get(0)); 
		for (int i = 1; i < trip.size(); i++) 
		{ 
			double dist = haversineDistance(trip.get(i) , trip.get(i - 1)); 
			
			if (dist >= FIRST_HOUR) 
			{ 
				movingTrip.add(trip.get(i));
			}
			
			else 
			{ 
				counter++;
			}
			
		}
		
		return counter; 
		
	}
    
    public static int h2StopDetection() throws FileNotFoundException, IOException
	{
		readFile("triplog.csv"); 
		movingTrip = new ArrayList<TripPoint>(trip); 

		ArrayList<TripPoint> stops = new ArrayList<TripPoint>(); 
		
	
		stops.add(trip.get(0)); 
		
		for (int i = 1; i < trip.size(); i++)
		{
			removeStop(trip.get(i), stops);
			
		}
		return trip.size() - movingTrip.size();
	}
    
    private static void removeStop(TripPoint point , ArrayList<TripPoint> stops)
	{
		boolean found = false; 
		final double SECOND_HEUR = .5;
		
		for (int i = 0; i < stops.size(); i++)
		{ 
			double dist = haversineDistance(point , stops.get(i));
			
			if (dist <= SECOND_HEUR) 
			{
				found = true;
				stops.add(point);
				break;
			}
			
			else
			{
				
				if (stops.size() < 3) 
				{
					stops.clear();
				}
				
			}
			
		}
		
		if (!found)
		{ 
			for (int i = 0; i < stops.size(); i++)
			{ 
				movingTrip.remove(stops.get(i));
			}
			stops.clear(); 
			stops.add(point); 
		}
		
	}
    
    public String toString()
	{
		
		return null;
	}
    
}





