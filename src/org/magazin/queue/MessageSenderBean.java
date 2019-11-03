package org.magazin.queue;

import java.util.HashMap;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.ActivationConfigProperty;
import java.util.Map;

import javax.annotation.Resource;  
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.magazin.cooking.RecipeManagerBean;
import org.magazin.shopping.BasketManagerBean;

@Stateless
public class MessageSenderBean implements MessageSenderLocal {
	
  private Logger LOGGER = LogManager.getLogger(this.getClass().getSimpleName());
  //@Resource(mappedName = "jms/__StrutiConnectionFactory")
	
  @Resource(mappedName = "jms/__StrutiConnectionFactory")
  private ConnectionFactory connectionFactory;
  
  @Resource(mappedName = "jms/__IngredientQueue")
  private  Destination queue;
  MessageProducer messageProducer;
  private HashMap<String, Integer> contents;
  
  public void sendMapMessage(HashMap<String, Integer> contents ) {
	  
	 
    try {
    	 
	      Connection connection = connectionFactory.createConnection();
	      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	      messageProducer = session.createProducer(queue);
	      MapMessage mapMessage = session.createMapMessage();
	      
	      contents.forEach((x,y) -> {
			try {
				mapMessage.setInt(x, y);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }); 
      
      messageProducer.send(mapMessage);
      LOGGER.info("Message sent");
      messageProducer.close();
      session.close();
      connection.close();
    }
    catch (JMSException e) {
    	e.printStackTrace();
    }
  }
}
