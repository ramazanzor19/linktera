package linktera;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

@Controller
public class BookController {

	@RequestMapping(value = "/newbook", method = RequestMethod.GET)
    public String newBookPage(HttpSession session) {
		
		String type = (String) session.getAttribute("type");
		if (type != null) {
			return "book/new";
		} else {
			return "home";
		}
    }
	
	@RequestMapping(value = "/newbook", method = RequestMethod.POST)
    public String newBook(HttpSession session, ModelMap model, @RequestParam String id,
					@RequestParam String name, @RequestParam String writer,
					@RequestParam String publisher, @RequestParam String count )  {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		
		int book_count;
		try {
			book_count = Integer.parseInt(count);
		} catch (NumberFormatException e) {
			model.addAttribute("error", "Adet geçerli değil");
    		model.addAttribute("bookname", name);
    		model.addAttribute("publisher", publisher);
    		model.addAttribute("writer", writer);
    		return "book/new";
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
	    	FindIterable<Document> iterable = db.getCollection("books").find(
	                new Document("name", name).append("publisher", publisher));
	 
	    	Document doc = iterable.first();
	    
	    	if (doc != null) {
	    		model.addAttribute("error", "Kitap sistemde kayıtlı");
	    		model.addAttribute("bookname", name);
	    		model.addAttribute("publisher", publisher);
	    		model.addAttribute("writer", writer);
	    		return "book/new";
	    	}
	    	doc = new Document();
	    	doc.append("id", id);
	    	doc.append("name", name);
	    	doc.append("publisher", publisher);
	    	doc.append("writer", writer);
	    	doc.append("count", book_count);
	    	doc.append("rezervation_count", 0);
	    	doc.append("total_rezervation_count", 0);
	    	db.getCollection("books").insertOne(doc);
	    	System.out.println("new book added");
	    	
		} catch (Exception ex) {
			ex.printStackTrace();
			
		} finally {
			mongoClient.close();
		}
		return "redirect:/books";
    }
	
	@RequestMapping(value = "/books", method = RequestMethod.GET)
    public String booksPage(HttpSession session, ModelMap model) {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try { 
			List<Document> books = (List<Document>) db.getCollection("books").find().into(
						new ArrayList<Document>());
			
			model.addAttribute("books", books);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "book/all";
		
    }
	
	@RequestMapping(value = "/deletebook", method = RequestMethod.POST)
    public String deleteBook(HttpSession session, @RequestParam String id) {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
		try {
			ObjectId object = new ObjectId(id);  
	    	db.getCollection("books").deleteOne(new Document("_id",object));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "redirect:/books";
	}
}
