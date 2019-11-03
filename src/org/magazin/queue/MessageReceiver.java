package org.magazin.queue;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

@MessageDriven(
activationConfig = {
		// IBM MQSeries
		@ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jms/__StrutiConnectionFactory"),
		//@ActivationConfigProperty(propertyName = "destination", propertyValue = "TADA.HIERARCHY.QUEUE"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/__IngredientQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "wmq.jmsra")
})
@TransactionManagement(TransactionManagementType.BEAN)
  public class MessageReceiver implements MessageListener {
	  private Logger LOGGER = LogManager.getLogger(this.getClass().getSimpleName());
      

   @Override
  public void onMessage(Message message) {
      LOGGER.info("onMsg!" + message);
  }
  }