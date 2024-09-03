# Premissas

## Painel do Cliente

### Cadastro Primeiro:
O cliente precisa se cadastrar para enviar mensagens. O cadastro pode ser feito pela interface web, onde verificamos se o e-mail, telefone e CNPJ são únicos. Durante o cadastro, ele precisa preencher o nome, email, senha, telefone, CPF do responsável, CNPJ, nome da empresa e escolher o plano. 

- **Plano Pré-pago**: Começa com R$ 50,00 de saldo.
- **Plano Pós-pago**: Começa com um limite de R$200,00.

### Dashboard:
Após se cadastrar e logar na plataforma, o cliente verá um dashboard que varia de acordo com o plano:
- **Pós-pago**: Mostra o plano, limite, consumo e quantidade de mensagens enviadas.
- **Pré-pago**: Mostra o plano, saldo e quantidade de mensagens enviadas.

### Enviar Mensagem:
O cliente pode enviar mensagens e, ao entrar na tela, ele verá:
- **Pós-pago**: Consumo atual e limite disponível.
- **Pré-pago**: Saldo de crédito.
  
Ao enviar a mensagem, o sistema faz a validação:
- **Pós-pago**: Verifica se o consumo não ultrapassa o limite disponível.
- **Pré-pago**: Debita o valor do saldo de crédito até zerar.

### Histórico de Envio:
O cliente pode ver o histórico de envio que mostra a data, destinatário, tipo (SMS ou WhatsApp), status e o custo. No futuro, será implementado um relatório completo com filtros.

## Painel do Financeiro

### Dashboard Financeiro do Sistema:
O pessoal do financeiro verá:
- Total de clientes
- Total de mensagens enviadas
- Total de receitas (soma do custo de todas as mensagens enviadas)

### Gestão de Clientes:
Lista todos os clientes, permitindo a edição das informações. Ao acessar um cliente específico, é possível visualizar:
- **Informações Pessoais**
- **Dados da Empresa**
- **Dados Financeiros**: 

  Dependendo do plano do cliente:
  - **Pré-pago**: Mostra o saldo de créditos, plano atual, permite adicionar créditos e alterar o plano.
  - **Pós-pago**: Exibe o limite disponível, consumo, plano atual e permite alterar tanto o limite quanto o plano.

### Troca de Planos:
- **Pré-pago para Pós-pago**: Saldo atual é transferido para o limite e o saldo é zerado.
- **Pós-pago para Pré-pago**: Começa com saldo inicial de R$50,00 e o limite é zerado.

## Arquitetura Backend

Utilizei a arquitetura em camadas e organizei os pacotes para isolar bem as responsabilidades, o que facilita a manutenção e a evolução do sistema.

### Separação de Pacotes

- **api.controller**: Controladores REST, responsáveis por receber as requisições HTTP e delegar a lógica para os serviços.
- **api.dto.request e api.dto.response**: DTOs para transferência de dados entre o cliente e a API.
- **api.mapper**: ModelMapper para converter as entidades em DTOs e vice-versa.
- **domain.model**: Entidades do sistema (Cliente, Mensagem, Pessoa, Usuario).
- **domain.service**: Implementação dos serviços que processam as regras do sistema.
- **domain.repository**: Interfaces que estendem JpaRepository, permitindo operações de persistência no banco de dados.
- **core.security**: Implementação de autenticação e autorização, usando JWT para gerar e validar tokens de acesso.
- **core.util**: Utilitários de log, mensagens de validação e centralização da configuração de endpoints e segurança.

### Padrões Utilizados

- **DTO**: Comunicação entre o front-end e a API.
- **Mapper**: Uso do ModelMapper para conversão entre entidades e DTOs.
- **Service**: Isolamento da lógica de negócios.
- **Repository**: Gerenciamento da persistência dos dados.
- **Exception Handling**: ApiExceptionHandler para centralizar o tratamento de exceções.

## Segurança

Uso de JWT (JSON Web Tokens) para autenticação e controle de acesso. As permissões são baseadas em perfis como FINANCEIRO e CLIENTE.

## Relacionamento entre as Entidades

O Cliente está diretamente relacionado com uma Pessoa e um Usuario. Quando o cliente se cadastra, automaticamente criamos um Usuario vinculado à Pessoa que representa esse cliente. Esse relacionamento garante que cada cliente tenha suas informações pessoais e de login bem definidas e conectadas.

### Estrutura de Entidades

- **Cliente**: Representa a empresa ou indivíduo que utiliza o sistema.
- **Pessoa**: Armazena as informações pessoais do cliente.
- **Usuario**: Ligado à pessoa e ao cliente, responsável pelos dados de acesso ao sistema.

Quando um novo cliente é cadastrado, também são criados a pessoa e o usuário associados, garantindo a integração dos dados e o acesso ao sistema sem configurações adicionais.
