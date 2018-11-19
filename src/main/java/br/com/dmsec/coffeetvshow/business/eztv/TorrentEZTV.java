package br.com.dmsec.coffeetvshow.business.eztv;

import org.springframework.data.annotation.Id;

public class TorrentEZTV {

	

	//id : 1211379
	//hash : "fcdbab344b14938c881c704bf619f15759d7ae1e"
	//filename : "The.Sinner.S02E08.720p.WEB.x264-TBS[eztv].mkv"
	//episode_url : "https://eztv.re/ep/1211379/the-sinner-s02e08-720p-web-x264-tbs/"
	//torrent_url : "https://zoink.ch/torrent/The.Sinner.S02E08.720p.WEB.x264-TBS[eztv].mkv.torrent"
	//magnet_url : "magnet:?xt=urn:btih:fcdbab344b14938c881c704bf619f15759d7ae1e&dn=The.Sinner.S02E08.720p.WEB.x264-TBS%5Beztv%5D&tr=udp://tracker.coppersurfer.tk:80&tr=udp://glotorrents.pw:6969/announce&tr=udp://tracker.leechers-paradise.org:6969&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://exodus.desync.com:6969"
	//title : "The Sinner S02E08 720p WEB x264-TBS EZTV"
	//imdb_id : "6048596"
	//season : "2"
	//episode : "8"
	//small_screenshot : "//ezimg.ch/thumbs/the-sinner-s02e08-720p-web-x264-tbs-small.jpg"
	//large_screenshot : "//ezimg.ch/thumbs/the-sinner-s02e08-720p-web-x264-tbs-large.jpg"
	//seeds : 55
	//peers : 6
	//date_released_unix : 1537439224
	//size_bytes : "921961691"
	
	
	
	@Id
	private Long id;
	private String hash;
	private String filename;
	private String episodeUrl;
	private String torrentUrl;
	private String magnetUrl;
	private String title;
	private String imdbId;
	private int season;
	private int episode;
	private String seeds;
	private String peers;
	private String dateReleasedUnix;
	private String sizeBytes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getEpisodeUrl() {
		return episodeUrl;
	}
	public void setEpisodeUrl(String episodeUrl) {
		this.episodeUrl = episodeUrl;
	}
	public String getTorrentUrl() {
		return torrentUrl;
	}
	public void setTorrentUrl(String torrentUrl) {
		this.torrentUrl = torrentUrl;
	}
	public String getMagnetUrl() {
		return magnetUrl;
	}
	public void setMagnetUrl(String magnetUrl) {
		this.magnetUrl = magnetUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public int getEpisode() {
		return episode;
	}
	public void setEpisode(int episode) {
		this.episode = episode;
	}
	public String getSeeds() {
		return seeds;
	}
	public void setSeeds(String seeds) {
		this.seeds = seeds;
	}
	public String getPeers() {
		return peers;
	}
	public void setPeers(String peers) {
		this.peers = peers;
	}
	public String getDateReleasedUnix() {
		return dateReleasedUnix;
	}
	public void setDateReleasedUnix(String dateReleasedUnix) {
		this.dateReleasedUnix = dateReleasedUnix;
	}
	public String getSizeBytes() {
		return sizeBytes;
	}
	public void setSizeBytes(String sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	
	
}



