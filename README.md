# BlackJack

Esta é uma implementação básica do jogo blackjack.

- Você irá jogar contra o dealer. 
- Valor da cartá Ás depende da pontuação atual da mão.
- Multiplayer ainda não suportado.
- As jogadas de dividir, dobrar e se render não foram implementados.

## Executando

Como toda aplicação maven, primeiro é precisamos fazer uma build.
```shell script
./mvnw package
```
Feito o build, basta executarmos via terminal o arquivo criado em target/quarkus-app/quarkus-run.jar
```shell script
java -jar target/quarkus-app/quarkus-run.jar
```

> **_NOTE:_** A aplicação foi implementada utilizando o java 11 e maven 3.9.2.


