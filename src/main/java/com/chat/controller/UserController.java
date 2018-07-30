package com.chat.controller;

import com.chat.Entity.User;
import com.chat.param.UserParam;
import com.chat.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index(){
		return "redirect:/getlist";
	}
	@GetMapping("/getlist")
	public String list(Model model,
			@RequestParam(value = "page",defaultValue = "0")Integer page,
			@RequestParam(value = "size",defaultValue = "6")Integer size){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		Pageable pageable = new PageRequest(page,size,sort);
		Page<User> users = userService.findList(pageable);
		model.addAttribute("users",users);
		return "list.html";
	}

	@RequestMapping("/toAdd")
	public String toAdd(){
		return "userAdd";
	}

	@RequestMapping("/add")
	public String add(@Valid UserParam userParam,BindingResult result, @RequestParam(value="file")MultipartFile file,  ModelMap modelMap){
		String errorMsg = "";
		String destFileName= "";
		String fileName ="";
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				errorMsg = errorMsg + error.getCode() + "-" + error.getDefaultMessage() + ";";
			}
			modelMap.addAttribute("errorMsg",errorMsg);
			return "userAdd";
		}
		User user = userService.findByUserName(userParam.getUserName());
		if(user != null){
			modelMap.addAttribute("errorMsg","用户已存在");
			return "userAdd";
		}
		//上传图片
		if (!file.isEmpty()) {
			try {
				fileName = System.currentTimeMillis()+file.getOriginalFilename();
				destFileName="E:\\uploaded"+File.separator+fileName;

				File destFile = new File(destFileName);
				destFile.getParentFile().mkdirs();
				file.transferTo(destFile);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			}
		}
		User user1 = new User();
		BeanUtils.copyProperties(userParam,user1);
		user1.setRegTime(new Date());
		user1.setDestFileName("/advertIMG/"+fileName);
		userService.addUser(user1);
		return "redirect:/getlist";
	}


	@RequestMapping("/toEdit")
	public String toEdit(Model model, Integer id){
		User user = userService.findById(id);
		model.addAttribute("user",user);
		return "userEdit";
	}

	/**
	 * 修改完成后提交到后台
	 */
	@RequestMapping("/edit")
	public String edit(@Valid UserParam userParam,BindingResult result,@RequestParam(value="file")MultipartFile file,ModelMap modelMap){
		String errorMsg = "";
		String destFileName= "";
		String fileName ="";
		User user1 = userService.findById(userParam.getId());
		User user = new User();
		BeanUtils.copyProperties(userParam,user);
		user.setRegTime(new Date());
		user.setDestFileName(user1.getDestFileName());

		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list){
				errorMsg = errorMsg + error.getCode() + "-" + error.getDefaultMessage() + ";";
			}
			modelMap.addAttribute("errorMsg",errorMsg);
			modelMap.addAttribute("user",user);
			return "userEdit";
		}
		//上传图片
		if (!file.isEmpty()) {
			try {
				fileName = System.currentTimeMillis()+file.getOriginalFilename();
				destFileName="E:\\uploaded"+File.separator+fileName;

				File destFile = new File(destFileName);
				destFile.getParentFile().mkdirs();
				file.transferTo(destFile);
				user.setDestFileName("/advertIMG/"+fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			}
		}
		userService.addUser(user);
		return "redirect:/getlist";
	}

	/**
	 * 后端根据用户 ID 进行删除即可
	 */

	@RequestMapping("/delete")
	@Transactional
	@Modifying
	public String delete(Integer id){
		userService.deleteById(id);
		return "redirect:/getlist";
	}
}
