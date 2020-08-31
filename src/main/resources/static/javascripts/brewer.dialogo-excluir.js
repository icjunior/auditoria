Brewer = Brewer || {};

Brewer.DialogoExcluir = (function() {

	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn');
		this.botaoInativar = $('.js-botao-inativar');
	}

	DialogoExcluir.prototype.iniciar = function() {
		this.botaoInativar.on('click', onInativarClicado.bind(this));
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));

		if (window.location.search.indexOf('excluido') > -1) {
			console.log('devo mostrar a mensagem de excluído com sucesso');
			swal('Pronto!', 'Excluído com sucesso!', 'success');
		}
	}

	function onExcluirClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');

		swal({
			title : 'Tem certeza?',
			text : 'Excluir ' + objeto + '? Você não poderá recuperar depois',
			showCancelButton : true,
			confirmButtonCollor : '#DD6B55',
			confirmButtonText : 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));
	}
	
	function onInativarClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');

		swal({
			title : 'Tem certeza?',
			text : 'Excluir ' + objeto + '? Você não poderá recuperar depois',
			showCancelButton : true,
			confirmButtonCollor : '#DD6B55',
			confirmButtonText : 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onInativarConfirmado.bind(this, url));
	}

	function onExcluirConfirmado(url) {
		$.ajax({
			url : url,
			method : 'DELETE',
			success : onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onInativarConfirmado(url) {
		$.ajax({
			url : url,
			method : 'PUT',
			success : onInativadoSucesso.bind(this),
			error: onErroInativar.bind(this)
		});
	}
	
	function onInativadoSucesso() {
		var urlAtual = window.location.href;
		var separator = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual
				+ separator + 'excluido';

		window.location = novaUrl;
	}

	function onExcluidoSucesso() {
		var urlAtual = window.location.href;
		var separator = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual
				+ separator + 'excluido';

		window.location = novaUrl;
	}
	
	function onErroExcluir(e){
		swal('Oops!', e.responseText, 'error');
	}

	function onErroInativar(e){
		swal('Oops!', e.responseText, 'error');
	}

	return DialogoExcluir;
}());

$(function() {
	var dialogo = new Brewer.DialogoExcluir();
	dialogo.iniciar();
});
