# lab2Proj
# Homework3
CS 46B Fall 2017 Homework 3
For this assignment you will create a class called Movie and observe its behavior when it is managed by different kinds of “Film Archive” collections.
Information that is underlined and italicized has a high probability of appearing on your next exam.
Due date
This assignment is due on or before 11:59 PM on Tuesday September 26, 2017. Late work will not be accepted.
Getting started
Create a new workspace directory, or use an existing one. Create a Java project
   
called hw3proj in the workspace. Create a package called movies in the project. All classes and interfaces that you create should go in the movies package.
Class movie
Create a public class Movie with private instance variables String title and int year. The class should declare that it implements the Comparable<Movie> interface, and should provide the following:
• A constructor that takes 2 arguments: a String and an int (in that order) for initializing title and year.
• A method that satisfies the Comparable<Movie> interface. Movies should be compared first by title and then by year.
The Maltese Falcon The Thomas Crown Affair The Thomas Crown Affair 1941 1968 1999
• Methods getTitle() and getYear() that do the right thing.
• An equals() method that is compatible with the method that satisfies the
Comparable<Movie> interface.
• A toString() method that returns “Movie” followed by 1 space followed
by the title followed by 1 space followed by open-parenthesis followed by the year followed by close-parenthesis. Example:
Movie The Maltese Falcon (1941)
   
• A public static method getTestMovies(), which returns an array of 10 unique Movie instances. They don’t have to be real movies – it’s ok to make them up. The 0th and 1st array elements must be 2 movies with the same title but from different years (e.g. The Thomas Crown Affair 1968 and The Thomas Crown Affair 1999, or True Grit 1969 and True Grit 2010). The 2nd and 3rd elements (counting from 0) must be 2 movies with different titles but from the same year (e.g. The Martian 2015 and Bridge of Spies 2015). The 4th and 5th elements must be 2 different objects that represent the same movie (same title and same year). The remaining elements can be any movies you like.
• A hashCode() method. Use the following:
public int hashCode() {
return title.hashCode() + year; }
Interface FilmArchive
Create an interface called FilmArchive that defines 2 methods:
• getSorted(), which takes no args and returns ArrayList<Movie>.
Implementations should return an array list whose members are unique according to deep equality, and sorted according to the criteria in Movie’s compareTo() method.
• add(), which takes one arg of type Movie and returns a boolean. If add() is called where the arg already appears in the film archive, the method should return false and otherwise do nothing; if the arg of add() does not yet appear in the archive, it should be added as described below and the method should return true.
3 implementing classes
Create 3 classes, named ListFilmArchive, HashFilmArchive, and TreeFilmArchive, that implement the FilmArchive interface.
Class ListFilmArchive
This class should extend ArrayList<Movie>. In your add() method, check every movie in the array list for deep equality to the arg of add(). If you find a movie that “equals()” the arg, just return false; if you don’t find one, add the arg to the array list and return true.
Hint: you are overriding the add() method inherited from the ArrayList superclass. When you detect that the arg movie doesn’t appear in the array list, you want to call the superclass’ version of add(). To do that, use something like this line:
boolean reallyAdded = super.add(aMovie);
“super.” in this overridden method tells Java “please use the version of add in my superclass”.
For the getSorted() method, use a TreeSet<Movie> to do the sorting. First construct a TreeSet<Movie>, passing the ArrayList into the TreeSet constructor; since ListFilmArchive “isa” ArrayList<Movie>, “this” is a reference to the ArrayList that you want to pass. Then construct and return a new ArrayList<Movie>, passing the TreeSet into the ArrayList constructor; when that ArrayList is constructed, the movies in the TreeSet will be added to it one by one, in the TreeSet’s natural order, which is the order that you want. In other words, this long paragraph describes 2 simple lines of code.
Class HashFilmArchive
This class should extend HashSet<Movie>. It’s ok to add movies to a HashSet because Movie has mutually compatible equals(), and hashCode() methods.
For the add() method, first read the documentation for add() in the java.util.HashSet API page (https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html ). Convince yourself that this method does exactly what add() in HashFilmArchive should do. Since the inherited method is acceptable, you don’t need to create add() in HashFilmArchive. (But you do need to understand why you don’t need to).
 
For the getSorted() method, do something similar to what you did in ListFilmArchive.
Class TreeFilmArchive
This class should extend TreeSet<Movie>.
For the add() method, first read the documentation for add() in the API page for java.util.TreeSet (https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html ). Convince yourself that this method does exactly what add() in TreeFilmArchive should do. Since the inherited method is acceptable, you don’t need to create add() in TreeFilmArchive.
For the getSorted() method, do something somewhat similar to, but simpler than, what you did in ListFilmArchive and HashFilmArchive. All you need to do is construct an ArrayList<Movie>, passing “this” into the ArrayList ctor. Well, that’s not all you need to do ... you also need to understand why it works.
Testing your 3 FilmArchive classes
To test your classes, you will do what professional programmers do in the real world: Create test inputs, think about what outputs those inputs will cause, run the code, look at the output, and see if your expectations were met. If they were met, that’s great. If not, either your expectations or your implementation was wrong. Figure out where you are wrong, and correct your expectations or your code.
You already have test inputs: the array returned by Movie.getTestMovies(). Be sure that this array meets the requirements described above.
To test the ListFilmArchive class, add the following main() method:
    
