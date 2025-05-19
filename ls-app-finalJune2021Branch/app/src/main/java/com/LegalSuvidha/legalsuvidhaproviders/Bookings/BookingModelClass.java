package com.LegalSuvidha.legalsuvidhaproviders.Bookings;

public class BookingModelClass {
    String BusinessType;
    int PaymentAmount;
    String ServiceType;
    String TimestampPayment;
    String TimestampBooking;
    String PaymentStatus;
    String DocID;
    int ItemTotal;
    boolean CoinsUsed;
//    int CoinDiscount;
    int CoinEarned;
    int CoinDiscountUsed;


    public BookingModelClass() {
    }

    public BookingModelClass(String businessType, int paymentAmount, String serviceType, String timestampPayment, String DocID, String timestampBooking, String paymentStatus, int ItemTotal, boolean CoinsUsed, int coinEarned, int coinDiscountUsed) {
        this.BusinessType = businessType;
        this.PaymentAmount = paymentAmount;
        this.ServiceType = serviceType;
        this.DocID = DocID;
        this.TimestampPayment = timestampPayment;
        this.TimestampBooking = timestampBooking;
        this.PaymentStatus = paymentStatus;
        this.ItemTotal = ItemTotal;
        this.CoinsUsed = CoinsUsed;
//        this.CoinDiscount= CoinDiscount;
        this.CoinEarned = coinEarned;
        this.CoinDiscountUsed= coinDiscountUsed;
    }

    public BookingModelClass(String businessType, int paymentAmount, String serviceType, String timestampPayment, String timestampBooking, String paymentStatus) {
        this.BusinessType = businessType;
        this.PaymentAmount = paymentAmount;
        this.ServiceType = serviceType;
        this.TimestampPayment = timestampPayment;
        this.TimestampBooking = timestampBooking;
        this.PaymentStatus = paymentStatus;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public int getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getTimestampPayment() {
        return TimestampPayment;
    }

    public void setTimestampPayment(String timestampPayment) {
        TimestampPayment = timestampPayment;
    }

    public String getTimestampBooking() {
        return TimestampBooking;
    }

    public void setTimestampBooking(String timestampBooking) {
        TimestampBooking = timestampBooking;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public int getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(int itemTotal) {
        ItemTotal = itemTotal;
    }

    public boolean isCoinsUsed() {
        return CoinsUsed;
    }

    public void setCoinsUsed(boolean coinsUsed) {
        CoinsUsed = coinsUsed;
    }

//    public int getCoinDiscount() {
//        return CoinDiscount;
//    }
//
//    public void setCoinDiscount(int coinDiscount) {
//        CoinDiscount = coinDiscount;
//    }

    public int getCoinEarned() {
        return CoinEarned;
    }

    public void setCoinEarned(int coinEarned) {
        this.CoinEarned = coinEarned;
    }

    public int getCoinDiscountUsed() {
        return CoinDiscountUsed;
    }

    public void setCoinDiscountUsed(int coinDiscountUsed) {
        this.CoinDiscountUsed = coinDiscountUsed;
    }


}
