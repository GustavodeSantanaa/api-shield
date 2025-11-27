# 🛡️ API Shield — Arquitetura do Sistema

O **API Shield** é uma plataforma modular de segurança para APIs, criada para:
- Aplicar rate limiting
- Registrar requisições de forma estruturada
- Detectar abuso de uso
- Fornecer métricas de tráfego e segurança

Este documento descreve a arquitetura utilizada e o motivo das decisões técnicas.

---

## 🧱 1. Arquitetura em Camadas (Layered Architecture)

O projeto segue o modelo de **3 camadas principais**:

### **1. Controller Layer**
- Recebe requisições HTTP.
- Valida entradas básicas.
- Encaminha para os serviços.
- Retorna respostas HTTP.

### **2. Service Layer**
- Implementa regras de negócio.
- Aplica os padrões de projeto (Strategy, Factory, Template Method).
- Orquestra serviços.
- Contém as implementações em `service.impl`.

### **3. Repository Layer**
- Persistência com PostgreSQL.
- Repositórios Spring Data JPA.
- Entidades são armazenadas em `domain`.

---

## 🧩 2. Padrões de Projeto Utilizados

### 🧠 **Strategy Pattern**
Usado para modularizar comportamentos de segurança, permitindo trocar lógicas sem alterar o restante do código.

Chamadas atuais:
- `RateLimitStrategy`
- `AbuseDetectionStrategy`
- `LoggingStrategy`

📌 **Motivo:**  
Permite que as proteções sejam ativadas, desativadas ou trocadas futuramente sem mexer no core da aplicação.

---

### 🧱 **Factory Method**
Usado para criar instâncias de estratégias de segurança.

📌 **Motivo:**  
Evita criar objetos manualmente no controller ou service, deixando a aplicação preparada para expansão futura.

---

### 🧵 **Template Method (futuro, Sprint 2)**
Será aplicado no fluxo de validação da requisição:

📌 **Motivo:**  
Padroniza o fluxo garantindo que todos os endpoints passem pelas mesmas etapas de segurança.

---

## 🗂️ 3. Estrutura de Pacotes

```text
com.apishield.core
 ├── config          → Configurações do Spring (WebConfig, interceptors)
 ├── controller      → Endpoints REST
 ├── service         → Interfaces de regras de negócio
 │     └── impl      → Implementações concretas
 ├── middleware      → Interceptores e middlewares customizados
 ├── domain          → Entidades JPA
 ├── repository      → Repositórios Spring Data
 └── dto             → Objetos de transferência de dados
```


---

## 🗃️ 4. Banco de Dados — PostgreSQL

- Usado pela comunidade internacional em produção.
- Totalmente open-source.
- Compatível com Spring Data JPA.
- Tem forte consistência e bom desempenho.

### Entidades futuras:
- `RequestLog`
- `RateLimitRecord`
- `AbuseRecord`

---

## 🚀 5. Objetivo da Arquitetura

- Ser **extensível**
- Ser **limpa e modular**
- Ter **baixo acoplamento**
- Ser fácil de manter e evoluir
- Ser atrativa para recrutadores e empresas

Com essa arquitetura, adicionar novas funcionalidades (ex: bloqueio por geolocalização, throttling avançado, cache) será rápido e seguro.

---

## 📌 Status Atual da Arquitetura (Sprint 1)
- Estrutura criada
- Interfaces criadas
- Configurações iniciais sendo preparadas
- Interceptor será implementado no Dia 3

---

## ✨ Conclusão

A arquitetura do API Shield foi planejada para ser robusta, moderna e escalável.  
Este documento será atualizado conforme novas Sprints forem finalizadas.