package project3;

import static java.lang.System.exit;

import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the date file.");
		 File datafile = new File(scan.nextLine()); // open data file
		//File datafile = new File("data1.txt");
		// scan.close();
		System.out.println("Enter the path file.");

		 File pathfile = new File(scan.nextLine()); // open path file

		//File pathfile = new File("data2.txt");
		System.out.println("Enter the output file.");

		File outfile = new File(scan.nextLine()); // open output file

		scan.close();
		PrintWriter outputFile = new PrintWriter(outfile);// open output file for output
		Graph graph = new Graph();
		if (datafile.exists()) {// check file exist
			Scanner inFile = new Scanner(datafile);
			while (inFile.hasNextLine()) {
				String data = inFile.nextLine();
				if (data.indexOf('|') == -1) // skip the first line since is linked list
					continue;
				parseData(graph, data);
			} // finish input data
			inFile.close();
		}

		int flightNum = 1;
		if (pathfile.exists()) {
			Scanner inFile = new Scanner(pathfile);
			
			//keep reading input
			while (inFile.hasNextLine()) {
				String path = inFile.nextLine();
				if (path.indexOf('|') == -1) { // skip the first line since is linked list
					continue;
				}
				String output = generatePath(graph, path, flightNum);
				outputFile.println(output);
				++flightNum;
			}
			inFile.close();
		}

		outputFile.close();
		exit(0);

	}

	private static String generatePath(Graph graph, String input, int flightNum) {
		// TODO Auto-generated method stub
		String cityS = "", cityD = "";
		boolean time = true;
		int begin = 0;
		int end = input.indexOf('|');

		//parse the String
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				cityS = input.substring(begin, end);
				break;
			case 1:
				cityD = input.substring(begin + 1, end);
				break;
			case 2:
				time = ((input.substring(begin + 1).compareTo("T")) == 0);
				break;
			}
			begin = end;
			end = input.indexOf('|', begin + 1);
		}
		
		// Node<City> citySNode = new Node<City>(new City(cityS, 0, 0));
		Node<City> cityDNode = new Node<City>(new City(cityD, 0, 0)); //create for destination 
		Node<LinkedList<City>> cityLLnode = graph.findCity(graph.path.head, new Node<City>(new City(cityS, 0, 0))); //find the city LL
		if((cityLLnode == null) || (graph.findCity(graph.path.head, cityDNode)== null))  // if any of the city cannot find in the graph there is no path
			return "Flight "+ flightNum+": The City Dose Not exist\n";
		
		LinkedList<City>[] path = new LinkedList[3];
		for (int i = 0; i < 3; i++) {
			path[i] = new LinkedList<City>(); //initialize 
		}
		Stack stack = new Stack();
		int[] costarray = new int[3];
		int[] timearray = new int[3];
		stack.push(new Node<City>(cityLLnode.payload.head.payload));

		findpath(graph, cityLLnode, cityDNode, time, path, stack, costarray, timearray);
		// return pathToString(path, flightNum, cityS, cityD,time);
		
		if (path[0].isEmpty()) // no shortest path, no path
			return "There is no path from " + cityS + " to " + cityD + ".\n";
		String out = "";
		out = "Flight " + flightNum + ": " + cityS + ", " + cityD;
		if (time)
			out += " (Time)\n";
		else
			out += " (Cost)\n";
		for (int i = 0; i < 3; i++) {
			out += pathToString(out, path, costarray, timearray, i);
			out += "\n";
		}

		return out;
	}

	private static String pathToString(String out, LinkedList<City>[] path, int[] costarray, int[] timearray, int i) {
		// TODO Auto-generated method stub
		if (path[i].isEmpty())
			return "";
		return  "Path " + (i+1) + ": " + toCityPathString(path[i].head) + ". Time: " + timearray[i] + " Cost: "
				+ costarray[i];
	}

	private static String toCityPathString(Node<City> head) {
		if (head.next == null)
			return head.toString();
		else
			return head.toString() + " -> " + toCityPathString(head.next);

	}

	private static void findpath(Graph graph, Node<LinkedList<City>> cityS, Node<City> cityD, boolean time,
			LinkedList<City>[] path, Stack stack, int[] costarray, int[] timearray) {
		// TODO Auto-generated method stub

		//stack.push(new Node<City>(cityS.payload.head.payload));
		if (cityD.compareToCity(cityS) == 0) // reach destination
		{
			popPath(stack, path, time, costarray, timearray);
			return;
		}

		// Node<LinkedList<City>> node = graph.findCity(graph.path.head, cityS); // get
		// the LL for that city

		// get neighbor
		Node<City> nodeCheck = cityS.payload.head;

		while (nodeCheck != null) {// have not visited all of the edge
			if (!stack.find(nodeCheck)) // if the neighbor is not in the stack
			{
				stack.push(new Node<City>(nodeCheck.payload));
				findpath(graph, graph.findCity(graph.path.head, nodeCheck),  //Recursive find path
						cityD, time, path, stack, costarray,timearray);
				stack.pop();
			}
			nodeCheck = nodeCheck.next;
		}

	}


	
	private static void popPath(Stack stack, LinkedList<City>[] path, boolean time, int[] costarray, int[] timearray) {
		// TODO Auto-generated method stub
		int data1, data2, data3, stackData;
		if (time) {
			data1 = getLLTime(path[0]);
			data2 = getLLTime(path[1]);
			data3 = getLLTime(path[2]);
			stackData = getLLTime(stack.LL);
		} else {
			data1 = getLLCost(path[0]);
			data2 = getLLCost(path[1]);
			data3 = getLLCost(path[2]);
			stackData = getLLCost(stack.LL);
		}

		int position = -1;

		if (stackData < data1)
			position = 1;
		else if (stackData < data2)
			position = 2;
		else if (stackData < data3)
			position = 3;

		switch (position) {
		case -1:
			return;
		case 1:
			switchPath(path[1], path[2]);
			costarray[2] = costarray[1];
			costarray[1] = costarray[0];
			timearray[2] = timearray[1];
			timearray[1] = timearray[0];
			switchPath(path[0], path[1]);
			// switchPath(path[1], path[2]);
			path[0].clear();
			insertPath(stack.LL.head, path[0]);
			costarray[0] = getLLCost(stack.LL);
			timearray[0] = getLLTime(stack.LL);
			break;
		case 2:
			switchPath(path[1], path[2]);
			path[1].clear();
			insertPath(stack.LL.head, path[1]);
			costarray[2] = costarray[1];
			
			costarray[1] = getLLCost(stack.LL);
			timearray[1] = getLLTime(stack.LL);
			break;
		case 3:

			path[2].clear();
			insertPath(stack.LL.head, path[2]);
			costarray[2] = getLLCost(stack.LL);
			timearray[2] = getLLTime(stack.LL);
		}

	}

	private static void insertPath(Node<City> node, LinkedList<City> linkedList) {
		// TODO Auto-generated method stub
		if (node == null)
			return;
		linkedList.addFirst(new Node<City>(node.payload));
		insertPath(node.next, linkedList);

	}

	static void switchPath(LinkedList LS, LinkedList LD) {
		Node node;
		if (LS.isEmpty())
			return;
		LD.clear();
		while ((node = LS.removeFirst()) != null)
			LD.addLast(node);
	}

	static int getLLTime(LinkedList<City> LL) {
		if (!LL.isEmpty()) {
			return getTime(LL.head); // last item is city itself
		}
		return Integer.MAX_VALUE;
	}

	static int getLLCost(LinkedList<City> LL) {
		if (!LL.isEmpty()) {
			return getCost(LL.head);
		}
		return Integer.MAX_VALUE;
	}

	static int getTime(Node<City> path) {
		if (path.next == null)
			return 0;
		return path.payload.time + getTime(path.next);
	}

	static int getCost(Node<City> path) {
		if (path.next == null)
			return 0;
		return path.payload.cost + getCost(path.next);
	}

	private static void parseData(Graph graph, String data) {
		// TODO Auto-generated method stub

		String citySString = "", cityDString = "";
		int cost = 0, time = 0;

		int begin = 0;
		int end = data.indexOf('|');

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				citySString = data.substring(begin, end);
				break;
			case 1:
				cityDString = data.substring(begin + 1, end);
				break;
			case 2:
				cost = Integer.parseInt(data.substring(begin + 1, end));
				break;
			case 3:
				time = Integer.parseInt(data.substring(begin + 1));
				break;
			}
			begin = end;
			end = data.indexOf('|', begin + 1);
		}
		City cityS = new City(citySString, cost, time), cityD = new City(cityDString, cost, time);

		graph.add(cityS, cityD, cost, time);
	}
}
