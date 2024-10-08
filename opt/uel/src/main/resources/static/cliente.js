$(document).ready(function() {
    // Função de Login
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        var loginData = {
            email: $('#emailLogin').val(),
            senha: $('#senhaLogin').val(),
            data: $('#dataLogin').val()
        };

        $.ajax({
            url: '/clientes/login', // Endpoint para validar login
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(loginData),
            success: function(response) {
                if (response.success) {
                    // Redireciona o usuário para a página de operações, passando o CPF pela URL
                    window.location.href = '/homeusuario/' + response.clienteCpf;

                }
            },
            error: function() {
                alert('Email ou Senha Incorretos.');
            }
        });
    });

    // Função de Cadastro
    $('#cadastroForm').submit(function(event) {
        event.preventDefault();

        var cadastroData = {
            cpf: $('#cpf').val(),
            pnome: $('#pnome').val(),
            snome: $('#snome').val(),
            email: $('#email').val(),
            senha: $('#senha').val(),
            endereco: $('#endereco').val(),
        };

        $.ajax({
            url: '/clientes/cadastro',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(cadastroData),
            success: function() {
                alert('Cadastro realizado com sucesso!');
                $('#cadastroForm')[0].reset(); // Limpa o formulário
            },
            error: function() {
                alert('Erro no cadastro.');
            }
        });

    });
});
