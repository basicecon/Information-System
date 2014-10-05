package movies;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessCommandServlet extends HttpServlet {
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Key movieKey = KeyFactory.createKey("Movies", "Purdue");

		String content = req.getParameter("command");
		String[] commandEls = content.split(":");
		String results = null;

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (commandEls[0].equals("add_actor")) {
			String name = commandEls[1];
			String gender = commandEls[2];
			String date_of_birth = commandEls[3];

			boolean duplicate;
			Key actorKey = KeyFactory.createKey("Actor", name);
			Entity actor;

			try {
				actor = datastore.get(actorKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (!duplicate) {
				
				// actor = new Entity("Actor", actorKey);
				actor = new Entity(actorKey);
				actor.setProperty("name", name);
				actor.setProperty("gender", gender);
				actor.setProperty("date_of_birth", date_of_birth);
				datastore.put(actor);
				results = "Command executed successfully!";
			} else {
				results = "Actor already exists!";
			}
		} else if (commandEls[0].equals("add_director")) {
			String name = commandEls[1];
			String gender = commandEls[2];
			String date_of_birth = commandEls[3];

			boolean duplicate;
			Key directorKey = KeyFactory.createKey("Director", name);
			Entity director;

			try {
				director = datastore.get(directorKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (!duplicate) {
				director = new Entity(directorKey);
				director.setProperty("name", name);
				director.setProperty("gender", gender);
				director.setProperty("date_of_birth", date_of_birth);
				datastore.put(director);
				results = "Command executed successfully!";
			} else {
				results = "Director already exists!";
			}
		} else if (commandEls[0].equals("add_company")) {
			String name = commandEls[1];
			String address = commandEls[2];

			boolean duplicate;
			Key companyKey = KeyFactory.createKey("Company", name);
			Entity company;

			try {
				company = datastore.get(companyKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (!duplicate) {
				company = new Entity(companyKey);
				company.setProperty("name", name);
				company.setProperty("address", address);
				datastore.put(company);
				results = "Command executed successfully!";
			} else {
				results = "Company already exists!";
			}
		} else if (commandEls[0].equals("add_movie")) {
			String title = commandEls[1];
			String release_year = commandEls[2];
			String length = commandEls[3];
			String genre = commandEls[4];
			String plot = commandEls[5];
			String director = commandEls[6];
			String company = commandEls[7];

			String movie_key = title + "?" + release_year;

			// check director/company exist
			boolean valid;
			Key directorKey = KeyFactory.createKey("Director", director);
			Key companyKey = KeyFactory.createKey("Company", company);
			Entity movie_director, movie_company;
			try {
				movie_director = datastore.get(directorKey);
				movie_company = datastore.get(companyKey);
				valid = true;
			} catch (EntityNotFoundException e) {
				valid = false;
			}

			boolean duplicate;
			Key movieDesKey = KeyFactory.createKey("Movie", movie_key);
			Entity movie;

			try {
				movie = datastore.get(movieDesKey);
				duplicate = true;

			} catch (Exception e) {
				duplicate = false;
			}
			if (valid) {
				if (!duplicate) {
					
					// entity kind = Movies
					movie = new Entity(movieDesKey);
					movie.setProperty("Title", title);
					movie.setProperty("Release_Year", release_year);
					movie.setProperty("Length", length);
					movie.setProperty("Genre", genre);
					movie.setProperty("Plot", plot);
					movie.setProperty("Director", director);
					movie.setProperty("Company", company);
					datastore.put(movie);
					results = "Command executed successfully!";
				} else {
					results = "Movie already exists!";
				}
			} else {
				results = "Foreign key constraints violated!";
			}
		} else if (commandEls[0].equals("add_awards_event")) {
			String event_name = commandEls[1];
			String year = commandEls[2];
			String venue = commandEls[3];

			String award_event_key = event_name + "?" + year;

			boolean duplicate;
			Key awardEventKey = KeyFactory.createKey("Awards_Event",
					award_event_key);
			Entity awardEvent;

			try {
				awardEvent = datastore.get(awardEventKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (!duplicate) {
				awardEvent = new Entity(awardEventKey);
				awardEvent.setProperty("Event_Name", event_name);
				awardEvent.setProperty("Year", year);
				awardEvent.setProperty("Venue", venue);
				datastore.put(awardEvent);
				results = "Command executed successfully!";
			} else {
				results = "Awards Event already exists!";
			}
		} else if (commandEls[0].equals("add_user")) {
			String user_id = commandEls[1];

			boolean duplicate;
			Key userKey = KeyFactory.createKey("User", user_id);
			Entity user;

			try {
				user = datastore.get(userKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (!duplicate) {
				user = new Entity(userKey);
				user.setProperty("User_Id", user_id);
				datastore.put(user);
				results = "Command executed successfully!";
			} else {
				results = "User already exists!";
			}
		} else if (commandEls[0].equals("add_movie_rating")) {
			String user_id = commandEls[1];
			String movie_title = commandEls[2];
			String release_year = commandEls[3];
			String rating = commandEls[4];

			// foreign keys
			String user_key = user_id;
			String movie_key = movie_title + "?" + release_year;
			String movie_rating_key = user_key + "?" + movie_key;

			// check user/movies exist
			boolean valid;
			Key userKey = KeyFactory.createKey("User", user_key);
			Key movieDesKey = KeyFactory.createKey("Movie", movie_key);
			Entity user, movie;
			try {
				user = datastore.get(userKey);
				movie = datastore.get(movieDesKey);
				valid = true;
			} catch (Exception e) {
				valid = false;
			}

			boolean duplicate;
			Key movieRatingKey = KeyFactory.createKey("Movie_Rating",
					movie_rating_key);
			Entity movieRating;

			try {
				movieRating = datastore.get(movieRatingKey);
				duplicate = true;

			} catch (Exception e) {
				duplicate = false;
			}
			if (valid) {
				if (!duplicate) {
					movieRating = new Entity(movieRatingKey);
					movieRating.setProperty("User_Id", user_id);
					movieRating.setProperty("Movie_Title", movie_title);
					movieRating.setProperty("Release_Year", release_year);
					movieRating.setProperty("Rating", rating);
					datastore.put(movieRating);
					results = "Command executed successfully!";
				} else {
					results = "Movie Rating already exists!";
				}
			} else {
				results = "Foreign key constraints violated!";
			}
		} else if (commandEls[0].equals("add_cast")) {
			String movie_title = commandEls[1];
			String release_year = commandEls[2];
			String actor_name = commandEls[3];
			String role = commandEls[4];

			String actor_key = actor_name;
			String movie_key = movie_title + "?" + release_year;
			String cast_key = movie_key + "?" + actor_key;

			// check actor/movies exist
			boolean valid;
			Key actorKey = KeyFactory.createKey("Actor", actor_key);
			Key movieDesKey = KeyFactory.createKey("Movie", movie_key);
			Entity actor, movie;
			try {
				actor = datastore.get(actorKey);
				movie = datastore.get(movieDesKey);
				valid = true;
			} catch (Exception e) {
				valid = false;
			}

			boolean duplicate;
			Key castKey = KeyFactory.createKey("Cast", cast_key);
			Entity cast;

			try {
				cast = datastore.get(castKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}

			if (valid) {
				if (!duplicate) {
					cast = new Entity(castKey);
					cast.setProperty("Movie_Title", movie_title);
					cast.setProperty("Release_Year", release_year);
					cast.setProperty("Actor_Name", actor_name);
					cast.setProperty("Role", role);
					datastore.put(cast);
					results = "Command executed successfully!";
				} else {
					results = "Cast already exists!";
				}
			} else {
				results = "Foreign key constraints violated!";
			}
		} else if (commandEls[0].equals("add_nomination_category")) {
			String category_name = commandEls[1];
			boolean duplicate;
			Key nominationCategoryKey = KeyFactory.createKey("Nomination_Category", category_name);
			Entity nomination_category;
			try {
				nomination_category = datastore.get(nominationCategoryKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}
			
			if (!duplicate) {
				nomination_category = new Entity(nominationCategoryKey);
				nomination_category.setProperty("Category_Name", category_name);
				datastore.put(nomination_category);
				results = "Command executed successfully!";
			} else {
				results = "Nomination Category already exists!";
			}
		} else if (commandEls[0].equals("add_nomination")) {
			String movie_title = commandEls[1];
			String movie_year = commandEls[2];
			String event = commandEls[3];
			String event_year = commandEls[4];
			String category = commandEls[5];
			String won = commandEls[6];
			
			String movie_key = movie_title + "?" + movie_year;
			String event_key = event + "?" + event_year;
			String category_key = category;
			
			// check actor/movies exist
			boolean valid;
			Key movieDesKey = KeyFactory.createKey("Movie", movie_key);
			Key eventKey = KeyFactory.createKey("Awards_Event", event_key);
			Key categoryKey = KeyFactory.createKey("Nomination_Category", category_key);
			
			Entity nomination_movie, nomination_event, nomination_category;
			try {
				nomination_movie = datastore.get(movieDesKey);
				nomination_event = datastore.get(eventKey);
				nomination_category = datastore.get(categoryKey);
				valid = true;
			} catch (Exception e) {
				valid = false;
			}
			
			boolean duplicate;
			String nomination_key = movie_key + "?" + event_key + "?" + category_key;
			Key nominationKey = KeyFactory.createKey("Nomination", nomination_key);
			Entity nomination;
			try {
				nomination = datastore.get(nominationKey);
				duplicate = true;
			} catch (Exception e) {
				duplicate = false;
			}
			
			if (valid) {
				if (!duplicate) {
					nomination = new Entity(nominationKey);
					nomination.setProperty("Movie_Title", movie_title);
					nomination.setProperty("Movie_Year", movie_year);
					nomination.setProperty("Event", event);
					nomination.setProperty("Event_Year", event_year);
					nomination.setProperty("Category", category);
					nomination.setProperty("Won", won);
					datastore.put(nomination);
					results = "Command executed successfully!";
				} else {
					results = "Nomination already exists!";
				}
			} else {
				results = "Foreign key constraints violated!";
			}
			
		} else if (commandEls[0].equals("get_movies_by_company")) {
			String company = commandEls[1];
			Query query = null;
			List<Entity> movies = new LinkedList<Entity>();
			
			query = new Query("Movie");
			query.addFilter("Company", FilterOperator.EQUAL, company);
			movies = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			if (movies != null) {
				results = "";
				for (Entity e : movies) {
					results += e.getProperty("Title") + ", " + e.getProperty("Release_Year") + "; ";
				}
				results = results.substring(0, results.length()-2);
			}
		} else if ( commandEls[0].equals("get_movies_by_director") ) {
			String director = commandEls[1];
			Query query = null;
			List<Entity> movies = new LinkedList<Entity>();
			
			query = new Query("Movie");
			query.addFilter("Director", FilterOperator.EQUAL, director);
			movies = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			if (movies != null) {
				results = "";
				for (Entity e : movies) {
					results += e.getProperty("Title") + ", " + e.getProperty("Release_Year") + "; ";
				}
				results = results.substring(0, results.length()-2);
			}
		} else if ( commandEls[0].equals("get_nominations_for_actor") ) {
			//results += "AAA   ";
			// printing twice????
			
			String actor_name = commandEls[1];
			
			Key actorKey = KeyFactory.createKey("Actor", actor_name);
			Entity actor = null;
			try {
				actor = datastore.get(actorKey);
			} catch (EntityNotFoundException e) {
				results += "ENTITY NOT FOUND!!!";
			}
			String gender = (String) actor.getProperty("gender");
			Query cast_query = null;
			Query nomination_query = null;
			List<Entity> casts = null;
			List<Entity> nominations = null;
			
			cast_query = new Query("Cast");
			cast_query.addFilter("Actor_Name", FilterOperator.EQUAL, actor_name);	
			casts = datastore.prepare(cast_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));

			//results += "SIZE=" + casts.size();
			if (casts != null) {
				results = "";
				for (Entity e : casts) {
					nomination_query = new Query("Nomination");
					
					String role = (String) e.getProperty("Role");
				
					if (gender.equals("Female")) {
						if (role.equals("lead actress")) {
							//results += "...LEADING...";
							nomination_query.addFilter("Category", FilterOperator.EQUAL, "best lead actress");
						} else {
							//results += "...SUPPORTING...";
							nomination_query.addFilter("Category", FilterOperator.EQUAL, "best supporting actress");
						}
					} else {
						if (role.equals("lead actor")) {
							nomination_query.addFilter("Category", FilterOperator.EQUAL, "best lead actor");
						} else {
							nomination_query.addFilter("Category", FilterOperator.EQUAL, "best supporting actor");
						}
					}
					
					nomination_query.addFilter("Movie_Title", FilterOperator.EQUAL, e.getProperty("Movie_Title"));
					nomination_query.addFilter("Movie_Year", FilterOperator.EQUAL, e.getProperty("Release_Year"));
					nominations = datastore.prepare(nomination_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
					
					for (Entity ee : nominations) {
						results += ee.getProperty("Event") + ", " + ee.getProperty("Event_Year") + ", " + ee.getProperty("Category") + ", " + ee.getProperty("Won") + "; ";
					}
				}
			}
			if (results.length() > 2) {
				results = results.substring(0, results.length()-2);
			}
			
		} else if ( commandEls[0].equals("get_movies_of_genre_for_actor") ) {
			String actor_name = commandEls[1];
			String genre = commandEls[2];
			
			Query cast_query = new Query("Cast");
			Query movie_query = new Query("Movie");
			List<Entity> casts = null;
			List<Entity> movies = null;
			
			cast_query.addFilter("Actor_Name", FilterOperator.EQUAL, actor_name);
			casts = datastore.prepare(cast_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			//movie_query = new Query("Movie");
			//movie_query.addFilter("Genre", FilterOperator.EQUAL, genre);
			
			if (casts != null) {
				results = "";
				for (Entity e : casts) {
					movie_query = new Query("Movie");
					 
					movie_query.addFilter("Genre", FilterOperator.EQUAL, genre);
					movie_query.addFilter("Title", FilterOperator.EQUAL, e.getProperty("Movie_Title"));
					movie_query.addFilter("Release_Year", FilterOperator.EQUAL, e.getProperty("Release_Year"));
					movies = datastore.prepare(movie_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
					for (Entity ee : movies) {
						results += ee.getProperty("Title") + ", " + ee.getProperty("Release_Year") + "; ";
					}
				}
				results = results.substring(0, results.length()-2);
			}
		} else if ( commandEls[0].equals("get_number_of_nominations_for_movie") ) {
			String movie_title = commandEls[1];
			String release_year = commandEls[2];
			
			Query nomination_query = new Query("Nomination");
			List<Entity> nominations = null;
			
			nomination_query.addFilter("Movie_Title", FilterOperator.EQUAL, movie_title);
			nomination_query.addFilter("Movie_Year", FilterOperator.EQUAL, release_year);
			nominations = datastore.prepare(nomination_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			results = nominations.size() + "";
		} else if ( commandEls[0].equals("get_average_rating_for_movie") ) {
			String movie_title = commandEls[1];
			String release_year = commandEls[2];
			
			Query rating_query = new Query("Movie_Rating");
			List<Entity> ratings = null;
			
			rating_query.addFilter("Movie_Title", FilterOperator.EQUAL, movie_title);
			rating_query.addFilter("Release_Year", FilterOperator.EQUAL, release_year);
			ratings = datastore.prepare(rating_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			double score = 0.0;
			for (Entity e : ratings) {
				score += Integer.parseInt((String) e.getProperty("Rating"));
			}
			score /= ratings.size();
			results = score + "";
		} else if ( commandEls[0].equals("get_average_rating_of_user") ) {
			String user_id = commandEls[1];
			Query rating_query = new Query("Movie_Rating");
			List<Entity> ratings = null;
			rating_query.addFilter("User_Id", FilterOperator.EQUAL, user_id);
			ratings = datastore.prepare(rating_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			double score = 0.0;
			for (Entity e : ratings) {
				score += Integer.parseInt((String) e.getProperty("Rating"));
			}
			score /= ratings.size();
			results = score + ""; 
			
		} else if ( commandEls[0].equals("delete_company") ) {
			String company = commandEls[1];
			Query movie_query = new Query("Movie");
			List<Entity> movies = null;
			movie_query.addFilter("Company", FilterOperator.EQUAL, company);
			movies = datastore.prepare(movie_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			if (movies.size() == 0) { //delete
				Key companyKey = KeyFactory.createKey("Company", company);
				datastore.delete(companyKey);
				results = "Command executed	successfully!";	
			} else {
				results = "Referential integrity violation!";
			}
		} else if ( commandEls[0].equals("delete_user") ) {
			String user_id = commandEls[1];
			Query rating_query = new Query("Movie_Rating");
			List<Entity> ratings = null;
			rating_query.addFilter("User_Id", FilterOperator.EQUAL, user_id);
			ratings = datastore.prepare(rating_query).asList(FetchOptions.Builder.withLimit(Integer.MAX_VALUE));
			
			if (ratings.size() == 0) { //delete
				Key companyKey = KeyFactory.createKey("User", user_id);
				datastore.delete(companyKey);
				results = "Command executed	successfully!";
			} else {
				results = "Referential integrity violation!";
			}
		}

		/* your implementation ends here */

		resp.sendRedirect("/movies.jsp?moviedbName=Purdue&display=" + results);
	}  

}
