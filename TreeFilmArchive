package movies;

import java.util.TreeSet;

import java.util.ArrayList;

import movies.FilmArchive;

import movies.Movie;

public class TreeFilmArchive extends TreeSet<Movie> implements FilmArchive {

	public ArrayList<Movie> getSorted() {

		ArrayList<Movie> movies = new ArrayList<Movie>(this);

		return movies;

	}

	public static void main(String[] args) {

		TreeFilmArchive archive = new TreeFilmArchive();

		for (Movie m : Movie.getTestMovies())

			archive.add(m);

		for (Movie m : archive)

			System.out.println(m);

	}

}
