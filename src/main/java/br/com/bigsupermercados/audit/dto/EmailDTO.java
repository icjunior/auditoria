package br.com.bigsupermercados.audit.dto;

public class EmailDTO {

	private String email;
	private Long codigo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
