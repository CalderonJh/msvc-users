package org.calderon.users.service.impl;

import static org.calderon.users.tool.MessagesTool.msg;

import org.calderon.users.model.User;
import org.calderon.users.model.dto.UserDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.UserRepository;
import org.calderon.users.service.usecases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	@Override
	public String createUser(UserDTO userDTO) {
		User user = UserMapper.INSTANCE.toUser(userDTO);
//		this.userRepository.save()
    return msg("user.created");
	}

	@Override
	public String updateUser(UserDTO userDTO) {
		return msg("user.updated", userDTO.getName());
	}

	@Override
	public void deleteUser(Long id) {

	}

	@Override
	public Optional<User> getUser(Long id) {
		return Optional.empty();
	}

	@Override
	public List<User> getUsers() {
		return List.of();
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
