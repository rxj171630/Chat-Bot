import org.jibble.pircbot.*;
//extend the main pircbot class - This can only be done AFTER you import the jar file 
//This class is the main logic of your pircbot, this is where you will implement any functionality 
public class bot extends PircBot
{
	//constructor 
	public bot(){
       		this.setName("rahulsBot"); //this is the name the bot will use to join the IRC server
       		api api = new api();
   	}
	//every time a message is sent, this method will be called and this information will be passed on
	//this is how you read a message from the channel 
	public void onMessage(String channel, String sender, String login, String hostname, String message)
    	{
	// Use this function to read the message that comes in 
	//For example, you can have an if statment that says:
	if (message.toLowerCase().contains("weather")) {
		//the user wants weather, so call the weather API you created in part 1
		String location = "Richardson";
		
		 // Split message into separate words
	    String[] words = message.split(" ");
	    
	    if (words.length == 1) {
	    	location = "Richardson";
	    }
	    // If message is 2 words long (looking for "weather {location}"
	    if (words.length == 2) {
	        // If they say "weather {location}" then location = second word.
	        // If they say "{location} weather" then location = first word
	        if (words[0].equals("weather")) {
	            location = words[1];
	        } else {
	            location = words[0];
	        }
	    }
	    if (words.length == 3) {
	    	if (words[1].equals("in")) {
	    		location = words[2];
	    	}
	    }
	        location = location.substring(0, 1).toUpperCase() + location.substring(1);
	        try {
	        	sendMessage(channel, "In " +location + ", it is currently " +api.weather(location) + "°F.");
	        } catch (Exception e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	}
	if (message.toLowerCase().contains("playing") || message.toLowerCase().contains("song") || message.toLowerCase().contains("music")) {
		try {
			String output = api.music(sender);
			String title = "";
			String artist = "";
			String album = "";
			
			if (!output.contains("\n")) {
				sendMessage(channel, "The user \"" +sender +"\"" +output);
			}
			else {
				title = output.substring(0, output.indexOf('\n'));
				output = output.substring(output.indexOf('\n') + 1);
				if (output.contains("\n")) {
					artist = output.substring(0, output.indexOf('\n'));
					output = output.substring(output.indexOf('\n') + 1);
					album = output;
				}
				else {
					artist = output;
				}
				sendMessage(channel, title);
				sendMessage(channel, artist);
				if (!album.equals("From the album:"))
					sendMessage(channel, album);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	//or to start, do something small like:
	if (message.toLowerCase().contains("hello") || message.toLowerCase().contains("hey")) {
		//the user wants weather, so call the weather API you created in part 1
		//this is how you send a message back to the channel 
		sendMessage(channel, "Hey " + sender + "! ");
	} 
	}
    	}

