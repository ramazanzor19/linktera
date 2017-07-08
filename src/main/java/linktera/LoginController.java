package linktera;


import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

@Controller
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootPage(HttpSession session) {
		
		return "home";
    }
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
    public String adminLoginPage(HttpSession session) {
		
		return "adminlogin";
    }
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public String adminLogin(HttpSession session, ModelMap model, @RequestParam String email,
								@RequestParam String password) throws MissingServletRequestParameterException {
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		
    	try {
	    	
	    	FindIterable<Document> iterable = db.getCollection("admins").find(
	                new Document("email", email).append("password", password));
	    	
	    	Document doc = iterable.first();
	    	if (doc != null) {
	    		session.setAttribute("email", doc.getString("email"));
	    		session.setAttribute("name", doc.getString("name"));
		        session.setAttribute("type", "admin");
		        mongoClient.close();
		        return "home";
	    	} else {
	    		model.addAttribute("error", "Login Failed"); 		
	    	}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		mongoClient.close();
    	}
    	
    	return "adminlogin";	
    }
	
	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public String userLoginPage(HttpSession session) {
		
		return "userlogin";
    }
	
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    public String userLogin(HttpSession session, ModelMap model, @RequestParam String email,
								@RequestParam String password) throws MissingServletRequestParameterException {
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		
    	try {
	    	FindIterable<Document> iterable = db.getCollection("users").find(
	                new Document("email", email).append("password", password));
	    	
	    	Document doc = iterable.first();
	    	if (doc != null) {
	    		session.setAttribute("email", doc.getString("email"));
	    		session.setAttribute("name", doc.getString("name"));
		        session.setAttribute("type", "user");
		        return "home";
	    	} else {
	    		model.addAttribute("error", "Login Failed");
	    		return "userlogin";
	    	}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}	finally {
    		mongoClient.close();
    	}  	
    	return "userlogin";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
		
		session.removeAttribute("type");
		session.removeAttribute("name");
		session.removeAttribute("email");
		return "home";
    }
	
}
