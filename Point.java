class Point {
	int x, y;

	Point(int px, int py) {
		x = px;
		y = py;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		return x * y + x + y;
	}

	boolean equal(Point o) {
		return this.x == o.x && this.y == o.y;
	}

	double dist(Point o) {
		double dx = (this.x - o.x);
		double dy = (this.y - o.y);
		return Math.sqrt(dx * dx + dy * dy);
	}
}
