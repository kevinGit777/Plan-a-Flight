package project3;

public class Node<E> {

	Node<E> next;
	E payload;

	public Node() {
	}

	public Node(E value) {
		this.payload = value;
	}

	public Node<E> getNext() {
		return next;
	}

	public E getPayload() {
		return payload;
	}

	public String toString() {
		if (payload != null)
			return payload.toString();
		return "";// if nothing in the payload return empty
	}

	public int compareToCity(Node<LinkedList<City>> node) {
		// compare function of Node
		return (payload.toString()).compareTo(node.payload.toString());
	}

	public int compareTo(Node<City> node) {
		// compare function of Node
		return (payload.toString()).compareTo(node.payload.toString());
	}

}
