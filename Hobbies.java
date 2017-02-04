
public enum Hobbies {
MUSIC("music,concert,guitar"),
ART("art,brush,craft,paint"),
READER("book,library"),
MOVIE_BUFF("movie,cinema,theatre"),
FOODIE("pizza,burger,restaurant,pasta,noodles,coffee,tea,food"),
TRAVEL("uber,lyft,transportation"),
SPORTS("football,skating,cricket,baseball,soccer,bowling"),
STUDENT("student,course"),
VIDEOGAME("playstation,xbox,nintendo,wii");
private String keywords;
Hobbies( String keywords ){
	    this.keywords = keywords;
	  }
public String getKeywords(){
	    return this.keywords;
	  }

}
