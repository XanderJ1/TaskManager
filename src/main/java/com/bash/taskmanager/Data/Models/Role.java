    package com.bash.taskmanager.Data.Models;

    import jakarta.persistence.*;
    import org.springframework.security.core.GrantedAuthority;
    import lombok.Data;

    @Data
    @Entity
    public class Role implements GrantedAuthority {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "role_id")
        private Long roleId;
        private String authority;

        public Role(){
            super();
        }
        public Role(Long roleId, String authority){
            this.roleId = roleId;
            this.authority = authority;
        }
        public Role(String authority){
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
