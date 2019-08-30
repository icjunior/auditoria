package br.com.bigsupermercados.audit.dto;

public class AnaliseRespostasDTO {

	private Long totalRespostasSatisfatorias;
	private Long totalRespostasInsatisfatorias;

	public AnaliseRespostasDTO(Long totalRespostasSatisfatorias, Long totalRespostasInsatisfatorias) {
		super();
		this.totalRespostasSatisfatorias = totalRespostasSatisfatorias;
		this.totalRespostasInsatisfatorias = totalRespostasInsatisfatorias;
	}

	public Long getTotalRespostasSatisfatorias() {
		return totalRespostasSatisfatorias;
	}

	public void setTotalRespostasSatisfatorias(Long totalRespostasSatisfatorias) {
		this.totalRespostasSatisfatorias = totalRespostasSatisfatorias;
	}

	public Long getTotalRespostasInsatisfatorias() {
		return totalRespostasInsatisfatorias;
	}

	public void setTotalRespostasInsatisfatorias(Long totalRespostasInsatisfatorias) {
		this.totalRespostasInsatisfatorias = totalRespostasInsatisfatorias;
	}
}
