/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */

package br.com.dmsec.coffeetvshow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import br.com.dmsec.coffeetvshow.business.coffeetvshow.EpisodeCoffee;
import br.com.dmsec.coffeetvshow.business.eztv.TorrentEZTV;
import br.com.dmsec.coffeetvshow.business.plex.MediaContainer;
import br.com.dmsec.coffeetvshow.business.plex.User;
import br.com.dmsec.coffeetvshow.business.plex.Video;
import br.com.dmsec.coffeetvshow.business.tvshowtime.AccessToken;
import br.com.dmsec.coffeetvshow.business.tvshowtime.AuthorizationCode;
import br.com.dmsec.coffeetvshow.business.tvshowtime.EpisodeTST;
import br.com.dmsec.coffeetvshow.business.tvshowtime.Message;
import br.com.dmsec.coffeetvshow.business.tvshowtime.ShowTST;
import br.com.dmsec.coffeetvshow.config.PMSConfig;
import br.com.dmsec.coffeetvshow.config.TVShowTimeConfig;
import br.com.dmsec.coffeetvshow.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import static br.com.dmsec.coffeetvshow.util.Constants.MINUTE_IN_MILIS;
import static br.com.dmsec.coffeetvshow.util.Constants.PMS_WATCH_HISTORY;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_ACCESS_TOKEN_URI;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_AUTHORIZE_URI;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_CHECKIN_URI;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_CLIENT_ID;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_CLIENT_SECRET;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_RATE_REMAINING_HEADER;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_TO_WATCH;
import static br.com.dmsec.coffeetvshow.util.Constants.TVST_USER_AGENT;
import static br.com.dmsec.coffeetvshow.util.Constants.OMDB_URI;
import static br.com.dmsec.coffeetvshow.util.Constants.EZTV_URI;


import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;

@SpringBootApplication
@EnableScheduling
public class AppMain {

	private static final Logger LOG = LoggerFactory.getLogger(AppMain.class);

	@Autowired
	private TVShowTimeConfig tvShowTimeConfig;
	@Autowired
	private PMSConfig pmsConfig;

	private RestTemplate tvShowTimeTemplate;
	private RestTemplate pmsTemplate;
	private RestTemplate omdbTemplate;
	private RestTemplate eztvTemplate;
	private AccessToken accessToken;
	private Timer tokenTimer;

