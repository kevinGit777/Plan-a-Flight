package project3;

public class Stack {

	LinkedList LL;

	public Stack() {
		LL = new LinkedList();
	};

	public Stack(Node node) {
		LL = new LinkedList();
		LL.addFirst(node);
	}

	public Node push(Node node) {
		LL.addFirst(node);
		return node;
	}

	public Node pop() {
		return LL.removeFirst();
	}

	public Node peek() {
		if (!LL.isEmpty()) {
			return LL.getFirst();
		}
		return null;
	};

	boolean find(Node node) // node should be <city>
	{
		return (LL.find(LL.head, node) != null); // if found, return true
	}

}
