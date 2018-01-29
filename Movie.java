package movies;

public class Movie implements Comparable<Movie> {
	private String title;
	private int year;

	// A constructor that takes 2 arguments: a String and an int (in that order)
	// for initializing title and year.
	public Movie(String title, int year) {
		this.title = title;
		this.year = year;
	}

	// Methods getTitle() and getYear() that do the right thing.
	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	// An equals() method that is compatible with the method that satisfies the
	// Comparable<Movie> interface.
	public boolean equals(Object x) {
		if (x instanceof Movie) {
			Movie that = (Movie) x;
			return this.compareTo(that) == 0;
		} else
			return false;
	}

	// A method that satisfies the Comparable<Movie> interface. Movies should be
	// compared first by title and then by year.
	public int compareTo(Movie that) {

		// this is the best way to compare strings
		int titleCompareInteger = this.title.compareTo(that.title);
		/*
		 * Test System.out.println("that.tile is: " + that.title +
		 * "that.year is: " + that.year); System.out.println("this.tile is :" +
		 * this.title + "this.year is: " + this.year);
		 * System.out.println(titleCompareInteger);
		 */

		if (titleCompareInteger != 0) {
			return titleCompareInteger;
		} else if (titleCompareInteger == 0 && this.year == that.year) {
			return titleCompareInteger;
		}

		else if (this.year < that.year) {
			return -1;

		} else {
			return 1;
		}
	}

	public String toString() {
		return "Movie " + title + " (" + year + ")";
	}

	public static Movie[] getTestMovies() {
		Movie[] movieArray = new Movie[10];
		movieArray[0] = new Movie("Aladdin", 1968);
		movieArray[1] = new Movie("Aladdin", 1956);
		movieArray[2] = new Movie("Mr.Penguin", 1977);
		movieArray[3] = new Movie("Fremont", 1977);
		movieArray[4] = new Movie("The Parent Trap", 1998);
		movieArray[5] = new Movie("The Parent Trap", 1998);
		movieArray[6] = new Movie("The Intern", 2015);
		movieArray[7] = new Movie("Tangled", 2013);
		movieArray[8] = new Movie("Up", 2008);
		movieArray[9] = new Movie("Pride & Prejudice", 2003);
		return movieArray;
	}

	public int hashCode() {
		return title.hashCode() + year;
	}
}
