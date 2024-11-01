Overview:
The third part of a multi-project series, this Java project builds on the GPS trip data by detecting stops in the trip log. Using the previously calculated GPS data, we refine the dataset by identifying and excluding stationary points (stops) to gain a more accurate understanding of the moving portions of the trip.

In this project, we implement methods to detect stops based on two heuristics:
    - Displacement Threshold: Points within a specified distance of the previous point are considered a stop.
    - Stop Radius: Groups of three or more points within a given radius are classified as a stop.
By removing stop points, we achieve a clearer view of the active segments of the trip, which allows us to compute more precise metrics such as moving time and average moving speed.

Features:
1. Stop Detection: Implements two heuristics for identifying stops in the trip data.
2. Separate Moving and Stationary Data: Keeps two versions of the trip data—one for the full dataset and another excluding stops, which is stored in a new movingTrip ArrayList.

Additional Metrics:
1. Moving Time: Calculates time spent actively moving.
2. Stopped Time: Calculates time spent at stop points.
3. Average Moving Speed: Computes speed based on moving segments only.

Core Methods:
1. h1StopDetection: Detects stops using a displacement threshold of 0.6 km and fills movingTrip with active points.
2. h2StopDetection: Detects stops using a radius of 0.5 km, grouping three or more points as a stop zone.
3. movingTime: Returns total time spent moving.
4. stoppedTime: Calculates total time spent stationary.
5. avgMovingSpeed: Computes the average speed during active portions of the trip.

Getting Started:
1. Setup: Clone the repository and ensure that the triplog.csv file generated in Project 1 is accessible.
2. Run: Implement and run each method to update the trip data and analyze active segments.
3. View Results: The refined dataset provides insights on active travel times and speeds, with stops removed for accuracy in metrics.