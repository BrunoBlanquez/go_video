package com.govideo.gerenciador.dtos;

public class MessageResponseDTO {

    private String mensagem;

    public MessageResponseDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

}
