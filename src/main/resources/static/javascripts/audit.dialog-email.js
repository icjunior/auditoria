Brewer = Brewer || {};

Brewer.DialogoEmail = (function() {

	function DialogoEmail() {
		this.email = $('.js-email-btn');
	}

	DialogoEmail.prototype.iniciar = function() {
		this.email.on('click', onEnviarEmailClicado.bind(this));
		
		if (window.location.search.indexOf('enviado') > -1) {
			console.log('devo mostrar a mensagem de enviado com sucesso');
			swal('Pronto!', 'O e-mail foi enviado com sucesso!', 'success');
		}
	}

	function onEnviarEmailClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');

		swal({
				title: 'Informe o e-mail do destinatário',
				type: 'input',
				inputPlaceholder: 'E-mail'
			}, function(inputValue){
				console.log('url', url);
				onEnviarEmailConfirmado(inputValue, url, objeto);
			})
	}
	
	function onEnviarEmailConfirmado(inputValue, url, objeto) {
		var email = inputValue;
		
		console.log('Vou enviar o email para ', email);
		console.log('Com o código de auditoria', objeto);
		console.log('para a url', url);
		
		$.ajax({
			url : url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				email : email,
				codigo: objeto
			}),
			error : onErroEnvioEmail.bind(this),
			success : onSucessoEnvioEmail.bind(this)
		});
	}
	
	function onErroEnvioEmail(obj){
		console.log('Deu erro ao enviar email');
	}
	
	function onSucessoEnvioEmail(obj){
		console.log('Sucesso ao enviar email');
		
		var urlAtual = window.location.href;
		var separator = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('enviado') > -1 ? urlAtual : urlAtual
				+ separator + 'enviado';

		window.location = novaUrl;
	}

	return DialogoEmail;
}());

$(function() {
	var dialogo = new Brewer.DialogoEmail();
	dialogo.iniciar();
});
