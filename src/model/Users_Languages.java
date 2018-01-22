package model;

public class Users_Languages {
	
	private long idu;
	private long idl;
	private long speakingLevel;
	private long writingLevel;
	private long listeningLevel;
	private long readingLevel;
	
	public Users_Languages(){
		
	}
	
	public Users_Languages(long idu, long idl, long speakingLevel, long writingLevel, long listeningLevel, long readingLevel){
		this.idu = idu;
		this.idl = idl;
		this.speakingLevel = speakingLevel;
		this.writingLevel = writingLevel;
		this.listeningLevel = listeningLevel;
		this.readingLevel = readingLevel;
	}
	
	public Users_Languages(long speakingLevel, long writingLevel, long listeningLevel, long readingLevel){
		this.setSpeakingLevel(speakingLevel);
		this.setWritingLevel(writingLevel);
		this.setListeningLevel(listeningLevel);
		this.setReadingLevel(readingLevel);
	}
	
	public long getIdu() {
		return idu;
	}
	public void setIdu(long idu) {
		this.idu = idu;
	}
	
	public long getIdl() {
		return idl;
	}
	public void setIdl(long idl) {
		this.idl = idl;
	}

	/**
	 * @return the speakingLevel
	 */
	public long getSpeakingLevel() {
		return speakingLevel;
	}

	/**
	 * @param speakingLevel the speakingLevel to set
	 */
	public void setSpeakingLevel(long speakingLevel) {
		this.speakingLevel = speakingLevel;
	}

	/**
	 * @return the writingLevel
	 */
	public long getWritingLevel() {
		return writingLevel;
	}

	/**
	 * @param writingLevel the writingLevel to set
	 */
	public void setWritingLevel(long writingLevel) {
		this.writingLevel = writingLevel;
	}

	/**
	 * @return the listeningLevel
	 */
	public long getListeningLevel() {
		return listeningLevel;
	}

	/**
	 * @param listeningLevel the listeningLevel to set
	 */
	public void setListeningLevel(long listeningLevel) {
		this.listeningLevel = listeningLevel;
	}
	
	/**
	 * 
	 */
	public long getReadingLevel() {
		return readingLevel;
	}

	/**
	 * @param listeningLevel the listeningLevel to set
	 */
	public void setReadingLevel(long readingLevel) {
		this.readingLevel = readingLevel;
	}
	
	


}
