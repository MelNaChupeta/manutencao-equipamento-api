package br.com.tads.manutencao_equipamento_api.commom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    
    private Boolean status;
    private String message;
    private Boolean valid;

    public Response(String message){
        this.message = message;
    }

    public Response(Boolean valid){
        this.valid = valid;
    }

    public Response(Boolean status, String message){
        this.status = status;
        this.message = message;
    }

}