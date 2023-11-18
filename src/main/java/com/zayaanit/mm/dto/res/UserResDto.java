package com.zayaanit.mm.dto.res;

import org.modelmapper.ModelMapper;

import com.zayaanit.mm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto extends BaseResponseDTO<User>{

	public UserResDto(User user){
		new ModelMapper().map(user, this);
	}

	private String username;
}
