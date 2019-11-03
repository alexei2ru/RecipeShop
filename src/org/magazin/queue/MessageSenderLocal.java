package org.magazin.queue;

import java.util.HashMap;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.jms.JMSException;

@Local
public interface MessageSenderLocal {
	void sendMapMessage(HashMap<String, Integer> contents) throws JMSException;
}
