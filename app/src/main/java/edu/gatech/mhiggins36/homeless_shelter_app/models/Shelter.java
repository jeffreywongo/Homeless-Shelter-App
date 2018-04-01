package edu.gatech.mhiggins36.homeless_shelter_app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeffreywongo on 2/27/2018.
 */

public class Shelter implements Parcelable{
    private int uniqueKey;
    private String name;
    private int capacity;
    private int vacancies;
    private  String restrictions;
    private float longitude;
    private float latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;

    public Shelter(int uniqueKey, String name, int capacity, int vacancies, String restrictions,
                   float longitude, float latitude, String address, String specialNotes,
                   String phoneNumber) {
        this.uniqueKey = uniqueKey;
        this.name = name;
        this.capacity = capacity;
        this.vacancies = vacancies;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    public int getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getVacancies() {return vacancies;}

    public void setVacancies(int vacancies) {this.vacancies = vacancies;}

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uniqueKey);
        parcel.writeString(this.name);
        parcel.writeInt(this.capacity);
        parcel.writeInt(this.vacancies);
        parcel.writeString(this.restrictions);
        parcel.writeFloat(this.longitude);
        parcel.writeFloat(this.latitude);
        parcel.writeString(this.address);
        parcel.writeString(this.specialNotes);
        parcel.writeString(this.phoneNumber);
    }

    // Parcelling part
    public Shelter(Parcel in){
        this.uniqueKey = in.readInt();
        this.name = in.readString();
        this.capacity = in.readInt();
        this.vacancies = in.readInt();
        this.restrictions = in.readString();
        this.longitude = in.readFloat();
        this.latitude = in.readFloat();
        this.address = in.readString();
        this.specialNotes = in.readString();
        this.phoneNumber = in.readString();
    }
}
