package movies;

import movies.HashFilmArchive;
import movies.Movie;

public class HashAnalyzer {
	public static void main(String[] args) {
		HashFilmArchive archive = new HashFilmArchive();
		for (int i = 0; i < 100000; i++) {
			archive.add(new Movie("Movie" + i, 2017));
			if (i % 1000 == 0)
				System.out.println(i);
		}
	}
}
