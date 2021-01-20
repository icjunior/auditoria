Brewer = Brewer || {};

Brewer.DialogoFinalizar = (function() {

	function DialogoFinalizar() {
		this.email = $('.js-finalizar-btn');
	}

	DialogoFinalizar.prototype.iniciar = function() {
		this.email.on('click', onFinalizarClicado.bind(this));
		
		if (window.location.search.indexOf('enviado') > -1) {
			console.log('devo mostrar a mensagem de enviado com sucesso');
			swal('Pronto!', 'O e-mail foi enviado com sucesso!', 'success');
		}
	}

	function onFinalizarClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');

		swal({
				title: 'Tem certeza?',
				text : `Deseja finalizar a auditoria ${objeto}?`,
				showCancelButton : true,
				confirmButtonCollor : '#DD6B55',
				confirmButtonText : 'Sim, finalize agora!',
				closeOnConfirm: false
			}, (isConfirm) => {
				if(isConfirm) {
					onFinalizarConfirmado(url, objeto);								
				}
		})
					
	}
	
	function onFinalizarConfirmado(url, objeto) {
		console.log('Com o código de auditoria', objeto);
		console.log('para a url', url);
		
		$.ajax({
			url : url,
			method : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify({
				codigo: objeto
			}),
			error : onErroFinalizar.bind(this),
			success : onSucessoFinalizar.bind(this)
		});
	}
	
	function onErroFinalizar(obj){
		console.log('Deu erro ao finalizar auditoria');
		swal(':-(', 'Existem perguntas que não foram respondidas. Revise-as e tente finalizar novamente.', 'error');
	}
	
	function onSucessoFinalizar(obj){
		console.log('Sucesso ao finalizar auditoria');
		
		var urlAtual = window.location.href;
		var separator = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('enviado') > -1 ? urlAtual : urlAtual
				+ separator + 'enviado';

		window.location = novaUrl;
	}

	return DialogoFinalizar;
}());

$(function() {
	var dialogo = new Brewer.DialogoFinalizar();
	dialogo.iniciar();
});
