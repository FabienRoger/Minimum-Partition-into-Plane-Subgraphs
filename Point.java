class Point{
	double x,y;

	Point(){
		x = 0;
		y = 0;
	}
	
	Point(double px, double py){
		x = px;
		y = py;
	}
	
	@Override
	public String toString(){
		return "(" + Double.toString(x) + "," + Double.toString(y) + ")";
	}

	@Override
	public int hashCode(){
		return Double.hashCode(x*y + x + y);
	}
}
