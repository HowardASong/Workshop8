package com.example.workshop8;

public class TripTypes {
    private String TripTypeId;
    private String TTName;

    public TripTypes(String tripTypeId, String TTName) {
        TripTypeId = tripTypeId;
        this.TTName = TTName;
    }

    public String getTripTypeId() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        TripTypeId = tripTypeId;
    }

    public String getTTName() {
        return TTName;
    }

    public void setTTName(String TTName) {
        this.TTName = TTName;
    }

    @Override
    public String toString() {
        return getTTName();
    }
}
