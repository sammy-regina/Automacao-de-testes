# language: pt
@api @GET @regressivo
Funcionalidade: Busca de Posts na API JSONPlaceholder
    Para garantir a integridade dos dados dos posts
    Como um usuário da API
    Eu quero poder buscar posts específicos e validar suas informações

    Esquema do Cenário: Validar um post pelo ID
        Dado que a API de posts está disponível
        Quando eu busco o post com o ID <ID_Post>
        Então o status code da resposta deve ser <Status_Code>
        E o titulo do post deve ser "<Titulo_Esperado>"

        Exemplos:
            | ID_Post | Status_Code | Titulo_Esperado                                     |
            | 1       | 200         | sunt aut facere repellat provident occaecati excepturi optio reprehenderit |
            | 2       | 200         | qui est esse        |
            | 99      | 200         | temporibus sit alias delectus eligendi possimus magni |
            | 999     | 404         |                                                     |