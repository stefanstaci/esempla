package com.example.internesempla.entity;

import com.example.internesempla.enumeration.RoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;
    private String imageUrl;
    private Boolean activated;
    private String langKey;
    private String activationKey;
    private String resetKey;
    private String createdBy;
    private LocalDate createdDate;
    private LocalDate resetDate;
    private String lastModifiedBy;
    private LocalDate lastModifiedDate;
    @OneToMany(mappedBy = "createdBy")
    private List<StorageFileEntity> files = new ArrayList<>();
    @OneToMany(mappedBy = "userId")
    private List<UserReservationEntity> reservations = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String login, String password, String firstName, String lastName, String email, String imageUrl, Boolean activated, String langKey, String activationKey, String resetKey, String createdBy, LocalDate createdDate, LocalDate resetDate, String lastModifiedBy, LocalDate lastModifiedDate, RoleEnum roles, List<StorageFileEntity> files, List<UserReservationEntity> reservations) {
        this.login = login;
        this.passwordHash = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        this.activationKey = activationKey;
        this.resetKey = resetKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.resetDate = resetDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.roles = roles;
        this.files = files;
        this.reservations = reservations;
    }

    public UserEntity(Integer id, String login, String password, String firstName, String lastName, String email, String imageUrl, Boolean activated, String langKey, String activationKey, String resetKey, String createdBy, LocalDate createdDate, LocalDate resetDate, String lastModifiedBy, LocalDate lastModifiedDate, RoleEnum roles, List<StorageFileEntity> files, List<UserReservationEntity> reservations) {
        this.id = id;
        this.login = login;
        this.passwordHash = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        this.activationKey = activationKey;
        this.resetKey = resetKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.resetDate = resetDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.roles = roles;
        this.files = files;
        this.reservations = reservations;
    }

    public UserEntity(String login, String password) {
        this.login = login;
        this.passwordHash = password;
    }

    public UserEntity(int id, String login, String email, String firstName, String lastName, String passwordHash, RoleEnum roles, String activationKey, boolean activated) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName= lastName;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.activationKey = activationKey;
        this.activated = activated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getResetDate() {
        return resetDate;
    }

    public void setResetDate(LocalDate resetDate) {
        this.resetDate = resetDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public RoleEnum getRoles() {
        return roles;
    }

    public void setRoles(RoleEnum roles) {
        this.roles = roles;
    }

    public List<StorageFileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<StorageFileEntity> files) {
        this.files = files;
    }

    public List<UserReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<UserReservationEntity> reservations) {
        this.reservations = reservations;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof UserEntity user)) return false;
//        return getId().equals(user.getId()) && getLogin().equals(user.getLogin()) && getPasswordHash().equals(user.getPasswordHash()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getEmail().equals(user.getEmail()) && getRoles() == user.getRoles() && Objects.equals(getImageUrl(), user.getImageUrl()) && Objects.equals(getActivated(), user.getActivated()) && Objects.equals(getLangKey(), user.getLangKey()) && Objects.equals(getActivationKey(), user.getActivationKey()) && Objects.equals(getResetKey(), user.getResetKey()) && Objects.equals(getCreatedBy(), user.getCreatedBy()) && Objects.equals(getCreatedDate(), user.getCreatedDate()) && Objects.equals(getResetDate(), user.getResetDate()) && Objects.equals(getLastModifiedBy(), user.getLastModifiedBy()) && Objects.equals(getLastModifiedDate(), user.getLastModifiedDate());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getLogin(), getPasswordHash(), getFirstName(), getLastName(), getEmail(), getRoles(), getImageUrl(), getActivated(), getLangKey(), getActivationKey(), getResetKey(), getCreatedBy(), getCreatedDate(), getResetDate(), getLastModifiedBy(), getLastModifiedDate());
//    }
//
//    @Override
//    public String toString() {
//        return "UserEntity{" +
//                "id=" + id +
//                ", login='" + login + '\'' +
//                ", passwordHash='" + passwordHash + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", roles=" + roles +
//                ", imageUrl='" + imageUrl + '\'' +
//                ", activated=" + activated +
//                ", langKey='" + langKey + '\'' +
//                ", activationKey='" + activationKey + '\'' +
//                ", resetKey='" + resetKey + '\'' +
//                ", createdBy='" + createdBy + '\'' +
//                ", createdDate=" + createdDate +
//                ", resetDate=" + resetDate +
//                ", lastModifiedBy='" + lastModifiedBy + '\'' +
//                ", lastModifiedDate=" + lastModifiedDate +
//                '}';
//    }
}


