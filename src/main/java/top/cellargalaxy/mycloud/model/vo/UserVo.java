package top.cellargalaxy.mycloud.model.vo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 4950747715064264519L;
    private UserBo user;
    private List<AuthorizationBo> authorizations;

    public static final UserVo GUEST = new UserVo();

    static {
        GUEST.setUser(UserBo.GUEST);
        GUEST.setAuthorizations(Arrays.asList(AuthorizationBo.GUEST));
    }
}
