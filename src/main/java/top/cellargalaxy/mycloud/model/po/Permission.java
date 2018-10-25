package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public enum Permission implements Serializable {
	GUEST("GUEST"), USER("USER"), ADMIN("ADMIN");

	public final String permission;

	Permission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return permission;
	}
}
