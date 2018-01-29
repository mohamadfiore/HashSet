package movies;

import java.util.ArrayList;

import movies.Movie;

public interface FilmArchive {
	public ArrayList<Movie> getSorted();

	public boolean add(Movie m);
}
