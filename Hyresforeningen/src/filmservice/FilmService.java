package filmservice;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/FilmService")
public class FilmService {
   @GET
   @Path("/films")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Film> getFilms() {
	   List<Film> out = new ArrayList<Film>();
	   out.add(new Film("id0", "Modern Times", "En komedi-klassiker om arbetares kamp mot teknik och kapitalism."));
	   out.add(new Film("id1", "Metropolis", "En science fiction-klassiker om arbetares kamp mot teknik och kapitalism."));
	   return out;
   }
}
