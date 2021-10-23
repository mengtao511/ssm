package com.qianfeng.shop.pojo;


public class TbGoodsType {

  private long id;
  private String name;
  private long level;
  private long parent;
  private long status;
  private String parentName;

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


  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }


  public long getParent() {
    return parent;
  }

  public void setParent(long parent) {
    this.parent = parent;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  @Override
  public String toString() {
    return "TbGoodsType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", level=" + level +
            ", parent=" + parent +
            ", status=" + status +
            ", parentName='" + parentName + '\'' +
            '}';
  }
}
