package com.fiwall.service;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.User;
import com.fiwall.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
public class UserService {

    public static final String DOCUMENT_ALREADY_EXISTS = "Document Already Exists";
    public static final String EMAIL_ALREADY_EXISTS = "Email Already Exists";
    public static final String WITHOUT_DOCUMENT = "Without Document";


    private final UserRepository userRepository;

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
        if (isDocumentExists(document))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DOCUMENT_ALREADY_EXISTS);
        if (isDocumentNull(document)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, WITHOUT_DOCUMENT);
    }

    private boolean isDocumentNull(String document) {
        return Objects.isNull(document);
    }

    private boolean isDocumentExists(String document) {
        return userRepository.findByDocument(document).isPresent();
    }

    private void validEmailExists(String email) {
        if (isEmailExists(email)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_ALREADY_EXISTS);
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User findUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }
}
