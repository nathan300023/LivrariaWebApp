
---

# 📚 **Livraria Virtual - Backend** | *Banco de Dados 1*

Este projeto é o desenvolvimento de um **backend** para uma livraria virtual, que oferece uma ampla variedade de livros, permitindo aos clientes navegar pelo catálogo, adicionar itens ao carrinho de compras e efetivar suas compras. O backend tem como foco a integração com banco de dados e a geração de relatórios e gráficos.

---

## 🔖 **Índice**

1. [📘 Descrição da Loja Virtual](#-descrição-da-loja-virtual)
2. [🎯 Objetivos do Projeto](#-objetivos-do-projeto)
3. [🛠️ Funcionalidades Principais](#%EF%B8%8F-funcionalidades-principais)
4. [📊 Relatórios Relevantes](#-relatórios-relevantes)
5. [💻 Tecnologias Utilizadas](#-tecnologias-utilizadas)

---

## 📘 **Descrição da Loja Virtual**

A loja virtual escolhida pela equipe é uma **livraria online** que oferece uma vasta coleção de livros. Os clientes podem:
- **Navegar pelo catálogo de livros**
- **Pesquisar por título ou autor**
- **Adicionar itens ao carrinho de compras**
- **Finalizar a compra através de uma API REST**.

Este projeto implementa todas essas funcionalidades no **backend**, com foco na **integração do banco de dados** e na geração de **relatórios e gráficos** de desempenho da loja.

---

## 🎯 **Objetivos do Projeto**

O principal objetivo deste projeto é:
- **Desenvolver o backend** de uma livraria virtual com foco em integração de banco de dados e arquitetura em camadas.
- Aplicar conceitos de **Model-View-Controller (MVC)** em uma API REST.
- Proporcionar **experiência prática com SQL**, lidando com a manipulação direta dos dados do banco sem o uso de frameworks de persistência.
- **Gerar relatórios e gráficos** para análise das vendas, estoque e clientes.

---

## 🛠️ **Funcionalidades Principais**

1. **Cadastro de Produtos (Livros)**:
    - Os livros serão cadastrados diretamente no banco de dados via comandos SQL.

2. **Cadastro de Clientes**:
    - Clientes podem se cadastrar criando uma conta com informações como nome, cpf, etc.
    - Os clientes cadastrados poderão acessar seu **histórico de compras** e revisar suas **informações de conta**.

3. **Efetivação de Compras**:
    - Clientes podem **selecionar livros**, adicionar ao carrinho e finalizar a compra.

4. **Geração de Relatórios e Gráficos**:
    - O backend oferece funcionalidades para gerar relatórios de desempenho da loja.

---

## 📊 **Relatórios Relevantes**

1. **Relatório de Vendas Diárias/Semanais/Mensais**:
    - Exibe o **total de vendas** realizadas em um período específico, detalhando os livros mais vendidos.

2. **Relatório de Estoque**:
    - Mostra a **quantidade de livros disponíveis** em estoque.
    - Destaca os livros com estoque baixo, alertando sobre a necessidade de reposição.

3. **Relatório de Clientes**:
    - Lista os **clientes mais ativos**, detalhando o número de compras realizadas e o valor total gasto por cada cliente.

Esses relatórios utilizam **consultas SQL** para realizar **junções, agregações, ordenações** e **subconsultas**, explorando diferentes funcionalidades da linguagem SQL.

---

## 💻 **Tecnologias Utilizadas**

- **Linguagem**: Java (Java EE)
- **Banco de Dados**: PostgreSQL
- **API REST**: JSON como formato de troca de dados
- **Padrão de Arquitetura**: Model-View-Controller (MVC)
- **Ferramentas de Manipulação de API**: Insomnia (para simulação de operações da loja)

---

