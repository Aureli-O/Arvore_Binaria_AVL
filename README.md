
# Árvore Binaria AVL com HASH
## Trabalho de Estruturas de Dados

Nesse Trabalho foi feito uma Árvore Binaria AVL,em que é armazenado em cada Nó da Árvore uma linha do arquivo de texto,antes de ser adicionada ela passa por um tratamento usando o SHA1 passando de texto comum para texto HASH,ou criptografado.
A Árvore armazena os HASH e os balanceia usando a sua altura na árvore, nos testes o código teve um desempenho e execução perfeitos,tanto usando Strings como Números. Após isso é feito um método em que os HASH's são somados e tratados novamente para no final sobrar um HASH único que caso algo no arquivo seja mudado ele irá mudar.


### Passo a passo resumido do funcionamento do código:

- Cria Árvore Binária de Busca AVL
- Lê o texto
- Adiciona linha por linha do texto na Árvore passando pelo tratamento do Hash
- No método Adicionar ele balanceia a Árvore toda vez que um elemento novo é adicionado
- Gera um código Hash resultado das somas dos filhos esquerdo e direito,essa função percorre toda a Árvore
- No final é gerado um único código resultado da soma de todos os Hash's dos Nós.