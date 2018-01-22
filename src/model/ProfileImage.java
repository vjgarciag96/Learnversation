package model;

public class ProfileImage {
	
	private long idi;
	private String imageName;
	
	public ProfileImage(long idi, String imageName){
		this.setIdi(idi);
		this.setImageName(imageName);
	}

	/**
	 * @return the idi
	 */
	public long getIdi() {
		return idi;
	}

	/**
	 * @param idi the idi to set
	 */
	public void setIdi(long idi) {
		this.idi = idi;
	}

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
