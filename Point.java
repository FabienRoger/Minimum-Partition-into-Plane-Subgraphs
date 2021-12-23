class Point {
	int x, y;

	Point(int px, int py) {
		x = px;
		y = py;
	}

	@Override
	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ")";
	}

	@Override
	public int hashCode() {
		return Double.hashCode(x * y + x + y);
	}

	boolean equal(Point o) {
		return this.x == o.x && this.y == o.y;
	}
}
