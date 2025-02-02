package cs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Simulation {
	private static int num_provider;
	private static int time_provider;
	private static int num_nurses;
	private static int time_nurses;
	private static int num_admin;
	private static int time_admin;
	private static int num_room;
	
	private static int intervalTime;  
	private static int simulation_time;
	
	private static Stack<Person> providers = new Stack<Person>();
	private static Stack<Person> nurses = new Stack<Person>();
	private static Stack<Person> admin_ass = new Stack<Person>();
	private static Room[] rooms; 
	
	private static Queue<Patient> emergency = new Queue<Patient>();
	private static Queue<Patient> regular = new Queue<Patient>(); 
	private static Queue<Patient> checkOut = new Queue<Patient>();
	
	private static Queue<Person> adminQ = new Queue<Person>();
	private static Queue<Patient> beingServedQ = new Queue<Patient>();
	
	private static int currentTime = 0;
	private static int totalPatients = 0;
	private static int served; //totalPatients - emergency.getLength() - regular.getLength() - checkOut.getLength();
	private static int totalWaitTime = 0;
	
	private final static String fileName = "sim-trial.txt";
	private static PrintWriter pw;
	
	public static void main(String[] args) {
		initialize();
		System.out.println("Sim start");
		simulate();
		System.out.println("Sim complete");
	}
	
    // Method to initialize simulation data
    private static void initialize() {
        Scanner obj = new Scanner(System.in);

        // Input prompts for user
        System.out.println("NOTE");
        System.out.println("For logical reasons, the number of providers,nurses and rooms must all be the same");
        System.out.println("All values must be greater than 0");
        System.out.println();
        
        System.out.print("Enter number of providers: ");
        num_provider = obj.nextInt();

        System.out.print("Enter time(s) taken by provider to serve a patient: ");
        time_provider = obj.nextInt();

        System.out.print("Enter number of nurses: ");
        num_nurses = obj.nextInt();

        System.out.print("Enter time(s) taken by a nurse to serve a patient: ");
        time_nurses = obj.nextInt();

        System.out.print("Enter number of rooms: ");
        num_room = obj.nextInt();  //Number of rooms must be equal to the number of providers and nurses

        System.out.print("Enter number of admin assistants: ");
        num_admin = obj.nextInt();

        System.out.print("Enter time(s) taken by admin assistant to check out: ");
        time_admin = obj.nextInt();

        System.out.print("Enter time(s) to run the simulation: ");
        simulation_time = obj.nextInt(); // Should be 180s
        
        rooms = new Room[num_room];

        // Initialize stacks
        for (int i = 0; i < num_provider; i++) {
            Person provider = new Person("P" + (i + 1), time_provider);
            providers.push(provider);
        }

        for (int i = 0; i < num_nurses; i++) {
            Person nurse = new Person("N" + (i + 1), time_nurses);
            nurses.push(nurse);
        }

        for (int i = 0; i < num_admin; i++) {
            Person admin = new Person("AA" + (i + 1), time_admin);
            admin_ass.push(admin);
        }

        // Initialize rooms
		for(int i = 0; i < rooms.length; i++) {
			Room room = new Room("Room" + (i + 1));
			rooms[i] = room;
        }
		
		obj.close();
    }
    
    private static void Arrival() {
        double prob = Math.random();  // Generate a unique probability for each patient
        for (int i = 0; i < 2; i++) {
            Patient patient = new Patient("Patient_" + ++totalPatients , currentTime);
            if (prob <= 0.2) { // If probability is less than 0.2, assign to emergency queue
                emergency.add(patient); 
            } else {
                regular.add(patient); 
            }
        }
    }
    
	private static boolean patientAvailable() {
		boolean available = false;
		if(emergency.getLength() > 0 || regular.getLength() > 0) {
			available = true;
		}
		return available;
	}

	private static Patient getPatientAvailable() {
		Patient patient = null;
		if(emergency.getLength() > 0) {
			patient = emergency.remove();
		}
		else if (regular.getLength() > 0) {
			patient = regular.remove();
		}
		return patient;
	}
    
	private static boolean personAvailable(Stack<Person> stack) {
		return (stack.getLength() > 0);
	}
	
	private static Person getPersonAvailable(Stack<Person> stack) {
		if(stack.getLength() > 0) {
			return stack.pop();
		}
		else {
			return null;
		}
	}
	
	private static boolean roomAvailable() {
		boolean available = false;
		for(int i = 0; i < rooms.length && available == false; i++) {
			if(!rooms[i].getOccupied()) {
				available = true;
			}
		}
		return available;
	}
	
	private static Room getRoomAvailable() {
		for(int i = 0; i < rooms.length; i++) {
			if(!rooms[i].getOccupied()) {
				rooms[i].setOccupied(true);
				return rooms[i];
			}
		}
		return null;
	}
	
	private static void simulate() {
	    for(currentTime = 0; currentTime < simulation_time; currentTime += intervalTime) {
	    	intervalTime = (int)(Math.random() * 5) + 1; // Random number between 1 and 5
	        System.out.println("Time: " + currentTime); // Output current simulation time to console

	        Arrival(); // Patient arrival

	        // Start handling patients and assigning resources (nurses, rooms)
	        while (patientAvailable() && personAvailable(nurses) && roomAvailable()) {
	            Patient patient = getPatientAvailable();
	            patient.setServiceStartTime(currentTime);
	            totalWaitTime += (patient.getServiceStartTime() - patient.getArrivalTime());
	            Person nurse = getPersonAvailable(nurses);
	            Room room = getRoomAvailable();
	            room.setCurrentPatient(patient);
	            room.setCurrentPerson(nurse);
	            room.setPersonType("Nurse");
	            nurse.setWorking(true);
	        }

	        // Check rooms and update service completion logic
	        for (int i = 0; i < rooms.length; i++) {
	            if (rooms[i].getOccupied()) {
	                // Only check if there is a person in the room
	                Person currentPerson = rooms[i].getCurrentPerson();
	                if (currentPerson != null && currentPerson.getCurrentCycleTime() >= currentPerson.getTimeToServe()) {
	                    currentPerson.setCurrentCycleTime(0);
	                    currentPerson.setWorking(false);
	                    if ("Nurse".equals(rooms[i].getPersonType())) {
	                        nurses.push(currentPerson);
	                    } else if ("Provider".equals(rooms[i].getPersonType())) {
	                        providers.push(currentPerson);
	                    }
	                    rooms[i].setCurrentPerson(null);
	                }
	            }
	        }

	        // Process admin assistants and served queue
	        while (adminQ.getLength() > 0 && adminQ.getFront().getCurrentCycleTime() >= adminQ.getFront().getTimeToServe()) {
	            Person admin = adminQ.remove();  // Get the first person from the queue
	            admin.setCurrentCycleTime(0); // Reset the cycle time
	            admin.setWorking(false);      // Set the worker as not working
	            admin_ass.push(admin);        // Push the admin assistant back to the stack
	        }

	        // Provider assignment
	        for (int i = 0; i < rooms.length; ++i) {
	            if (rooms[i].getOccupied() && rooms[i].getPersonType().equals("Nurse") && rooms[i].getCurrentPerson() == null) {
	                if (personAvailable(providers)) {
	                    Person provider = getPersonAvailable(providers);
	                    rooms[i].setCurrentPerson(provider);
	                    rooms[i].setPersonType("Provider");
	                    provider.setWorking(true);
	                }
	            }
	        }

	        // AA assignment
	        for (int i = 0; i < rooms.length; ++i) {
	            if (rooms[i].getOccupied() && rooms[i].getPersonType().equals("Provider") && rooms[i].getCurrentPerson() == null) {
	                Patient patient = rooms[i].getCurrentPatient();
	                rooms[i].setCurrentPatient(null);
	                rooms[i].setOccupied(false);
	                rooms[i].setPersonType(null);
	                checkOut.add(patient);
	            }
	        }

	        // Process checkout and assign work to admin assistants
	        while (checkOut.getLength() > 0 && personAvailable(admin_ass)) {
	            Person aa = getPersonAvailable(admin_ass);
	            aa.setWorking(true);
	            adminQ.add(aa);
	            beingServedQ.add(checkOut.remove()); // aaQ and beingServedQ will go hand in hand.
	        }

	        // Increase the time for all rooms and persons
	        for (int i = 0; i < rooms.length; ++i) {
	            if (rooms[i].getOccupied()) {
	                rooms[i].setTotalOccupiedTime(rooms[i].getTotalOccupiedTime() + intervalTime);
	                if (rooms[i].getCurrentPerson() != null) {
	                    Person currentPerson = rooms[i].getCurrentPerson();
	                    currentPerson.setCurrentCycleTime(currentPerson.getCurrentCycleTime() + intervalTime);
	                    currentPerson.setTotalWorkedTime(currentPerson.getTotalWorkedTime() + intervalTime);
	                }
	            }
	        }

	        // Process people in admin assistant queue
	        if (adminQ.getLength() > 0) {
	            // Assuming aaQ can return an iterator or front element for direct iteration
	            Person currentPerson = adminQ.getFront();
	            while (currentPerson != null) {
	                currentPerson.setCurrentCycleTime(currentPerson.getCurrentCycleTime() + intervalTime);
	                currentPerson.setTotalWorkedTime(currentPerson.getTotalWorkedTime() + intervalTime);
	                
	                // Move to the next person in the queue
	                adminQ.remove();  // Remove the current person
	                if (adminQ.getLength() > 0) {
	                    currentPerson = adminQ.getFront();  // Get the next person
	                } else {
	                    currentPerson = null;  // End of the queue
	                }
	            }
	        }

	        //Simulate the passage of time
	        try {
	            TimeUnit.SECONDS.sleep(intervalTime);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	         printToFile();
	         printStatsToFile();
	    }
	}
	
	private static void printToFile() {
	    try {
	        pw = new PrintWriter(fileName);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }

	    // Emergency Queue - Print and Restore the Queue
	    printQueueToFile(emergency, "Emergency Queue");
	    pw.println();
	    
	    // Regular Queue - Print and Restore the Queue
	    printQueueToFile(regular, "Regular Queue");
	    pw.println();

	    // Nurses - Stack Printing
	    printStackToFile(nurses, "Available Nurses", pw);
	    pw.println();
	    
	    // Providers - Stack Printing
	    printStackToFile(providers, "Available Providers", pw);
	    pw.println();

	    // Admin Assistants - Stack Printing
	    printStackToFile(admin_ass, "Available Administrative Assistants", pw);
	    pw.println();

	    // Rooms - Iterate through each room and print status
	    for (int i = 0; i < rooms.length; i++) {
	        Room room = rooms[i];
	        Patient patient = room.getCurrentPatient();
	        Person person = room.getCurrentPerson();
	        pw.print(room.getName() + ": ");
	        if (patient != null) pw.print("Patient: " + patient.getName() + " ");
	        if (person != null) pw.print("Staff: " + person.getName() + " ");
	        if (!room.getOccupied()) pw.print("Room Available");
	        pw.println();
	    }

	    pw.println();

	    // Checkout Queue
	    printQueueToFile(checkOut, "Checkout Queue");
	    pw.println();

	    pw.close();
	}

	// Utility to print and restore queue
	private static void printQueueToFile(Queue<?> queue, String queueName) {
	    pw.print(queueName + ": ");
	    Queue<?> tempQueue = new Queue<>();
	    while (queue.getLength() > 0) {
	        Object element = queue.remove();
	        pw.print(element + " ");
	        tempQueue.addObject(element);  // Store the element back to preserve queue
	    }
	    while (tempQueue.getLength() > 0) {
	        queue.addObject(tempQueue.remove());  // Rebuild the original queue
	    }
	    pw.println();
	}

	// Utility to print and restore stack
	private static void printStackToFile(Stack<?> stack, String stackName, PrintWriter pw) {
	    pw.print(stackName + ": ");
	    Stack<Object> tempStack = new Stack<>();  // Temp stack to preserve the original stack

	    // Pop all elements from the original stack and print them, store in tempStack
	    while (stack.getLength() > 0) {
	        Object element = stack.pop();
	        pw.print(element + " ");
	        tempStack.push(element);  // Push element to tempStack to restore the original stack
	    }

	    // Restore the original stack by pushing elements back from tempStack
	    while (tempStack.getLength() > 0) {
	        stack.pushObject((Object) tempStack.pop());  // Push elements back into the original stack
	    }

	    pw.println(); 
	}
	
	private static void printStatsToFile() {
	    File file = new File(fileName);
	    FileWriter fw = null;
	    BufferedWriter bw = null;
	    PrintWriter pw = null;

	    try {
	        fw = new FileWriter(file, true);
	        bw = new BufferedWriter(fw);
	        pw = new PrintWriter(bw);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }

	    // Write patient statistics
	    served = totalPatients - emergency.getLength() - regular.getLength() - checkOut.getLength();
	    pw.println();
	    pw.println("Emergency Queue has " + emergency.getLength() + " patient(s)");
	    pw.println("Regular Queue has " + regular.getLength() + " patient(s)");
	    pw.println("Total Patients Served: " + served);
	    pw.println("Total Wait Time: " + totalWaitTime + " seconds");
	    if (served > 0) {
	        pw.println("Average Wait Time: " + (float) totalWaitTime / served + " seconds");
	    } else {
	        pw.println("Average Wait Time: N/A");
	    }
	    pw.println();

	    // Nurses' working time
	    pw.println("Nurse Work Time");
	    printPersonStackStats(nurses, pw);

	    // Check if any nurses are currently in rooms
	    for (Room room : rooms) {
	        if ("Nurse".equals(room.getPersonType())) {
	            pw.println(room.getCurrentPerson().getName() + ": " + room.getCurrentPerson().getTotalWorkedTime() + " seconds");
	        }
	    }
	    pw.println();

	    // Providers' working time
	    pw.println("Provider Work Time");
	    printPersonStackStats(providers, pw);

	    // Check if any providers are currently in rooms
	    for (Room room : rooms) {
	        if ("Provider".equals(room.getPersonType())) {
	            pw.println(room.getCurrentPerson().getName() + ": " + room.getCurrentPerson().getTotalWorkedTime() + " seconds");
	        }
	    }
	    pw.println();

	    // Admin Assistants' working time
	    pw.println("Admin Assistant Worked Time:");
	    pw.println(served*time_admin*num_admin + " seconds");

	    // Check admin assistants currently serving
	    Queue<Person> tempAAQueue = new Queue<>();
	    while (adminQ.getLength() > 0) {
	        Person person = adminQ.remove();
	        pw.println(person.getName() + ": " + person.getTotalWorkedTime() + " seconds");
	        tempAAQueue.add(person); // Restore aaQ
	    }
	    while (tempAAQueue.getLength() > 0) {
	    	adminQ.add(tempAAQueue.remove());
	    }
	    pw.println();

	    // Room utilization time
	    pw.println("Room Use Time");
	    for (Room room : rooms) {
	        pw.println(room.getName() + ": " + room.getTotalOccupiedTime() + " seconds");
	    }

	    pw.close(); 
	}

	// Utility method to print stats for a stack of persons
	private static void printPersonStackStats(Stack<Person> stack, PrintWriter pw) {
	    Stack<Person> tempStack = new Stack<>();
	    while (stack.getLength() > 0) {
	        Person person = stack.pop();
	        pw.println(person.getName() + ": " + person.getTotalWorkedTime() + " seconds");
	        tempStack.push(person); // Store in tempStack to restore original
	    }
	    while (tempStack.getLength() > 0) {
	        stack.push(tempStack.pop());
	    }
	}

}