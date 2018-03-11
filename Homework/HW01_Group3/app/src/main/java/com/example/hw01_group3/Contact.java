package com.example.hw01_group3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by alexb on 2/9/2018.
 */

public class Contact implements Parcelable {

    private byte[] selfie;
    private String firstName, lastName, company, phone, email, basicURL, address,
    birthday, nickname, facebookURL, twitterURL, skypeURL, youtubeURL;

    public Contact(byte[] selfie, String firstName, String lastName, String company, String phone, String email, String basicURL,
                   String address, String birthday, String nickname, String facebookURL, String twitterURL, String skypeURL,
                   String youtubeURL) {
        this.selfie = selfie;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.basicURL = basicURL;
        this.address = address;
        this.birthday = birthday;
        this.nickname = nickname;
        this.facebookURL = facebookURL;
        this.twitterURL = twitterURL;
        this.skypeURL = skypeURL;
        this.youtubeURL = youtubeURL;
    }

    //PARCEL IMPLEMENTATION//
    protected Contact(Parcel in) {
        this.selfie = in.createByteArray();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.company = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.basicURL = in.readString();
        this.address = in.readString();
        this.birthday = in.readString();
        this.nickname = in.readString();
        this.facebookURL = in.readString();
        this.twitterURL = in.readString();
        this.skypeURL = in.readString();
        this.youtubeURL = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {return new Contact[size];}
    };

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(this.selfie);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.company);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.basicURL);
        dest.writeString(this.address);
        dest.writeString(this.birthday);
        dest.writeString(this.nickname);
        dest.writeString(this.facebookURL);
        dest.writeString(this.twitterURL);
        dest.writeString(this.skypeURL);
        dest.writeString(this.youtubeURL);
    }

    //toString//


    @Override
    public String toString() {
        return "Contact{" +
//                "selfie=" + Arrays.toString(selfie) +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", basicURL='" + basicURL + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nickname='" + nickname + '\'' +
                ", facebookURL='" + facebookURL + '\'' +
                ", twitterURL='" + twitterURL + '\'' +
                ", skypeURL='" + skypeURL + '\'' +
                ", youtubeURL='" + youtubeURL + '\'' +
                '}';
    }

    //GETTERS AND SETTERS//
    public byte[] getSelfie() {
        return selfie;
    }

    public void setSelfie(byte[] selfie) {
        this.selfie = selfie;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBasicURL() {
        return basicURL;
    }

    public void setBasicURL(String basicURL) {
        this.basicURL = basicURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getSkypeURL() {
        return skypeURL;
    }

    public void setSkypeURL(String skypeURL) {
        this.skypeURL = skypeURL;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }
}
