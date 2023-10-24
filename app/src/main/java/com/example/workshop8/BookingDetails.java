package com.example.workshop8;

import java.util.Date;

public class BookingDetails {
    private int BookingDetailId;
    private int ItineraryNo;
    private Date TripStart;
    private Date TripEnd;
    private String Description;
    private String Destination;
    private int BasePrice;
    private int AgencyCommission;
    private int BookingId;
    private String RegionId;
    private String ClassId;
    private String FeeId;
    private int ProductsupplierId;

    public int getBookingDetailId() {
        return BookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        BookingDetailId = bookingDetailId;
    }

    public int getItineraryNo() {
        return ItineraryNo;
    }

    public void setItineraryNo(int itineraryNo) {
        ItineraryNo = itineraryNo;
    }

    public Date getTripStart() {
        return TripStart;
    }

    public void setTripStart(Date tripStart) {
        TripStart = tripStart;
    }

    public Date getTripEnd() {
        return TripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        TripEnd = tripEnd;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(int basePrice) {
        BasePrice = basePrice;
    }

    public int getAgencyCommission() {
        return AgencyCommission;
    }

    public void setAgencyCommission(int agencyCommission) {
        AgencyCommission = agencyCommission;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getFeeId() {
        return FeeId;
    }

    public void setFeeId(String feeId) {
        FeeId = feeId;
    }

    public int getProductsupplierId() {
        return ProductsupplierId;
    }

    public void setProductsupplierId(int productsupplierId) {
        ProductsupplierId = productsupplierId;
    }

    public BookingDetails(int bookingDetailId, int itineraryNo, Date tripStart, Date tripEnd, String description, String destination, int basePrice, int agencyCommission, int bookingId, String regionId, String classId, String feeId, int productsupplierId) {
        BookingDetailId = bookingDetailId;
        ItineraryNo = itineraryNo;
        TripStart = tripStart;
        TripEnd = tripEnd;
        Description = description;
        Destination = destination;
        BasePrice = basePrice;
        AgencyCommission = agencyCommission;
        BookingId = bookingId;
        RegionId = regionId;
        ClassId = classId;
        FeeId = feeId;
        ProductsupplierId = productsupplierId;
    }

    @Override
    public String toString() {
        return "BookingDetails{" +
                "BookingDetailId=" + BookingDetailId +
                ", ItineraryNo=" + ItineraryNo +
                ", TripStart=" + TripStart +
                ", TripEnd=" + TripEnd +
                ", Description='" + Description + '\'' +
                ", Destination='" + Destination + '\'' +
                ", BasePrice=" + BasePrice +
                ", AgencyCommission=" + AgencyCommission +
                ", BookingId=" + BookingId +
                ", RegionId='" + RegionId + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", FeeId='" + FeeId + '\'' +
                ", ProductsupplierId=" + ProductsupplierId +
                '}';
    }
}
