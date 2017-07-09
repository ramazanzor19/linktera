package linktera;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

@Controller
public class RezervationController {
	
	@RequestMapping(value = "/newrezervation", method = RequestMethod.GET)
    public String newRezervationPage(HttpSession session) {
		
		String type = (String) session.getAttribute("type");
		if (type != null) {
			return "rezervation/new";
		} else {
			return "home";
		}
    }
	
	@RequestMapping(value = "/newrezervation", method = RequestMethod.POST)
    public String newBook(HttpSession session, ModelMap model, @RequestParam String bookid,
					@RequestParam String studentid, @RequestParam String studentname,
					@RequestParam String phone)  {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		
		ServletContext ctx = session.getServletContext();
    	MongoDatabase db = (MongoDatabase) ctx.getAttribute("MongoDatabase");
		try {
	    	FindIterable<Document> iterable = db.getCollection("books").find(
	                new Document("id", bookid));
	 
	    	Document doc = iterable.first();
	    
	    	// if the book not exist
	    	if (doc == null) {
	    		model.addAttribute("error", "Kitap bulunamadı");
	    		model.addAttribute("studentid", studentid);
	    		model.addAttribute("studentname", studentname);
	    		model.addAttribute("phone", phone);
	    		return "rezervation/new";
	    	}
	    	
	    	// if the all the books reserved
	    	if (doc.getInteger("count") <= doc.getInteger("rezervation_count")) {
	    		model.addAttribute("error", "Bu kitap başkaları tarafından rezerve edildi");
	    		model.addAttribute("studentid", studentid);
	    		model.addAttribute("studentname", studentname);
	    		model.addAttribute("phone", phone);
	    		return "rezervation/new";
	    	}
	    	
	    	// if you have already reserved the book
	    	iterable = db.getCollection("rezervations").find(
	                new Document("bookid", bookid).append("studentid", studentid));
	    	Document reservedBefore = iterable.first();
	    	if (reservedBefore != null) {
	    		model.addAttribute("error", "Öğrenci bu kitabı daha önce rezerve etmiş");
	    		model.addAttribute("studentid", studentid);
	    		model.addAttribute("studentname", studentname);
	    		model.addAttribute("phone", phone);
	    		return "rezervation/new";
	    	}
	    	
	    	// update book reservation count
	    	Document newdoc = new Document();
    		newdoc.append("rezervation_count", doc.getInteger("rezervation_count")+1);
    		newdoc.append("total_rezervation_count", doc.getInteger("total_rezervation_count")+1);
    		db.getCollection("books").updateOne(doc, new Document("$set",newdoc));

    		// add rezervation
	    	doc = new Document();
	    	doc.append("bookid", bookid);
	    	doc.append("studentid", studentid);
	    	doc.append("studentname", studentname);
	    	doc.append("phone", phone);
	    	doc.append("date", new Date());
	    	doc.append("return", "bekleniyor");
	    	db.getCollection("rezervations").insertOne(doc);
		} catch (Exception ex) {
			ex.printStackTrace();	
		}
		return "redirect:/rezervations";
    }
	
	@RequestMapping(value = "/rezervations", method = RequestMethod.GET)
    public String rezervationsPage(HttpSession session, ModelMap model) {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		
		ServletContext ctx = session.getServletContext();
    	MongoDatabase db = (MongoDatabase) ctx.getAttribute("MongoDatabase");
		try { 
			List<Document> rezervations = (List<Document>) db.getCollection("rezervations").find().into(
						new ArrayList<Document>());
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	    	model.addAttribute("formatter", formatter);
			model.addAttribute("rezervations", rezervations);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "rezervation/all";
		
    }
	
	@RequestMapping(value = "/backrezervation", method = RequestMethod.GET)
    public String returnRezervationPage(HttpSession session) {
		
		String type = (String) session.getAttribute("type");
		if (type != null) {
			return "rezervation/return";
		} else {
			return "home";
		}
    }
	
	@RequestMapping(value = "/backrezervation", method = RequestMethod.POST)
    public String returnRezervation(HttpSession session, ModelMap model,
    		@RequestParam String bookid, @RequestParam String studentid) {
		
		String type = (String) session.getAttribute("type");
		if (type == null) {
			return "home";
		}
		
		ServletContext ctx = session.getServletContext();
    	MongoDatabase db = (MongoDatabase) ctx.getAttribute("MongoDatabase");
		try {
	    	FindIterable<Document> iterable = db.getCollection("rezervations").find(
	                new Document("bookid", bookid).append("studentid", studentid).append("return", "bekleniyor"));
	 
	    	Document doc = iterable.first();
	    	
	    	// if rezervation not found 
	    	if (doc == null) {
	    		model.addAttribute("error", "Rezervasyon kaydı bulunamadı");
	    		model.addAttribute("bookid", bookid);
	    		model.addAttribute("studentid", studentid);
	    		return "rezervation/return";
	    	}
	    	
	    	// update rezervation - change return status to 'teslim edildi'
	    	Document newdoc = new Document();
	    	newdoc.append("return", "teslim edildi");
	    	db.getCollection("rezervations").updateOne(doc, new Document("$set",newdoc));
	    	
	    	// decrement book rezervation count by 1
	    	iterable = db.getCollection("books").find(
	                new Document("id", bookid));
	    	doc = iterable.first();
	    	newdoc = new Document();
	    	newdoc.append("rezervation_count", doc.getInteger("rezervation_count")-1);
	    	db.getCollection("books").updateOne(doc, new Document("$set",newdoc));
	    	
	    	
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return "redirect:/rezervations";
    }
}
