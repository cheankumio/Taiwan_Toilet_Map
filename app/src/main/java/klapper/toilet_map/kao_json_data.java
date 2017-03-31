package klapper.toilet_map;

/**
 * Created by c1103304 on 2017/3/31.
 */

public class kao_json_data {
    public kao_json_data(String country, String city, String village, String number, String administration, String name, String address, String type, String longitude, String grade, String latitude) {
        Country = country;
        City = city;
        Village = village;
        Number = number;
        Administration = administration;
        Name = name;
        Address = address;
        Type = type;
        Longitude = longitude;
        Grade = grade;
        Latitude = latitude;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getAdministration() {
        return Administration;
    }

    public void setAdministration(String administration) {
        Administration = administration;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    String Country;
    String City;
    String Village;
    String Number;
    String Administration;
    String Name;
    String Address;
    String Type;
    String Longitude;
    String Grade;
    String Latitude;

}
