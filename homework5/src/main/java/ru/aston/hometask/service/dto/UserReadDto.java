package ru.aston.hometask.service.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserReadDto {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private EGender gender;
    private double discount;
    private OffsetDateTime dtCreate;
    private OffsetDateTime dtUpdate;

    public UserReadDto(UUID id, String email, String firstname,
                      String lastname, LocalDate birthDate, EGender gender,
                      double discount, OffsetDateTime dtCreate, OffsetDateTime dtUpdate) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.discount = discount;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
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


    public OffsetDateTime getDtCreate() {
        return dtCreate;
    }

    public OffsetDateTime getDtUpdate() {
        return dtUpdate;
    }

    public static UserReadBuilder builder() {
        return new UserReadBuilder();
    }

    public static class UserReadBuilder {
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

        private UserReadBuilder() {
        }

        public UserReadBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public UserReadBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserReadBuilder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserReadBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserReadBuilder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserReadBuilder setGender(EGender gender) {
            this.gender = gender;
            return this;
        }

        public UserReadBuilder setDiscount(double discount) {
            this.discount = discount;
            return this;
        }

        public UserReadBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserReadBuilder setDtCreate(OffsetDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserReadBuilder setDtUpdate(OffsetDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserReadDto build() {
            return new UserReadDto(id,email,firstname,lastname, birthDate,gender,discount,dtCreate,dtUpdate);
        }
    }
}
