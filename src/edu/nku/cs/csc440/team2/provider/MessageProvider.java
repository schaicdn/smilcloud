package edu.nku.cs.csc440.team2.provider;

import edu.nku.cs.csc440.team2.message.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import edu.nku.cs.csc440.team2.mediaCloud.MessageLite;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.os.Environment;
import com.thoughtworks.xstream.XStream;


public class MessageProvider {

	public MessageProvider() { }

	// List of Messages
	public MessageLite[] getAllMessage(int userId) {

		try {
			String xml = RequestHelper
					.makeHttpGetRequest("http://nkucloud.dyndns.org:8080/mediacloud/getMessageList.jsp?user="
							+ userId);
			
			XStream xstream = new XStream();
			
			MessageLite[] allMessageLite = (MessageLite[]) xstream.fromXML(xml);

			return allMessageLite;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get Message
	 * 
	 * @param Message
	 *            ID
	 * @return Message
	 */
	public Message getMessageById(String id) {

		// Return parsed message
		Message msg = null;

		// Make request to get message XML

		try {
			String xml = RequestHelper
					.makeHttpGetRequest("http://nkucloud.dyndns.org:8080/mediacloud/getMessage.jsp?uniqueId="
							+ id);

			File fi = new File(Environment.getExternalStorageDirectory()
					+ "/tempfile.xml");

			fi.createNewFile();

			FileOutputStream fop = new FileOutputStream(fi);
			fop.write(xml.getBytes());

			fop.flush();
			fop.close();

			msg = new Message(fi);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Save message
	 * 
	 * @param Message
	 * @return Message ID
	 */
	public void sendMessage(int senderId, int recipientId, String messageTitle ,Message msg) {

		String xml = msg.toXml();

		String url = "http://nkucloud.dyndns.org:8080/mediacloud/sendMessage.jsp";
		
		

		List<NameValuePair> data = new ArrayList<NameValuePair>();
		
		data.add(new BasicNameValuePair("userId", senderId + ""));
		data.add(new BasicNameValuePair("recipient", recipientId + ""));
		data.add(new BasicNameValuePair("name", messageTitle));
		data.add(new BasicNameValuePair("message", xml));

		try {

			// Make POST request
			RequestHelper.makeHttpPostRequest(url, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//return "";

	}

}