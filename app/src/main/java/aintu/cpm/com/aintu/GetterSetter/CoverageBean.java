package aintu.cpm.com.aintu.GetterSetter;

import java.io.Serializable;

public class CoverageBean implements Serializable{
    String common_id,store_id,user_id,in_time,out_time,visit_date,lattitude,longitude,
            coverage_status,image,geo_tag,reason_id, remark,checkout_image,reason;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCoverage_status() {
        return coverage_status;
    }

    public void setCoverage_status(String coverage_status) {
        this.coverage_status = coverage_status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGeo_tag() {
        return geo_tag;
    }

    public void setGeo_tag(String geo_tag) {
        this.geo_tag = geo_tag;
    }

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckout_image() {
        return checkout_image;
    }

    public void setCheckout_image(String checkout_image) {
        this.checkout_image = checkout_image;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
