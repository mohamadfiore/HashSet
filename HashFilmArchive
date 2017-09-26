package movies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import movies.FilmArchive;
import movies.Movie;

public class HashFilmArchive extends HashSet<Movie> implements FilmArchive {
	
	public ArrayList<Movie> getSorted() {
		// For the getSorted() method, do something similar to what you did in
		// ListFilmArchive.
		HashSet<Movie> hashSet = new HashSet<Movie>(this);
		ArrayList<Movie> newArrayListMovies = new ArrayList<Movie>(hashSet);
		return newArrayListMovies;
	}

	public static void main(String[] args) {
		HashFilmArchive archive = new HashFilmArchive();
		for (Movie m : Movie.getTestMovies())
			archive.add(m);
		for (Movie m : archive)
			System.out.println(m);
	}
}
