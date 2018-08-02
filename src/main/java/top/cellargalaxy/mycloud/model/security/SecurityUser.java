package top.cellargalaxy.mycloud.model.security;

import java.io.Serializable;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/7/31
 */
public interface SecurityUser extends Serializable {
	String getUsername();

	String getPassword();

	Set<String> getPermissions();
}
