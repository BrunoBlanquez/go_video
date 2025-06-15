package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.services.EquipmentService;
import com.govideo.gerenciador.utilidades.EquipamentoGenerator;
import com.govideo.gerenciador.utilidades.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipmentControllerTest {

    private TokenGenerator tokenGenerator;

    private EquipamentoGenerator equipamentoGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EquipmentService equipmentService;

    @BeforeEach
    public void beforeEach() {
        this.equipamentoGenerator = new EquipamentoGenerator();
        this.tokenGenerator = new TokenGenerator();
    }

    @Test
    public void deveriaDevolver201AoCadastrarEquipamento() throws Exception {
        URI uri = new URI("/equipamentos");
        String json = "{\r\n"
                + "    \"modelo\": \"Pocket Cinema 6K\",\r\n"
                + "    \"descricao\": \"Filmadora profissional Pocket Cinema 6K\",\r\n"
                + "    \"marca\": \"Black Magic\",\r\n"
                + "    \"categoria\": \"Filmadoras\",\r\n"
                + "    \"urlFoto\": \"https://emania.vteximg.com.br/arquivos/ids/209607\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void deveriaDevolver200AoBuscarTodosOsEquipamentos() throws Exception {
        URI uri = new URI("/equipamentos");
        equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEquipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEquipamento.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEquipamentoPorId() throws Exception {
        String idEquipamento = equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);
        URI uri = new URI("/equipamentos/" + idEquipamento);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String equipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(equipamento.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEquipamentosPorStatusDisponivel() throws Exception {
        URI uri = new URI("/equipamentos/buscarPorStatus/disponivel");
        equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String equipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(equipamento.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoAtualizarEquipamento() throws Exception {
        URI uri = new URI("/equipamentos/1");
        equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        String json = "{\r\n"
                + "    \"modelo\": \"Modelo alterado\",\r\n"
                + "    \"descricao\": \"Descricao alterada\",\r\n"
                + "    \"marca\": \"Marca alterada\",\r\n"
                + "    \"categoria\": \"Categoria alterada\",\r\n"
                + "    \"urlFoto\": \"url alterada\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver200AoExcluirEquipamento() throws Exception {
        URI uri = new URI("/equipamentos/1");
        equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver403AoExcluirEquipamentoComEmprestimoVigente() throws Exception {
        String idEquipamento = equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post("/emprestimos/" + idEquipamento)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .contentType(MediaType.APPLICATION_JSON));

        URI uri = new URI("/equipamentos/" + idEquipamento);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(403));
    }

    @Test
    public void deveriaDevolver200AoInativarEquipamentoComEmprestimoEncerrado() throws Exception {
        String idEquipamento = equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post("/emprestimos/" + idEquipamento)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                        .contentType(MediaType.APPLICATION_JSON));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String idEmprestimo = jsonParser.parseMap(resultString).get("id").toString();

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put("/emprestimos/encerrar/" + idEmprestimo)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .contentType(MediaType.APPLICATION_JSON));

        URI uri = new URI("/equipamentos/" + idEquipamento);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

}
