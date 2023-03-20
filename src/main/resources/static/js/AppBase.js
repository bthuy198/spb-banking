class AppBase {
    static DOMAIN_SERVER = location.origin;

    static API_SERVER = this.DOMAIN_SERVER + '/api';

    static API_CUSTOMER = this.API_SERVER + '/customers';
    static API_ALL_CUSTOMERS = this.API_SERVER + '/customers';
    static API_ALL_CUSTOMERS_DELETED = this.API_SERVER + '/customers/deleted';
    static API_DELETE_CUSTOMER = this.API_CUSTOMER + '/delete';


    static API_DEPOSIT = this.API_CUSTOMER + '/deposit';
    static API_WITHDRAW = this.API_CUSTOMER + '/withdraw';
    static API_TRANSFER = this.API_CUSTOMER + '/transfer';
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Customer {
    constructor(id, fullName, email, phone, locationRegion, balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
        this.balance = balance;
    }
}

// class TransferViewDTO {
//     constructor(id, senderName, recipientName, createdAt, transferAmount, feesAmount, totalAmount) {
//         this.id = id;
//         this.senderName = senderName;
//         this.recipientName = recipientName;
//         this.createdAt = createdAt;
//         this.transferAmount = transferAmount;
//         this.feesAmount = feesAmount;
//         this.totalAmount = totalAmount;
//     }
// }