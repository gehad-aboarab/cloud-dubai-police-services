package DubaiPolice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Path("/dubai-police")
public class DubaiPoliceWebServices {
	private static final DatabaseHelper database = new DatabaseHelper("cloud-computing");
	
	@GET
	@Path("get-fines/{license-no}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFines(@PathParam("license-no") String license_no) {
		return database.getFines(license_no).toString();
	}
	
	@GET
	@Path("payment/{fine-no}/{license-no}/{credit-card}/{amount}")
	@Produces(MediaType.TEXT_PLAIN)
	public String payment(@PathParam("fine-no") String fine_no, 
			@PathParam("license-no") String license_no, 
			@PathParam("credit-card") String credit_card, 
			@PathParam("amount") double amount) {
		return database.payment(fine_no, license_no, credit_card, amount).toString();
	}
}
