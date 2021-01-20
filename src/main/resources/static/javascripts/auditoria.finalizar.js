function finalizarAuditoria(){
	const codigoAuditoria = document.getElementById('auditoria').textContent;
	
	console.log(`Auditoria selecionada ${codigoAuditoria}`);
	
	const token = $("meta[name='_csrf']").attr("content");
	
	swal({
		title : 'Tem certeza?',
		text : `Deseja finalizar a auditoria ${codigoAuditoria.textContent}?`,
		showCancelButton : true,
		confirmButtonCollor : '#DD6B55',
		confirmButtonText : 'Sim, finalize agora!',
		closeOnConfirm: false
	}, (isConfirm) => {
			if(isConfirm) {
				putFinalizaAuditoria(codigoAuditoria, token)
					.then((resposta) => {
						window.location.href = `/audit/auditorias`;
						swal(`Pronto!`, `Auditoria finalizada com sucesso!`, 'success').then();
					})
					.catch((erro) => {
						console.log(erro);
						swal('Erro!', `A auditoria não foi finalizada. Tente novamente mais tarde.`, 'error');
					})
			}
	})
}

const putFinalizaAuditoria = async (codigoAuditoria, token) => {
	console.log(`Auditoria recebida ${codigoAuditoria}`);
	
	const uri = `/audit/auditorias/fechar/{codigoAuditoria}`;

	const requestInfo = {
		method : 'PATCH',
		headers : {
			'Content-type' : 'application/json',
		 	'X-CSRF-TOKEN' : token
		}
	};
	
	const resposta = await fetch(uri, requestInfo);
	
	return resposta;
}