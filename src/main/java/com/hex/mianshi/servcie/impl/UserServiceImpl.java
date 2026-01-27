package com.hex.mianshi.servcie.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hex.mianshi.model.entity.User;
import com.hex.mianshi.servcie.UserService;
import com.hex.mianshi.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author DYC666
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2026-01-27 14:11:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




