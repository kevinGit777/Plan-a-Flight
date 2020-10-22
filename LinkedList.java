package project3;

public class LinkedList<E> {

	Node<E> head;
	Node<E> tail;
	public int length;

	public LinkedList() {
		head = null;
		tail = head;
		length = 0;
	}

	public LinkedList(Node<E> node) {
		head = node;
		tail = node;
		length = 1;
	}

	public Node<E> getHead() {
		return head;
	}

	public Node<E> traversal(int index) {
		Node<E> node = head;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}

		return node;
	}

	public Node<E> delete(int index) {

		Node<E> front = traversal(index - 1);
		Node<E> node = front.next;
		front.next = node.next; // nothing point to node anymore
		--length;
		return node;

	}

	public Node<E> find(Node head, Node node) {
		if (isEmpty())
			return null;
		if (head == null) // reach the end
			return null;
		if (head.compareTo(node) == 0)
			return head;

		return find(head.next, node);
	}

	public Node<E> addLast(Node<E> node) {

		if (isEmpty()) { // case that it is empty LL
			head = node; // set head point to node
			tail = node;
		} else {// not empty
			tail.next = node; // just add node at the end
			tail = node; // update tail
		}
		++length;
		return tail;
	}

	public void insert(int index, Node<E> node) {
		// insert at the indexx place
		Node<E> front = traversal(index - 1);
		node.next = front.next;
		front.next = node;
		++length;
	}

	public String toString() {

		return head.toString(); // print the node and make the argument LL short by 1

	}

	public Node addFirst(Node node) {
		// TODO Auto-generated method stub
		node.next = head;
		head = node;
		if (isEmpty())
			tail = head;
		++length;
		return head;
	}

	public Node removeFirst() {
		// TODO Auto-generated method stub
		if (!isEmpty()) {
			Node node = head;
			head = head.next;
			--length;
			return node;
		}
		return null;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (length == 0);
	}

	public Node getFirst() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;
		return head;
	}

	public int compareTo(LinkedList o) {
		// TODO Auto-generated method stub
		return head.compareTo(o.head);
	}

	public void clear() {
		head = null;
		tail = head;
		length = 0;
	}

}
