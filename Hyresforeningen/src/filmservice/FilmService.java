package filmservice;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/ApartmentService")
public class FilmService {
	DBUtility dBUtility = new DBUtility();
   @GET
   @Path("/apartments")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Film> getFilms() throws Exception {
	   List<Film> out = new ArrayList<Film>();
	   out.add(new Film("id0", "Modern Times", "En komedi-klassiker om arbetares kamp mot teknik och kapitalism."));
	   out.add(new Film("id1", "Metropolis", "En science fiction-klassiker om arbetares kamp mot teknik och kapitalism."));
	   return out;
	   //System.out.println("hej");
	   //List<Apartment> Apartments = dBUtility.getAllApartments();
	   //System.out.println(Apartments);
	   //return Apartments;
	   
   }
}

// Det funkar nu , måste få det att fungera med Aparments