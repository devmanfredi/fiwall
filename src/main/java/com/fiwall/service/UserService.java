package com.fiwall.service;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.User;
import com.fiwall.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    public static final String DOCUMENTO_CADASTRADO = "Documento já está cadastrado";
    public static final String EMAIL_CADASTRADO = "Email já cadastrado";


    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(UserRequestDto userRequestDto) {
        validEmailExists(userRequestDto.getEmail());
        validDocument(userRequestDto.getDocument());
        var user = new User();
        BeanUtils.copyProperties(userRequestDto, user);
        return userRepository.save(user);
    }

    private void validDocument(String document) {
        if (isDocumentExists(document)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DOCUMENTO_CADASTRADO);
    }

    private boolean isDocumentExists(String document) {
        return userRepository.findByDocument(document).isPresent();
    }

    private void validEmailExists(String email) {
        if (isEmailExists(email)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_CADASTRADO);
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
