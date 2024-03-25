package com.jbk.service;
import org.springframework.transaction.annotation.Transactional;
import com.jbk.entity.Role;
import com.jbk.entity.User;
import com.jbk.security.CustomUserDetail;


@Transactional
public interface UserService {
	
	public CustomUserDetail loadUserByUserId(String userId);
	public boolean addUser(User user);
	public User loginUser(User user);
	public User getUserById(String id);
	public Role getRoleById(int roleId);
	
	

}
