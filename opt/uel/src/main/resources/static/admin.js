$(document).ready(function () {
    // Função para buscar e exibir os livros
    function loadBooks() {
        $.ajax({
            url: '/books',
            method: 'GET',
            dataType: 'json',
            success: function (books) {
                var bookList = $('#bookList tbody');
                bookList.empty(); // Limpa a tabela antes de preencher

                books.forEach(function (book) {
                    bookList.append(
                        '<tr>' +
                        '<td>' + book.isbn + '</td>' +
                        '<td>' + book.titulo + '</td>' +
                        '<td>' + book.autor + '</td>' +
                        '<td>' + book.editora + '</td>' +
                        '<td>' + parseFloat(book.preco).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + '</td>' +
                        '<td>' + book.estoque + '</td>' +
                        '<td>' +
                        '<button class="edit-btn" data-isbn="' + book.isbn + '">Editar</button>' + // Botão de editar
                        '<button class="delete-btn" data-isbn="' + book.isbn + '">Deletar</button>' + // Botão de deletar
                        '</td>' +
                        '</tr>'
                    );
                });
            },
            error: function () {
                alert('Erro ao carregar a lista de livros.');
            }
        });
    }

    // Chama a função para carregar os livros ao abrir a página
    loadBooks();

    // Evento de envio do formulário para adicionar ou atualizar livro
    $('#addBookForm').submit(function (event) {
        event.preventDefault();

        var newBook = {
            isbn: $('#isbn').val(),
            titulo: $('#titulo').val(),
            autor: $('#autor').val(),
            editora: $('#editora').val(),
            preco: $('#preco').val(),
            estoque: $('#estoque').val()
        };

        var method = $(this).data('edit') ? 'PUT' : 'POST'; // Verifica se é edição ou adição
        var url = $(this).data('edit') ? '/books/' + $(this).data('isbn') : '/books'; // URL para adicionar ou editar

        $.ajax({
            url: url,
            method: method,
            contentType: 'application/json',
            data: JSON.stringify(newBook),
            success: function () {
                alert('Livro ' + ($(this).data('edit') ? 'atualizado' : 'adicionado') + ' com sucesso!');
                $('#addBookForm')[0].reset(); // Limpa o formulário
                $('#addBookForm button[type="submit"]').text('Adicionar Livro'); // Restaura o texto do botão
                loadBooks(); // Atualiza a lista de livros
                $('#addBookForm').removeData('edit').removeData('isbn'); // Limpa os dados de edição
            },
            error: function () {
                alert('Erro ao ' + ($(this).data('edit') ? 'atualizar' : 'adicionar') + ' o livro.');
            }
        });
    });

    $(document).on('click', '.edit-btn', function () {
        var isbn = $(this).data('isbn');

        $.ajax({
            url: '/books/' + isbn,
            method: 'GET',
            dataType: 'json',
            success: function (book) {
                // Preenche o formulário com os dados do livro
                $('#isbn').val(book.isbn);
                $('#titulo').val(book.titulo);
                $('#autor').val(book.autor);
                $('#editora').val(book.editora);
                $('#preco').val(book.preco);
                $('#estoque').val(book.estoque);

                $('#addBookForm button[type="submit"]').text('Atualizar Livro');
                $('#addBookForm').data('edit', true).data('isbn', isbn);
            },
            error: function () {
                alert('Erro ao buscar detalhes do livro.');
            }
        });
    });

    // Evento de clique no botão de deletar
    $(document).on('click', '.delete-btn', function () {
        var isbn = $(this).data('isbn'); // Obtém o código do livro a ser deletado

        // Confirmação antes de deletar
        if (confirm('Você tem certeza que deseja deletar este livro?')) {
            // Faz uma requisição para deletar o livro
            $.ajax({
                url: '/books/' + isbn, // Endpoint para deletar o livro
                method: 'DELETE', // Método HTTP DELETE
                success: function () {
                    alert('Livro deletado com sucesso!');
                    loadBooks(); // Atualiza a lista de livros após a deleção
                },
                error: function () {
                    alert('Erro ao deletar o livro.'); // Alerta sobre o erro
                }
            });
        }
    });
});
