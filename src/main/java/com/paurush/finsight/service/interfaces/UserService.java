package com.paurush.finsight.service.interfaces;

import com.paurush.finsight.dto.LoginRequest;
import com.paurush.finsight.dto.RegisterRequest;
import com.paurush.finsight.entity.User;

public interface UserService {

    User registerUser(RegisterRequest request);
    User loginUser(LoginRequest request);

}
