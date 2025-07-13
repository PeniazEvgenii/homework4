package ru.aston.hometask.dao.entity;

import ru.aston.hometask.service.dto.EGender;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserEntity {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private EGender gender;
    private double discount;
    private String password;
    private OffsetDateTime dtCreate;
    private OffsetDateTime dtUpdate;

    public UserEntity(UUID id, String email, String firstname,
                      String lastname, LocalDate birthDate, EGender gender,
                      double discount, String password, OffsetDateTime dtCreate, OffsetDateTime dtUpdate) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.discount = discount;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public EGender getGender() {
        return gender;
    }

    public double getDiscount() {
        return discount;
    }

    public String getPassword() {
        return password;
    }

    public OffsetDateTime getDtCreate() {
        return dtCreate;
    }

    public OffsetDateTime getDtUpdate() {
        return dtUpdate;
    }

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    public static class UserEntityBuilder {
        private UUID id;
        private String email;
        private String firstname;
        private String lastname;
        private LocalDate birthDate;
        private EGender gender;
        private double discount;
        private String password;
        private OffsetDateTime dtCreate;
        private OffsetDateTime dtUpdate;

        private UserEntityBuilder() {
        }

        public UserEntityBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserEntityBuilder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserEntityBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserEntityBuilder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserEntityBuilder setGender(EGender gender) {
            this.gender = gender;
            return this;
        }

        public UserEntityBuilder setDiscount(double discount) {
            this.discount = discount;
            return this;
        }

        public UserEntityBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder setDtCreate(OffsetDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserEntityBuilder setDtUpdate(OffsetDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(id,email,firstname,lastname, birthDate,gender,discount,password,dtCreate,dtUpdate);
        }
    }
}
