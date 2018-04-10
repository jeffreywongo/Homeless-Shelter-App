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

    /**
     * shelter constructor
     * @param uniqueKey unique id
     * @param name name of the shelter
     * @param capacity number of beds that shelter can hold
     * @param vacancies number of beds available
     * @param restrictions sentence describing the shelter's restrictions
     * @param longitude location of shelter
     * @param latitude location of shelter
     * @param address address of the shelter
     * @param specialNotes notes for the shelter
     * @param phoneNumber phone number of shelter
     */
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

    /**
     *
     * @return the unique id of shelter
     */
    public int getUniqueKey() {
        return uniqueKey;
    }

    /**
     *
     * @param uniqueKey unique id of shelter
     */
    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    /**
     * get name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get capacity
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * set capacity
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * get vacancies
     * @return the vacancies
     */
    public int getVacancies() {return vacancies;}

    /**
     * set vacancies
     * @param vacancies the vacancies
     */
    public void setVacancies(int vacancies) {this.vacancies = vacancies;}

    /**
     * get restrictions
     * @return the restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * set restrictions
     * @param restrictions the restrictions
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * get longitude
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * set longitude
     * @param longitude the longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * get latitude
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * set latitude
     * @param latitude the latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * get address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get special notes
     * @return the special notes
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * set special notes
     * @param specialNotes the special notes
     */
    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    /**
     * get phone number
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set phone number
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param parcel the parcel
     * @param i the number
     */
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

    /**
     * the parceling constructor?
     * @param in idk fam
     */
    private Shelter(Parcel in){
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
