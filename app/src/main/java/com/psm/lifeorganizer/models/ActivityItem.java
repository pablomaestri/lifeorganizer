package com.psm.lifeorganizer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.psm.lifeorganizer.models.Enum.QuantityType;

import java.util.Date;

/**
 * Created by pmaestri on 1/30/2018.
 */

@DatabaseTable(tableName = "activity_item")
public class ActivityItem implements Parcelable {

    public ActivityItem() {

    }

    protected ActivityItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        quantity = in.readString();
        priceUnit = in.readDouble();
        //completed = in.rea
        //createdDate = in.read

    }

    public static final Creator<ActivityItem> CREATOR = new Creator<ActivityItem>() {
        @Override
        public ActivityItem createFromParcel(Parcel in) {
            return new ActivityItem(in);
        }

        @Override
        public ActivityItem[] newArray(int size) {
            return new ActivityItem[size];
        }
    };

    @JsonIgnore
    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "name",required = true)
    private String name;
    @DatabaseField(canBeNull = true)
    @JsonProperty(value = "description",required = false)
    private String description;
    @DatabaseField(canBeNull = true)
    @JsonProperty(value = "quantity",required = false)
    private String quantity;
    @DatabaseField(canBeNull = true)
    @JsonProperty(value = "priceUnit",required = false)
    private Double priceUnit;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "completed",required = true)
    private boolean completed;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "createdDate",required = false)
    private Date createdDate;
    @DatabaseField(canBeNull = false)
    @JsonProperty(value = "modDate",required = false)
    private Date modDate;
    @DatabaseField(canBeNull = false, foreign = true)
    private Activity activity;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
        dest.writeString(quantity);
        dest.writeDouble(priceUnit);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityItem item = (ActivityItem) o;

        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
