import java.net.*;
import com.google.gson.*;
import java.io.*;
public class api {
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Weather API
	public static String weather(String location) throws Exception {
		String myAPIurl = "http://api.openweathermap.org/data/2.5/weather?";
		// Ask user: What city are you looking for? 
		String userInput = "";// Read user input Ex: “Dallas”
		String myApiToken = "&APPID=bf5a44f47c15cd6c9704c60bc9b8bbc9";
		
		//check if the user input is a zipcode or a city name
		if (location.charAt(0) >= '0' && location.charAt(0) <= '9') {
			userInput = "zip=" + location;
		}
		else {
			userInput = "q=" + location;
		}

		String weatherURL = myAPIurl + userInput + myApiToken; 
		URL url = new URL(weatherURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		
		while((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		return parseJsonWeather(sb.toString());  //this function will return 
	}
		
	//At this point: Make another function that will parse your JSON 
	public static String parseJsonWeather(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		//print out object and see what is inside of it 
		//If this doesn’t make sense - take another look at the JSON Response I provided on Slide 5
		String cityName = object.get("name").getAsString();
		JsonObject main = object.getAsJsonObject("main");
		double temp = ((9.0/5.0) * ((main.get("temp").getAsDouble()) - 273) + 32);
		temp = Math.round(temp * 100.0) / 100.0;
		return " "+temp; 
	} 
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Last.fm API
	public static String music(String user) throws Exception{
		
		
		String myAPIurl = "http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=";
		String userInput = user;
		String myApiToken = "&api_key=24e7330e3c60b14c18d457b5d8b73506&format=json";
		
		String musicURL = myAPIurl + userInput + myApiToken;
		URL url = new URL(musicURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		
		while((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		return parseJsonMusic(sb.toString());  //this function will return
	}
	
	public static String parseJsonMusic(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		//print out object and see what is inside of it 
		//If this doesn’t make sense - take another look at the JSON Response I provided on Slide 5
		if (object.has("error")) {
			return (" could not be found on last.fm");
		}
		else {
			JsonObject recenttracks = object.getAsJsonObject("recenttracks");
			JsonArray track = recenttracks.getAsJsonArray("track");
			if (track.size() != 0) {
				JsonObject track1 = track.get(0).getAsJsonObject();
				String title = track1.get("name").getAsString();
				String artist = track1.get("artist").getAsJsonObject().get("#text").getAsString();
				String album = track1.get("album").getAsJsonObject().get("#text").getAsString();
				if (!album.equals(""))
					return ("Now playing: " + title +"\nBy: " + artist + "\nFrom the album: " +album);
				else
					return ("Now playing: " + title +"\nBy: " + artist);
			}
			else {
				return (" could not be found on last.fm");
			}
		}
	} 
	
}