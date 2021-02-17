var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	function UploadFoto() {
		this.inputNomeFoto = $('input[name = files]');
		this.nomeFoto = document.getElementById('files');
		this.arrayFoto = [];
		
		if(this.nomeFoto.value){
			this.arrayFoto.push(this.nomeFoto.value);
		}
				
		this.inputContentType = $('input[name = contentType]');
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);

		this.containerFotoCerveja = $('.js-container-foto-cerveja');
		this.uploadDrop = $('#upload-drop');
	}

	UploadFoto.prototype.iniciar = function() {
		var settings = {
			type : 'json',
			filelimit : 10,
			allow : '*.(jpg|jpeg|png)',
			action : this.containerFotoCerveja.data('url-fotos'),
			complete : onUploadCompleto.bind(this),
			beforeSend : adicionarCsrfToken
		}

		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop($('#upload-drop'), settings);

		//console.log("O nome do inputNomeFoto: " + this.inputNomeFoto.val());

		if (this.inputNomeFoto.val()) {
			onUploadCompleto.call(this, {
				nome : this.inputNomeFoto.val(),
				contentType : this.inputContentType.val()
			});
		}
	}

	function adicionarCsrfToken(xhr) {
		var token = $('input[name = _csrf]').val();
		var header = $('input[name = _csrf_header]').val();

		xhr.setRequestHeader(header, token);
	}

	function onUploadCompleto(resposta) {		
		this.arrayFoto.push(resposta.nome);
		console.log(`${this.arrayFoto}`);

		//this.inputNomeFoto.val(resposta.nome);
		this.inputNomeFoto.val(this.arrayFoto);
		this.inputContentType.val(resposta.contentType);

		//this.uploadDrop.addClass('hidden');
		var htmlFotoCerveja = this.template({
			nomeFoto : resposta.nome
		});
		this.containerFotoCerveja.append(htmlFotoCerveja);

		$('.js-foto-remove').on('click', onRemoverFoto.bind(this));
	}

	function onRemoverFoto() {
		$('.js-foto-cerveja').remove();
		//this.uploadDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}

	return UploadFoto;
})();

$(function() {

	var uploadFoto = new Brewer.UploadFoto();

	uploadFoto.iniciar();
});