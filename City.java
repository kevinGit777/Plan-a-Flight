package project3;

public class City {

	String name;
	int cost;
	int time;

	public City() {
	};

	public City(String name, int cost, int time) {
		this.name = name;
		this.cost = cost;
		this.time = time;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	public int compareTo(City city) {
		// TODO Auto-generated method stub
		return name.compareTo(city.name);
	}

}
