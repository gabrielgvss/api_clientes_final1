package com.ex.api_clientes.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="data_nascimento")
    private LocalDate nascimento;
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public Cliente() {
        super();
    }

    public Cliente(Long id, LocalDate nascimento, String cpf, String nome, String endereco, String telefone, String email) {
        super();
        this.id = id;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente(LocalDate nascimento, String cpf, String nome, String endereco, String telefone, String email) {
        super();
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nascimento=" + nascimento + ", cpf=" + cpf + ", nome=" + nome + ", endereco="
                + endereco + ", telefone=" + telefone + ", email=" + email;
    }

}