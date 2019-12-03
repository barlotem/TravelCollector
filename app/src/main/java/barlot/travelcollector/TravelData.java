package barlot.travelcollector;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER on 25-Nov-19.
 */

public class TravelData implements Parcelable {

    SimpleDateFormat dateFormmater = new SimpleDateFormat("dd-MM-yyyy");

    private int albumId = 0;
    private Date date = null;
    private String groupName = "";
    private String guideName = "";
    private String description = "";
    private double distanceInKm = 0;
    private String tags = "";
    private String alternative = "";
    private String country = "";
    private String comments = "";
    private String link = "";
    private String link2 = "";
    private String linkGoPlus = "";

    public TravelData(int albumId, Date date, String groupName, String guideName, String description, double distanceInKm, String tags, String alternative, String country, String comments, String link, String link2, String linkGoPlus) {
        this.albumId = albumId;
        this.date = date;
        this.groupName = groupName;
        this.guideName = guideName;
        this.description = description;
        this.distanceInKm = distanceInKm;
        this.tags = tags;
        this.alternative = alternative;
        this.country = country;
        this.comments = comments;
        this.link = link;
        this.link2 = link2;
        this.linkGoPlus = linkGoPlus;
    }

    public TravelData(Parcel in) throws ParseException {
        this.albumId = in.readInt();
        this.date = dateFormmater.parse(in.readString());
        this.groupName =  in.readString();
        this.guideName =  in.readString();
        this.description =  in.readString();
        this.distanceInKm =  in.readDouble();
        this.tags =  in.readString();
        this.alternative =  in.readString();
        this.country =  in.readString();
        this.comments =  in.readString();
        this.link = in.readString();
        this.link2 =  in.readString();
        this.linkGoPlus = in.readString();
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getgroupName() {
        return groupName;
    }

    public void setgroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getLinkGoPlus() {
        return linkGoPlus;
    }

    public void setLinkGoPlus(String linkGoPlus) {
        this.linkGoPlus = linkGoPlus;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(albumId);
        dest.writeString(dateFormmater.format(date));
        dest.writeString(groupName);
        dest.writeString(guideName);
        dest.writeString(description);
        dest.writeDouble(distanceInKm);
        dest.writeString(tags);
        dest.writeString(alternative);
        dest.writeString(country);
        dest.writeString(comments);
        dest.writeString(link);
        dest.writeString(link2);
        dest.writeString(linkGoPlus);
    }

    public static final Parcelable.Creator<TravelData> CREATOR = new Parcelable.Creator<TravelData>() {
        @Override
        public TravelData createFromParcel(Parcel in) {
            try {
                return new TravelData(in);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public TravelData[] newArray(int size) {
            return new TravelData[size];
        }
    };
}
