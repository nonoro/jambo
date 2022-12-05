package jambo.service;



import jambo.domain.user.User;
import jambo.dto.UserJoinDTO;

import java.util.HashMap;

public interface UserService {


    /**
     * 아이디 중복체크
     */
    HashMap<String, Object> userEmailOverlap(String email);

    /**
     * 회원가입
     */
    Long join(UserJoinDTO userJoinDTO);
}