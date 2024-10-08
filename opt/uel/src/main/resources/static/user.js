// Function to load books and attach event handlers
function loadBooks() {
    $.ajax({
        url: '/lista-livros', // URL to fetch the list of books
        type: 'GET',
        success: function (books) {
            books.forEach(function (book) {
                $('#bookList').append(
                    '<tr>' +
                    '<td>' + book.titulo + '</td>' +
                    '<td>' + parseFloat(book.preco).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + '</td>' +
                    '<td>' + book.isbn + '</td>' +
                    '<td>' +
                    '<button class="adicionaCarrinho" data-isbn="' + book.isbn + '" data-cpf="' + $('#clienteCpf').val() + '">Adicionar ao Carrinho</button>' +
                    '</td>' +
                    '</tr>'
                );
            });

            // Attach the click event to the "Adicionar ao Carrinho" buttons after they are added to the DOM
            attachAddToCartEvent();
        },
        error: function () {
            alert('Erro ao carregar a lista de livros.');
        }
    });
}

// Function to attach click event to "Adicionar ao Carrinho" buttons
function attachAddToCartEvent() {
    console.log("Função attachAddToCartEvent chamada");
    $(".adicionaCarrinho").off('click').on('click', function() {
        var dadosCarrinhoLivro = {
            isbn: $(this).data('isbn'),
            cpf: $(this).data('cpf'),
            quantidade: 1
        };

        $.ajax({
            url: '/carrinho-livro', // Route to add to cart
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dadosCarrinhoLivro),
            success: function(response) {
                alert("Livro adicionado ao carrinho!");
                atualizarCarrinho(response);
            },
            error: function(error) {
                console.error("Erro ao adicionar ao carrinho: ", error);
                alert("Ocorreu um erro ao adicionar o livro ao carrinho.");
            }
        });
    });
}

// Call the function to load books when the document is ready
$(document).ready(function() {
    loadBooks();
});