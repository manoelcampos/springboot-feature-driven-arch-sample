ENV_FILE?=.env.staging

.PHONY: docker install app

install:
	# Instala versão atualizada do yarn, como indicado em .yarnrc.yml
	corepack enable

app:
	# Instala dependências da app, faz o build e inicia o app
	yarn install && yarn build && yarn start

docker:
	echo "Passe um arquivo .env como argumento, ex: make docker ENV_FILE=.env"
	echo "Usando arquivo de configuração: $(ENV_FILE)"

	# Executa o arquivo .env para criar variáveis de ambiente (usa ; e \ para rodar todos os comandos num mesmo shell)
	set -a; . $(ENV_FILE); set +a; \
	docker build --build-arg NEXT_PUBLIC_API_URL=${NEXT_PUBLIC_API_URL} -t sigad-frontend . ; \
	docker run --rm -p 3000:3000 sigad-frontend
