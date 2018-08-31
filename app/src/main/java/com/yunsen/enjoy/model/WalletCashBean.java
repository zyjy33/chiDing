package com.yunsen.enjoy.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/5/31.
 * 提现明细
 */

public class WalletCashBean {
    /**
     * id : 70049
     * serial_no : H171012205325532538
     * trade_no : null
     * order_no : B171012205315531589
     * from_user_id : 7025
     * from_user_name : T15220072935
     * from_previous : 7203.83
     * from_expense : 538
     * from_balance : 6665.83
     * to_user_id : null
     * to_user_name : null
     * to_previous : null
     * to_income : null
     * to_balance : null
     * fund_id : 1
     * platform_id : 1
     * payment_id : 2
     * consumer_id : 0
     * consumer_name : null
     * expenses_id : 0
     * company_id : 0
     * company_name : null
     * datatype : null
     * remark : 扣取账户金额
     * add_time : 2017-10-12 20:53:25
     * update_time : 2017-10-12 20:53:25
     */

    private int id;
    private String serial_no;
    private String trade_no;
    private String order_no;
    private String from_user_id;
    private String from_user_name;
    private String from_previous;
    private String from_expense;
    private String from_balance;
    private String to_user_id;
    private String to_user_name;
    private double to_previous;
    private double to_income;
    private double to_balance;
    private int fund_id;
    private int platform_id;
    private int payment_id;
    private int consumer_id;
    private String consumer_name;
    private int expenses_id;
    private int company_id;
    private String company_name;
    private String datatype;
    private String remark;
    private String add_time;
    private String update_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom_expenseStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(from_expense);
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public String getFrom_previous() {
        return from_previous;
    }

    public void setFrom_previous(String from_previous) {
        this.from_previous = from_previous;
    }

    public String getFrom_expense() {
        return from_expense;
    }

    public void setFrom_expense(String from_expense) {
        this.from_expense = from_expense;
    }

    public String getFrom_balance() {
        return from_balance;
    }

    public void setFrom_balance(String from_balance) {
        this.from_balance = from_balance;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getTo_user_name() {
        return to_user_name;
    }

    public void setTo_user_name(String to_user_name) {
        this.to_user_name = to_user_name;
    }

    public double getTo_previous() {
        return to_previous;
    }

    public void setTo_previous(double to_previous) {
        this.to_previous = to_previous;
    }

    public double getTo_income() {
        return to_income;
    }

    public void setTo_income(double to_income) {
        this.to_income = to_income;
    }

    public double getTo_balance() {
        return to_balance;
    }

    public void setTo_balance(double to_balance) {
        this.to_balance = to_balance;
    }

    public int getFund_id() {
        return fund_id;
    }

    public void setFund_id(int fund_id) {
        this.fund_id = fund_id;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public int getExpenses_id() {
        return expenses_id;
    }

    public void setExpenses_id(int expenses_id) {
        this.expenses_id = expenses_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}