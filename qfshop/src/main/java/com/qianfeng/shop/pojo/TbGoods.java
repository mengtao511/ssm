package com.qianfeng.shop.pojo;


import java.util.Date;

public class TbGoods {

  private long   id;
  private String name;
  private Date   pubdate;
  private String picture;
  private long   price;
  private long   star;
  private String intro;
  private long   typeid;

  private int kzid;

  public int getKzid() {
    return kzid;
  }

  public void setKzid(int kzid) {
    this.kzid = kzid;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getPubdate() {
    return pubdate;
  }

  public void setPubdate(Date pubdate) {
    this.pubdate = pubdate;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public long getStar() {
    return star;
  }

  public void setStar(long star) {
    this.star = star;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public long getTypeid() {
    return typeid;
  }

  public void setTypeid(long typeid) {
    this.typeid = typeid;
  }

  @Override
  public String toString() {
    return "TbGoods{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", pubdate=" + pubdate +
            ", picture='" + picture + '\'' +
            ", price=" + price +
            ", star=" + star +
            ", intro='" + intro + '\'' +
            ", typeid=" + typeid +
            '}';
  }


}
