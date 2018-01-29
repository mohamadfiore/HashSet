package movies;

import java.lang.reflect.*;
import java.util.*;

import javax.xml.ws.handler.MessageContext.Scope;


public class FilmArchiveGrader 
{
	private final static Class<?>[]			EMPTY_TYPE_LIST 		= { };
	private final static Class<?>[]			STRING_INT_TYPE_LIST 	= { String.class, int.class };
	
	private       static Class<?>[]			MOVIE_TYPE_LIST;
	private       static Class<?>			filmArchiveClass;
	
	
	static
	{
		try
		{
			MOVIE_TYPE_LIST = new Class<?>[] { Class.forName("movies.Movie")  };
		}
		catch (ClassNotFoundException x)
		{
			sop("No movies.Movie class");
			System.exit(1);
		}
	}
	
	
	private static Map<String, Integer>		reasonToDeductions;
	
	
	// Aborts on 1st error.
	private static void checkStructure()
	{
		checkFilmArchiveStructure();
		checkMovieStructure();
		checkImplementorsStructure();
	}
	
	
	private static void abortZeroPoints(String msg)
	{
		sop(msg + "\nZero points");
		System.exit(1);
	}
	
	
	private static void checkFilmArchiveStructure()
	{
		try
		{
			filmArchiveClass = Class.forName("movies.FilmArchive");		// exists?
		}
		catch (ClassNotFoundException x)
		{
			abortZeroPoints("No movies.FilmArchive interface");
		}
		assert filmArchiveClass != null;
		if (!filmArchiveClass.isInterface())							// is an interface?
		{
			abortZeroPoints("movies.FilmArchive isn't an interface");
		}
		try
		{
			filmArchiveClass.getDeclaredMethod("getSorted", EMPTY_TYPE_LIST);	// getSorted() ok?
		}
		catch (NoSuchMethodException x)
		{
			abortZeroPoints("No getSorted() method in FilmArchive");
		}
		try
		{
			Method addMeth = filmArchiveClass.getDeclaredMethod("add", MOVIE_TYPE_LIST);	// add() ok?
			if (addMeth.getReturnType() != boolean.class)
				abortZeroPoints("add() in FilmArchive has wrong return type, should be boolean");
		}
		catch (NoSuchMethodException x)
		{
			sop("No add(Movie) method in FilmArchive");
			System.exit(1);
		}
	}
	
	
	// Static initializer already verified class exists.
	private static void checkMovieStructure()
	{
		Class<?> movieClass = MOVIE_TYPE_LIST[0];
		assert movieClass != null;
		
		try
		{
			movieClass.getConstructor(STRING_INT_TYPE_LIST);
		}
		catch (NoSuchMethodException x)
		{
			abortZeroPoints("No Movie(String, int) constructor in Movie");
		}
		
		boolean implementsOk = false;
		for (Class c: movieClass.getInterfaces())
		{
			if (c.equals(Comparable.class))
			{
				implementsOk = true;
				break;
			}
		}
		if (!implementsOk)															// implements Comparable?
			abortZeroPoints("Movies class doesn't declare that it implements Comparable");
		
		try
		{
			Method cmpMeth = movieClass.getDeclaredMethod("compareTo", MOVIE_TYPE_LIST);	// compareTo()
			if (cmpMeth.getReturnType() != int.class)
				abortZeroPoints("compareTo() in Movie has wrong return type, should be int");
		}
		catch (NoSuchMethodException x)
		{
			sop("No compareTo(Movie) method in Movie");
			System.exit(1);
		}
		
		try																			// getTestMovies()
		{
			Method getTestMoviesMeth = movieClass.getDeclaredMethod("getTestMovies", EMPTY_TYPE_LIST);
			int mods = getTestMoviesMeth.getModifiers();
			if ((mods & Modifier.STATIC) == 0)
				abortZeroPoints("getTestMovies() in Movie isn't static");
		}
		catch (NoSuchMethodException x)
		{
			sop("No getTestMovies() method in Movie");
			System.exit(1);
		}
		
		try																			// getTitle()
		{
			Method getTitleMeth = movieClass.getDeclaredMethod("getTitle", EMPTY_TYPE_LIST);
			if (getTitleMeth.getReturnType() != String.class)
				abortZeroPoints("In Movie, getTitle() should return a String");
		}
		catch (NoSuchMethodException x)
		{
			sop("No getTitle() method in Movie");
			System.exit(1);
		}
		
		try																			// getYear()
		{
			Method getYearMeth = movieClass.getDeclaredMethod("getYear", EMPTY_TYPE_LIST);
			if (getYearMeth.getReturnType() != int.class)
				abortZeroPoints("In Movie, getTitle() should return an int");
		}
		catch (NoSuchMethodException x)
		{
			sop("No getYear() method in Movie");
			System.exit(1);
		}
	}
	
	
	private static void checkImplementorsStructure()
	{
		String[] classNames = { "ListFilmArchive", "HashFilmArchive", "TreeFilmArchive" };
		for (String className: classNames)
		{
			try
			{
				Class<?> clazz = Class.forName("movies." + className);			// exists?
				boolean implementsOk = false;
				for (Class ifclass: clazz.getInterfaces())
				{
					if (ifclass == filmArchiveClass)
					{
						implementsOk = true;
						break;
					}
				}
				if (!implementsOk)												// implements Comparable?
					abortZeroPoints(className + " class doesn't declare that it implements FilmArchive");
				int mods = clazz.getModifiers();
				if ((mods & Modifier.ABSTRACT) != 0)
					abortZeroPoints(className + " should not be abstract");
			}
			catch (ClassNotFoundException x)
			{
				abortZeroPoints("No class " + className);
			}
		}
	}
	
	
	private static void checkFunctionality()
	{
		checkMovieFunctionality();
	}
	
	
	private static String movieToString(Movie m)
	{
		return '"' + m.getTitle() + "\" " + m.getYear();
	}
	
	
	private static void checkMovieFunctionality()
	{
		Movie falcon1 = new Movie("The Maltese Falcon", 1941);
		Movie falcon2 = new Movie("The Maltese Falcon", 1941);
		Movie tc1 = new Movie("The Thomas Crown Affair", 1968);
		Movie tc2 = new Movie("The Thomas Crown Affair", 1999);
		Movie[] movies = { falcon1, falcon2, tc1, tc2 };
		
		// Check hash codes.
		for (Movie m: movies)
		{
			int goldenHashCode = m.getTitle().hashCode() +  m.getYear();
			if (m.hashCode() != goldenHashCode)
			{
				String err = "Bad hashCode for " + movieToString(m) + ": expected " + goldenHashCode +
						", saw " + m.hashCode();
				reasonToDeductions.put(err, 15);
				break;
			}
		}
		
		// Comparison.
		Movie fclub = new Movie("Fight Club", 1999);
		Movie[] these = { falcon1, falcon1, falcon1, tc1, tc2, fclub };
		Movie[] those = { falcon1, falcon2, tc1,     tc2, tc1, tc2 };
		int[] expects = { 0, 0, -1, -1, 1, -1 };
		assert these.length == those.length;
		assert those.length == expects.length;
		for (int i=0; i<these.length; i++)
		{
			int cmp = these[i].compareTo(those[i]);
			if (Math.signum(cmp) != expects[i])
			{
				String s = "In Movie, compareTo returned " + cmp;
				s += " when comparing {" + movieToString(these[i]) + "} to {" + movieToString(those[i]) + "}";
				s += " ... expected ";
				if (expects[i] == 0)
					s += "0";
				else if (expects[i] < 0)
					s += "negative";
				else
					s += "positive";
				reasonToDeductions.put(s, 25);
				break;
			}
		}
		
		// Test movies.
		Movie[] testMovies = Movie.getTestMovies();
		if (testMovies == null)
		{
			reasonToDeductions.put("Movie.getTestMovies() returned null", 25);
			return;
		}
		if (testMovies.length != 10)
		{
			String s = "Movie.getTestMovies() returned an array of length " + testMovies.length +
					", expected 10";
			if (testMovies.length < 6)
			{
				reasonToDeductions.put(s, 25);
				return;
			}
			else
				reasonToDeductions.put(s, 15);
		}
		assert testMovies.length == 10;
		for (int i=0; i<10; i++)
		{
			if (testMovies[i] == null)
			{
				reasonToDeductions.put("testMovies[" + i + "] is null", 15);
				break;
			}
		}
		Movie m0 = testMovies[0];
		Movie m1 = testMovies[1];
		if (!m0.getTitle().equals(m1.getTitle()))
			reasonToDeductions.put("Items 0 and 1 in test movies array don't have same title", 10);
		if (m0.getYear() == m1.getYear())
			reasonToDeductions.put("Items 0 and 1 in test movies array don't have different years", 10);
		Movie m2 = testMovies[2];
		Movie m3 = testMovies[3];
		if (m2.getTitle().equals(m3.getTitle()))
			reasonToDeductions.put("Items 2 and 3 in test movies array don't have different titles", 10);
		if (m2.getYear() != m3.getYear())
			reasonToDeductions.put("Items 2 and 3 in test movies array don't have same years", 10);
	}
	
	
	private static void checkAnalyzers()
	{
		String[] names = { "HashAnalyzer", "ListAnalyzer" };
		for (String name: names)
		{
			try
			{
				Class<?> clazz = Class.forName("movies." + name);
			}
			catch (ClassNotFoundException x)
			{
				reasonToDeductions.put("No " + name + " class", 25);
			}
		}
	}
	
	
	private static void printScore()
	{
		int totalDeductions = 0;
		for (Integer ded: reasonToDeductions.values())
			totalDeductions += ded;
		totalDeductions = Math.min(totalDeductions, 100);
		if (totalDeductions == 0)
			sop("Graderbot deducted zero points");
		else
		{
			sop("Graderbot deductions: ");
			for (String reason: reasonToDeductions.keySet())
				sop("  -" + reasonToDeductions.get(reason) + ": " + reason);
			sop("Total graderbot deductions = " + totalDeductions);
		}
	}
	
	
	static void sop(Object x)
	{
		System.out.println(x);
	}
	
	
	public static void main(String[] args)
	{
		sop("START");
		
		// Any structure errors => immediate failure.
		checkStructure();
		
		// Functional errors cause deductions.
		reasonToDeductions = new LinkedHashMap<>();
		checkFunctionality();
		
		// Check analyzers. Submission can pass if these have wrong structure.
		checkAnalyzers();
		
		printScore();
	}
}
