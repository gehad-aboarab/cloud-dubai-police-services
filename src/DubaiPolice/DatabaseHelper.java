package DubaiPolice;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseHelper {
	private static MongoClient mongoClient;
	private static MongoDatabase database;

	private static String FINES_TABLE = "dubai-police-fine";

	public DatabaseHelper(String databaseName) {
		mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://Gehad:Aboarab97@cloud-computing-zqxty.mongodb.net/test?retryWrites=true"));
		database = mongoClient.getDatabase(databaseName);
	}

	// Get the fines that belong to that license plate number
	public static JSONObject getFines(String license_no) {
		MongoCollection<Document> collection = database.getCollection(FINES_TABLE);
		FindIterable<Document> documents = collection.find();

		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		try {
			for (Document document : documents) {
				if (document.getString("license-no").equals(license_no)) {
					document.remove("_id");
					jsonArray.put(document);
				}
			}
			result.put("result", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Get the fines that belong to that license plate number and date
//		public static JSONObject getFiness(JSONObject message) {
//			MongoCollection<Document> collection = database.getCollection(FINES_TABLE);
//			FindIterable<Document> documents = collection.find();
//
//			JSONObject result = new JSONObject();
//			JSONArray jsonArray = new JSONArray();
//
//			try {
//				for (Document document : documents) {
//					if (document.getString("license-no").equals(message.get("license-no")) && document.getString("date").equals(message.get("date"))) {
//						document.remove("_id");
//						jsonArray.put(document);
//					}
//				}
//				result.put("result", jsonArray);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return result;
//		}
	
	// Payment of a specific fine
	public static JSONObject payment(String fine_no, String license_no, String credit_card, double amount) {
		MongoCollection<Document> collection = database.getCollection(FINES_TABLE);
		
		// Delete fines from database
		if(fine_no.equals("all")) {
			FindIterable<Document> documents = collection.find();

			try {
				for (Document document : documents) {
					if (document.getString("license-no").equals(license_no)) {
						collection.deleteOne(document);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Bson filter = new Document("fine-no", fine_no);
			collection.deleteOne(filter);
		}

		try {
			JSONObject message = new JSONObject();
			message.put("confirmed", "true");
			return message;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new JSONObject();
	}

	// For testing
	public static void main(String[] args) {
		mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://Gehad:Aboarab97@cloud-computing-zqxty.mongodb.net/test?retryWrites=true"));
		database = mongoClient.getDatabase("cloud-computing");
		
//		JSONObject obj = new JSONObject();
//		obj.put("license-no", "L11978");
//		obj.put("date", "12/1/2017");
//		System.out.println(getFines("L11978"));
//		System.out.println(payment("all","L11","dsds",2434));
	}
}
