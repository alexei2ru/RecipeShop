package org.magazin;
import javax.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.magazin.helpers.JPAService;

import com.opensymphony.xwork2.ActionSupport;
public class LoginCheck  extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(LoginCheck.class.getName());
    
	private String uname,pass;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	 public void validate() {
	        if (getUname().length() == 0) {
	            addFieldError("uname", "User Name is required");
	        } 
	        if (getPass().length() == 0) {
	            addFieldError("pass", "Password required");
	        }
	    }
	
	
	public String credCheck()
	{
		
	    EntityManager em = JPAService.getEntityManager();
	    LOGGER.info("Entity manager initialized");
	    
	    try{
	    	String usr = em.createQuery("SELECT u.username from Users u WHERE u.username=:username and u.password=:password")
	    			.setParameter("username", uname).setParameter("password", pass).getSingleResult().toString();
	    			
	    	LOGGER.info("User found: "+usr);
	    //String result = q.getSingleResult().toString();
	    	if(usr.equals(uname))
			{
	    		LOGGER.info("User found: "+uname);
				em.close();
				return "success";
			}
	    	
			em.close();
			return "error";
	    }
	    catch (Exception e) {
	    	em.close();
	    	return "error";
	    }
	    finally{
	    	
	    }
	    
		
		
	}

}
