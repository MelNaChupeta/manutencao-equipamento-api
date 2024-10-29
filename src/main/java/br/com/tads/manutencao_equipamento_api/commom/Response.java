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
    private Object data;
    public Response(String message){
        this.message = message;
    }

    public Response(boolean status) {
        this.status = status;
    }

    public Response(Boolean status, String message){
        this.status = status;
        this.message = message;
    }
    
    public Response(Boolean status, Object data){
        this.status = status;
        this.data = data;
    }

}