var Brewer = Brewer || {};

Brewer.MultiSelecao = (function() {

	function MultiSelecao() {
		this.selecaoCheckbox = $('.js-selecao');
		this.selecaoTodosCheckbox = $('.js-selecao-todos');
	}

	MultiSelecao.prototype.iniciar = function() {
		this.selecaoTodosCheckbox.on('click', onSelecaoTodosClicado.bind(this));
		this.selecaoCheckbox.on('click', onSelecaoClicado.bind(this));
	}

	function onSelecaoTodosClicado() {
		var status = this.selecaoTodosCheckbox.prop('checked');
		this.selecaoCheckbox.prop('checked', status);
	}

	function onSelecaoClicado() {
		var selecaoCheckboxChecados = this.selecaoCheckbox.filter(':checked');
		this.selecaoTodosCheckbox.prop('checked',
				selecaoCheckboxChecados.length >= this.selecaoCheckbox.length);
	}
	
	return MultiSelecao;
}());

$(function() {
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
});
