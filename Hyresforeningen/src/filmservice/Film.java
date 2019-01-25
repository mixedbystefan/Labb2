package filmservice;

public class Film {
	
	private String id;
	private String title;
	private String description;
	public Film(String idString, String titleString, String descriptionString) {
		id = idString;
		title = titleString;
		description = descriptionString;
	}	
	public String getId() {return id;}
	public String getTitle() {return title;}
	public String getDescription() {return description;}
	@Override
	public String toString() {return id + " " + title + "\n" + description;}
	
//	@Override
//	public boolean equals(Object object)
//	{
//		if(object == null){return false;}
//		else if(!(object instanceof Film)){return false;}
//		else { 
//			Film film = (Film)object;
//			if(id == film.getId() && title.equals(film.getTitle()) && description.equals(film.getDescription()))
//			{return true;}
//			}
//      return false;
//   }   
	}
