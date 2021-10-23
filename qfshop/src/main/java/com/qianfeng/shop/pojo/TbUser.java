package com.qianfeng.shop.pojo;


public class TbUser {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String gender;
  private Long flag;//使用包装类型的原因是因为默认值是 null,而我们的数据有 0 这个值,long默认是 0,如果值是0 无法判断是没有值还是就是 0
  private Long role;
  private String code;
  private String nickname;


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Long getFlag() {
    return flag;
  }

  public void setFlag(Long flag) {
    this.flag = flag;
  }

  public Long getRole() {
    return role;
  }

  public void setRole(Long role) {
    this.role = role;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "TbUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", gender='" + gender + '\'' +
            ", flag=" + flag +
            ", role=" + role +
            ", code='" + code + '\'' +
            ", nickname='" + nickname + '\'' +
            '}';
  }
}
