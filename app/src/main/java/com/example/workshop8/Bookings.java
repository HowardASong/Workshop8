package com.example.workshop8;

import java.util.Date;

public class Bookings {
    private int BookingId;
    private Date BookingDate;
    private String BookingNo;
    private int TravelerCount;
    private int CustomerId;
    private String TripTypeId;
    private int PackageId;

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingNo() {
        return BookingNo;
    }

    public void setBookingNo(String bookingNo) {
        BookingNo = bookingNo;
    }

    public int getTravelerCount() {
        return TravelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        TravelerCount = travelerCount;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getTripTypeId() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        TripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    public Bookings(int bookingId, Date bookingDate, String bookingNo, int travelerCount, int customerId, String tripTypeId, int packageId) {
        BookingId = bookingId;
        BookingDate = bookingDate;
        BookingNo = bookingNo;
        TravelerCount = travelerCount;
        CustomerId = customerId;
        TripTypeId = tripTypeId;
        PackageId = packageId;
    }
    public Bookings() {}

    @Override
    public String toString() {
        return "bookings{" +
                "BookingId=" + BookingId +
                ", BookingDate=" + BookingDate +
                ", BookingNo='" + BookingNo + '\'' +
                ", TravelerCount=" + TravelerCount +
                ", CustomerId=" + CustomerId +
                ", TripTypeId='" + TripTypeId + '\'' +
                ", PackageId=" + PackageId +
                '}';
    }
}
