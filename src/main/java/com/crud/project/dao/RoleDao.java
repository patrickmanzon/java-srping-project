package com.crud.project.dao;

import com.crud.project.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

}
