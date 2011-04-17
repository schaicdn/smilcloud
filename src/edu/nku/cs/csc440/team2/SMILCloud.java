package edu.nku.cs.csc440.team2;

import java.util.ArrayList;

import edu.nku.cs.csc440.team2.mediaCloud.MessageLite;

import android.app.Application;

public class SMILCloud extends Application 
{
  private ArrayList<MessageLite> messages;
	
  private String queuedDocumentToPlayID;
  private String queueDocumentToEditID;
	
	public void queueDocumentToPlay(String id) { this.queuedDocumentToPlayID = id; }
	public String getQueuedDocumentForPlayback() { return this.queuedDocumentToPlayID; }
	
	public void queueDocumentToEdit(String id) { this.queueDocumentToEditID = id; }
	public String getQueuedDocumentForEditing() { return this.queueDocumentToEditID; }

  public synchronized void addMessage(MessageLite message)
  {
    this.messages.add(message);
  }

  public synchronized ArrayList<MessageLite> getMessages()
  {
    return this.messages;
  }
}
