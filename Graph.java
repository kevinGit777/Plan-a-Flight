package project3;

public class Graph {
	LinkedList<LinkedList<City>> path;

	public Graph() {
		path = new LinkedList<LinkedList<City>>();
	}

	void add(City cityS, City cityD, int cost, int time) {

		Node<City> nodeS = new Node<City>(cityS);
		Node<City> nodeD = new Node<City>(cityD);
		Node<LinkedList<City>> citySNode = new Node<LinkedList<City>>(new LinkedList<>(new Node<City>(cityS)));
		Node<LinkedList<City>> cityDNode = new Node<LinkedList<City>>(new LinkedList<>(new Node<City>(cityD)));

		cityDNode = addCity(cityDNode);
		citySNode = addCity(citySNode);
		addPath(cityDNode, nodeS);
		addPath(citySNode, nodeD);
	}

	Node<LinkedList<City>> addCity(Node node) {
		Node<LinkedList<City>> head = findCity(path.head, node);
		if (head == null) // can't find city then add
			return path.addLast(node);

		return head;
	}

	void addPath(Node<LinkedList<City>> city, Node connect) {
		city.payload.addLast(connect);
	}

	Node<LinkedList<City>> findCity(Node head, Node node) {
		if (head == null || path.length == 0)
			return null;
		if (node.compareToCity(head) == 0)
			return head;
		return findCity(head.next, node);

	}

	Node findPath(Node<LinkedList<City>> city, Node connect) {
		return city.payload.find(city, connect);
	}

}
