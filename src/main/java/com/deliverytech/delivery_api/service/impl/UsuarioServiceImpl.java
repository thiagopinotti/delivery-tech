package com.deliverytech.delivery_api.service.impl;

import com.deliverytech.delivery_api.dto.request.LoginRequest;
import com.deliverytech.delivery_api.dto.response.LoginResponse;
import com.deliverytech.delivery_api.model.Usuario;
import com.deliverytech.delivery_api.service.UsuarioService;
import com.deliverytech.delivery_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            String token = jwtUtil.generateToken(authentication.getName());
            
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(authentication.getName());
            response.setMessage("Login realizado com sucesso");
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Credenciais inválidas", e);
        }
    }

    @Override
    public void logout(String token) {
        // Implementar lógica de logout se necessário
        // Por exemplo, adicionar token a uma blacklist
    }

    @Override
    public Object buscarPorId(Long id) {
        // TODO: Implementar busca de usuário por ID
        // Por enquanto, retorna null ou lança exceção
        throw new UnsupportedOperationException("Método buscarPorId ainda não implementado");
    }

    // Implementar outros métodos que possam estar na interface UsuarioService
    // Adicione conforme necessário baseado na interface existente

    @Override
    public void inativarUsuario(Long id) {
        // TODO: Implementar lógica para inativar usuário
        throw new UnsupportedOperationException("Método inativarUsuario ainda não implementado");
    }

    @Override
    public boolean existePorEmail(String email) {
        // TODO: Implementar lógica para verificar existência por email
        throw new UnsupportedOperationException("Método existePorEmail ainda não implementado");
    }

    @Override
    public UserDetails buscarPorEmail(String email) {
        // TODO: Implementar lógica para buscar usuário por email
        throw new UnsupportedOperationException("Método buscarPorEmail ainda não implementado");
    }

    @Override
    public Usuario salvar(com.deliverytech.delivery_api.dto.request.RegisterRequest registerRequest) {
        // TODO: Implementar lógica para salvar usuário
        throw new UnsupportedOperationException("Método salvar ainda não implementado");
    }
}
