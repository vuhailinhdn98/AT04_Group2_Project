package models;

import java.util.Objects;

public class UserInfo {
    private String fullName, email, phoneNumber, address, password;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo(String fullName, String email, String phoneNumber, String address, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(fullName, userInfo.fullName) && Objects.equals(email, userInfo.email) && Objects.equals(phoneNumber, userInfo.phoneNumber) && Objects.equals(address, userInfo.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, phoneNumber, address);
    }
}
