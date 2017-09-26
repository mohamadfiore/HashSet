package movies;

import java.util.ArrayList;
import java.util.TreeSet;

import movies.FilmArchive;
import movies.Movie;

public class ListFilmArchive extends ArrayList<Movie> implements FilmArchive {
	/*
	 * In your add() method, check every movie in the array list for deep
	 * equality to the arg of add(). If you find a movie that “equals()” the
	 * arg, just return false; if you don’t find one, add the arg to the array
	 * list and return true.
	 */
	public boolean add(Movie that) {
		for (Movie temp : this) {
			if (temp.getYear() == that.getYear() && temp.getTitle().equals(that.getTitle())) {
				return false;
			}
		}
		boolean reallyAdded = super.add(that);
		return reallyAdded;
	}

	public ArrayList<Movie> getSorted() {
		// First construct a TreeSet<Movie> pass the ArrayList to TreeSet
		// constructor
		TreeSet<Movie> tree = new TreeSet<Movie>(this);
		// Then construct and return a new ArrayList<Movie>, passing the TreeSet
		// into the ArrayList constructor;
		ArrayList<Movie> newArrayListMovies = new ArrayList<Movie>(tree);
		return newArrayListMovies;
	}

	public static void main(String[] args) {
		ListFilmArchive archive = new ListFilmArchive();
		for (Movie m : Movie.getTestMovies())
			archive.add(m);
		for (Movie m : archive)
			System.out.println(m);
	}
}
