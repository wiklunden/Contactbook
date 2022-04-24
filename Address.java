package se.miun.projekt;

/**
 * Class which is called when implementing a person's address
 * Uses all methods to form a complete home address along with a zipcode and city name
 */
public class Address {

    private String address;
    private String zipcode;
    private String city;

    public Address() {
        this("unknown", "unknown", "unknown");
    }

    public Address(String address, String zipcode, String city) {
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }
}
