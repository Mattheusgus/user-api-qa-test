package com.example.userapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class User {

    @Schema(description = "ID único do usuário", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    private String name;

    @Schema(description = "Email do usuário", example = "joao@email.com", required = true)
    private String email;

    @Schema(description = "Idade do usuário", example = "25", required = true)
    private Integer age;

    public User() {}

    public User(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}