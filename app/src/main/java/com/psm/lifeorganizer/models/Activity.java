package com.psm.lifeorganizer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.psm.lifeorganizer.models.Enum.ActivityStyle;
import com.psm.lifeorganizer.models.Enum.ActivityType;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by pmaestri on 1/30/2018.
 */

@DatabaseTable(tableName = "activity")
public class Activity implements Parcelable {

    public Activity() {

    }

    protected Activity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        //activityDate
        //items = in.readArrayList();
        //completed
        //activityStyle
        //activityType
        color = in.readString();
        //completed = in.rea
        //createdDate = in.read

    }

    @JsonIgnore
    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "name",required = true)
    private String name;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "description",required = false)
    private String description;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "activityDate",required = true)
    private Date activityDate;
    //http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Foreign-Collection
    @JsonProperty(value = "items",required = true)
    List<ActivityItem> items;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "completed",required = true)
    private boolean completed;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "activityStyle",required = true)
    private ActivityStyle activityStyle;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "activityType",required = true)
    private ActivityType activityType;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "color",required = true)
    private String color;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "createdDate",required = false)
    private Date createdDate;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "modDate",required = false)
    private Date modDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public List<ActivityItem> getItems() {
        return items;
    }

    public void setItems(List<ActivityItem> items) {
        this.items = items;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ActivityStyle getActivityStyle() {
        return activityStyle;
    }

    public void setActivityStyle(ActivityStyle activityStyle) {
        this.activityStyle = activityStyle;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(color);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
