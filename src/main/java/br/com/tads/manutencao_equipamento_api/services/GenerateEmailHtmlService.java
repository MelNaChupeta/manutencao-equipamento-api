package br.com.tads.manutencao_equipamento_api.services;

import org.springframework.stereotype.Service;

@Service
public class GenerateEmailHtmlService {
    public static String generatePasswordEmailTemplate(String userName, String password) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <style>" +
               "        table {" +
               "            font-family: Arial, sans-serif;" +
               "            border-collapse: collapse;" +
               "            width: 100%;" +
               "        }" +
               "        td, th {" +
               "            border: 1px solid #dddddd;" +
               "            text-align: left;" +
               "            padding: 8px;" +
               "        }" +
               "        tr:nth-child(even) {" +
               "            background-color: #f2f2f2;" +
               "        }" +
               "    </style>" +
               "</head>" +
               "<body>" +
               "    <h2>Olá " + userName + ",</h2>" +
               "    <p>Sua conta foi criada com sucesso , segue sua nova senha para entrar no aplicativo: </p>" +
               "    <table>" +
               "        <tr>" +
               "            <td>Senha</td>" +
               "            <td>" + password + "</td>" +
               "        </tr>" +
               "    </table>" +
               "</body>" +
               "</html>";
    }
}