	//@Autowired
	//private EpisodeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);
	}

	@Scheduled(fixedDelay = Long.MAX_VALUE)
	public void init() {
		tvShowTimeTemplate = new RestTemplate();

		File storeToken = new File(tvShowTimeConfig.getTokenFile());
		if (storeToken.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(storeToken);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				accessToken = (AccessToken) objectInputStream.readObject();
				objectInputStream.close();
				fileInputStream.close();

				LOG.info("AccessToken loaded from file with success : " + accessToken);
			} catch (Exception e) {
				LOG.error("Error parsing the AccessToken stored in 'session_token'.");
				LOG.error("Please remove the 'session_token' file, and try again.");
				LOG.error(e.getMessage());

				System.exit(1);
			}
							//Original para marcar o que foi assistido.
			            try {
			                processWatchedEpisodes();
			            } catch (Exception e) {
			                LOG.error("Error during marking episodes as watched.");
			                LOG.error(e.getMessage());
			                System.exit(1);
			            }

//			try {
//				List<EpisodeCoffee> listEpisodeToWatch = loadToWatchTST();
//				List<EpisodeCoffee> listEpisodeToDownload = findTorrentURL(listEpisodeToWatch);
//				LOG.info("Retornei para o init");
//
//
//			}catch(Exception e) {
//				LOG.error(e.getMessage());
//			}

		} else {
			requestAccessToken();
		}
	}

	private List<EpisodeCoffee> loadToWatchTST() throws JSONException, ParseException {


		List<EpisodeCoffee> listEpisodesCoffee = new ArrayList<EpisodeCoffee>();


		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", TVST_USER_AGENT);
		HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

		String toWatchURL = new StringBuilder(TVST_TO_WATCH)
				.append("?access_token=")
				.append(accessToken.getAccess_token())
				.append("&limit=300")
				.toString();

		ResponseEntity<String> content = tvShowTimeTemplate.exchange(toWatchURL, GET, entity, String.class);
		String message = content.getBody();


		if (content.getStatusCode() == HttpStatus.OK) {
			LOG.info("Message"+ message);

			JSONObject jobj = new JSONObject(message); 
			JSONArray jsonarr_episodes = (JSONArray) jobj.get("episodes"); 


			//Get data for Results array
			for(int i=0;i<jsonarr_episodes.length();i++){

				EpisodeCoffee episodeCoffee = new EpisodeCoffee();
				//Store the JSON objects in an array
				//Get the index of the JSON object and print the values as per the index
				JSONObject jsonobj_episode = (JSONObject)jsonarr_episodes.get(i);

				EpisodeTST episodeTST = new EpisodeTST();
				episodeTST.setId(Long.valueOf(jsonobj_episode.getString("id")));
				episodeTST.setName(jsonobj_episode.getString("name"));
				episodeTST.setNumber(Integer.valueOf(jsonobj_episode.getString("number")));
				episodeTST.setSeason_number(Integer.valueOf(jsonobj_episode.getString("season_number")));
				episodeTST.setAirDate(jsonobj_episode.getString("air_date"));
				episodeTST.setAirTime(jsonobj_episode.getString("air_time"));
				episodeTST.setNetwork(jsonobj_episode.getString("network"));
				episodeTST.setOverview(jsonobj_episode.getString("overview"));
				episodeTST.setNbComments(jsonobj_episode.getString("nb_comments"));
				episodeTST.setIs_new(jsonobj_episode.getString("is_new"));
				episodeTST.setSeen(jsonobj_episode.getString("seen"));
				episodeTST.setPreviousEpisode(jsonobj_episode.getString("previous_episode"));
				episodeTST.setNextEpisode(jsonobj_episode.getString("next_episode"));


				//Load Show
				JSONObject jsonobj_show = jsonobj_episode.getJSONObject("show");
				ShowTST showTST = new ShowTST();
				showTST.setId(Long.valueOf(jsonobj_show.getString("id")));
				showTST.setName(jsonobj_show.getString("name"));
				showTST.setSeenEpisodes(jsonobj_show.getString("seen_episodes"));
				showTST.setAiredEpisodes(jsonobj_show.getString("aired_episodes"));
				showTST.setGenre(jsonobj_show.getString("genre"));
				showTST.setHashtag(jsonobj_show.getString("hashtag"));

				//Call the OMDBAPI 
				String imdb_id = requestIMDBID(showTST.getName());
				showTST.setImdbId(imdb_id.replaceAll("tt", ""));

				//add show to episode
				episodeTST.setShow(showTST);


				//Add EpisodeTST to EpisodeCoffee
				episodeCoffee.setEpisodeTST(episodeTST);

				//Included EpisodeCoffee to a list
				listEpisodesCoffee.add(episodeCoffee);

			}

		} else {
			LOG.error("Error while querying TVShowTime ");
		}

		return listEpisodesCoffee;
	}


	/**
	 * //CALL the EZTV - Find link magnet Eztv
	 * @param listEpisodeToWatch
	 * @return
	 * @throws JSONException
	 */
	private List<EpisodeCoffee> findTorrentURL(List<EpisodeCoffee> listEpisodeToWatch) throws JSONException {
		
		List<EpisodeCoffee> listEpisodeToDownload = new ArrayList<EpisodeCoffee>();
		

		eztvTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.add("user-agent", TVST_USER_AGENT);
		HttpEntity<String> entity = new HttpEntity<>("parameters",headers);


		//episode.setLinkMagnet(linkMagnet);
		Iterator<EpisodeCoffee> it = listEpisodeToWatch.iterator();


		/**
		 * TO-DO Se tem mais de 100 no resultado.. devo chamar novamente, portanto melhorar o codigo abaixo
		 */
		while(it.hasNext()) {
			EpisodeCoffee episodeCoffee = it.next();
			LOG.info("IMDB to find on EZTV: " + episodeCoffee.getEpisodeTST().getShow().getImdbId());


			try {
				ResponseEntity<String> content_1 = requestEZTV(episodeCoffee.getEpisodeTST().getShow().getImdbId(), 1, entity);
				String message_1 = content_1.getBody();
				JSONObject jsonobj_eztv_1 = new JSONObject(message_1);
				int torrents_count_1 = jsonobj_eztv_1.getInt("torrents_count");
				JSONArray jsonarr_torrents_1 = (JSONArray) jsonobj_eztv_1.get("torrents");
				
				for(int i=0;i<jsonarr_torrents_1.length();i++){
					JSONObject jsonobj_auxiliar = (JSONObject)jsonarr_torrents_1.get(i);
					TorrentEZTV torrentEZTV = jsonEZTVtoBean(jsonobj_auxiliar);
					if(episodeCoffee.getEpisodeTST().getSeason_number() == torrentEZTV.getEpisode() && episodeCoffee.getEpisodeTST().getNumber() == torrentEZTV.getEpisode() ){
						episodeCoffee.setTorrent(torrentEZTV);
						listEpisodeToDownload.add(episodeCoffee);
						LOG.info("Add: "+ episodeCoffee.getEpisodeTST().getShow().getName());
					}
					
				}

				if(torrents_count_1 > 100) {
					ResponseEntity<String> content_2 = requestEZTV(episodeCoffee.getEpisodeTST().getShow().getImdbId(), 2, entity);
					String message_2 = content_2.getBody();
					JSONObject jsonobj_eztv_2 = new JSONObject(message_2);
					//int torrents_count_2 = jsonobj_eztv_2.getInt("torrents_count");
					JSONArray jsonarr_torrents_2 = (JSONArray) jsonobj_eztv_2.get("torrents");
					
					for(int i=0;i<jsonarr_torrents_2.length();i++){
						JSONObject jsonobj_auxiliar = (JSONObject)jsonarr_torrents_2.get(i);
						TorrentEZTV torrentEZTV = jsonEZTVtoBean(jsonobj_auxiliar);
						if(episodeCoffee.getEpisodeTST().getSeason_number() == torrentEZTV.getEpisode() && episodeCoffee.getEpisodeTST().getNumber() == torrentEZTV.getEpisode() ){
							episodeCoffee.setTorrent(torrentEZTV);
							listEpisodeToDownload.add(episodeCoffee);
							LOG.info("Add: "+ episodeCoffee.getEpisodeTST().getShow().getName());
						}
					}

					
				}


			}catch(Exception e) {
				LOG.error("Error - findTorrentURL: "+ e.getMessage());
				if(it.hasNext()) {
					it.next();
					continue;	
				}else {
					continue;
				}

			}

		}

		return listEpisodeToDownload;
	}


	private ResponseEntity<String> requestEZTV(String imdb_id, int page, HttpEntity<String> entity){

		String eztv_uri = new StringBuilder(EZTV_URI)
				.append(imdb_id)
				.append("&limit=100")
				.append("&page="+page)
				.toString();

		ResponseEntity<String> content = eztvTemplate.exchange(eztv_uri, GET, entity, String.class);
		return content;
	}

	
	private TorrentEZTV jsonEZTVtoBean(JSONObject jsonobj_eztv) throws NumberFormatException, JSONException {
		
		TorrentEZTV torrentEZTV = new TorrentEZTV();
		torrentEZTV.setId(Long.valueOf(jsonobj_eztv.getString("id")));
		torrentEZTV.setHash(jsonobj_eztv.getString("hash"));
		torrentEZTV.setFilename(jsonobj_eztv.getString("filename"));
		torrentEZTV.setEpisodeUrl(jsonobj_eztv.getString("episode_url"));
		torrentEZTV.setTorrentUrl(jsonobj_eztv.getString("torrent_url"));
		torrentEZTV.setMagnetUrl(jsonobj_eztv.getString("magnet_url"));
		torrentEZTV.setTitle(jsonobj_eztv.getString("title"));
		torrentEZTV.setImdbId(jsonobj_eztv.getString("imdb_id"));
		torrentEZTV.setSeason(jsonobj_eztv.getInt("season"));
		torrentEZTV.setEpisode(jsonobj_eztv.getInt("episode"));
		torrentEZTV.setSeeds(jsonobj_eztv.getString("seeds"));
		torrentEZTV.setPeers(jsonobj_eztv.getString("peers"));
		torrentEZTV.setDateReleasedUnix(jsonobj_eztv.getString("date_released_unix"));
		torrentEZTV.setSizeBytes(jsonobj_eztv.getString("size_bytes"));
		
		return torrentEZTV;
	}

	private String requestIMDBID(String name) throws JSONException {
		omdbTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

		String omdb_uri = new StringBuilder(OMDB_URI)
				.append("t=")
				.append(parserNameEpisode(name))
				.toString();


		LOG.info("FIND the IMDB_ID: "+ name);
		String imdbId = "";
		try {
			ResponseEntity<String> content = omdbTemplate.exchange(omdb_uri, GET, entity, String.class);
			String message = content.getBody();

			JSONObject jsonobj_omdb = new JSONObject(message); 
			imdbId = jsonobj_omdb.getString("imdbID");
			LOG.info("FIND the IMDB_ID: "+ name +" founded: "+ imdbId);
			
		}catch(Exception e) {
			LOG.error("Error requestIMDBID: "+ e.getMessage());
		}
		

		return imdbId;

	}

	private String parserNameEpisode(String name) {

		try {
			String nameAux = name.replace(")", ";").replace("(", ";");
			String[] aux = nameAux.split(";");

			return aux[0].replaceAll(" ", "+");	
		}catch(StringIndexOutOfBoundsException e) {
			return name.replaceAll(" " , "+");
		}

	}

	private void requestAccessToken() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<String> entity = new HttpEntity<>("client_id=" + TVST_CLIENT_ID, headers);

			ResponseEntity<AuthorizationCode> content = tvShowTimeTemplate.exchange(TVST_AUTHORIZE_URI, POST, entity,
					AuthorizationCode.class);
			AuthorizationCode authorizationCode = content.getBody();

			if (authorizationCode.getResult().equals("OK")) {
				LOG.info("Linking with your TVShowTime account using the code " + authorizationCode.getDevice_code());
				LOG.info("Please open the URL " + authorizationCode.getVerification_url() + " in your browser");
				LOG.info("Connect with your TVShowTime account and type in the following code : ");
				LOG.info(authorizationCode.getUser_code());
				LOG.info("Waiting for you to type in the code in TVShowTime :-D ...");

				tokenTimer = new Timer();
				tokenTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						loadAccessToken(authorizationCode.getDevice_code());
					}
				}, 1000 * authorizationCode.getInterval(), 1000 * authorizationCode.getInterval());
			} else {
				LOG.error("OAuth authentication TVShowTime failed.");

				System.exit(1);
			}
		} catch (Exception e) {
			LOG.error("OAuth authentication TVShowTime failed.");
			LOG.error(e.getMessage());

			System.exit(1);
		}
	}

	private void loadAccessToken(String deviceCode) {
		String query = new StringBuilder("client_id=")
				.append(TVST_CLIENT_ID)
				.append("&client_secret=")
				.append(TVST_CLIENT_SECRET)
				.append("&code=")
				.append(deviceCode)
				.toString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<>(query, headers);

		ResponseEntity<AccessToken> content = tvShowTimeTemplate.exchange(TVST_ACCESS_TOKEN_URI, POST, entity,
				AccessToken.class);
		accessToken = content.getBody();

		if (accessToken.getResult().equals("OK")) {
			LOG.info("AccessToken from TVShowTime with success : " + accessToken);
			tokenTimer.cancel();

			storeAccessToken();
			processWatchedEpisodes();
		} else {
			if (!accessToken.getMessage().equals("Authorization pending")
					&& !accessToken.getMessage().equals("Slow down")) {
				LOG.error("Unexpected error did arrive, please reload the service :-(");
				tokenTimer.cancel();

				System.exit(1);
			}
		}
	}

	private void storeAccessToken() {
		try {
			File storeToken = new File(tvShowTimeConfig.getTokenFile());
			FileOutputStream fileOutputStream = new FileOutputStream(storeToken);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(accessToken);
			objectOutputStream.close();
			fileOutputStream.close();

			LOG.info("AccessToken store successfully inside a file...");
		} catch (Exception e) {
			LOG.error("Unexpected error did arrive when trying to store the AccessToken in a file ");
			LOG.error(e.getMessage());

			System.exit(1);
		}
	}

	private void processWatchedEpisodes() {
		pmsTemplate = new RestTemplate();
		String watchHistoryUrl = pmsConfig.getPath() + PMS_WATCH_HISTORY;

		if (pmsConfig.getToken() != null && !pmsConfig.getToken().isEmpty()) {
			watchHistoryUrl += "?X-Plex-Token=" + pmsConfig.getToken();
			LOG.info("Calling Plex with a X-Plex-Token...");
		}

		ResponseEntity<MediaContainer> response =  pmsTemplate.getForEntity(watchHistoryUrl, MediaContainer.class);
		MediaContainer mediaContainer = response.getBody();

		if(mediaContainer.getVideo() != null) { 

			for (Video video : mediaContainer.getVideo()) {
				LocalDateTime date = DateUtils.getDateTimeFromTimestamp(video.getViewedAt());

				// Mark as watched only episodes for configured user
				if (pmsConfig.getUsername() != null && video.getUser() != null) {
					List<User> users = video.getUser().stream().filter(user -> user.getName().equals(pmsConfig.getUsername())).collect(Collectors.toList());

					if (users.stream().count() == 0) {
						continue;
					}
				}

				// Mark as watched only today and yesterday episodes
				if (DateUtils.isTodayOrYesterday(date) || pmsConfig.getMarkall() == true) {
					if (video.getType().equals("episode")) {
						String episode = new StringBuilder(video.getGrandparentTitle())
								.append(" - S")
								.append(video.getParentIndex())
								.append("E").append(video.getIndex())
								.toString();

						markEpisodeAsWatched(episode);
					} else {
						continue;
					}
				} else {
					LOG.info("All episodes are processed successfully ...");
					System.exit(0);
				}
			}


		}else {
			LOG.info("MediaContainer = 0");
			System.exit(0);
		}
	}

	private void markEpisodeAsWatched(String episode) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("User-Agent", TVST_USER_AGENT);
		HttpEntity<String> entity = new HttpEntity<>("filename=" + episode, headers);

		String checkinUrl = new StringBuilder(TVST_CHECKIN_URI)
				.append("?access_token=")
				.append(accessToken.getAccess_token())
				.toString();

		ResponseEntity<Message> content = tvShowTimeTemplate.exchange(checkinUrl, POST, entity, Message.class);
		Message message = content.getBody();

		if (message.getResult().equals("OK")) {
			LOG.info("Mark " + episode + " as watched in TVShowTime");
		} else {
			LOG.error("Error while marking [" + episode + "] as watched in TVShowTime ");
		}

		// Check if we are below the Rate-Limit of the API
		int remainingApiCalls = Integer.parseInt(content.getHeaders().get(TVST_RATE_REMAINING_HEADER).get(0));
		if (remainingApiCalls == 0) {
			try {
				LOG.info("Consumed all available TVShowTime API calls slots, waiting for new slots ...");
				Thread.sleep(MINUTE_IN_MILIS);
			} catch (Exception e) {
				LOG.error(e.getMessage());

				System.exit(1);
			}
		}
	}
}
