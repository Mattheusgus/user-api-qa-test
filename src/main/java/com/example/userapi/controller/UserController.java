package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "API para gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<User> createUser(
            @RequestBody(description = "Dados do usuário para criação", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody User user) {
        user.setId(null);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody(description = "Dados do usuário para atualização", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody User userDetails) {
        userDetails.setId(null);
        User user = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }


    @GetMapping("/{id}/details")
    @Operation(summary = "Obter detalhes do usuário")
    public ResponseEntity<String> getUserDetails(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.ok("Usuário não encontrado");
        }

        String details = "Nome: " + user.getName() +
                " | Email: " + user.getEmail() +
                " | Idade: " + user.getAge() +
                " | Categoria: " + getCategory(user.getAge());


        return ResponseEntity.ok(details);
    }

    private String getCategory(Integer age) {
        if (age < 18 && age >= 0) return "Jovem";
        if (age < 60 && age > 18) return "Adulto";
        if (age >= 60) return "Idoso";
        return null;
    }

    // ✅ Schema customizado para requests (sem ID)
    @Schema(name = "UserRequest", description = "Dados para criar ou atualizar usuário")
    public static class UserRequest {
        @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
        private String name;

        @Schema(description = "Email do usuário", example = "joao@email.com", required = true)
        private String email;

        @Schema(description = "Idade do usuário", example = "25", required = true)
        private Integer age;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
    }
}