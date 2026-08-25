package br.com.jaanalves.fase4.services;

import br.com.jaanalves.fase4.dto.PlanoDTO;
import br.com.jaanalves.fase4.entities.PlanoTelefonia;
import br.com.jaanalves.fase4.repository.PlanoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Integra o Mockito ao JUnit 5, permitindo que as anotações
// @Mock e @InjectMocks sejam inicializadas automaticamente.
@ExtendWith(MockitoExtension.class)
public class PlanoServiceTest {

    // Cria um Mock do PlanoRepository.
    // O Mockito cria uma versão "falsa" do Repository para que o teste
    // não precise acessar o banco de dados de verdade.
    @Mock
    private PlanoRepository planoRepository;

    // Cria uma instância real do PlanoService e injeta nela os Mocks
    // necessários, neste caso, o planoRepository.
    @InjectMocks
    private PlanoService planoService;


    // ============================================================
    // TESTE 1 - Cadastro de plano com sucesso
    // ============================================================

    @Test
    void deveCadastrarPlanoComSucesso() {

        // -------------------------
        // ARRANGE (Preparação)
        // -------------------------

        // Cria um DTO válido que será utilizado no cadastro.
        PlanoDTO dto = new PlanoDTO();
        dto.setNome("Plano Controle 40GB");
        dto.setFranquiaGb(40);
        dto.setValorMensal(39.99);

        // Configura o Mock para informar que não existe
        // outro plano com esse nome.
        when(planoRepository.existsByNomeIgnoreCase(dto.getNome()))
                .thenReturn(false);

        // Simula o comportamento do banco de dados ao salvar.
        // O banco normalmente geraria o ID da entidade.
        // Aqui simulamos esse comportamento colocando o ID 1L
        // na entidade que será salva.
        when(planoRepository.save(any(PlanoTelefonia.class)))
                .thenAnswer(invocation -> {

                    // Recupera a entidade que o Service enviou
                    // para o método save().
                    PlanoTelefonia plano = invocation.getArgument(0);

                    // Simula o ID que seria gerado pelo banco.
                    plano.setId(1L);

                    // Retorna a entidade simulando o comportamento
                    // do Repository após o salvamento.
                    return plano;
                });


        // -------------------------
        // ACT (Ação)
        // -------------------------

        // Executa o método que estamos testando.
        PlanoTelefonia resultado = planoService.cadastrar(dto);


        // -------------------------
        // ASSERT (Validação)
        // -------------------------

        // Verifica se o resultado não é nulo.
        Assertions.assertNotNull(resultado);

        // Verifica se o ID foi gerado corretamente.
        Assertions.assertEquals(1L, resultado.getId());

        // Verifica se os dados do DTO foram corretamente
        // transferidos para a entidade.
        Assertions.assertEquals("Plano Controle 40GB", resultado.getNome());
        Assertions.assertEquals(40, resultado.getFranquiaGb());
        Assertions.assertEquals(39.99, resultado.getValorMensal());

        // Verifica se o método save() do Repository
        // foi chamado exatamente uma vez.
        verify(planoRepository, times(1))
                .save(any());
    }


    // ============================================================
    // TESTE 2 - Cadastro de plano com nome duplicado
    // ============================================================

    @Test
    void deveLancarExcecaoAoCadastrarPlanoComNomeDuplicado() {

        // -------------------------
        // ARRANGE (Preparação)
        // -------------------------

        // Cria um DTO que será utilizado na tentativa de cadastro.
        PlanoDTO dto = new PlanoDTO();
        dto.setNome("Plano Controle 40GB");
        dto.setFranquiaGb(40);
        dto.setValorMensal(39.99);

        // Configura o Mock para informar que o nome
        // já existe no banco de dados.
        when(planoRepository.existsByNomeIgnoreCase(dto.getNome()))
                .thenReturn(true);


        // -------------------------
        // ACT + ASSERT (Ação + Validação)
        // -------------------------

        // Executa o método cadastrar() e verifica se ele
        // lança a exceção IllegalArgumentException.
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> planoService.cadastrar(dto)
        );


        // -------------------------
        // VERIFY (Verificação)
        // -------------------------

        // Garante que o método save() NUNCA foi chamado,
        // pois o cadastro deve ser interrompido quando
        // o nome do plano já estiver cadastrado.
        Mockito.verify(planoRepository, never())
                .save(any());
    }
}
