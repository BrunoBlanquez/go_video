<div align="center">
  <img align="center" src="https://user-images.githubusercontent.com/102121711/206720762-c075e523-f79c-401e-961c-d748723722a8.png" width="200px"/>
</div>

<samp>
  <h1 align="center"> Go Video </h1>
<p align="center">
Aplicação que tem proposta fazer a gestão de equipamentos para produtoras de vídeos. Nessa plataforma será possível gerenciar a utilização dos equipamentos empregados diariamente pelos colaboradores em projetos da produtora. 

<br>
  
<h2 align="center"> Conteúdo </h2>
  
  • <a href="#Repositorios">Repositórios</a> <br>
  
  • <a href="#Funcionalidades">Funcionalidades</a> <br>
	
  • <a href="#Modelagem">Modelagem</a> <br>
	
  • <a href="#Documentaçao">Documentação</a> <br>

  • <a href="#Fluxograma">Fluxograma</a> <br>

  • <a href="#Prototipaçao">Prototipação</a> <br>
	
  • <a href="#Tecnologias">Tecnologias</a> <br>

  • <a href="#Links">Links</a> <br>
	
  • <a href="#Equipe">Equipe</a> <br>

</p>

<h2 align="center"> <a name="Repositorios"> Repositórios </a> </h2>

Acessos: 

<a href="https://github.com/nmaia19/go_video">Back-end</a> <br>

<a href="https://github.com/nmaia19/go-video_frontend">Front-end</a> <br>

<br>

<h2 align="center"> <a name="Funcionalidades"> Funcionalidades </a> </h2>

 <div align="center">
  <img src="https://user-images.githubusercontent.com/102121711/206282872-4d4c8451-0600-454d-b93f-0278267ab831.jpg" width="700px"/>
</div> 
	
<br><br>

 <h3>FUNCIONALIDADES USUÁRIO</h3>

Será possível fazer as seguintes interações:

- [x] Logar e deslogar;

- [x] Alterar senha;

- [x] Consultar equipamentos;

- [x] Reservar equipamentos;

- [x] Devolver equipamentos;

- [x] Consultar seu perfil.

 <br>
 
 <h3>FUNCIONALIDADES ADMINISTRADOR</h3>

Será possível fazer as seguintes interações:

- [x] Logar e deslogar;

- [x] Alterar senha;

- [x] Gerenciar equipamentos;

- [x] Gerenciar colaboradores;

- [x] Gerenciar empréstimos.

<br>

<h2 align="center"> <a name="Modelagem">  Modelagem </h2>

- Diagrama de classes:
<div align="center">
  <img src="https://user-images.githubusercontent.com/102121711/206715807-da246cb7-dd83-4075-b543-1a3de6726b37.png" width="600px"><br>
</div> 

<br><br>

- Modelagem do banco de dados:
<div align="center">
  <img src="https://user-images.githubusercontent.com/102121711/206715616-194f8f35-9bbe-4f9f-b935-307f898f9b9b.png" width="600px"><br>
</div> 

<br>
		
<h2 align="center"> <a name="Documentaçao">  Documentação </h2>

- Acesso:

Para consultar as funcionalidades da API implementadas, acesse a documentação Swagger. 
<br>
	
- Autenticação:

Para fazer requisições com `Try it out` que requerem permissão, é preciso obter um token de autenticação através do endpoint `/login`.

