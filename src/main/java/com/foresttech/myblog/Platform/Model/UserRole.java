package com.foresttech.myblog.Platform.Model;



import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "USER_ROLE")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(length = 25)
    private String name;
    //懒加载 不会查询role表
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users;
    //急加载 会查询role表
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private List<UserAuthority> Authoritys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<UserAuthority> getAuthoritys() {
        return Authoritys;
    }

    public void setAuthoritys(List<UserAuthority> authoritys) {
        Authoritys = authoritys;
    }
}
