package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.mm.annotations.RestApiController;
import com.zayaanit.mm.dto.req.UserReqDto;
import com.zayaanit.mm.dto.res.UserResDto;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@RestApiController
@RequestMapping("/api/v1/user")
@Tag(
	name = "User", 
	description = "The User API. Contains all the operations that can be performed on a user."
)
public class UserController extends AbstractBaseController<User, UserReqDto, UserResDto> {

	UserController(UserService<UserReqDto, UserResDto> userService){
		super(userService);
	}

}
