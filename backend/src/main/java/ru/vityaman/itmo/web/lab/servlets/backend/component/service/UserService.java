package ru.vityaman.itmo.web.lab.servlets.backend.component.service;

public interface UserService {
    String generateNewbieToken();
    void activateUserAccountWithToken(String token);
}
