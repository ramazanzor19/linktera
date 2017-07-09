package linktera;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@WebListener
public class DatabaseListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
    	
    	MongoClient mongoClient = new MongoClient();
    	MongoDatabase db = mongoClient.getDatabase("linktera");
    	
    	//create database connection and set it to context
    	ctx.setAttribute("MongoClient", mongoClient);
    	ctx.setAttribute("MongoDatabase", db);
    	System.out.println("Database connection initialized");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
    	MongoClient mongoClient = (MongoClient) ctx.getAttribute("MongoClient");
    	mongoClient.close();
    	System.out.println("Database connection closed");
    	
    }
}
