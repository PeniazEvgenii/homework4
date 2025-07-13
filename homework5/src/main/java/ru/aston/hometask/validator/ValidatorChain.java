package ru.aston.hometask.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.valid.DateValidator;
import ru.aston.hometask.validator.valid.EmailUniqueValidator;
import ru.aston.hometask.validator.valid.EmailValidator;
import ru.aston.hometask.validator.valid.FirstnameValidator;
import ru.aston.hometask.validator.valid.LastnameValidator;
import ru.aston.hometask.validator.valid.PasswordValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorChain {

    public static IUserValidator build(IUserDao userDao) {
        BaseUserValidator dateValidator = new DateValidator();
        BaseUserValidator emailValidator = new EmailValidator();
        BaseUserValidator emailUniqueValidator = new EmailUniqueValidator(userDao);
        BaseUserValidator firstnameValidator = new FirstnameValidator();
        BaseUserValidator lastnameValidator = new LastnameValidator();
        BaseUserValidator passwordValidator = new PasswordValidator();

        dateValidator
                .setNext(emailValidator)
                .setNext(emailUniqueValidator)
                .setNext(firstnameValidator)
                .setNext(lastnameValidator)
                .setNext(passwordValidator);

        return dateValidator;
    }
}
