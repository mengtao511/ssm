package com.qianfeng.shop.pojo;


import java.util.Date;

public class TbOrder {

  private String id;//订单编号
  private int uid;//用户id
  private int money;//总金额
  private String status;//订单状态
  private Date time;//订单时间
  private int aid;
  private String detail;

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public int getAid() {
    return aid;
  }

  public void setAid(int aid) {
    this.aid = aid;
  }

  @Override
  public String toString() {
    return "TbOrder{" +
            "id='" + id + '\'' +
            ", uid=" + uid +
            ", money=" + money +
            ", status='" + status + '\'' +
            ", time=" + time +
            ", aid=" + aid +
            '}';
  }
}
