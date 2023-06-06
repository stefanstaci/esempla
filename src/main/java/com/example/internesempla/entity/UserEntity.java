package com.example.internesempla.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private Boolean activated;
    private String langKey;
    private String activationKey;
    private String resetKey;
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date resetDate;
    private String lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @OneToMany(mappedBy = "createdBy")
    private List<StorageFileEntity> files = new ArrayList<>();
    @OneToMany(mappedBy = "userId")
    private List<UserReservationEntity> reservations = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user-authority",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_name", referencedColumnName = "name"))
    private Collection<AuthorityEntity> roles;

    public UserEntity() {
    }

    public UserEntity(String login, String password, String firstName, String lastName, String email, String imageUrl, Boolean activated, String langKey, String activationKey, String resetKey, String createdBy, Date createdDate, Date resetDate, String lastModifiedBy, Date lastModifiedDate, Collection<AuthorityEntity> roles, List<StorageFileEntity> files, List<UserReservationEntity> reservations) {
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

    public UserEntity(Integer id, String login, String password, String firstName, String lastName, String email, String imageUrl, Boolean activated, String langKey, String activationKey, String resetKey, String createdBy, Date createdDate, Date resetDate, String lastModifiedBy, Date lastModifiedDate, Collection<AuthorityEntity> roles, List<StorageFileEntity> files, List<UserReservationEntity> reservations) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Collection<AuthorityEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<AuthorityEntity> roles) {
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
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
}