public static void main(String[] args) {
ListFilmArchive archive = new ListFilmArchive(); for (Movie m: Movie.getTestMovies())
archive.add(m); for (Movie m: archive)
System.out.println(m); System.out.println("**************"); for (Movie m: archive.getSorted())
System.out.println(m);
}
If you get a compiler error on the line that prints out a row of stars, delete the quote signs and then type them back in. Some systems do weird things with smart quotes. Stupid smart quotes!
Run ListFilmArchive as an app. Look at the number of movies that were printed out, and their order. Are these what you expected?
To test HashFilmArchive, add the same main() to HashFilmArchive but change “ListFilmArchive” to “HashFilmArchive”. Then add the HashFilmArchive main() to TreeFilmArchive but change “HashFilmArchive” to “TreeFilmArchive”. This is a common way to test individual classes in Java: you give each class a main() method that tests just that class.
Let’s do science
So far in this class you have done programming. It’s time to do science. Computer science, like all science, includes making observations and looking for patterns. An entire branch of computer science involves determining how slow or fast an algorithm will be. A novel algorithm, even if it’s brilliant, isn’t worth very much if it won't complete execution on practical data during your lifetime. It isn’t enough for software to be correct; it also has to be efficient. Expect a question on that last sentence on your next midterm.
In lecture you learned that inserting into a HashSet is extremely fast, while similar functionality based on an ArrayList is increasingly slow as the list expands. It’s time to experience this for yourself.
Create the following class:
package movies;
public class HashAnalyzer {
public static void main(String[] args) {
HashFilmArchive archive = new HashFilmArchive(); for (int i=0; i<100000; i++)
{
archive.add(new Movie("Movie" + i, 2017)); if (i%1000 == 0)
System.out.println(i);
}
System.out.println("DONE"); }
}
The main() method creates 100,000 fake movies and puts them into a HashFilmArchive. Every 1000 insertions, it prints out a message. When you run the app, the time interval between messages will give you feedback about time performance of the add() method of HashFilmArchive. Now run the app. Do you notice anything about the interval between messages? Is it long or short? Does it increase as the archive grows?
Create a second analyzer class called ListAnalyzer. It should be just like HashAnalyzer, except it should use a ListFilmArchive. Run the app. Now what do you notice about the interval between messages.
Comments
Every class and interface should start with a descriptive comment. Every method whose body is longer than 1 line should have a comment before the method declaration. In this assignment the constructors are trivial, so they
don’t need comments. Non-trivial methods (like compareTo or getTestMovies in the Movie class) might need internal comments.
A Man forComments should be accurate and helpful.
Submission
As usual, export your project as a JAR file (not an Executable JAR file) and upload to Canvas. Be sure your jar contains all your sources.
You may only submit once.
You might be able to manually test your submission exactly the graders do it. You don’t have to do this, but you might find it interesting. Caution: if these instructions don’t work for you, there are lots of possible causes. Don’t ask your professor or graders to make it work. You can probably get all the help you need from the Computer Club.
Many thanks to Bharath for putting this together...
Instructions for unzipping and running a jar file via Terminal in Mac
1) Let’s make a new folder (HW1) with the jar file that need to be extracted in it. Lets say it is under JohnDoe/Documents/HW1/hw1.jar
2) Navigate to that folder via the terminal. Most of the time when you launch a new terminal window, you will be in your User folder. In order to see the structure of the folder, type in ls. This command lists all the files and directories. You should see Documents, Desktop etc listed.
3) Navigate to the folder where you placed your new folder with the jar. For this we use the cd Documents/HW1 command. This command changes the current directory to the new path specified, in this case Documents/HW1
4) Now it's time to unzip the jar file. We use the command jar xf hw1.jar to unzip it. The jar xf command instructs it extract the file (x) and that we are providing the name of the file (f).
 
5) Now if you go to that folder or type in ls, you will see that new folders/files exist. These are the extracted files.
6) Navigate to the folder with your .java files. This usually has the same name as your package. For instance in hw1, planets will be your package name and that will be the name of you extracted folder as well. Type cd [package-name] inorder to move to this directory.
7) If you type ls, you will see all your .java files now. Now inorder to compile all these files use javac *.java. The asterisk represents a wildcard character and tells java to compile all the files that have an extension of .java.
8) Type in ls and you will see new files with an extension .class . These are the compiled files.
9) Finally, to run the program return to your new folder. To do this, you can type cd .. which takes you back to the previous directory. Now type in java [package- name].[class-name]. For example, in HW1, Homework1Grader has the main method. So we type in java planets.Homework1Grader.
10) You will be able to see you output on the terminal.
Instructions for unzipping and running a jar file via Command Prompt (CMD) in Windows
1. Let’s make a new folder (HW1) with the jar file that need to be extracted in it. Lets say it is under JohnDoe\Documents\HW1\hw1.jar
2. Navigate to that folder via the CMD. Most of the time when you launch a new CMD window, you will be in your User folder. Inorder to see the structure of the folder, type in dir. This command lists all the files and directories. You should see Documents, Desktop etc listed.
3. Navigate to the folder where you placed your new folder with the jar. For this we use the cd Documents\HW1 command. This command changes the current directory to the new path specified, in this case Documents\HW1
4. Now it's time to unzip the jar file. We use the command jar xf hw1.jar to unzip it. The jar xf command instructs it extract the file (x) and that we are providing the name of the file (f).
5. Now if you go to that folder or type in ls, you will see that new folders/files exist. These are the extracted files.
6. Navigate to the folder with your .java files. This usually has the same name as your package. For instance in hw1, planets will be your package name and that will be the name of your extracted folder as well. Type cd [package-name] in order to move to this directory.
7. If you type ls, you will see all your .java files now. Now in order to compile all these files use javac *.java. The asterisk represents a wildcard character and tells java to compile all the files that have an extension of .java.
8. Type in dir and you will see new files with an extension .class . These are the compiled files.
9. Finally, to run the program return to your new folder. To do this, you can type cd .. which takes you back to the previous directory. Now type in java [package- name].[class-name]. For example, in HW1, Homework1Grader has the main method. So we type in java planets.Homework1Grader.
10. You will be able to see you output on the terminal.
