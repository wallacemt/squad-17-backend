package br.com.projeto.models.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "usuarios") // Nome da tabela no banco
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(name = "image_path")
    private String imagePath; // URL da foto de perfil, pode ser nulo

    @Column(name = "data_cadastro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro = new Date(); // Inicializa com a data atual

    @Column(nullable = false)
    private int reviews = 0; // Quantidade de resenhas feitas

    @Column(nullable = false)
    private int followers = 0; // Quantidade de seguidores

    @Column(nullable = false)
    private int followings = 0; // Quantidade de pessoas que o usuário segue

    @Column(name = "codigo_recuperacao_senha")
    private String codigoRecuperacaoSenha;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioCodigo;

    public Usuario(Long id, String nome, String email, String senha, String imagePath, int reviews, int followers, int followings) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.imagePath = imagePath;
        this.dataCadastro = new Date();
        this.reviews = reviews;
        this.followers = followers;
        this.followings = followings;
    }

    /**
     * Atualiza o código de recuperação de senha e a data de envio.
     * @param codigo Código de recuperação gerado
     */
    public void atualizarCodigoRecuperacaoSenha(String codigo) {
        this.codigoRecuperacaoSenha = codigo;
        this.dataEnvioCodigo = new Date(); // Atualiza a data para o momento atual
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email; // Alterado para usar email como username
    }

    public Date getDataEnvioCodigo() {
        return dataEnvioCodigo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataEnvioCodigo(Date dataEnvioCodigo) {
        this.dataEnvioCodigo = dataEnvioCodigo;
    }

    public void setCodigoRecuperacaoSenha(String codigoRecuperacaoSenha) {
        this.codigoRecuperacaoSenha = codigoRecuperacaoSenha;
    }

    public String getEmail() {
        return email;
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
