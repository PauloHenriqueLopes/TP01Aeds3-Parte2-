# PUCFlix - Sistema de Gerenciamento de Séries e Episódios

## O que o trabalho faz?

O trabalho implementa um sistema completo para o gerenciamento de séries e seus episódios, simulando um serviço de streaming. O sistema permite:

- Cadastrar, buscar, alterar **séries** e excluir as que não possuem vinculo com nenhum episódio.
- Cadastrar, buscar, alterar e excluir **episódios**, vinculados a séries.
- Visualizar **episódios por temporada** de uma série.
- Visualizar **todas as séries cadastradas**.
- Visualizar **todos os episódios cadastrados** de uma série.
- Garantir a integridade referencial entre séries e episódios, utilizando **ID de série como chave estrangeira** nos episódios.
- Utilizar estruturas eficientes de **armazenamento e indexação** com:
  - CRUD genérico com arquivos.
  - **Árvore B+** como índice secundário para o relacionamento 1:N entre série e episódio.
  - **Hash Extensível** como índice direto para acesso rápido por ID.

O sistema é baseado no padrão **MVC** e foi escrito em **Java**.

---

## Participante

- Paulo Henrique Lopes de Paula

---

## Estrutura de Classes

### `Serie.java` (entidades)
- `Construtores, Metodos Geters e Setters`, `toByteArray()`, `fromByteArray()`

### `Episodio.java` (entidades)
- Contém o `idSerie` como chave estrangeira
- `Contrutores, Metodos Geters e Setters`, `toByteArray()`, `fromByteArray()`

### `Arquivo<T>` (modelo)
- Classe genérica de CRUD em arquivo.
- Métodos: `create()`, `read()`, `update()`, `delete()`

### `ArquivoSerie.java`
- CRUD específico para `Serie`, utilizando `Arquivo<T>`, `HashExtensível`, e outros índices.
- Implementa busca por nome (`readNome`) e `readAll`.

### `ArquivoEpisodio.java`
- CRUD específico para `Episodio`, com uso de Árvore B+ (`indiceSerieEpisodio`) para manter o relacionamento 1:N.
- Métodos adicionais:
  - `readPorSerie(int id)`: retorna todos episódios de uma série.
  - `readPorSerieETemporada(int id, int temporada)`: retorna episódios de uma temporada específica de uma série.

### `ParIdId.java`
- Par de IDs usado como chave na Árvore B+ para representar a relação `idSerie -> idEpisodio`.

### `Principal.java`
- Ponto de entrada do programa, com interface de texto.
- Contém a navegação por menus e chamadas às operações.

---

## Experiência e Desenvolvimento

O trabalho proporcionou uma experiência prática rica com estruturas de dados avançadas como Árvore B+ e Hash Extensível, além de aprofundar o uso de arquivos e programação genérica em Java.

- **Requisitos implementados**: Sim, todos os requisitos foram implementados.
- **Maior desafio**: Integrar corretamente a Árvore B+ com o CRUD genérico e garantir que episódios estejam sempre associados a séries válidas.
- **Resultado**: O projeto está funcionando como esperado. Conseguimos realizar todas as operações de forma eficiente, e o código está modular e bem estruturado.
- **Aprendizado**: Reforçei conceitos de chave primária e estrangeira, e a importância de consistência nos relacionamentos entre entidades.

---

## Checklist

- [x] As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? **Sim**
- [x] As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? **Sim**
- [x] Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? **Sim**
- [x] O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? **Sim**
- [x] Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries? **Sim**
- [x] Há uma visualização das séries que mostre os episódios por temporada? **Sim**
- [x] A remoção de séries checa se há algum episódio vinculado a ela? **Sim**
- [x] A inclusão da série em um episódio se limita às séries existentes? **Sim**
- [x] O trabalho está funcionando corretamente? **Sim**
- [x] O trabalho está completo? **Sim**
- [x] O trabalho é original e não a cópia de um trabalho de outro grupo? **Sim**

---

## Como executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/PauloHenriqueLopes/TP01Aeds3.git
   cd TP01Aeds3
