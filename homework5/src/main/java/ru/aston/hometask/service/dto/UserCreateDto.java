package ru.aston.hometask.service.dto;

public class UserCreateDto {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String password;
    private final String birthdate;
    private final EGender gender;

    public UserCreateDto(String firstname, String lastname, String email, String password, String birthdate, EGender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public EGender getGender() {
        return gender;
    }

    public static UserCreateDtoBuilder builder() {
        return new UserCreateDtoBuilder();
    }

    public static class UserCreateDtoBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String birthdate;
        private EGender gender;

        private UserCreateDtoBuilder() {}

        public UserCreateDtoBuilder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserCreateDtoBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserCreateDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserCreateDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserCreateDtoBuilder setBirthdate(String birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public UserCreateDtoBuilder setGender(EGender gender) {
            this.gender = gender;
            return this;
        }

        public UserCreateDto build() {
            return new UserCreateDto(firstname, lastname, email, password, birthdate, gender);
        }
    }
}
