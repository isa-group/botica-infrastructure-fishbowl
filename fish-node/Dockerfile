# --- Stage 1: Build ---
FROM node:22 AS builder
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build && npm prune --production


# --- Stage 2: Runtime ---
FROM node:22-slim
WORKDIR /app

COPY --from=builder /app .

CMD [ "npm", "start" ]
