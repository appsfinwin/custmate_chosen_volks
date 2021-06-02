package com.finwin.chosenvolks.custmate.SupportingClass;

import com.finwin.chosenvolks.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionListResponse;
import com.finwin.chosenvolks.custmate.login.pojo.AccNo;

import java.util.ArrayList;

public class ConstantClass {

//    public static final String ip_global = "http://192.168.0.221:5363";
//    public static final String ip_global = "http://209.126.76.226:5363";
//    public static final String ip_global = "http://35.196.223.10:5363";
//    public static final String ip_global = "http://192.168.0.223:120";

      public static final String ip_global = "http://www.finwintechnologies.com:5363";
     // public static final String ip_global = "https://custmateuvnl.digicob.in";
    //public static final String ip_global = "http://192.168.225.221:81";
    //public static final String ip_global = "http://192.16.0.200:81";


    //public static String[] const_acc_nos_array;
    public static ArrayList<String> listAccountNumbers;
    public static String const_password="const_password";
    public static final String mstrType = "M_TYPE";
    public static String[] masterTypArray;
    public static String[] masterTypArrayID;
    public static TransactionListResponse transactionListResponse;
    public static ArrayList<String> listScheme;
    public static String pinforQR = "qrpass";
    public static String ACCOUNT_NUMBER = "accountNumber";
    public static String CUST_ID = "custId";
    public static String USERNAME = "userName";
    public static String PHONE_NUMBER = "phoneNumber";
    public static String MPIN_STATUS = "mpinStatus";
    public static String SCHEME = "SCHEME";
    public static ArrayList<AccNo> accountList;
    public  static  final String CURRENT_BALANCE = "CURRENT_BALANCE";
    public static String imageUrl = "http://chosenvolks.digicob.in/LogInDesign/images/";
    public static final String PREFS_DATA = "PrefesContent";
    //    public static String PREFS_MPIN = "";

//    public static String QR_SCND = "QRSCND";
//    public static String QR_ACCNO = "QRACCNO";

}
