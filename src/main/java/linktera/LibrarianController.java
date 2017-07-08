package linktera;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

@Controller
public class LibrarianController {

	
	@RequestMapping(value = "/newlibrarian", method = RequestMethod.GET)
    public String newLibrarianPage(HttpSession session) {
		
		String type = (String) session.getAttribute("type");
		if (type.equals("admin")) {
			return "librarian/new";
		} else {
			return "home";
		}
    }
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/newlibrarian", method = RequestMethod.POST)
    public String newLibrarian(HttpSession session, ModelMap model, @RequestParam String name,
					@RequestParam String email, @RequestParam String password,
					@RequestParam String phone) throws MissingServletRequestParameterException {
		
		String type = (String) session.getAttribute("type");
		if (!type.equals("admin")) {
			return "home";
		}
		ValidationService service = new ValidationService();
		if (!service.validateEmail(email)) {
			model.addAttribute("error", "Email adresi geçerli değil");
    		return "librarian/new";	
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
	    	FindIterable<Document> iterable = db.getCollection("users").find(
	                new Document("email", email));
	 
	    	Document doc = iterable.first();
	    
	    	if (doc != null) {
	    		model.addAttribute("error", "Email daha önce kullanılmış");
	    		mongoClient.close();
	    		return "librarian/new";
	    	}
	    	doc = new Document();
	    	doc.append("email", email);
	    	doc.append("name", name);
	    	doc.append("password", password);
	    	doc.append("phone", phone);
	    	db.getCollection("users").insertOne(doc);
	    	System.out.println("librarian added");
	    	
		} catch (Exception ex) {
			ex.printStackTrace();
			
		} finally {
			mongoClient.close();
			return "redirect:/librarians";
		}
		
    }
	

	@RequestMapping(value = "/librarians", method = RequestMethod.GET)
    public String librariansPage(HttpSession session, ModelMap model) {
		
		String type = (String) session.getAttribute("type");
		if (!type.equals("admin")) {
			return "home";
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try { 
			List<Document> librarians = (List<Document>) db.getCollection("users").find().into(
						new ArrayList<Document>());
			
			model.addAttribute("librarians", librarians);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "librarian/all";
		
    }
	
	@RequestMapping(value = "/updatelibrarian/{id}", method = RequestMethod.GET)
    public String updateLibrarianPage(HttpSession session, ModelMap model, @PathVariable("id") String id) {
		
		String type = (String) session.getAttribute("type");
		if (!type.equals("admin")) {
			return "home";
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
			ObjectId object = new ObjectId(id);  
	    	FindIterable<Document> iterable = db.getCollection("users").find(
	                new Document("_id", object));
	    	
	    	Document doc = iterable.first();
	    	
	    	if (doc != null) {
	    		System.out.println("librarian: " + doc.getString("name"));
	    		model.addAttribute("librarian", doc);
	    	}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
    	
		return "librarian/update";
	}
	
	@RequestMapping(value = "/updatelibrarian", method = RequestMethod.POST)
    public String updateLibrarian(HttpSession session, ModelMap model, @RequestParam String id,
    					@RequestParam String name, @RequestParam String email, 
    					@RequestParam String phone, @RequestParam String password) {
		
		String type = (String) session.getAttribute("type");
		if (!type.equals("admin")) {
			return "home";
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
			ObjectId object = new ObjectId(id);  
	    	FindIterable<Document> iterable = db.getCollection("users").find(
	                new Document("_id", object));
	    	
	    	Document doc = iterable.first();
	    	ValidationService service = new ValidationService();
			if (!service.validateEmail(email)) {
				model.addAttribute("error", "Email adresi geçerli değil");
				model.addAttribute("librarian", doc);
	    		return "librarian/update";	
			}
			// look if email exist
			iterable = db.getCollection("users").find(
	                new Document("email", email));
	    	Document exist = iterable.first();
	    	if (exist != null && !exist.getObjectId("_id").equals(object)) {
	    		model.addAttribute("error", "Email adresi kullanılmış");
				model.addAttribute("librarian", doc);
	    		return "librarian/update";
	    	}
	    	
	    	if (doc != null) {
	    		System.out.println("librarian: " + doc.getString("name"));
	    		Document newdoc = new Document();
	    		newdoc.append("name", name);
	    		newdoc.append("email", email);
	    		newdoc.append("phone", phone);
	    		newdoc.append("password", password);
	    		db.getCollection("users").updateOne(doc, new Document("$set",newdoc));
	    		System.out.println("librarian updated");
	    		
	    	}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "redirect:/librarians";
	}
	
	@RequestMapping(value = "/deletelibrarian", method = RequestMethod.POST)
    public String deleteLibrarian(HttpSession session, @RequestParam String id) {
		
		String type = (String) session.getAttribute("type");
		if (!type.equals("admin")) {
			return "home";
		}
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
			ObjectId object = new ObjectId(id);  
	    	db.getCollection("users").deleteOne(new Document("_id",object));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "redirect:/librarians";
	}
}