![image](https://user-images.githubusercontent.com/102121711/206713906-7612e00b-f2ba-424b-a765-4e95a7a3fa98.png)

Credencial com permissão de administrador para testes:
```json
{
  "email": "admin@email.com",
  "senha": "govideo123"
}
```
<br>	
	
- Endpoints:
	
<details>
<summary>Autenticação</summary><br>
 
  |![image](https://user-images.githubusercontent.com/102121711/206714241-f0ae817d-f6f4-4401-b07f-d6f565df77e7.png)|
  |:--:|
  | <b>Autenticação Endpoint</b>|
</details>
	
<details>
<summary>Equipamentos</summary><br>
 
  |![image](https://user-images.githubusercontent.com/102121711/206714517-7fde2825-b157-44d4-bb27-ce133a62e730.png)|
  |:--:|
  | <b>Equipamentos Endpoint</b>|
</details>

<details>
<summary>Empréstimos</summary><br>
 
  |![image](https://user-images.githubusercontent.com/102121711/206714356-7b3ee2c9-094b-48af-94a1-404a3a70a688.png)|
  |:--:|
  | <b>Empréstimos Endpoint</b>|
</details>
	
<details>
<summary>Usuários</summary><br>
 
  |![image](https://user-images.githubusercontent.com/102121711/206714139-85ea1ee0-5a7f-45f7-ae06-70328caa9f5a.png)|
  |:--:|
  | <b>Usuários Endpoint</b>|
</details>

<br>

<h2 align="center"> <a name="Fluxograma">  Fluxograma</h2>

Fluxogramas de rotas:
<div align="center">
  <img src="https://user-images.githubusercontent.com/102121711/206441841-a6a24dd7-915d-47b4-9f7e-d563a918ca9e.jpg" width="850px"><br>
</div> 

<br>

 <h2 align="center"> <a name="Prototipaçao"> Prototipação </a> </h2>

- Acesso:

Para consultar as telas prototipadas no Figma, acesse o link: 
<br>
<a href="https://www.figma.com/file/c83HNClXXIUbwf6lg9vuG4/Go_Video">Figma - Go Video</a>
<br>

- Guia de estilos: 

<br>

<div align="center">
  <img src="https://user-images.githubusercontent.com/102121711/206720553-b36d1bb4-156d-4ee7-b202-9efbfee451ae.png" width="700px"><br>
</div> 

<br>

  <h2 align="center"> <a name="Tecnologias">  Tecnologias </a> </h2>

Ferramentas usadas na construção do projeto:

- Gerenciamento de sprints:
<div>
  <img alt="GoVideo-Trello" src="https://img.shields.io/badge/Trello-0052CC?style=flat&logo=trello&logoColor=white">
</div> 
<br>

- Diagramação e prototipação:
<div>
  <img alt="GoVideo-Miro" src="https://img.shields.io/badge/Miro-050038?style=flat&logo=Miro&logoColor=white">
  <img alt="GoVideo-Figma" src="https://img.shields.io/badge/Figma-F24E1E??style=flat&logo=figma&logoColor=white">
</div>  
<br>
	
- Back-end:
<div>
  <img alt="GoVideo-Java" src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white">
  <img alt="GoVideo-MySQL" src="https://img.shields.io/badge/MySQL-00000F?style=flat&logo=mysql&logoColor=white">
  <img alt="GoVideo-Postman" src="https://img.shields.io/badge/Postman-FA7343?style=flat&logo=postman&logoColor=white">
  <img alt="GoVideo-JUnit" src="https://img.shields.io/badge/JUnit-CC0000?style=flat&logo=JUnit5&logoColor=white">
  <br>
  <img alt="GoVideo-Spring" src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white">
  <img alt="GoVideo-SpringBoot" src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=Spring-Boot&logoColor=white">
  <img alt="GoVideo-SpringData" src="https://img.shields.io/badge/SpringData-6DB33F?style=flat&logo=spring&logoColor=white">
  <img alt="GoVideo-SpringSecurity" src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=Spring-Security&logoColor=white">
</div>	
<br>

- Versionamento:
<div>
  <img alt="GoVideo-Git" src="https://img.shields.io/badge/Git-E44C30?style=flat&logo=git&logoColor=white">
  <img alt="GoVideo-GitHub" src="https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white"><br>
</div>
<br>
	
- Documentação:
<div>
  <img alt="GoVideo-Swagger" src="https://img.shields.io/badge/Swagger-59666C?style=flat&logo=Swagger&logoColor=white"><br>
</div>
<br>

- Front-end:
<div>
  <img alt="GoVideo-HTML" src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white">
  <img alt="GoVideo-CSS" src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white">
  <img alt="GoVideo-Bootstrap" src="https://img.shields.io/badge/Bootstrap-563D7C?style=flat&logo=bootstrap&logoColor=white">
  <img alt="GoVideo-TypeScript" src="https://img.shields.io/badge/TypeScript-007ACC?style=flat&logo=typescript&logoColor=white">
  <img alt="GoVideo-Angular" src="https://img.shields.io/badge/Angular-DD0031?style=flat&logo=angular&logoColor=white">
</div> 
<br>
	
- IDE's:
<div>
  <img alt="GoVideo-IntelliJ" src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=flat&logo=intellij-idea&logoColor=white">
  <img alt="GoVideo-VSCode" src="https://img.shields.io/badge/-VS%20Code-260B02?style=flat&logo=visual-studio-code&logoColor=white">
</div> 

<br>
	
<h2 align="center"> <a name="Links">  Links </a> </h2>

• <a href="https://miro.com/app/board/uXjVPMLfw28=/?share_link_id=506333936334">Diagramas elaborados no Miro</a> <br> 

• <a href="https://trello.com/b/llFKHfXZ/go-video">Gestão do projeto no Trello</a> <br>

<br>
	
<h2 align="center"> <a name="Equipe">  Equipe </a> </h2>
  
<table align="center">
  <tr>
    <td align="center"><a href="https://github.com/aline-fagundes"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/102121711?s=400&u=65912f1e97edf6fc26f36aec52fff6089807cb36&v=4" width="100px;" alt=""/><br /><sub><b>ALINE FAGUNDES</b></sub></a><br />
    <td align="center"><a href="https://github.com/BrunoBlanquez"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/92882615?v=4" width="100px;" alt=""/><br /><sub><b>BRUNO BLANQUEZ</b></sub></a><br />
    <td align="center"><a href="https://github.com/diegodop"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/31602401?v=4" width="100px;" alt=""/><br /><sub><b>DIEGO PEIXOTO</b></sub></a><br />
    <td align="center"><a href="https://github.com/HalineTamaoki"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/104457218?v=4" width="100px;" alt=""/><br /><sub><b>HALINE TAMAOKI</b></sub></a><br />
    <td align="center"><a href="https://github.com/nmaia19"><img style="border-radius: 50%;" src="https://media-exp1.licdn.com/dms/image/C4E03AQGcMVl2Cdf7Hw/profile-displayphoto-shrink_800_800/0/1619709823397?e=1675900800&v=beta&t=cocdqYhtEHmFqJwTPcjRoDX3qvSlXCUxfHox_TCnpCY" width="100px;" alt=""/><br /><sub><b>NAYANE MAIA</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/RafaelFRF"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/88984340?v=4" width="100px;" alt=""/><br /><sub><b>RAFAEL FIORINI</b></sub></a><br /></td>
  </tr>
</table>

</samp>
