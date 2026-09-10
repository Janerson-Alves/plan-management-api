package br.com.jaanalves.fase4.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

// Entidade que vai ter uma tabela chamada tb_perfil dentro do banco
@Entity
@Table(name = "tb_perfil")
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;

    // Construtor sem parametros
    public Perfil() {}
    // Construtor com parametros
    public Perfil(String nome) {
        this.nome = nome;
    }
    // Sobrescrevendo Método do GrantedAuthority
    @Override
    public String getAuthority() {
        return this.nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
