package model;

public class Level {
	
	private long idlv;
	private String levelName;
	
	public Level(){
		
	}
	
	public Level(String levelName){
		this.levelName = levelName;
	}
	
	public Level(long idlv, String levelName){
		this.idlv = idlv;
		this.levelName = levelName;
	}
	
	public long getIdlv() {
		return idlv;
	}
	
	public void setIdlv(long idlv) {
		this.idlv = idlv;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Override
	public String toString() {
		return "idlv = "+idlv+", levelName = "+levelName;
	}
	

}
