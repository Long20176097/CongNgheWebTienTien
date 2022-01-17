package com.example.zalo.service.impl;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.example.zalo.entity.Authority;
import com.example.zalo.entity.Block;
import com.example.zalo.entity.User;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.mapper.UserMapper;
import com.example.zalo.model.request.ChangePasswordRequest;
import com.example.zalo.model.request.CreateUserRequest;
import com.example.zalo.model.request.SignUpRequest;
import com.example.zalo.model.request.UpdateUserRequest;
import com.example.zalo.repository.BlockRepository;
import com.example.zalo.repository.UserChatRepository;
import com.example.zalo.repository.UserRepository;
import com.example.zalo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserChatRepository userChatRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final BlockRepository blockRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserChatRepository userChatRepository, PasswordEncoder passwordEncoder, BlockRepository blockRepository) {
        this.userRepository = userRepository;
        this.userChatRepository = userChatRepository;

        this.passwordEncoder = passwordEncoder;
        this.blockRepository = blockRepository;
    }

    public List<User> findAll() {
//        log.info("retrieving all users");
        return userRepository.findAll();
    }
    @Override
    public List<UserDTO> getAllUser() {

        List<User> users = userRepository.findByStatus("enabled");
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserMapper.toUserDTO(user));
        }
        return result;
    }

    @Override
    public UserDTO findByPhoneNumber1(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDTO getUserById(int id ,int userId) {

        Block blockUser= blockRepository.checkBlockUser(id,userId);


        if(blockUser != null){
            throw new BadGuyException("user");
        }
        Optional<User> user = userRepository.findById(id);
        if(user.get().getStatus().equals("disabled")){
            throw new InternalServerException("disable");
        }
        if (user.isEmpty()) {
            throw new NotFoundException("No user found");
        }
        return UserMapper.toUserDTO(user.get());
    }

    @Override
    public int getCurrentUserId(Principal principal) {
//        String username = principal.getName();
//        User user = userRepository.findByUsername(username);
//        int id = user.getId();
//        return id;
        return 0;
    }

    @Override
    public UserDTO updateUser(UpdateUserRequest request, int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No user found");
        }
        User updateUser = UserMapper.mergeUpdate(request, user.get());
        userRepository.save(updateUser);
        return UserMapper.toUserDTO(updateUser);
    }

    @Override
    public UserDTO disableUser(UpdateUserRequest request, int id) {
        Optional<User> user = userRepository.findById(id);


        User changeUserStatus = UserMapper.mergeDisable(request, user.get());
        try {
            userRepository.save(changeUserStatus);
        } catch (Exception ex) {
            throw new BadRequestException("Can't change user status");
        }
        return UserMapper.toUserDTO(changeUserStatus);
    }

    @Override
    public List<UserDTO> searchByNameOrId(String keyword) {
        String fullName = "%" + keyword + "%";
        String id = keyword;
        List<User> users = userRepository.findUserByFullNameOrId(fullName, id);
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserMapper.toUserDTO(user));
        }
        return result;
    }

    @Override
    public List<UserDTO> getUsers(String type, String keyword) {

        List<User> users = new ArrayList<>();
        String fullName = "%" + keyword + "%";
        String id = keyword;
        if (type == null && keyword == null) {
            users = userRepository.findByStatus("enabled");
        } else if (type != null && keyword == null) {
            users = userRepository.findByAuthority_authorityAndStatus(type, "enabled");
        } else if (type == null && keyword != null) {
            users = userRepository.findUserByFullNameOrId(fullName, id);
        } else if (type != null && keyword != null) {
            users = userRepository.findUserByFullNameOrId(fullName, id);
            users = users.stream().filter(user -> user.getAuthority().getAuthority().equals(type.toUpperCase())).collect(Collectors.toList());
        }
        return users.stream()

                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {

        if(!request.getPhoneNumber().startsWith("0")){
            throw new InternalServerException("wrong phone number ");
        }
        if(request.getPhoneNumber().length()>10 || request.getPhoneNumber().length()<6){
            throw  new BusinessException();
        }
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(user!=null){
            throw new DuplicateRecordException("duplicate phone number");
        }

        long count = userRepository.count() + 1;

        user = UserMapper.toUser(request);


        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("ddMMyyyy");
        String dob = user.getDob().format(formatters);

        user.setStatus("enabled");
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Authority authority = new Authority();
        authority.setAuthority(request.getAuthority());
        authority.setUser(user);

        user.setAuthority(authority);
        userRepository.saveAndFlush(user);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO changePassword(ChangePasswordRequest request, String phoneNumber) {
        User updateUser = UserMapper.toUser(request);
       User user = userRepository.findByPhoneNumber(phoneNumber);
        try {
            updateUser.setAuthority(user.getAuthority());
            updateUser.setPhoneNumber(user.getPhoneNumber());
            updateUser.setLinkAvatar(user.getLinkAvatar());
            updateUser.setDob(user.getDob());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setStatus(user.getStatus());
            updateUser.setJoinedDate(user.getJoinedDate());
            updateUser.setGender(user.getGender());
            updateUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.updatePassword(updateUser.getPassword(), phoneNumber);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update password");
        }

        return UserMapper.toUserDTO(updateUser);
    }

       public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userChatRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDTO signUp(SignUpRequest request) {
        if(!request.getPhoneNumber().startsWith("0")){
            throw new InternalServerException("wrong phone number ");
        }
        if(request.getPhoneNumber().length()>10 || request.getPhoneNumber().length()<6){
            throw  new BusinessException();
        }
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(user!=null){
            throw new DuplicateRecordException("duplicate phone number");
        }
        user = UserMapper.toUser(request);
        user.setStatus("enabled");
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUser(user);
        user.setAuthority(authority);
        userRepository.saveAndFlush(user);
        return UserMapper.toUserDTO(user);
    }

}
