package com.jbk.dao;

import com.jbk.entity.Role;
import com.jbk.entity.User;
import com.jbk.security.CustomUserDetail;


public interface UserDao 
{
	public CustomUserDetail loadUserByUserId(String userId);
	public boolean addUser(User user);
	public User loginUser(User user);
	public User getUserById(String id);
	public Role getRoleById(int roleId);

}
