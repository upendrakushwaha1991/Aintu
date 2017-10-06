package aintu.cpm.com.aintu.GetterSetter;

public class GeotaggingBeans {


	public String common_id;
	public String store_id;
	public String status;
	public String image;
	public String Latitude;
	public String Longitude;

	public String getGeotag_data() {
		return geotag_data;
	}

	public void setGeotag_data(String geotag_data) {
		this.geotag_data = geotag_data;
	}

	public String geotag_data;


	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getCommon_id() {
		return common_id;
	}

	public void setCommon_id(String common_id) {
		this.common_id = common_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


}