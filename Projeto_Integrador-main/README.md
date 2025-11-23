# 🚌 Simulador de Ônibus Autônomo -- Projeto Integrador

Sistema desenvolvido para simular rotas, consumo energético e
gerenciamento de frota de ônibus autônomos dentro de um campus fictício.

## 📌 Sobre o Projeto

Este repositório contém o desenvolvimento do **Projeto Integrador do 5º
semestre do curso de Engenharia da Computação da UniFECAF**.

O objetivo do projeto é demonstrar, na prática, diversas competências
aprendidas ao longo do semestre, incluindo:

-   Modelagem orientada a objetos\
-   Estruturação de serviços\
-   Simulação física simplificada\
-   Algoritmos de roteamento\
-   Boas práticas de desenvolvimento

🔹 **IMPORTANTE:**\
O campus utilizado no mapa e na simulação é **TOTALMENTE FICTÍCIO** --
criado apenas para fins didáticos.

------------------------------------------------------------------------

## 🎯 Funcionalidades Principais

### 🗺️ 1. Mapa do Campus

O sistema contém um mapa fictício com pontos e distâncias
pré-configuradas.

### 🔄 2. Cálculo da Melhor Rota

-   Define um ponto inicial\
-   Gera automaticamente a ordem ideal de visita aos demais pontos\
-   Apresenta a rota otimizada

### ⚙️ 3. Cálculo Físico Trecho a Trecho

Para cada trecho são simulados:\
- Distância percorrida\
- Velocidade média\
- Tempo estimado\
- Energia consumida baseada na massa e motor do ônibus

### 🚐 4. Gerenciamento de Frota

-   Frota com múltiplos ônibus autônomos\
-   Escolha manual do veículo antes da simulação\
-   Verificação de autonomia antes do início\
-   Cálculo da autonomia restante após toda a rota

### 🧾 5. Relatório Final no Console

Exibe todas as informações da viagem, incluindo:\
- Trechos percorridos\
- Distância total\
- Tempo total\
- Energia consumida total\
- Autonomia final disponível

------------------------------------------------------------------------

## 📁 Estrutura do Projeto

```
src/
 └── br/
      └── com/
           └── fecaf/
                ├── model/
                │     ├── Motor.java
                │     ├── OnibusAutonomo.java
                │     ├── Veiculo.java
                │     └── MapaCampus.java
                │
                ├── service/
                │     ├── CalculoFisicoService.java
                │     └── MelhorRota5PontosService.java
                │
                ├── ui/
                │     └── SimuladorFrame.java
                │
                └── Main.java
```

------------------------------------------------------------------------

## ▶️ Como Executar

### ✔ Requisitos

-   **Java 17+**
-   IDE como IntelliJ IDEA, Eclipse ou VSCode

### ✔ Rodando o Projeto

``` 
git clone https://github.com/gustavoazzola76261-arch/Projeto_Integrador
```

Abra o projeto na IDE e execute:

    src/Main.java

------------------------------------------------------------------------

## 🛠 Tecnologias Utilizadas

-   Java 17\
-   Programação Orientada a Objetos\
-   Simulação física de energia/tempo\
-   Algoritmos de busca e otimização\
-   Estruturas de dados

------------------------------------------------------------------------

## 👤 Autor

**Gustavo Azzola**  
**Paulo César**  
**Nelson Vitor**  
Estudantes de Engenharia da Computação — 5º Semestre  
UniFECAF (Campus fictício no contexto do projeto)

------------------------------------------------------------------------

## 📜 Licença

Uso livre para fins educacionais.
