package blitbotbot;

import java.util.*;
import java.util.concurrent.TimeUnit;

import twitter4j.*;

public class blitbotbot {

	public static void main(String[] args) throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		long lastStatusId = twitter.getUserTimeline().get(0).getInReplyToStatusId();
		do {
			try {
	            List<Status> statuses;
	            String user = "theblitbot";
	            statuses = twitter.getUserTimeline(user);
	            Status status = statuses.get(0);
	            if (status.getId()!=lastStatusId) {
	                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	                reply(status.getId(),twitter);
	                lastStatusId=status.getId();
	                TimeUnit.MINUTES.sleep(30);
	            }else {
	            	System.out.println("duplicate?");
	            	System.exit(-1);
	            }
	        } catch (TwitterException | InterruptedException te) {
	            te.printStackTrace();
	            System.out.println("Failed to get timeline: " + te.getMessage());
	            System.exit(-1);
	        }
		}while(true);
	}
	
	public static void reply(long origStatusId,Twitter twitter) throws TwitterException {
		Status status = twitter.showStatus(origStatusId);
		StatusUpdate stat;
		if(status.getText().contains("lame")) {
			stat = new StatusUpdate("@theblitbot shut up nerd");
		}else
			stat = new StatusUpdate("@theblitbot lame");
		stat.setInReplyToStatusId(origStatusId);
		try {
			twitter.updateStatus(stat);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
