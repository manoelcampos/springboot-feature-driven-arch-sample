# syntax = docker/dockerfile:1

ARG NODE_VERSION=23.1.0
FROM node:${NODE_VERSION}-alpine as base

# https://nextjs.org/telemetry
ENV NEXT_TELEMETRY_DISABLED=1

LABEL fly_launch_runtime="Next.js"

# Next.js app lives here
WORKDIR /app

# Set production environment
ENV NODE_ENV="production"

# Se uma variável de ambiente com o nome abaixo não for definida, usa este valor padrão.
ARG NEXT_PUBLIC_API_URL="http://localhost:8080"
ENV NEXT_PUBLIC_API_URL=$NEXT_PUBLIC_API_URL

# Instala versão atualizada do yarn, como indicado em .yarnrc.yml
RUN corepack enable

# Throw-away build stage to reduce size of final image
FROM base as build

# Install packages needed to build node modules
RUN apk update && apk upgrade && apk add --no-cache libc6-compat

# Install dependencies only when needed
FROM base AS builder
WORKDIR /app

# Install node modules
COPY . .
RUN yarn install --immutable

# Build the app (in standalone mode based on next.config.js)
RUN yarn build

# Production image, copy all the files and run next
FROM base AS runner
WORKDIR /app

ARG USERNAME=nodejs
# add the user and group we'll need in our final image
RUN addgroup --system --gid 1001 ${USERNAME}
RUN adduser --system --uid 1001 ${USERNAME}

# Copy built application
COPY --from=builder --chown=${USERNAME} /app /app
# copy the static folder inside the .next folder generated from the build process
COPY --from=builder --chown=${USERNAME} /app/.next/static ./.next/static

USER ${USERNAME}

# A porta aqui deve ser a mesma definida no fly.toml
ENV PORT 3000
EXPOSE ${PORT}

CMD [ "yarn", "start" ]
