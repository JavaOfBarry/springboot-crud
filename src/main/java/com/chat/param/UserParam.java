package com.chat.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class UserParam {

	private Integer id;
	@NotEmpty(message="姓名不能为空")
	private String userName;
	@NotEmpty(message="密码不能为空")
	@Length(min=6,message="密码长度不能小于6位")
	private String passWord;
	@Max(value = 100, message = "年龄不能大于100岁")
	@Min(value= 18 ,message= "必须年满18岁！" )
	private int age;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

}
