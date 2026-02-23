# Projeto Next.js

Aplicação construída com **Next.js** pronta para desenvolvimento e
produção.

------------------------------------------------------------------------

## Pré-requisitos

Antes de começar, você precisa ter instalado:

-   Node.js (recomendado \>= 18)
-   npm, yarn ou pnpm

Verifique as versões:

``` bash
node -v
npm -v
```

------------------------------------------------------------------------

## Configuração do ambiente

### Clone o repositório

``` bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_PROJETO>
```

### Instale as dependências

``` bash
npm install
```

ou

``` bash
yarn install
```

ou

``` bash
pnpm install
```

------------------------------------------------------------------------

## Variáveis de ambiente

O projeto utiliza variáveis de ambiente.

### Copie o arquivo de exemplo (se existir):

``` bash
cp .env.example .env
```

### Ou crie manualmente o `.env` na raiz do projeto

Exemplo:

``` env
VITE_API_URL=http://localhost:8080
```

Ajuste conforme necessário para seu ambiente.

------------------------------------------------------------------------

## Rodando em desenvolvimento

``` bash
npm run dev
```

A aplicação estará disponível em:

http://localhost:3000

------------------------------------------------------------------------

## Build para produção

``` bash
npm run build
```

------------------------------------------------------------------------

## Rodar em produção

``` bash
npm start
```

------------------------------------------------------------------------

## Lint

``` bash
npm run lint
```

------------------------------------------------------------------------

## Scripts disponíveis

-   `dev` → roda em modo desenvolvimento
-   `build` → cria build de produção
-   `start` → inicia servidor em produção
-   `lint` → verifica padrões de código

------------------------------------------------------------------------

## Contribuição

Sinta-se à vontade para abrir issues ou pull requests com melhorias.

------------------------------------------------------------------------

## Licença

Este projeto está sob a licença MIT.
