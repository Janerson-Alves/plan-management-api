package br.com.jaanalves.fase4.services;

import br.com.jaanalves.fase4.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Classe de serviço para autenticar o usuario com seu perfil.
@Service
public class AutenticacaoService implements UserDetailsService {

    // Atributo para pegar o Usuario no Repositorio
    private final UsuarioRepository usuarioRepository;
    // Construtor para receber o usuario do repositorio.
    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Sobrescrevendo o Metodo criado dentro do UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
